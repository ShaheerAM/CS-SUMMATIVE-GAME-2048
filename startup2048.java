import javax.swing.*;
import java.util.*;
import java.io.*;

/**2048 Program
  * Creates a game of 2048 that features a sortable leaderboard, can display recent and previous game results, and can be played with either the provided
  * buttons, WASD, or arrow keys.
  * 
  * Last Modified: January 22, 2023
  * @author Shaheer Majeed
  */

public class startup2048
{
     /**The main method
       */
     public static void main (String [] args) throws IOException
     {
          Model2048 model = new Model2048();  //the 2048 model
          GUI2048 main = new GUI2048(model);  //The 2048 view
          
          //Initialize the Frame
          JFrame f = new JFrame("2048");
          f.setSize(1000,800);
          f.setLocation(250,25);
          f.setResizable(false);
          f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
          f.setContentPane(main);
          f.setVisible(true);
     } //end of main method
}