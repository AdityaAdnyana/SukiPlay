package code;

import java.awt.*;
import java.awt.event.*;
//import java.util.HashSet;
import java.util.Random;

import javax.swing.*;
import asset.*;
import code.data_structure.SingleLinkedList;
import code.data_structure.pacman_graf.Graf;
//Local Library
import code.model.Block;
import code.model.BlueGhost;
import code.model.Entity;
import code.model.Ghost;
import code.model.OrangeGhost;
import code.model.RedGhost;
import code.model.PacManEntity;
import code.model.Text;
import code.physics.*;

public class PacMan extends JPanel implements ActionListener, KeyListener {
    // Window
    public static final int ROW_COUNT = 18;
    public static final int COLUMN_COUNT = 38;
    public static final int TILE_SIZE = 32;
    public static final int FONT_SIZE = 16;

    private int boardWidth = COLUMN_COUNT * TILE_SIZE;
    private int boardHeight = ROW_COUNT * TILE_SIZE;

    // ASSET
    ArtAsset asset = new ArtAsset();
    private Image wallImage;
    private Image blueGhostImage;
    private Image orangeGhostImage;
    private Image pinkGhostImage;
    private Image redGhostImage;

    private Image pacManUpImage;
    private Image pacManDownImage;
    private Image pacManLeftImage;
    private Image pacManRightImage;

    public static final String[] TILE_MAP = {
            "XXXXXXXXXXXXadityaX1acmanXXXXXXXXXXXXX",
            "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXOOX",
            "XXXXXX            P         XXXXXXXXXX",
            "X      X XXXXXXXXX XXXXXX X          X",
            "XXXX XXX                  X XXXXXXX XX",
            "X      X XX XXX  XXX  XXX XXXXXX X   X",
            "X XXXX X    XOX XXXXX XOX        X X X",
            "X      X XX XOX XXrXX XOX X XXXX X X X",
            "X XXXXXX XX XX  XpboX  XX X XOOX X X X",
            "         XX    XXX XXX      XOOX   X  ",
            "XXXXXXXX XX XX  XX XX  XX X XOOX X X X",
            "X         X XXX XX XX XXX X XXXX X X X",
            "X XXXXXXX       XX XX     X      X X X",
            "X         X XXX XX XX XXX X XXXXXX   X",
            "XXXXX XXX X XOX       XOX X        XXX",
            "XXOOX XXX X XXX XX XX XXX XX X XXX XOX",
            "XXOOX                              XOX",
            "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
    };

    public static SingleLinkedList<Block> walls = new SingleLinkedList<>(); // Block walls[] = new Block[370];
    private SingleLinkedList<Block> foods = new SingleLinkedList<>(); // 277 Food
    private SingleLinkedList<Ghost> ghosts = new SingleLinkedList<>(); // Entity ghosts[] = new Entity[4];
    private SingleLinkedList<Text> texts = new SingleLinkedList<>(); // texts[] = new Text[12];
    private PacManEntity pacMan;
    // private int currentWallsIndex;
    // // private int currentFoodsIndex;
    // private int currentGhostsIndex;
    // private int currentTextsIndex;

    private Timer gameLoop;
    char[] direction = { 'U', 'D', 'L', 'R' };
    Random random = new Random();

    int score = 0;
    int lives = 3;
    boolean gameOver = false;

    Graf graf = new Graf();

    PacMan() {
        System.out.println("PACMAN");
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        setBackground(Color.BLACK);
        addKeyListener(this);
        setFocusable(true);

        loadImage();

        loadMap();

        // for (Entity ghost :ghosts){
        // ghost.updatePath(pacMan);
        // }

        gameLoop = new Timer(40, this); // 25FPS <- 1000/25 = 40 :Note(Aditya)
        gameLoop.start();
        // System.out.println(walls.size());
        // System.out.println(foods.size());
        // System.out.println(ghosts.size());
    }

    private void loadImage() {
        this.wallImage = asset.getImage("wall");
        this.blueGhostImage = asset.getImage("blueGhost");
        this.orangeGhostImage = asset.getImage("orangeGhost");
        this.pinkGhostImage = asset.getImage("pinkGhost");
        this.redGhostImage = asset.getImage("redGhost");

        this.pacManUpImage = asset.getImage("pacManUp");
        this.pacManDownImage = asset.getImage("pacManDown");
        this.pacManLeftImage = asset.getImage("pacManLeft");
        this.pacManRightImage = asset.getImage("pacManRight");
    }

    public void loadMap() {
        walls.clear();
        foods.clear();
        ghosts.clear();
        texts.clear();
        graf.clear();

        for (int r = 0; r < ROW_COUNT; r++) {
            for (int c = 0; c < COLUMN_COUNT; c++) {
                char tile = TILE_MAP[r].charAt(c);
                int x = c * TILE_SIZE;
                int y = r * TILE_SIZE;

                addBlock(tile, x, y); 

                if (tile != 'X') {
                    int pathId = (r * COLUMN_COUNT) + c;
                    graf.addVertex(pathId);
                }
            }
        }

        for (int r = 0; r < ROW_COUNT; r++) {
            for (int c = 0; c < COLUMN_COUNT; c++) {
                char tile = TILE_MAP[r].charAt(c);

                if (tile != 'X') {
                    int pathId = (r * COLUMN_COUNT) + c;

                    // Tetangga Atas
                    if (r - 1 >= 0 && TILE_MAP[r - 1].charAt(c) != 'X') {
                        int upId = ((r - 1) * COLUMN_COUNT) + c;
                        graf.addEdge(pathId, upId, 1);
                    }
                    // Tetangga Bawah
                    if (r + 1 < ROW_COUNT && TILE_MAP[r + 1].charAt(c) != 'X') {
                        int downId = ((r + 1) * COLUMN_COUNT) + c;
                        graf.addEdge(pathId, downId, 1);
                    }
                    // Tetangga Kiri
                    if (c - 1 >= 0 && TILE_MAP[r].charAt(c - 1) != 'X') {
                        int leftId = (r * COLUMN_COUNT) + (c - 1);
                        graf.addEdge(pathId, leftId, 1);
                    }
                    // Tetangga Kanan (JANGAN ADA LOGIKA != 'O')
                    if (c + 1 < COLUMN_COUNT && TILE_MAP[r].charAt(c + 1) != 'X') {
                        int rightId = (r * COLUMN_COUNT) + (c + 1);
                        graf.addEdge(pathId, rightId, 1);
                    }
                }
            }
        }
    }

    public void addBlock(char tileMapChar, int x, int y) {
        int foodGap = (TILE_SIZE - (TILE_SIZE / 8)) / 2; // <-- Cari posisi tengah untuk posisi food :Note(Aditya)
        switch (tileMapChar) {
            case 'X':
                walls.insertFirst(new Block(wallImage, x, y, TILE_SIZE, TILE_SIZE));
                break;
            case ' ':
                Block food = new Block(null, x + foodGap, y + foodGap, TILE_SIZE / 8, TILE_SIZE / 8);
                foods.insertFirst(food);
                break;
            case 'p':
                //ghosts.insertFirst(new PinkGhost(pinkGhostImage, x, y, TILE_SIZE, TILE_SIZE));
                break;
            case 'b':
                ghosts.insertFirst(new BlueGhost(blueGhostImage, x, y, TILE_SIZE, TILE_SIZE));
                break;
            case 'o':
                ghosts.insertFirst(new OrangeGhost(orangeGhostImage, x, y, TILE_SIZE, TILE_SIZE));
                break;
            case 'r':
                ghosts.insertFirst(new RedGhost(redGhostImage, x, y, TILE_SIZE, TILE_SIZE));
                break;
            case 'P':
                pacMan = new PacManEntity(pacManRightImage, x - 4, y + 4, 28, 28);
                break;
            case 'a':
                texts.insertLast(new Text("A", x, y, FONT_SIZE));

                break;
            case 'd':
                texts.insertLast(new Text("D", x, y, FONT_SIZE));

                break;
            case 'i':
                texts.insertLast(new Text("I", x, y, FONT_SIZE));

                break;
            case 't':
                texts.insertLast(new Text("T", x, y, FONT_SIZE));

                break;
            case 'y':
                texts.insertLast(new Text("Y'", x, y, FONT_SIZE));

                break;
            case '1':
                texts.insertLast(new Text("P", x, y, FONT_SIZE));

                break;
            case 'c':
                texts.insertLast(new Text("C", x, y, FONT_SIZE));

                break;
            case 'm':
                texts.insertLast(new Text("M", x, y, FONT_SIZE));

                break;
            case 'n':
                texts.insertLast(new Text("N", x, y, FONT_SIZE));

                break;
        }
    }

    // Note(Aditya): Jika kita menggerakan window permainannya nanti, fungsi draw
    // ini dipanggil
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);

    }

    public void draw(Graphics g) {
        g.drawImage(pacMan.image, pacMan.x, pacMan.y, pacMan.width, pacMan.height, null);

        for (Entity ghost : ghosts) {
            g.drawImage(ghost.image, ghost.x, ghost.y, ghost.width, ghost.height, null);
        }

        for (Block wall : walls) {
            g.drawImage(wall.image, wall.x, wall.y, wall.width, wall.height, null);
        }

        g.setColor(Color.WHITE);
        g.setFont(new Font("MONOSPACED", Font.PLAIN, TILE_SIZE));

        if (gameOver) {
            g.drawString("GameOver" + String.valueOf(score), TILE_SIZE / 2, TILE_SIZE / 2);
        } else {
            g.drawString("x" + String.valueOf(lives) + "Score: " + String.valueOf(score), TILE_SIZE / 2, TILE_SIZE / 2);
        }

        for (Text text : texts) {
            if (text.c == "P") {
                g.setColor(Color.YELLOW);
            }
            g.drawString(text.c, text.x, text.y + TILE_SIZE);
        }

        g.setColor(Color.WHITE);
        for (Block food : foods) {
            g.fillRect(food.x, food.y, food.width, food.height);
        }
    }

    public void move() {
        pacMan.x += pacMan.velocityX;
        pacMan.y += pacMan.velocityY;

        if (pacMan.x <= 0) {
            pacMan.x = COLUMN_COUNT * TILE_SIZE;
        } else if (pacMan.x >= (COLUMN_COUNT * TILE_SIZE)) {
            pacMan.x = 0;
        }

        for (Block wall : walls) {
            if (Collision.checkCollision(pacMan, wall)) {
                pacMan.x -= pacMan.velocityX;
                pacMan.y -= pacMan.velocityY;
                break;
            }
        }

        for (Ghost ghost : ghosts) {
            ghost.updatePath(pacMan);
            ghost.x += ghost.velocityX;
            ghost.y += ghost.velocityY;
            //COLLISION CHECK
            // if (Collision.checkCollision(ghost, pacMan)) {
            //     lives -= 1;
            //     if (lives == 0) {
            //         gameOver = true;
            //         return;
            //     }
            //     resetPosition();
            // }

            for(Block wall : walls){
                if(Collision.checkCollision(ghost, wall)){
                    ghost.x -= ghost.velocityX;
                    ghost.y -= ghost.velocityY;
                    ghost.updatePath(pacMan);
                }
            }
        }

        Block foodEaten = null;
        for (Block food : foods) {
            if (Collision.checkCollision(pacMan, food)) {
                foodEaten = food;
                score += 10;
            }
        }
        foods.softDeleteNode(foodEaten);

        if (foods.isEmpty()) {
            foods.recoverAll();
            resetPosition();
        }

    }

    public void resetPosition() {
        pacMan.reset();
        pacMan.velocityX = 0;
        pacMan.velocityY = 0;
        for (Entity ghost : ghosts) {
            ghost.reset();
        }
    }

    @Override // Note(Aditya): Setiap gameLoop akan memanggil ini.
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if (gameOver) {
            gameLoop.stop();
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("Key Event: " + e.getKeyCode() + "\n" + e.getKeyChar());

        if (gameOver) {
            loadMap();
            resetPosition();
            lives = 3;
            score = 0;
            gameOver = false;
            gameLoop.start();
        }

        // PERSIMPANGAN NANTI
        // if((pacMan.x % 32) == TILE_SIZE/4){
        // pacMan.x += TILE_SIZE/4;
        // System.out.println("Magnet");
        // }
        // if((pacMan.y % 32) == TILE_SIZE/4){
        // pacMan.y += TILE_SIZE/4;
        // System.out.println("Magnet");
        // }
        // if((pacMan.x % 32) == (TILE_SIZE/4)*3){
        // pacMan.x -= TILE_SIZE/4;
        // System.out.println("Magnet");
        // }
        // if((pacMan.y % 32) == (TILE_SIZE/4)*3){
        // pacMan.y -= TILE_SIZE/4;
        // System.out.println("Magnet");
        // }

        switch (e.getKeyCode()) {
            case 38:
            case 87: // W
                pacMan.updateDirection('U');

                break;
            case 40:
            case 83: // S
                pacMan.updateDirection('D');
                break;
            case 37:
            case 65: // A
                pacMan.updateDirection('L');
                break;
            case 39:
            case 68: // D
                pacMan.updateDirection('R');
                break;
        }

        switch (pacMan.direction) {
            case 'U':
                pacMan.image = pacManUpImage;
                break;
            case 'D':
                pacMan.image = pacManDownImage;
                break;
            case 'L':
                pacMan.image = pacManLeftImage;
                break;
            case 'R':
                pacMan.image = pacManRightImage;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}