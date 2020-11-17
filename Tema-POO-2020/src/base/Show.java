package base;

import entertainment.MySeason;

import java.util.ArrayList;

public class Show extends Video {
    private final int numberOfSeasons;
    private final ArrayList<MySeason> seasons;

    public Show(final String title, final int year, final ArrayList<String> cast,
                final ArrayList<String> genres, final int numberOfSeasons,
                final ArrayList<MySeason> seasons) {
        super(title, year, cast, genres);
        this.numberOfSeasons = numberOfSeasons;
        this.seasons = seasons;
    }

    public final int getNumberOfSeasons() {
        return numberOfSeasons;
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
