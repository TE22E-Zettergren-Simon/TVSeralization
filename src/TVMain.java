import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class TVMain {
    private ArrayList<TVSeries> shows = new ArrayList<>();
    private final String SAVE_PATH = "saved-shows.ser";

    private final Scanner scanner = new Scanner(System.in);

    public TVMain() {
        readShows();
        System.out.println("Welcome! :)");

        String[] mainMenu = {
                "Add show",
                "Edit a show",
                "See shows",
                "Exit",
        };

        loop: while (true) {
            System.out.println();
            int userChoice = chooseInMenu(mainMenu);

            switch (userChoice) {
                case 0:
                    createShow();
                    break;
                case 1:
                    editShow();
                    break;
                case 2:
                    for (TVSeries show : shows) {
                        System.out.println();
                        show.present();
                    }
                    break;
                case 3:
                    break loop;
            }
        }

        System.out.println();
        writeShows();
        System.out.println("Goodbye! :)");
    }


    private int chooseInMenu(String[] choices) {
        for (int i = 0; i < choices.length; i++) {
            System.out.println((i+1) + ". " + choices[i]);
        }
        System.out.print("Choose a number in the menu > ");

        while (true) {
            try {
                int choice = scanner.nextInt();
                scanner.nextLine();

                if (choice < 1) {
                    System.out.print("Enter a number greater than 0 > ");
                } else if (choice > choices.length) {
                    System.out.print("Enter a number less than " + (choices.length+1) + " > ");
                } else {
                    return choice - 1;
                }

            } catch (InputMismatchException e) {
                scanner.nextLine();
                System.out.print("Enter a number > ");
            }
        }
    }


    private void createShow() {
        System.out.print("Name of series > ");
        String name = scanner.nextLine();
        TVSeries series = new TVSeries(name);

        System.out.print("Its rating > ");
        while (true) {
            try {
                int rating = scanner.nextInt();
                scanner.nextLine();
                series.setRating(rating);
                break;
            } catch (InputMismatchException e) {
                scanner.nextLine();
                System.out.print("Enter a number > ");
            } catch (IllegalArgumentException e) {
                System.out.print("Enter a number between 0 and 10 > ");
            }
        }

        int i = 1;
        while (true) {
            System.out.print("Episodes in season " + i + " (0 exits) > ");

            int episodes;
            while (true) {
                try {
                    episodes = scanner.nextInt();
                    scanner.nextLine();
                    break;
                } catch (InputMismatchException e) {
                    scanner.nextLine();
                    System.out.print("Enter a number > ");
                }
            }

            if (episodes <= 0) {
                break;
            } else {
                series.addEpisodes(episodes, i);
            }

            i++;
        }

        shows.add(series);
    }

    private void editShow() {
        String[] showNames = new String[shows.size()];
        for (int i = 0; i < shows.size(); i++) {
            showNames[i] = "Edit " + shows.get(i).getName();
        }
        int userChoice = chooseInMenu(showNames);
        TVSeries show = shows.get(userChoice);

        int numOfSeasons = show.getNumOfSeasons();
        String[] seasons = new String[numOfSeasons + 1];
        for (int i = 0; i < numOfSeasons; i++) {
            seasons[i] = "Edit season " + (i+1);
        }
        seasons[numOfSeasons] = "Add season " + (numOfSeasons+1);
        int season = chooseInMenu(seasons) + 1;

        System.out.print("How many episodes do you want to add > ");
        while (true) {
            try {
                int newEpisodes = scanner.nextInt();
                scanner.nextLine();
                show.addEpisodes(newEpisodes, season);
                break;
            } catch (IllegalArgumentException e) {
                System.out.print("The season cannot have less than 1 episode, retry > ");
            }catch (InputMismatchException e) {
                scanner.nextLine();
                System.out.print("Enter a number > ");
            }
        }
    }

    private void writeShows() {
        new File(SAVE_PATH);
        try {
            FileOutputStream fileOut = new FileOutputStream(SAVE_PATH);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);

            objectOut.writeObject(shows);
            System.out.println("Saved " + shows.size() + " shows");

            objectOut.close();
            fileOut.close();
        } catch (Exception e) {
            System.out.println("Failed to save shows");
        }
    }

    private void readShows() {
        try {
            FileInputStream fileIn = new FileInputStream(SAVE_PATH);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);

            shows = (ArrayList<TVSeries>) objectIn.readObject();
            System.out.println("Read " + shows.size() + " shows");

            objectIn.close();
            fileIn.close();
        } catch (Exception e) {
            System.out.println("Did not read any shows");
        }
    }
}
