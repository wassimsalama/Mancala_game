package mancala;

import java.io.Serializable;

public class UserProfile implements Serializable {
    private static final long serialVersionUID = 1L; 

    private String playerName;
    private int kalahGamesPlayed;
    private int ayoGamesPlayed;
    private int kalahWins;
    private int ayoWins;

  
    public UserProfile() {
        this.playerName = "";
        kalahGamesPlayed = 0;
        ayoGamesPlayed = 0;
        kalahWins = 0;
        ayoWins = 0;
    }
    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(final String playerName) {
        this.playerName = playerName;
    }
   
    public int getKalahGamesPlayed() {
        return kalahGamesPlayed;
    }

    public int getAyoGamesPlayed() {
        return ayoGamesPlayed;
    }

    public int getKalahWins() {
        return kalahWins;
    }

    public void setKalahWins(final boolean kalahWins) {
        kalahGamesPlayed++;
        if(kalahWins){
         this.kalahWins++;
        }
    }

    public int getAyoWins() {
        return ayoWins;
    }

    public void setAyoWins(final boolean ayoWins) {
        ayoGamesPlayed++;
        if(ayoWins){
            this.ayoWins++;
        }
    }
    
    @Override
    public String toString() {
        return "UserProfile{" +
                "playerName='" + playerName + '\'' +
                 +
                '}';
    }
}

