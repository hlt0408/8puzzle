public class Board {
    
    private final int[][] board;
    private final int dim;
    
    public Board(int[][] blocks) {          // construct a board from an N-by-N array of blocks
        dim = blocks.length;                // (where blocks[i][j] = block in row i, column j)
        board = clone(blocks);           
    }
    
    private int[][] clone(int[][] blocks) {
        int[][] res = new int[dim][dim];
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                res[i][j] = blocks[i][j];
            }
        }
        return res;
    }
        
                                           
    public int dimension() {                // board dimension N
        return dim;
    }
    
    public int hamming() {                  // number of blocks out of place
        int result = 0;
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if ((board[i][j] != 0) && (board[i][j] != dim * i + j + 1)) result++;
            }
        }
        return result;
    }
    
    public int manhattan() {                // sum of Manhattan distances between blocks and goal
        int result = 0;
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (board[i][j] != 0) {
                    result += Math.abs((board[i][j] - 1) / dim - i) + Math.abs((board[i][j] - 1) % dim - j);
                }
            }
        }                   
        return result;
    }
    
    public boolean isGoal() {               // is this board the goal board?
        return hamming() == 0;
            }   
    
    private void swap(int[][] al, int i, int j, int it, int jt) {        
        int temp = al[i][j];
        al[i][j] = al[it][jt];
        al[it][jt] = temp;        
    }
    
    public Board twin() {                   // a board that is obtained by exchanging two adjacent blocks in the same row        
        int[][] twarray = clone(board);
                
        if (twarray[0][0] != 0 && twarray[0][1] != 0) { swap(twarray, 0, 0, 0, 1);            
        } else {            
            swap(twarray, 1, 0, 1, 1);                                 
        }
        Board tw = new Board(twarray);
        return tw;
    }
    
    public boolean equals(Object y) {       // does this board equal y?
        if (this == y) return true;
        if (y == null || this.getClass() != y.getClass()) return false;
        Board that = (Board) y;
        if (this.dim != that.dim) return false;
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (this.board[i][j] != that.board[i][j]) return false;
            }
        }
        return true;
    }
    
    public Iterable<Board> neighbors() {    // all neighboring boards
        int row = 0; // row for zero
        int col = 0; // col for zero
        boolean found = false;
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (board[i][j] == 0) {
                    found = true;
                    row = i;
                    col = j;
                    break;
                }
            }
        if (found) break;
        }
            
        Stack<Board> sk = new Stack<>(); 
        int[][] cp = clone(board);
        
        if (row > 0) {            
            swap(cp, row, col, row - 1, col);            
            Board bd = new Board(cp);
            sk.push(bd);
            swap(cp, row, col, row - 1, col);  
        }
        
        if (row < dim - 1) {            
            swap(cp, row, col, row + 1, col);            
            Board bd = new Board(cp);
            sk.push(bd);
            swap(cp, row, col, row + 1, col);  
        }
        
        if (col > 0) {            
            swap(cp, row, col, row, col - 1);            
            Board bd = new Board(cp);
            sk.push(bd);
            swap(cp, row, col, row, col - 1);  
        }
        
        if (col < dim - 1) {            
            swap(cp, row, col, row, col + 1);            
            Board bd = new Board(cp);
            sk.push(bd);
            swap(cp, row, col, row, col + 1);  
        }
            
        return sk;
        
    }
    
    public String toString() {               // string representation of this board (in the output format specified below)   
        StringBuilder s = new StringBuilder();
        s.append(dim + "\n");
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                s.append(String.format("%2d ", board[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    public static void main(String[] args) { // unit tests (not graded)
        int[][] blocks = { {8, 1, 3}, 
                           {4, 0, 2},
                           {7, 6, 5}
        };
        /*int[][] blocks = { {1, 2, 3}, 
                          {4, 5, 6},
                          {7, 8, 9}
                         };*/        
        Board bd = new Board(blocks);  
        StdOut.println(bd);
        
        StdOut.println("bd dimention: " + bd.dimension());
        StdOut.println("bd hamming: " + bd.hamming());
        StdOut.println("bd manhattan: " + bd.manhattan());
        StdOut.println("is bd goal? " + bd.isGoal());                                       
        Board ce = new Board(blocks);
        StdOut.println("bd == ce? " + (bd == ce));
        StdOut.println("bd equals ce? " + bd.equals(ce));  
        StdOut.println(bd.twin());
        StdOut.println("bd == twin? " + (bd == bd.twin()));        
        StdOut.println("bd equals twin? " + bd.equals(bd.twin()));
        
        StdOut.println(bd.neighbors());
        
        
        
        
    }
}
