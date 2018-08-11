package box_overflow.game;

import box_overflow.screen.render.texture.TextureRenderer;
import box_overflow.screen.screens.GameScreen;
import box_overflow.util.XmlReader;
import box_overflow.util.math.Vec2;

public class LevelManager {
    private int currentLevel = 4;
    private int[][][] currentMap;
    private Tile tileset[];
    private int posX, posY;
    private int endX, endY;
    private int blockPosed;
    private int blockToPose;

    public LevelManager(){
        tileset = new Tile[16];
        tileset[0] = new Tile();
        tileset[1] = new Tile("/textures/tileset/ground.png",0);
        tileset[2] = new Tile("/textures/tileset/box1.png",1);
        tileset[3] = new Tile("/textures/tileset/box2.png",1);
        tileset[4] = new Tile("/textures/tileset/boxp1.png",1);
        tileset[5] = new Tile("/textures/tileset/boxp2.png",1);
        tileset[6] = new Tile("/textures/tileset/arrow-left.png",0);
        tileset[7] = new Tile("/textures/tileset/arrow-up.png",0);
        tileset[8] = new Tile("/textures/tileset/arrow-right.png",0);
        tileset[9] = new Tile("/textures/tileset/arrow-down.png",0);
        tileset[10] = new Tile("/textures/tileset/end-left.png",0);
        tileset[11] = new Tile("/textures/tileset/end-up.png",0);
        tileset[12] = new Tile("/textures/tileset/end-right.png",0);
        tileset[13] = new Tile("/textures/tileset/end-down.png",0);
        tileset[14] = new Tile("/textures/tileset/pillar.png",1);
        tileset[15] = new Tile("/textures/tileset/tetepillar.png",1);
    }

    public void load(){
        currentMap = XmlReader.loadMap(currentLevel);
        chargeConfig();
        GameScreen.entityManager.setPosition(posX,posY);
    }

    public void update(){
        Vec2 pos = GameScreen.entityManager.getPlayer().getPos();
        if(endX == pos.getX()){
            if(endY == pos.getY()){
                if(blockPosed >= blockToPose) {
                    GameScreen.setState(GameScreen.STATE_WIN);
                }
            }
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
        for(int i = 0; i < currentMap[0].length; i++){
            for(int j = 0; j < currentMap[0][i].length; j++){
                if(currentMap[0][i][j] == 6 || currentMap[0][i][j] == 7 || currentMap[0][i][j] == 8 || currentMap[0][i][j] == 9){
                    posX = j;
                    posY = i;
                } else if(currentMap[0][i][j] == 10 || currentMap[0][i][j] == 11 || currentMap[0][i][j] == 12 || currentMap[0][i][j] == 13){
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

    public void reset(){
        currentMap = XmlReader.loadMap(currentLevel);
        GameScreen.entityManager.setPosition(posX, posY);
    }

    public boolean isSolid(int posX, int posY){
        if(posX < 0 || posX > currentMap[1][0].length-1)return true;

        if(posY < 0 || posY > currentMap[1].length-1)return true;

        return tileset[currentMap[1][posY][posX]].getType() == 1;
    }

    public void addBlock(int posX, int posY){
        if(currentMap[0][posY][posX] == 1){
            currentMap[0][posY][posX] = 0;
            currentMap[1][posY][posX] = 4;
            currentMap[2][posY-1][posX] = 5;
            blockPosed++;
        }
    }

    public void unload(){
        for(Tile tile : tileset){
            tile.unload();
        }
    }
}
