import java.util.Scanner;
import java.util.Queue;
import java.util.LinkedList;
import java.util.ArrayList;
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

class Snake {
    static int id;
    int snake_id;
    int head;
    int tail;

    Snake(int head,int tail) {
        this.snake_id = ++id;
        this.head = head;
        this.tail = tail;
    }
}

class Ladder {
    static int id;
    int ladder_id;
    int down;
    int up;

    Ladder(int down,int up) {
        this.ladder_id = ++id;
        this.down = down;
        this.up = up;
    }
}

public class SnakeLadderGame {

    static int[][] board = new int[10][10];
    static Queue<Player> players = new LinkedList<>();
    static ArrayList<Snake> snakes = new ArrayList<>();
    static ArrayList<Ladder> ladders = new ArrayList<>();
    static HashMap<Integer,Integer> snake_pos = new HashMap<>();
    static HashMap<Integer,Integer> ladder_pos = new HashMap<>();
    static Random rand = new Random();

    public static void addPlayers() {
        for(int i=0;i<2;i++) players.add(new Player());
    }

    public static void addSnakes() {
        for(int i=0;i<5;) {
            int head = rand.nextInt(100) + 1 , tail = rand.nextInt(100) + 1;
            if(head > tail && head != 100 && snake_pos.containsKey(head) == false && snake_pos.containsValue(head) == false) {
                snakes.add(new Snake(head,tail));
                snake_pos.put(head,tail);
                i++;
            }
        }
    }

    public static void addLadders() {
        for(int i=0;i<5;) {
            int down = rand.nextInt(100) + 1 , up = rand.nextInt(100) + 1;
            if(down < up && up != 100 && ladder_pos.containsKey(down) == false && snake_pos.containsKey(down) == false) {
                ladders.add(new Ladder(down,up));
                ladder_pos.put(down,up);
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

            if(snake_pos.containsKey(new_pos)) {
                System.out.println("Sanke bite at " + new_pos);
                new_pos = snake_pos.get(new_pos);
            }
            else if(ladder_pos.containsKey(new_pos)) {
                System.out.println("Climbing ladder at " + new_pos);
                new_pos = ladder_pos.get(new_pos);
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

    public static boolean printSnake(int val) {
        for(Snake s : snakes) {
            if(s.head == val || s.tail == val) {
                System.out.print("[s" + s.snake_id + "]");
                return true;
            }
        }
        return false;
    }

    public static boolean printLadder(int val) {
        for(Ladder l : ladders) {
            if(l.down == val || l.up == val) {
                System.out.print("[l" + l.ladder_id + "]");
                return true;
            }
        }
        return false;
    }

    public static void printBoard() {
        for(int i=0;i<10;i++) {
            for(int j=0;j<10;j++) {
                if(printPlayers(board[i][j])) continue;
                if(printSnake(board[i][j])) continue;
                if(printLadder(board[i][j])) continue;
                System.out.print(board[i][j] + " ");

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

/*
    board - interface
    queue - players
    hashmap - player update
    arralist<obj> - printing obj(obj.id,obj.head,obj.tail,obj.down,obj.up)    // easy for printing
    
*/

