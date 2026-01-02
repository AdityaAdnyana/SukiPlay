package code.model;

import java.awt.Image;

public abstract class Ghost extends Entity{

    public Ghost(Image image, int x, int y, int width, int height) {
        super(image, x, y, width, height);
        //TODO Auto-generated constructor stub
    }

    public abstract void updatePath(PacManEntity pacman);
}
