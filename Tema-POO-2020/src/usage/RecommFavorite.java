package usage;

import base.User;
import base.Video;
import fileio.ActionInputData;
import fileio.Writer;
import org.json.simple.JSONArray;

import java.util.ArrayList;

public final class RecommFavorite {
    private final ArrayList<Video> totalVideoList;
    private final ArrayList<User> userArrayList;
    private final ActionInputData action;

    public RecommFavorite(ArrayList<Video> totalVideoList,
                          ArrayList<User> userArrayList,
                          ActionInputData action) {
        this.totalVideoList = totalVideoList;
        this.userArrayList = userArrayList;
        this.action = action;
    }

    public void showFavorite(JSONArray arrayResult, Writer fileWriter) {
        User theUser = null;
        for (User getTheName : userArrayList) {
            if (getTheName.getUsername().equals(action.getUsername())
                    && getTheName.getUserSubType().equals("PREMIUM")) {
                theUser = getTheName;
                break;
            }
        }
        if (theUser != null) {

        }
    }
}
