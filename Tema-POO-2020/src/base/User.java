package base;

import fileio.Writer;
import org.json.simple.JSONArray;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class User {
    private final String userSubType;
    private final ArrayList<String> favoriteMovies;
    private final String username;
    private final Map<String, Integer> history;
    private final HashMap<String, Boolean> checkSettedRating;

    public User(final String userSubType, final ArrayList<String> favoriteMovies,
                final String username, final Map<String, Integer> history) {
        this.userSubType = userSubType;
        this.favoriteMovies = favoriteMovies;
        this.username = username;
        this.history = history;
        this.checkSettedRating = new HashMap<>();
    }


    public final ArrayList<String> getFavoriteMovies() {
        return favoriteMovies;
    }

    public final String getUsername() {
        return username;
    }

    public final Map<String, Integer> getHistory() {
        return history;
    }

    public final String getUserSubType() {
        return userSubType;
    }

    /**
     * mark the video as rated by the user
     *
     * @param videoTitle for the title
     */
    public final void addToRated(final String videoTitle) {
        if (!checkSettedRating.get(videoTitle)) {
            checkSettedRating.put(videoTitle, true);
        }
    }

    /**
     * Add the Video to user's favorites.
     */
    public final void addFavorite(final Video video, final JSONArray arrayResult,
                                  final int actionId, final Writer fileWriter)
            throws IOException {
        boolean isInMap = this.history.containsKey(video.getTitle());
        if (isInMap) {
            for (String forChecking : favoriteMovies) {
                if (video.getTitle().equals(forChecking)) {
                    //noinspection unchecked
                    arrayResult.add(fileWriter.writeFile(actionId, "?",
                            "error -> " + video.getTitle() + " is already in favourite list"));
                    return;
                }
            }
            favoriteMovies.add(video.getTitle());
            //noinspection unchecked
            arrayResult.add(fileWriter.writeFile(actionId, "?",
                    "success -> " + video.getTitle() + " was added as favourite"));
            video.setNumberofAparitionsInFavorite(
                    video.getNumberofAparitionsInFavorite() + 1);
        } else {
            //noinspection unchecked
            arrayResult.add(fileWriter.writeFile(actionId, "?",
                    "error -> " + video.getTitle() + " is not seen"));
        }
    }

    /**
     * Add the video to history or increment the number of viewed times.
     */
    public final void makeItViewed(final Video video, final JSONArray arrayResult,
                                   final int actionId, final Writer fileWriter)
            throws IOException {
        boolean isInMap = this.history.containsKey(video.getTitle());
        if (isInMap) {
            history.put(video.getTitle(), history.get(video.getTitle()) + 1);
        } else {
            history.put(video.getTitle(), 1);
        }
        video.setTotalNumberOfViewed(video.getTotalNumberOfViewed() + 1);
        //noinspection unchecked
        arrayResult.add(fileWriter.writeFile(actionId, "?",
                "success -> " + video.getTitle() + " was viewed with total views "
                        + "of " + history.get(video.getTitle())));
    }

    /**
     * Add the rating for the movie.
     *
     * @param video the movie which will get the rating
     * @param grade movie grade from user
     */
    public final void addRatingVideo(final Video video, final Double grade,
                                     final JSONArray arrayResult, final Writer fileWriter,
                                     final int actionId)
            throws IOException {
        if (!this.history.containsKey(video.getTitle())) {
            //noinspection unchecked
            arrayResult.add(fileWriter.writeFile(actionId, "?",
                    "error -> " + video.getTitle() + " is not seen"));
            return;
        }
        if (checkSettedRating.containsKey(video.getTitle())) {
            //noinspection unchecked
            arrayResult.add(fileWriter.writeFile(actionId, "?",
                    "error -> " + video.getTitle() + " has been already rated"));
            return;
        }
        if (this.history.containsKey(video.getTitle())
                && !checkSettedRating.containsKey(video.getTitle())) {
            Movie theMovie = (Movie) video;
            theMovie.avgRating(grade);
            checkSettedRating.put(video.getTitle(), true);
            //noinspection unchecked
            arrayResult.add(fileWriter.writeFile(actionId, "?",
                    "success -> " + video.getTitle() + " was rated with " + grade
            + " by " + this.username));
        }
    }

    /**
     * Add the rating for the season.
     *
     * @param video        the show of whose season will take the rating
     * @param grade        grade of the season
     * @param seasonNumber the number of the season in list
     */

    public final void addRatingVideo(final Video video, final Double grade, final int actionId,
                                     final int seasonNumber, final JSONArray arrayResult,
                                     final Writer fileWriter)
            throws IOException {
        boolean isInMap = this.history.containsKey((video.getTitle()));
        if (!isInMap) {
            //noinspection unchecked
            arrayResult.add(fileWriter.writeFile(actionId, "?",
                    "error -> " + video.getTitle() + " is not seen"));
            return;
        }
        String getTheSeasonAndNumber = video.getTitle() + seasonNumber;
        if (checkSettedRating.containsKey(getTheSeasonAndNumber)) {
            //noinspection unchecked
            arrayResult.add(fileWriter.writeFile(actionId, "?",
                    "error -> " + video.getTitle() + " has been already rated"));
            return;
        }

        Show internalShow = (Show) video;
        internalShow.getSeasons().get(seasonNumber - 1).setAvgRating(grade);
        internalShow.avgRating();
        internalShow.setNumberOfRatings(internalShow.getNumberOfRatings() + 1);
        checkSettedRating.put(getTheSeasonAndNumber, true);
        //noinspection unchecked
        arrayResult.add(fileWriter.writeFile(actionId, "?",
                "success -> " + video.getTitle() + " was rated with " + grade
                        + " by " + this.username));
    }

    @Override
    public final String toString() {
        return "Users{"
                + "userSubType='" + userSubType + '\''
                + ", viewedMovies=" + favoriteMovies
                + ", username='" + username + '\''
                + ", history=" + history
                + '}';
    }

}
