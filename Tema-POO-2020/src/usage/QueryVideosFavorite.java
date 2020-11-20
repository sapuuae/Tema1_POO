package usage;

import base.Video;
import fileio.ActionInputData;
import fileio.Writer;
import org.json.simple.JSONArray;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class QueryVideosFavorite {
    private final ArrayList<Video> videoArrayList;
    private final ActionInputData action;

    public QueryVideosFavorite(ArrayList<Video> videoArrayList, final ActionInputData action) {
        this.action = action;
        this.videoArrayList = videoArrayList;
    }

    public final void showTheFavorites(JSONArray arrayResult, Writer fileWriter) throws IOException {
        ArrayList<Video> copyOfVideos = new ArrayList<>();
        int yearIndex = 0;
        int genresIndex = 1;
        for (Video theVideo : videoArrayList) {
            boolean ok = true;
            List<String> yearString = this.action.getFilters().get(yearIndex);
            int year = 0;
            if (yearString.get(yearIndex) != null) {
                year = Integer.parseInt(yearString.get(yearIndex));
            }
            if (theVideo.getNumberofAparitionsInFavorite() != 0) {
                if (theVideo.getYear() != year && year != 0) {
                    ok = false;
                } else {
                    List<String> genresString = this.action.getFilters().get(genresIndex);
                    ArrayList<String> videoGenres = theVideo.getGenres();
                    for (String s : genresString) {
                        if (!videoGenres.contains(s)) {
                            ok = false;
                            break;
                        }
                    }
                }
            } else {
                ok = false;
            }
            if (ok) {
                copyOfVideos.add(theVideo);
            }
        }
        copyOfVideos.sort((o1, o2) -> {
            int c;
            c = o2.getNumberofAparitionsInFavorite().compareTo(
                    o1.getNumberofAparitionsInFavorite());
            if (c == 0) {
                c = o2.getTitle().compareTo(o1.getTitle());
            }
            return c;
        });

        ArrayList<String> finalList = new ArrayList<>();
        if (copyOfVideos.size() < action.getNumber()) {
            for (Video copy : copyOfVideos) {
                finalList.add(copy.getTitle());
            }
        } else {
            for (int i = 0; i < action.getNumber(); i++) {
                finalList.add(copyOfVideos.get(i).getTitle());
            }
        }
        arrayResult.add(fileWriter.writeFile(action.getActionId(), "?",
                "Query result: " + finalList + ""));
    }

}
