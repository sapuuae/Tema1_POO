package usage;

import base.User;
import base.Video;
import fileio.ActionInputData;

import java.io.IOException;
import java.util.ArrayList;

import fileio.Writer;
import org.json.simple.JSONArray;


public final class BestUnseenRecommendation extends Recommendation {

    public BestUnseenRecommendation(final ArrayList<Video> totalVideoArray,
                                    final ActionInputData action,
                                    final ArrayList<User> userArrayList) {
        super(action, userArrayList, totalVideoArray);
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
        /*
        Create a list with movies and shows, then sort all of them
        by rating and order in database.
         */
        ArrayList<Video> totalList = new ArrayList<>(getTotalVideoList());
        totalList.sort((o1, o2) -> {
            int c;
            c = o2.getRating().compareTo(o1.getRating());
            if (c == 0) {
                c = o1.getOrderInDatabase().compareTo(o2.getOrderInDatabase());
            }
            return c;
        });
        User theUser = getTheUser(username);
        String recommendedVideo = getRecommendedVideo(theUser, totalList);
        if (recommendedVideo != null) {
            // noinspection unchecked
            arrayResult.add(fileWriter.writeFile(getAction().getActionId(), "?",
                    "BestRatedUnseen" + "Recommendation " + "result: " + recommendedVideo));
        } else {
            // noinspection unchecked
            arrayResult.add(fileWriter.writeFile(getAction().getActionId(), "?",
                    "BestRatedUnseen" + "Recommendation " + "cannot be applied!"));
        }
    }
}
