package base;

import entertainment.Season;

import java.util.ArrayList;

public class Shows extends Video {
    private final int numberOfSeasons;
    private final ArrayList<Season> seasons;

    public Shows(final String title, final int year, final ArrayList<String> cast,
                 final ArrayList<String> genres, final int numberOfSeasons,
                 final ArrayList<Season> seasons) {
        super(title, year, cast, genres);
        this.numberOfSeasons = numberOfSeasons;
        this.seasons = seasons;
    }

    public final int getNumberOfSeasons() {
        return numberOfSeasons;
    }

    public final ArrayList<Season> getSeasons() {
        return seasons;
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
