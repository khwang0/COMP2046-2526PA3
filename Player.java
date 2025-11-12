/**
 * This Player class is modified based on the Player class provided in PA2.
 * This class has been completed for you. 
 * You should not make any change on this class.
 * When we compile your program, we will use this exact same class.
 */

public abstract class Player {
    private final String name;
    private final String color;
    protected final PlayCard[] playCards = new PlayCard[5];

    public String getName() {
        return name;
    }

    public String revealColor() {
        return color;
    }

    public Player(String name, String color) {
        this.name = name;
        this.color = color;
    }

    /****
     * In this method, the player will need to choose a card to play from their hand.
     *
     * In assignment 2, we only have a human player.
     * In assignment 3, we will have both human and AI players.
     *
     * This method will be implemented differently by human and AI players.
     * By referring Gameboard.startGame(), you can see how this method is used.
     * This method should also remove the chosen card from the player's hand.
     *
     * @return the card in the player's hand that the player chooses to play.
     */
    abstract public PlayCard chooseCardToPlay();

    /**
     * In this method, the player will need to choose an option from the given options.
     *
     * This method will be implemented differently by human and AI players.
     *
     *
     * @param type - what type of card that trigger this prompt. The type can be either
     *             "Rainbow+1", "Rainbow-1", "Arrow", or "DoubleArrow" only.
     *
     * @param options - what options that the player can choose from.
     * @return the option that the player chooses.
     */
    abstract public String choosePrompt(String type, String[] options);



    public void drawCard(PlayCard card) {
        for (int i = 0; i < playCards.length; i++) {
            if (playCards[i] == null) {
                playCards[i] = card;
                return;
            }
        }
        System.out.println("No space to draw more cards."); 
    }
    
    // UPDATE: we made this method final.
    public final PlayCard playCard(int index) {
        if (index < 0 || index >= playCards.length || playCards[index] == null) {
            return null;
        }
        PlayCard card = playCards[index];
        playCards[index] = null; // Remove the card after playing
        return card;
    }
    public String toString() {
        //display the player's name and the cards in hand
        String result = name + "(" + color + ") has the following cards: \n";
        for (int i = 0; i < playCards.length; i++) {
            if (playCards[i] != null) {
                result += i + ": " + playCards[i].toString() + "\n";
            }
        }
        return result;
    }
}
