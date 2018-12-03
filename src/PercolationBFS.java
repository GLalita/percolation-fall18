import java.util.LinkedList;
import java.util.Queue;

public class PercolationBFS extends PercolationDFSFast {

	public PercolationBFS(int n) {
		super(n);
	}
	
	@Override
	protected void dfs(int row, int col) {
		// out of bounds?
		if (! inBounds(row,col)) return;
		
        int[] rowDelta = {-1,1,0,0};
        int[] colDelta = {0,0,-1,1};
        
		int size = myGrid[0].length;
		Queue<Integer> q = new LinkedList<>();
		
		myGrid[row][col] = FULL;
		q.add(value(row, col));
		
		while(q.size() != 0) {
			Integer v = q.remove();
            for(int k=0; k < rowDelta.length; k++){
                row = v/size + rowDelta[k];
                col = v%size + colDelta[k];
                
                //If the neighboring cell is open and not full, fill it!
                if (inBounds(row,col) && myGrid[row][col] == OPEN) {
                	myGrid[row][col] = FULL;
                	q.add(value(row, col));
                }
            }
		}
            
	}
	
	protected int value(int row, int col) {
		return row*myGrid[0].length + col;
	}

}
