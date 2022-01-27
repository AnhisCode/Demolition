package demolition;

import processing.core.PApplet;
import processing.core.PImage;

public class Tile{

    public int xCord, yCord, width, height;
    public char type;
    public PImage texture;
    public boolean destroyed = false;

    public int counter = 0;


    // load all wall images
    private PImage brokenWall = App.getApp().loadImage("src/main/resources/broken/broken.png");
    private PImage wall = App.getApp().loadImage("src/main/resources/wall/solid.png");
    private PImage goal = App.getApp().loadImage("src/main/resources/goal/goal.png");


    // load all explosion images 
    private PImage centerExplosion = App.getApp().loadImage("src/main/resources/explosion/centre.png");
    private PImage endBottomExplosion = App.getApp().loadImage("src/main/resources/explosion/end_bottom.png");
    private PImage endLeftExplosion = App.getApp().loadImage("src/main/resources/explosion/end_left.png");
    private PImage endRightExplosion = App.getApp().loadImage("src/main/resources/explosion/end_right.png");
    private PImage endTopExplosion = App.getApp().loadImage("src/main/resources/explosion/end_top.png");
    private PImage verticalExplosion = App.getApp().loadImage("src/main/resources/explosion/vertical.png");
    private PImage horizontalExplosion = App.getApp().loadImage("src/main/resources/explosion/horizontal.png");
    
    
    // each tile is 32 x 32 pixel

    /**
     * Constructor for Tile object
     * @param x x coordinate
     * @param y y coordinate
     * @param width the width of the tile
     * @param height the height of the tile 
     * @param type The type of the tile (Wall, Broken, Empty, Goal)
     */
    public Tile(int x, int y, int width, int height, char type){
        this.xCord = x;
        this.yCord = y + 64;
        this.width = width;
        this.height = height;
        this.type = type;
    }

    /** 
     * updates the tile sprite every frame
     */
    public void draw(){
        if (this.type == 'B'){
            App.getApp().image(brokenWall, this.xCord, this.yCord);
        }
        if (this.type == 'W'){
            App.getApp().image(wall, this.xCord, this.yCord);
        }
        if (this.type == 'G'){
            App.getApp().image(goal, this.xCord, this.yCord);
        }
        if (this.type == 'c'){
            App.getApp().image(centerExplosion, this.xCord, this.yCord);
            if (counter == 30){
                this.type = ' ';
            }
        }
        // explosion
        {
            if (this.type == 'v'){
                App.getApp().image(verticalExplosion, this.xCord, this.yCord);
                if (counter == 30){
                    this.type = ' ';
                }
            }
            if (this.type == 'h'){
                App.getApp().image(horizontalExplosion, this.xCord, this.yCord);
                if (counter == 30){
                    this.type = ' ';
                }
            }
            if (this.type == 'u'){
                App.getApp().image(endTopExplosion, this.xCord, this.yCord);
                if (counter == 30){
                    this.type = ' ';
                }
            }
            if (this.type == 'l'){
                App.getApp().image(endLeftExplosion, this.xCord, this.yCord);
                if (counter == 30){
                    this.type = ' ';
                }
            }
            if (this.type == 'r'){
                App.getApp().image(endRightExplosion, this.xCord, this.yCord);
                if (counter == 30){
                    this.type = ' ';
                }
            }
            if (this.type == 'd'){
                App.getApp().image(endBottomExplosion, this.xCord, this.yCord);
                if (counter == 30){
                    this.type = ' ';
                }
            }
        }
    }

    /**
     * destroy the tile
     */
    public void destroy(){
        this.destroyed = true;
    }

    /**
     * check if the tile is destroyed 
     * @return is the tile destroyed
     */
    public boolean isDestroyed(){
        return destroyed;
    }

    
    /**
     * returns the type of the tile
     * @return the char type of the tile
     */
    public char getType(){
        return this.type;
    }

    /**
     * change the type of the tile
     * @param newType new tile type
     */
    public void changeType(char newType){
        this.type = newType;
    }

}