package actor;

/*
Used for query awards, to sort the list of actors.
 */
public final class ActorsAndAwards implements Comparable<ActorsAndAwards> {
    private final String name;
    private final Integer numberOfAwards;

    public ActorsAndAwards(final String name, final int numberOfAwards) {
        this.name = name;
        this.numberOfAwards = numberOfAwards;
    }

    public String getName() {
        return name;
    }

    public Integer getNumberOfAwards() {
        return numberOfAwards;
    }

    @Override
    public int compareTo(final ActorsAndAwards o) {
        return 0;
    }

}
