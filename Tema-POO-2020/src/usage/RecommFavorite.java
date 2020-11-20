package usage;

import base.User;
import base.Video;
import fileio.ActionInputData;
import fileio.Writer;
import org.json.simple.JSONArray;

import java.io.IOException;
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

    public void showFavorite(JSONArray arrayResult, Writer fileWriter) throws IOException {
        User theUser = null;
        for (User getTheName : userArrayList) {
            if (getTheName.getUsername().equals(action.getUsername())
                    && getTheName.getUserSubType().equals("PREMIUM")) {
                theUser = getTheName;
                break;
            }
        }
        if (theUser != null) {
            ArrayList<Video> theFavorites = new ArrayList<>();
            for (Video v : totalVideoList) {
                if (v.getNumberofAparitionsInFavorite() != 0) {
                    theFavorites.add(v);
                }
            }
            theFavorites.sort((o1, o2) -> {
                int c;
                c = o2.getNumberofAparitionsInFavorite().compareTo(o1.getNumberofAparitionsInFavorite());
                if (c == 0) {
                    c = o2.getOrderInDatabase().compareTo(o1.getOrderInDatabase());
                }
                return c;
            });
            String recommendedVideo = null;
            for (Video t : theFavorites) {
                if (!theUser.getHistory().containsKey(t.getTitle())) {
                    recommendedVideo = t.getTitle();
                    break;
                }
            }
            if (recommendedVideo != null) {
                arrayResult.add(fileWriter.writeFile(action.getActionId(), "?",
                        "Favorite" + "Recommendation " + "result: " + recommendedVideo));

            } else {
                arrayResult.add(fileWriter.writeFile(action.getActionId(), "?",
                        "Favorite" + "Recommendation " + "cannot be applied!"));
            }
        }
    }
}
