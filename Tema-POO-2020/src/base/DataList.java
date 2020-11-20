package base;

import entertainment.MySeason;
import entertainment.Season;
import fileio.ActionInputData;
import fileio.ActorInputData;
import fileio.Input;
import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.UserInputData;
import fileio.Writer;
import org.json.simple.JSONArray;
import usage.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class DataList {
    private final Input input;
    /*
    Users' list.
     */
    private ArrayList<User> userArrayList;
    /*
    Movies' list.
     */
    private ArrayList<Video> movieArrayList;
    /*
    Serials' list,
     */
    private ArrayList<Video> showArrayList;
    /*
    Actors' list.
     */
    private ArrayList<Actor> actorArrayList;
    /*
    Total list of videos and serials.
     */
    private ArrayList<Video> totalVideoArray;

    public DataList(final Input input) {
        this.input = input;
        this.initArrays();
    }

    /**
     * Put the fields in the arrays.
     */
    public final void initArrays() {
        int order = 0;
        /*
        Convert data from input variable to
        users type variable
        */
        userArrayList = new ArrayList<>();
        movieArrayList = new ArrayList<>();
        showArrayList = new ArrayList<>();
        actorArrayList = new ArrayList<>();
        totalVideoArray = new ArrayList<>();

        /*
         * Complete data from movies list.
         */
        for (int i = 0; i < input.getMovies().size(); i++) {
            MovieInputData movieData = input.getMovies().get(i);
            Movie newMovie = new Movie(movieData.getTitle(),
                    movieData.getYear(), movieData.getCast(),
                    movieData.getGenres(), movieData.getDuration(), order);

            movieArrayList.add(newMovie);
            totalVideoArray.add(newMovie);
            order++;
        }

        /*
         * Complete data from show list.
         */
        for (int i = 0; i < input.getSerials().size(); i++) {
            SerialInputData serialData = input.getSerials().get(i);
            ArrayList<Season> seasonList = serialData.getSeasons();
            ArrayList<MySeason> newSeasonList = new ArrayList<>();
            for (Season normalSeason : seasonList) {
                MySeason newSeason = new MySeason(normalSeason.getCurrentSeason(),
                        normalSeason.getDuration());
                newSeasonList.add(newSeason);
            }
            int totalDuration = 0;
            for (Season s : seasonList) {
                totalDuration += s.getDuration();
            }
            Show newShow = new Show(serialData.getTitle(),
                    serialData.getYear(), serialData.getCast(),
                    serialData.getGenres(), serialData.getNumberSeason(),
                    newSeasonList, totalDuration, order);

            showArrayList.add(newShow);
            totalVideoArray.add(newShow);
            order++;
        }

        /*
        Complete data from users list
         */
        for (int i = 0; i < input.getUsers().size(); i++) {
            ArrayList<String> theFavorites = input.getUsers().get(i).getFavoriteMovies();
            for (String s : theFavorites) {
                boolean ok = true;
                for (Video theMovie : movieArrayList) {
                    if (theMovie.getTitle().equals(s)) {
                        theMovie.setNumberofAparitionsInFavorite(
                                theMovie.getNumberofAparitionsInFavorite() + 1);
                        ok = false;
                        break;
                    }
                }
                if (ok) {
                    for (Video theShow : showArrayList) {
                        if (theShow.getTitle().equals(s)) {
                            theShow.setNumberofAparitionsInFavorite(
                                    theShow.getNumberofAparitionsInFavorite() + 1);
                            break;
                        }
                    }
                }
            }

            for (Map.Entry<String, Integer> entry :
                    input.getUsers().get(i).getHistory().entrySet()) {
                String key = entry.getKey();
                Integer views = entry.getValue();
                boolean ok = true;
                for (Video v : movieArrayList) {
                    if (v.getTitle().equals(key)) {
                        v.setTotalNumberOfViewed(v.getTotalNumberOfViewed() + views);
                        ok = false;
                        break;
                    }
                }
                if (ok) {
                    for (Video v : showArrayList) {
                        if (v.getTitle().equals(key)) {
                            v.setTotalNumberOfViewed(v.getTotalNumberOfViewed() + views);
                            break;
                        }
                    }
                }
            }

            UserInputData userData = input.getUsers().get(i);
            User newUser = new User(userData.getSubscriptionType(),
                    userData.getFavoriteMovies(), userData.getUsername(),
                    userData.getHistory());

            userArrayList.add(newUser);
        }

        for (int i = 0; i < input.getActors().size(); i++) {
            ActorInputData actorData = input.getActors().get(i);
            Actor newActor = new Actor(actorData.getName(),
                    actorData.getCareerDescription(), actorData.getFilmography(),
                    actorData.getAwards());
            actorArrayList.add(newActor);
        }
    }

    public final void checkActions(final Input dataInput, final JSONArray arrayResult,
                                   final Writer fileWriter) throws IOException {
        for (int i = 0; i < dataInput.getCommands().size(); i++) {
            ActionInputData actionData = dataInput.getCommands().get(i);
            /*
                Verify the actions.
             */
            if (actionData.getActionType().equals("command")) {
                Command myCommand = new Command(userArrayList, movieArrayList, showArrayList);
                myCommand.makeTheCommand(actionData, arrayResult, fileWriter);
            } else if (actionData.getActionType().equals("query")) {
                if (actionData.getObjectType().equals("actors")) {
                    if (actionData.getCriteria().equals("average")) {
                        QueryAverage theAverageQuery = new QueryAverage(movieArrayList,
                                showArrayList, actorArrayList);
                        theAverageQuery.makeTheAverage(actionData.getNumber(), arrayResult,
                                fileWriter, actionData);
                    } else if (actionData.getCriteria().equals("awards")) {
                        QueryAwards theAwardsQuery = new QueryAwards(actorArrayList,
                                actionData.getFilters().get(actionData.getFilters().size() - 1));
                        theAwardsQuery.makeTheSort(actionData, arrayResult, fileWriter);
                    } else if (actionData.getCriteria().equals("filter_description")) {
                        int listWithFiltersCnt = 2;
                        QueryFilters theFilterQuery = new QueryFilters(actorArrayList,
                                actionData.getFilters().get(listWithFiltersCnt));
                        theFilterQuery.checkTheFilters(actionData, arrayResult, fileWriter);
                    }
                } else if (actionData.getObjectType().equals("movies")) {
                    if (actionData.getCriteria().equals("ratings")) {
                        QueryVideosRating theMoviesSorted = new QueryVideosRating(movieArrayList,
                                actionData);
                        theMoviesSorted.sortTheMovies(arrayResult, fileWriter);
                    } else if (actionData.getCriteria().equals("favorite")) {
                        QueryVideosFavorite theFavorites = new QueryVideosFavorite(
                                movieArrayList, actionData);
                        theFavorites.showTheFavorites(arrayResult, fileWriter);
                    } else if (actionData.getCriteria().equals("longest")) {
                        QueryVideosLongest theLongestMovies = new QueryVideosLongest(
                                movieArrayList, actionData);
                        theLongestMovies.moviesLongestView(arrayResult, fileWriter);
                    } else if (actionData.getCriteria().equals("most_viewed")) {
                        QueryVideosMostViewed theMostViewed = new QueryVideosMostViewed(
                                movieArrayList, actionData);
                        theMostViewed.showTheMostViewed(arrayResult, fileWriter);
                    }
                } else if (actionData.getObjectType().equals("shows")) {
                    if (actionData.getCriteria().equals("ratings")) {
                        QueryVideosRating theShowsSorted = new QueryVideosRating(showArrayList,
                                actionData);
                        theShowsSorted.sortTheMovies(arrayResult, fileWriter);
                    } else if (actionData.getCriteria().equals("favorite")) {
                        QueryVideosFavorite theFavorites = new QueryVideosFavorite(
                                showArrayList, actionData);
                        theFavorites.showTheFavorites(arrayResult, fileWriter);
                    } else if (actionData.getCriteria().equals("longest")) {
                        QueryVideosLongest theLongestShows = new QueryVideosLongest(
                                showArrayList, actionData);
                        theLongestShows.showLongestShows(arrayResult, fileWriter);
                    } else if (actionData.getCriteria().equals("most_viewed")) {
                        QueryVideosMostViewed theMostViewed = new QueryVideosMostViewed(
                                showArrayList, actionData);
                        theMostViewed.showTheMostViewed(arrayResult, fileWriter);
                    }
                } else if (actionData.getObjectType().equals("users")) {
                    if (actionData.getCriteria().equals("num_ratings")) {
                        QueryUsersActive theActiveUsers = new QueryUsersActive(
                                userArrayList, actionData);
                        theActiveUsers.showTheUsers(arrayResult, fileWriter);
                    }
                }
            }
            else if (actionData.getActionType().equals("recommendation")) {
                if (actionData.getType().equals("standard")) {
                    RecommStandard theRecomm = new RecommStandard(
                            userArrayList, actionData, totalVideoArray);
                    theRecomm.showUsers(actionData.getUsername(), arrayResult, fileWriter);
                } else if (actionData.getType().equals("best_unseen")) {
                    RecommBestUnseen theUnseen = new RecommBestUnseen(movieArrayList,
                            showArrayList, actionData, userArrayList);
                    theUnseen.getTheBestUnseen(actionData.getUsername(), arrayResult, fileWriter);
                } else if (actionData.getType().equals("search")) {
                    RecommSearch theRecomm = new RecommSearch(userArrayList, actionData,
                            totalVideoArray);
                    theRecomm.searchTheVideos(arrayResult, fileWriter);
                } else if (actionData.getType().equals("popular")) {
                    RecommPopular theRecomm = new RecommPopular(totalVideoArray,
                            actionData, userArrayList);
                    theRecomm.showPopular(arrayResult, fileWriter);
                }
            }
        }
    }
}
