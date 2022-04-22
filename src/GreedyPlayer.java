/**
 * A Connect-4 player that makes Greedy moves.
 * 
 * @author Daniel Szafir / Wilson Seet
 *
 */
public class GreedyPlayer implements Player
{
    private static java.util.Random rand = new java.util.Random();
    int id;
    int cols;
    @Override
    public String name() {
        return "Randy";
    }

    @Override
    public void init(int id, int msecPerMove, int rows, int cols) {
    	this.id = id; 
    	this.cols = cols;
    }

    @Override
    public void calcMove(
        Connect4Board board, int oppMoveCol, Arbitrator arb) 
        throws TimeUpException {
        // Make sure there is room to make a move.
        if (board.isFull()) {
            throw new Error ("Complaint: The board is full!");
        }
       
        //THE GREEDY PLAYER!!
        //Find maximum score from all possible moves
        int col = 0;
        int bestScore = -1000;
        int bestCol = 0;
        int myColScore = 0;
             
         //For each move:
		 for (col = 0; col < cols; col++) 
          {
			if (board.isValidMove(col))
			{
			// Temporarily make the move using board.move()
            board.move(col, this.id);
           
            //Calculate a score based on how the board is for you now that you've made the move
            myColScore = calcScore(board,id) - calcScore(board, 3-id);
            
            if (myColScore > bestScore)
            {
              bestScore = myColScore;
              bestCol = col;
            }
            //Undo the move using board.unmove 
            board.unmove(col, this.id);
			}
          }
		//Return the move that had the highest calculated score
        arb.setMove(bestCol);
        
        
    }


//Return the number of connect-4s that player #id has.
	public int calcScore(Connect4Board board, int id)
	{
		final int rows = board.numRows();
		final int cols = board.numCols();
		int score = 0;
		// Look for horizontal connect-4s.
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c <= cols - 4; c++) {
				if (board.get(r, c + 0) != id) continue;
				if (board.get(r, c + 1) != id) continue;
				if (board.get(r, c + 2) != id) continue;
				if (board.get(r, c + 3) != id) continue;
				score++;
			}
		}
		// Look for vertical connect-4s.
		for (int c = 0; c < cols; c++) {
			for (int r = 0; r <= rows - 4; r++) {
				if (board.get(r + 0, c) != id) continue;
				if (board.get(r + 1, c) != id) continue;
				if (board.get(r + 2, c) != id) continue;
				if (board.get(r + 3, c) != id) continue;
				score++;
			}
		}
		// Look for diagonal connect-4s.
		for (int c = 0; c <= cols - 4; c++) {
			for (int r = 0; r <= rows - 4; r++) {
				if (board.get(r + 0, c + 0) != id) continue;
				if (board.get(r + 1, c + 1) != id) continue;
				if (board.get(r + 2, c + 2) != id) continue;
				if (board.get(r + 3, c + 3) != id) continue;
				score++;
			}
		}
		for (int c = 0; c <= cols - 4; c++) {
			for (int r = rows - 1; r >= 4 - 1; r--) {
				if (board.get(r - 0, c + 0) != id) continue;
				if (board.get(r - 1, c + 1) != id) continue;
				if (board.get(r - 2, c + 2) != id) continue;
				if (board.get(r - 3, c + 3) != id) continue;
				score++;
			}
		}
		return score;
	}
	
}