package mancala;

public class KalahRules extends GameRules  {
    private static final long serialVersionUID = 1L; 
    private boolean bonusTurn = false;
    private int lastPitPos;
    private static final int PLAYER_ONE = 1;
    private static final int PLAYER_ONE_MAX = 6;
    private static final int PLAYER_TWO_MAX = 7;

    public KalahRules() {
        super();
    }

    @Override
    public int moveStones(final int startPit, final int player) throws InvalidMoveException {
        
        if (startPit < 1 || startPit > 12) {
            throw new InvalidMoveException();
        }
        if(player == PLAYER_ONE){
            if(startPit>PLAYER_ONE_MAX){
                throw new InvalidMoveException();
            }
        }else{
            if(startPit<PLAYER_TWO_MAX){
                throw new InvalidMoveException();
            }
        }
        if (getDataStructure().getNumStones(startPit) == 0) {
            throw new InvalidMoveException();
        }
        final int initialStoreCount = getDataStructure().getStoreCount(player);
        distributeStones(startPit);
        final int finalStoreCount = getDataStructure().getStoreCount(player);
        if (lastPitPos == getDataStructure().getStorePlayerPos(player)) {
            bonusTurn = true; // Set bonus turn flag if last stone lands in player's store
        } else {
            bonusTurn = false;
            switchPlayer(); // Switch player if the last stone doesn't land in the player's store
        }
        return finalStoreCount - initialStoreCount;
    }

    @Override
    public int distributeStones(final int startPit) {
        int total = 0;
        int lastPitIndex;
        int inLastPit = 0;
        final int player = getPlayer();
        final int numStones = getDataStructure().removeStones(startPit);
        getDataStructure().setIterator(startPit, player, false);
        for (int i = 0; i < numStones; i++) {
            final Countable currentSpot = getDataStructure().next();
            inLastPit = currentSpot.getStoneCount();
            currentSpot.addStone();
            total++;
        }
        lastPitIndex = getDataStructure().getPos();
       lastPitPos = lastPitIndex;

       if (inLastPit == 0) { 
        if (player == 1 && lastPitIndex <= 5 && lastPitIndex >= 0 || player == 2 && lastPitIndex <= 11 && lastPitIndex >= 6) {
            total += captureStones(lastPitIndex + 1);
        }
    }
    
        return total;
    }

    @Override
    public int captureStones(final int stoppingPoint) {
        final int player = getPlayer();
        final int oppositePitIndex = 13 - stoppingPoint;
        final int oppositePit = getDataStructure().getNumStones(oppositePitIndex);
        

        if (oppositePit != 0 ) {
            int captured = getDataStructure().removeStones(oppositePitIndex);
            captured += getDataStructure().removeStones(stoppingPoint);
            getDataStructure().addToStore(player, captured);
            return captured;
        }
        return 0;
    }

    public boolean bonusTurn() {
        return bonusTurn;
    }
}
