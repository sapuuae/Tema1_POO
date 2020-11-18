package base;

import java.util.ArrayList;

public final class ActorsAndRating implements Comparable<ActorsAndRating> {
    private final String actorName;
    private final ArrayList<String> filmography;
    private Double ratingOfActor;

    public ActorsAndRating(String actorName, ArrayList<String> filmography,
                           final double ratingOfActor) {
        this.actorName = actorName;
        this.filmography = filmography;
        this.ratingOfActor = ratingOfActor;
    }

    public final Double getRatingOfActor() {
        return ratingOfActor;
    }

    public final void setRatingOfActor(Double ratingOfActor) {
        this.ratingOfActor = ratingOfActor;
    }

//    public final void getTheNewRating(Double rating) {
//        double newRating = this.getRatingOfActor() * this.getNumberOfPlays() + rating;
//        this.setNumberOfPlays(this.getNumberOfPlays() + 1);
//        newRating /= this.getNumberOfPlays();
//        this.setRatingOfActor(newRating);
//    }

    @Override
    public String toString() {
        return "ActorsAndRating{" +
                "actorName='" + actorName + '\'' +
                ", filmography=" + filmography +
                ", ratingOfActor=" + ratingOfActor +
                '}';
    }

    public String getActorName() {
        return actorName;
    }

    @Override
    public int compareTo(ActorsAndRating o) {
        if (getRatingOfActor() == null || o.getRatingOfActor() == null) {
            return  0;
        }
        int c;
        c = getRatingOfActor().compareTo(o.getRatingOfActor());
        if (c == 0) {
            c = getActorName().compareTo(o.getActorName());
        }
        return  c;
    }
}
