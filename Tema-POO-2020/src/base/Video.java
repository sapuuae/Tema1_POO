package base;

import java.util.ArrayList;

public abstract class Video {
    private final String title;
    private final int year;
    private final ArrayList<String> cast;
    private final ArrayList<String> genres;
    private double rating;
    private int numberofAparitionsInFavorite;
    private int numberOfRatings;

    public Video(final String title, final int year,
                 final ArrayList<String> cast, final ArrayList<String> genres) {
        this.title = title;
        this.year = year;
        this.cast = cast;
        this.genres = genres;
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

    public final int getNumberofAparitionsInFavorite() {
        return numberofAparitionsInFavorite;
    }

    public final void setNumberofAparitionsInFavorite(final int numberofAparitionsInFavorite) {
        this.numberofAparitionsInFavorite = numberofAparitionsInFavorite;
    }

    public int getNumberOfRatings() {
        return numberOfRatings;
    }

    public void setNumberOfRatings(int numberOfRatings) {
        this.numberOfRatings = numberOfRatings;
    }

    public void avgRating() {

    }

    public final void setRating(final Double rating) {
        this.rating = rating;
    }

    public final Double getRating() {
        return rating;
    }
}
