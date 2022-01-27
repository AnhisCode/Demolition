/**
 * The main class for App
 * @author Anh Dao
 */

package demolition;

import java.util.*;

import processing.core.PApplet;
import processing.core.PImage;

public class App extends PApplet {

    public static final int WIDTH = 480;
    public static final int HEIGHT = 480;

    public static final int FPS = 60;

    public static boolean keyCheck = true;

    private static PApplet app;

    public static Player player;
    public static Map map;
    public static Hud hud;
    public static ReadJson jsonReader;

    public static String defaultConfig = "config.json";
    public static String currentLevel;
    public static int currentLevelIndex = 0;

    /**
     * Construct the app object
     */
    public App() {
        // construct objects here
        this.app = this;
    }

    /**
     * Getter method for app object
     * @return app object
     */
    public static PApplet getApp(){
        return app;
    }

    /**
     * Getter method for map object
     * @return map object
     */
    public static Map getMap(){
        return map;
    }

    /**
     * Getter method for player object
     * @return player object
     */
    public static Player getPlayer(){
        return player;
    }

    /**
     * App settings
     */
    public void settings() {
        size(WIDTH, HEIGHT);
    }

    /**
     * Sets up and creates the necessary objects in the order of:
     * JSONreader - Map - HUD - Player
     */
    public void setup() {
        frameRate(FPS);
        Player.loadAllImages();
        Bomb.loadAllImages();
        Enemy.loadAllImages();
        jsonReader = new ReadJson(defaultConfig);
        jsonReader.readJsonFile();
        currentLevel = ReadJson.gameLevels.get(currentLevelIndex);
        map = new Map(Map.readMapConfig(currentLevel));
        hud = new Hud(ReadJson.gameTimes.get(currentLevelIndex), jsonReader.getPlayerLives());
        hud.setUp();
        map.generateMap();
        int[] playerCord = map.findPlayer();
        player = new Player(playerCord[0], playerCord[1]+40, loadImage("src/main/resources/player/player_left3.png"), jsonReader.getPlayerLives());
    }

    /**
     * Restarting the level after the player has taken damage or has died
     */
    public static void restartLevel() {
        player.bombRemove = false;
        Player.bombPlaced = new ArrayList<Bomb>();
        player.setLife(-1);
        player.lastDirection = "down";
        map = new Map(Map.readMapConfig(currentLevel));
        map.generateMap();
        int[] playerCord = map.findPlayer();
        player.x = playerCord[0];
        player.y = playerCord[1] + 40;
    }

    /**
     * Sends the player to the next level after the goal is reached
     */
    public static void nextLevel() {
        player.bombRemove = false;
        Player.bombPlaced = new ArrayList<Bomb>();
        player.lastDirection = "down";
        currentLevelIndex++;
        // if there are still next level
        if (currentLevelIndex < ReadJson.gameLevels.size()){
            currentLevel = ReadJson.gameLevels.get(currentLevelIndex);
            hud.updateTime(ReadJson.gameTimes.get(currentLevelIndex));
            map = new Map(Map.readMapConfig(currentLevel));
            map.generateMap();
            int[] playerCord = map.findPlayer();
            player.x = playerCord[0];
            player.y = playerCord[1] + 40;
        } else {
            Hud.gameWin();
        }

    }

    /**
     * Occurs every frame
     */
    public void draw() {
        background(255, 165, 0);
        fill(0, 125, 0);
        rect(-10, 64, 500,500);
        // main loop here
        player.tick();
        map.drawMap();
        map.tick();
        player.draw(this);
        hud.draw();
    }

    /**
     * Check the user's keyboard input in real time
     */
    public void keyPressed(){
        if (keyCode == 38 && keyCheck){
            player.move("up");
        }
        if (keyCode == 37 && keyCheck){
            player.move("left");
        }
        if (keyCode == 39 && keyCheck){
            player.move("right");
        }
        if (keyCode == 40 && keyCheck){
            player.move("down");
        }
        if (keyCode == 32 && keyCheck){
            int[] playerCord = map.findPlayer();
            player.placeBomb(playerCord[0], playerCord[1]);
        }
        keyCheck = false;
    }

    /**
     * Make sure the player has released the button before using it again
     */
    public void keyReleased(){
        keyCheck = true;
    }

    /**
     * The main method
     * @param args
     */
    public static void main(String[] args) {
        PApplet.main("demolition.App");
    }

}
