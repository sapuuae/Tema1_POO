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

    public QueryUsersActive(ArrayList<User> userArray, ActionInputData action) {
        this.userArray = userArray;
        this.action = action;
    }

    public void showTheUsers(JSONArray arrayResult, Writer fileWriter) throws IOException {
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
