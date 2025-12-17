package asset;

import code.data_structure.*;

import java.awt.Image;

import javax.swing.ImageIcon;

public class ArtAsset {
    
SingleLinkedList<ArtImage> sllImage = new SingleLinkedList<>();    

    public ArtAsset(){
        Image wallImage = new ImageIcon(getClass().getResource("art/wall.png")).getImage();
        Image blueGhostImage = new ImageIcon(getClass().getResource("art/blueGhost.png")).getImage();
        Image orangeGhostImage = new ImageIcon(getClass().getResource("art/orangeGhost.png")).getImage();
        Image pinkGhostImage = new ImageIcon(getClass().getResource("art/pinkGhost.png")).getImage();
        Image redGhostImage = new ImageIcon(getClass().getResource("art/redGhost.png")).getImage();

        sllImage.insertLast(new ArtImage(wallImage, "wall"));
        sllImage.insertLast(new ArtImage(blueGhostImage, "blueGhost"));
        sllImage.insertLast(new ArtImage(orangeGhostImage, "orangeGhost"));
        sllImage.insertLast(new ArtImage(pinkGhostImage, "pinkGhost"));
        sllImage.insertLast(new ArtImage(redGhostImage, "redGhost"));
    }

    public Image getImage(String name){
        ArtImage dummySearch = new ArtImage(null, name);
        
        // Search data dengan membandingkan nama pada Node di SLL dengan dummySearch
        ArtImage foundNode = sllImage.searchData(dummySearch);

        if(foundNode != null){
            return foundNode.getImage();
        } else {
            System.out.println("Image not found: " + name);
            return null;
        }
    }

    // public static void main(String[] args) {
    //     ArtAsset asset = new ArtAsset();
    //     System.out.println(asset.getImage("wall"));
    // }
}

class ArtImage{
    private Image image;
    private String imageName;

    public ArtImage(Image image, String imageName){
        this.image = image;
        this.imageName = imageName;
    }

    public Image getImage(){
        return image;
    }

    public String getImageName(){
        return imageName;
    }

    @Override
    public boolean equals(Object obj){
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ArtImage image = (ArtImage) obj;
        return getImageName().equals(image.getImageName());
    }
}
