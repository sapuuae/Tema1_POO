package usage;

import base.Movie;
import base.Show;
import base.Video;
import fileio.ActionInputData;
import fileio.Writer;
import org.json.simple.JSONArray;
import utils.Utils;

import java.io.IOException;
import java.util.ArrayList;

public class QueryVideosLongest {
    private final ArrayList<Video> videoArrayList;
    private final ActionInputData action;

    public QueryVideosLongest(final ArrayList<Video> videoArrayList,
                              final ActionInputData action) {
        this.videoArrayList = videoArrayList;
        this.action = action;
    }
    /**
     * Sort the shows by the duration and name, then write them in the file.
     * @param arrayResult the array used to write in file
     * @param fileWriter used for writing in file
     * @throws IOException check I/O
     */
    public final void longestShows(final JSONArray arrayResult,
                                   final Writer fileWriter) throws IOException {
        ArrayList<Show> showsList = new ArrayList<>();
        for (Video theVideo : videoArrayList) {
            if (Utils.checkVideo(theVideo, this.action)) {
                showsList.add((Show) theVideo);
            }
        }
        if (action.getSortType().equals("asc")) {
            showsList.sort((o1, o2) -> {
                int c;
                c = o1.getTotalDuration().compareTo(o2.getTotalDuration());
                if (c == 0) {
                    c = o1.getTitle().compareTo(o2.getTitle());
                }
                return c;
            });
        } else {
            showsList.sort((o1, o2) -> {
                int c;
                c = o2.getTotalDuration().compareTo(o1.getTotalDuration());
                if (c == 0) {
                    c = o2.getTitle().compareTo(o1.getTitle());
                }
                return c;
            });
        }
        ArrayList<String> finalList = new ArrayList<>();
        if (showsList.size() < action.getNumber()) {
            for (Show theShow : showsList) {
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

    /**
     * Sort the movies by the duration and name, then write them in the file.
     * @param arrayResult the array used to write in file
     * @param fileWriter used for writing in file
     * @throws IOException check I/O
     */
    public final void longestMovies(final JSONArray arrayResult,
                                    final Writer fileWriter) throws IOException {
        ArrayList<Movie> videosList = new ArrayList<>();
        for (Video theVideo : videoArrayList) {
            if (Utils.checkVideo(theVideo, this.action)) {
                videosList.add((Movie) theVideo);
            }
        }
        if (action.getSortType().equals("asc")) {
            videosList.sort((o1, o2) -> {
                int c;
                c = o1.getDuration().compareTo(o2.getDuration());
                if (c == 0) {
                    c = o1.getTitle().compareTo(o2.getTitle());
                }
                return c;
            });
        } else {
            videosList.sort((o1, o2) -> {
                int c;
                c = o2.getDuration().compareTo(o1.getDuration());
                if (c == 0) {
                    c = o2.getTitle().compareTo(o1.getTitle());
                }
                return c;
            });
        }
        ArrayList<String> finalList = new ArrayList<>();
        if (videosList.size() < action.getNumber()) {
            for (Movie theShow : videosList) {
                finalList.add(theShow.getTitle());
            }
        } else {
            for (int i = 0; i < action.getNumber(); i++) {
                finalList.add(videosList.get(i).getTitle());
            }
        }
        // noinspection unchecked
        arrayResult.add(fileWriter.writeFile(action.getActionId(), "?",
                "Query result: " + finalList + ""));
    }
}
