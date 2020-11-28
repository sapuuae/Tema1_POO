package usage;

import base.Video;
import fileio.ActionInputData;
import fileio.Writer;
import org.json.simple.JSONArray;
import utils.Utils;

import java.io.IOException;
import java.util.ArrayList;

public final class QueryVideosRating {
    private final ArrayList<Video> videoArrayList;
    private final ActionInputData action;

    public QueryVideosRating(final ArrayList<Video> videoArrayList,
                             final ActionInputData action) {
        this.videoArrayList = videoArrayList;
        this.action = action;
    }

    private boolean checkTheVideo(final Video theVideo) {
        boolean ok;
        if (theVideo.getNumberOfRatings() != 0) {
            ok = Utils.checkVideo(theVideo, action);
        } else {
            ok = false;
        }
        return ok;
    }

    /**
     * Sort the videos using just rating and title.
     * @param arrayResult the array used to write in file
     * @param fileWriter used for writing in file
     * @throws IOException check I/O
     */
    public void sortTheVideos(final JSONArray arrayResult,
                              final Writer fileWriter) throws IOException {
        ArrayList<Video> theMovies = new ArrayList<>();
        for (Video theVideo : videoArrayList) {
            if (checkTheVideo(theVideo)) {
                theMovies.add(theVideo);
            }
        }
        if (action.getSortType().equals("asc")) {
            theMovies.sort((o1, o2) -> {
                int c;
                c = o1.getRating().compareTo(o2.getRating());
                if (c == 0) {
                    c = o1.getTitle().compareTo(o2.getTitle());
                }
                return c;
            });
        } else {
            theMovies.sort((o1, o2) -> {
                int c;
                c = o2.getRating().compareTo(o1.getRating());
                if (c == 0) {
                    c = o2.getTitle().compareTo(o1.getTitle());
                }
                return c;
            });
        }
        ArrayList<String> theFinalList = new ArrayList<>();
        if (theMovies.size() < action.getNumber()) {
            for (Video theVideo : theMovies) {
                theFinalList.add(theVideo.getTitle());
            }
        } else {
            for (int i = 0; i < action.getNumber(); i++) {
                theFinalList.add(theMovies.get(i).getTitle());
            }
        }
        // noinspection unchecked
        arrayResult.add(fileWriter.writeFile(action.getActionId(), "?",
                "Query result: " + theFinalList + ""));
    }
}
