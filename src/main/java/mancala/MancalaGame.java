package mancala;

import java.io.Serializable;
import java.util.ArrayList;
public class MancalaGame implements Serializable {
    private static final long serialVersionUID = 1L; 
    private GameRules gameRules; // This replaces the Board
   final private ArrayList<Player> players;
    private Player currentPlayer;
    private static final int PLAYER_TWO_MAX = 7;
    private static final int PLAYER_ONE = 1;

    public MancalaGame() {
        this.gameRules = new KalahRules();
        this.players = new ArrayList<>();
    }
    public GameRules getBoard(){
        return this.gameRules;
    }
    public void setBoard(final GameRules theBoard){
        this.gameRules = theBoard;
    }
    public int getNumStones(final int pitNum){
        return gameRules.getNumStones(pitNum);
    }
    public void setPlayers(final Player onePlayer, final Player twoPlayer) {
       players.add(onePlayer);
       players.add(twoPlayer);
       gameRules.registerPlayers(onePlayer,twoPlayer);
       setCurrentPlayer(onePlayer);
    }
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(final Player player) {
        currentPlayer = player;
    }
    public int getStoreCount(final Player player){
        return player.getStoreCount();
    }

    public int move(final int pitIndex) throws InvalidMoveException {
        int total = 0;
        int player;
        if (currentPlayer == players.get(0)) {
            gameRules.setPlayer(1);
            player = 1;
        } else {
            gameRules.setPlayer(2);
            player = 2;
        }
        gameRules.moveStones(pitIndex, player);

        gameRules.collectRemainingStonesIfSideEmpty();
        if (pitIndex < PLAYER_TWO_MAX) {
            for (int i = 1; i < 7; i++) {
                total += gameRules.getNumStones(i);
            }
        } else {
            for (int i = 7; i < 13; i++) {
                total += gameRules.getNumStones(i);
            }
        }
        if(gameRules.getPlayer() == PLAYER_ONE) {
            setCurrentPlayer(players.get(0));
        }
        else{
            setCurrentPlayer(players.get(1));
        }
        return total;
    }

    public Player getWinner() {
        final int stonesPlayerOne = gameRules.getDataStructure().getStoreCount(1); 
        final int stonesPlayerTwo = gameRules.getDataStructure().getStoreCount(2);
        if(stonesPlayerOne > stonesPlayerTwo){
            return players.get(0);
        }
        else if (stonesPlayerOne < stonesPlayerTwo){
            return players.get(1);
        }
        else{
            return null;
        }
    }

    public boolean isGameOver() {
        if(gameRules.isSideEmpty(1)){
            return true;
        }
        else if (gameRules.isSideEmpty(7)){
            return true;
        }
        return false;
    }
    public void startNewGame() {
        gameRules.resetBoard();
    }
    public ArrayList <Player> getPlayers(){
        return players;
    }
    @Override
    public String toString() {
        final String stringMancalaGame = "\nCurrent Player: " + currentPlayer.getName() + "\n" + gameRules.toString();
        return stringMancalaGame;
}
}
