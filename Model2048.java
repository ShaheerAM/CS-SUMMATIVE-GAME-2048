/**Model2048 Class
 * This class processes the functions of 2048, its leaderboard pane, and results pane.
 *
 * @author Shaheer Majeed
 * @since January 22, 2022
*/

import java.util.*;
import java.io.*;
import javax.swing.*;

public class Model2048 extends Object {
     
     //Instance Variables
     private GUI2048 view;                      //The view attatched to the model
     private int [][] values = new int [4][4];  //Stores values of all spots in the grid
     
     private boolean win = false;               //Stores if the game has been won
     private boolean loss = false;              //Stores if the game has been lost
     
     private boolean lbState;                                  //Stores if the leaderboard has contents
     private Scores [] leaderboard = new Scores [1];           //Stores the all time leaderboard of scores
     private Scores [] currentGame;                            //Stores the scores of each round in the current game
     private String results;                                   //Stores the results of a game
     
     private String name;                       //Stores the name of the user
     private int totalScore = 2;                //Stores the total score of the round
     private int moves = 0;                     //Stores the number of moves made in the round
     private int maxRounds;                     //Stores the user selected max round in the game
     private int roundNum = 0;                  //Stores the current round number
     private long timeStarted;                  //Stores a timestamp of when the round was started
     private long timeCompleted;                //Stores a timestamp of when the round was complete
     
     /**Constructor for the model; loads the leaderboard and creates a new value on the grid
       */
     public Model2048 (){
          super ();
          this.loadLeaderboard ();
          this.newValue();
     } //end of constructor
     
     /**Sets the GUI of the model.
       * @param view1 The view to be attatched to this model
       */
     public void setGUI (GUI2048 view1) {
          this.view = view1;
     } //end of setGUI method
     
     //---------------------------------------------------------------------------------------------------SETUP AND RESET METHODS
     
     /**Sets up the game
       * @param userName The name of the user playing
       * @param rounds The maximum number of rounds input by the user
       */
     public void setUpGame (String userName, int rounds) {
          this.name = userName;
          this.maxRounds = rounds;
          this.currentGame = new Scores [maxRounds];
     } //end of setUpGame method
     
     /**Sets up a new round.
       */
     public void newRound () {
          //resets all values to default and increments round by 1
          values = new int [4][4];
          this.newValue();
          this.totalScore = 2;
          this.moves = 0;
          this.win = false;
          this.loss = false;
          this.roundNum += 1;
          this.updateView();
     } //end of newRound method
     
     /**Resets the game to start a new game
       */
     public void resetGame () {
          //generates a game file if a round has been started and a move has been made
          if (this.totalScore > 2 && currentGame != null) {
               if (!this.loss) {
                    this.timeCompleted = System.currentTimeMillis();
                    this.newScore();
               }
               this.exportResults();
          }
          
          //resets all values to default and clears game stats
          this.newRound();
          this.roundNum = 0;
          this.maxRounds = 0;
          this.updateView();
          this.currentGame = null;
     } //end of resetGame method
     
     //---------------------------------------------------------------------------------------------------ACCESSOR METHODS
     
     /**Returns the values of all spots in the grid
       * @return Copy of the contents in the values array attribute
       */
     public int [][] getGridValues () {
          int [][] array = new int [4][4]; //stores a copy of the values array to be returned
          
          //copies grid values to new array and returns it
          for (int row = 0; row < 4; row++) {
               for (int col = 0; col < 4; col++)
                    array [row][col] = this.values[row][col];
          }
          return array;
     } //end of getGridValues method
     
     /**Accesses the if the game has been won
       * @return Boolean of if the game has been won or not
       */
     public boolean getWinState () {
          return this.win;
     } //end of getWinState method
     
     /**Accesses the if the game has been lost
       * @return Boolean of if the game has been lost or not
       */
     public boolean getLossState () {
          return this.loss;
     } //end of getLossState method
     
     /**Accesses the number of moves made in the round
       * @return The number of move made
       */
     public int getMoves() {
          return this.moves;
     } //end of getMoves method
     
     /**Acesses the total score of the round
       * @return The total score of the round
       */
     public int getTotalScore() {
          return this.totalScore;
     } //end of getTotalScore method
     
     /**Acesses the max number of rounds
       * @return The round number
       */
     public int getMaxRounds() {
          return this.maxRounds;
     } //end of getTotalScore method
     
     /**Acesses the current round number
       * @return The round number
       */
     public int getRoundNum() {
          return this.roundNum;
     } //end of getTotalScore method
     
     /**Acesses the results string
       * @return The results string
       */
     public String getResults() {
          return this.results;
     } //end of getTotalScore method
     
     //---------------------------------------LEADERBOARD ACCESSOR METHODS
     
     /**Acesses the state of the leaderboard; implemented to fix nullPointException errors when trying to display an empty leaderboard
       * @return If the first spot on the leaderboard is occupied
       */
     public boolean getLBState () {
          if (this.leaderboard[0] == null)
               this.lbState = false;
          else
               this.lbState = true;
          return this.lbState;
     } //end of getLBState method
     
     /**Acesses the length of the leaderboard
       * @return The round number
       */
     public int getLBLength () {
          return this.leaderboard.length;
     } //end of getLBLength method
     
     /**Acesses the name of the round stored at the specified position of the leaderboard
       * @param index The specified position in the leaderboard
       * @return Name of the stored round
       */
     public String getLBName (int index) {
          return this.leaderboard[index].getName();
     } //end of getLBNames method
     
     /**Acesses the score of the round stored at the specified position of the leaderboard
       * @param index The specified position in the leaderboard
       * @return Score of the stored round
       */
     public int getLBScore (int index) {
          return this.leaderboard[index].getScore();
     } //end of getLBScore method
     
     /**Acesses the number of moves of the round stored at the specified position of the leaderboard
       * @param index The specified position in the leaderboard
       * @return Number of moves of the stored round
       */
     public int getLBMoves (int index) {
          return this.leaderboard[index].getMoves();
     } //end of getLBMoves method
     
     /**Acesses the date the round stored at the specified position of the leaderboard was played on
       * @param index The specified position in the leaderboard
       * @return date of the stored round
       */
     public String getLBDate (int index) {
          return this.leaderboard[index].getDate();
     } //end of getLBDate method
     
     /**Acesses the total time of the round in seconds stored at the specified position of the leaderboard
       * @param index The specified position in the leaderboard
       * @return Time in seconds
       */
     public int getLBTime (int index) {
          return (int)((this.leaderboard[index].getCompTime() - this.leaderboard[index].getStartTime())/1000);
     } //end of getLBTime method
     
     /**Acesses the the lifetime highscore
       * @return Lifetime high score
       */
     public int getHighScore () {
          int highScore = 0; //the lifetime highscore
          
          //locates the high score
          if (this.leaderboard[0] != null) {
               for (int x = 0; x < this.leaderboard.length; x++) {
                    if (this.leaderboard[x].getScore() > highScore)
                         highScore = this.leaderboard[x].getScore();
               }
          }
          
          //use the current round score if it is greater than the previous high score
          if (this.totalScore > highScore)
               highScore = this.totalScore;
          
          return highScore;
     } //end of getHighScore method
     
     //---------------------------------------------------------------------------------------------------MOVE PROCESSING METHODS
     
     /**Processes the move made by the user
       * @param move The move made by the user
       */
     public void processMove (String move) {
          boolean [] moveMade = new boolean [3]; //array of flags to determine if any value has changed
          
          moveMade[0] = this.shiftValues(move);
          moveMade[1] = this.combineTerms(move);
          moveMade[2] = this.shiftValues(move);
          
          //sets start time on first move
          if (moves == 0) {
               this.timeStarted = System.currentTimeMillis();
          }
          
          //creates a new value iff a change has been made as a result of the player's move
          if (moveMade[0] == true || moveMade[1] == true || moveMade[2] == true) {
               this.newValue();
               this.moves++;
          }
          
          this.calculateTotalScore();
          this.checkWinState();
          this.checkLossState();
          this.updateView();
     } //end of processMove method
     
     /**Helper method the processMove method; shifts all values in the grid to desired side
       * @param move The move made by the user
       */
     private boolean shiftValues (String move) {
          boolean changed = false; //flag to determine if any value has changed
          
          if (move.equals("Right")) {
               //move all to the right
               for (int row = 0; row < 4; row++) { //each row
                    for (int col = 3; col >= 0; col--) { //each spot in the row
                         if (this.values [row][col] == 0) {
                              for (int check = col-1; check >= 0; check--) {
                                   if (this.values[row][check] != 0) {
                                        this.values [row][col] = this.values [row][check];
                                        this.values [row][check] = 0;
                                        changed = true;
                                        break;
                                   }
                              }
                         }
                    }
               }
          }
          else if (move.equals("Left")) {
               //move all to the left
               for (int row = 0; row < 4; row++) { //each row
                    for (int col = 0; col < 4; col++) { //each spot in the row
                         if (this.values [row][col] == 0) {
                              for (int check = col+1; check < 4; check++) {
                                   if (this.values[row][check] != 0) {
                                        this.values [row][col] = this.values [row][check];
                                        this.values [row][check] = 0;
                                        changed = true;
                                        break;
                                   }
                              }
                         }
                    }
               }
          }
          else if (move.equals("Up")) {
               //move all to the top
               for (int col = 0; col < 4; col++) { //each column
                    for (int row = 0; row < 4; row++) { //each spot in the column
                         if (this.values [row][col] == 0) {
                              for (int check = row+1; check < 4; check++) {
                                   if (this.values[check][col] != 0) {
                                        this.values [row][col] = this.values [check][col];
                                        this.values [check][col] = 0;
                                        changed = true;
                                        break;
                                   }
                              }
                         }
                    }
               }
          }
          else if (move.equals("Down")) {
               //move all to the bottom
               for (int col = 0; col < 4; col++) { //each column
                    for (int row = 3; row >= 0; row--) { //each spot in the column
                         if (this.values [row][col] == 0) {
                              for (int check = row-1; check >= 0; check--) {
                                   if (this.values[check][col] != 0) {
                                        this.values [row][col] = this.values [check][col];
                                        this.values [check][col] = 0;
                                        changed = true;
                                        break;
                                   }
                              }
                         }
                    }
               }
          }
          
          return changed;
     } //end of shiftValues method
     
     /**Helper method the processMove method; combines terms that of the same value
       * @param move The move made by the user
       */
     private boolean combineTerms (String move) {
          boolean changed = false; //flag to determine if any value has changed
          
          if (move.equals("Right")) {
               for (int row = 0; row < 4; row++) { //each row
                    for (int col = 3; col > 0; col--) { //each spot in the row (right to left)
                         if (this.values [row][col] == this.values[row][col-1] && this.values[row][col] != 0) {
                              this.values [row][col] *= 2;
                              this.values [row][col-1] = 0;
                              changed = true;
                              col--;
                         }
                    }
               }
          }
          else if (move.equals("Left")) {
               for (int row = 0; row < 4; row++) { //each row
                    for (int col = 0; col < 3; col++) { //each spot in the row (left to right)
                         if (this.values [row][col] == this.values[row][col+1] && this.values[row][col] != 0) {
                              this.values [row][col] *= 2;
                              this.values [row][col+1] = 0;
                              changed = true;
                              col++;
                         }
                    }
               }
          }
          else if (move.equals("Up")) {
               for (int col = 0; col < 4; col++) { //each column
                    for (int row = 0; row < 3; row++) { //each spot in the column (top to bottom)
                         if (this.values [row][col] == this.values[row+1][col] && this.values[row][col] != 0) {
                              this.values [row][col] *= 2;
                              this.values [row+1][col] = 0;
                              changed = true;
                              row++;
                         }
                    }
               }
          }
          else if (move.equals("Down")) {
               for (int col = 0; col < 4; col++) { //each column
                    for (int row = 3; row > 0; row--) { //each spot in the column (bottom to top)
                         if (this.values [row][col] == this.values[row-1][col] && this.values[row][col] != 0) {
                              this.values [row][col] *= 2;
                              this.values [row-1][col] = 0;
                              changed = true;
                              row--;
                         }
                    }
               }
          }
          
          return changed;
     } //end of combineTerms method
     
     //---------------------------------------------------------------------------------------------------NEW VALUE CREATION
     
     /**Generates a new value within the grid
       */
     private void newValue () {
          int row; //row of the grid
          int col; //column of the grid
          
          //finds a random empty spot if any are available
          if (this.hasEmptySpot()) {
               while (true) {
                    row = (int)(Math.random()*4);
                    col = (int)(Math.random()*4);
                    if (this.values [row][col] == 0) {
                         break;
                    }
               }
               this.values [row][col] = 2;
          }
     } //end of newValues method
     
     /**Locates all empty spots within the grid
       * @return Whether or not the grid has empty spots left to fill
       */
     private boolean hasEmptySpot () {
          boolean empty = false; //flag which stores if the grid has any empty spots
          
          //searches each row for an empty spot
          for (int row = 0; row < 4; row++) {
               for (int col = 0; col < 4; col++) {
                    if (this.values[row][col] == 0) {
                         empty = true;
                         break;
                    }
               }
               if (empty == true)
                    break;
          }
          return empty;
     } //end of hasEmptySpot method
     
     //---------------------------------------------------------------------------------------------------WIN / LOSS DETERMINATION
     
     /**Determines if 2048 has been reached and the game has been "won"
      */
     private void checkWinState () {
          int largestVal = 0; //largest value within the grid
          
          //finds the largest value within the grid
          for (int row = 0; row < 4; row++) {
               for (int col = 0; col < 4; col++) {
                    if (this.values[row][col] > largestVal)
                         largestVal = this.values[row][col];
               }
          }
          
          //sets win state to true if largest value is greater than 2048
          if (largestVal >= 2048) //set to lower value for easier debugging/testing
               this.win = true;
     } //end of checkWinState method
     
     /**Determines if the game has been lost
      */
     private void checkLossState () {
          boolean canChange = this.hasEmptySpot(); //tracks if a move can be made in the current grid
          
          //searches for equal terms next to each other along each row
          if (!canChange) {
               for (int row = 0; row < 4; row++) { //each row
                    for (int col = 3; col > 0; col--) { //each spot in the row (right to left)
                         if (this.values [row][col] == this.values[row][col-1] && this.values[row][col] != 0) {
                              canChange = true;
                              break;
                         }
                    }
                    if (canChange)
                         break;
               }
          }
          
          //searches for equal terms next to each other along each column iff they haven't already been found in a row
          if (!canChange) {
               for (int col = 0; col < 4; col++) { //each column
                    for (int row = 0; row < 3; row++) { //each spot in the column (top to bottom)
                         if (this.values [row][col] == this.values[row+1][col] && this.values[row][col] != 0) {
                              canChange = true;
                              break;
                         }
                    }
                    if (canChange)
                         break;
               }
          }
          
//          canChange = false; //uncommment for easier debugging/testing of loss behaviour
          
          //sets if the game has been lost iff no further moves can be made
          if (!canChange) {
               this.loss = true;
               this.timeCompleted = System.currentTimeMillis();
               this.newScore();
          }
     } //end of checkLossState method
     
     //---------------------------------------------------------------------------------------------------STATS AND TOTALS
     
     /**Calculates the total score of the round
       */
     private void calculateTotalScore () {
          this.totalScore = this.moves*2+2;
     } //end of calculateTotalScore method
     
     /**Loads the leaderboard from a file
       */
     private void loadLeaderboard () {
          //loads the leaderboard file
          File aFile = new File ("leaderboard.txt"); //file to write to
          Scanner in = null; //scanner used for input
          
          //opens the file
          while (true) {
               try {
                    in = new Scanner (aFile);
                    break;
               }
               catch (FileNotFoundException ex) {
                    //creates leaderboard.txt file
                    try {
                    aFile.createNewFile();
                    }
                    catch (IOException e) {}
               }
          } //end of file opening loop
          in.useDelimiter(";");
          
          //loads scores from file into array line by line
          while (in.hasNextLine()) {
               //grows the leaderboard array for each line after the first
               if (this.leaderboard[0] != null)
                    this.growLeaderboard();
               
               //temporary variables storing input data
               String aName = in.next();
               int aScore = in.nextInt();
               int aMoves = in.nextInt();
               String aDate = in.next();
               long tStart = in.nextLong();
               long tComp = in.nextLong();
               
               //generate score on the leaderboard from loaded info
               this.leaderboard[this.leaderboard.length-1] = new Scores (aName, aScore, aMoves, aDate, tStart, tComp);
               in.nextLine();
          }
          in.close();
     } //end of loadLeaderboard method
     
     /**Writes the leaderboard scores to a file
       */
     private void writeLeaderboard () {
          //load leaderboard file to be written to
          File aFile = new File ("leaderboard.txt"); //file to be written to
          PrintWriter out = null; //printwriter object used to write
          
          //opens the file
          try {
               out = new PrintWriter (aFile);
          }
          catch (FileNotFoundException ex) {} //leaderboard.txt should already be made at this point
          
          //writes round stats to file
          for (int x = 0; x < this.leaderboard.length; x++) {
               out.print(this.leaderboard[x].getName() + ";");
               out.print(this.leaderboard[x].getScore() + ";");
               out.print(this.leaderboard[x].getMoves() + ";");
               out.print(this.leaderboard[x].getDate() + ";");
               out.print(this.leaderboard[x].getStartTime() + ";");
               out.println(this.leaderboard[x].getCompTime() + ";");
          }
          out.close();
     } //end of writeLeaderboard method
     
     /**Exports game results to a file
       */
     private void exportResults () {
          //create file to be written to
          String fileName = this.name + " - " + System.currentTimeMillis() + ".txt"; //generated file name of the game
          File aFile = new File (fileName); //file to write to
          
          //creates results file
          try {
               aFile.createNewFile();
          }
          catch (IOException e) {}
          
          //create printwriter object
          PrintWriter out = null; //printwrite object to write with
          try {
               out = new PrintWriter (aFile);
          }
          catch (FileNotFoundException ex) {} //leaderboard.txt should already be made at this point
          
          //writes stats of each round to results file
          for (int x = 0; x < this.currentGame.length; x++) {
               if (currentGame[x] != null) {
                    out.print(this.currentGame[x].getName() + ";");
                    out.print(this.currentGame[x].getScore() + ";");
                    out.print(this.currentGame[x].getMoves() + ";");
                    out.print(this.currentGame[x].getDate() + ";");
                    out.print(this.currentGame[x].getStartTime() + ";");
                    out.println(this.currentGame[x].getCompTime() + ";");
               }
          }
          out.close();
          this.loadResults(aFile);
     } //end of exportResults method
     
     /**Loads game results from a file
       * @param resultsFile The file to load results from
       */
     public void loadResults (File resultsFile) {
          Scanner in = null; //scanner used for file reading
          
          //opens file
          try {
               in = new Scanner (resultsFile);
          }
          catch (FileNotFoundException e) {}
          in.useDelimiter(";");
          
          //header
          this.results = resultsFile.getName() + ":\nRound\tName\t\tScore\tMoves\tTime(sec.)\tDate\n\n";
          
          //loads all round results of the game onto a string
          int roundCount = 1; //counter for the number of rounds in the game results stored in the file
          while (in.hasNextLine()) {
               //adds round number
               this.results = this.results.concat(Integer.toString(roundCount));
               this.results = this.results.concat("\t");
               
               //adds the name of the user
               String nameString = in.next();
               if (nameString.length() > 10) //trims name to 10 characters
                    nameString = nameString.substring(0,10);
               this.results = this.results.concat(nameString);
               if (nameString.length() < 9)
                    this.results = this.results.concat("\t");
               this.results = this.results.concat("\t");
               
               //adds score and number of moves
               this.results = this.results.concat(in.next());
               this.results = this.results.concat("\t");
               this.results = this.results.concat(in.next());
               this.results = this.results.concat("\t");
               
               //adds date completed and time it took for the round to complete
               String date = in.next();                 //stores the date the round was completed
               long timeS = in.nextLong();              //stores the start timestamp of the round
               long timeC = in.nextLong();              //stores the completion stimestamp of the round
               int timeT = (int)((timeC - timeS)/1000); //stores the total time in seconds the round lasted
               
               this.results = this.results.concat(Integer.toString(timeT));
               this.results = this.results.concat("\t");
               this.results = this.results.concat(date);
               this.results = this.results.concat("\n");
               
               in.nextLine();
               roundCount++;
          } //end of line reading loop
          in.close();
          
          this.view.updateResults();
     } //end of loadResults method
     
     /**Generates a new score when the round ends for both the leaderboard and current game array
       */
     private void newScore () {
          //grows leaderboard if it is not first round ever played
          if (this.leaderboard[0] != null)
               this.growLeaderboard();
          
          //gets the date
          Date time = new Date(this.timeCompleted); //date object
          String timeS = time.toString(); //string with the date
          
          //creates new score on both leaderboard and currentGame and updates the leaderboard
          this.leaderboard[this.leaderboard.length-1] = new Scores (this.name, this.totalScore, this.moves, timeS, this.timeStarted, this.timeCompleted);
          this.currentGame[this.roundNum-1] = new Scores (this.name, this.totalScore, this.moves, timeS, this.timeStarted, this.timeCompleted);
          this.writeLeaderboard();
     } //end of newScore method
     
     /**Grows the length of the leaderboard array by 1
       */
     private void growLeaderboard () {
          Scores [] newLB = new Scores [this.leaderboard.length + 1]; //new grown array
          
          //copies all elements in leaderboard to grown array
          for (int x = 0; x < leaderboard.length; x++) {
               newLB [x] = leaderboard [x];
          }
          
          this.leaderboard = newLB;
     } //end of growLeaderboard method
     
     //---------------------------------------------------------------------------------------------------LEADERBOARD SORTING
     
     /**Sorts the leaderboard using a selection sort algorithm
       * @param sortOption the sorting option selected by the user
       */
     public void sortLeaderboard (String sortOption) {
          Scores temp; //temporary variable to hold a score
          
          //selection sort loop; sorts from least to greatest
          for (int x = 1; x < this.leaderboard.length; x++) {
               temp = this.leaderboard [x];
               
               //sets value of temp score used for comparisons according to selected sort Option
               long tempValue = this.getTempValue(sortOption, temp);
               
               for (int checking = x-1; checking >= 0; checking--) {
                    //sets value being checked according to selected sort Option
                    long checkValue = this.getCheckValue(sortOption, checking);
                    
                    //makes shifts to the leaderboard based on selected option least to greatest
                    if (checkValue > tempValue) {
                         this.leaderboard [checking+1] = this.leaderboard [checking];
                         if (checking - 1 < 0) {
                              this.leaderboard [checking] = temp;
                              break;
                         }
                    }
                    else {
                         this.leaderboard [checking+1] = temp;
                         break;
                    }
               } //end of inside sorting loop
          } //end of outside sorting loop
          
          //reverses the leaderboard to highest to lowest if necessary
          if (sortOption.equals("Most Recent") || sortOption.equals("Score (highest-lowest)") || sortOption.equals("Moves (highest-lowest)") || sortOption.equals("Time (highest-lowest)")) {
               this.reverseLeaderboard();
          }
          this.updateView();
     } //end of sortLBRecent
     
     /**Gets the value of the temp score for desired sorting option
       * @param sortOption The selected sorting option by the user
       * @param temp The temporary score in the sorting algorithm
       */
     private long getTempValue (String sortOption, Scores temp) {
          long tValue = 0; //tempValue to be returned
          
          if (sortOption.equals("Most Recent") || sortOption.equals("Oldest"))
               tValue = temp.getCompTime();
          else if (sortOption.equals("Score (highest-lowest)") || sortOption.equals("Score (lowest-highest)"))
               tValue = temp.getScore();
          else if (sortOption.equals("Moves (highest-lowest)") || sortOption.equals("Moves (lowest-highest)"))
               tValue = temp.getMoves();
          else if (sortOption.equals("Time (highest-lowest)") || sortOption.equals("Time (lowest-highest)")) 
               tValue = temp.getCompTime() - temp.getStartTime();
          
          return tValue;
     } //end of getTempValue method
     
     /**Gets the value of the score being checked for desired sorting option
       * @param sortOption The selected sorting option by the user
       * @param checking The index of the leaderboard item being checked
       */
     private long getCheckValue (String sortOption, int checking) {
          long cValue = 0; //checkValue to be returned
          
          if (sortOption.equals("Most Recent") || sortOption.equals("Oldest"))
               cValue = this.leaderboard[checking].getCompTime();
          else if (sortOption.equals("Score (highest-lowest)") || sortOption.equals("Score (lowest-highest)"))
               cValue = this.leaderboard[checking].getScore();
          else if (sortOption.equals("Moves (highest-lowest)") || sortOption.equals("Moves (lowest-highest)"))
               cValue = this.leaderboard[checking].getMoves();
          else if (sortOption.equals("Time (highest-lowest)") || sortOption.equals("Time (lowest-highest)"))
               cValue = this.leaderboard[checking].getCompTime() - this.leaderboard[checking].getStartTime();
          
          return cValue;
     } //end of getCheckValue method
     
     /**Reverses the order of the leaderboard
       */
     private void reverseLeaderboard () {
          Scores temp; //temporory variable to hold a score
          
          //swaps leaderboard values from the outside towards the center of the array
          for (int x = 0; x < this.leaderboard.length/2; x++) {
               temp = this.leaderboard[x];
               this.leaderboard[x] = this.leaderboard[this.leaderboard.length-1-x];
               this.leaderboard[this.leaderboard.length-1-x] = temp;
          }
     } //end of reverseLeaderboard method
     
     //---------------------------------------------------------------------------------------------------VIEW UPDATING
     
     /**Updates the view
       */
     private void updateView ()
     {
          this.view.update();
     } //end of updateView method
     
     /**Enables or disables the movement buttons
       * @param enabled Whether to enable or disable the buttons
       */
     public void setButtonsEnabled (boolean enabled) {
          if (enabled)
               this.view.enableButtons();
          else
               this.view.disableButtons();
     } //end of setButtonsEnabled buttons
} //end of Model2048 class