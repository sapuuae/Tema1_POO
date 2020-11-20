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

    public final void checkTheFilters(final ActionInputData action, final JSONArray arrayResult,
                                      Writer fileWriter) throws IOException {
        ArrayList<String> namesOfActors = new ArrayList<>();
        for (Actor myActor : actorArrayList) {
            String theCareer = myActor.getCareerDescription();
            HashMap<String, Boolean> theWords = new HashMap<>();
            boolean ok = true;
            String[] splitted = theCareer.split("\\s+|,|\\.|\\-");
            for (String s : splitted) {
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
                namesOfActors.add(name);
            }
        }
        if (action.getSortType().equals("asc")) {
            Collections.sort(namesOfActors);
        } else {
            Collections.sort(namesOfActors);
            Collections.reverse(namesOfActors);
        }
        ArrayList<String> finalList = new ArrayList<>();
        for (int i = 0; i < namesOfActors.size(); i++) {
            finalList.add(namesOfActors.get(i));
        }

        arrayResult.add(fileWriter.writeFile(action.getActionId(), "?",
                "Query result: " + finalList + ""));
    }
}
