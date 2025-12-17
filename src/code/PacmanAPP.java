package code;

import javax.swing.JFrame;

public class PacmanAPP{

    public static void main(String[] args){
        int rowCount = 18;
        int columnCount = 38;
        int tileSize = 32;

        int boardWidth = columnCount * tileSize;
        int boardHeight = rowCount * tileSize;

        JFrame frame = new JFrame("Pac Man");
        frame.setVisible(true);
        //frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        PacMan game = new PacMan();
        frame.add(game);
        frame.pack(); //Menyesuaikan ukuran window size sesuai minimum size


    }
}