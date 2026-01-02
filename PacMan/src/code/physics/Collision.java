package code.physics;

import code.model.GameObject;
public class Collision{
    public Collision(){}

    public static boolean checkCollision(GameObject a, GameObject b){
        return  a.x < b.x + b.width && //pacMan.x < wall.x + 32
                a.x + a.width > b.x && //pacMan.x + pacMan.width > wall.x
                a.y < b.y + b.height&& //pacMan.y < wall.y + wall.heigh
                a.y + a.height > b.y;  //pacMan.y + pacMan.heigh > wall.y
    }
}
    