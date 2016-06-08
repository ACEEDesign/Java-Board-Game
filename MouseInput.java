import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
/**
 * Write a description of class MouseInput here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MouseInput extends JFrame implements MouseListener, MouseMotionListener
{
    // instance variables - replace the example below with your own
    private ArrayList<Piece> pieces;
    private int startX, startY; //Point where mouse is pressed
    private int prevX, prevY; //Most recent mouse cords
    private boolean dragging; //true when dragging
    private Piece dragPiece;
    private JPanel[][] board;
    private boolean pieceSelected;
    private Piece testPiece;
    private Game game;
    private JFrame frame;
    private Boolean whiteActive;
    private Rules rules;
    private ImageIcon kingCounterWhite = new ImageIcon(System.getProperty("user.dir") + "//images//KingCounterWhite.png");
    private ImageIcon kingCounterBlack = new ImageIcon(System.getProperty("user.dir") + "//images//KingCounterBlack.png");
    private ImageIcon queenWhite = new ImageIcon(System.getProperty("user.dir") + "//images//QueenWhite.png");
    private ImageIcon queenBlack = new ImageIcon(System.getProperty("user.dir") + "//images//QueenBlack.png");
    private AI AI;
    

    /**
     * Constructor for objects of class MouseInput
     */
    public MouseInput(ArrayList<Piece> pieces, Game game, JPanel[][] board, JFrame frame, Boolean whiteActive)
    {
        // initialise instance variables
        this.pieces = pieces;
        this.board = board;
        this.game = game;
        this.frame = frame;
        this.whiteActive = whiteActive;
        rules = new Rules();

    }

    /**
     * An example of a method - replace this comment with your own
     * 
     * @param  y   a sample parameter for a method
     * @return     the sum of x and y 
     */
    public void mousePressed(MouseEvent evt)
    {

            pieceSelected = false;
            dragging = true;
            startX = findSquare(evt.getX()); //start position
            startY = findSquare(evt.getY());
            prevX = startX; //most recent
            prevY = startY;
           

            Iterator<Piece> itr = pieces.iterator();
            while (itr.hasNext())
            {
                Piece dragPiece = itr.next();
                if (dragPiece.getX() == startX && dragPiece.getY() == startY)
                {
                    if (dragPiece.getColour() == "White" && whiteActive == true)
                    {
                        pieceSelected = true;
                        testPiece = dragPiece;
                        break;
                    }
                    
                    else if (dragPiece.getColour() == "Black" && whiteActive == false)
                    {
                        pieceSelected = true;
                        testPiece = dragPiece;
                        break;
                    }
                    
                }
            }
            
            if (pieceSelected == true)
            {
                board[testPiece.getY()][testPiece.getX()].remove(testPiece.getPieceLabel());
                board[testPiece.getY()][testPiece.getX()].repaint();
                pieces.remove(testPiece);
                game.drawPieces(pieces);
                this.game.repaint();
                findAvaliable(board, pieces, testPiece, startX, startY, whiteActive);
            }
            
            else
            {
                dragging = false;
            }

    }
    
    public void mouseDragged(MouseEvent evt)
    {
        if (dragging == false) // Check we are dragging
        {
           return; //processing a dragging gesture
        }

        int x = evt.getX(); // current mouse position
        int y = evt.getY();
        
        prevX = x; // Remember current position
        prevY = y;
        
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Point hotSpot = new Point(0,0);
        String name = testPiece.getType();
        ImageIcon imageicon = testPiece.getImage();
        Image image = imageicon.getImage();
        
        Cursor cursor = toolkit.createCustomCursor(image, hotSpot, name);
        this.frame.setCursor(cursor);
        
    }    
    
    public void mouseReleased(MouseEvent evt)
    {
        if (dragging == false)
        {
            return;
        }
        
        int x = findSquare(evt.getX()); // current mouse position
        int y = findSquare(evt.getY());
        int direction = y-startY;
        
        //checks move is valid
        if (rules.validMove(board,pieces, testPiece, startX, startY, x,y, whiteActive, true  ) == true)
        {
             testPiece.changeX(x);
             testPiece.changeY(y);
             testPiece.increaseMoveCount();
             
             if(testPiece.getColour() == "White" && testPiece.getY() == 0 && testPiece.getType() == "Counter")
             {
                 testPiece.changeType("KingCounter", kingCounterWhite, 5);
             }
        
             if(testPiece.getColour() == "Black" && testPiece.getY() == 7 && testPiece.getType() == "Counter")
             {
                 testPiece.changeType("KingCounter", kingCounterBlack,5);
             }
             
             if(testPiece.getColour() == "White" && testPiece.getY() == 0 && testPiece.getType() == "Pawn")
             {
                 testPiece.changeType("Queen", queenWhite, 15);
             }
        
             if(testPiece.getColour() == "Black" && testPiece.getY() == 7 && testPiece.getType() == "Pawn")
             {
                 testPiece.changeType("Queen", queenBlack, 15);
             }
             
             
             //check for second move
             if(testPiece.getType() == "Counter" && rules.isSpaceEmpty(pieces, (x+2), (y+direction)) == true && rules.isOpponent(pieces, (x+1), (y+direction/2), whiteActive) == true && 
             (y-startY == 2 || y-startY == -2) && ((x+2) <= 7 && (x+2) >= 0) && ((y+direction) <= 7 && (y+direction >= 0 )))
             {
                 //can retake
                 //whiteActive = !whiteActive;
             }
             else if(testPiece.getType() == "Counter" && rules.isSpaceEmpty(pieces, (x-2), (y+direction)) == true && rules.isOpponent(pieces, (x-1), (y+direction/2), whiteActive) == true && 
             (y-startY == 2 || y-startY == -2) && ((x-2) >= 0 && (x-2) <= 7)&& ((y+direction) <= 7 && (y+direction >= 0 )))
             {
                 //can retake
                 //whiteActive = !whiteActive;
             }
             
             
             else if(testPiece.getType() == "KingCounter" && rules.isSpaceEmpty(pieces, (x-2), (y+2)) == true && rules.isOpponent(pieces, (x-1), (y+1), whiteActive) == true && 
             (y-startY == 2 || y-startY == -2) && ((x-2) >= 0 && (x-2) <=7)&& ((y+2) <= 7 && (y+2 >= 0 )))
             {
                 //can retake
                 //whiteActive = !whiteActive;
             }
             
             else if(testPiece.getType() == "KingCounter" && rules.isSpaceEmpty(pieces, (x-2), (y-2)) == true && rules.isOpponent(pieces, (x-1), (y-1), whiteActive) == true && 
             (y-startY == 2 || y-startY == -2) && ((x-2) >= 0 && (x-2) <= 7)&& ((y-2) <= 7 && (y-2 >= 0 )))
             {
                 //can retake
                 //whiteActive = !whiteActive;
             }
             else if(testPiece.getType() == "KingCounter" && rules.isSpaceEmpty(pieces, (x+2), (y+2)) == true && rules.isOpponent(pieces, (x+1), (y+1), whiteActive) == true && 
             (y-startY == 2 || y-startY == -2) && ((x+2) >= 0 && (x+2) <= 7) && ((y+2) <= 7 && (y+2 >= 0 )))
             {
                 //can retake
                 //whiteActive = !whiteActive;
             }
             
             else if(testPiece.getType() == "KingCounter" && rules.isSpaceEmpty(pieces, (x+2), (y-2)) == true && rules.isOpponent(pieces, (x+1), (y-1), whiteActive) == true && 
             (y-startY == 2 || y-startY == -2) && ((x+2) >= 0 && (x+2) <= 7 ) && ((y-2) <= 7 && (y-2 >= 0 )))
             {
                 //can retake
                 //whiteActive = !whiteActive;
             }
             
             else
             {
                 if (whiteActive == true)
                 {
                     game.whiteMoves = game.whiteMoves + 1;
                 }
                 else
                 {
                     game.blackMoves = game.blackMoves + 1;
                 }
                 whiteActive = !whiteActive;
             }
        }
        
        pieces.add(testPiece);
        game.drawPieces(pieces);
        this.board[testPiece.getY()][testPiece.getX()].add(testPiece.getPieceLabel());
        this.board[testPiece.getY()][testPiece.getX()].repaint();
        this.game.repaint();
        this.frame.setCursor(Cursor.DEFAULT_CURSOR);
        resetBoardBackground();
        
        if (rules.checkWin(game.gameName, pieces, whiteActive, board) == true)
        {
            if (whiteActive == !true)
            {
                System.out.println("White wins!");
            }
            else
            {
                System.out.println("Black wins!");
            }
        }
        
        dragging = false;
        
        if(game.opponent == "Computer"  && whiteActive == false)
        {
             AI = new AI(whiteActive, pieces, board, game);
             ArrayList<Piece> active = AI.getActive(whiteActive, pieces);
             ArrayList<Move> moves = AI.getMoves(board, active, whiteActive, pieces);
             Iterator<Move> itrM = moves.iterator();
             while (itrM.hasNext())
             {
                 Move move = itrM.next();
                 if (move.getRank() == AI.getHighestRank(moves))
                 {
                     Move bestMove = move;
                     
                     
                     Iterator<Piece> itr = pieces.iterator();
                     while (itr.hasNext())
                     {
                         Piece piece = itr.next();
                         if (piece.getX() == move.getStartX() && piece.getY() == move.getStartY())
                         {
                             System.out.println(piece.getType());
                             System.out.println(move.getStartX()+" "+move.getStartY()+" To "+move.getEndX()+" "+move.getEndY());
                             System.out.println(move.getRank());
                             this.board[piece.getY()][piece.getX()].remove(piece.getPieceLabel());
                             this.board[piece.getY()][piece.getX()].repaint();
                             pieces.remove(piece);
                             rules.validMove(board,pieces, piece, piece.getX(), piece.getY(), move.getEndX(),move.getEndY(), whiteActive, true  );
                             
                             piece.changeX(move.getEndX());
                             piece.changeY(move.getEndY());
                             
                            
                             
                             
                             if(piece.getColour() == "White" && piece.getY() == 0 && piece.getType() == "Counter")
                             {
                                 piece.changeType("KingCounter", kingCounterWhite,5);
                             }
        
                             if(piece.getColour() == "Black" && piece.getY() == 7 && piece.getType() == "Counter")
                             {
                                 piece.changeType("KingCounter", kingCounterBlack,5);
                             }
             
                             if(piece.getColour() == "White" && piece.getY() == 0 && piece.getType() == "Pawn")
                             {
                                 piece.changeType("Queen", queenWhite, 5);
                             }
        
                             if(piece.getColour() == "Black" && piece.getY() == 7 && piece.getType() == "Pawn")
                             {
                                 piece.changeType("Queen", queenBlack, 5);
                                }
                             board[move.getStartY()][move.getStartX()].setBackground(Color.RED);
                             piece.increaseMoveCount();
                             pieces.add(piece);
                             game.drawPieces(pieces);
                             this.board[piece.getY()][piece.getX()].add(piece.getPieceLabel());
                             this.board[piece.getY()][piece.getX()].repaint();
                             this.game.repaint();   
                                
                                
                             break;
                            }
             
                        }
                     
                     break;
                
                    }
                }
            
                
             if (whiteActive == true)
             {
                 game.whiteMoves = game.whiteMoves + 1;
               }
             else
             {
                 game.blackMoves = game.blackMoves + 1;
             }   
             whiteActive = !whiteActive;
             
        }
        
        
        
            
    }
    
    public void mouseClicked(MouseEvent evt)
    {
       //
    }
    
    public void mouseEntered(MouseEvent evt)
    {
        //code?
    }
    
    public void mouseExited(MouseEvent evt)
    {
        // code
    }
    
    public void mouseMoved(MouseEvent evt)
    {
        // code
    }
    
    public int findSquare(int number)
    {
        if (number < 50)
        {
            return 0;
        }
        else if (number < 100)
        {
           return 1; 
        }
        else if (number < 150)
        {
            return 2;
        }
        else if (number < 200)
        {
            return 3;
        }
        else if (number < 250)
        {
            return 4;
        }
        else if (number < 300)
        {
            return 5;
        }
        else if (number < 350)
        {
            return 6;
        }
        else if (number < 400)
        {
            return 7;
        }
        else
        {
            return 8;
        }
    }
    
    public void findAvaliable(JPanel[][] board, ArrayList<Piece> pieces, Piece activePiece, int startX, int startY, Boolean whiteActive)
    {
        for(int x=0; x<8; x++)
        {
            for(int y=0; y<8; y++)
            {
                if (rules.validMove(board, pieces, activePiece, startX, startY, x, y, whiteActive, false))
                {
                    board[y][x].setBackground(Color.GREEN);
                }
            }
        }
    }
    
    public void resetBoardBackground()
    {
        for (int y = 0; y < 8; y++)
            for (int x = 0; x < 8; x++)
            {
                if (y % 2 == 0)
                    if (x % 2 != 0)
                        board[y][x].setBackground(Color.DARK_GRAY);
                    else
                        board[y][x].setBackground(Color.WHITE);
                else

                              if (x % 2 == 0)

                                    board[y][x].setBackground(Color.DARK_GRAY);

                              else

                                    board[y][x].setBackground(Color.WHITE);

            }

    }
}
    

