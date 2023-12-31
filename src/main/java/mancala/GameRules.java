package mancala;
import java.io.Serializable;
/**
 * Abstract class representing the rules of a Mancala game.
 * KalahRules and AyoRules will subclass this class.
 */
public abstract class GameRules implements Serializable {
    final private MancalaDataStructure gameBoard;
    private int currentPlayer = 1; // Player number (1 or 2)
    private static final long serialVersionUID = 1L; 

    /**
     * Constructor to initialize the game board.
     */
    public GameRules() {
        gameBoard = new MancalaDataStructure();
        gameBoard.setUpPits();
    }
    /**
     * Get the number of stones in a pit.
     *
     * @param pitNum The number of the pit.
     * @return The number of stones in the pit.
     */
    public int getNumStones(final int pitNum) {
        return gameBoard.getNumStones(pitNum);
    }
    /*
     * returns nothing 
     * checks if the side is empty and if it is empty it adds all the stones on the opposite players side to their store 
     */
    public void collectRemainingStonesIfSideEmpty() {
        if (isSideEmpty(1)) {
            for (int i = 7; i <= 12; i++) {
                final int stones = gameBoard.removeStones(i);
                gameBoard.addToStore(2, stones);
            }
        } else if (isSideEmpty(7)) {
            for (int i = 1; i <= 6; i++) {
                final int stones = gameBoard.removeStones(i);
                gameBoard.addToStore(1, stones);
            }
        }
    }
    /*
     * @return the current player 
     */

    public int getPlayer(){
        return currentPlayer;
    }
    /**
     * Get the game data structure.
     *
     * @return The MancalaDataStructure.
     */
    MancalaDataStructure getDataStructure() {
        return gameBoard;
    }
    /**
     * switches the players 
     */

    public void switchPlayer(){
        this.currentPlayer = this.currentPlayer % 2 + 1;
    }

    /**
     * Check if a side (player's pits) is empty.
     *
     * @param pitNum The number of a pit in the side.
     * @return True if the side is empty, false otherwise.
     */
    boolean isSideEmpty(final int pitNum) {
        // This method can be implemented in the abstract class.
        int i;
        if(pitNum >= 1 && pitNum <= 6){
            for(i=1;i<7;i++){
                if(gameBoard.getNumStones(i) > 0){
                    return false;
                }
            }
        }
    else{
        for(i=7;i<=12;i++){
            if(gameBoard.getNumStones(i) > 0){
                return false;
            }
        }
    }
    return true;
    }

    /**
     * Set the current player.
     *
     * @param playerNum The player number (1 or 2).
     */
    public void setPlayer(final int playerNum) {
        currentPlayer = playerNum;
    }

    /**
     * Perform a move and return the number of stones added to the player's store.
     *
     * @param startPit  The starting pit for the move.
     * @param playerNum The player making the move.
     * @return The number of stones added to the player's store.
     * @throws InvalidMoveException If the move is invalid.
     */
    public abstract int moveStones(int startPit, int playerNum) throws InvalidMoveException;
    /**
     * Distribute stones from a pit and return the number distributed.
     *
     * @param startPit The starting pit for distribution.
     * @return The number of stones distributed.
     */
    abstract int distributeStones(int startPit);
    /**
     * Capture stones from the opponent's pit and return the number captured.
     *
     * @param stoppingPoint The stopping point for capturing stones.
     * @return The number of stones captured.
     */
    abstract int captureStones(int stoppingPoint);
    /**
     * Register two players and set their stores on the board.
     *
     * @param one The first player.
     * @param two The second player.
     */
    public void registerPlayers(final Player one, final Player two) {
        // this method can be implemented in the abstract class.
        final Store storeOne = new Store();
        final Store storeTwo = new Store();
        storeOne.setOwner(one);
        storeTwo.setOwner(two);
        one.setStore(storeOne);
        two.setStore(storeTwo);
        gameBoard.setStore(storeOne, 1); // Method to be defined in MancalaDataStructure
        gameBoard.setStore(storeTwo, 2);

        /* make a new store in this method, set the owner
         then use the setStore(store,playerNum) method of the data structure*/
    }
    /**
     * Reset the game board by setting up pits and emptying stores.
     */
    public void resetBoard() {
        gameBoard.setUpPits();
        gameBoard.emptyStores();
    }

    @Override
    public String toString() {
        String result = "Pits:\n";

        for (int i = 12; i >= 7; i--) {
            result += Integer.toString(getNumStones(i)) + " ";
        }

        result += "\n";

        for (int i = 1; i <= 6; i++) {
            result += Integer.toString(getNumStones(i)) + " ";
        }

        result += "\n\n";

        Store playerStore = getDataStructure().getStore(1);
        result += playerStore.toString() + "\n";

        playerStore = getDataStructure().getStore(2);
        result += playerStore.toString() + "\n";

        return result;
    }
}
