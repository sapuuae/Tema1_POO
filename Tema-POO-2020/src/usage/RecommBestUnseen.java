package usage;

import base.User;
import base.Video;
import fileio.ActionInputData;

import java.io.IOException;
import java.util.ArrayList;

import fileio.Writer;
import org.json.simple.JSONArray;


public final class RecommBestUnseen {
    private final ArrayList<Video> movieArrayList;
    private final ArrayList<Video> showArrayList;
    private final ActionInputData action;
    private final ArrayList<User> userArrayList;

    public RecommBestUnseen(ArrayList<Video> movieArrayList,
                            ArrayList<Video> showArrayList, final ActionInputData action,
                            ArrayList<User> userArrayList) {
        this.movieArrayList = movieArrayList;
        this.showArrayList = showArrayList;
        this.action = action;
        this.userArrayList = userArrayList;
    }

    public void getTheBestUnseen(String username, JSONArray arrayResult,
                                 Writer fileWriter) throws IOException {
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
        char ch1 = action.getType().charAt(0);
        ch1 = Character.toUpperCase(ch1);
        String s = action.getType().substring(1);
        if (recommendedVideo != null) {
            arrayResult.add(fileWriter.writeFile(action.getActionId(), "?",
                    "BestRatedUnseen" + "Recommendation " + "result: " + recommendedVideo));
        } else {
            arrayResult.add(fileWriter.writeFile(action.getActionId(), "?",
                    "BestRatedUnseen" + "Recommendation " + "cannot be applied!"));
        }
    }
}
