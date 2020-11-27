package usage;

import base.Actor;
import fileio.ActionInputData;
import fileio.Writer;
import org.json.simple.JSONArray;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class QueryFilters {
    private final ArrayList<Actor> actorArrayList;
    private final List<String> filtersToCheck;

    public QueryFilters(final ArrayList<Actor> actorArrayList,
                        final List<String> filtersToCheck) {
        this.actorArrayList = actorArrayList;
        this.filtersToCheck = filtersToCheck;
    }

    /**
     * Give the actors which have these words in their description.
     * @param action used to get the id of the query
     * @param arrayResult the array used to write in file
     * @param fileWriter used for writing in file
     * @throws IOException check I/O
     */
    public final void checkTheFilters(final ActionInputData action, final JSONArray arrayResult,
                                      final Writer fileWriter) throws IOException {
        ArrayList<String> actorsWithWordsInDescription = new ArrayList<>();
        for (Actor myActor : actorArrayList) {
            String theCareer = myActor.getCareerDescription();
            HashMap<String, Boolean> theWords = new HashMap<>();
            boolean ok = true;
            String[] split = theCareer.split("\\s+|,|\\.|-");
            for (String s : split) {
                theWords.put(s.toUpperCase(), true);
            }
            for (String s : filtersToCheck) {
                if (!theWords.containsKey(s.toUpperCase())) {
                    ok = false;
                    break;
                }
            }
            if (ok) {
                String name = myActor.getName();
                actorsWithWordsInDescription.add(name);
            }
        }
        if (action.getSortType().equals("asc")) {
            Collections.sort(actorsWithWordsInDescription);
        } else {
            Collections.sort(actorsWithWordsInDescription);
            Collections.reverse(actorsWithWordsInDescription);
        }
        ArrayList<String> finalList = new ArrayList<>(actorsWithWordsInDescription);
        // noinspection unchecked
        arrayResult.add(fileWriter.writeFile(action.getActionId(), "?",
                "Query result: " + finalList + ""));
    }
}
