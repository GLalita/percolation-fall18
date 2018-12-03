
public class PercolationUF implements IPercolate {
	
	boolean[][] myGrid;
	IUnionFind myFinder = new QuickUWPC();
	int myOpenCount;
	private final int VBOTTOM;
	private final int VTOP;

	PercolationUF(int size, IUnionFind finder){

	myGrid = new boolean[size][size]; 
	myFinder = finder;
	myFinder.initialize(size*size + 2);
	myOpenCount = 0;
	VTOP = size*size;
	VBOTTOM = size*size + 1;
	
	}
	
	@Override
	public void open(int row, int col) {
		if (! inBounds(row,col)) {
			throw new IndexOutOfBoundsException(
					String.format("(%d,%d) not in bounds", row,col));
		}
		
		if (isOpen(row, col)) {
			return;
		}
		
		myGrid[row][col] = true;
		myOpenCount++;
		
        int[] rowDelta = {-1,1,0,0};
        int[] colDelta = {0,0,-1,1};
        for(int k = 0; k < rowDelta.length; k++) {
        	int rowSide = row + rowDelta[k];
        	int colSide = col + colDelta[k];
        	
    		if (inBounds(rowSide, colSide) && isOpen(rowSide, colSide)) {
    			myFinder.union(intValue(row, col), intValue(rowSide, colSide));
    		}
        }
        
        if(row == 0) {
        	myFinder.union(intValue(row,col), VTOP);
        }
        if(row == myGrid.length - 1) {
        	myFinder.union(intValue(row,col), VBOTTOM);
        }
	}

	@Override
	public boolean isOpen(int row, int col) {
		if (! inBounds(row,col)) {
			throw new IndexOutOfBoundsException(
					String.format("(%d,%d) not in bounds", row,col));
		}
		return myGrid[row][col];
	}

	@Override
	public boolean isFull(int row, int col) {
		if (! inBounds(row,col)) {
			throw new IndexOutOfBoundsException(
					String.format("(%d,%d) not in bounds", row,col));
		}
		if (myFinder.connected(intValue(row, col), VTOP)) {
			return true;
		}
		return false;
	}

	@Override
	public boolean percolates() {
		if (myFinder.connected(VTOP,VBOTTOM)) {
			return true;
		}

		return false;
	}

	@Override
	public int numberOfOpenSites() {

		return myOpenCount;
	}
	
	public int intValue(int row, int col) {
		return row*myGrid[0].length + col;
	}
	
	public boolean inBounds(int row, int col) {
		if (row < 0 || row >= myGrid.length) return false;
		if (col < 0 || col >= myGrid[0].length) return false;
		return true;
	}
	
}
