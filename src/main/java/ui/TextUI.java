package ui;

import java.util.ArrayList;
import java.util.Scanner;

import mancala.AyoRules;
import mancala.GameNotOverException;
import mancala.InvalidMoveException;
import mancala.KalahRules;
import mancala.MancalaGame;
import mancala.Player;
import mancala.Saver;
import mancala.UserProfile;

public class TextUI {
    private MancalaGame game;
    private Scanner input = new Scanner(System.in);

    public TextUI(MancalaGame newGame) {
        this.game = newGame;
    }

    public void startGame() throws GameNotOverException {
        int pit;
        int again = 0;
        String answer;
        Player winner;
        boolean invalid;
        boolean exit = false;

        do {

            while (!game.isGameOver()) {
                System.out.println(game.toString());

                System.out.println("Save and exit? y/N: ");

                answer = input.nextLine();

                if (answer.equals("y") || answer.equals("Y")) {
                    exit = true;
                    System.out.println("Name the file (no spaces): ");

                    answer = input.nextLine();

                    Saver.saveObject(game, answer + ".bin");
                    break;
                }

                invalid = true;

                while (invalid) {
                    System.out.println("\n\nPick a pit: ");

                    pit = input.nextInt();
                    input.nextLine();

                    try {
                        game.move(pit);
                        invalid = false;
                    } catch (InvalidMoveException e) {
                        System.out.println("Invalid pit, try again");
                        continue;
                    }
                }

                if (game.isGameOver()) {
                    System.out.println("\nGame Finished.");
                }

            }

            if (!exit) {
                System.out.println(game.getBoard().toString());

                winner = game.getWinner();

                if (winner == null) {
                    System.out.println("It's a tie!");
                } else {
                    System.out.println(winner.getName() + " is the Winner!");
                    
                    ArrayList<Player> players = game.getPlayers();

                    if (winner.getName().equals(players.get(0).getName())) {
                        if (game.getBoard() instanceof KalahRules) {
                            players.get(0).getProfile().setKalahWins(true);
                            players.get(1).getProfile().setKalahWins(false);
                        } else {
                            players.get(0).getProfile().setAyoWins(true);
                            players.get(1).getProfile().setAyoWins(false);
                        }
                    } else {
                        if (game.getBoard() instanceof KalahRules) {
                            players.get(1).getProfile().setKalahWins(true);
                            players.get(0).getProfile().setKalahWins(false);
                        } else {
                            players.get(1).getProfile().setAyoWins(true);
                            players.get(0).getProfile().setAyoWins(false);
                        }
                    }
                }

                System.out.println("Would you like to start a new game? y/N: ");

                answer = input.nextLine();

                if ((answer.equals("y") || answer.equals("Y"))) {
                    again = 1;
                } else {
                    game.startNewGame();
                }
            }

        } while (again == 1);

    }

    // Other methods for user interaction and output

    public static void main(String[] args) throws InvalidMoveException, GameNotOverException {
        MancalaGame mancalaGame;
        UserProfile profile1 = new UserProfile();
        UserProfile profile2 = new UserProfile();
        Scanner userIn = new Scanner(System.in);
        boolean found = false;
        int num;
        String name;
        String filename;
        String choice;

        System.out.println("Welcome to Mancala!");

        System.out.println("Would you like to load a game? y/N: ");

        choice = userIn.nextLine();

        if (choice.equals("y") || choice.equals("Y")) {
            System.out.println("Enter the filename: ");

            filename = userIn.nextLine();

            mancalaGame = (MancalaGame) Saver.loadObject(filename);

            if (mancalaGame == null) {
                System.out.println("File not found.");
            } else {
                System.out.println("Game loaded successfully!.");
                TextUI textUI = new TextUI(mancalaGame);
                textUI.startGame();
            }
        } else {

            System.out.println("Would you like to load a user profile for player one? y/N: ");

            choice = userIn.nextLine();

            if (choice.equals("y") || choice.equals("Y")) {
                System.out.println("Enter the filename: ");

                filename = userIn.nextLine();

                profile1 = (UserProfile) Saver.loadObject(filename);

                if (profile1 == null) {
                    System.out.println("File not found.");
                } else {
                    found = true;
                }
            }

            if (found == false) {
                System.out.println("Enter the name of the first player: ");

                name = userIn.nextLine();

                profile1.setPlayerName(name);

                Saver.saveObject(profile1, name + ".bin");
            }

            found = false;

            System.out.println("Would you like to load a user profile for player two? y/N: ");

            choice = userIn.nextLine();

            if (choice.equals("y") || choice.equals("Y")) {
                System.out.println("Enter the filename: ");

                filename = userIn.nextLine();

                profile2 = (UserProfile) Saver.loadObject(filename);

                if (profile2 == null) {
                    System.out.println("File not found.");
                } else {
                    found = true;
                }
            }

            if (found == false) {
                System.out.println("Enter the name of the second player: ");

                name = userIn.nextLine();

                profile2.setPlayerName(name);

                Saver.saveObject(profile2, name + ".bin");
            }

            System.out.println("What ruleset would you like to use? (1) Kalah or (2) Ayoayo: ");

            do {

                num = userIn.nextInt();

                if (num != 1 && num != 2) {
                    System.out.println("Invalid choice, try again.");
                } else {
                    if (num == 1) {
                        mancalaGame = new MancalaGame();
                        mancalaGame.setBoard(new KalahRules());
                        mancalaGame.setPlayers(new Player(profile1), new Player(profile2));
                        TextUI textUI = new TextUI(mancalaGame);
                        textUI.startGame();
                    } else {
                        mancalaGame = new MancalaGame();
                        mancalaGame.setBoard(new AyoRules());
                        mancalaGame.setPlayers(new Player(profile1), new Player(profile2));
                        TextUI textUI = new TextUI(mancalaGame);
                        textUI.startGame();
                    }
                }

            } while (num != 1 && num != 2);

        }

        userIn.close();

        // mancalaGame = new MancalaGame();

        // mancalaGame.setBoard(new KalahRules());

        // mancalaGame.setPlayers(new Player(profile1), new Player(profile2));

        // System.out.println("Would you like to save this game? y/N: ");

        // choice = userIn.nextLine();

        // if (choice.equals("y") || choice.equals("Y")) {
        // System.out.println("Enter the filename: ");

        // filename = userIn.nextLine();

        // Saver.saveObject(mancalaGame, filename + ".bin");
        // }

        // TextUI textUI = new TextUI(mancalaGame);
        // textUI.startGame();

        // profile1.setPlayerName("yaya");

        // System.out.println("Before:\n" + profile1.getPlayerName());

        // Saver.saveObject(profile1, "user.bin");

        // profile2 = (UserProfile) Saver.loadObject("user.bin");

        // System.out.println("After:\n" + profile2.getPlayerName());

        // mancalaGame = new MancalaGame();

        // mancalaGame.setBoard(new KalahRules());

        // mancalaGame.setPlayers(new Player(profile1), new Player(profile2));

        // System.out.println("Before:\n" + mancalaGame.toString());

        // Saver.saveObject(mancalaGame, "game.bin");

        //  MancalaGame other = (MancalaGame) Saver.loadObject("game.bin");

        //  System.out.println("After:\n" + other.toString());

    }
}
