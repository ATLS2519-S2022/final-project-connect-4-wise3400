/**
 * A Connect-4 player that makes Minimax moves with alpha beta pruning.
 * 
 * @author Daniel Szafir / Wilson Seet
 *
 */
public class AlphaBeta implements Player
{
    private static java.util.Random rand = new java.util.Random();
    int id;
    int cols;
    
  @Override
    public String name() {
        return "Alpha Beta with Pruning Player";
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
        int myColScore;
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;
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
            myColScore = alphabeta(3-id,board, maxDepth-1, alpha,beta,arb);
		    
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
  

	//Minimax with Alpha-Beta Pruning Pseudocode
	
	//function alphabeta(node, depth, α, β, maximizingPlayer): 
		//if depth = 0 or node is a terminal node then
		//return the heuristic value of node
		//if maximizingPlayer then
		//value = −∞
		//for each child of node do
		//value = max(value, alphabeta(child, depth − 1, α, β, FALSE)) 
		//α = max(α, value)
		//if α ≥ β then
		//break /* β cut-off */
		//return value
		//else
		//value = +∞
		//for each child of node do
		//value = min(value, alphabeta(child, depth − 1, α, β, TRUE)) 
		//β = min(β, value)
		//if α ≥ β then
		//break /* α cut-off */
		//return value

  //function alphabeta(node, depth, α, β, maximizingPlayer): 
  public int alphabeta(int player, Connect4Board brd, int depth, int alpha, int beta, Arbitrator arb)
  {
    // if(game over in current board position)
	//if depth = 0 or node is a terminal node then
    if(brd.isFull() || depth == 0 || arb.isTimeUp())
    {
         //return winner
    	 //return the heuristic value of node
    	 return calcScore(brd,id) - calcScore(brd, 3-id);
    }

    // children = all legal moves for player from this board
   // if(max's turn)
  //if maximizingPlayer then
    if (player == 1)
    {
      //value = −∞
      int maxChild = Integer.MIN_VALUE;
      
      //for each child of node do
      for (int col = 0; col < 7; col++)
      {
        if (brd.isValidMove(col))
        {
          brd.move(col,player);
          //value = max(value, alphabeta(child, depth − 1, α, β, FALSE)) 
          int child = alphabeta(2,brd,depth-1,alpha,beta, arb); //modified
          //α = max(α, value)
          brd.unmove(col, player); //modified
          if(child > maxChild)
          {
        	  maxChild = child;
          }
          if(maxChild > alpha)
          {
        	  alpha = maxChild;
          }
        //if α ≥ β then
          if (alpha >= beta) //modified
          {
        	//break /* β cut-off */
            break;
          }
        
      }
      }
    //return value
    return maxChild;
    }
    else
    {
      //else (min's turn)
      //value = +∞
      int minChild = Integer.MAX_VALUE;
      //return maximal score of calling minimax on all the children    
      //for each child of node do
      for (int col = 0; col < 7; col++)
      {
        if (brd.isValidMove(col))
        {
          brd.move(col,player);
          //value = min(value, alphabeta(child, depth − 1, α, β, TRUE)) 
          int child = alphabeta(1,brd,depth-1,alpha,beta, arb); //modified
          //β = min(β, value)
          brd.unmove(col, player); //modified
        
          if(child < minChild) //check
          {
        	  minChild = child;
          }
          if(minChild < beta)
          {
        	  beta = minChild;
          }
         //if α ≥ β then
         if (alpha >= beta) // modified
          {
        	//break /* α cut-off */
            break;
          }
        }
      }
      //return value
      return minChild;   
      }   
}
}

  
