package base;

import java.util.ArrayList;
import java.util.Map;

public class User {
    private final String userSubType;
    private final ArrayList<String> favoriteMovies;
    private final String username;
    private final Map<String, Integer> history;


    public User(final String userSubType, final ArrayList<String> favoriteMovies,
                final String username, final Map<String, Integer> history) {
        this.userSubType = userSubType;
        this.favoriteMovies = favoriteMovies;
        this.username = username;
        this.history = history;
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
     Add the Video to user's favorites.
     */
    public final void addFavorite(final Video video) {
        boolean isInMap = this.history.containsKey(video.getTitle());
        if (isInMap) {
            favoriteMovies.add(video.getTitle());
            video.setNumberofAparitionsInFavorite(
                    video.getNumberofAparitionsInFavorite() + 1);
        }
    }
    /**
     Add the video to history or increment the number of viewed times.
     */
    public final void makeItViewed(final Video video) {
        boolean isInMap = this.history.containsKey(video.getTitle());
        if (isInMap) {
            history.put(video.getTitle(), history.get(video.getTitle()) + 1);
        } else {
            history.put(video.getTitle(), 1);
        }
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
