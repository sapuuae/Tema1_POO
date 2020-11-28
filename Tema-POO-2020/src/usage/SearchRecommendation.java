package usage;

import base.User;
import base.Video;
import fileio.ActionInputData;
import fileio.Writer;
import org.json.simple.JSONArray;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public final class SearchRecommendation extends Recommendation {

    public SearchRecommendation(final ArrayList<User> userArrayList,
                                final ActionInputData action,
                                final ArrayList<Video> totalVideoList) {
        super(action, userArrayList, totalVideoList);
    }

    /**
     * Give all the videos from a genre to a specified user.
     * @param arrayResult the array used to write in file
     * @param fileWriter used for writing in file
     * @throws IOException check I/O
     */
    public void searchTheVideos(final JSONArray arrayResult,
                                final Writer fileWriter) throws IOException {
        String genre = getAction().getGenre();
        User theUser = null;
        for (User getTheName : getUserArrayList()) {
            if (getTheName.getUsername().equals(getAction().getUsername())
            && getTheName.getUserSubType().equals("PREMIUM")) {
                theUser = getTheName;
                break;
            }
        }
        /*
        Create the list with videos with genre from the input.
         */
        ArrayList<Video> videosWithThatGenre = new ArrayList<>();
        if (theUser != null) {
            for (Video v : getTotalVideoList()) {
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
            // noinspection unchecked
            arrayResult.add(fileWriter.writeFile(getAction().getActionId(), "?",
                    "Search" + "Recommendation result: " + finalList));
        } else {
            // noinspection unchecked
            arrayResult.add(fileWriter.writeFile(getAction().getActionId(), "?",
                    "Search" + "Recommendation " + "cannot be applied!"));
        }
    }
}
