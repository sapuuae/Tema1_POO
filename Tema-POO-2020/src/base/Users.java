package base;

import java.util.ArrayList;
import java.util.Map;

public class Users {
    private final String userSubType;
    private final ArrayList<String> favoriteMovies;
    private final String username;
    private final Map<String, Integer> history;


    public Users(final String userSubType, final ArrayList<String> favoriteMovies,
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
