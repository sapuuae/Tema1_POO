package base;

import java.util.ArrayList;

public final class ActorsAndRating {
    private final String actorName;
    private final ArrayList<String> filmography;
    private Double ratingOfActor;

    public ActorsAndRating(final String actorName, final ArrayList<String> filmography,
                           final double ratingOfActor) {
        this.actorName = actorName;
        this.filmography = filmography;
        this.ratingOfActor = ratingOfActor;
    }

    public Double getRatingOfActor() {
        return ratingOfActor;
    }

    public void setRatingOfActor(final Double ratingOfActor) {
        this.ratingOfActor = ratingOfActor;
    }

    public String getActorName() {
        return actorName;
    }

}
