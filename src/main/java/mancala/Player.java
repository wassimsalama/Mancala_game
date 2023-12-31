package mancala;
import java.io.Serializable;
public class Player implements Serializable{
    private static final long serialVersionUID = 1L; 
    final private UserProfile userProfile;
    private Store playerStore;
    public Player(){
        this.playerStore = new Store();
        this.userProfile = new UserProfile();
    }

    public Player(final UserProfile userProfile) {
        this.userProfile = userProfile;
        this.playerStore = new Store();
    }
    
    public String getName() {
        return this.userProfile.getPlayerName();
    }

    public Store getStore() {
        return this.playerStore;
    }

    public int getStoreCount() {
        return this.playerStore.getStoneCount();
    }
    public void setStore(final Store store) {
        this.playerStore = store;
    }
    public UserProfile getProfile(){
        return this.userProfile;
    }
    @Override
    public String toString() {
        return "Player{" +
               "name='" + getName() + '\'' +
               ", storeCount=" + getStoreCount() +
               '}';
    }
    

}
