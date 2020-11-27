package base;

import java.util.ArrayList;

/*
Used to make a map in PopularRecommendation class.
 */
public final class VideosWithGenresAndViews {
    /*
    Genre of the videos.
     */
    private final String genre;
    /*
    Arraylist to save all videos which have this genre.
     */
    private final ArrayList<Video> videoList;
    private Integer numberOfViews;

    public VideosWithGenresAndViews(final String genre,
                                    final Integer numberOfViews) {
        this.genre = genre;
        this.numberOfViews = numberOfViews;
        this.videoList = new ArrayList<>();
    }

    public String getGenre() {
        return genre;
    }

    public Integer getNumberOfViews() {
        return numberOfViews;
    }

    public void setNumberOfViews(final Integer numberOfViews) {
        this.numberOfViews = numberOfViews;
    }

    public ArrayList<Video> getVideoList() {
        return videoList;
    }
}
