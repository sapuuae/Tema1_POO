package usage;

import base.User;
import base.Video;
import fileio.ActionInputData;
import fileio.Writer;
import org.json.simple.JSONArray;

import java.io.IOException;
import java.util.ArrayList;

public final class FavoriteRecommendation extends Recommendation {

    public FavoriteRecommendation(final ArrayList<Video> totalVideoList,
                                  final ArrayList<User> userArrayList,
                                  final ActionInputData action) {
        super(action, userArrayList, totalVideoList);
    }

    /**
     * Check the type of user and show the most favorite movie
     * from the database which isn't seen by him.
     * @param arrayResult the array used to write in file
     * @param fileWriter used for writing in file
     * @throws IOException check I/O
     */
    public void showFavorite(final JSONArray arrayResult,
                             final Writer fileWriter) throws IOException {
        User theUser = null;
        for (User getTheName : getUserArrayList()) {
            if (getTheName.getUsername().equals(getAction().getUsername())
                    && getTheName.getUserSubType().equals("PREMIUM")) {
                theUser = getTheName;
                break;
            }
        }
        if (theUser != null) {
            ArrayList<Video> theFavorites = new ArrayList<>();
            for (Video v : getTotalVideoList()) {
                if (v.getNumberOfApparitionsInFavorite() != 0) {
                    theFavorites.add(v);
                }
            }
            theFavorites.sort((o1, o2) -> {
                int c;
                c = o2.getNumberOfApparitionsInFavorite().compareTo(
                        o1.getNumberOfApparitionsInFavorite());
                if (c == 0) {
                    c = o1.getOrderInDatabase().compareTo(o2.getOrderInDatabase());
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
                // noinspection unchecked
                arrayResult.add(fileWriter.writeFile(getAction().getActionId(), "?",
                        "Favorite" + "Recommendation " + "result: " + recommendedVideo));

            } else {
                // noinspection unchecked
                arrayResult.add(fileWriter.writeFile(getAction().getActionId(), "?",
                        "Favorite" + "Recommendation " + "cannot be applied!"));
            }
        }
    }
}
