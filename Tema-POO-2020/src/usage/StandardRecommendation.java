package usage;

import base.User;
import base.Video;
import fileio.ActionInputData;
import fileio.Writer;
import org.json.simple.JSONArray;

import java.io.IOException;
import java.util.ArrayList;

public final class StandardRecommendation extends Recommendation {

    public StandardRecommendation(final ArrayList<User> userArrayList,
                                  final ActionInputData action,
                                  final ArrayList<Video> totalVideoArray) {
        super(action, userArrayList, totalVideoArray);
    }

    /**
     * Give the first video unseen by user from the database.
     * @param username username to give the recommendation
     * @param arrayResult the array used to write in file
     * @param fileWriter used for writing in file
     * @throws IOException check I/O
     */
    public void showUsers(final String username, final JSONArray arrayResult,
                          final Writer fileWriter) throws IOException {
        User theUser = getTheUser(username);
        String recommendedVideo = getRecommendedVideo(theUser, getTotalVideoList());
        char ch1 = getAction().getType().charAt(0);
        ch1 = Character.toUpperCase(ch1);
        String s = getAction().getType().substring(1);
        if (recommendedVideo != null) {
            // noinspection unchecked
            arrayResult.add(fileWriter.writeFile(getAction().getActionId(), "?",
                    ch1 + s + "Recommendation " + "result: " + recommendedVideo));
        } else {
            // noinspection unchecked
            arrayResult.add(fileWriter.writeFile(getAction().getActionId(), "?",
                    ch1 + s + "Recommendation " + "cannot be applied!"));
        }
    }

}
