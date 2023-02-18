/**GUI2048 Class
  * This class output the view of 2048 and its features
  *
  * @author Shaheer
  * @since January 22, 2023
  */

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;               

public class GUI2048 extends JPanel
{
     //Instance Variables
     private GridSpot[][] grid;                               //Creates a 4x4 grid of gridSpots
     private GridLayout gridLay = new GridLayout(4,4);        //4x4 layout for the grid array
     private Model2048 model;                                 //The model attached to the view
     
     private JFileChooser fileChooser = new JFileChooser();   //The file chooser object to load a file to the view
     
     private JLabel nameLabel = new JLabel("Enter Players Name: ", JLabel.RIGHT);         //Label asking user for the players' name
     private JTextField playerName = new JTextField(10);                                  //User inputs player name
     private JLabel inputRounds = new JLabel("Enter Maximum Rounds: ", JLabel.RIGHT);     //Label asking user for number of rounds
     private JTextField numRounds = new JTextField(10);                                   //User input for number of rounds
     private JLabel instructions = new JLabel("Press OK to continue. Then use the buttons, WASD, or arrow keys to move.");         //instructs the user to get started
     private JButton userInput =  new JButton("OK");                           //The enter button to confirm users input
     
     private JLabel currentRounds = new JLabel("Round #:   ");                 //The current game round
     
     private JLabel score = new JLabel("Score:   ");                           //The current game score
     private JLabel highScore = new JLabel("High Score:   ");                  //The current high score
     
     private JLabel status = new JLabel("");                                   //Declares the status of the game
     private JButton up = new JButton("Up");                                   //The up button
     private JButton down = new JButton("Down");                               //The down button
     private JButton left = new JButton("Left");                               //The left button
     private JButton right = new JButton("Right");                             //The right button
     private JButton space1 = new JButton("");                                 //Empty spots that do nothing
     private JButton space2 = new JButton("");                                 //Empty spots that do nothing
     private JButton space3 = new JButton("");                                 //Empty spots that do nothing
     private JButton space4 = new JButton("");                                 //Empty spots that do nothing
     private JButton space5 = new JButton("");                                 //Empty spots that do nothing
     
     private JButton quit = new JButton("QUIT");                               //The end game button
     private JButton newGame = new JButton("NEW GAME");                        //The new game button
     
     private JTabbedPane statsWindow = new JTabbedPane();                    //Displays a tab for the leaderboard and a tab for the match results
     
     private JLabel leaderBoardLabel = new JLabel("Leaderboard: ");            //Title of the leaderboard text area
     private JTextArea leaderboard = new JTextArea(12,66);                     //Displays list of previous scores
     private JScrollPane leaderboardScoll = new JScrollPane(leaderboard);      //The Scroll Pane for the leaderboard text area
          
     String[] sortStrings = {"Most Recent", "Oldest", "Score (highest-lowest)",      //Array list of String options to sort the leaderboard
                             "Score (lowest-highest)", "Moves (highest-lowest)", 
                             "Moves (lowest-highest)", "Time (highest-lowest)", 
                             "Time (lowest-highest)"};
     private JComboBox sortingOptions = new JComboBox(sortStrings);                  //The combo box to view all options to sort leaderboard
     
     private JLabel resultsLabel = new JLabel("Game Results: ");               //Title of the results text area
     private JTextArea results = new JTextArea(12,66);                         //Displays the result of current game
     private JScrollPane resultsScroll = new JScrollPane(results);             //The Sroll Pane for the results text area
     
     private JButton loadFile = new JButton("Load File");                      //The button that will load a file to the view

     private JPanel scorePanel = new JPanel();                                 //Contains the score and highscore buttons
     private JPanel gridPanel = new JPanel();                                  //Contains the grid
     private JPanel arrowPanel = new JPanel();                                 //Contans all the move buttons
     private JPanel movementPanel = new JPanel ();                             //contains arrowPanel and status label
     private JPanel userDataPanel = new JPanel();                              //Contains the users information
     private JPanel startUpPanel = new JPanel();                               //Contains userDataPanel and instructions
     private JPanel miscButtonsPanel = new JPanel();                           //Contains the quit and newGame button
     private JPanel leaderBoardPanel = new JPanel();                           //Contains all the leaderboard information
     private JPanel resultsPanel = new JPanel();                               //Contains the results of each round
     
     private JPanel leftPanel = new JPanel();                                  //Contains all components on the left side
     private JPanel rightPanel = new JPanel();                                 //Contains all components on the right side
     private JPanel bottomPanel = new JPanel();                                //Contains all components on the bottom side
     
     /** Constructer for the view
       * @param aModel - The model the view is attached to
       */
     public GUI2048(Model2048 aModel)
     {
          super();
          this.model = aModel;
          this.model.setGUI(this);
          this.layoutView();
          this.setFocusable(true);
          this.registerControllers();
          this.update();
     }//end of GUI2048 Contructor
     
     /** Draws the initial layout for the GUI
       */ 
     private void layoutView()
     {
          //**********Score Labels**********//
          scorePanel.setLayout(new GridLayout(1,3));
          scorePanel.add(currentRounds);
          scorePanel.add(score);
          scorePanel.add(highScore);
          
          //**********2048 Grid**********//
          grid = new GridSpot[4][4];
          
          gridPanel.setLayout(gridLay); //
          //space of the lines between the gridspots
          gridLay.setHgap(10);              
          gridLay.setVgap(10);
          //draws a 4x4 grid
          for(int i = 0; i < 4; i++)
          {
               for(int j = 0; j < 4; j++)
               {
                    grid[i][j] = new GridSpot();
                    grid[i][j].setPreferredSize (new Dimension(100,100));
                    gridPanel.add(grid[i][j]);
               }//End of For loop
          }//End of For loop
          
          //**********Arrow Keys**********//
          arrowPanel.setLayout(new GridLayout(3,3));
          arrowPanel.add(space1);
          arrowPanel.add(up);
          this.up.setEnabled(false);
          arrowPanel.add(space2);
          arrowPanel.add(left);
          this.left.setEnabled(false);
          arrowPanel.add(space3);
          arrowPanel.add(right);
          this.right.setEnabled(false);
          arrowPanel.add(space4);
          arrowPanel.add(down);
          this.down.setEnabled(false);
          arrowPanel.add(space5);
          
          //removes border for space buttons
          space1.setBorderPainted(false);
          space2.setBorderPainted(false);
          space3.setBorderPainted(false);
          space4.setBorderPainted(false);
          space5.setBorderPainted(false);
          //removes opacity for space buttons
          space1.setOpaque(false);
          space2.setOpaque(false);
          space3.setOpaque(false);
          space4.setOpaque(false);
          space5.setOpaque(false);
          //Disables space buttons
          space1.setEnabled(false);
          space2.setEnabled(false);
          space3.setEnabled(false);
          space4.setEnabled(false);
          space5.setEnabled(false);
          
          //Sets the border of the arrows
          arrowPanel.setBorder(new EmptyBorder(20,0,40,0));
          
          //Adds status and movement buttons to movement panel
          status.setVisible(false);
          status.setHorizontalAlignment(JLabel.CENTER);
          movementPanel.setLayout(new BorderLayout());
          movementPanel.add(status, BorderLayout.NORTH);
          movementPanel.add(arrowPanel, BorderLayout.SOUTH);
          
          //**********User Info Declaration**********//
          userDataPanel.setLayout(new GridLayout(2,2));
          startUpPanel.setLayout(new BorderLayout());
          userDataPanel.add(nameLabel);
          userDataPanel.add(playerName);
          userDataPanel.add(inputRounds);
          userDataPanel.add(numRounds);
          instructions.setHorizontalAlignment(JLabel.CENTER);
          startUpPanel.add(userDataPanel, BorderLayout.NORTH);
          startUpPanel.add(instructions, BorderLayout.SOUTH);
          userDataPanel.setBorder(new EmptyBorder(0,0,20,0));
          startUpPanel.setBorder(new EmptyBorder(0,0,20,0));
          
          //**********Miscellaneous Buttons**********//
          miscButtonsPanel.setLayout(new GridLayout(1,3));
          miscButtonsPanel.add(userInput);
          miscButtonsPanel.add(newGame);
          miscButtonsPanel.add(quit);
                             
          //**********LeaderBoard Stats and Sorting Options**********//
          leaderboard.setEditable(false);
          leaderBoardPanel.setLayout(new BorderLayout());
          leaderBoardPanel.add(leaderBoardLabel, BorderLayout.WEST);
          leaderboardScoll.setBounds(5,5,100,100);
          leaderBoardPanel.add(leaderboardScoll, BorderLayout.SOUTH);
          
          sortingOptions.setSelectedIndex(1);
          leaderBoardPanel.add(sortingOptions, BorderLayout.EAST);
          
          //**********Results and Load File Button**********//
          results.setEditable(false);
          resultsPanel.setLayout(new BorderLayout());
          resultsPanel.add(resultsLabel, BorderLayout.WEST);
          leaderboardScoll.setBounds(5,5,100,100);
          resultsPanel.add(resultsScroll, BorderLayout.SOUTH);
          resultsPanel.add(loadFile, BorderLayout.EAST);
                    
          //**********Left View**********//
          leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
          leftPanel.add(scorePanel);
          leftPanel.add(gridPanel);
          
          //**********Right View*********//
          rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
          rightPanel.add(startUpPanel);
          rightPanel.add(movementPanel);
          rightPanel.add(miscButtonsPanel);
          rightPanel.setBorder(new EmptyBorder(0,50,0,0));
          
          //**********Bottom View - Tabbed Pane**********//
          statsWindow.add("Leaderboard",leaderBoardPanel);
          statsWindow.add("Match Results",resultsPanel);
          bottomPanel.setBorder(new EmptyBorder(20,0,0,0));
          bottomPanel.setLayout(new FlowLayout());
          bottomPanel.add(statsWindow);
          
          //**********Main View**********//
          this.add(leftPanel);
          this.add(rightPanel);
          this.add(bottomPanel);
          
          this.leaderboard.setFont(new Font (Font.MONOSPACED, Font.PLAIN, 12));  //Sets font of text in leaderboard
     }//End of layoutView method
     
     /**Assigns the controllers to the buttons and keys
       */ 
     private void registerControllers()
     {
          //**********Move Controller*********//
          MoveController moveController = new MoveController (this.model);
          this.up.addActionListener (moveController);
          this.down.addActionListener (moveController);
          this.left.addActionListener (moveController);
          this.right.addActionListener (moveController);
          
          //**********Quit Controller*********//
          QuitController quitController = new QuitController (this.model);
          this.quit.addActionListener(quitController);
          
          //**********New Game Controller*********//
          NewGameController newGameController = new NewGameController (this.model, this.startUpPanel, this.userInput, this.status, this.statsWindow);
          this.newGame.addActionListener(newGameController);
          
          //**********OK Controller*********//
          OKController okController = new OKController(this.model, this.startUpPanel, this.playerName, this.numRounds, this.userInput, this.status);
          this.userInput.addActionListener(okController);
          
          //**********Leaderboard Controller*********//
          LeaderboardController leaderboardCont = new LeaderboardController (this.model, this.sortingOptions);
          this.sortingOptions.addActionListener(leaderboardCont);
          
          //**********File Controller*********//
          FileController fileController = new FileController(this.model, this, this.fileChooser);
          this.loadFile.addActionListener(fileController);
          
          //**********WASD Controller*********//
          WASDController wasdController = new WASDController(this.model);
          this.up.addKeyListener(wasdController);
          this.down.addKeyListener(wasdController);
          this.right.addKeyListener(wasdController);
          this.left.addKeyListener(wasdController);
          this.addKeyListener(wasdController);
     }//End of registerControllers Method
     
     /** Based on the data provided to the model, the update method redraws the GUI
       */ 
     public void update()
     {
          //sets the value of the gridspots
          int [][] gridValue = this.model.getGridValues();
          for(int i = 0; i < 4; i++)
          {
               for(int j = 0; j < 4; j++)
               {
                    this.grid[i][j].setValue(gridValue[i][j]);
               }
          }
          
          //Sets score, rounds, and highscore
          this.score.setText("Score: " + this.model.getTotalScore());
          this.currentRounds.setText("Round: " + this.model.getRoundNum() + " of " + this.model.getMaxRounds());
          this.highScore.setText("High Score: " + this.model.getHighScore());
          
          //Sets the status of the game
          if (this.model.getWinState()) {
               this.status.setVisible(true);
               this.status.setText("Congrats on reaching 2048! You won, but can keep playing :)");
          }
          if (this.model.getLossState()) {
               this.disableButtons();
               this.status.setVisible(true);
               if (this.model.getRoundNum() == this.model.getMaxRounds()) {
                    //set text of jlabel to say "Game over! Press New Game to export results and play again!" or something
                    this.status.setText("Game over! Press New Game to export results and play again!");
               }
               else {
                    this.userInput.setEnabled(true);
                    this.status.setText("Round Lost! Press OK to continue to next round.");
               }
          }
          //calls the updateLeaderboard method 
          this.updateLeaderboard();
     }//End of update method
     
     /** Updates the leaderboard text area
       */
     private void updateLeaderboard() {
          String leaderboard1 = "Name\t\tScore\tMoves\tTime(sec.)\tDate\n\n";  //leaderboard string to be output
          
          //Updates if leaderboard is not empty
          if (this.model.getLBState()) {
               //Attaches all stats of each round to the leaderboard string
               for (int i = 0; i < this.model.getLBLength(); i++) {
                    //Attaches first 10 characters of player name attached to round
                    String nameString = this.model.getLBName(i);   //Temporarily stores name of player to be modified before being added to the leaderboard string
                    if (nameString.length() > 10) //trims name to 10 characters
                         nameString = nameString.substring(0,10);
                    leaderboard1 = leaderboard1.concat(nameString);
                    leaderboard1 = leaderboard1.concat("\t");
                    if (nameString.length() < 9)
                         leaderboard1 = leaderboard1.concat("\t");
                    //Attaches round score, moves, time, and date completed
                    leaderboard1 = leaderboard1.concat(Integer.toString(this.model.getLBScore(i)));
                    leaderboard1 = leaderboard1.concat("\t");
                    leaderboard1 = leaderboard1.concat(Integer.toString(this.model.getLBMoves(i)));
                    leaderboard1 = leaderboard1.concat("\t");
                    leaderboard1 = leaderboard1.concat(Integer.toString(this.model.getLBTime(i)));
                    leaderboard1 = leaderboard1.concat("\t\t");
                    leaderboard1 = leaderboard1.concat(this.model.getLBDate(i));
                    leaderboard1 = leaderboard1.concat("\n");
               }
          }
          //Output leaderboard
          this.leaderboard.setText(leaderboard1);
          //Focuses on this GUI2048 panel
          this.requestFocus();
     }//End of updateLeaderboard method
     
     /**Enable buttons when user presses okay
       */
     public void enableButtons () {
          this.up.setEnabled(true);
          this.down.setEnabled(true);
          this.left.setEnabled(true);
          this.right.setEnabled(true);
     }//End of enabledButtons method
     
     /**Disable buttons in the beginning, when new game is pressed, and when player losses
       */
     public void disableButtons () {
          this.up.setEnabled(false);
          this.down.setEnabled(false);
          this.left.setEnabled(false);
          this.right.setEnabled(false);
     }//End of disabledButtons method
     
     /**Updates the results area in the model
       */
     public void updateResults () {
          results.setText(model.getResults());
     }//End of updateResults method
     
}//End of GUI2048 class