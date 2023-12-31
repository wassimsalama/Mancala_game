package mancala;

public class AyoRules extends GameRules {
    private static final long serialVersionUID = 1L; 
    private static final int PLAYER_ONE = 1;
    private static final int PLAYER_ONE_MAX = 6;
    private static final int PLAYER_TWO_MAX = 7;
    public AyoRules() {
        super();
    }
    @Override
    public int moveStones(final int startPit, final int playerNum) throws InvalidMoveException{
        if (startPit < 1 || startPit > 12) {
            throw new InvalidMoveException();
        }
        if(playerNum == PLAYER_ONE){
            if(startPit>PLAYER_ONE_MAX){
                throw new InvalidMoveException();
            }
        }else{
            if(startPit<PLAYER_TWO_MAX){
                throw new InvalidMoveException();
            }
        }
        if (getDataStructure().getNumStones(startPit) == 0 ){
            throw new InvalidMoveException();
        }
        final int  initialStoreCount = getDataStructure().getStoreCount(playerNum);
        distributeStones(startPit);
        final int  finalStoreCount =  getDataStructure().getStoreCount(playerNum);
        switchPlayer();
        return  finalStoreCount - initialStoreCount;
    }

    /**
     * Distribute stones from a pit and return the number distributed.
     *
     * @param startPit The starting pit for distribution.
     * @return The number of stones distributed.
     */
    @Override
public int distributeStones(final int startPit) {
    int stonesDistributed = 0;
    final int player = getPlayer();
    int numStones = getDataStructure().removeStones(startPit);
    getDataStructure().setIterator(startPit, player, true); 

    while (numStones > 0) {
       final Countable currentSpot = getDataStructure().next();
       final int currentPos = getDataStructure().getPos();
        if (!getDataStructure().isStore(currentPos) || player == 1 && currentPos == 6 || player == 2 && currentPos == 13) {
            currentSpot.addStone();
            stonesDistributed++;
            numStones--;
        }
        if (numStones == 0 && currentSpot.getStoneCount() > 1) {
            numStones = currentSpot.removeStones();
            stonesDistributed--; 
        }
    }
   final int finalPos = getDataStructure().getPos() + 1;
    final boolean isPlayersSide = player == 1 && finalPos < 6 || player == 2 && finalPos >= 7 && finalPos < 13;
    if (isPlayersSide && getDataStructure().getNumStones(finalPos) == 1) {
        stonesDistributed += captureStones(finalPos);
    }

    return stonesDistributed;
} 
            @Override
            public int captureStones(final int stoppingPoint) {
              final  int player = getPlayer();
                final int oppositePitIndex = 13 - stoppingPoint; // Adjust for zero-based indexing
               final int oppositePit = getDataStructure().getNumStones(oppositePitIndex);
            
                if (oppositePit > 0) {
                    final int captured = getDataStructure().removeStones(oppositePitIndex);
                    getDataStructure().addToStore(player, captured);
                    return captured;
                }
                return 0;
            } 
}
