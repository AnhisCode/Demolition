package demolition;

import processing.core.PApplet;
import processing.core.PImage;

import java.util.ArrayList;
import java.util.Objects;

public class YellowEnemy extends Enemy{

    /**
    * Constructor for YellowEnemy object
    * @param x starting X coordinate 
    * @param y starting Y coordinate
    */
    public YellowEnemy(int x, int y){
        super(x,y);
        this.sprite = Enemy.yellowDown.get(0);
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
                this.sprite = Enemy.yellowUp.get(animationCounter);
                animationCounter++;
                if (animationCounter > 3){
                    animationCounter = 0;
                }
            }
        }
        if (this.lastDirection == "left"){
            if (counter % 12 == 0){
                this.sprite = Enemy.yellowLeft.get(animationCounter);
                animationCounter++;
                if (animationCounter > 3){
                    animationCounter = 0;
                }
            }
        }
        if (this.lastDirection == "right"){
            if (counter % 12 == 0){
                this.sprite = Enemy.yellowRight.get(animationCounter);
                animationCounter++;
                if (animationCounter > 3){
                    animationCounter = 0;
                }
            }
        }
        if (this.lastDirection == "down"){
            if (counter % 12 == 0){
                this.sprite = Enemy.yellowDown.get(animationCounter);
                animationCounter++;
                if (animationCounter > 3){
                    animationCounter = 0;
                }
            }
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
            if (lastDirection == "up"){
                lastDirection = "left";
            }
            else if (lastDirection == "left"){
                lastDirection = "down";
            }
            else if (lastDirection == "right"){
                lastDirection = "up";
            }
            else if (lastDirection == "down"){
                lastDirection = "right";
            }
        }
    }

}