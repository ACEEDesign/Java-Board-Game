import javax.swing.*;
import java.awt.*;

    /**
 * Write a description of class piece here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Piece
{
    // instance variables - replace the example below with your own
    private int x;
    private int y;
    private String type;
    private String colour;
    private Boolean takeable;
    private ImageIcon image;
    private JLabel label;
    private int size;
    private int movesMade;
    private int value;

    /**
     * Constructor for objects of class piece
     */
    public Piece(int x, int y, String type, String colour, Boolean takeable, ImageIcon image, int value)
    {
        // initialise instance variables
        this.x = x;
        this.y = y;
        this.type = type;
        this.colour = colour;
        this.image = image;
        label = new JLabel(image);
        size = 8;
        movesMade = 0;
        this.takeable = takeable;
        this.value = value;
    }

    /**
     * An example of a method - replace this comment with your own
     * 
     * @param  y   a sample parameter for a method
     * @return     the sum of x and y 
     */
    public int getX()
    {
        // put your code here
        return x;
    }
    
    public int getY()
    {
        return y;
    }
    
    public String getType()
    {
        return type;
    }
    
    public String getColour()
    {
        return colour;
    }
    
    public ImageIcon getImage()
    {
        return image;
    }
    
    public void changeX(int newX)
    {
        if (newX <= size)
        {
            x = newX;
        }
    }
    
    public void changeY(int newY)
    {
        if (newY <= size)
        {
            y = newY;
        }
    }
    
    public void changeType(String newType)
    {
        type = newType;
    }
    
    public void changeColour(String newColour)
    {
        colour = newColour;
    }
    
    public boolean canBeTaken()
    {
        return takeable;
       
    }
    
    public JLabel getPieceLabel()
    {
        
        return label;
    }
    
    public int getHeight()
    {
        return size;
    }
    
    public int getWidth()
    {
        return size;
    }
    
    public void changeType(String type2, ImageIcon image2, int value2)
    {
        type = type2;
        image = image2;
        label = new JLabel(image2);
        value = value2;
    }
    
    public void increaseMoveCount()
    {
        movesMade = movesMade + 1;
    }
    
    public int getMoveCount()
    {
        return movesMade;
    }
    
    public int getValue()
    {
        return value;
    }
    
    public void changeValue(int newValue)
    {
        value = newValue;
    }
}

