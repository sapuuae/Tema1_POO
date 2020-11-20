package usage;

import base.Video;
import base.VideosForRating;
import fileio.ActionInputData;
import fileio.Writer;
import org.json.simple.JSONArray;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class QueryVideosRating {
    private final ArrayList<Video> movieArrayList;
    private final ActionInputData action;

    public QueryVideosRating(final ArrayList<Video> movieArrayList,
                             final ActionInputData action) {
        this.movieArrayList = movieArrayList;
        this.action = action;
    }

    private boolean checkTheVideo (Video theVideo) {
        boolean ok = true;
        int yearIndex = 0;
        int genresIndex = 1;
        List<String> yearString = this.action.getFilters().get(yearIndex);
        int year = 0;
        if (yearString.get(0) != null) {
            year = Integer.parseInt(yearString.get(0));
        }
        if (theVideo.getNumberOfRatings() != 0) {
            if (theVideo.getYear() != year && year != 0) {
                ok = false;
            } else {
                List<String> genresString = this.action.getFilters().get(genresIndex);
                if (genresString.get(0) != null) {
                    ArrayList<String> videoGenres = theVideo.getGenres();
                    for (String s : genresString) {
                        if (!videoGenres.contains(s)) {
                            ok = false;
                            break;
                        }
                    }
                }
            }
        } else {
            ok = false;
        }
        return ok;
    }

    public void sortTheMovies(final JSONArray arrayResult,
                              final Writer fileWriter) throws IOException {
        ArrayList<VideosForRating> theMovies = new ArrayList<>();
        for (Video theVideo : movieArrayList) {
            if (checkTheVideo(theVideo)) {
                VideosForRating toAdd = new VideosForRating(theVideo.getTitle(),
                        theVideo.getRating());
                theMovies.add(toAdd);
            }
        }
        if (action.getSortType().equals("asc")) {
            theMovies.sort((o1, o2) -> {
                int c;
                c = o1.getRating().compareTo(o2.getRating());
                if (c == 0) {
                    c = o1.getVideoTitle().compareTo(o2.getVideoTitle());
                }
                return c;
            });
        } else {
            theMovies.sort((o1, o2) -> {
                int c;
                c = o2.getRating().compareTo(o1.getRating());
                if (c == 0) {
                    c = o2.getVideoTitle().compareTo(o1.getVideoTitle());
                }
                return c;
            });
        }
        ArrayList<String> theFinalList = new ArrayList<>();
        if (theMovies.size() < action.getNumber()) {
            for (VideosForRating theVideo : theMovies) {
                theFinalList.add(theVideo.getVideoTitle());
            }
        } else {
            for (int i = 0; i < action.getNumber(); i++) {
                theFinalList.add(theMovies.get(i).getVideoTitle());
            }
        }
        arrayResult.add(fileWriter.writeFile(action.getActionId(), "?",
                "Query result: " + theFinalList + ""));
    }
}
