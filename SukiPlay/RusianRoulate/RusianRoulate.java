import java.util.Scanner;

public class RusianRoulate {
    // 1. Mekanik dibuat satu kali di sini sebagai pusat data
    Mekanik MK = new Mekanik();
    // 2. Controller menerima MK yang sama agar datanya nyambung
    Controller control = new Controller(MK);
    ComputerAI COM = new ComputerAI(MK, control); // AI juga butuh akses ke MK dan Controller yang sama
    
    public static void main(String []args){
        RusianRoulate RR = new RusianRoulate();       
        RR.round();
    }

    public void round(){
        Scanner scanner = new Scanner (System.in);
        System.out.println("=== Russian Roulette ===");
        System.out.print("Masukan nama player : ");
        String plyName = scanner.nextLine();
        
        control.startGame(plyName); // Controller mengisi data ke MK yang dishare

        // Sekarang MK ini sudah berisi data, jadi tidak akan Null
        NodeCharacter ply = MK.searchCharacter(0);
        NodeCharacter com = MK.searchCharacter(1);

        // FIX: Loop condition diubah menjadi < 40
        for(int round = 1; round <= 40; round++){
            int turn = round % 2;
            
            // Cek peluru, jika habis reload
            if(MK.headBullet == null){
                System.out.println("--- RELOADING ---");
                control.reload();
                MK.view(); // Lihat isi peluru sebentar (opsional cheat)
            }

            System.out.println("\nRound : " + round);
            System.out.println(ply.namaPlayer+" HP:"+ply.HP+" \tVS\t "+com.namaPlayer+" HP:"+com.HP);
            
            // Visualisasi peluru
            NodeBullet cur = MK.headBullet;
            System.out.print("Magazine: ");
            while(cur != null){
                System.out.print("[ ? ] ");
                cur = cur.next;
            }
            System.out.println("");

            // Cek Kematian sebelum giliran
            if(ply.HP <= 0){
                System.out.println("Anda Mati! Game Over.");
                return;
            } else if (com.HP <= 0){
                System.out.println("Musuh Mati! Anda Menang.");
                return;
            }

            if(turn == 1){ // Giliran Player (Ganjil)
                System.out.println(">> Giliran ANDA");
                actionPLY(ply.namaPlayer);
            } else { // Giliran Komputer (Genap)
                System.out.println(">> Giliran KOMPUTER");
                COM.action();
            }
        }
    }

    public void actionPLY(String plyName){
        Scanner scanner = new Scanner (System.in);
        // HAPUS: RusianRoulate RR = new RusianRoulate(); (Jangan bikin objek baru)
        
        NodeItem view = MK.searhItem("View", 0);
        NodeItem heal = MK.searhItem("Heal", 0);
        NodeItem skip = MK.searhItem("Skip", 0);
        
        System.out.println("======   Option   =====");
        System.out.println("1. Shoot Enemy (Tembak Musuh)");
        System.out.println("2. Shoot Self (Tembak Diri Sendiri - Risk)");
        System.out.println("3. Item View (" + view.jumlahItem + ")");
        System.out.println("4. Item Heal (" + heal.jumlahItem + ")");
        System.out.println("5. Item Skip (" + skip.jumlahItem + ")");
        System.out.print("Pilih aksi: ");
        
        int opsi = scanner.nextInt();

        switch(opsi){
            case 1:
                System.out.println("Anda menembak musuh...");
                control.shootEnemy(1); // ID 1 adalah musuh
                break;
            case 2:
                System.out.println("Anda menembak diri sendiri...");
                control.shootSelf(0); // ID 0 adalah player
                break;
            case 3:
                if(view.jumlahItem > 0){
                    control.ViewBullet(0);
                    // Setelah pakai item, giliran belum habis, panggil action lagi
                    actionPLY(plyName); 
                } else {
                    System.out.println("Item habis!");
                    actionPLY(plyName);
                }
                break;
            case 4:
                if(heal.jumlahItem > 0){
                    control.heal(0);
                    // Heal biasanya memakan giliran atau tidak? Asumsikan memakan giliran.
                } else {
                    System.out.println("Item habis!");
                    actionPLY(plyName);
                }
                break;
            case 5:
                if(skip.jumlahItem > 0){
                    control.skip(0);
                    System.out.println("Anda skip giliran musuh (tapi implementasi skip butuh logika turn extra)");
                } else {
                    System.out.println("Item habis!");
                    actionPLY(plyName);
                }
                break;
            default:
                actionPLY(plyName);
        }
    }
}

class Controller{
    Mekanik MK; // Reference ke Mekanik utama

    // Constructor Injection: Terima MK dari RusianRoulate
    public Controller(Mekanik mkRef){
        this.MK = mkRef;
    }

    public void reload(){
        // FIX: Loop condition n < 6
        for (int n = 0 ; n < 6 ; n++) {
            MK.addBullet();
        }
    }

    public void shootSelf(int idPly){
       NodeBullet bullet = MK.dequeue(); // Pakai dequeue bukan enqueue (ambil peluru, bukan nambah)
       if(bullet != null) {
           MK.shoot(idPly, bullet);
           System.out.println("Peluru keluar: " + bullet.nama + " (" + bullet.value + " damage)");
       }
    }

    public void shootEnemy(int idEnemy){
       NodeBullet bullet = MK.dequeue();
       if(bullet != null) {
           MK.shoot(idEnemy, bullet);
           System.out.println("Peluru keluar: " + bullet.nama + " (" + bullet.value + " damage)");
       }
    }
    
    public void heal(int id){
        NodeCharacter ply = MK.searchCharacter(id);
        NodeItem heal = MK.searhItem("Heal", id);
        if(heal.jumlahItem > 0){
            MK.takeHeal(ply, heal.value);
            heal.jumlahItem -=1;
            System.out.println(ply.namaPlayer + " menggunakan Heal.");
        }
    }

    public void ViewBullet(int id){
        NodeItem view = MK.searhItem("View", id);
        if(view.jumlahItem > 0){
            MK.view();
            view.jumlahItem -=1;
        }
    }

    public void skip(int id){
        NodeItem skip = MK.searhItem("Skip", id);
        skip.jumlahItem -=1;
    }

    public void startGame(String namaPly){
        MK.addNewCharacter(namaPly, 0);
        MK.addNewCharacter("Computer", 1);
        
        NodeCharacter ply = MK.searchCharacter(0);
        NodeCharacter com = MK.searchCharacter(1);
        
        if(ply != null) {
            MK.instalNewItem(ply);
            MK.addItem(ply);
        }
        if(com != null) {
            MK.instalNewItem(com);
            MK.addItem(com);
        }
        
        reload();
    }
}

class ComputerAI{
    Mekanik MK;
    Controller controller;

    // Constructor Injection
    public ComputerAI(Mekanik mkRef, Controller contRef){
        this.MK = mkRef;
        this.controller = contRef;
    }

    public void action(){
        NodeCharacter com = MK.searchCharacter(1);
        
        // AI Logic sederhana
        int rand = (int)(Math.random() * 2) + 1; // 1 atau 2
        
        // Jika HP rendah, prioritas heal
        NodeItem heal = MK.searhItem("Heal", 1);
        if(com.HP <= 30 && heal.jumlahItem > 0){
            System.out.println("Computer uses Heal!");
            controller.heal(1);
            return;
        }

        if(rand == 1){
            System.out.println("Computer shoots YOU!");
            controller.shootEnemy(0); // Tembak player (ID 0)
        } else {
            System.out.println("Computer shoots ITSELF!");
            controller.shootSelf(1); // Tembak diri sendiri (ID 1)
        }
    }
}

class Mekanik{
    NodeCharacter headCharacter;
    // NodeItem headItem; // Tidak perlu headItem global jika item nempel di character
    NodeBullet headBullet;
    
    public Mekanik(){}

    public void addNewCharacter(String name, int idCharacters){
        NodeCharacter nn = new NodeCharacter(name, 60, idCharacters);
        if(headCharacter == null){
            headCharacter = nn;
        } else {
            NodeCharacter cur = headCharacter;
            while (cur.next != null) { 
                cur = cur.next;
            }
            cur.next = nn;
        }
    }

    public void instalNewItem(NodeCharacter ply){
        // Membuat linked list item terpisah untuk setiap player
        // Perbaikan logika instalasi item agar chain-nya benar
        NodeItem i1 = new NodeItem("View", 1, 0, ply.idCharacter);
        NodeItem i2 = new NodeItem("Heal", 3, 20, ply.idCharacter); // Value heal 20
        NodeItem i3 = new NodeItem("Skip", 2, 0, ply.idCharacter);
        
        // Manual linking
        i1.next = i2;
        i2.next = i3;
        
        ply.item = i1; // Set head item milik player
    }

    // Method addItem sebelumnya agak redundant dengan instalNewItem, disederhanakan saja
    public void addItem(NodeCharacter ply){
        // Logic sudah dihandle di instalNewItem
    }

    public NodeCharacter searchCharacter(int idCharacter){
        NodeCharacter cur = headCharacter;
        while(cur != null){
            if(cur.idCharacter == idCharacter){
                return cur;
            }
            cur = cur.next;
        }
        return null;
    }

    public NodeItem searhItem(String nama, int idCharacter){
        NodeCharacter ply = searchCharacter(idCharacter);
        if(ply == null) return null;
        
        NodeItem cur = ply.item;
        while(cur != null){
            // Gunakan .equals untuk string!
            if(cur.namaItem.equals(nama)){
                return cur;
            }
            cur = cur.next;
        }
        return null;
    }

    public void takeDMG(NodeCharacter player, int value){
        player.HP -= value;
        System.out.println(player.namaPlayer + " terkena " + value + " damage! Sisa HP: " + player.HP);
    }
    
    public void takeHeal(NodeCharacter player, int value){
        player.HP += value;
        if(player.HP > 60) player.HP = 60;
        System.out.println(player.namaPlayer + " heal " + value + " HP. Total HP: " + player.HP);
    }

    public void addBullet(){
        int rand = (int)(Math.random() * 2) + 1;
        NodeBullet nn;
        if (rand == 1) {
            nn = new NodeBullet("Blank", 0); // Blank tidak ada damage
        } else {
            nn = new NodeBullet("Live", 10); // Live ada damage
        }

        if(headBullet == null){
            headBullet = nn;
        } else {
            NodeBullet cur = headBullet;
            while(cur.next != null){
                cur = cur.next;
            }
            cur.next = nn;
        }
    }

    // Mengambil peluru dari depan (FIFO/Queue) lalu menghapusnya
    public NodeBullet dequeue(){
        if(headBullet == null){
            return null;
        }
        NodeBullet bullet = headBullet; // Ambil yang paling depan
        headBullet = headBullet.next;   // Geser head ke belakang
        return bullet;
    }

    public void shoot(int targetId, NodeBullet bullet){
        NodeCharacter target = searchCharacter(targetId); // FIX: Cari berdasarkan ID parameter
        if(target != null){
            if(bullet.nama.equals("Live")){ // Gunakan .equals
                takeDMG(target, bullet.value);
            } else {
                System.out.println("*Klik* (Blank) - Tidak ada damage.");
            }
        }
    }

    public void view(){
       NodeBullet cur = headBullet;
       int live = 0;
       int blank = 0;
       while (cur != null) { 
           if (cur.nama.equals("Live")) live++;
           else blank++;
           cur = cur.next;
       }
       System.out.println(">> SECRET VIEW: " + live + " Live, " + blank + " Blank");
    }
}

class NodeBullet{
    String nama;
    int value;
    NodeBullet next;

    public NodeBullet(String nama, int value) {
        this.nama = nama;
        this.value = value;
        this.next = null;
    }
}

class NodeCharacter{
    String namaPlayer;
    int HP;
    NodeItem item; // Head dari linked list item milik player ini
    NodeCharacter next;
    int idCharacter;
    boolean life;

    public NodeCharacter(String namaPlayer, int HP, int idCharacter) {
        this.namaPlayer = namaPlayer;
        this.HP = HP;
        this.idCharacter = idCharacter;
        this.life = true;
        this.next = null;
    }
}

class NodeItem{
    String namaItem;
    int value;
    int jumlahItem;
    NodeItem next;
    int idCharacter;

    public NodeItem(String namaItem, int jumlahItem, int value, int idCharacter) {
        this.namaItem = namaItem;
        this.jumlahItem = jumlahItem;
        this.value = value;
        this.idCharacter = idCharacter;
        this.next = null;
    }
}