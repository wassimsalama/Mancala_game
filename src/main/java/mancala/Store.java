package mancala;
import java.io.Serializable;
public class Store implements Countable,Serializable {
    private static final long serialVersionUID = 1L; 
    private int storeCount;
    private Player playerGame;

    public Store() {
        this.storeCount = 0;
    }
    @Override
    public void addStone() {
        this.addStones(1);
    }
    @Override
    public void addStones(final int amount) {
        this.storeCount += amount;
    }
    public Player getOwner() {
        return this.playerGame;
    }
    @Override
    public int getStoneCount() {
        return this.storeCount;
    }
    public void setOwner(final Player player) {
        this.playerGame = player;
    }
    @Override
    public int removeStones() {
       final int tempStore = this.storeCount;
        this.storeCount = 0;
        return tempStore;
    }
    @Override
    public String toString() {
       return playerGame.getName() + "'s Store:" + Integer.toString(storeCount);
    }
}
