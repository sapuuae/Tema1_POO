package usage;

import base.User;
import base.Video;
import fileio.ActionInputData;

import java.util.ArrayList;

public class Recommendation {
    private final ActionInputData action;
    private final ArrayList<User> userArrayList;
    private final ArrayList<Video> totalVideoList;

    public Recommendation(final ActionInputData action,
                          final ArrayList<User> userArrayList,
                          final ArrayList<Video> totalVideoList) {
        this.action = action;
        this.userArrayList = userArrayList;
        this.totalVideoList = totalVideoList;
    }

    public final ActionInputData getAction() {
        return action;
    }

    public final ArrayList<User> getUserArrayList() {
        return userArrayList;
    }

    public final ArrayList<Video> getTotalVideoList() {
        return totalVideoList;
    }

    /**
     * Get an user object by his username.
     * @param username the username to search for
     * @return the user object
     */
    public User getTheUser(final String username) {
        User theUser = null;
        for (User getTheName : getUserArrayList()) {
            if (getTheName.getUsername().equals(username)) {
                theUser = getTheName;
                break;
            }
        }
        return theUser;
    }

    /**
     * Get the video which should be recommended to the user.
     * @param theUser user in whose history map to search for
     * @param totalListWithRatingsSorted the list of videos in which to search for a video
     * @return the video which is recommended to the user
     */
    public String getRecommendedVideo(final User theUser,
                                      final ArrayList<Video> totalListWithRatingsSorted) {
        String recommendedVideo = null;
        if (theUser != null) {
            /*
            Get the user and the video for him.
             */
            for (Video v : totalListWithRatingsSorted) {
                if (!theUser.getHistory().containsKey(v.getTitle())) {
                    recommendedVideo = v.getTitle();
                    break;
                }
            }
        }
        return recommendedVideo;
    }
}
