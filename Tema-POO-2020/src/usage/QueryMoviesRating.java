package usage;

import base.Video;
import base.VideosForRating;
import fileio.ActionInputData;
import fileio.Writer;
import org.json.simple.JSONArray;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class QueryMoviesRating {
    private final ArrayList<Video> movieArrayList;
    private final ActionInputData action;

    public QueryMoviesRating(ArrayList<Video> movieArrayList, ActionInputData action) {
        this.movieArrayList = movieArrayList;
        this.action = action;
    }

    public final void sortTheVideos(JSONArray arrayResult, Writer fileWriter) throws IOException {
        ArrayList<VideosForRating> theMovies = new ArrayList<>();
        int yearIndex = 0;
        int genresIndex = 1;
        for (Video theVideo : movieArrayList) {
            boolean ok = true;
            List<String> yearString = this.action.getFilters().get(yearIndex);
            int year = Integer.parseInt(yearString.get(0));
            if (theVideo.getNumberOfRatings() != 0) {
                if (theVideo.getYear() != year) {
                    ok = false;
                } else {
                    List<String> genresString = this.action.getFilters().get(genresIndex);
                    if (genresString.size() != theVideo.getGenres().size()) {
                        ok = false;
                    } else {
                        ArrayList<String> copyVideoGenres = new ArrayList<>(theVideo.getGenres());
                        ArrayList<String> copyCommandGenres = new ArrayList<>(genresString);
                        Collections.sort(copyVideoGenres);
                        Collections.sort(copyCommandGenres);
                        if (!copyVideoGenres.equals(copyCommandGenres)) {
                            ok = false;
                        }
                    }
                }
            } else {
                ok = false;
            }
            if (ok) {
                VideosForRating toAdd = new VideosForRating(theVideo.getTitle(), theVideo.getRating());
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
