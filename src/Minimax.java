/**
 * A Connect-4 player that makes random Minimax moves.
 * 
 * @author Daniel Szafir / Wilson Seet
 *
 */
public class Minimax implements Player
{
    private static java.util.Random rand = new java.util.Random();
    int id;
    int cols;
    
  @Override
    public String name() {
        return "MiniMax Player";
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
      //Find maximum score from all possible moves

        int col = 0;
        
        int bestScore = 0;
        int bestCol = 0;
        int myColScore = 0;
        //Initialize a maximum search depth to be 1
        int maxDepth = 1;
       
        	
        //While there is time remaining to calculate your move (you can check this with the arb.isTimeUp() method) and your current search depth is <= the number of moves remaining (you can check this with the board.numEmptyCells() method):
        while(!arb.isTimeUp() && maxDepth <= board.numEmptyCells()) {
        	bestScore = -1000;
         //Do a minimax search to the depth of your maximum search variable	
		 for (col = 0; col < cols; col++) 
          {
			 //CHECK BOARD TO SEE IF THERE IS VALID MOVE.
			 //Calculate a score based on how the board is for you now that you've made the move
			if (board.isValidMove(col))
		    {
	            board.move(col, this.id);
	            myColScore = Minimax(3-id,board, maxDepth-1, arb);
		    
	            if (myColScore > bestScore)
	            {
	              bestScore = myColScore;
	              bestCol = col;
	            }
	            board.unmove(col, this.id);
		     }

          }
		//Set your move as the best move found so far
        arb.setMove(bestCol);
      //Increment your maximum search depth
        maxDepth++;
        }
        
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
  
	
	//PSEUDOCODE for MiniMax
	//function minimax(node, depth, maximizingPlayer): 
		//if depth = 0 or node is a terminal node then
		//return the heuristic value of node 
		//if maximizingPlayer then
		//value := −∞
		//for each child of node do
		//value := max(value, minimax(child, depth − 1, FALSE)) 
		//return value
		//else /* minimizing player */ 
		//value := +∞
		//for each child of node do
		//value := min(value, minimax(child, depth − 1, TRUE)) 
		//return value
	
  public int Minimax(int player, Connect4Board brd, int depth, Arbitrator arb)
  {
    // if(game over in current board position)
	//if depth = 0 or node is a terminal node then
    if(brd.isFull() || depth == 0 || arb.isTimeUp())
    {
      //return the heuristic value of node 
      return calcScore(brd,id) - calcScore(brd, 3-id);
    }

     // children = all legal moves for player from this board
    // if(max's turn)
    //if maximizingPlayer then
    if (player == 1)
    {
      //value := −∞
      int maxChild = Integer.MIN_VALUE;
      //return maximal score of calling minimax on all the children 
      //for each child of node do
      for (int col = 0; col < 7; col++)
      {
        if (brd.isValidMove(col))
        {
          brd.move(col,player);
          //value := max(value, minimax(child, depth − 1, FALSE)) 
          int child = Minimax(2,brd,depth-1,arb);
          brd.unmove(col, player);
          if (child > maxChild)
          {
            maxChild = child;
          }
        
      }
    //return value
    return maxChild;
      }
    }
	//else /* minimizing player */ 
    else
    {
      //else (min's turn)
      //value := +∞
      int minChild = Integer.MAX_VALUE;
      //return maximal score of calling minimax on all the children  
      //for each child of node do
      for (int col = 0; col < 7; col++)
      {
        if (brd.isValidMove(col))
        {
          brd.move(col,player);
          //value := min(value, minimax(child, depth − 1, TRUE)) 
          int child = Minimax(1,brd,depth-1,arb);
          brd.unmove(col, player);
          if (child < minChild)
          {
            minChild = child;
          }
        }
      }
     //return value
      return minChild;   
      }
        
    return 0;
  
    
}
}

  
