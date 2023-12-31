package mancala;
import java.io.Serializable;
public class Pit implements Countable,Serializable {
    private static final long serialVersionUID = 1L; 
    private int pitCount;

    public Pit() {
        this.pitCount = 0;
    }
    @Override
    public void addStone() {
        this.pitCount++;
    }
    @Override
    public void addStones(final int amount) {
        this.pitCount = this.pitCount + amount;
    }
    @Override
    public int getStoneCount() {
        return this.pitCount;
    }
    @Override
    public int removeStones() {
        int tempStones;
        tempStones = this.pitCount;
        this.pitCount = 0;
        return tempStones;
    }
    @Override
    public String toString() {

        return "Pit{" + "stoneCount=" + pitCount + '}';
    }
}