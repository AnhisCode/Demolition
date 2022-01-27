package demolition;

import processing.core.PApplet;
import processing.core.PImage;

import java.util.ArrayList;
import java.util.Objects;

public class Enemy{

    protected String type;
    protected int x, y;
    protected boolean killed = false;

    protected PImage sprite;
    protected String lastDirection = "down";
    protected static ArrayList<PImage> redUp = new ArrayList<PImage>();
    protected static ArrayList<PImage> redRight = new ArrayList<PImage>();
    protected static ArrayList<PImage> redLeft = new ArrayList<PImage>();
    protected static ArrayList<PImage> redDown = new ArrayList<PImage>();

    protected static ArrayList<PImage> yellowUp = new ArrayList<PImage>();
    protected static ArrayList<PImage> yellowRight = new ArrayList<PImage>();
    protected static ArrayList<PImage> yellowLeft = new ArrayList<PImage>();
    protected static ArrayList<PImage> yellowDown = new ArrayList<PImage>();

    // counters/time for game
    protected int counter = 0;
    protected int animationCounter = 0;

    /**
     * Constructor for enemy class
     * @param x initial starting x coordinate
     * @param y initial starting y coordinate 
     */
    public Enemy(int x, int y){
        this.x = x;
        this.y = y+40;
    }

    /**
     * Loads all necessary images for enemy classes 
     */
    public static void loadAllImages(){
        for (int i = 1; i < 5; i++){
            redUp.add(App.getApp().loadImage("src/main/resources/red_enemy/red_up"+i+".png"));
            redLeft.add(App.getApp().loadImage("src/main/resources/red_enemy/red_left"+i+".png"));
            redRight.add(App.getApp().loadImage("src/main/resources/red_enemy/red_right"+i+".png"));
            redDown.add(App.getApp().loadImage("src/main/resources/red_enemy/red_down"+i+".png"));
        }
        for (int i = 1; i < 5; i++){
            yellowUp.add(App.getApp().loadImage("src/main/resources/yellow_enemy/yellow_up"+i+".png"));
            yellowLeft.add(App.getApp().loadImage("src/main/resources/yellow_enemy/yellow_left"+i+".png"));
            yellowRight.add(App.getApp().loadImage("src/main/resources/yellow_enemy/yellow_right"+i+".png"));
            yellowDown.add(App.getApp().loadImage("src/main/resources/yellow_enemy/yellow_down"+i+".png"));
        }
    }

    /**
     * handles logic, calls once every frame 
     */
    public void tick(){
        counter++;
        App.getApp().image(this.sprite, this.x, this.y);
    }


    /**
     * Getter method for the coordinate of the enemy 
     * @return the [x,y] coordinate of player 
     */
    public int[] getCoordinate(){
        int[] coordinates = new int[2];
        coordinates[0] = x;
        coordinates[1] = y;
        return coordinates;
    }


    /**
     * check if enemy is currently dead
     * @return true if dead, false otherwise
     */
    public boolean isDead(){
        return killed;
    }


    /**
     * check if this enemy object is in colission with an explosion 
     */
    public void checkBombCollision(){
        for(ArrayList<int[]> list : Player.impactedAreas){
            for (int[] coordinateOfExplosion : list){
                if (this.y - 40 == coordinateOfExplosion[0]*32 && this.x  == coordinateOfExplosion[1]*32){
                    this.killed = true;
                }
            }
        }
    }


}