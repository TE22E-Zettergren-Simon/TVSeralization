import java.io.Serializable;
import java.util.ArrayList;

public class TVSeries implements Serializable {
    private String name;
    private int rating = 0;
    private ArrayList<Integer> episodes = new ArrayList<>();

    public TVSeries(String name) {
        this.name = name;
    }

    public void addEpisodes(int numOfEpisodes, int season) {
        if (season > episodes.size() + 1) {
            throw new IllegalArgumentException("The season does not exist and cannot be created");
        }

        if (season == episodes.size() + 1) {
            if (numOfEpisodes <= 0) {
                throw new IllegalArgumentException("A season must have at least one episode");
            }
        } else if (episodes.get(season - 1) + numOfEpisodes <= 0) {
            throw new IllegalArgumentException("A season must have at least one episode");
        }

        if (season == episodes.size() + 1) {
            episodes.add(numOfEpisodes);
        } else {
            int episodesInSeason = episodes.get(season - 1);
            episodes.set(season - 1, numOfEpisodes + episodesInSeason);
        }
    }

    public void setRating(int newRating) {
        if (newRating < 0 || newRating > 10) {
            throw new IllegalArgumentException("Rating must be between 0 and 10");
        }

        this.rating = newRating;
    }

    public void present() {
        System.out.println(name + "   Rating: " + rating + "/10");
        System.out.println("Seasons:");
        for (int i = 0; i < episodes.size(); i++) {
            System.out.println(i+1 + ": " + episodes.get(i) + " episodes");
        }
    }

    public String getName() {
        return name;
    }

    public int getNumOfSeasons() {
        return episodes.size();
    }
}
