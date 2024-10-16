public class TVMain {
    public TVMain() {
        TVSeries s1 = new TVSeries("Skibidi Toilet");
        s1.setRating(10);
        s1.addEpisodes(15, 1);
        s1.addEpisodes(13, 2);
        s1.addEpisodes(10, 3);
        s1.present();
    }
}
