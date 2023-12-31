package mancala;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Paths;
import java.nio.file.Path;

public class Saver {
    

    public static void saveObject(final Serializable object, final String filename) {
       final String filePath = "assets";
       final Path path = Paths.get(filePath, filename);
    
        try (FileOutputStream fileOut = new FileOutputStream(path.toFile());
             ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {
    
            objectOut.writeObject(object);
    
            // Additional logging based on object's type
            if (object instanceof MancalaGame) {
                System.out.println("Mancala game data saved to " + path);
            } else if (object instanceof UserProfile) {
                System.out.println("User profile data saved to " + path);
            } else {
                System.out.println("Data saved to " + path);
            }
    
        } catch (IOException e) {
            System.err.println("Failed to save due to: " + e.getMessage());
        }
    }
    
    
    public static Serializable loadObject(final String filename) {
        final File file = new File("assets", filename);
        if (!file.exists()) {
            System.out.println("File not found: " + file.getAbsolutePath());
            return null;
        }
    
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            final Serializable object = (Serializable) ois.readObject();
            
            // Check if the object is an instance of MancalaGame or UserProfile
            if (object instanceof MancalaGame || object instanceof UserProfile) {
                return object;
            } else {
                //System.out.println("The loaded object is not a MancalaGame or UserProfile.");
                return null;
            }
        } catch (IOException | ClassNotFoundException e) {
           // System.out.println("Error while loading object: " + e.getMessage());
            return null;
        }
    }
    
}


