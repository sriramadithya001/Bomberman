import java.util.Scanner;

public class Boat {


    static int N,src_row,src_col,des_row,des_col;
    static int[][] grid = new int[100][100];
    static boolean[][] vis = new boolean[100][100];
    static int[] row = {-1,1,0,0,-1,-1,1,1};
    static int[] col = {0,0,-1,1,-1,1,-1,1};
    
    public static boolean f(int src_row,int src_col,int des_row,int des_col) {
        if(src_row == des_row && src_col == des_col) return true;
        
        if(src_row < 0 || src_col < 0 || src_row > N-1 || src_col > N-1 || vis[src_row][src_col]) return false;
        
        for(int d=0;d<8;d++) if(grid[src_row][src_col]==0 && src_row+row[d] >= 0 && src_col+col[d] >= 0 && src_row+row[d] <= N-1 && src_col+col[d] <= N-1 && grid[src_row+row[d]][src_col+col[d]] == 2) return false;
        
        vis[src_row][src_col] = true;
        
        boolean reach = false;
        
        for(int d=0;d<4;d++)  reach |= f(src_row+row[d],src_col+col[d],des_row,des_col);
        
        return reach;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();src_row = sc.nextInt();src_col = sc.nextInt();des_row = sc.nextInt();des_col = sc.nextInt();
        for(int i=0;i<N;i++) for(int j=0;j<N;j++) grid[i][j] = sc.nextInt();
        boolean res = f(src_row,src_col,des_row,des_col);
        System.out.println(res);
    }
}