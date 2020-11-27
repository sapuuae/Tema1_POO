package usage;

import base.Actor;
import base.ActorsAndRating;
import base.Video;
import fileio.ActionInputData;
import fileio.Writer;
import org.json.simple.JSONArray;

import java.io.IOException;
import java.util.ArrayList;

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

    /**
     * Get the video from the movie/shows list, using the title.
     * @param list list of movies/shows
     * @param videoTitle title of video
     * @return specific video
     */
    public Video getVideo(final ArrayList<Video> list, final String videoTitle) {
        for (Video video : list) {
            if (video.getTitle().equals(videoTitle)) {
                return video;
            }
        }
        return null;
    }

    /**
     * Calculate the ratings for actors, then sort them.
     * @param numberOfActors actors to write in arrayResult
     * @param arrayResult the array used to write in file
     * @param fileWriter used for writing in file
     * @param action  used to get the id
     * @throws IOException check I/O
     */
    public void makeTheAverage(final int numberOfActors, final JSONArray arrayResult,
                               final Writer fileWriter, final ActionInputData action)
            throws IOException {
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
                    Search for it in the shows list, if it isn't found in the movie list.
                     */
                    theVideo = getVideo(showArrayList, myFilm);
                }
                if (theVideo != null) {
                    if (theVideo.getNumberOfRatings() != 0) {
                        /*
                        Increment the number of plays of actors in videos.
                         */
                        playsInVideos++;
                        finalRatingForActor += theVideo.getRating();
                    }
                }
            }
            if (playsInVideos != 0) {
                finalRatingForActor /= playsInVideos;
                ActorsAndRating myActorWithRating = new ActorsAndRating(
                        myActor.getName(), finalRatingForActor);
                theActorsWithRatings.add(myActorWithRating);
            }
        }
        if (action.getSortType().equals("asc")) {
            /*
            Sort ascending.
             */
            theActorsWithRatings.sort((o1, o2) -> {
                int c;
                c = o1.getRatingOfActor().compareTo(o2.getRatingOfActor());
                if (c == 0) {
                    c = o1.getActorName().compareTo(o2.getActorName());
                }
                return c;
            });
        } else {
            /*
            Sort descending.
             */
            theActorsWithRatings.sort((o1, o2) -> {
                int c;
                c = o2.getRatingOfActor().compareTo(o1.getRatingOfActor());
                if (c == 0) {
                    c = o2.getActorName().compareTo(o1.getActorName());
                }
                return c;
            });
        }
        ArrayList<String> finalList = new ArrayList<>();
        if (theActorsWithRatings.size() < numberOfActors) {
            theActorsWithRatings.forEach(list -> finalList.add(list.getActorName()));
        } else {
            for (int i = 0; i < numberOfActors; i++) {
                finalList.add(theActorsWithRatings.get(i).getActorName());
            }
        }
        //noinspection unchecked
        arrayResult.add(fileWriter.writeFile(action.getActionId(), "?",
                "Query result: " + finalList + ""));
    }
}
