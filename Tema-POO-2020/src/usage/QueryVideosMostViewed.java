package usage;

import base.Video;
import fileio.ActionInputData;
import fileio.Writer;
import org.json.simple.JSONArray;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class QueryVideosMostViewed {
    private final ArrayList<Video> videoArrayList;
    private final ActionInputData action;

    public QueryVideosMostViewed(final ArrayList<Video> videoArrayList,
                                 final ActionInputData action) {
        this.videoArrayList = videoArrayList;
        this.action = action;
    }

    private boolean checkVideo(final Video theVideo) {
        boolean ok = true;
        int yearIndex = 0;
        int genresIndex = 1;
        List<String> yearString = this.action.getFilters().get(yearIndex);
        int year = 0;
        if (yearString.get(0) != null) {
            year = Integer.parseInt(yearString.get(0));
        }
        if (theVideo.getTotalNumberOfViewed() != 0) {
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

    /**
     * Show the most viewed videos, sorted by the number of views and name.
     * @param arrayResult the array used to write in file
     * @param fileWriter used for writing in file
     * @throws IOException check I/O
     */
    public void showTheMostViewed(final JSONArray arrayResult,
                                  final Writer fileWriter) throws IOException {
        ArrayList<Video> showsList = new ArrayList<>();
        for (Video theVideo : videoArrayList) {
            if (checkVideo(theVideo)) {
                showsList.add(theVideo);
            }
        }
        if (action.getSortType().equals("asc")) {
            showsList.sort((o1, o2) -> {
                int c;
                c = o1.getTotalNumberOfViewed().compareTo(o2.getTotalNumberOfViewed());
                if (c == 0) {
                    c = o1.getTitle().compareTo(o2.getTitle());
                }
                return c;
            });
        } else {
            showsList.sort((o1, o2) -> {
                int c;
                c = o2.getTotalNumberOfViewed().compareTo(o1.getTotalNumberOfViewed());
                if (c == 0) {
                    c = o2.getTitle().compareTo(o1.getTitle());
                }
                return c;
            });
        }
        ArrayList<String> finalList = new ArrayList<>();
        if (showsList.size() < action.getNumber()) {
            for (Video theShow : showsList) {
                finalList.add(theShow.getTitle());
            }
        } else {
            for (int i = 0; i < action.getNumber(); i++) {
                finalList.add(showsList.get(i).getTitle());
            }
        }
        // noinspection unchecked
        arrayResult.add(fileWriter.writeFile(action.getActionId(), "?",
                "Query result: " + finalList + ""));
    }
}
