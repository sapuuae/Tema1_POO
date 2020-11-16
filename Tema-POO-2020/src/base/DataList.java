package base;

import fileio.ActionInputData;
import fileio.ActorInputData;
import fileio.Input;
import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.UserInputData;

import java.util.ArrayList;

public class DataList {
    private final Input input;

    private ArrayList<User> userArrayList;
    /**
    creez o lista pentru filme
    */
    private ArrayList<Video> movieArrayList;
    /*
        creez o lista pentru seriale
     */
    private ArrayList<Video> showArrayList;
    private ArrayList<Actor> actorArrayList;

    public DataList(final Input input) {
        this.input = input;
        this.initArrays();
    }

    /**
     Put the fields in the arrays.
     */
    public final void initArrays() {
        /*
        Convert data from input variable to
        users type variable
        */
        userArrayList = new ArrayList<>();
        movieArrayList = new ArrayList<>();
        showArrayList = new ArrayList<>();
        actorArrayList = new ArrayList<>();
        /*
        Complete data from users list
         */
        for (int i = 0; i < input.getUsers().size(); i++) {
            UserInputData userData = input.getUsers().get(i);
            User newUser = new User(userData.getSubscriptionType(),
                    userData.getFavoriteMovies(), userData.getUsername(),
                    userData.getHistory());

            userArrayList.add(newUser);
        }

        /*
        Complete data from movies list
         */
        for (int i = 0; i < input.getMovies().size(); i++) {
            MovieInputData movieData = input.getMovies().get(i);
            Movie newMovie = new Movie(movieData.getTitle(),
                    movieData.getYear(), movieData.getCast(),
                    movieData.getGenres(), movieData.getDuration());

            movieArrayList.add(newMovie);
        }

        for (int i = 0; i < input.getSerials().size(); i++) {
            SerialInputData serialData = input.getSerials().get(i);
            Show newShow = new Show(serialData.getTitle(),
                    serialData.getYear(), serialData.getCast(),
                    serialData.getGenres(), serialData.getNumberSeason(),
                    serialData.getSeasons());

            showArrayList.add(newShow);
        }

        for (int i = 0; i < input.getActors().size(); i++) {
            ActorInputData actorData = input.getActors().get(i);
            Actor newActor = new Actor(actorData.getName(),
                    actorData.getCareerDescription(), actorData.getFilmography(),
                    actorData.getAwards());
            actorArrayList.add(newActor);
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


    public final void checkCommands(final Input dataInput) {
        for (int i = 0; i < dataInput.getCommands().size(); i++) {
            ActionInputData actionData = dataInput.getCommands().get(i);
            if (actionData.getActionType().equals("command")) {
                if (actionData.getType().equals("favorite")) {
                    UserWrapper getTheUser = new UserWrapper();
                    VideoWrapper getTheVideo = new VideoWrapper();
                    getUsernameAndVideo(actionData.getUsername(), actionData.getTitle(),
                            getTheUser, getTheVideo);
                    if (getTheUser.getInteriorUser() == null
                        || getTheVideo.getInteriorVideo() == null) {
                        return;
                    } else {
                        getTheUser.getInteriorUser().addFavorite(getTheVideo.getInteriorVideo());
                    }
                } else if (actionData.getType().equals("view")) {
                    UserWrapper getTheUser = new UserWrapper();
                    VideoWrapper getTheVideo = new VideoWrapper();
                    getUsernameAndVideo(actionData.getUsername(), actionData.getTitle(),
                            getTheUser, getTheVideo);
                    if (getTheUser.getInteriorUser() == null
                        || getTheVideo.getInteriorVideo() == null) {
                        return;
                    } else {
                        getTheUser.getInteriorUser().
                                makeItViewed(getTheVideo.getInteriorVideo());
                    }

                }
            }
        }
    }


}
