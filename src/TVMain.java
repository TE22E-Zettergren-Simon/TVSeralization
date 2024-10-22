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
                    for (TVSeries show : shows) {
                        System.out.println();
                        show.present();
                    }
                    break;
                case 2:
                    break loop;
            }
        }

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
        int rating = scanner.nextInt();
        scanner.nextLine();
        series.setRating(rating);

        int i = 1;
        while (true) {
            System.out.print("Episodes in season " + i + " (0 exits) > ");
            int episodes = scanner.nextInt();
            scanner.nextLine();

            if (episodes <= 0) {
                break;
            } else {
                series.addEpisodes(episodes, i);
            }

            i++;
        }

        shows.add(series);
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
            System.out.println("Read " + shows.size() + " shows.");

            objectIn.close();
            fileIn.close();
        } catch (Exception e) {
            System.out.println("Did not read any shows");
        }
    }
}
