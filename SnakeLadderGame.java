import java.util.Scanner;
import java.util.Queue;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.Random;

class Player {
    static char id = 'A';
    char name;
    int pos;

    Player() {
        this.name = id++;
        this.pos = 1;
    }
}

public class SnakeLadderGame {

    static int[][] board = new int[10][10];
    static Queue<Player> players = new LinkedList<>();
    static HashMap<Integer,Integer> snakes = new HashMap<>();
    static HashMap<Integer,Integer> ladders = new HashMap<>();
    static Random rand = new Random();

    public static void addPlayers() {
        for(int i=0;i<2;i++) players.add(new Player());
    }

    public static void addSnakes() {
        for(int i=0;i<10;) {
            int head = rand.nextInt(100) + 1 , tail = rand.nextInt(100) + 1;
            if(head > tail && head != 100 && snakes.containsKey(head) == false && snakes.containsValue(head) == false) {
                snakes.put(head,tail);
                i++;
            }
        }
    }

    public static void addLadders() {
        for(int i=0;i<10;) {
            int down = rand.nextInt(100) + 1 , up = rand.nextInt(100) + 1;
            if(down < up && up != 100 && ladders.containsKey(down) == false && snakes.containsKey(down) == false) {
                ladders.put(down,up);
                i++;
            }
        }
    }

    public static int rollDice() {
        return rand.nextInt(6) + 1;
    }

    public static void playGame() {
        while(!players.isEmpty()) {
            Player player = players.poll();

            System.out.println("name : " + player.name);
            System.out.println("old pos : " + player.pos);

            int new_pos = player.pos + rollDice();

            if(snakes.containsKey(new_pos)) {
                System.out.println("Sanke bite at " + new_pos);
                new_pos = snakes.get(new_pos);
            }
            else if(ladders.containsKey(new_pos)) {
                System.out.println("Climbing ladder at " + new_pos);
                new_pos = ladders.get(new_pos);
            }

            if(new_pos <= 100) {
                player.pos = new_pos;
                System.out.println("new pos : " + player.pos);
            }

            if(player.pos == 100) {
                System.out.println("Player " + player.name + " won");
            } else {
                players.add(player);
            }

            printBoard();
        }
        System.out.println("Game ends");
    }

    public static boolean printPlayers(int val) {
        boolean flag = false;
        for(Player p : players) {
            if(p.pos == val) {
                System.out.print(p.name);
                flag = true;
            }
        }
        System.out.print(" ");
        return flag;
    }

    public static void printBoard() {
        for(int i=0;i<10;i++) {
            for(int j=0;j<10;j++) {
                if(!printPlayers(board[i][j])) System.out.print(board[i][j] + " ");
            }
            System.out.println("\n");
        }
    }

    public static void initializeBoard() {
        int val = 1;
        for(int r=9;r>=0;r--) {
            if(r%2 == 1) {
                for(int c=0;c<10;c++) board[r][c] = val++;
            } else {
                for(int c=9;c>=0;c--) board[r][c] = val++;
            }
        }
    }

    public static void main(String[] args) {
        addPlayers();
        addSnakes();
        addLadders();
        initializeBoard();
        printBoard();
        playGame();
    }
}