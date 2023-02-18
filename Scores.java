/**Scores Class
 * This class is used to create objects which store scores of individual rounds.
 *
 * @author Shaheer Majeed
 * @since January 20, 2023
*/

public class Scores
{
     //instance variables
     private String name;       //name of the player who completed the round
     private int score;         //score achieived in the round
     private int numMoves;      //number of moves made in the round
     private String dateTime;   //date and time of the round
     private long timeStarted;  //timestamp of when the round was started
     private long timeComplete; //timestamp of when the round was completed
     
     /**Constructor for MoveController object
       * @param aName Name of the player who completed the round
       * @param aScore Score achieived in the round
       * @param aMoves Number of moves made in the round
       * @param date Date and time of the round
       * @param timeS Timestamp of when the round was started
       * @param timeC Timestamp of when the round was completed
       */
     public Scores (String aName, int aScore, int aMoves, String date, long timeS, long timeC)
     {
          this.name = aName;
          this.score = aScore;
          this.numMoves = aMoves;
          this.dateTime = date;
          this.timeStarted = timeS;
          this.timeComplete = timeC;
     } //end of constructor
     
     /**Accesses the name of the player
       * @return The name of the player
       */
     public String getName () {
          return this.name;
     } //end of getName method
     
     /**Accesses the score of the round
       * @return The score of the round
       */
     public int getScore () {
          return this.score;
     } //end of getScore method
     
     /**Accesses the number of moves made in the round
       * @return The number of moves made in the round
       */
     public int getMoves () {
          return this.numMoves;
     } //end of getMoves method
     
     /**Accesses the date the round was completed
       * @return The date the round was completed
       */
     public String getDate () {
          return this.dateTime;
     } //end of getDate method
     
     /**Accesses the timestamp of when the round was started
       * @return The timestamp of when the round was started
       */
     public long getStartTime () {
          return this.timeStarted;
     } //end of getStartTime method
     
     /**Accesses the timestamp of when the round was completed
       * @return The timestamp of when the round was completed
       */
     public long getCompTime () {
          return this.timeComplete;
     } //end of getCompTime method
} //end of Scores class