package box_overflow.game;

import box_overflow.main.Config;
import box_overflow.main.Window;
import box_overflow.screen.GameManager;
import box_overflow.screen.render.shape.ShapeRenderer;
import box_overflow.screen.render.text.FontRenderer;
import box_overflow.screen.render.text.StaticFonts;
import box_overflow.screen.render.texture.TextureRenderer;
import box_overflow.screen.screens.GameScreen;
import box_overflow.util.TextManager;
import box_overflow.util.XmlReader;
import box_overflow.util.math.Color4;
import box_overflow.util.math.Vec2;

import java.util.ArrayList;

public class LevelManager {

    private static final int DEATHTIME = 60;
    public static final int TRANSITIONTIME = (int)(24);

    private FontRenderer start;

    private int currentLevel;
    private int[][][] currentMap;
    private Tile tileset[];

    private Vec2 begin;
    private Vec2 end;
    private int blockPosed;
    private int blockToPose;

    private Vec2 path[];

    private int turnCounter;

    private boolean discover, death, transition, win;
    private int tWith, tHeight;
    private int counter, count, tcounter, dcounter;
    private int time;

    private ArrayList<Integer[]> blockToAdd;

    public LevelManager(){
        tileset = new Tile[22];
        tileset[0] = new Tile();
        tileset[1] = new Tile("/textures/tileset/ground.png",0,true);
        tileset[2] = new Tile("/textures/tileset/box1.png",1, false);
        tileset[3] = new Tile("/textures/tileset/box2.png",1, false);
        tileset[4] = new Tile("/textures/tileset/boxp1.png",1, false);
        tileset[5] = new Tile("/textures/tileset/boxp2.png",1, false);
        tileset[6] = new Tile("/textures/tileset/arrow-left.png",2, false);
        tileset[7] = new Tile("/textures/tileset/arrow-up.png",2, false);
        tileset[8] = new Tile("/textures/tileset/arrow-right.png",2, false);
        tileset[9] = new Tile("/textures/tileset/arrow-down.png",2, false);
        tileset[10] = new Tile("/textures/tileset/end-left.png",3, false);
        tileset[11] = new Tile("/textures/tileset/end-up.png",3, false);
        tileset[12] = new Tile("/textures/tileset/end-right.png",3, false);
        tileset[13] = new Tile("/textures/tileset/end-down.png",3, false);
        tileset[14] = new Tile("/textures/tileset/pillar.png",1, false);
        tileset[15] = new Tile("/textures/tileset/pillarh.png",1, false);
        tileset[16] = new Tile("/textures/tileset/door.png",1, false);
        tileset[17] = new Tile("/textures/tileset/nopic.png",0, true);
        tileset[18] = new Tile("/textures/tileset/pic.png",5, true);
        tileset[19] = new Tile("/textures/tileset/hole.png",4, true);
        tileset[20] = new Tile("/textures/tileset/noHole.png",0, true);
        tileset[21] = new Tile("/textures/tileset/croix.png",0, false);
        blockToAdd = new ArrayList<>();
        start = new FontRenderer(TextManager.GAME,0,StaticFonts.monofonto, Window.width*0.05f,
                new Vec2(Window.width*0.50f, Window.height*0.50f), Color4.WHITE.copy());
    }

    public void load(){
        discover = true;
        GameScreen.entityManager.getPlayer().stop();
        count = 0;
        counter = 0;
        tcounter = 0;
        dcounter = 0;
        turnCounter = 0;
        death = false;
        transition = false;
        win = false;
        currentLevel = Config.getCurrentMap();
        currentMap = XmlReader.loadMap(currentLevel);
        chargeConfig();
        GameScreen.entityManager.setPosition(begin);
        path = new Vec2[blockToPose];
        blockToAdd = new ArrayList<>();
        GameScreen.tile = GameScreen.MAX_GAMETILESIZE;
        time = 60 * 5 / blockToPose;
    }

    private void begin(){
        GameScreen.entityManager.getPlayer().stop();
        tHeight = currentMap[0].length*GameScreen.tile;
        tWith = currentMap[0][0].length*GameScreen.tile;
        if (tHeight > Window.height || tWith > Window.width) GameScreen.tile-=2;
        counter++;
        GameManager.CAMERA.setPosition(Window.width / 2 - tWith/2,Window.height / 2 - tHeight/2,false);
    }

    public void update(){
        if(discover){
            begin();
            return;
        } else if(death){
            death();
        } else if(transition) {
            if (tcounter > TRANSITIONTIME) {
                tcounter = 0;
                transition = false;
                for(Integer[] block:blockToAdd){
                    currentMap[block[0]][block[1]][block[2]] = block[3];
                }
                blockToAdd = new ArrayList<>();
                GameScreen.entityManager.getPlayer().stop();
            }
            tcounter++;
        } else if(win) {
            return;
        }

        try {
            Vec2 pos = GameScreen.entityManager.getPlayer().getPos();
            if(end.getX() == pos.getX()){
                if(end.getY() == pos.getY()){
                    if(blockPosed >= blockToPose) {
                        // Win !!!
                        win = true;
                        Config.setMapConcluded(currentLevel,2);
                        if(Config.getMapConcluded(currentLevel+1) == 0) {
                            Config.setMapConcluded(currentLevel + 1, 1);
                            Config.setLastMap(currentLevel + 1);
                        }
                        Window.gameManager.setState(GameScreen.STATE_WIN);
                        Config.close();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void display(){
        int size = GameScreen.tile;
        for (int j = 0; j < currentMap.length-1; j++) {
            for (int i = 0; i < currentMap[j].length; i++) {
                for (int a = 0; a < currentMap[j][i].length; a++) {
                    if(currentMap[j][i][a] == 0)continue;
                    tileset[currentMap[j][i][a]].bind();
                    TextureRenderer.image(a * size, i * size, size, size);
                }
            }
        }
        GameScreen.entityManager.display();
        for (int i = 0; i < currentMap[currentMap.length-1].length; i++) {
            for (int a = 0; a < currentMap[currentMap.length-1][i].length; a++) {
                if(currentMap[currentMap.length-1][i][a] == 0)continue;
                tileset[currentMap[currentMap.length-1][i][a]].bind();
                TextureRenderer.image(a * size, i * size, size, size);
            }
        }
        if(discover){
            ShapeRenderer.rectC(new Vec2(0,0), new Vec2(Window.width,Window.height), new Color4(0.0f, 0.0f, 0.0f, 0.3f));
            start.renderC();
        }
    }

    private void chargeConfig(){
        blockToPose = 0;
        blockPosed = 0;
        for(int i = 0; i < currentMap[0].length; i++){
            for(int j = 0; j < currentMap[0][i].length; j++){
                if(tileset[currentMap[0][i][j]].getType() == Tile.BEGIN){
                    GameScreen.entityManager.getPlayer().setSide(currentMap[0][i][j]-6);
                    begin = new Vec2(j,i);
                } else if(tileset[currentMap[0][i][j]].getType() == Tile.END){
                    end = new Vec2(j,i);
                }
            }
        }

        for(int i = 0; i < currentMap[0].length; i++){
            for(int j = 0; j < currentMap[0][i].length; j++){
                if(tileset[currentMap[0][i][j]].getBlock()) blockToPose++;
            }
        }
    }

    public Vec2 newPos(Vec2 pos, String newPos){
        Integer[] add = new Integer[4];
        Vec2 tempPos = pos.copy();
        if(discover || death || newPos.equals("idle"))return tempPos;

        int posX = 0, posY  = 0;
        switch (newPos){
            case "left":
               posX--;
                break;
            case "up":
                posY--;
                break;
            case "right":
                posX++;
                break;
            case "down":
                posY++;
                break;
        }

        pos.setX(pos.getX()+ posX);
        pos.setY(pos.getY()+ posY);
        if((pos.getX() < 0 || pos.getX() >= currentMap[1][0].length))return tempPos;
        if(pos.getY() < 0 || pos.getY() >= currentMap[1].length)return tempPos;

        if(tileset[currentMap[0][(int)pos.getY()][(int)pos.getX()]].getType() == Tile.HOLE){
            if(tileset[currentMap[1][(int)pos.getY()+posY][(int)pos.getX()+posX]].getType() != Tile.SOLID &&
                    tileset[currentMap[0][(int)pos.getY()+posY][(int)pos.getX()+posX]].getType() == Tile.EMPTY){
                add[0] = 0; add[1] = (int)pos.getY(); add[2] = (int)pos.getX(); add[3] = 20;
                blockToAdd.add(add);
                addBlock((int)tempPos.getX(), (int)tempPos.getY());
                pos.setX(pos.getX()+posX);
                pos.setY(pos.getY()+posY);
                nextTurn();
                return pos;
            } else {
                return tempPos;
            }
        }

        if(tileset[currentMap[1][(int)pos.getY()][(int)pos.getX()]].getType() != Tile.SOLID){
            addBlock((int)tempPos.getX(), (int)tempPos.getY());
            nextTurn();
            return pos;
        }

        return tempPos;
    }

    private void nextTurn(){
        transition = true;
        GameScreen.entityManager.getPlayer().start();
        turnCounter++;
        for(int i = 0; i < currentMap[0].length; i++){
            for(int j = 0; j < currentMap[0][i].length; j++){
                if(currentMap[0][i][j] == 17){
                    currentMap[0][i][j] = 18;
                } else if(currentMap[0][i][j] == 18){
                    currentMap[0][i][j] = 17;
                }
            }
        }
    }

    private void addBlock(int posX, int posY){
        if(tileset[currentMap[0][posY][posX]].getBlock()){
            Integer[] add = new Integer[4];
            add[0] = 0; add[1] = posY; add[2] = posX; add[3] = 0;
            blockToAdd.add(add);
            add = new Integer[4];
            add[0] = 1; add[1] = posY; add[2] = posX; add[3] = 4;
            blockToAdd.add(add);
            add = new Integer[4];
            add[0] = 2; add[1] = posY-1; add[2] = posX; add[3] = 5;
            blockToAdd.add(add);
            path[blockPosed] = new Vec2(posX,posY);
            blockPosed++;
        }
    }

    public int checkType(Vec2 pos){ return tileset[currentMap[0][(int)pos.getY()][(int)pos.getX()]].getType(); }

    public void death(){
        death = true;
        if(dcounter > DEATHTIME){
            GameScreen.entityManager.getPlayer().died();
        }
        dcounter++;
    }

    public void finish() {
        if(Config.getSpew()){
            finishbouuuuu();
            return;
        }

        tHeight = currentMap[0].length*GameScreen.tile;
        tWith = currentMap[0][0].length*GameScreen.tile;
        if (tHeight > Window.height || tWith > Window.width) {
            GameScreen.tile--;
        } else {
            if (count < blockToPose) {
                if (counter > time) {
                    currentMap[1][(int) path[count].getY()][(int) path[count].getX()] = 2;
                    currentMap[2][(int) path[count].getY() - 1][(int) path[count].getX()] = 3;
                    count++;
                    counter = 0;
                }
                counter++;
            }
        }
        GameManager.CAMERA.setPosition(Window.width / 2 - tWith/2,Window.height / 2 - tHeight/2,true);
    }

    private void finishbouuuuu(){
        if (count < blockToPose) {
            GameManager.CAMERA.setPosition( (int)(Window.width / 2 - path[count].getX()*GameScreen.tile),
                        ((int)(Window.height / 2 - path[count].getY()*GameScreen.tile)),true);
            if (counter > time) {
                currentMap[1][(int) path[count].getY()][(int) path[count].getX()] = 2;
                currentMap[2][(int) path[count].getY() - 1][(int) path[count].getX()] = 3;
                count++;
                counter = 0;
            }
            counter++;
        }
    }

    public boolean getTransition(){return transition;}

    public boolean getBegin(){return discover;}

    public boolean getDeath(){return death;}

    public boolean getWin(){return win;}

    public void setPlay(){
        if(GameScreen.tile < GameScreen.MIN_GAMETILESIZE) GameScreen.tile = GameScreen.MIN_GAMETILESIZE;
        discover = false;
    }

    public void unload(){
        for(Tile tile : tileset){
            tile.unload();
        }
        start.unload();
    }
}
