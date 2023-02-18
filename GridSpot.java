/**GridSpot Class
  * This class creates a single grid spot, assigning a value and corresponding colour
  * 
  * @author Shaheer Majeed
  * @since January 22, 2023
  */

import java.awt.*;
import javax.swing.*;

public class GridSpot extends JComponent
{
     //Instance Variables
     private int value; //The value assigned to the gridspot
     
     /**Constructor for GridSpot object */
     public GridSpot()
     {
          super();
          this.setPreferredSize(new Dimension(500,500));
     }//End of GridSpot constructor
     
     /** The graphics drawing that creates the grid spot
       * @param g - The graphics object used to draw
       */
     public void paintComponent(Graphics g)
     {
          super.paintComponent(g);
          
          Graphics2D g2 = (Graphics2D) g;
          g2.scale (this.getWidth() / 50, this.getHeight() / 50);
          g2.setStroke (new BasicStroke (1.0F / this.getWidth()));
          
          //Assigns a colour to each possible value of the grid spot
          if (value == 0){
               g2.setColor(new Color(204, 192, 179));
          }
          else if (value == 2){
               g2.setColor(new Color(238, 228, 218));
          }
          else if (value == 4){
               g2.setColor(new Color(237, 224, 200));
          }
          else if (value == 8){
               g2.setColor(new Color(242, 177, 121));
          }
          else if (value == 16){
               g2.setColor(new Color(245, 149, 99));
          }
          else if (value == 32){
               g2.setColor(new Color(246, 124, 95));
          }
          else if (value == 64){
               g2.setColor(new Color(246, 94, 59));
          }
          else if (value == 128){
               g2.setColor(new Color(237, 207, 114));
          }
          else if (value == 256){
               g2.setColor(new Color(237, 204, 97));
          }
          else if (value == 512){
               g2.setColor(new Color(237, 200, 80));
          }
          else if (value == 1024){
               g2.setColor(new Color(237, 197, 63));
          }
          else if (value == 2048){
               g2.setColor(new Color(237, 194, 46));
          }
          else if (value > 2048){
               g2.setColor(Color.BLACK);
          }
          g2.fillRect(0,0,50,50);
          
          //sets the colour of the value 
          if (value == 2 || value == 4)
               g2.setColor(new Color (128,64,32));
          else
               g2.setColor(Color.WHITE);
          
          //draws the value onto the grid spot
          String valueString = Integer.toString(this.value);
          g2.drawString(valueString,5,30); 
     }//End of paintComponent method
     
     /**Set the value of the gridSpot
       * @param myValue - The value assigned to the grid spot
       */
     public void setValue(int myValue)
     {
          this.value = myValue;
          this.repaint();
     }//end of setValue method
}//End of GridSpot class

