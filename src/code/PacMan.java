package code;
import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;
import java.util.Random;
import javax.swing.*;

import asset.*;

public class PacMan extends JPanel{
    ArtAsset asset = new ArtAsset();

    private int rowCount = 18;
    private int columnCount = 38;
    private int tileSize = 32;

    private int boardWidth = columnCount * tileSize;
    private int boardHeight = rowCount * tileSize;


    //ASSET
    private Image wallImage;
    private Image blueGhostImage;
    private Image orangeGhostImage;
    private Image pinkGhostImage;
    private Image redGhostImage;

    PacMan(){
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        setBackground(Color.BLACK);

        loadImage();
    }

    private void loadImage(){
        

        this.wallImage = asset.getImage("wall");
        this.blueGhostImage = asset.getImage("blueGhost");
        this.orangeGhostImage = asset.getImage("orangeGhost");
        this.pinkGhostImage = asset.getImage("pinkGhost");
        this.redGhostImage = asset.getImage("redGhost");
    
    }
}