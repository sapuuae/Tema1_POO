package base;

import fileio.*;

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

    public User getUser(ArrayList<User> list, String username) {
        for (User user : list) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public Video getVideo(ArrayList<Video> list, String videoTitle) {
        for (Video video : list) {
            if (video.getTitle().equals(videoTitle)) {
                return video;
            }
        }
        return null;
    }


    public void checkCommands(Input input) {
        for (int i = 0; i < input.getCommands().size(); i++) {
            ActionInputData actionData = input.getCommands().get(i);
            if (actionData.getActionType().equals("command")) {
                if (actionData.getType().equals("favorite")) {
                    String username = actionData.getUsername();
                    String videoTitle = actionData.getTitle();
                    User indexUser = getUser(this.userArrayList, username);
                    Video addedVideo = getVideo(this.movieArrayList, videoTitle);
                    if (addedVideo == null) {
                        getVideo(this.showArrayList, videoTitle);
                    }
                    if (indexUser == null || addedVideo == null) {
                        return;
                    } else {
                        indexUser.addFavorite(addedVideo);
                    }
                }
            }
        }
    }


}
