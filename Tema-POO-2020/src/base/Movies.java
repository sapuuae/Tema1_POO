package base;

import java.util.ArrayList;

public class Movies extends Video {
    private final int duration;

    public Movies(final String title, final int year,
                  final ArrayList<String> cast, final ArrayList<String> genres,
                  final int duration) {
        super(title, year, cast, genres);
        this.duration = duration;
    }

    public final int getDuration() {
        return duration;
    }

    @Override
    public final String toString() {
        return "Movies{"
                + "duration=" + duration
                + ", title='" + getTitle() + '\''
                + ", year=" + getYear()
                + ", cast=" + getCast()
                + ", genres=" + getGenres()
                + '}';
    }
}
