package code.model;

import java.awt.Image;

import code.PacMan;
import code.physics.Collision;


public class Entity extends GameObject{
        public int startX;
        public int startY;
        public char direction;
        public int velocityX;
        public int velocityY;
        int TILE_SIZE = PacMan.TILE_SIZE;
        int COLUMN_COUNT = PacMan.COLUMN_COUNT;
        
        public Entity(Image image, int x, int y, int width, int height){
            this.image = image;
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.image = image;
            this.startX = x;
            this.startY = y;
        }
      
        public void updateDirection(char direction){
            char prevDirection = this.direction;
            this.direction = direction;
            updateVelocity();
            this.x += this.velocityX;
            this.y += this.velocityY;

            for(Block wall: PacMan.walls){
                if(Collision.checkCollision(this, wall)){ //packMan or Ghost
                    this.x -= this.velocityX;
                    this.y -= this.velocityY;
                    this.direction = prevDirection;
                    updateVelocity();
                }
            }
        }

        public void updateVelocity(){
            switch (direction) {
            case 'U': //W
                velocityY = -TILE_SIZE/4;
                velocityX = 0;
                break;
            case 'D': //S
                velocityY = TILE_SIZE/4;
                velocityX = 0;
                break;
            case 'L': //A
                velocityX = -TILE_SIZE/4;
                velocityY = 0;
                break;
            case 'R': //D
                velocityX = TILE_SIZE/4;
                velocityY = 0;
                break;
            }
        }

        public void reset(){
            this.x = this.startX;
            this.y = this.startY;
        }
}