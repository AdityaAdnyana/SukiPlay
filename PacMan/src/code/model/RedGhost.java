package code.model;

import java.awt.Image;


import code.ai.Dijkstra;
import code.data_structure.Stack;

public class RedGhost extends Ghost{
    private Stack<Integer> path = new Stack<>();

    private int prevPacmanTileX = -1;
    private int prevPacmanTileY = -1;
    private Dijkstra dijkstra = new Dijkstra();

    public RedGhost(Image image, int x, int y, int width, int height) {
        super(image, x, y, width, height);
        //TODO Auto-generated constructor stub
    }

    @Override
    public void updatePath(PacManEntity pacman){
        
        int selfCol = this.x/TILE_SIZE;
        int selfRow = this.y/TILE_SIZE;
        
        int pacmanCol = pacman.x/TILE_SIZE;
        int pacmanRow = pacman.y/TILE_SIZE;


        int selfTileID = (selfRow * COLUMN_COUNT) + selfCol;
        int pacmanTileID = (pacmanRow * COLUMN_COUNT) + pacmanCol;

        if(prevPacmanTileX != pacmanCol || prevPacmanTileY != pacmanRow){
            prevPacmanTileX = pacmanCol;
            prevPacmanTileY = pacmanRow;
            path = dijkstra.findPath(selfTileID, pacmanTileID);

            if(path != null && ! path.isEmpty()){
                int firstStep = path.peek();
                if(firstStep == selfTileID){
                    path.pop();
                }
            }
        }

        if((this.x%TILE_SIZE) == 0 && (this.y%TILE_SIZE) == 0){

            if(path != null && !path.isEmpty()){
                int nextPathID = path.pop();
                
                int nextCol = nextPathID % COLUMN_COUNT;
                int nextRow = nextPathID / COLUMN_COUNT;

                int nextPathX = nextCol * TILE_SIZE;
                int nextPathY = nextRow * TILE_SIZE;
                

                if(nextPathX > this.x){
                    direction = 'R';
                }else if(nextPathX < this.x){
                    direction = 'L';
                }else if(nextPathY < this.y){
                    direction = 'U';
                }else if(nextPathY > this.y){
                    direction = 'D';
                }
               
                updateVelocity();
            }           
        }
    }    
}