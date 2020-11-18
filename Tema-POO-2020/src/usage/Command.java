package usage;

import base.*;
import fileio.ActionInputData;
import fileio.Writer;
import org.json.simple.JSONArray;

import java.io.IOException;
import java.util.ArrayList;

public class Command {
    private static Command singleCommand = null;
    /*
    Users' list.
     */
    private final ArrayList<User> userArrayList;
    /*
    Movies' list.
     */
    private final ArrayList<Video> movieArrayList;
    /*
    Serials' list,
     */
    private final ArrayList<Video> showArrayList;

    public Command(final ArrayList<User> userArrayList, final ArrayList<Video> movieArrayList,
                   final ArrayList<Video> showArrayList) {
        this.userArrayList = userArrayList;
        this.movieArrayList = movieArrayList;
        this.showArrayList = showArrayList;
    }

    public static Command getInstance(final ArrayList<User> userArrayList,
                                      final ArrayList<Video> movieArrayList,
                                      final ArrayList<Video> showArrayList) {
        if (singleCommand == null) {
            singleCommand = new Command(userArrayList, movieArrayList, showArrayList);
        }
        return singleCommand;
    }

    public final void makeTheCommand(final ActionInputData actionData,
                                     final JSONArray arrayResult, final Writer fileWriter)
            throws IOException {
        /*
                Get the User and Video for the command, using title and username.
                 */
        UserWrapper getTheUser = new UserWrapper();
        VideoWrapper getTheVideo = new VideoWrapper();
        getUsernameAndVideo(actionData.getUsername(), actionData.getTitle(),
                getTheUser, getTheVideo);
        if (getTheUser.getInteriorUser() == null
                || getTheVideo.getInteriorVideo() == null) {
            return;
        }
        User myUser = getTheUser.getInteriorUser();
        Video myVideo = getTheVideo.getInteriorVideo();
                /*
                    Check favorite command.
                 */
        if (actionData.getType().equals("favorite")) {
            myUser.addFavorite(myVideo,
                    arrayResult, actionData.getActionId(), fileWriter);
        } else if (actionData.getType().equals("view")) {
            myUser.makeItViewed(myVideo,
                    arrayResult, actionData.getActionId(), fileWriter);
        } else if (actionData.getType().equals("rating")) {
            if (actionData.getSeasonNumber() == 0) {
                myUser.addRatingVideo(myVideo, actionData.getGrade(),
                        arrayResult, fileWriter, actionData.getActionId());
            } else {
                myUser.addRatingVideo(myVideo, actionData.getGrade(),
                        actionData.getActionId(), actionData.getSeasonNumber(),
                        arrayResult, fileWriter);
            }
        }
    }

    public final User getUser(final ArrayList<User> list, final String username) {
        for (User user : list) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public final Video getVideo(final ArrayList<Video> list, final String videoTitle) {
        for (Video video : list) {
            if (video.getTitle().equals(videoTitle)) {
                return video;
            }
        }
        return null;
    }

    public final void getUsernameAndVideo(final String username, final String videoTitle,
                                          final UserWrapper userWrapper,
                                          final VideoWrapper videoWrapper) {
        User addedUser = getUser(this.userArrayList, username);
        userWrapper.setInteriorUser(addedUser);
        Video addedVideo = getVideo(this.movieArrayList, videoTitle);
        if (addedVideo == null) {
            addedVideo = getVideo(this.showArrayList, videoTitle);
        }
        videoWrapper.setInteriorVideo(addedVideo);
    }

}
