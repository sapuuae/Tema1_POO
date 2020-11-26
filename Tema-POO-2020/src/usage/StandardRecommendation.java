package usage;

import base.User;
import base.Video;
import fileio.ActionInputData;
import fileio.Writer;
import org.json.simple.JSONArray;

import java.io.IOException;
import java.util.ArrayList;

public final class StandardRecommendation {
    private final ArrayList<User> userArrayList;
    private final ActionInputData action;
    private final ArrayList<Video> totalVideoArray;

    public StandardRecommendation(final ArrayList<User> userArrayList,
                                  final ActionInputData action,
                                  final ArrayList<Video> totalVideoArray) {
        this.userArrayList = userArrayList;
        this.action = action;
        this.totalVideoArray = totalVideoArray;
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
        User theUser = null;
        for (User getTheName : userArrayList) {
            if (getTheName.getUsername().equals(username)) {
                theUser = getTheName;
                break;
            }
        }
        String recommendedVideo = null;
        if (theUser != null) {
            for (Video v : totalVideoArray) {
                if (!theUser.getHistory().containsKey(v.getTitle())) {
                    recommendedVideo = v.getTitle();
                    break;
                }
            }
        }
        char ch1 = action.getType().charAt(0);
        ch1 = Character.toUpperCase(ch1);
        String s = action.getType().substring(1);
        if (recommendedVideo != null) {
            arrayResult.add(fileWriter.writeFile(action.getActionId(), "?",
                    ch1 + s + "Recommendation " + "result: " + recommendedVideo));
        } else {
            arrayResult.add(fileWriter.writeFile(action.getActionId(), "?",
                    ch1 + s + "Recommendation " + "cannot be applied!"));
        }
    }

}
