

import java.util.Scanner;

import Sukimon.src.Sukimon;
import code.PacmanAPP;
import src.RusianRoulate;

// 1. CLASS NODE: Merepresentasikan satu Game
class GameNode {
    String judulGame;
    GameNode next; // Pointer ke game selanjutnya

    public GameNode(String judulGame) {
        this.judulGame = judulGame;
        this.next = null;
    }
}

class Akun {
    String nama;
    String pass;

    public Akun(String nama, String pass) {
        this.nama = nama;
        this.pass = pass;
    }

    public String getNama() {
        return nama;
    }

    public String getPass() {
        return pass;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Akun input = (Akun) obj;
        return getNama().equals(input.getNama()) && getPass().equals(input.getPass());
    }

    @Override
    public String toString() {
        return getNama();
    }

}

// 2. CLASS LINKED LIST: Mengelola Library Game
class GameLibrary {
    GameNode head;

    // Menambah game baru ke library (Insert at End)
    public void tambahGame(String judul) {
        GameNode newGame = new GameNode(judul);
        if (head == null) {
            head = newGame;
        } else {
            GameNode temp = head;
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.next = newGame;
        }
    }

    // Menampilkan semua game di library
    public void tampilkanLibrary() {
        if (head == null) {
            System.out.println("Library kosong. Belum ada game yang diinstal.");
            return;
        }

        GameNode temp = head;
        int nomor = 1;
        System.out.println("\n=== KOLEKSI GAME ANDA ===");
        while (temp != null) {
            System.out.println(nomor + ". " + temp.judulGame);
            temp = temp.next;
            nomor++;
        }
    }

    // Logika memilih game berdasarkan nomor urut (Linked List Traversal)
    public void mainkanGame(int nomorUrut) {
        GameNode temp = head;
        int hitung = 1;
        boolean ditemukan = false;

        while (temp != null) {
            if (hitung == nomorUrut) {
                System.out.println("\n>> Memulai System...");
                System.out.println(">> LAUNCHING: " + temp.judulGame + "...");
                System.out.println(">> Selamat Bermain!");
                ditemukan = true;

                switch (temp.judulGame) {
                    case "Pacman":
                        PacmanAPP pacmanGame = new PacmanAPP();
                        pacmanGame.playPacMan();
                        break;
                    case "Sukimon":
                        Sukimon skm = new Sukimon();
                        skm.play();
                        break;
                    case "Rusian Roulate":
                        RusianRoulate rr = new RusianRoulate();
                        rr.round();
                        break;
                    default:
                        System.out.println("GAADA KOCAK");
                        break;
                }

                break;
            }
            temp = temp.next;
            hitung++;
        }

        if (!ditemukan) {
            System.out.println("\n[!] Game dengan nomor tersebut tidak ditemukan.");
        }
    }
}

class AkunManager {
    static Scanner scanner = new Scanner(System.in);

    public static SingleLinkedList<Akun> sllAccount = new SingleLinkedList<>();

    private static void registrasi() {
        System.out.println("Masukkan Username Baru: ");
        String user = scanner.nextLine();
        scanner.nextLine();
        System.out.print("Password : ");
        String pass = scanner.nextLine();

        sllAccount.insertFirst(new Akun(user, pass));
        Akun dummy = new Akun(user, pass);
        System.out.println("REGISTRASI BERHASIL: " + sllAccount.searchData(dummy));
        menu();
    }

    private static void login() {
        System.out.print("Username : ");
        String user = scanner.nextLine();
        scanner.nextLine();
        System.out.print("Password : ");
        String pass = scanner.nextLine();

        Akun newInput = new Akun(user, pass);

        boolean isAkunFound = newInput.equals(sllAccount.searchData(newInput));

        if (isAkunFound) {
            System.out.println("\nLogin Berhasil! Selamat datang, " + user + ".");
        } else {
            System.out.println("[!] Username atau Password salah. Coba lagi.\n");
            menu();
        }
    }

    public static void menu(){
        System.out.println("1. REGISTRASI / 2. LOGIN");
        int regisOrLogin = -1;

    
            regisOrLogin = scanner.nextInt();
        

        switch (regisOrLogin) {
            case 1:
                registrasi();
                break;
            case 2:
                login();
                break;
            default:
                menu();
                break;

        }
    }

}


// 3. CLASS MAIN: Login & Menu Utama
public class GamesMenu {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        GameLibrary myGames = new GameLibrary();

        // Mengisi Data Linked List (Pre-installed Games)
        myGames.tambahGame("Pacman");
        myGames.tambahGame("Sukimon");
        myGames.tambahGame("Rusian Roulate");

        // --- BAGIAN 1: LOGIN ---
        System.out.println("################################");
        System.out.println("#        SUKI LAUNCHER         #");
        System.out.println("################################");

        AkunManager.menu();

        

        // boolean isLogged = false;
        // while (!isLogged) {
        // System.out.print("Username : ");
        // String user = scanner.nextLine();
        // System.out.print("Password : ");
        // String pass = scanner.nextLine();

        // // Validasi Login (User: gamer, Pass: main123)
        // if (user.equals("Salman") && pass.equals("momon") || user.equals("Adit") &&
        // pass.equals("ditya")) {
        // isLogged = true;
        // System.out.println("\nLogin Berhasil! Selamat datang, " + user + ".");
        // } else {
        // System.out.println("[!] Username atau Password salah. Coba lagi.\n");
        // }
        // }

        // --- BAGIAN 2: MENU UTAMA ---
        boolean running = true;
        while (running) {
            System.out.println("\n------- MAIN MENU -------");
            System.out.println("1. Lihat Library Game");
            System.out.println("2. Mainkan Game (Pilih Game)");
            System.out.println("3. Logout / Keluar");
            System.out.print("Pilih menu [1-3]: ");

            String input = scanner.nextLine(); // Menggunakan nextLine agar buffer aman

            switch (input) {
                case "1":
                    myGames.tampilkanLibrary();
                    break;
                case "2":
                    myGames.tampilkanLibrary();
                    System.out.print("\nPilih nomor game yang ingin dimainkan: ");
                    try {
                        int pilih = Integer.parseInt(scanner.nextLine());
                        myGames.mainkanGame(pilih);
                    } catch (NumberFormatException e) {
                        System.out.println("[!] Input harus berupa angka.");
                    }
                    break;
                case "3":
                    System.out.println("Logging out... Sampai jumpa!");
                    running = false;
                    break;
                default:
                    System.out.println("[!] Pilihan tidak valid.");
            }
        }
        scanner.close();
    }
}