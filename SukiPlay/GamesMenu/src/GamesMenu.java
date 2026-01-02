package com.mycompany.gamesmenu;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

import java.util.Scanner;

// 1. CLASS NODE: Merepresentasikan satu Game
class GameNode {
    String judulGame;
    GameNode next; // Pointer ke game selanjutnya

    public GameNode(String judulGame) {
        this.judulGame = judulGame;
        this.next = null;
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

                PacmanApp pacmanGame = new PacmanApp();
                pacmanGame.playPacMan();
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

// 3. CLASS MAIN: Login & Menu Utama
public class GamesMenu {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GameLibrary myGames = new GameLibrary();

        // Mengisi Data Linked List (Pre-installed Games)
        myGames.tambahGame("Mobile Legends");
        myGames.tambahGame("PUBG Mobile");
        myGames.tambahGame("Minecraft");
        myGames.tambahGame("Genshin Impact");

        // --- BAGIAN 1: LOGIN ---
        System.out.println("################################");
        System.out.println("#        SUKI LAUNCHER         #");
        System.out.println("################################");
        
        boolean isLogged = false;
        while (!isLogged) {
            System.out.print("Username : ");
            String user = scanner.nextLine();
            System.out.print("Password : ");
            String pass = scanner.nextLine();

            // Validasi Login (User: gamer, Pass: main123)
            if (user.equals("Salman") && pass.equals("momon")) {
                isLogged = true;
                System.out.println("\nLogin Berhasil! Selamat datang, " + user + ".");
            } else {
                System.out.println("[!] Username atau Password salah. Coba lagi.\n");
            }
        }

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
