package base;

import fileio.Input;
import fileio.UserInputData;

import java.util.ArrayList;

public class DataList {
    private final Input input;

    private ArrayList<Users> usersArrayList;
    private ArrayList<Movies> moviesArrayList;
    private ArrayList<Shows> showsArrayList;

    public DataList(final Input input) {
        this.input = input;
    }
    public final void initUsersArray(final Input input) {
        /*
        Convert data from input variable to
        users type variable
        */
        usersArrayList = new ArrayList<>();
        for (int i = 0; i < input.getUsers().size(); i++) {
            UserInputData inputData = input.getUsers().get(i);
            Users newUser = new Users(inputData.getSubscriptionType(),
                    inputData.getFavoriteMovies(), inputData.getUsername(),
                    inputData.getHistory());
            usersArrayList.add(newUser);
        }

        for (int i = 0; i < input.getMovies().size(); i++) {
            moviesArrayList = new ArrayList<>();
        }
    }

}
