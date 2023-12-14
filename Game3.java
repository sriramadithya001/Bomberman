import java.util.Scanner;
import java.util.Random;

public class Game3 {

    static int n,m,w;
    static int[][] board = new int[100][100];
    
    public static void printBoard() {
        for(int i=0;i<n;i++) {
            for(int j=0;j<m;j++) System.out.print(board[i][j] + " ");
            System.out.println();
        }
    }
    
    public static void clearBoard() {
        for(int i=0;i<n;i++) for(int j=0;j<m;j++) board[i][j] = 0;
        for(int i=0;i<3;i++) insertFirst();
    }
    
    public static void insertFirst() {
        int r = new Random().nextInt(n),c = new Random().nextInt(m);
        if(board[r][c] == 0) {
            board[r][c] = 2;
            return;
        }
        insertFirst();
    }
    
    public static void insertTwo(int option) {
        if(option < 1 || option > 4) return;
        Random rand = new Random();
        int r = 0,c = 0;
        while(true) {
            r = rand.nextInt(n);c = rand.nextInt(m);
            if(board[r][c] == 0) {
                if(option == 1 && c==m-1) break;
                if(option == 2 && c==0) break;
                if(option == 3 && r==n-1) break;
                if(option == 4 && r==0) break; 
            }
        }
        board[r][c] = 2;
    }
    
    public static void moveLeft() {
        for(int r=0;r<n;r++) {
            int[] update = new int[m];boolean[] merged = new boolean[m];
            for(int c=0,k=0;c<m;c++) {
                if(board[r][c] == 0) continue;
                if(k-1 >= 0 && update[k-1]==board[r][c] && merged[k-1]!=true) {
                    merged[k-1] = true;
                    update[k-1] += board[r][c];
                }
                else update[k++] = board[r][c];
            }
            for(int u=0;u<m;u++) board[r][u] = update[u];
        }
    }
    
    public static void moveRight() {
        for(int r=0;r<n;r++) {
            int[] update = new int[m];boolean[] merged = new boolean[m];
            for(int c=m-1,k=m-1;c>=0;c--) {
                if(board[r][c] == 0) continue;
                if(k+1 <= m-1 && update[k+1]==board[r][c] && merged[k+1]!=true) {
                    merged[k+1] = true;
                    update[k+1] += board[r][c];
                }
                else update[k--] = board[r][c];
            }
            for(int u=0;u<m;u++) board[r][u] = update[u];
        }

    }
    
    public static void moveUp() {
        for(int c=0;c<m;c++) {
            int[] update = new int[n];boolean[] merged = new boolean[n];
            for(int r=0,k=0;r<n;r++) {
                if(board[r][c] == 0) continue;
                if(k-1 >= 0 && update[k-1]==board[r][c] && merged[k-1]!=true) {
                    merged[k-1] = true;
                    update[k-1] += board[r][c];
                }
                else update[k++] = board[r][c];
            }
            for(int u=0;u<n;u++) board[u][c] = update[u];
        }
    }
    
    public static void moveDown() {
        for(int c=0;c<m;c++) {
            int[] update = new int[n];boolean[] merged = new boolean[n];
            for(int r=n-1,k=n-1;r>=0;r--) {
                if(board[r][c] == 0) continue;
                if(k+1 <= n-1 && update[k+1]==board[r][c] && merged[k+1]!=true) {
                    merged[k+1] = true;
                    update[k+1] += board[r][c];
                }
                else update[k--] = board[r][c];
            }
            for(int u=0;u<n;u++) board[u][c] = update[u];
        }
   
    }
    
    public static boolean checkWin() {
        for(int i=0;i<n;i++) for(int j=0;j<m;j++) if(board[i][j] == w) return true;
        return false;
    }
    
    public static boolean checkLose() {
        for(int i=0;i<n;i++) for(int j=0;j<m;j++) if(board[i][j] == 0) return false;
        return true;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter row : ");
        n = sc.nextInt();
        System.out.print("Enter col : ");
        m = sc.nextInt();
        System.out.print("Set win number : ");
        w = sc.nextInt();
        for(int i=0;i<3;i++) insertFirst();
        printBoard();
        boolean loop = true;
        while(loop) {
            System.out.print("1. Left\n2. Right\n3. Up\n4. Down\n5. stop\noption : ");
            int option = sc.nextInt();
            switch(option) {
                case 1:
                    moveLeft();
                    break;
                case 2:
                    moveRight();
                    break;
                case 3:
                    moveUp();
                    break;
                case 4:
                    moveDown();
                    break;
                case 5:
                    loop = false;
                    System.out.println("Game stopped");
                    System.exit(0);
                default:
                    System.out.println("Enter a valid option");
            }
            if(checkWin()) {
                printBoard();
                System.out.println("Game Won");
              
                System.out.print("Do you want to restart the game (Y/N) : ");
                char choice = sc.next().charAt(0);
                if(choice == 'Y') {
                    clearBoard();
                    printBoard();
                    continue;
                    
                } else break;
            }
            else if(checkLose()) {
                printBoard();
                System.out.println("Game lost");
                
                System.out.print("Do you want to restart the game (Y/N) : ");
                char choice = sc.next().charAt(0);
                if(choice == 'Y') {
                    clearBoard();
                    printBoard();
                    continue;
                } else break;

            } 
            insertTwo(option);           
            printBoard();
        }
    }
}
