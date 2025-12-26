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
        Image pacManDownImage = new ImageIcon(getClass().getResource("art/pacmanDown.png")).getImage();
        Image pacManUpImage = new ImageIcon(getClass().getResource("art/pacmanUp.png")).getImage();
        Image pacManLeftImage = new ImageIcon(getClass().getResource("art/pacmanLeft.png")).getImage();
        Image pacManRightImage = new ImageIcon(getClass().getResource("art/pacmanRight.png")).getImage();
        Image scaredGhostImage = new ImageIcon(getClass().getResource("art/scaredGhost.png")).getImage(); 
        Image powerFoodImage = new ImageIcon(getClass().getResource("art/powerFood.png")).getImage();
        Image cherryImage = new ImageIcon(getClass().getResource("art/cherry.png")).getImage();
        Image cherry2Image = new ImageIcon(getClass().getResource("art/cherry2.png")).getImage();

        sllImage.insertLast(new ArtImage(wallImage, "wall"));
        sllImage.insertLast(new ArtImage(blueGhostImage, "blueGhost"));
        sllImage.insertLast(new ArtImage(orangeGhostImage, "orangeGhost"));
        sllImage.insertLast(new ArtImage(pinkGhostImage, "pinkGhost"));
        sllImage.insertLast(new ArtImage(redGhostImage, "redGhost"));
        sllImage.insertLast(new ArtImage(pacManDownImage, "pacManDown"));
        sllImage.insertLast(new ArtImage(pacManUpImage, "pacManUp"));
        sllImage.insertLast(new ArtImage(pacManLeftImage, "pacManLeft"));

        sllImage.insertLast(new ArtImage(pacManRightImage, "pacManRight"));
        sllImage.insertLast(new ArtImage(scaredGhostImage, "scaredGhost"));
        sllImage.insertLast(new ArtImage(powerFoodImage, "powerFood"));
        sllImage.insertLast(new ArtImage(cherryImage, "cherryImage"));
        sllImage.insertLast(new ArtImage(cherry2Image, "cherry2Image"));
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

    @Override
    public String toString(){
        return imageName;
    }
}
