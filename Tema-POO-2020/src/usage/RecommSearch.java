package usage;

import base.User;
import base.Video;
import fileio.ActionInputData;
import fileio.Writer;
import org.json.simple.JSONArray;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public final class RecommSearch {
    private final ArrayList<User> userArrayList;
    private final ActionInputData action;
    private final ArrayList<Video> totalVideoList;

    public RecommSearch(final ArrayList<User> userArrayList,
                        final ActionInputData action,
                        final ArrayList<Video> totalVideoList) {
        this.userArrayList = userArrayList;
        this.action = action;
        this.totalVideoList = totalVideoList;
    }

    public void searchTheVideos(JSONArray arrayResult, Writer fileWriter) throws IOException {
        String genre = action.getGenre();
        User theUser = null;
        for (User getTheName : userArrayList) {
            if (getTheName.getUsername().equals(action.getUsername())
            && getTheName.getUserSubType().equals("PREMIUM")) {
                theUser = getTheName;
                break;
            }
        }
        ArrayList<Video> videosWithThatGenre = new ArrayList<>();
        if (theUser != null) {
            for (Video v : totalVideoList) {
                ArrayList<String> videoGenres = v.getGenres();
                boolean ok = videoGenres.contains(genre);
                if (ok) {
                    Map<String, Integer> userHistory = theUser.getHistory();
                    if (!userHistory.containsKey(v.getTitle())) {
                        videosWithThatGenre.add(v);
                    }
                }
            }
        }
        videosWithThatGenre.sort((o1, o2) -> {
            int c;
            c = o1.getRating().compareTo(o2.getRating());
            if (c == 0) {
                c = o1.getTitle().compareTo(o2.getTitle());
            }
            return c;
        });
        if (theUser != null && videosWithThatGenre.size() != 0) {
            ArrayList<String> finalList = new ArrayList<>();
            for (Video v : videosWithThatGenre) {
                finalList.add(v.getTitle());
            }
            arrayResult.add(fileWriter.writeFile(action.getActionId(), "?",
                    "Search" + "Recommendation result: " + finalList));
        } else {
            arrayResult.add(fileWriter.writeFile(action.getActionId(), "?",
                    "Search" + "Recommendation " + "cannot be applied!"));
        }
    }
}
