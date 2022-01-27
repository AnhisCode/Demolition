package demolition;

import processing.core.PApplet;
import processing.core.PImage;

import java.util.Random;

import java.util.ArrayList;
import java.util.Objects;

public class RedEnemy extends Enemy{

    public String lastDirection = "right";

    /**
     * Constructor for RedEnemy object
     * @param x starting X coordinate 
     * @param y starting Y coordinate
     */
    public RedEnemy(int x, int y){
        super(x,y);
        this.sprite = Enemy.redDown.get(0);
    }

    /**
     * Calls on the directionUpdate and checkBombCollision methods every frame
     */
    public void draw(){
        if (counter % 60 == 0){
            move();
        }
        directionUpdate();
        checkBombCollision();
    }

    /**
     * updates the direction of the enemy
     */
    public void directionUpdate(){
        if (this.lastDirection == "up"){
            if (counter % 12 == 0){
                this.sprite = Enemy.redUp.get(animationCounter);
                animationCounter++;
                if (animationCounter > 3){
                    animationCounter = 0;
                }
            }
        }
        if (this.lastDirection == "left"){
            if (counter % 12 == 0){
                this.sprite = Enemy.redLeft.get(animationCounter);
                animationCounter++;
                if (animationCounter > 3){
                    animationCounter = 0;
                }
            }
        }
        if (this.lastDirection == "right"){
            if (counter % 12 == 0){
                this.sprite = Enemy.redRight.get(animationCounter);
                animationCounter++;
                if (animationCounter > 3){
                    animationCounter = 0;
                }
            }
        }
        if (this.lastDirection == "down"){
            if (counter % 12 == 0){
                this.sprite = Enemy.redDown.get(animationCounter);
                animationCounter++;
                if (animationCounter > 3){
                    animationCounter = 0;
                }
            }
        }
    }

    /**
     * returns a random direction of UP, DOWN, LEFT, RIGHT
     * @return the randomised direction
     */
    public static String randomDirection(){
        Random randomN = new Random();
        int number = randomN.nextInt(4);
        if (number == 0){
            return "up";
        }
        if (number == 1){
            return "left";
        }
        if (number == 2){
            return "right";
        }
        if (number == 3){
            return "down";
        } else {
            return null;
        }

    }


    /**
     * Manages the automatic moement of the enemy 
     */
    public void move(){
        if(App.getMap().validMove(this.x, this.y, this.lastDirection)){
            if (lastDirection == "up"){
                this.y -= 32;
            }
            if (lastDirection == "left"){
                this.x -= 32;
            }
            if (lastDirection == "right"){
                this.x += 32;
            }
            if (lastDirection == "down"){
                this.y += 32;
            }
        } else {
            if (lastDirection == randomDirection()){
                move();
            } else {
                lastDirection = randomDirection();
            }
            
        }
    }
}