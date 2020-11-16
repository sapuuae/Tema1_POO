package entertainment;

import java.util.ArrayList;
import java.util.List;

public final class MySeason {
    private final int currentSeason;
    /**
     * Duration in minutes of a season
     */
    private int duration;
    /**
     * List of ratings from every user for the season
     */
    private List<Double> ratings;

    private Double avgRating;

    public MySeason(final int currentSeason, final int duration) {
        this.currentSeason = currentSeason;
        this.duration = duration;
        this.ratings = new ArrayList<>();
    }

    public int getCurrentSeason() {
        return currentSeason;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(final int duration) {
        this.duration = duration;
    }

    public Double getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(final Double grade) {
        this.ratings.add(grade);
        double sum = 0;
        for (Double rating : this.ratings) {
            sum += rating;
        }
        this.avgRating = sum / this.ratings.size();
    }

    public List<Double> getRatings() {
        return ratings;
    }

    public void setRatings(final List<Double> ratings) {
        this.ratings = ratings;
    }
}
