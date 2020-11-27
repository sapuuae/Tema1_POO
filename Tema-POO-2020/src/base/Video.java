package base;

import java.util.ArrayList;

public abstract class Video {
    private final String title;
    private final int year;
    private final ArrayList<String> cast;
    private final ArrayList<String> genres;
    private Double rating = 0.0;
    /*
    Used to get the number of added by users to favorite.
     */
    private Integer numberOfApparitionsInFavorite = 0;
    /*
    Used to check if the rating is set.
     */
    private Integer numberOfRatings = 0;
    /*
    Used to check if the video is seen.
     */
    private Integer totalNumberOfViewed = 0;
    /*
    Used to check where is the video in database.
     */
    private final Integer orderInDatabase;

    public Video(final String title, final int year,
                 final ArrayList<String> cast, final ArrayList<String> genres,
                 final Integer orderInDatabase) {
        this.title = title;
        this.year = year;
        this.cast = cast;
        this.genres = genres;
        this.orderInDatabase = orderInDatabase;
    }
    /**
     * Makes the rating for Movie/Show. Is overwritten in movie and show classes.
     */
    public void avgRating() {
    }

    public final String getTitle() {
        return title;
    }

    public final int getYear() {
        return year;
    }

    public final ArrayList<String> getCast() {
        return cast;
    }

    public final ArrayList<String> getGenres() {
        return genres;
    }

    public final Integer getNumberOfApparitionsInFavorite() {
        return numberOfApparitionsInFavorite;
    }

    public final void setNumberOfApparitionsInFavorite(
            final Integer numberOfApparitionsInFavorite) {
        this.numberOfApparitionsInFavorite = numberOfApparitionsInFavorite;
    }

    public final Integer getTotalNumberOfViewed() {
        return totalNumberOfViewed;
    }

    public final void setTotalNumberOfViewed(final Integer totalNumberOfViewed) {
        this.totalNumberOfViewed = totalNumberOfViewed;
    }

    public final int getNumberOfRatings() {
        return numberOfRatings;
    }

    public final void setNumberOfRatings(final int numberOfRatings) {
        this.numberOfRatings = numberOfRatings;
    }

    public final void setRating(final Double rating) {
        this.rating = rating;
    }

    public final Double getRating() {
        return rating;
    }

    public final Integer getOrderInDatabase() {
        return orderInDatabase;
    }
}
