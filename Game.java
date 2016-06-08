import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;

/**
 * Write a description of class Board here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Game extends JFrame
{
    // instance variables - replace the example below with your own
    private JFrame frame;
    private JPanel panel; 
    private JPanel[][] board;
    private ArrayList<Piece> pieces;
    private ImageIcon counterWhite = new ImageIcon(System.getProperty("user.dir") + "//images//CounterWhite.png");
    private ImageIcon counterBlack = new ImageIcon(System.getProperty("user.dir") + "//images//CounterBlack.png");
    private ImageIcon kingCounterWhite = new ImageIcon(System.getProperty("user.dir") + "//images//KingCounterWhite.png");
    private ImageIcon kingCounterBlack = new ImageIcon(System.getProperty("user.dir") + "//images//KingCounterBlack.png");
    private ImageIcon bishopWhite = new ImageIcon(System.getProperty("user.dir") + "//images//BishopWhite.png");
    private ImageIcon bishopBlack = new ImageIcon(System.getProperty("user.dir") + "//images//BishopBlack.png");
    private ImageIcon rookWhite = new ImageIcon(System.getProperty("user.dir") + "//images//RookWhite.png");
    private ImageIcon rookBlack = new ImageIcon(System.getProperty("user.dir") + "//images//RookBlack.png");
    private ImageIcon queenWhite = new ImageIcon(System.getProperty("user.dir") + "//images//QueenWhite.png");
    private ImageIcon queenBlack = new ImageIcon(System.getProperty("user.dir") + "//images//QueenBlack.png");
    private ImageIcon pawnWhite = new ImageIcon(System.getProperty("user.dir") + "//images//PawnWhite.png");
    private ImageIcon pawnBlack = new ImageIcon(System.getProperty("user.dir") + "//images//PawnBlack.png");
    private ImageIcon kingWhite = new ImageIcon(System.getProperty("user.dir") + "//images//KingWhite.png");
    private ImageIcon kingBlack = new ImageIcon(System.getProperty("user.dir") + "//images//KingBlack.png");
    private ImageIcon knightWhite = new ImageIcon(System.getProperty("user.dir") + "//images//KnightWhite.png");
    private ImageIcon knightBlack = new ImageIcon(System.getProperty("user.dir") + "//images//KnightBlack.png");
    public String gameName;
    private MouseInput mouse;
    private Boolean whiteActive;
    public String opponent;
    public int blackMoves;
    public int whiteMoves;

    /**
     * Constructor for objects of class Board
     */
    public Game()
    {
        // initialise instance variables
        gameName = "Chess";
        //gameName = "Checkers";
        opponent = "Human";
        frame = new JFrame();
        panel = new JPanel(new GridLayout(8,8));
        board = new JPanel[8][8];
        pieces = new ArrayList<Piece>();
        
        if(gameName == "Test")
        {
            testChessPieces();
        }
        else if(gameName == "Checkers")
        {
            standardCheckersPieces();
        }
        else if(gameName == "Chess")
        {
            standardChessPieces();

        }
        blackMoves = 0;
        whiteMoves = 0;
        whiteActive = true;
        mouse = new MouseInput(pieces, this, board, frame, whiteActive);
        frame.setTitle(gameName);
        frame.setSize(400, 425);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.add(panel);
        panel.addMouseListener(mouse);
        panel.addMouseMotionListener(mouse);
        this.drawGameBoard();
        this.drawPieces(pieces);
        
        
       

        
    }

    /**
     * An example of a method - replace this comment with your own
     * 
     * @param  y   a sample parameter for a method
     * @return     the sum of x and y 
     */
    public void standardCheckersPieces()
    {
       pieces.add(new Piece(7,7,"Counter", "White" ,true, counterWhite, 1));
       pieces.add(new Piece(5,7,"Counter", "White" ,true, counterWhite, 1));
       pieces.add(new Piece(3,7,"Counter", "White" ,true, counterWhite, 1));
       pieces.add(new Piece(1,7,"Counter", "White" ,true, counterWhite, 1));
       pieces.add(new Piece(0,6,"Counter", "White" ,true, counterWhite, 1));
       pieces.add(new Piece(2,6,"Counter", "White" ,true, counterWhite, 1));
       pieces.add(new Piece(4,6,"Counter", "White" ,true, counterWhite, 1));
       pieces.add(new Piece(6,6,"Counter", "White" ,true, counterWhite, 1));
       pieces.add(new Piece(7,5,"Counter", "White" ,true, counterWhite, 1));
       pieces.add(new Piece(5,5,"Counter", "White" ,true, counterWhite, 1));
       pieces.add(new Piece(3,5,"Counter", "White" ,true, counterWhite, 1));
       pieces.add(new Piece(1,5,"Counter", "White" ,true, counterWhite, 1));
       
       pieces.add(new Piece(0,0,"Counter", "Black" , true, counterBlack, 1));
       pieces.add(new Piece(2,0,"Counter", "Black" , true, counterBlack, 1));
       pieces.add(new Piece(4,0,"Counter", "Black" , true, counterBlack, 1));
       pieces.add(new Piece(6,0,"Counter", "Black" , true, counterBlack, 1));
       pieces.add(new Piece(1,1,"Counter", "Black" , true, counterBlack, 1));
       pieces.add(new Piece(3,1,"Counter", "Black" , true, counterBlack, 1));
       pieces.add(new Piece(5,1,"Counter", "Black" , true, counterBlack, 1));
       pieces.add(new Piece(7,1,"Counter", "Black" , true, counterBlack, 1));
       pieces.add(new Piece(0,2,"Counter", "Black" , true, counterBlack, 1));
       pieces.add(new Piece(2,2,"Counter", "Black" , true, counterBlack, 1));
       pieces.add(new Piece(4,2,"Counter", "Black" , true, counterBlack, 1));
       pieces.add(new Piece(6,2,"Counter", "Black" , true, counterBlack, 1));
 
    }
    
    public void testChessPieces()
    {
       pieces.add(new Piece(3,1,"Counter","Black",true, counterBlack, 1)); 
        pieces.add(new Piece(4,1,"Pawn","Black",true, pawnBlack, 1)); 
        pieces.add(new Piece(5,1,"Counter","Black",true, counterBlack, 1)); 
        pieces.add(new Piece(0,0,"Rook","Black",true, rookBlack, 5));
        pieces.add(new Piece(1,0,"Knight","Black",true, knightBlack, 3));
        pieces.add(new Piece(2,0,"Bishop","Black",true, bishopBlack, 4));
        pieces.add(new Piece(3,0,"Queen","Black",true, queenBlack, 12));
        pieces.add(new Piece(4,0,"King","Black",true, kingBlack, 15));
        pieces.add(new Piece(5,0,"Bishop","Black",true, bishopBlack, 4));
        pieces.add(new Piece(6,0,"Knight","Black",true, knightBlack, 3));
        pieces.add(new Piece(7,0,"Rook","Black",true, rookBlack, 5));
        pieces.add(new Piece(0,1,"Pawn","Black",true, pawnBlack, 1)); 
        pieces.add(new Piece(1,1,"Counter","Black",true, counterBlack, 1)); 
        pieces.add(new Piece(2,1,"Pawn","Black",true, pawnBlack, 1)); 
        pieces.add(new Piece(6,1,"Pawn","Black",true, pawnBlack, 1)); 
        pieces.add(new Piece(7,1,"Counter","Black",true, counterBlack, 1)); 
        
        pieces.add(new Piece(0,7,"Rook","White",true, rookWhite, 5));
        pieces.add(new Piece(1,7,"Knight","White",true, knightWhite, 3));
        pieces.add(new Piece(2,7,"Bishop","White",true, bishopWhite, 4));
        pieces.add(new Piece(3,7,"Queen","White",true, queenWhite, 12));
        pieces.add(new Piece(4,7,"King","White",true, kingWhite, 15));
        pieces.add(new Piece(5,7,"Bishop","White",true, bishopWhite, 4));
        pieces.add(new Piece(6,7,"Knight","White",true, knightWhite, 3));
        pieces.add(new Piece(7,7,"Rook","White",true, rookWhite, 5));
        pieces.add(new Piece(0,6,"Pawn","White",true, pawnWhite, 1)); 
        pieces.add(new Piece(1,6,"Counter","White",true, counterWhite, 1)); 
        pieces.add(new Piece(2,6,"Pawn","White",true, pawnWhite, 1)); 
        pieces.add(new Piece(3,6,"Counter","White",true, counterWhite, 1)); 
        pieces.add(new Piece(4,6,"Pawn","White",true, pawnWhite, 1)); 
        pieces.add(new Piece(5,6,"Counter","White",true, counterWhite, 1)); 
        pieces.add(new Piece(6,6,"Pawn","White",true, pawnWhite, 1)); 
        pieces.add(new Piece(7,6,"Counter","White",true, counterWhite, 1)); 

    }
    
    public void standardChessPieces()
    {
        pieces.add(new Piece(3,1,"Pawn","Black",true, pawnBlack, 1)); 
        pieces.add(new Piece(4,1,"Pawn","Black",true, pawnBlack, 1)); 
        pieces.add(new Piece(5,1,"Pawn","Black",true, pawnBlack, 1)); 
        pieces.add(new Piece(0,0,"Rook","Black",true, rookBlack, 5));
        pieces.add(new Piece(1,0,"Knight","Black",true, knightBlack, 3));
        pieces.add(new Piece(2,0,"Bishop","Black",true, bishopBlack, 4));
        pieces.add(new Piece(3,0,"Queen","Black",true, queenBlack, 12));
        pieces.add(new Piece(4,0,"King","Black",false, kingBlack, 15));
        pieces.add(new Piece(5,0,"Bishop","Black",true, bishopBlack, 4));
        pieces.add(new Piece(6,0,"Knight","Black",true, knightBlack, 3));
        pieces.add(new Piece(7,0,"Rook","Black",true, rookBlack, 5));
        pieces.add(new Piece(0,1,"Pawn","Black",true, pawnBlack, 1)); 
        pieces.add(new Piece(1,1,"Pawn","Black",true, pawnBlack, 1)); 
        pieces.add(new Piece(2,1,"Pawn","Black",true, pawnBlack, 1)); 
        pieces.add(new Piece(6,1,"Pawn","Black",true, pawnBlack, 1)); 
        pieces.add(new Piece(7,1,"Pawn","Black",true, pawnBlack, 1)); 
        
        pieces.add(new Piece(0,7,"Rook","White",true, rookWhite, 5));
        pieces.add(new Piece(1,7,"Knight","White",true, knightWhite, 3));
        pieces.add(new Piece(2,7,"Bishop","White",true, bishopWhite, 4));
        pieces.add(new Piece(3,7,"Queen","White",true, queenWhite, 12));
        pieces.add(new Piece(4,7,"King","White",false, kingWhite, 15));
        pieces.add(new Piece(5,7,"Bishop","White",true, bishopWhite, 4));
       pieces.add(new Piece(6,7,"Knight","White",true, knightWhite, 3));
        pieces.add(new Piece(7,7,"Rook","White",true, rookWhite, 5));
        pieces.add(new Piece(0,6,"Pawn","White",true, pawnWhite, 1)); 
        pieces.add(new Piece(1,6,"Pawn","White",true, pawnWhite, 1)); 
        pieces.add(new Piece(2,6,"Pawn","White",true, pawnWhite, 1)); 
        pieces.add(new Piece(3,6,"Pawn","White",true, pawnWhite, 1)); 
        pieces.add(new Piece(4,6,"Pawn","White",true, pawnWhite, 1)); 
        pieces.add(new Piece(5,6,"Pawn","White",true, pawnWhite, 1)); 
        pieces.add(new Piece(6,6,"Pawn","White",true, pawnWhite, 1)); 
        pieces.add(new Piece(7,6,"Pawn","White",true, pawnWhite, 1)); 
    }
    
    public void testPieces()
    {
       //pieces.add(new Piece(0,0,"Rook", "Black" , true, rookBlack));
       //pieces.add(new Piece(2,0,"King", "Black" , false, kingBlack));
       //pieces.add(new Piece(4,0,"Knight", "Black" , true, knightBlack));
       //pieces.add(new Piece(6,0,"Counter", "Black" , true, counterBlack));
       
       //pieces.add(new Piece(7,7,"Rook", "White" , true, rookWhite));
       //pieces.add(new Piece(5,7,"King", "White" ,false, kingWhite));
       //pieces.add(new Piece(3,7,"Knight", "White" ,true, knightWhite));
       //pieces.add(new Piece(1,7,"Counter", "White" ,true, counterWhite));
       //pieces.add(new Piece(4,6,"Pawn", "White", true, pawnWhite));
       
       //pieces.add(new Piece(0,6,"KingCounter", "White" , true, kingCounterWhite));
       //pieces.add(new Piece(2,6,"Bishop", "White" ,true, bishopWhite));
       //pieces.add(new Piece(4,2,"KingCounter", "Black" , true, kingCounterBlack));
       //pieces.add(new Piece(6,2,"Bishop", "Black" , true, bishopBlack));
       
       //pieces.add(new Piece(7,1,"Queen", "Black" , true, queenBlack));
       //pieces.add(new Piece(5,1,"Pawn", "Black" , true, pawnBlack));
       //pieces.add(new Piece(1,5,"Queen", "White" ,true, queenWhite));
       
    }
    
    public void drawPieces(ArrayList<Piece> pieces)
    {
        Iterator<Piece> itr = pieces.iterator();
        while (itr.hasNext())
        {
            Piece piece = itr.next();
            this.board[piece.getY()][piece.getX()].add(piece.getPieceLabel());
            this.board[piece.getY()][piece.getX()].validate();
        }
        
    }
    // needs to listen for mouse input correctly
    
    private void drawGameBoard()
    {
        for (int y = 0; y < 8; y++)
            for (int x = 0; x < 8; x++)
            {
                board[y][x] = new JPanel(new BorderLayout());
                panel.add(board[y][x]);
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
