package demolition;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import processing.core.PApplet;
import processing.core.PImage;

import java.util.ArrayList;
import java.util.Objects;

public class Map{

    public static Map currentMap;

    // 13 rows tall, and 15 columns wide map
    public char[][] mapSpecification = new char[15][13];
    public Tile[][] map = new Tile[15][13];
    public Tile[][] tempMap = new Tile[15][13];
    public boolean redEnemyExist = false;
    public boolean yellowEnemyExist = false;
    public ArrayList<RedEnemy> redEnemy = new ArrayList<RedEnemy>();
    public ArrayList<YellowEnemy> yellowEnemy = new ArrayList<YellowEnemy>();

    /**
     * Constructor for Map class
     * @param mapSpecification A multidimentional character array
     */
    public Map(char[][] mapSpecification){
        this.mapSpecification = mapSpecification;
        currentMap = this;
    }


    // reads the txt file and put it into a multi dimentional array of characters
    /**
     * reads the txt file and return a multi dimentional array of characters
     * @param fileLocation the location of the map config file
     * @return the multidimentional array of characters 
     */
    public static char[][] readMapConfig(String fileLocation){
        char[][] readMap = new char[15][13];

        // read txt file
        try {
            File levelLayout = new File(fileLocation);
            Scanner myReader = new Scanner(levelLayout);
            int i = 0;
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();

                // Copy character by character into array
                for (int j = 0; j < data.length(); j++) {
                    readMap[j][i] = data.charAt(j);
                }
                i++;
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return readMap;
    }

    /**
     * Generates all the tile classes based on the current map specification
     */
    public void generateMap(){
        for(int i = 0 ; i < mapSpecification.length; i++){
            for (int j = 0; j < mapSpecification[i].length; j++){
                this.map[i][j] = new Tile(i * 32, j * 32, 32,32, mapSpecification[i][j]);
            }
        }
        // strip the player location from tempMap
        for(int i = 0 ; i < tempMap.length; i++){
            for (int j = 0; j < tempMap[i].length; j++){
                this.tempMap[i][j] = new Tile(i * 32, j * 32, 32,32, mapSpecification[i][j]);
                if (this.map[i][j].getType() == 'P'){
                    this.tempMap[i][j].changeType(' ');
                }
                if (this.map[i][j].getType() == 'R'){
                    redEnemy.add(new RedEnemy(i*32,j*32));
                    redEnemyExist = true;
                }
                if (this.map[i][j].getType() == 'Y'){
                    yellowEnemy.add(new YellowEnemy(i*32,j*32));
                    yellowEnemyExist = true;
                }
            }
        }
    }

    /**
     * checks if the direction UP, DOWN, LEFT, RIGHT, contiains a non-passable object based on the given coordinate
     * @param i Vertical gridmap coordinate
     * @param j Horizontal gridmap coordinate 
     * @param direction The direction the check 
     * @return if the next tile is immovable   
     */
    public boolean validMove(int i, int j, String direction){
        i = i/32;
        j = (j-32)/32;

        if (direction == "up"){
            if (this.map[i][j-1].getType() == 'B' || this.map[i][j-1].getType() == 'W'){
                return false;
            } else {
                return true;
            }
        }
        if (direction == "left"){
            if (this.map[i-1][j].getType() == 'B' || this.map[i-1][j].getType() == 'W'){
                return false;
            } else {
                return true;
            }
        }
        if (direction == "right"){
            if (this.map[i+1][j].getType() == 'B' || this.map[i+1][j].getType() == 'W'){
                return false;
            } else {
                return true;
            }
        }
        if (direction == "down"){
            if (this.map[i][j+1].getType() == 'B' || this.map[i][j+1].getType() == 'W'){
                return false;
            } else {
                return true;
            }
        }
        else{
            return false;
        }
    }


    /**
     * Returns the character type of the block in direction UP, DOWN, LEFT, RIGHT from the given coordinate 
     * @param i Vertical gridmap coordinate
     * @param j Horizontal gridmap coordinate 
     * @param direction The direction the check 
     * @return char type of the object
     */
    public char nextBlock(int j, int i, String direction){

        if (direction == "up"){
            return this.map[i][j-1].getType();
        }
        else if (direction == "left"){
            return this.map[i-1][j].getType();
        }
        else if (direction == "right"){
            return this.map[i+1][j].getType();
        }
        else if (direction == "down"){
            return this.map[i][j+1].getType();
        }
        else{
            return 'e';
        }
    }
    

    /**
     * Change the coordinate of the player given a position 
     * @param direction The direction the player intends to move
     */
    public void changePlayerPosition(String direction){
        int[] playerCoordinates = new int[2];
        playerCoordinates = findPlayer();
        int i = playerCoordinates[0]/32;
        int j = playerCoordinates[1]/32;


        if (direction == "up"){
            this.map[i][j-1].changeType('P');
        }
        if (direction == "left"){
            this.map[i-1][j].changeType('P');
        }
        if (direction == "right"){
            this.map[i+1][j].changeType('P');
        }
        if (direction == "down"){
            this.map[i][j+1].changeType('P');
        }
        if (this.map[i][j].isDestroyed()){
            this.map[i][j].changeType(' ');
        } else {
            this.map[i][j].changeType(tempMap[i][j].getType());
        }
        
    }
    
    /**
     * Finds the coordinate of the player and returns the coordinate in [x,y]
     * @return the x,y coordinate in an array
     */
    public int[] findPlayer(){
        int[] coordinates = new int[2];
        for(int i = 0 ; i < this.map.length; i++){
            for (int j = 0; j < this.map[i].length; j++){
                if (this.map[i][j].getType() == 'P'){
                    coordinates[0] = i*32;
                    coordinates[1] = j*32;
                    return coordinates;
                }
            }
        }
        return coordinates;
    }


    /**
     * returns the object at given gridmap coordinate 
     * @param xCord Horizontal gridmap coordinate
     * @param yCord Vertical Gridmap Coordinate
     * @return Tile type at given coordinate
     */
    public Tile getTileAt(int xCord, int yCord){
        return map[yCord][xCord];
    }


    /**
     * manages enemy generation
     */
    public void manageEnemy(){
        boolean redRemove = false;
        int redIndex = 0;
        if (redEnemyExist){
            for(RedEnemy red : redEnemy){
                red.tick();
                red.draw();
                if (red.isDead()){
                    redIndex = redEnemy.indexOf(red);
                    redRemove = true;
                }
            }
            if (redRemove){
                this.redEnemy.remove(redIndex);
            }
        }

        if (yellowEnemyExist){
            boolean yellowRemove = false;
            int yellowIndex = 0;
            for(YellowEnemy yellow : yellowEnemy){
                yellow.tick();
                yellow.draw();
                // if enemy is dead remove them from the list
                if (yellow.isDead()){
                    yellowIndex = yellowEnemy.indexOf(yellow);
                    yellowRemove = true;
                }
            }
            if (yellowRemove){
                this.yellowEnemy.remove(yellowIndex);
            }
        }
    }


    /**
     * Draw the map and update every frame
     */
    public void drawMap(){
        for(int i = 0 ; i < mapSpecification.length; i++){
            for (int j = 0; j < mapSpecification[i].length; j++){
                this.map[i][j].draw();
                this.map[i][j].counter++;
            }
        }
        currentMap = this;
    }

    /**
     * calls the manageEnemy method
     */
    public void tick(){
        manageEnemy();
    }    
}