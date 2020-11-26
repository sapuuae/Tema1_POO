package usage;

import base.User;
import fileio.ActionInputData;
import fileio.Writer;
import org.json.simple.JSONArray;

import java.io.IOException;
import java.util.ArrayList;

public final class QueryUsersActive {
    private final ArrayList<User> userArray;
    private final ActionInputData action;

    public QueryUsersActive(final ArrayList<User> userArray,
                            final ActionInputData action) {
        this.userArray = userArray;
        this.action = action;
    }

    /**
     * Show the most active users, who gave rating for movies and shows.
     * @param arrayResult the array used to write in file
     * @param fileWriter used for writing in file
     * @throws IOException check I/O
     */
    public void showTheUsers(final JSONArray arrayResult,
                             final Writer fileWriter) throws IOException {
        ArrayList<User> usersWhoGivenRating = new ArrayList<>();
        for (User theUser : userArray) {
            if (theUser.getGivenRatings() != 0) {
                usersWhoGivenRating.add(theUser);
            }
        }
        if (action.getSortType().equals("asc")) {
            usersWhoGivenRating.sort((o1, o2) -> {
                int c;
                c = o1.getGivenRatings().compareTo(o2.getGivenRatings());
                if (c == 0) {
                    c = o1.getUsername().compareTo(o2.getUsername());
                }
                return c;
            });
        } else {
            usersWhoGivenRating.sort((o1, o2) -> {
                int c;
                c = o2.getGivenRatings().compareTo(o1.getGivenRatings());
                if (c == 0) {
                    c = o2.getUsername().compareTo(o1.getUsername());
                }
                return c;
            });
        }
        ArrayList<String> finalList = new ArrayList<>();
        if (usersWhoGivenRating.size() < action.getNumber()) {
            for (User theUser : usersWhoGivenRating) {
                finalList.add(theUser.getUsername());
            }
        } else {
            for (int i = 0; i < action.getNumber(); i++) {
                finalList.add(usersWhoGivenRating.get(i).getUsername());
            }
        }
        arrayResult.add(fileWriter.writeFile(action.getActionId(), "?",
                "Query result: " + finalList + ""));
    }
}
