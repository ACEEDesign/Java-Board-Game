import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import java.util.concurrent.*;
/**
 * Write a description of class AI here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class AI
{
    // instance variables - replace the example below with your own
    private int x;
    private Rules rules;
    private boolean whiteActive;
    private ArrayList<Piece> pieces;
    private JPanel[][] board;
    private Move bestMove;
    private Game game;
    /**
     * Constructor for objects of class AI
     */
    public AI(boolean whiteActive, ArrayList<Piece> pieces, JPanel[][] board, Game game)
    {
        // initialise instance variables
        x = 0;
        rules = new Rules();
        whiteActive = this.whiteActive;
        pieces = this.pieces;
        board = this.board;
        game = this.game;
    }
    
    public void getMove()
    {
        
    }

    /**
     * An example of a method - replace this comment with your own
     * 
     * @param  y   a sample parameter for a method
     * @return     the sum of x and y 
     */
    public ArrayList<Piece> getActive(boolean whiteActive, ArrayList<Piece> pieces)
    {
        // put your code here
        String colour = " ";
        if (whiteActive == true)
        {
            colour = "White";
        }
        else 
        {
            colour = "Black";
        }
        ArrayList<Piece> activePieces = new ArrayList<Piece>();
        Iterator<Piece> itr = pieces.iterator();
        while (itr.hasNext())
         {
            Piece piece = itr.next();
            if (piece.getColour() == colour)
            {
                activePieces.add(piece);
            }
         }
        
        return activePieces;
    }
  
    public ArrayList<Move> getMoves(JPanel[][] board, ArrayList<Piece> active, boolean whiteActive, ArrayList<Piece> pieces)
    {
        Iterator<Piece> itr = active.iterator();
        ArrayList<Move> validMoves = new ArrayList<Move>();
        
        while (itr.hasNext())
        {
            Piece piece = itr.next();
            for (int y = 0; y < 8; y++)
            {
                for (int x = 0; x < 8; x++)
                {
                    if(rules.validMove(board, pieces, piece, piece.getX(), piece.getY(), x, y, whiteActive, false) == true)
                    {
                        ArrayList<Piece> tempPieces = pieces;
                        Move move = new Move(piece.getX(), piece.getY(), x, y);
                        System.out.println(piece.getType());
                        System.out.println("From: x:"+piece.getX()+" y:"+piece.getY());
                        System.out.println("To: x:"+x+" y:"+y);
                        move.increaseRank(1);
                        int value = piece.getValue();
                        int preKingMoves = kingMoves(tempPieces);
                        int oppTotalValuePre = getTotalValue(tempPieces, !whiteActive);
                        
                        // pre move
                        if(piece.getMoveCount() == 0)
                        {
                            move.increaseRank(1);
                            System.out.println("Has not moved +1");
                            if(piece.getType() == "Pawn")
                            {
                                move.increaseRank(1);
                                System.out.println("Pawn Has not moved +1");
                            }
                            else
                            {
                                move.increaseRank(2);
                                System.out.println("Not pawn +2");
                            }
                            // Makes AI more likely to move pieces not yet moved
                        }
                        if(y > 3)
                        {
                            move.increaseRank(2);
                            System.out.println("Board progression +2");
                            // Makes AI move to opponents end of the board
                        }
                        if((y == 3 && x == 3) || (y == 4 && x == 3) || (y == 3 && x == 4) || (y == 4 && x == 4))
                        {
                            move.increaseRank(2);
                            System.out.println("Middle control +2");
                            // makes AI hold middle squares
                        }

                        if(squareThreatened(pieces, piece.getX(), piece.getY(), whiteActive) == true)
                        {
                            // Makes AI move from threatened area
                            //int value = getPieceValue(pieces, piece.getX(), piece.getY());
                            move.increaseRank(value);
                            System.out.println("Move from threat +"+value);
                        }
                        if (getPieceColour(tempPieces, x, y) == "White")
                        {
                            int oppVal = getPieceValue(tempPieces, x, y);
                            move.increaseRank(oppVal);
                            System.out.println("Take +"+oppVal);
                        }
                        
                        
                        System.out.println("After move:");
                        tempPieces.remove(piece);
                        piece.changeX(x);
                        piece.changeY(y);
                        tempPieces.add(piece);
                        // post move
                        
                        int postKingMoves = kingMoves(tempPieces);
                        int oppTotalValuePost = getTotalValue(tempPieces, !whiteActive);
                        
                        if(oppTotalValuePre > oppTotalValuePost)
                        {
                            int movement = oppTotalValuePre - oppTotalValuePost;
                            System.out.println("Opponent value down +"+movement);
                            move.increaseRank(movement);
                        }
                        
                        
                        if(preKingMoves > postKingMoves)
                        {
                            System.out.println("King move reduction +2");
                            move.increaseRank(2);
                        }
                        
                        if (kingThreatened(tempPieces, whiteActive) == true)
                        {
                            System.out.println("Opponent check +3");
                            move.increaseRank(3);
                        }
                        if(squareThreatened(tempPieces, piece.getX(), piece.getY(), whiteActive) == true)
                        {
                            // Makes AI not move to threatened area
                            //int value = getPieceValue(pieces, move.getStartX(), move.getStartY());
                            move.decreaseRank(value*1.5);
                            System.out.println("Dont move to threat -"+(value*1.5));
                        }
                        if(squareThreatened(pieces, piece.getX(), piece.getY(), !whiteActive) == true)
                        {
                            // Makes AI not move to threatened area
                            //int value = getPieceValue(pieces, move.getStartX(), move.getStartY());
                            move.increaseRank(value);
                            System.out.println("Defended -"+(value));
                        }
                        
                        System.out.println("Rank: "+ move.getRank());
                        System.out.println("end");
                        
                        validMoves.add(move);
                        tempPieces.remove(piece);
                        piece.changeX(move.getStartX());
                        piece.changeY(move.getStartY());
                        tempPieces.add(piece);
                        
                    }
                }
            }
        }
        return validMoves;
    }
    
    public double getHighestRank(ArrayList<Move> moves)
    {
        Iterator<Move> itr = moves.iterator();
        double highestRank = 0;
        while (itr.hasNext())
        {
            Move move = itr.next();
            if (move.getRank() >= highestRank)
            {
                highestRank = move.getRank();
            }
        }
        return highestRank;
    }
    
    public int getPieceValue(ArrayList<Piece> pieces, int x, int y)
    {
        Iterator<Piece> itr = pieces.iterator();
        int value = 0;
        while (itr.hasNext())
        {
            Piece piece = itr.next();
            if (piece.getX() == x && piece.getY() == y)
            {
                value = piece.getValue();
                break;
            }
        }
        return value;
    }
    
    public boolean squareThreatened(ArrayList<Piece> pieces, int x, int y, boolean whiteActive)
    {
        ArrayList<Piece> opponents = getActive(!whiteActive, pieces);
        // gets list of opponents pieces
        boolean threat = false;
        Iterator<Piece> itr = opponents.iterator();
        while (itr.hasNext())
         {
            Piece piece = itr.next();
            if(rules.validMove(board, pieces, piece, piece.getX(), piece.getY(), x, y, !whiteActive, false) == true)
            {
                threat = true;
                // checks if any opponent piece threatens entered x & y
                break;
            }        
        }
        return threat;
    }
    
    public boolean kingThreatened(ArrayList<Piece> pieces, boolean whiteActive)
    {
        ArrayList<Piece> active = getActive(whiteActive, pieces);
        boolean threat = false;
        Iterator<Piece> itr = active.iterator();
        int kingX = 0;
        int kingY = 0;
        for (int y = 0; y < 8; y++)
        {
            for (int x = 0; x < 8; x++)
            {
                if(getPieceType(pieces, x, y) == "King" && getPieceColour(pieces, x, y) == "White")
                {
                    kingX = x;
                    kingY = y;
                    // System.out.println("King:"+kingX+" "+kingY);
                }
            }
        }   
        while (itr.hasNext())
         {
            Piece piece = itr.next();
            if(rules.validMove(board, pieces, piece, piece.getX(), piece.getY(), kingX, kingY, whiteActive, false) == true)
            {
                threat = true;
                break;
            }  
                  
        }
        return threat;
    }
    
    public boolean kingsDirection(ArrayList<Piece> pieces, boolean whiteActive)
    {
        ArrayList<Piece> opponents = getActive(!whiteActive, pieces);
        boolean threat = false;
        Iterator<Piece> itr = opponents.iterator();
        while (itr.hasNext())
         {
            Piece piece = itr.next();
            for (int y = 0; y < 8; y++)
            {
                for (int x = 0; x < 8; x++)
                {
                    if(rules.pieceMoves(board, pieces, piece, piece.getX(), piece.getY(), x, y, !whiteActive, false, piece.getMoveCount()) == true && getPieceType(pieces, x, y) == "King")
                    {
                        threat = true;
                        break;
                    }  
                }
            }      
        }
        return threat;
    }
    
    public String getPieceType(ArrayList<Piece> pieces, int x, int y)
    {
        Iterator<Piece> itr = pieces.iterator();
        String type = " ";
        while (itr.hasNext())
        {
            Piece piece = itr.next();
            if (piece.getX() == x && piece.getY() == y)
            {
                type = piece.getType();
                break;
            }
        }
        return type;
    }
    
    public String getPieceColour(ArrayList<Piece> pieces, int x, int y)
    {
        Iterator<Piece> itr = pieces.iterator();
        String colour = " ";
        while (itr.hasNext())
        {
            Piece piece = itr.next();
            if (piece.getX() == x && piece.getY() == y)
            {
                colour = piece.getColour();
                break;
            }
        }
        return colour;
    }
    
    public int kingMoves(ArrayList<Piece> pieces)
    {
        int kingX = 0;
        int kingY = 0;
        int count = 0;
        for (int y = 0; y < 8; y++)
        {
            for (int x = 0; x < 8; x++)
            {
                if(getPieceType(pieces, x, y) == "King" && getPieceColour(pieces, x, y) == "White")
                {
                    kingX = x;
                    kingY = y;
                    // System.out.println("King:"+kingX+" "+kingY);
                }
            }
        }
        
        CopyOnWriteArrayList<Piece> kingPieces = new CopyOnWriteArrayList<Piece>(pieces);
        Iterator<Piece> itr = kingPieces.iterator();
        while (itr.hasNext())
        {
            Piece piece = itr.next();
            if(piece.getX() == kingX && piece.getY() == kingY)
            {
                for (int y2 = 0; y2 < 8; y2++)
                {
                    for (int x2 = 0; x2 < 8; x2++)
                    {
                        if(rules.pieceMoves(board, pieces, piece, kingX, kingY, x2, y2, !whiteActive, false, 0) == true)
                        {
                            count = count + 1;
                        }
                    }
                }
            }
        }
        
        
        return count;
    }
    
    public int getTotalValue(ArrayList<Piece> pieces, boolean whiteActive)
    {
        int value = 0;
        Iterator<Piece> itr = pieces.iterator();
        while (itr.hasNext())
         {
            Piece piece = itr.next();
            if (piece.getColour() == "White" && whiteActive == true)
            {
                value = value + piece.getValue();
            }
            else if (piece.getColour() == "Black" && whiteActive == !true)
            {
                value = value + piece.getValue();
            }
        }
        return value;
    }
    
 
    
    
}
        
        
