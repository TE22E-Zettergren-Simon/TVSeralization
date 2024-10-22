import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class TVMain {
    private ArrayList<TVSeries> shows = new ArrayList<>();
    private final String SAVE_PATH = "saved-shows.ser";

    private final Scanner scanner = new Scanner(System.in);

    public TVMain() {
        readShows();

        createShow();
        createShow();

        writeShows();
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
            throw new RuntimeException(e);
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
