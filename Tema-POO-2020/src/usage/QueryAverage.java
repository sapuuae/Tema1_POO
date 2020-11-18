package usage;

import base.*;

import fileio.ActionInputData;
import fileio.Writer;
import org.json.simple.JSONArray;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public final class QueryAverage {
    private final ArrayList<Video> movieArrayList;
    private final ArrayList<Video> showArrayList;
    private final ArrayList<Actor> actorArrayList;

    public QueryAverage(final ArrayList<Video> movieArrayList,
                        final ArrayList<Video> showArrayList,
                        final ArrayList<Actor> actorArrayList) {
        this.movieArrayList = movieArrayList;
        this.showArrayList = showArrayList;
        this.actorArrayList = actorArrayList;
    }

    public final Video getVideo(final ArrayList<Video> list, final String videoTitle) {
        for (Video video : list) {
            if (video.getTitle().equals(videoTitle)) {
                return video;
            }
        }
        return null;
    }

    public final void makeTheAverage(int numberOfActors, JSONArray arrayResult,
                                     Writer fileWriter, ActionInputData action) throws IOException {
        ArrayList<ActorsAndRating> theActorsWithRatings = new ArrayList<>();
        for (Actor myActor : this.actorArrayList) {
            double finalRatingForActor = 0;
            int playsInVideos = 0;
            ArrayList<String> theFilmography = myActor.getFilmography();
            for (String myFilm : theFilmography) {
                Video theVideo;
                theVideo = getVideo(movieArrayList, myFilm);
                if (theVideo == null) {
                    /*
                    Il caut inca o data in lista de seriale, daca la filme nu s-a gasit.
                     */
                    theVideo = getVideo(showArrayList, myFilm);
                }
                if (theVideo != null) {
//                    System.out.println("TITLU: " + theVideo.getTitle() + " RATING: " + theVideo.getNumberOfRatings());
                    if (theVideo.getNumberOfRatings() != 0) {
                        playsInVideos++;
                        finalRatingForActor += theVideo.getRating();
                    }
//                    System.out.println("PLAYS: " + playsInVideos);
                }
            }
            if (playsInVideos != 0) {
                finalRatingForActor /= playsInVideos;
                ActorsAndRating myActorWithRating = new ActorsAndRating(myActor.getName(),
                        theFilmography, finalRatingForActor);
                theActorsWithRatings.add(myActorWithRating);
            }
        }
        Collections.sort(theActorsWithRatings);
        ArrayList<String> finalList = new ArrayList<>();
        System.out.print("NUMAR ACTORI IN LISTA " + theActorsWithRatings.size() + " ");
        System.out.println("NUMAR ACTORI DE LA COMANDA: " + numberOfActors);
        if (numberOfActors > theActorsWithRatings.size()) {
            for (int i = 0; i < theActorsWithRatings.size(); i++) {
                finalList.add(theActorsWithRatings.get(i).getActorName());
            }
        } else {
        for (int i = 0; i < numberOfActors; i++) {
            finalList.add(theActorsWithRatings.get(i).getActorName());
        }
        }
      System.out.println(finalList);
        arrayResult.add(fileWriter.writeFile(action.getActionId(), "?",
                "Query result: " + finalList + ""));
    }
}
