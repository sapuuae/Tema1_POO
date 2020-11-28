package base;

import java.util.ArrayList;
/*
Show class.
 */
public class Show extends Video {
    private final int numberOfSeasons;
    private final ArrayList<MySeason> seasons;
    private final Integer totalDuration;

    public Show(final String title, final int year, final ArrayList<String> cast,
                final ArrayList<String> genres, final int numberOfSeasons,
                final ArrayList<MySeason> seasons, final int totalDuration,
                final Integer orderInDatabase) {
        super(title, year, cast, genres, orderInDatabase);
        this.numberOfSeasons = numberOfSeasons;
        this.seasons = seasons;
        this.totalDuration = totalDuration;
    }

    public final ArrayList<MySeason> getSeasons() {
        return seasons;
    }

    @Override
    public final void avgRating() {
        double sum = 0;
        for (MySeason theSeason : this.seasons) {
            sum += theSeason.getAvgRating();
        }
        sum /= this.numberOfSeasons;
        this.setRating(sum);
    }

    public final Integer getTotalDuration() {
        return totalDuration;
    }

    @Override
    public final String toString() {
        return "Shows{"
                + "numberOfSeasons=" + numberOfSeasons
                + ", seasons=" + seasons
                + ", title='" + getTitle() + '\''
                + ", year=" + getYear()
                + ", cast=" + getCast()
                + ", genres=" + getGenres()
                + '}';
    }
}
