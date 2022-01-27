package demolition;

import processing.core.PApplet;
import processing.core.PImage;

import java.util.ArrayList;
import java.util.Objects;

public class Bomb{

    private int x, y;
    private int counter = 0;
    public int animationCounter = 0;
    private PImage sprite = App.getApp().loadImage("src/main/resources/bomb/bomb.png");
    private boolean playerInImpact = false;

    // list of sprites
    private static ArrayList<PImage> bombAnimation = new ArrayList<PImage>();

    /**
     * Constructor for bomb class
     * @param x x coordinate of bomb 
     * @param y y coordinate of bomb
     */
    public Bomb(int x, int y){
        this.x = x;
        this.y = y;
    }

    /**
     * Handles image for bomb object, gets called once every frame
     */
    public void draw(){
        this.counter++;
        App.getApp().image(this.sprite, this.x, this.y);
        if (animationCounter != 8){
            this.updateAnimation();
        }
    }

    /**
     * Loads all images necessary for bomb object
     */         
    public static void loadAllImages(){
        for (int i = 1; i < 9; i++){
            bombAnimation.add(App.getApp().loadImage("src/main/resources/bomb/bomb"+i+".png"));
        }
    }

    /**
     * Updates bomb sprite every frame 
     */
    public void updateAnimation(){
        if (counter % 15 == 0){
            this.sprite = bombAnimation.get(animationCounter);
            animationCounter++;
        }
    }

    /**
     * expload method for bomb 
     * @return the coordinates of the impacted tiles in an arraylist of [x,y] coordinates  
     */
    public ArrayList<int[]> expload(){
        int i = (y - 64)/32;
        int j = x/32;
        boolean brokeBlock = false;
        this.playerInImpact = false;
        ArrayList<int[]> impactedArea = new ArrayList<int[]>();
        
        // middle
        impactedArea.add(new int[]{i, j});
        processExpload(i,j,'c');

        // down
        if (App.getMap().nextBlock(i, j, "down") != 'W'){
            if (App.getMap().nextBlock(i, j, "down") == 'B'){
                brokeBlock = true;
            }
            impactedArea.add(new int[]{i+1, j});
            processExpload(i+1, j, 'v');
            if (App.getMap().nextBlock(i+1, j, "down") != 'W' && !brokeBlock){
                impactedArea.add(new int[]{i+2, j});
                processExpload(i+2, j, 'd');
            }
            brokeBlock = false;
        }

        // up
        if (App.getMap().nextBlock(i, j, "up") != 'W'){
            if (App.getMap().nextBlock(i, j, "up") == 'B'){
                brokeBlock = true;
            }
            impactedArea.add(new int[]{i-1, j});
            processExpload(i-1,j,'v');
            if (App.getMap().nextBlock(i-1, j, "up") != 'W' && !brokeBlock){
                impactedArea.add(new int[]{i-2, j});
                processExpload(i-2,j,'u');
            }
            brokeBlock = false;
        }

        // left
        if (App.getMap().nextBlock(i, j, "left") != 'W'){
            if (App.getMap().nextBlock(i, j, "left") == 'B'){
                brokeBlock = true;
            }
            impactedArea.add(new int[]{i,j-1});
            processExpload(i,j-1,'h');
            if (App.getMap().nextBlock(i, j-1, "left") != 'W' && !brokeBlock){
                impactedArea.add(new int[]{i,j-2});
                processExpload(i,j-2,'l');
            }
            brokeBlock = false;
        }        

        // right
        if (App.getMap().nextBlock(i, j, "right") != 'W'){
            if (App.getMap().nextBlock(i, j, "right") == 'B'){
                brokeBlock = true;
            }
            impactedArea.add(new int[]{i,j+1});
            processExpload(i,j+1,'h');
            if (App.getMap().nextBlock(i, j+1, "right") != 'W' && !brokeBlock){
                impactedArea.add(new int[]{i,j+2});
                processExpload(i,j+2,'r');
            }   
            brokeBlock = false;
        }    

        // check if player was in impact range
        if (this.playerInImpact){
            App.restartLevel();
        }  

        return impactedArea;
    }

    /**
     * process the expload abd make changes to tiles and objects effected 
     * @param i the vertical coordinate of explosion in tile grid 
     * @param j the horizontal coordainte of explosion in tile grid
     * @param type the type of explosion that will be on the tile
     */
    public void processExpload(int i, int j, char type){
        boolean playerInImpact = false;
        if (App.getMap().getTileAt(i,j).getType() == 'P'){
            this.playerInImpact = true;
        }
        App.getMap().getTileAt(i,j).changeType(type);
        App.getMap().getTileAt(i,j).counter = 0;
        App.getMap().getTileAt(i,j).draw();
        App.getMap().getTileAt(i,j).destroy();
    }

}