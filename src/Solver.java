public class Solver {    
    private Stack<Board> close; // container for result to trace back
    private SearchNode result; // result node
    private boolean issolvable;  
    
    private class SearchNode implements Comparable<SearchNode> {
    	private Board current;
    	private SearchNode previous;
    	private int moves; // moves counting
    	private int priority;
    	    	
    	private SearchNode(Board curr, SearchNode prev) {
    		current = curr;
    		previous = prev;
    		moves = (previous == null) ? 0 : previous.moves + 1;
    		priority = moves + current.manhattan();
    	}    	    
    	
    	public int compareTo(SearchNode that) {
    		return (this.priority - that.priority);
    	}
    	
    }    
    
    public Solver(Board initial) {          // find a solution to the initial board (using the A* algorithm)
    	if (initial == null) throw new NullPointerException("null argument");
    	MinPQ<SearchNode> pq = new MinPQ<>(); // pq: open set
    	MinPQ<SearchNode> pqtw = new MinPQ<>();
    	pq.insert(new SearchNode(initial, null));
    	pqtw.insert(new SearchNode(initial.twin(), null));
    	
    	while (true) {
    		SearchNode lp = pq.delMin(); // lp: lowest priority
    		SearchNode lptw = pqtw.delMin();    		    	
    		
    		if (lp.current.isGoal()) {
    			issolvable = true;
    			result = lp;
    			break;
    		}
    		
    		if (lptw.current.isGoal()) {
    			result = null;
    			issolvable = false;
    			break;
    		}
    		
    		for (Board neighbor : lp.current.neighbors()) {
    			if (lp.previous == null || !neighbor.equals(lp.previous.current)) {
    				pq.insert(new SearchNode(neighbor, lp));
    			}
    			
    		}
    		
    		for (Board neighbor : lptw.current.neighbors()) {
    			if (lp.previous == null || !neighbor.equals(lptw.previous.current)) {
    				pqtw.insert(new SearchNode(neighbor, lptw));
    			}    			    	    		
    		}
    		
    	}
        
    }	     
    
    
        
    public boolean isSolvable() {           // is the initial board solvable?
        return issolvable;
    }
        
    public int moves() {                    // min number of moves to solve initial board; -1 if unsolvable
        if (isSolvable()) {
        	return result.moves;
        }
        else return -1;        
    }
        
    public Iterable<Board> solution() {     // sequence of boards in a shortest solution; null if unsolvable
    	if (!isSolvable()) return null;
    	close = new Stack<Board>();
    	SearchNode node = result;
    	while (node.previous != null) {
    		close.push(node.current);
    		StdOut.println("priority: " + node.priority);
    		node = node.previous;
    	}
    	close.push(node.current);
    	StdOut.println("priority: " + node.priority);
        return close;
    }
        
    public static void main(String[] args) {

        // create initial board from file
    	String filename = "testcase/puzzle2x2-unsolvable3.txt";     	
        In in = new In(filename);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
            blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}