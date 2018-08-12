package box_overflow.game;

import box_overflow.main.Config;
import box_overflow.main.Window;
import box_overflow.screen.GameManager;
import box_overflow.screen.render.texture.TextureRenderer;
import box_overflow.screen.screens.GameScreen;
import box_overflow.util.XmlReader;
import box_overflow.util.math.Vec2;

import java.lang.reflect.Executable;

public class LevelManager {
    private int currentLevel = 2;
    private int[][][] currentMap;
    private Tile tileset[];
    private int posX, posY;
    private int endX, endY;
    private int blockPosed;
    private int blockToPose;

    private Vec2 path[];

    private int tWith, tHeight;
    private int counter, count;
    private int time;

    public LevelManager(){
        tileset = new Tile[17];
        tileset[0] = new Tile();
        tileset[1] = new Tile("/textures/tileset/ground.png",0);
        tileset[2] = new Tile("/textures/tileset/box1.png",1);
        tileset[3] = new Tile("/textures/tileset/box2.png",1);
        tileset[4] = new Tile("/textures/tileset/boxp1.png",1);
        tileset[5] = new Tile("/textures/tileset/boxp2.png",1);
        tileset[6] = new Tile("/textures/tileset/arrow-left.png",2);
        tileset[7] = new Tile("/textures/tileset/arrow-up.png",2);
        tileset[8] = new Tile("/textures/tileset/arrow-right.png",2);
        tileset[9] = new Tile("/textures/tileset/arrow-down.png",2);
        tileset[10] = new Tile("/textures/tileset/end-left.png",3);
        tileset[11] = new Tile("/textures/tileset/end-up.png",3);
        tileset[12] = new Tile("/textures/tileset/end-right.png",3);
        tileset[13] = new Tile("/textures/tileset/end-down.png",3);
        tileset[14] = new Tile("/textures/tileset/pillar.png",1);
        tileset[15] = new Tile("/textures/tileset/pillarh.png",1);
        tileset[16] = new Tile("/textures/tileset/door.png",1);
    }

    public void load(){
        count = 0;
        counter = 0;
        GameScreen.tile = GameScreen.GAMETILESIZE;
        currentLevel = Config.getCurrentMap();
        currentMap = XmlReader.loadMap(currentLevel);
        chargeConfig();
        GameScreen.entityManager.setPosition(posX,posY);
        path = new Vec2[blockToPose];
        time = 60*5/blockToPose;
    }

    public void update(){
        try {
            Vec2 pos = GameScreen.entityManager.getPlayer().getPos();
            if(endX == pos.getX()){
                if(endY == pos.getY()){
                    if(blockPosed >= blockToPose) {
                        Config.setMapConcluded(currentLevel,2);
                        if(Config.getMapConcluded(currentLevel+1) == 0){
                            Config.setMapConcluded(currentLevel+1,1);
                            Config.setLastMap(currentLevel+1);
                        }
                        GameScreen.setState(GameScreen.STATE_WIN);
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
    }

    private void chargeConfig(){
        blockToPose = 0;
        blockPosed = 0;
        for(int i = 0; i < currentMap[0].length; i++){
            for(int j = 0; j < currentMap[0][i].length; j++){
                if(tileset[currentMap[0][i][j]].getType() == Tile.BEGIN){
                    posX = j;
                    posY = i;
                } else if(tileset[currentMap[0][i][j]].getType() == Tile.END){
                    endX = j;
                    endY = i;
                }
            }
        }

        for(int i = 0; i < currentMap[0].length; i++){
            for(int j = 0; j < currentMap[0][i].length; j++){
                if(currentMap[0][i][j] == 1) blockToPose++;
            }
        }
    }

    public boolean isSolid(int posX, int posY){
        if(posX < 0 || posX > currentMap[1][0].length-1)return true;

        if(posY < 0 || posY > currentMap[1].length-1)return true;

        return tileset[currentMap[1][posY][posX]].getType() == 1;
    }

    public void addBlock(int posX, int posY){
        if(currentMap[0][posY][posX] == 1 ){
            currentMap[0][posY][posX] = 0;
            currentMap[1][posY][posX] = 4;
            currentMap[2][posY-1][posX] = 5;
            path[blockPosed] = new Vec2(posX,posY);
            blockPosed++;
        }
    }

    public void unload(){
        for(Tile tile : tileset){
            tile.unload();
        }
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
}
