import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
/**
 * Write a description of class Move here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Move
{
    // instance variables - replace the example below with your own
    private int endX;
    private int endY;
    private double rank;
    private int startX;
    private int startY;
    private ArrayList<String> report;

    /**
     * Constructor for objects of class Move
     */
    public Move(int startX, int startY, int endX, int endY)
    {
        // initialise instance variables
        this.endX = endX;
        this.endY = endY;
        this.startX = startX;
        this.startY = startY;
        rank = 0;
    }

    /**
     * An example of a method - replace this comment with your own
     * 
     * @param  y   a sample parameter for a method
     * @return     the sum of x and y 
     */
    public void increaseRank(int add)
    {
       rank = rank + add;
    }
    
    public void decreaseRank(double min)
    {
       rank = rank - min;
    }
    
    public int getEndX()
    {
        return endX;
    }
    
    public int getEndY()
    {
        return endY;
    }
    
    public double getRank()
    {
        return rank;
    }
    
     public int getStartX()
    {
        return startX;
    }
    
    public int getStartY()
    {
        return startY;
    }
    
    public void addReport(ArrayList<String> report)
    {
        report = this.report;
    }
    
    public ArrayList<String> getReport()
    {
        return report;
    }
}
