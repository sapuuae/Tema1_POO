package base;

import java.util.ArrayList;

public final class ActorsAndRating {
    private final String actorName;
    private final Double ratingOfActor;

    public ActorsAndRating(final String actorName, final double ratingOfActor) {
        this.actorName = actorName;
        this.ratingOfActor = ratingOfActor;
    }

    /*
    Keep the actor' name and rating for sorting.
     */
    public Double getRatingOfActor() {
        return ratingOfActor;
    }

    public String getActorName() {
        return actorName;
    }

}
