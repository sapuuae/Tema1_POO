package base;

/*
Used to modify an user in command class.
 */
public final class UserWrapper {
    private User interiorUser;

    public UserWrapper() { }

    public void setInteriorUser(final User interiorUser) {
        this.interiorUser = interiorUser;
    }

    public User getInteriorUser() {
        return interiorUser;
    }
}
