import java.util.Scanner;
import java.util.Random;

public class Bomberman {
    static Scanner sc = new Scanner(System.in);

    static boolean loop = true;
    static int N;
    static char[][] board = new char[100][100];
    static int b1 = -1,b2 = -1,cnt = 3;
    
    static int p1,p2,k1,k2;
    
    public static void printBoard() {
        for(int r=0;r<N;r++) {
            for(int c=0;c<N;c++) System.out.print(board[r][c] + " ");
            System.out.println();
        }
    }
    
    public static void initialize() {
    
        System.out.print("Enter the size of board : ");
        N = sc.nextInt();
        
        for(int r=0;r<N;r++) for(int c=0;c<N;c++) board[r][c] = ' ';

    
        for(int c=1,ch=65;c<N;c++,ch++) board[0][c] = (char) ch;         
        for(int r=1,ch=65;r<N;r++,ch++) board[r][0] = (char) ch;         
        
        for(int r=0;r<N;r++) {
            for(int c=0;c<N;c++) {
                if (r == 0 && c == 0) continue;    
                if ((r == 1 && c >= 1) || (c == 1 && r >= 1) || (r == N - 1 && c >= 1) || (c == N - 1 && r >= 1) || (r >= 3 && r < N - 2 && c >= 3 && c < N - 2 && r % 2 != 0 && c % 2 != 0)) board[r][c] = '*';
            }
        }
        
        System.out.print("Enter player position : ");
        String p_pos = sc.next();
        System.out.print("Enter key position : ");
        String k_pos = sc.next();
        System.out.print("Enter villian positions (,) : ");
        String villians_pos = sc.next();
        String[] villians = villians_pos.split(",");
        System.out.print("Enter brick positions (,) : ");
        String bricks_pos = sc.next();
        String[] bricks = bricks_pos.split(",");
        
        
        // player
        p1 = (int) p_pos.charAt(0) - 'A' + 1;p2 = (int) p_pos.charAt(1) - 'A' + 1;
        board[p1][p2] = 'P';
        // key
        k1 = (int) k_pos.charAt(0) - 'A' + 1;k2 = (int) k_pos.charAt(1) - 'A' + 1;
        board[k1][k2] = 'K';
        // villians
        for(int v=0;v<villians.length;v++) board[(int) villians[v].charAt(0) - 'A' + 1][(int) villians[v].charAt(1) - 'A' + 1] = 'V';
        // bricks
        for(int b=0;b<bricks.length;b++) board[(int) bricks[b].charAt(0) - 'A' + 1][(int) bricks[b].charAt(1) - 'A' + 1] = 'B';
    }
    
    public static void play() {
        System.out.print("Enter direction [w,s,a,d] : ");
        char direction = sc.next().charAt(0);
        
        if(b1 !=-1 && b2 != -1) {
            cnt--;
            if(cnt == 0) {
                for(int i=0;i<3;i++) {
                    if(board[b1+i][b2] == 'V' || board[b1+i][b2] == 'B') board[b1+i][b2] = ' ';
                    if(board[b1-i][b2] == 'V' || board[b1+-i][b2] == 'B') board[b1-i][b2] = ' ';
                    if(board[b1][b2+i] == 'V' || board[b1][b2+i] == 'B') board[b1][b2+i] = ' ';
                    if(board[b1][b2-i] == 'V' || board[b1][b2-i] == 'B') board[b1][b2-i] = ' ';
                }
                cnt = 3;
                board[b1][b2] = ' ';
                b1 = -1;b2 = -1;
            }
        }
        
        
        switch(direction) {
            case 'w':
                if(p1>0 && board[p1-1][p2]==' ' || board[p1-1][p2]=='K') {
                    board[p1][p2] = ' ';
                    p1--;
                }
                break;
            case 's':
                if(p2<N-1 && board[p1+1][p2] == ' ' || board[p1+1][p2]=='K') {
                    board[p1][p2] = ' ';
                    p1++;
                }
                break;
            case 'a':
                if(p2>0 && board[p1][p2-1] == ' ' || board[p1][p2-1]=='K') {
                    board[p1][p2] = ' ';
                    p2--;
                }
                break;
            case 'd':
                if(p2<N-1 && board[p1][p2+1] == ' ' || board[p1][p2+1]=='K') {
                    board[p1][p2] = ' ';
                    p2++;
                }
                break;
            case 'x':
                if(p1>=0 && p2>=0 && p1<=N-1 && p2<=N-1) {
                    b1 = p1;b2 = p2;
                }
                break;
        }
        
        if(board[p1][p2] == 'K') {
            System.out.println("Game Won");
            loop = false;
        }
        
        board[p1][p2] = 'P';
        
        
        for(int r=0;r<N;r++) {
            for(int c=0;c<N;c++) {
                if(board[r][c] == 'V') {
                    moveVillian(r,c);
                }
            }
        }
        
        if(b1!=-1 && b2!=-1 && board[b1][b2] == ' ') board[b1][b2] = 'X';
        printBoard();
    }
    
    public static void moveVillian(int r,int c) {
        while(true) {
            Random rand = new Random();
            int n = rand.nextInt(4);
            int v1 = r, v2 = c;
            if(n == 0) v1--;
            else if(n == 1) v1++;
            else if(n == 2) v2--;
            else if(n == 3) v2++;
            else if(n==4) {v1--;v2--;}
            else if(n==5) {v1--;v2++;}
            else if(n==6) {v1++;v2--;}
            else if(n == 7) {v1++;v2++;}
            
            if(board[v1][v2] == 'P') {
                System.out.println("Game Lost");
                board[v1][v2] = 'V';
                loop = false;
                break;
            }
            if(board[v1][v2] == ' ') {
                board[r][c] = ' ';
                board[v1][v2] = 'V';
                break;
            }
        }
    }

    public static void main(String[] args) {
        initialize();        
        printBoard();
        while(loop) play();
    }
}