package base;

public final class VideosForRating {
    private final String videoTitle;
    private final Double rating;

    public VideosForRating(String videoTitle, Double rating) {
        this.videoTitle = videoTitle;
        this.rating = rating;
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public Double getRating() {
        return rating;
    }
}
