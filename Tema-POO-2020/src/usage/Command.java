package usage;

import base.User;
import base.UserWrapper;
import base.Video;
import base.VideoWrapper;
import fileio.ActionInputData;
import fileio.Writer;
import org.json.simple.JSONArray;

import java.io.IOException;
import java.util.ArrayList;

public class Command {
    private static Command singleCommand = null;
    /*
    Users list.
     */
    private final ArrayList<User> userArrayList;
    /*
    Movies list.
     */
    private final ArrayList<Video> movieArrayList;
    /*
    Shows list,
     */
    private final ArrayList<Video> showArrayList;

    public Command(final ArrayList<User> userArrayList, final ArrayList<Video> movieArrayList,
                   final ArrayList<Video> showArrayList) {
        this.userArrayList = userArrayList;
        this.movieArrayList = movieArrayList;
        this.showArrayList = showArrayList;
    }

    /**
     * Use the singleton design pattern to get an instance.
     *
     * @param userArrayList  users list
     * @param movieArrayList movies list
     * @param showArrayList  shows list
     * @return get the instance
     */
    public static Command getInstance(final ArrayList<User> userArrayList,
                                      final ArrayList<Video> movieArrayList,
                                      final ArrayList<Video> showArrayList) {
        if (singleCommand == null) {
            singleCommand = new Command(userArrayList, movieArrayList, showArrayList);
        }
        return singleCommand;
    }

    /**
     * Check every command and execute it.
     *
     * @param actionData  used for checking the type of command
     * @param arrayResult the array used to write in file
     * @param fileWriter  used for writing in file
     * @throws IOException check I/O
     */
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


    /**
     * Used to get the specific user from the user list by name.
     *
     * @param list     list of users
     * @param username the searched user
     * @return an User object
     */
    public final User getUser(final ArrayList<User> list, final String username) {
        for (User user : list) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    /**
     * Used to get the specific user from video list by title.
     *
     * @param list       lit of movies/shows
     * @param videoTitle title of the searched video
     * @return a Video object
     */
    public final Video getVideo(final ArrayList<Video> list, final String videoTitle) {
        for (Video video : list) {
            if (video.getTitle().equals(videoTitle)) {
                return video;
            }
        }
        return null;
    }

    /**
     * Return the User and Video objects.
     *
     * @param username     username of the User object
     * @param videoTitle   title of the Video object
     * @param userWrapper  a wrapper to move the User
     * @param videoWrapper a wrapper to move the Video
     */
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
