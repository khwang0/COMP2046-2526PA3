import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

public class Gameboard {
    /**
     * The finish line is 10 steps away from the start line.
     */
    public static final int GOAL_LINE = 10;


    /**
     * The types of play cards. In this version we don't have the rainbow card
     */
    public static final String[] TYPES = {"+1", "+1","+1","+1", "+1","+2", "-1", "-1" };


    /**
     * The colors of the turtles and the players.
     */
    public static final String[] COLORS = {"red", "blue", "green", "yellow", "purple"};
    /**
     * The number of turtles is actually the same as the number of colors.
     */
    public static final int MAX_TURTLES = COLORS.length;


    /*********************************************************************************
     *
     *
     *
     *
     *
     *
     *
     * Updates for PA3
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *********************************************************************************/


    /**
     * Update:
     *
     * The total number of play cards. In PA3, we add 7 meta cards and 3 arrow cards and 2 double arrow cards.
     */
    public static final int MAX_PLAY_CARDS = COLORS.length * TYPES.length + 2 + 5 + 3 + 2;


    /***
     * Update: add current player and turtleList
     */
    private Player player; //current player
    private List<Turtle> turtleList = new ArrayList<>();

    /**
     * Update: add getCurrentPlayer(), getTurtleList()
     *
     */
    public Player getCurrentPlayer() {
        return player;
    }
    public List<Turtle> getTurtleList() { return turtleList; }


    /**
     * Update: add three public constants for the special card types
     */
    public static final String ARROW_TYPE = "Arrow";
    public static final String DOUBLE_ARROW_TYPE = "Double Arrow";
    public static final String RAINBOW = "Rainbow";



    ///////////////////////////////////////////////////////////////////////////

    /**
     * The play cards.
     */
    private final PlayCard[] playCards = new PlayCard[MAX_PLAY_CARDS];
    /**
     * The discard pile.
     */
    private final PlayCard[] discardPile = new PlayCard[MAX_PLAY_CARDS];



    /**
     * The players.
     */
    private final Player[] players;
    /**
     * This is the board which logically has 5 by 10 squares that exactly five sqaures are occupied by the turtles.
     * .
     */
    private final Turtle[][] turtles;





    /**
     * Print the board.
     * 
     * It shall prints the turtles on the board where the starting line is at the left
     * and the finish line is at the right.
     * 
     * For example, it may look like:
     * 
     -----------------------------------------
    |   |   |   |   |   |   |   |   |   |   |
    |   |   |   |   |   |   |   |   |   |   |
    |   |   |   |   |   |   |   |   |   |   |
    |   |   |   |   |   | Y | B |   |   |   |
    |   |   |   |   |   | P | R | G |   |   |
    -----------------------------------------
     *
     * The turtles shall be represented by their color symbols Y P B R G, and the empty squares 
     * shall be represented by spaces.
     * 
     * No space shall be under a turtle.
     * 
     * You should use the symbol dash - and pine | to draw the board.
     */
    public void printBoard() {
        System.out.println("-".repeat(GOAL_LINE * 4 + 1));
        for (int row = MAX_TURTLES -1 ; row >= 0; row--) {
            System.out.print("| ");
            for (int col = 0; col < GOAL_LINE; col++) {
                if (turtles[row][col] != null) {
                    System.out.print(turtles[row][col].getColorSymbol() + " | ");
                } else {
                    System.out.print("  | ");
                }
            }   
            System.out.println();
        }
        System.out.println("-".repeat(GOAL_LINE * 4 + 1));
    }

    /**
     * Default constructor.
     * 
     * It creates a gameboard with 4 players.
     */
    public Gameboard() {
        this(4);
    }

    /**
     * Constructor with the number of players.
     * 
     * It creates a gameboard with the given number of players.
     * 
     * @param numberOfPlayers the number of players
     */
    public Gameboard(int numberOfPlayers) {
        this.turtles = new Turtle[MAX_TURTLES][GOAL_LINE];
        this.players = new Player[numberOfPlayers]; //default 4 players

        String[] myColor = {"red", "blue", "green", "yellow", "purple"};

        // UNCOMMENT for Testing Shuffler
        // Shuffler<String> colorShuffler = new Shuffler<>();
        // colorShuffler.shuffle(myColor);

        players[0] = new HumanPlayer("Java King",myColor[0]);
        for (int i = 1; i < players.length; i++) {
            players[i] = new AIPlayer(myColor[i], turtleList);

        }
        if (players.length >= 2) {
            ((AIPlayer)players[1]).setAlgorithm(new RandomAlgorithm());
        }
        if (players.length >= 3) {
            AIPlayer aiPlayer = (AIPlayer)players[2];
            //set a special algorithm for this AI player
            //TODO - this is the only TODO in this file
            // You should not change other part. Even if you change other part,
            // it will not be graded.
            // aiPlayer.setAlgorithm(...);

            // Hint: the answer should be like 5 to 10 lines only.
            // You should only replace ... by some code without adding
            // any extra line outside the ()
        }
        for (int i = 3; i < players.length; i++) {
            ((AIPlayer)players[i]).setAlgorithm(new RandomAlgorithm());
        }


        
        // UNCOMMENT for Testing Shuffler
        // Shuffler<Player> shuffler = new Shuffler<>();
        // shuffler.shuffle(players);


        preparePlayCard();
        shufflePlayCards();
        //each player draws 5 cards
        for (Player player : players) {
            for (int i = 0; i < 5; i++) {
                drawCard(player);
            }
        }
        //Create 5 turtles and place them on the board
        for (int i = 0; i < MAX_TURTLES; i++) {
            turtles[i][0] = new Turtle(COLORS[i], i,0 );
            turtleList.add(turtles[i][0]);
        }
        printBoard();
        
    }

    /**
     * Start the game.
     * 
     * It starts the game by drawing cards for each player and placing the turtles on the board.
     * 
     * This method is partially finish. Complete the TODO part.
     */
    public void startGame() {
        Scanner scanner = new Scanner(System.in);
        for (int p = 0; !isGameOver(); p = (p + 1) % players.length) {

            /**
             * Update: change local variable player to an instance variable
             */
//            Player player = players[p];
            player = players[p];

            System.out.println("This is the player's turn: " + player.getName());
            System.out.println(player);
            PlayCard card = null;
            debugPlayCards();

            /**
             * Update: change to use the playCard method of the Player class
             */
//            do {
//                System.out.println("Enter the index of the card you want to play: ");
//                try {
//                    int index = scanner.nextInt();
//                    card = player.playCard(index);
//                } catch (Exception e) {
//                    //invalid read of index
//                    scanner.nextLine();
//                }
//
//                if (card == null) {
//                    System.out.println("Invalid card index. Please try again.");
//                }
//            } while (card == null);
            card = player.chooseCardToPlay();
            System.out.println(player.getName() + " plays " + card);


            Turtle turtle = null;
            String color = card.getColor();
            for (int i = 0; i < turtles.length; i++) 
                for (Turtle t : turtles[i]) 
                    if (t != null && t.getColor().equals(color))
                        turtle = t;

            if (turtle == null) {
                System.out.println("No turtle found for the color of the card. Please try again.");
                continue;
            }
            moveTurtle(turtle, card.getSteps());
            printBoard();

            drawCard(player);
            insert(card, discardPile);

            //no more card, move discardPile to playCards
            if (playCards[0] == null) {
                for (int i = 0; i < discardPile.length; i++) {
                    playCards[i] = discardPile[i];
                    discardPile[i] = null;
                }
                shufflePlayCards();
            }
        }
        System.out.println("\n\nGame over!");
        Player winner = getWinner();
        if (winner != null) {
            System.out.println("The winner is " + winner.getName());
            System.out.println("The winner color is " + winner.revealColor());
        } else {
            System.out.println("No winner found. The game is a draw.");
        }

    }

    /**
     * To print out the structure of your playcards and discardPile for debug purpose
     *
     * This method has been completed for you. Don't change.
     */
    private void debugPlayCards() {
        System.out.print("playcards  :");
        for (PlayCard c : playCards)
            System.out.print(c == null ? "o" : "x");
        System.out.println();
        System.out.print("discardPile:");
        for (PlayCard c : discardPile)
            System.out.print(c == null ? "o" : "x");
        System.out.println();
        //check duplicate
        for (PlayCard c : playCards) {
            for (PlayCard d : discardPile)
                if (c == d && c != null)
                    System.out.println("Error! a card in both discardPile and playCards");
        }
    }


    /**
     * Get the winner of the game.
     * 
     * It returns the player who has the turtle on the finish line.
     * 
     * Player who wins the game must have the turtle on the finish line.
     * When the games end if no player has the turtle on the finish line, there will be no winner 
     * and the method shall return null.
     * 
     * If there are multiple players who have the turtle on the finish line, the player who have a 
     * lower turtle of the stack wins.
     */
    public Player getWinner() {
        for (int i = 0; i < MAX_TURTLES; i++) {
            if (turtles[i][GOAL_LINE - 1] != null) {
                String winningColor = turtles[i][GOAL_LINE - 1].getColor();
                for (Player player : players) 
                    if (player.revealColor().equals(winningColor)) 
                        return player;

            }
        }
        return null;
    }



    public static void main(String[] args) {
        if (args.length == 1) {
            try {
                int numberOfPlayers = Integer.parseInt(args[0]);
                if (numberOfPlayers < 2 || numberOfPlayers > MAX_TURTLES) {
                    System.out.println("Number of players must be between 2 and " + MAX_TURTLES);
                    return;
                }
                Gameboard gameboard = new Gameboard(numberOfPlayers);
                gameboard.startGame();
            } catch (NumberFormatException e) {
                System.out.println("run it by \"java Gameboard NO_OF_PLAYERS\", e.g. java Gameboard 4");

            }
            return;
        } 
        Gameboard gameboard = new Gameboard();
        gameboard.startGame();
    }    
    
    /**
     * Prepare the play cards.
     * 
     * It prepares the play cards by creating a play card for each color and type and put them into the playCards array.
     * 
     * Noted, throughout the assignment you cannot use any API such as Arrays, Vector, List, etc. to manipulate the array
     * You are required to use built-in java array only, i.e., int[], String[], Turtle[], etc.
     * 
     */
    public void preparePlayCard() {
        for (int i = 0; i < COLORS.length; i++) {
            for (int j = 0; j < TYPES.length; j++) {
                insert(new PlayCard(COLORS[i], TYPES[j]), playCards);
            }
        }
        //add two meta -1 cards, five meta +1 cards, three arrow cards, 2 double arrow cards
        insert(new RainbowPlayCard("-1", this), playCards);
        insert(new RainbowPlayCard("-1", this), playCards);
        for (int i = 0; i < 5; i++)
            insert(new RainbowPlayCard("+1", this), playCards);
        for (int i = 0; i < 3; i++)
            insert(new ArrowPlayCard(this), playCards);
        for (int i = 0; i < 2; i++)
            insert(new DoubleArrowPlayCard(this), playCards);
    }

    /**
     * Shuffle the play cards.
     * 
     * It shuffles the play cards by randomly swapping the cards in the playCards array.
     * 
     * Noted, throughout the assignment you cannot use any API such as Arrays, Vector, List, etc. to manipulate the array
     * You are required to use built-in java array only, i.e., int[], String[], Turtle[], etc.
     * 
     * A random shuffle - it should be random, running the program twice should give you different results.
     * ThreadLocalRandom.current().nextInt() must be used in this method.
     */
    public void shufflePlayCards() {
        if (playCards == null) {
            System.out.println("No play cards available to shuffle.");
            return;
        }
        
        // UNCOMMENT for Testing Shuffler
        // Shuffler<PlayCard> shuffler = new Shuffler<>();
        // shuffler.shuffle(playCards);
    }

    /**
     * To insert a card to the last position of a cardPile.
     * This method is completed for you. Don't change.
     *
     * @param card
     * @param cardPile
     */
    private void insert(PlayCard card, PlayCard[] cardPile) {
        if (card == null)
            return;
        for (int i = 0; i < cardPile.length; i++) {
            if (cardPile[i] == null) {
                cardPile[i] = card;
                return;
            }
        }
        throw new RuntimeException("Card pile is full when inserting");
    }
    /**
     * Draw a card from the playCards array and give it to the player.
     * 
     * It assigns the top card of the playCards array to the player. The card is removed from the playCards array.
     * Every card in the playCards array is shifted to the left by one position.
     * 
     * i.e. if the cardPile originally contains [A, B, C, D, E], after drawing A, the cardPile will be [B, C, D, E, null].
     * 
     * @param player
     */
    public void drawCard(Player player) {
        if (playCards == null) {
            System.out.println("No play cards available.");
            return;
        }
        player.drawCard(playCards[0]);
        for (int i = 1; i < playCards.length; i++) {
            playCards[i - 1] = playCards[i];
        }
        playCards[playCards.length - 1] = null;
    
    }

    /**
     * Move a turtle on the board.
     * 
     * It moves the turtle on the board by the given number of steps. 
     * According to the rules of the game, the turtle will carry other turtles on its back.
     * The relative vertical position of the turtles being moved needs to be preserved, i.e.
     * if Blue is on the top of Red, after moving Red, Blue should still be on the top of Red.
     * 
     * A turtle at the start line will stay at the start line if it is moved backward.
     * A turtle will arrive the finish line if it move beyond the finish line.
     * 
     * Note: in this assignment, there is no rainbow card.
     * 
     * @param turtle
     * @param steps
     */
    public void moveTurtle(Turtle turtle, int steps) {
        int row = turtle.getRow();
        int col = turtle.getCol(); 
        int newcol = col + steps;
        if (col + steps >= GOAL_LINE) 
            newcol = GOAL_LINE - 1; // Move to the goal line
        int newrow = 0;
        if (newcol <= 0) {
            newcol = 0;
            if (col == 0) return;
        }
        while (turtles[newrow][newcol] != null && newrow < MAX_TURTLES) 
            newrow++;


        if (col == 0) {
            // move to the first available row in the column
            turtles[newrow][newcol] = turtle;
            turtle.setCol(newcol);
            turtle.setRow(newrow);
            for (int i = row ; i < MAX_TURTLES - 1; i++) {
                turtles[i][col] = turtles[i+1][col];
                if (turtles[i][col] != null) {
                    turtles[i][col].setRow(i);
                }
            }
            turtles[MAX_TURTLES - 1][col] = null;
            return;
        }
 

        //otherwise, try moving the turtle and the turtles on it.
        for (int i = row; i < MAX_TURTLES && newrow < MAX_TURTLES; i++) {
            turtles[newrow][newcol] = turtles[i][col];
            turtles[i][col] = null;
            if (turtles[newrow][newcol] != null) {
                turtles[newrow][newcol].setRow(newrow);
                turtles[newrow][newcol].setCol(newcol);
            }
            newrow++;
        }
    }
    /**
     * Check if the game is over.
     * 
     * It checks if the game is over by checking if there is a turtle on the finish line.
     * 
     * @return true if the game is over, false otherwise
     */
    public boolean isGameOver() {
        return turtles[0][GOAL_LINE - 1] != null;
    }
}