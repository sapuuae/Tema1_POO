package base;

import java.util.ArrayList;

public class Movie extends Video {
    private final Integer duration;

    public Movie(final String title, final int year,
                 final ArrayList<String> cast, final ArrayList<String> genres,
                 final int duration, final Integer orderInDatabase) {
        super(title, year, cast, genres, orderInDatabase);
        this.duration = duration;
    }

    /**
     * Make the average rating for a movie, updating it every time a new grade come.
     * @param rating the rating for a movie, given by an user.
     */
    public final void avgRating(final Double rating) {
        double newRating = this.getRating() * this.getNumberOfRatings() + rating;
        this.setNumberOfRatings(this.getNumberOfRatings() + 1);
        newRating /= this.getNumberOfRatings();
        this.setRating(newRating);
    }

    public final Integer getDuration() {
        return duration;
    }
}
