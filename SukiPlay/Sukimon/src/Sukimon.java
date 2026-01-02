package Sukimon.src;

import java.util.Scanner;
import java.util.Random;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sukimon {

    Node root;

    Scanner input = new Scanner(System.in);
    Random acak = new Random();

    Clip clip;
    
    public static void main(String[] args) {
        Sukimon menu = new Sukimon();

        String music = "backsound.wav";
        menu.Music(music);

        menu.runthis();
    }

    public void play(){
        Sukimon menu = new Sukimon();

        String music = "backsound.wav";
        menu.Music(music);

        menu.runthis();
    }

    class Node {

        Enemy musuh;
        Node left, right;

        public Node(Enemy musuh) {
            this.musuh = musuh;
            left = right = null;
        }
    }

    class Enemy {

        int id;
        String nama;

        public Enemy(int id, String nama) {
            this.id = id;
            this.nama = nama;
        }

        public int getId() {
            return id;
        }

        public String getNama() {
            return nama;
        }

        public String toString() {
            return (id + " " + nama);
        }
    }

    public Sukimon() {
        root = null;
    }

    public void insert(int nim, String nama) {
        root = insertRec(root, nim, nama);
    }

    public Node insertRec(Node root, int id, String nama) {
        if (root == null) {
            root = new Node(new Enemy(id, nama));
            return root;
        }
        if (id < root.musuh.getId()) {
            root.left = insertRec(root.left, id, nama);
        } else if (id > root.musuh.getId()) {
            root.right = insertRec(root.right, id, nama);
        }
        return root;
    }

    private void inorderRec(Node root) {
        if (root != null) {
            inorderRec(root.left);
            System.out.print("Level " + root.musuh + " | ");
            inorderRec(root.right);
        }
    }

    void preorderTraversal(Node root) {
        if (root == null) {
            return;
        }
        System.out.print("Level " + root.musuh + " | ");
        preorderTraversal(root.left);
        preorderTraversal(root.right);
    }

    void postorderTraversal(Node root) {
        if (root == null) {
            return;
        }
        postorderTraversal(root.left);
        postorderTraversal(root.right);
        System.out.print("Level " + root.musuh + " | ");
    }

    public void dataShow() {
        System.out.println("<<===== Daftar Musuh =====>> ");
        System.out.print("inorder  : ");
        inorderRec(root);
        System.out.println(" ");
        System.out.print("Preorder : ");
        preorderTraversal(root);
        System.out.println("");
        System.out.print("Postorder : ");
        postorderTraversal(root);
        System.out.println("");
    }

    void runthis() {
        insert(8, "wrath");
        insert(6, "lust");
        insert(3, "Unfair");
        insert(10, "sloth");
        insert(5, "envy");
        insert(1, "Stubborn");
        insert(4, "pride");
        insert(2, "Gloomy");
        insert(9, "gluttony");
        insert(7, "Greedy");

        home();
    }

    String namaplayer;

    //stats player
    int hp1, atk1, deff1, level = 0;
    //stats musuh
    int hp2, atk2, deff2 = 0;

    //ini buat music
    public void Music(String lokasi) {
        try {

           if (getClass().getResource(lokasi) != null) {
                AudioInputStream input = AudioSystem.getAudioInputStream(getClass().getResource(lokasi));
                this.clip = AudioSystem.getClip();
                clip.open(input);
                clip.loop(Clip.LOOP_CONTINUOUSLY);
                clip.start();
           } else {
               System.out.println("File tidak ada");
           }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public void delay(int detik){
        try{
            Thread.sleep(detik *1000);
        }catch(Exception a){
            System.out.println(".....");
        }
    }

    public void home() {
        System.out.println(" ");
        System.out.println("<<====== Selamat datang di Sukimon battle =======>>");
        System.out.println("1. Play");
        System.out.println("2. Exit");
        System.out.print("Pilih: ");

        try {
            int pilihan = input.nextInt();

            switch (pilihan) {
                case 1:
                    pilihchar();
                    break;
                case 2:
                    System.out.println("Terima kasih telah bermain.");
                    clip.stop();
                    break;
                default:
                    home();
            }
        } catch (Exception w) {
            System.out.println("....");
            input.nextLine();
            home();
        }
    }

    public void pilihchar() {
        System.out.println("Siapakah nama kamu wahai pemuda?");

        input.nextLine();
        namaplayer = input.nextLine();

        System.out.println("Di suatu hari di dunia sukimon datang seorang " + namaplayer + " untuk menyelamatkan dunia itu dari kehancuran yang sebabkan oleh 10 Jendralmon yang menyebabkan warga di dunia itu melupakan jati dirinya.");
        delay(3);
        System.out.println("Untuk menyelamatkan dunia ini ia harus menghadapi mengalahkan para 10 Jendral kuat, sumber dari semua kekacauan di dunia sukimon");
        delay(3);
        System.out.println("Tentu tidaklah mudah,karena ia harus melawan para Jendralmon yang tersebar di setiap penjuru dunia sukimon ini. ");
        delay(3);
        System.out.println("Dan kini petualangan pun dimulai!!!");
        System.out.println(" ");
        jalurBattle();
    }

    public void jalurBattle() {
        System.out.println("<<====== PILIH JALUR PETUALANGANMU ======>>");
        dataShow();
        System.out.println("1. InOrder ");
        System.out.println("2. PreOrder ");
        System.out.println("3. PostOrder ");
        System.out.print("Pilihanmu: ");

        try {
            int mode = input.nextInt();
            input.nextLine();
            jalur(mode);
        } catch (Exception e) {
            System.out.println("hmm.... aneh.....");
            input.nextLine();
            home();
        }
    }

    public void jalur(int jalur) {
        // Status Player 
        hp1 = acak.nextInt(500) + 500;
        atk1 = acak.nextInt(200) + 100;
        deff1 = acak.nextInt(50) + 20;

        System.out.println("Memulai perjalanan... Player HP: " + hp1);

        switch (jalur) {
            case 1:
                battleInOrder(root);
                break;
            case 2:
                battlePreOrder(root);
                break;
            case 3:
                battlePostOrder(root);
                break;
            default:
                System.out.println("hmm agak unik namun tidak ada.");
                jalurBattle();
        }

        if (hp1 > 0) {
            System.out.println("<<================================================>>");
            System.out.println("SELAMAT! Semua jendralmon sudah kamu kalahkan. ");
            System.out.println("Dunia sukimon telah kembali damai");
            System.out.println("Terima kasih pahlawan " + namaplayer);
            System.out.println("<<================================================>>");
            System.out.println("");
            home();
        } else {
            gameOver();
        }
    }

    // inorder : Kiri, Lawan, Kanan
    public void battleInOrder(Node root) {
        if (root != null && hp1 > 0) {
            battleInOrder(root.left);

            if (hp1 > 0) {
                fightOneEnemy(root.musuh);
            }
            battleInOrder(root.right);
        }
    }

    // preorder : Lawan, Kiri, Kanan
    public void battlePreOrder(Node root) {
        if (root != null && hp1 > 0) {
            fightOneEnemy(root.musuh);

            if (hp1 > 0) {
                battlePreOrder(root.left);
            }
            if (hp1 > 0) {
                battlePreOrder(root.right);
            }
        }
    }

    // postorder : Kiri, Kanan, Lawan
    public void battlePostOrder(Node root) {
        if (root != null && hp1 > 0) {
            battlePostOrder(root.left);
            if (hp1 > 0) {
                battlePostOrder(root.right);
            }
            if (hp1 > 0) {
                fightOneEnemy(root.musuh);
            }
        }
    }

    public void fightOneEnemy(Enemy musuh) {
        System.out.println("<<================================================>>");
        System.out.println("<<====== MENGHADAPI: " + musuh.getNama() + " (Level " + musuh.getId() + ") =======>>");

        hp2 = 300 + (musuh.getId() * 100);
        atk2 = 50 + (musuh.getId() * 20);
        deff2 = (musuh.getId() * 5);

        System.out.println("Status Musuh -> HP: " + hp2 + " | ATK: " + atk2 + " | DEF: " + deff2);

        boolean playerWin = false;
        int turnLimit = 30;
        int turn = 0;
        level += 1;

        while (turn < turnLimit && hp1 > 0 && hp2 > 0) {
            turn++;
            System.out.println("--- Ronde " + turn + " ---");
            System.out.println(namaplayer + " [LEVEl  " + level + "] vs " + musuh.getNama() + " [LEVEL " + musuh.getId() + "]");
            System.out.println("HP Musuh : " + hp2 + " | ATK: " + atk2 + " | DEF: " + deff2);
            System.out.println("HP " + namaplayer + " : " + hp1 + " | ATK: " + atk1 + " | DEF: " + deff1);
            System.out.println("1. Attack  2. Heal  3. Buff Atk  0. Buff Def");
            System.out.print("Aksi: ");

            try {
                int aksi = input.nextInt();

                // Player moveSet
                switch (aksi) {
                    case 1: //attack 
                        int damage = atk1 - deff2;
                        if (damage <= 0) {
                            System.out.println(" pertahanan musuh terlalu kuat! Musuh tidak terkena damage :v ");
                            break;
                        } else {
                            hp2 -= damage;
                            System.out.println(namaplayer + " menyerang! Musuh terkena " + damage + " damage.");
                            break;
                        }
                    case 2: //heal
                        int heal = acak.nextInt(100) + 50;
                        hp1 += heal;
                        System.out.println(namaplayer + " memulihkan " + heal + " HP.");
                        break;
                    case 3: //buff attack
                        atk1 += 20;
                        System.out.println(namaplayer + " menajamkan serangan! (ATK +20)");
                        break;
                    case 0: //buff deffens
                        deff1 += 10;
                        System.out.println(namaplayer + " bertahan! (DEF +10)");
                        break;
                    default: // :v
                        System.out.println(namaplayer + " bingung dan tidak melakukan apa-apa.");
                }
            } catch (Exception e) {
                System.out.println(namaplayer + " kebingungan");
                input.nextLine();
            }

            if (hp2 <= 0) {
                playerWin = true;
                break;
            }

            // Bot moveSet
            int opsibot = acak.nextInt(4);
            switch (opsibot) {
                case 0: // Bot nya planga plongo :>
                    System.out.println(musuh.getNama() + " bingung dan tidak melakukan apa-apa.");
                    break;
                case 1:
                    // Attack 
                    int damageBot = atk2 - deff1;
                    if (damageBot < 0) {
                        System.out.println(" pertahananmu terlalu kuat! Musuh tidak memberikan damage :v ");
                        break;
                    } else {
                        hp1 -= damageBot;
                        System.out.println(musuh.getNama() + " menyerang! Kamu terkena " + damageBot + " damage.");
                        break;
                    }
                case 2:
                    // Heal
                    int healBot = 50;
                    hp2 += healBot;
                    System.out.println(musuh.getNama() + " memulihkan diri sebesar " + healBot + " HP.");
                    break;
                case 3:
                    // Buff Def
                    deff2 += 5;
                    System.out.println(musuh.getNama() + " memperkuat tubuhnya. (DEF +5)");
                    break;
            }
            
            delay(2);
        }

        if (playerWin == true) {
            System.out.println("<<====== " + musuh.getNama() + " KALAH! ======>>");
            System.out.println("Kamu mendapat sedikit istirahat (HP +50)");
            hp1 += 50;
        } else {
            System.out.println("<<====== KAMU KALAH MELAWAN " + musuh.getNama() + " ======>>");
            gameOver();
        }
    }

    public void gameOver() {
        System.out.println(".......");
        System.out.println("Menyerah bukanlah pilihan wahai pahlawan " + namaplayer);
        System.out.println("tapi apa yang bisa aku lakukan untuk mencegah mu menyerah? " + namaplayer);
        System.out.println("Coba lagi? (1. Ya / 2. Tidak)");
        try {
            int retry = input.nextInt();
            input.nextLine();
            if (retry == 1) {
                jalurBattle();
            } else {
                System.out.println("Selamat tinggal");
                delay(2);
                home();
            }
        } catch (Exception e) {
            System.out.println("Mungkin kamu sudah lelah....");
            input.nextLine();
            delay(3);
            home();
        }
    }
}
