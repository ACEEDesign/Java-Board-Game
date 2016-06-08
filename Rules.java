import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import java.util.concurrent.*;
/**
 * Write a description of class CheckersRules here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Rules
{
    // instance variables - replace the example below with your own
    private int x;
    public boolean hasTaken;
    private Piece dragPiece;
    private Piece takenPiece;
    private Piece returnPiece;
    
    /**
     * Constructor for objects of class CheckersRules
     */
    public Rules()
    {
        // initialise instance variables
        x = 0;
    }

    public Boolean validMove(JPanel[][] board, ArrayList<Piece> pieces, Piece activePiece, int startX, int startY, int endX, int endY, boolean whiteActive, Boolean isMove)
    {
        String colour = activePiece.getColour();
        String type = activePiece.getType();
        int movesMade = activePiece.getMoveCount();
        Piece takenPieceT = activePiece;
        boolean taking = false;
        
        boolean isValid = pieceMoves(board, pieces, activePiece, startX, startY, endX, endY, whiteActive, isMove, movesMade);
        
        if(isOpponent(pieces, endX, endY, whiteActive) == true)
        {
            Iterator<Piece> itrT = pieces.iterator();
            while (itrT.hasNext())
            {
                Piece pieceT = itrT.next();
                if (pieceT.getX() == endX && pieceT.getY() == endY)
                {
                    takenPieceT = pieceT;
                    taking = true;
                    break;
                }
                //else if((activePiece.getType() == "Counter" ||  activePiece.getType() == "KingCounter") && activePiece.getColour() == "White")
                //{
                    
                //}
            }
        
        }
        
        activePiece.changeX(endX);
        activePiece.changeY(endY);
        pieces.add(activePiece);
        if (taking == true)
        {
            pieces.remove(takenPieceT);
        }
        
        
        
        if(blackCheck(board, pieces) == true && whiteActive == !true)
        {
            isValid = false;
        }
        
        if(whiteCheck(board, pieces) == true && whiteActive == true)
        {
            isValid = false;
        }
        
        activePiece.changeX(startX);
        activePiece.changeY(startY);
        pieces.remove(activePiece);
        
        if (taking == true)
        {
            pieces.add(takenPieceT);
        }
        
        if(isMove == true && isValid == true)
        {
            Iterator<Piece> itr = pieces.iterator();
            while (itr.hasNext())
            {
                Piece piece = itr.next();
                if (piece.getX() == endX && piece.getY() == endY)
                {
                    takenPiece = piece;
                    if(takenPiece.canBeTaken() == false)
                    {
                        isValid = false;
                        break;
                    }
                    board[takenPiece.getY()][takenPiece.getX()].remove(takenPiece.getPieceLabel());
                    board[takenPiece.getY()][takenPiece.getX()].repaint();
                    pieces.remove(takenPiece);
                    break;
                }
            }
            
        }
        
        if (startX == endX && startY == endY)
        {
            isValid = false;
        }
        
        
        
        return isValid;
    }
    
    
    public Boolean pieceMoves(JPanel[][] board, ArrayList<Piece> pieces, Piece activePiece, int startX, int startY, int endX, int endY, boolean whiteActive, Boolean isMove, 
            int movesMade)
    {
        Boolean isValid = false;
        int direction = 0;
        int movementX = endX - startX;
        int movementY = endY - startY;
        int halfMoveX = movementX / 2;
        int halfMoveY = movementY / 2;
        Piece takenPiece;
        String colour = activePiece.getColour();
        String type = activePiece.getType();
        
        if (whiteActive == true)
        {
             direction = -1;
        }
        else
        {
            direction = 1;
        }
        
        if(type == "Counter")
        {
            if (movementY == direction && (movementX == 1 || movementX == -1) && isSpaceEmpty(pieces, endX, endY) == true)
            {
                isValid = true;
                //if movement is one row forwards and one to the side - standard move
            }
            
            else if (movementY == direction * 2 && (movementX == 2 || movementX == -2) && isSpaceEmpty(pieces, endX, endY) == true && isOpponent(pieces,(endX-halfMoveX), 
                    (endY-halfMoveY), whiteActive) == true)
            {
                if (isMove == true)
                {
                    Iterator<Piece> itr = pieces.iterator();
                    while (itr.hasNext())
                    {
                        Piece piece = itr.next();
                        if (piece.getX() == (endX-halfMoveX) && piece.getY() == (endY-halfMoveY))
                        {
                            takenPiece = piece;
                            board[takenPiece.getY()][takenPiece.getX()].remove(takenPiece.getPieceLabel());
                            board[takenPiece.getY()][takenPiece.getX()].repaint();
                            pieces.remove(takenPiece);
                            break;
                        }
                    }
                }
               
                isValid = true;
            }
        }
        
        else if(type == "KingCounter")
        {
            if ((movementY == 1) && (movementX == 1 || movementX == -1) && isSpaceEmpty(pieces, endX, endY) == true)
            {
                isValid = true;
                //if movement is one row forwards and one to the side - standard move
            }
            
            if ((movementY == -1) && (movementX == 1 || movementX == -1) && isSpaceEmpty(pieces, endX, endY) == true)
            {
                isValid = true;
                //if movement is one row forwards and one to the side - standard move
            }
            
            else if ((movementY == 2) && (movementX == 2 || movementX == -2) && isSpaceEmpty(pieces, endX, endY) == true && isOpponent(pieces,(endX-halfMoveX), 
                    (endY-halfMoveY), whiteActive) == true)
            {
                if(isMove == true)
                {
                    Iterator<Piece> itr = pieces.iterator();
                    while (itr.hasNext())
                    {
                        Piece piece = itr.next();
                        if (piece.getX() == (endX-halfMoveX) && piece.getY() == (endY-halfMoveY))
                        {
                            takenPiece = piece;
                            board[takenPiece.getY()][takenPiece.getX()].remove(takenPiece.getPieceLabel());
                            board[takenPiece.getY()][takenPiece.getX()].repaint();
                            pieces.remove(takenPiece);
                            break;
                        }
                    }
                }
                isValid = true;
            }
            
            else if ((movementY == -2) && (movementX == 2 || movementX == -2) && isSpaceEmpty(pieces, endX, endY) == true && isOpponent(pieces,(endX-halfMoveX), 
                    (endY-halfMoveY), whiteActive) == true)
            {
                if(isMove == true)
                {
                    Iterator<Piece> itr = pieces.iterator();
                    while (itr.hasNext())
                    {
                        Piece piece = itr.next();
                        if (piece.getX() == (endX-halfMoveX) && piece.getY() == (endY-halfMoveY))
                        {
                            takenPiece = piece;
                            board[takenPiece.getY()][takenPiece.getX()].remove(takenPiece.getPieceLabel());
                            board[takenPiece.getY()][takenPiece.getX()].repaint();
                            pieces.remove(takenPiece);
                            break;
                        }
                    }
                }
                isValid = true;
            }
        }
        
        else if (type == "Bishop")
        {
            if (movementX == movementY  && (isSpaceEmpty(pieces, endX, endY) == true || isOpponent(pieces, endX, endY, whiteActive) == true))
            {
                
                    int testX = startX;
                    int testY = startY;
                    boolean isBlocked = false;
                    boolean oppBlock = false;
                    while ((testX != endX) && (testY != endY))
                    {
                        testX = testX + findDirection(movementX);
                        testY = testY + findDirection(movementY);
                        if (isSpaceEmpty(pieces, testX, testY) == false)
                        {
                            if(isOpponent(pieces, testX, testY, whiteActive) == true && (testX == endX && testY == endY))
                            {
                                oppBlock = true;
                            }
                            isBlocked = true;
                            break;
                        }
                    }
                   
                    if (isBlocked == false || oppBlock == true)
                    {
                        isValid = true;
                    }
                
                
                
            }
            else if (movementX == -movementY  && (isSpaceEmpty(pieces, endX, endY) == true || isOpponent(pieces, endX, endY, whiteActive) == true))
            {
                    int testX = startX;
                    int testY = startY;
                    boolean isBlocked = false;
                    boolean oppBlock = false;
                    while ((testX != endX) && (testY != endY))
                    {
                        testX = testX + findDirection(movementX);
                        testY = testY + findDirection(movementY);
                        if (isSpaceEmpty(pieces, testX, testY) == false)
                        {
                            if(isOpponent(pieces, testX, testY, whiteActive) == true && (testX == endX && testY == endY))
                            {
                                oppBlock = true;
                            }
                            isBlocked = true;
                            break;
                        }
                    }
                   
                    if (isBlocked == false || oppBlock == true)
                    {
                        isValid = true;
                    }
            }
        }
        
        else if (type == "Queen")
        {
            if (endY == startY && (isSpaceEmpty(pieces, endX, endY) == true || isOpponent(pieces, endX, endY, whiteActive) == true))
            {
                int testX = startX;
                boolean isBlocked = false;
                boolean oppBlock = false;
                while (testX != endX)
                {
                    testX = testX + findDirection(movementX);
                    if (isSpaceEmpty(pieces, testX, endY) == false)
                    {
                        if(isOpponent(pieces, testX, endY, whiteActive) == true && testX == endX)
                        {
                            oppBlock = true;
                        }
                        isBlocked = true;
                        break;
                    }
                }
                   
                if (isBlocked == false || oppBlock == true)
                {
                    isValid = true;
                }
                
            }
            else if (endX == startX && (isSpaceEmpty(pieces, endX, endY) == true || isOpponent(pieces, endX, endY, whiteActive) == true))
            {
                int testY = startY;
                boolean isBlocked = false;
                boolean oppBlock = false;
                while (testY != endY)
                {
                    testY = testY + findDirection(movementY);
                    if (isSpaceEmpty(pieces, endX, testY) == false)
                    {
                        if(isOpponent(pieces, endX, testY, whiteActive) == true && testY == endY)
                        {
                            oppBlock = true;
                        }
                        isBlocked = true;
                        break;
                    }
                }
                   
                if (isBlocked == false || oppBlock == true)
                {
                    isValid = true;
                }
            }
            else if (movementX == movementY  && (isSpaceEmpty(pieces, endX, endY) == true || isOpponent(pieces, endX, endY, whiteActive) == true))
            {
                
                    int testX = startX;
                    int testY = startY;
                    boolean isBlocked = false;
                    boolean oppBlock = false;
                    while ((testX != endX) && (testY != endY))
                    {
                        testX = testX + findDirection(movementX);
                        testY = testY + findDirection(movementY);
                        if (isSpaceEmpty(pieces, testX, testY) == false)
                        {
                            if(isOpponent(pieces, testX, testY, whiteActive) == true && (testX == endX && testY == endY))
                            {
                                oppBlock = true;
                            }
                            isBlocked = true;
                            break;
                        }
                    }
                   
                    if (isBlocked == false || oppBlock == true)
                    {
                        isValid = true;
                    }
                
                
                
            }
            else if (movementX == -movementY  && (isSpaceEmpty(pieces, endX, endY) == true || isOpponent(pieces, endX, endY, whiteActive) == true))
            {
                    int testX = startX;
                    int testY = startY;
                    boolean isBlocked = false;
                    boolean oppBlock = false;
                    while ((testX != endX) && (testY != endY))
                    {
                        testX = testX + findDirection(movementX);
                        testY = testY + findDirection(movementY);
                        if (isSpaceEmpty(pieces, testX, testY) == false)
                        {
                            if(isOpponent(pieces, testX, testY, whiteActive) == true && (testX == endX && testY == endY))
                            {
                                oppBlock = true;
                            }
                            isBlocked = true;
                            break;
                        }
                    }
                   
                    if (isBlocked == false || oppBlock == true)
                    {
                        isValid = true;
                    }
            }
        }
        else if (type == "Rook")
        {
            if (endY == startY && (isSpaceEmpty(pieces, endX, endY) == true || isOpponent(pieces, endX, endY, whiteActive) == true))
            {
                int testX = startX;
                boolean isBlocked = false;
                boolean oppBlock = false;
                while (testX != endX)
                {
                    testX = testX + findDirection(movementX);
                    if (isSpaceEmpty(pieces, testX, endY) == false)
                    {
                        if(isOpponent(pieces, testX, endY, whiteActive) == true && testX == endX)
                        {
                            oppBlock = true;
                        }
                        isBlocked = true;
                        break;
                    }
                }
                   
                if (isBlocked == false || oppBlock == true)
                {
                    isValid = true;
                }
                
            }
            else if (endX == startX && (isSpaceEmpty(pieces, endX, endY) == true || isOpponent(pieces, endX, endY, whiteActive) == true))
            {
                int testY = startY;
                boolean isBlocked = false;
                boolean oppBlock = false;
                while (testY != endY)
                {
                    testY = testY + findDirection(movementY);
                    if (isSpaceEmpty(pieces, endX, testY) == false)
                    {
                        if(isOpponent(pieces, endX, testY, whiteActive) == true && testY == endY)
                        {
                            oppBlock = true;
                        }
                        isBlocked = true;
                        break;
                    }
                }
                   
                if (isBlocked == false || oppBlock == true)
                {
                    isValid = true;
                }
            }
        }
        else if (type == "Pawn")
        {
            if (movementY == direction && startX == endX && isSpaceEmpty(pieces, endX, endY) == true)
            {
               isValid = true;  
            }
            else if (movementY == 2 && startX == endX && isSpaceEmpty(pieces, endX, endY) == true && colour == "Black" && movesMade == 0 && isSpaceEmpty(pieces, endX, (endY-direction)) == true)
            {
                isValid = true;
            }
            else if (movementY == -2 && startX == endX && isSpaceEmpty(pieces, endX, endY) == true && colour == "White" && movesMade == 0 && isSpaceEmpty(pieces, endX, (endY-direction)) == true)
            {
                isValid = true;
            }
            else if(movementY == direction && endX == (startX+1) && isOpponent(pieces, endX, endY, whiteActive) == true)
            {
                isValid = true;
            }
            else if(movementY == direction && endX == (startX-1) && isOpponent(pieces, endX, endY, whiteActive) == true)
            {
                isValid = true;
            }
            else if (whiteActive == true)
            {
                if (startY == 3 && endY == 2 && endX == (startX + 1) && (startX + 1) <= 7)
                {
                    Iterator<Piece> itr = pieces.iterator();
                    while (itr.hasNext())
                    {
                        Piece piece = itr.next();
                        if (piece.getColour() == "Black" && piece.getType() == "Pawn" && piece.getX() == (startX + 1) && piece.getY() == startY && piece.getMoveCount() == 1)
                        {
                            isValid = true;
                            if(isMove == true)
                            {
                                pieces.remove(piece);
                                board[piece.getY()][piece.getX()].remove(piece.getPieceLabel());
                                board[piece.getY()][piece.getX()].repaint();
                            }
                            break;
                        }
                    }
                }
                else if (startY == 3 && endY == 2 && endX == (startX - 1) && (startX - 1) >= 0)
                {
                    Iterator<Piece> itr = pieces.iterator();
                    while (itr.hasNext())
                    {
                        Piece piece = itr.next();
                        if (piece.getColour() == "Black" && piece.getType() == "Pawn" && piece.getX() == (startX - 1) && piece.getY() == startY && piece.getMoveCount() == 1)
                        {
                            isValid = true;
                            if(isMove == true)
                            {
                                pieces.remove(piece);
                                board[piece.getY()][piece.getX()].remove(piece.getPieceLabel());
                                board[piece.getY()][piece.getX()].repaint();
                            }
                            break;
                        }
                    }
                }
            }
            else if (whiteActive == false)
            {
                if (startY == 4 && endY == 5 && endX == (startX + 1) && (startX + 1) <= 7)
                {
                    Iterator<Piece> itr = pieces.iterator();
                    while (itr.hasNext())
                    {
                        Piece piece = itr.next();
                        if (piece.getColour() == "White" && piece.getType() == "Pawn" && piece.getX() == (startX + 1) && piece.getY() == startY && piece.getMoveCount() == 1)
                        {
                            isValid = true;
                            if(isMove == true)
                            {
                                pieces.remove(piece);
                                board[piece.getY()][piece.getX()].remove(piece.getPieceLabel());
                                board[piece.getY()][piece.getX()].repaint();
                            }
                            break;
                        }
                    }
                }
                else if (startY == 4 && endY == 5 && endX == (startX - 1) && (startX - 1) >= 0)
                {
                    Iterator<Piece> itr = pieces.iterator();
                    while (itr.hasNext())
                    {
                        Piece piece = itr.next();
                        if (piece.getColour() == "White" && piece.getType() == "Pawn" && piece.getX() == (startX - 1) && piece.getY() == startY && piece.getMoveCount() == 1)
                        {
                            isValid = true;
                            if(isMove == true)
                            {
                                pieces.remove(piece);
                                board[piece.getY()][piece.getX()].remove(piece.getPieceLabel());
                                board[piece.getY()][piece.getX()].repaint();
                            }
                            break;
                        }
                    }
                }
            }
        }
        else if (type == "King")
        {
            if ((movementY <= 1 && movementY >= -1) && (movementX <= 1 && movementX >= -1) && (isSpaceEmpty(pieces, endX, endY) == true || isOpponent(pieces, endX, endY, whiteActive) == true))
            {
                isValid = true;
                if (isMove == true)
                {
                    board[activePiece.getY()][activePiece.getX()].remove(activePiece.getPieceLabel());
                    board[activePiece.getY()][activePiece.getX()].repaint();
                }
                
            }
             if(activePiece.getMoveCount() == 0)
                {
             if(movesMade == 0 && isSpaceEmpty(pieces, 5, 7) == true && isSpaceEmpty(pieces, 6,7) == true && endX == 6 && endY == 7)
            {
                ArrayList<Piece> pieces2 = pieces;
                Iterator<Piece> itr = pieces2.iterator();
                Piece tempPiece = activePiece;
                while (itr.hasNext())
                {
                    Piece piece = itr.next();
                    if (piece.getX() == 7 && piece.getY() == 7)
                    {
                        if (piece.getType() == "Rook" && piece.getMoveCount() == 0)
                        {
                            Boolean canContinue = true;
                            pieces2.add(tempPiece);
                            if(whiteCheck(board, pieces) == true)
                            {
                                canContinue = false;
                            }
                            if(whiteCheck(board, pieces2) == true)
                            {
                                canContinue = false;
                            }
                            pieces2.remove(tempPiece);
                            tempPiece.changeX(5); 
                            pieces2.add(tempPiece);
                            if(whiteCheck(board, pieces2) == true)
                            {
                                canContinue = false;
                            }
                            pieces2.remove(tempPiece);
                            tempPiece.changeX(6);
                            pieces2.add(tempPiece);
                            if(whiteCheck(board, pieces2) == true)
                            {
                                canContinue = false;
                            }
                            
                            if (canContinue == true)
                            {
                                isValid = true;
                            }
                            
                            if (isMove == false || isValid == false)
                            {
                                tempPiece.changeX(4);
                            }
                            
                            if (canContinue == true && isMove == true)
                            {
                                piece.changeX(5);
                                //activePiece.changeX(6);
                                board[7][7].remove(piece.getPieceLabel());
                                board[7][7].repaint();
                            }
                            pieces.remove(activePiece);
                        }
                        break;                
                    }
                }  
            }
            else if(movesMade == 0 && isSpaceEmpty(pieces, 1, 7) == true && isSpaceEmpty(pieces, 2,7) == true && isSpaceEmpty(pieces, 3,7) == true && endX == 2 && endY == 7)
            {
               
                ArrayList<Piece> pieces2 = pieces;
                Iterator<Piece> itr = pieces2.iterator();
                Piece tempPiece = activePiece;
                while (itr.hasNext())
                {
                    Piece piece = itr.next();
                    if (piece.getX() == 0 && piece.getY() == 7)
                    {
                        if (piece.getType() == "Rook" && piece.getMoveCount() == 0)
                        {
                            Boolean canContinue = true;
                            pieces2.add(tempPiece);
                            if(whiteCheck(board, pieces2) == true)
                            {
                                canContinue = false;
                            }
                            pieces2.remove(tempPiece);
                            tempPiece.changeX(3); 
                            pieces2.add(tempPiece);
                            if(whiteCheck(board, pieces2) == true)
                            {
                                canContinue = false;
                            }
                            pieces2.remove(tempPiece);
                            tempPiece.changeX(2);
                            pieces2.add(tempPiece);
                            if(whiteCheck(board, pieces2) == true)
                            {
                                canContinue = false;
                            }

                            
                            if (canContinue == true)
                            {
                                isValid = true;
                            }
                            
                            if (isMove == false || isValid == false)
                            {
                                tempPiece.changeX(4);
                            }
                            
                            if (canContinue == true && isMove == true)
                            {
                                piece.changeX(3);
                                //activePiece.changeX(2);
                                board[7][0].remove(piece.getPieceLabel());
                                board[7][0].repaint();
                            }
                            pieces.remove(activePiece);
                        }
                        break;                
                    }
                }  
            }
            else if(movesMade == 0 && isSpaceEmpty(pieces, 5, 0) == true && isSpaceEmpty(pieces, 6,0) == true && endX == 6 && endY == 0)
            {
                ArrayList<Piece> pieces2 = pieces;
                Iterator<Piece> itr = pieces2.iterator();
                Piece tempPiece = activePiece;
                while (itr.hasNext())
                {
                    Piece piece = itr.next();
                    if (piece.getX() == 7 && piece.getY() == 0)
                    {
                        if (piece.getType() == "Rook" && piece.getMoveCount() == 0)
                        {
                            Boolean canContinue = true;
                            pieces2.add(tempPiece);
                            if(blackCheck(board, pieces2) == true)
                            {
                                canContinue = false;
                            }
                            pieces2.remove(tempPiece);
                            tempPiece.changeX(5); 
                            pieces2.add(tempPiece);
                            if(blackCheck(board, pieces2) == true)
                            {
                                canContinue = false;
                            }
                            pieces2.remove(tempPiece);
                            tempPiece.changeX(6);
                            pieces2.add(tempPiece);
                            if(blackCheck(board, pieces2) == true)
                            {
                                canContinue = false;
                            }
                            
                            if (canContinue == true)
                            {
                                isValid = true;
                            }
                            
                            if (isMove == false || isValid == false)
                            {
                                tempPiece.changeX(4);
                            }
                            
                            if (canContinue == true && isMove == true)
                            {
                                piece.changeX(5);
                                //activePiece.changeX(6);
                                board[0][7].remove(piece.getPieceLabel());
                                board[0][7].repaint();
                            }
                            pieces.remove(activePiece);
                        }
                        break;                
                    }
                }  
            }
            else if(movesMade == 0 && isSpaceEmpty(pieces, 1, 0) == true && isSpaceEmpty(pieces, 2,0) == true && isSpaceEmpty(pieces, 3,0) == true && endX == 2 && endY == 0)
            {
                ArrayList<Piece> pieces2 = pieces;
                Iterator<Piece> itr = pieces2.iterator();
                Piece tempPiece = activePiece;
                while (itr.hasNext())
                {
                    Piece piece = itr.next();
                    if (piece.getX() == 0 && piece.getY() == 0)
                    {
                        if (piece.getType() == "Rook" && piece.getMoveCount() == 0)
                        {
                            Boolean canContinue = true;
                            
                            pieces2.add(tempPiece);
                            if(blackCheck(board, pieces2) == true)
                            {
                                canContinue = false;
                            }
                            pieces2.remove(tempPiece);
                            tempPiece.changeX(3); 
                            pieces2.add(tempPiece);
                            if(blackCheck(board, pieces2) == true)
                            {
                                canContinue = false;
                            }
                            pieces2.remove(tempPiece);
                            tempPiece.changeX(2);
                            pieces2.add(tempPiece);
                            if(blackCheck(board, pieces2) == true)
                            {
                                canContinue = false;
                            }

                            
                            if (canContinue == true)
                            {
                                isValid = true;
                            }
                            
                            if (isMove == false || isValid == false)
                            {
                                tempPiece.changeX(4);
                                
                            }
                            
                            if (canContinue == true && isMove == true)
                            {
                                piece.changeX(3);
                                //activePiece.changeX(2);
                                board[0][0].remove(piece.getPieceLabel());
                                board[0][0].repaint();
                            }
                            pieces.remove(activePiece);
                        }
                        break;                
                    }
                }  
            }
        }
        }
        else if (type == "Knight")
        {
            if (isSpaceEmpty(pieces, endX, endY) == true || isOpponent(pieces, endX, endY, whiteActive) == true)
            {
                if (endX == (startX + 2) && endY == (startY + 1))
                {
                    isValid = true;
                }
                else if (endX == (startX - 2) && endY == (startY + 1))
                {
                    isValid = true;
                }
                else if (endX == (startX + 2) && endY == (startY - 1))
                {
                    isValid = true;
                }
                else if (endX == (startX - 2) && endY == (startY - 1))
                {
                    isValid = true;
                }
                else if (endX == (startX + 1) && endY == (startY + 2))
                {
                    isValid = true;
                }
                else if (endX == (startX - 1) && endY == (startY + 2))
                {
                    isValid = true;
                }
                else if (endX == (startX + 1) && endY == (startY - 2))
                {
                    isValid = true;
                }
                else if (endX == (startX - 1) && endY == (startY - 2))
                {
                    isValid = true;
                }
            }
        }
        return isValid;
    }        
            
    
    /**
     * An example of a method - replace this comment with your own
     * 
     * @param  y   a sample parameter for a method
     * @return     the sum of x and y 
     */
    
    public Boolean isSpaceEmpty(ArrayList<Piece> pieces, int x, int y)
    {
        Boolean isSpaceEmpty = true;
        Iterator<Piece> itr = pieces.iterator();
        while (itr.hasNext())
        {
            Piece piece = itr.next();
            if (piece.getX() == x && piece.getY() == y)
            {
                isSpaceEmpty = false;
                break;
            }
        }
        
        return isSpaceEmpty;
    }
    
    public Boolean isOpponent(ArrayList<Piece> pieces, int x, int y, Boolean whiteActive)
    {
        Boolean isOpponent = false;
        Iterator<Piece> itr = pieces.iterator();
        while (itr.hasNext())
        {
            Piece piece = itr.next();
            if (piece.getX() == x && piece.getY() == y)
            {
                if(piece.getColour() == "White" && whiteActive == false)
                {
                    isOpponent = true;
                    break;
                }
                else if(piece.getColour() == "Black" && whiteActive == true)
                {
                    isOpponent = true;
                    break;
                }
            }
        }
        
        return isOpponent;
    }
    
    public int findDirection(int movement)
    {
        if (movement > 0)
        {
            return 1;
        }
        else if (movement < 0)
        {
            return -1;
        }
        else
        {
            return 0;
        }
    }
    
    public boolean checkWin(String game, ArrayList<Piece> pieces, boolean whiteActive, JPanel[][] board)
    {
        if(game == "Checkers" || game == "Test")
        {
            boolean win = true;
            
            Iterator<Piece> itr = pieces.iterator();
            while (itr.hasNext())
            {
                Piece piece = itr.next();
                if (isOpponent(pieces, piece.getX(), piece.getY(), !whiteActive) == true)
                {
                    win = false;
                    break;
                }
            }
            return win;
        }
        else if(game == "Chess")
        {
            boolean win = false;
            boolean bm = blackCanMove(board, pieces);
            boolean wm = whiteCanMove(board, pieces);
            
            if(bm == false || wm == false)
            {
                win = true;
            }
            
            return win;
        }
        else
        {
            return false;
        }
    }
  
    public int whiteKingX(ArrayList<Piece> pieces)
    {
        int x = 0;
        Iterator<Piece> itr = pieces.iterator();
        while (itr.hasNext())
        {
            Piece piece = itr.next();
            if(piece.getColour() == "White" && piece.getType() == "King")
            {
                x = piece.getX();
            }
        }
        return x;
    }
   
   public int whiteKingY(ArrayList<Piece> pieces)
    {
        int y = 0;
        Iterator<Piece> itr = pieces.iterator();
        while (itr.hasNext())
        {
            Piece piece = itr.next();
            if(piece.getColour() == "White" && piece.getType() == "King")
            {
                y = piece.getY();
            }
        }
        return y;
    } 
    
   public int blackKingX(ArrayList<Piece> pieces)
   {
        int x = 0;
        Iterator<Piece> itr = pieces.iterator();
        while (itr.hasNext())
        {
            Piece piece = itr.next();
            if(piece.getColour() == "Black" && piece.getType() == "King")
            {
                x = piece.getX();
            }
        }
        return x;
   }
   
   public int blackKingY(ArrayList<Piece> pieces)
   {
       int y = 0;
       Iterator<Piece> itr = pieces.iterator();
       while (itr.hasNext())
       {
           Piece piece = itr.next();
           if(piece.getColour() == "Black" && piece.getType() == "King")
           {
               y = piece.getY();
           }
       }
       return y;
    } 
    
   public boolean blackCheck(JPanel[][] board, ArrayList<Piece> pieces)
   {
       boolean blackCheck = false;
       Iterator<Piece> itr = pieces.iterator();
       while (itr.hasNext())
       {
           Piece piece = itr.next();
           if(piece.getColour() == "White")
           {
               if(pieceMoves(board, pieces, piece, piece.getX(), piece.getY(), blackKingX(pieces), blackKingY(pieces), true, false, piece.getMoveCount()) == true)
               {
                   blackCheck = true;
               }
           }
       }
       return blackCheck;
   }
   
   public boolean whiteCheck(JPanel[][] board, ArrayList<Piece> pieces)
   {
       boolean whiteCheck = false;
       Iterator<Piece> itr = pieces.iterator();
       while (itr.hasNext())
       {
           Piece piece = itr.next();
           if(piece.getColour() == "Black")
           {
               if(pieceMoves(board, pieces, piece, piece.getX(), piece.getY(), whiteKingX(pieces), whiteKingY(pieces), false, false, piece.getMoveCount()) == true)
               {
                   whiteCheck = true;
               }
           }
       }
       return whiteCheck;
   }
   
   public boolean whiteCanMove(JPanel[][] board, ArrayList<Piece> pieces)
   {
       boolean canMove = false;
       CopyOnWriteArrayList<Piece> tempPieces = new CopyOnWriteArrayList(pieces);
       Iterator<Piece> itr = tempPieces.iterator();
       while (itr.hasNext())
       {
           Piece piece = itr.next();
           if(piece.getColour() == "White")
           {
               for (int y = 0; y < 8; y++)
               {
                   for (int x = 0; x < 8; x++)
                   {
                       if(validMove(board, pieces, piece, piece.getX(), piece.getY(), x, y, true, false) == true)
                       {
                           canMove = true;
                           //System.out.println("White can move");
                          
                        }
                    }
                }
               if(canMove == true)
               {
                   break;
               }
               
           }
       }
       if(canMove == false)
       {
           //System.out.println("White can not move");
        }
       
       return canMove;
    }
   
   public boolean blackCanMove(JPanel[][] board, ArrayList<Piece> pieces)
   {
       boolean canMove = false;
       CopyOnWriteArrayList<Piece> tempPieces = new CopyOnWriteArrayList(pieces);
       Iterator<Piece> itr = tempPieces.iterator(); 
        
       while (itr.hasNext())
       {
           Piece piece = itr.next();
           if(piece.getColour() == "Black")
           {
               for (int y = 0; y < 8; y++)
               {
                   for (int x = 0; x < 8; x++)
                   {
                       if(validMove(board, pieces, piece, piece.getX(), piece.getY(), x, y, false, false) == true)
                       {
                           canMove = true;
                           //System.out.println("Black can move");
                        }
                    }
                   
                }
               if(canMove == true)
               {
                   break;
               }
               
           }
       }
       if(canMove == false)
       {
           //System.out.println("Black can not move");
        }
       return canMove;
    }
    
    public Piece getPiece(ArrayList<Piece> pieces, int x, int y)
    {
        Iterator<Piece> itr = pieces.iterator();
        while (itr.hasNext())
        {
            Piece piece = itr.next();
            if (piece.getX() == x && piece.getY() == y)
            {
                Piece returnPiece = piece;
            }
        }
        
        return returnPiece;
    }
   
}
