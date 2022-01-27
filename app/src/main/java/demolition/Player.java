package demolition;

import processing.core.PApplet;
import processing.core.PImage;

import java.util.ArrayList;
import java.util.Objects;

public class Player{

    // player speed and direction variable
    public int x;
    public int y;
    private int xCord;
    private int yCord;
    private String direction;

    // counters/time for game
    public static int counter = 0;
    public static int animationCounter = 0;

    // handles player animation sprite
    private PImage sprite;
    public String lastDirection = "down";
    public static ArrayList<PImage> movingUp = new ArrayList<PImage>();
    public static ArrayList<PImage> movingRight = new ArrayList<PImage>();
    public static ArrayList<PImage> movingLeft = new ArrayList<PImage>();
    public static ArrayList<PImage> movingDown = new ArrayList<PImage>();

    // handles player state
    public int life;
    public static ArrayList<Bomb> bombPlaced = new ArrayList<Bomb>();
    public static ArrayList<ArrayList<int[]>> impactedAreas = new ArrayList<ArrayList<int[]>>();
    public static ArrayList<Integer> impactedAreasCounter = new ArrayList<Integer>();
    public boolean bombRemove = false;

    /**
     * Player object constructor 
     * @param x the starting x coordinate for player character
     * @param y the starting y coordinate for player character
     * @param sprite the starting sprite for player character 
     * @param life the starting life for player character
     */
    public Player(int x, int y, PImage sprite, int life){
        // handling location of player
        this.x = x;
        // +8 to center the player
        this.y = y + 8;
        //players image
        this.sprite = sprite;

        //player info
        this.life = life;
        this.direction = "still";
    }


    
    /**
     * Handles logic, gets called once per frame 
     */
    public void tick(){
        // handles logic
        counter++;
        // removes bomb if there are any on the board
        if (bombPlaced.size() > 0){
            for (Bomb bomb : bombPlaced){
                bomb.draw();
                if (bomb.animationCounter == 8){
                    bombRemove = true;
                    // impactedAreas is an arraylist of arraylist of all tile coordinates that has an explosion
                    impactedAreas.add(bomb.expload());
                    impactedAreasCounter.add(0);
                }
            }
        }
        if (bombRemove){
            bombPlaced.remove(0);
            bombRemove = false;
        }

        if (impactedAreasCounter.size() > 0){
            for (int i = 0; i < impactedAreasCounter.size(); i++){
                impactedAreasCounter.set(i, impactedAreasCounter.get(i) + 1);
                if (impactedAreasCounter.get(i) == 30){
                    impactedAreasCounter.remove(i);
                    impactedAreas.remove(i);
                }
            }
        }

        checkEnemyCollision();
    }

    /**
     * Handles graphic, gets called once per frame 
     * @param app App object
     */
    public void draw(PApplet app){
        // handling graphics
        app.image(this.sprite, this.x, this.y);
        this.directionUpdate();
    }

    /**
     * Loads all necessary player image from resource
     */
    public static void loadAllImages(){
        for (int i = 1; i < 5; i++){
            movingUp.add(App.getApp().loadImage("src/main/resources/player/player_up"+i+".png"));
            movingLeft.add(App.getApp().loadImage("src/main/resources/player/player_left"+i+".png"));
            movingRight.add(App.getApp().loadImage("src/main/resources/player/player_right"+i+".png"));
            movingDown.add(App.getApp().loadImage("src/main/resources/player/player"+i+".png"));
        }
    }


    /**
     * Check if the player character is colliding with an enemy character
     */
    public void checkEnemyCollision(){
        int[] enemyCoordinates = new int[2];
        if (App.getMap().redEnemyExist){
            for (Enemy enemy : App.getMap().redEnemy){
                enemyCoordinates = enemy.getCoordinate();
                if (enemyCoordinates[0] == this.x && enemyCoordinates[1] == this.y){
                    App.restartLevel();
                }
            }
        }
        if (App.getMap().yellowEnemyExist){
            for (Enemy enemy : App.getMap().yellowEnemy){
                enemyCoordinates = enemy.getCoordinate();
                if (enemyCoordinates[0] == this.x && enemyCoordinates[1] == this.y){
                    App.restartLevel();
                }
            }
        }
    }


    /**
     * Updates the player sprite once every frame based on the last direction of 
     * the player character
     */
    public void directionUpdate(){
        if (Objects.equals(this.lastDirection, "up")){
            if (counter % 12 == 0){
                this.sprite = movingUp.get(animationCounter);
                animationCounter++;
                if (animationCounter > 3){
                    animationCounter = 0;
                }
            }
        }
        if (Objects.equals(this.lastDirection, "left")){
            if (counter % 12 == 0){
                this.sprite = movingLeft.get(animationCounter);
                animationCounter++;
                if (animationCounter > 3){
                    animationCounter = 0;
                }
            }
        }
        if (Objects.equals(this.lastDirection, "right")){
            if (counter % 12 == 0){
                this.sprite = movingRight.get(animationCounter);
                animationCounter++;
                if (animationCounter > 3){
                    animationCounter = 0;
                }
            }
        }
        if (Objects.equals(this.lastDirection, "down")){
            if (counter % 12 == 0){
                this.sprite = movingDown.get(animationCounter);
                animationCounter++;
                if (animationCounter > 3){
                    animationCounter = 0;
                }
            }
        }
    }

    
    /**
     * Handles player movement and checking if player is in collision with explosion or a goal.
     * @param direction the direction the player intends to move
     */
    public void move(String direction){
        if (Objects.equals(direction, "up")){
            this.lastDirection = direction;
            this.sprite = movingUp.get(animationCounter);
            if (App.getMap().validMove(x, y, direction)){
                char nextBlock = App.getMap().nextBlock((y-32)/32, x/32, direction);
                if (nextBlock == 'h' || nextBlock == 'v' ||nextBlock == 'd' || nextBlock == 'r' || nextBlock == 'u' || nextBlock == 'l'){
                    App.restartLevel();
                }
                else if (nextBlock == 'G'){
                    App.nextLevel();
                } else { 
                    this.y -= 32;
                    App.getMap().changePlayerPosition(direction);
                }
            }
        }
        if (Objects.equals(direction, "left")){
            this.lastDirection = direction;
            this.sprite = movingLeft.get(animationCounter);
            if (App.getMap().validMove(x, y, direction)){
                char nextBlock = App.getMap().nextBlock((y-32)/32, x/32, direction);
                if (nextBlock == 'h' || nextBlock == 'v' ||nextBlock == 'd' || nextBlock == 'r' || nextBlock == 'u' || nextBlock == 'l'){
                    App.restartLevel();
                }
                else if (nextBlock == 'G'){
                    App.nextLevel();
                } else {
                    this.x -= 32;
                    App.getMap().changePlayerPosition(direction);
                }
            }
        }  
        if (Objects.equals(direction, "right")){
            this.lastDirection = direction;
            this.sprite = movingRight.get(animationCounter);
            if (App.getMap().validMove(x, y, direction)){
                char nextBlock = App.getMap().nextBlock((y-32)/32, x/32, direction);
                if (nextBlock == 'h' || nextBlock == 'v' ||nextBlock == 'd' || nextBlock == 'r' || nextBlock == 'u' || nextBlock == 'l'){
                    App.restartLevel();
                }
                else if (nextBlock == 'G'){
                    App.nextLevel();
                } else {
                this.x += 32;
                App.getMap().changePlayerPosition(direction);
                }
            }
        }
        if (Objects.equals(direction, "down")){
            this.lastDirection = direction;
            this.sprite = movingDown.get(animationCounter);
            if (App.getMap().validMove(x, y, direction)){
                char nextBlock = App.getMap().nextBlock((y-32)/32, x/32, direction);
                if (nextBlock == 'h' || nextBlock == 'v' ||nextBlock == 'd' || nextBlock == 'r' || nextBlock == 'u' || nextBlock == 'l'){
                    App.restartLevel();
                }
                else if (nextBlock == 'G'){
                    App.nextLevel();
                } else {
                    this.y += 32;
                    App.getMap().changePlayerPosition(direction);
                }
            }
        }
    }

    /**
     * places bomb in the player location 
     * @param x player x coordinate
     * @param y player y coordinate
     */
    public void placeBomb(int x, int y){
        bombPlaced.add(new Bomb(x,y + 64));
    }


    /**
     * getter method for current player life
     * @return current player life 
     */
    public int getLife(){
        return this.life;
    }


    /**
     * updates the player life based on the parameter 
     * @param i change the value of current player life by i
     */
    public void setLife(int i){
        this.life += i;
    }

}
