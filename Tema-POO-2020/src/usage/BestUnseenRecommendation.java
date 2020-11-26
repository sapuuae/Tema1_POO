package usage;

import base.User;
import base.Video;
import fileio.ActionInputData;

import java.io.IOException;
import java.util.ArrayList;

import fileio.Writer;
import org.json.simple.JSONArray;


public final class BestUnseenRecommendation {
    private final ArrayList<Video> movieArrayList;
    private final ArrayList<Video> showArrayList;
    private final ActionInputData action;
    private final ArrayList<User> userArrayList;

    public BestUnseenRecommendation(final ArrayList<Video> movieArrayList,
                                    final ArrayList<Video> showArrayList,
                                    final ActionInputData action,
                                    final ArrayList<User> userArrayList) {
        this.movieArrayList = movieArrayList;
        this.showArrayList = showArrayList;
        this.action = action;
        this.userArrayList = userArrayList;
    }

    /**
     * Give the best video from database, sorted by rating and the order in database.
     * @param username the user who'll get the recommendation
     * @param arrayResult the array used to write in file
     * @param fileWriter used for writing in file
     * @throws IOException check I/O
     */
    public void getTheBestUnseen(final String username, final JSONArray arrayResult,
                                 final Writer fileWriter) throws IOException {
        ArrayList<Video> totalListWithRatingsSorted = new ArrayList<>();
        totalListWithRatingsSorted.addAll(movieArrayList);
        totalListWithRatingsSorted.addAll(showArrayList);
        totalListWithRatingsSorted.sort((o1, o2) -> {
            int c;
            c = o2.getRating().compareTo(o1.getRating());
            if (c == 0) {
                c = o1.getOrderInDatabase().compareTo(o2.getOrderInDatabase());
            }
            return c;
        });

        User theUser = null;
        for (User getTheName : userArrayList) {
            if (getTheName.getUsername().equals(username)) {
                theUser = getTheName;
                break;
            }
        }
        String recommendedVideo = null;
        if (theUser != null) {
            for (Video v : totalListWithRatingsSorted) {
                if (!theUser.getHistory().containsKey(v.getTitle())) {
                    recommendedVideo = v.getTitle();
                    break;
                }
            }
        }
        if (recommendedVideo != null) {
            arrayResult.add(fileWriter.writeFile(action.getActionId(), "?",
                    "BestRatedUnseen" + "Recommendation " + "result: " + recommendedVideo));
        } else {
            arrayResult.add(fileWriter.writeFile(action.getActionId(), "?",
                    "BestRatedUnseen" + "Recommendation " + "cannot be applied!"));
        }
    }
}
