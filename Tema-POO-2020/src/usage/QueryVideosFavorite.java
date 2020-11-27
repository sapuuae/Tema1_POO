package usage;

import base.Video;
import fileio.ActionInputData;
import fileio.Writer;
import org.json.simple.JSONArray;
import utils.Utils;

import java.io.IOException;
import java.util.ArrayList;

public class QueryVideosFavorite {
    private final ArrayList<Video> videoArrayList;
    private final ActionInputData action;

    public QueryVideosFavorite(final ArrayList<Video> videoArrayList,
                               final ActionInputData action) {
        this.action = action;
        this.videoArrayList = videoArrayList;
    }

    /**
     * Show the most added to favorite videos.
     * @param arrayResult the array used to write in file
     * @param fileWriter used for writing in file
     * @throws IOException check I/O
     */
    public final void showTheFavorites(final JSONArray arrayResult,
                                       final Writer fileWriter) throws IOException {
        /*
        Add only videos which meets the criteria in this list.
         */
        ArrayList<Video> copyOfVideos = new ArrayList<>();
        for (Video theVideo : videoArrayList) {
            boolean ok;
            if (theVideo.getNumberOfApparitionsInFavorite() != 0) {
                ok = Utils.checkVideo(theVideo, this.action);
            } else {
                ok = false;
            }
            if (ok) {
                copyOfVideos.add(theVideo);
            }
        }
        if (action.getSortType().equals("asc")) {
            copyOfVideos.sort((o1, o2) -> {
                int c;
                c = o1.getNumberOfApparitionsInFavorite().compareTo(
                        o2.getNumberOfApparitionsInFavorite());
                if (c == 0) {
                    c = o1.getTitle().compareTo(o2.getTitle());
                }
                return c;
            });
        } else {
            copyOfVideos.sort((o1, o2) -> {
                int c;
                c = o2.getNumberOfApparitionsInFavorite().compareTo(
                        o1.getNumberOfApparitionsInFavorite());
                if (c == 0) {
                    c = o2.getTitle().compareTo(o1.getTitle());
                }
                return c;
            });
        }
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
        //noinspection unchecked
        arrayResult.add(fileWriter.writeFile(action.getActionId(), "?",
                "Query result: " + finalList + ""));
    }

}
