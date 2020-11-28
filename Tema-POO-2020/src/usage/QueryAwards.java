package usage;

import base.ActorsAndAwards;
import actor.ActorsAwards;
import base.Actor;
import fileio.ActionInputData;
import fileio.Writer;
import org.json.simple.JSONArray;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class QueryAwards {
    private final ArrayList<Actor> actorArrayList;
    private final List<String> awardsForChecking;

    public QueryAwards(final ArrayList<Actor> actorArrayList,
                       final List<String> awardsForChecking) {
        this.actorArrayList = actorArrayList;
        this.awardsForChecking = awardsForChecking;
    }

    /**
     * Make the sum of the awards from an actor.
     * @param theAwards map to get the value for every award.
     * @return return the sum of awards for actors.
     */
    public int calculateAward(final Map<ActorsAwards, Integer> theAwards) {
        final int[] sum = {0};
        theAwards.forEach((ActorsAwards x, Integer y) -> sum[0] += y);
        return sum[0];
    }


    /**
     * Checks every actor to see if he has every award from the input.
     * Add them in a list and then sort it by the number of awards and name.
     * @param action used for sort, id and number of actors
     * @param arrayResult the array used to write in file
     * @param fileWriter used for writing in file
     * @throws IOException check I/O
     */
    public void makeTheSort(final ActionInputData action, final JSONArray arrayResult,
                                  final Writer fileWriter) throws IOException {
        /*
          Create a list just for actors which have all awards, then sort it.
         */
        ArrayList<ActorsAndAwards> lastActors = new ArrayList<>();
        for (Actor myActor : actorArrayList) {
            final int[] ok = {0};
            Map<ActorsAwards, Integer> theAwards = myActor.getAwards();
            for (String s : awardsForChecking) {
                theAwards.forEach((ActorsAwards x, Integer y) -> {
                    if (s.equalsIgnoreCase(String.valueOf(x))) {
                        ok[0]++;
                    }
                });
            }
            if (ok[0] == awardsForChecking.size()) {
                int sum = calculateAward(theAwards);
                ActorsAndAwards toAdd = new ActorsAndAwards(myActor.getName(), sum);
                lastActors.add(toAdd);
            }
        }
        if (action.getSortType().equals("asc")) {
            lastActors.sort((o1, o2) -> {
                int c;
                c = o1.getNumberOfAwards().compareTo(o2.getNumberOfAwards());
                if (c == 0) {
                    c = o1.getName().compareTo(o2.getName());
                }
                return c;
            });
        } else {
            lastActors.sort((o1, o2) -> {
                int c;
                c = o2.getNumberOfAwards().compareTo(o1.getNumberOfAwards());
                if (c == 0) {
                    c = o2.getName().compareTo(o1.getName());
                }
                return c;
            });
        }
        ArrayList<String> finalList = new ArrayList<>();
        if (lastActors.size() < action.getNumber()) {
            for (ActorsAndAwards lastActor : lastActors) {
                finalList.add(lastActor.getName());
            }
        } else {
            for (int i = 0; i < action.getNumber(); i++) {
                finalList.add(lastActors.get(i).getName());
            }
        }
        // noinspection unchecked
        arrayResult.add(fileWriter.writeFile(action.getActionId(), "?",
                "Query result: " + finalList + ""));
    }
}
