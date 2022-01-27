package demolition;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PFont;

public class Hud{

    private int counter = 0;

    public int time, playerLife;
    private PFont font;
    public static boolean gameOver = false;
    public static boolean winGame = false;

    private PImage clock = App.getApp().loadImage("src/main/resources/icons/clock.png");
    private PImage playerIcon = App.getApp().loadImage("src/main/resources/icons/player.png");


    /**
     * constructor class for hud 
     * @param time maximum time for current level 
     * @param playerLife the starting player lives 
     */
    public Hud(int time, int playerLife){
        this.time = time;
        this.playerLife = playerLife;
    }


    /**
     * set up the fonts for hud
     */
    public void setUp(){
        font = App.getApp().createFont("src/main/resources/PressStart2P-Regular.ttf", 18);
        App.getApp().textFont(font); 
    }

    /**
     * draw hud on the screen and check if either the time or player lives
     * have ran out. If it has, restart level.
     */
    public void draw(){
        counter++;
        if (counter % 60 == 0){
            time--;
        }
        App.getApp().fill(0,0,0);
        App.getApp().text(Integer.toString(time), 300, 40);
        App.getApp().text(Integer.toString(App.getPlayer().getLife()), 150, 40);
        App.getApp().image(clock, 265, 12);
        App.getApp().image(playerIcon, 110, 12);
        if (App.getPlayer().getLife() == -1 || time == -1){
            loseGame();
        }
        if (gameOver){
            App.getApp().fill(255, 165, 0);
            App.getApp().rect(-10, -10, 500,500);
            App.getApp().fill(0,0,0);
            // -81 for 4.5 character length
            App.getApp().text("GAME OVER", App.WIDTH/2 - 81, App.HEIGHT/2);
        }
        if (winGame){
            App.getApp().fill(255, 165, 0);
            App.getApp().rect(-10, -10, 500,500);
            App.getApp().fill(0,0 ,0);
            // -63 for 3.5 character length
            App.getApp().text("YOU WIN", App.WIDTH/2 - 63, App.HEIGHT/2);
        }
    }

    /**
     * changes the game state to game over
     */
    public static void loseGame(){
        gameOver = true;
    }

    /**
     * Changes the game state to win screen
     */
    public static void gameWin(){
        winGame = true;
    }

    /**
     * updates time
     * @param time new time 
     */
    public void updateTime(int time){
        this.time = time;
    }

}