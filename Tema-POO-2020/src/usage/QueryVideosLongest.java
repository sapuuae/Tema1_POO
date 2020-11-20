package usage;

import base.Movie;
import base.Show;
import base.Video;
import fileio.ActionInputData;
import fileio.Writer;
import org.json.simple.JSONArray;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class QueryVideosLongest {
    private final ArrayList<Video> videoArrayList;
    private final ActionInputData action;

    public QueryVideosLongest(ArrayList<Video> videoArrayList, ActionInputData action) {
        this.videoArrayList = videoArrayList;
        this.action = action;
    }

    private boolean checkVideo(Video theVideo) {
        boolean ok = true;
        int yearIndex = 0;
        int genresIndex = 1;
        List<String> yearString = this.action.getFilters().get(yearIndex);
        int year = 0;
        if (yearString.get(0) != null) {
            year = Integer.parseInt(yearString.get(0));
        }
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
        return ok;
    }

    public final void showLongestShows(JSONArray arrayResult, Writer fileWriter) throws IOException {
        ArrayList<Show> showsList = new ArrayList<>();
        for (Video theVideo : videoArrayList) {
            if (checkVideo(theVideo)) {
                showsList.add((Show)theVideo);
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
        arrayResult.add(fileWriter.writeFile(action.getActionId(), "?",
                "Query result: " + finalList + ""));
    }

    public final void moviesLongestView(JSONArray arrayResult, Writer fileWriter) throws IOException {
        ArrayList<Movie> videosList = new ArrayList<>();
        for (Video theVideo : videoArrayList) {
            if (checkVideo(theVideo)) {
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
        arrayResult.add(fileWriter.writeFile(action.getActionId(), "?",
                "Query result: " + finalList + ""));
    }



}
