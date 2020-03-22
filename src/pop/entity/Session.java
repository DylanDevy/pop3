package pop.entity;

import maildrop.entity.Maildrop;
import pop.enumeration.State;
import user.entity.User;

public class Session {
    private State state;
    private User user;
    private Maildrop maildrop;

    public Session() {
        this.state = State.AUTHORIZATION;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Maildrop getMaildrop() {
        return maildrop;
    }

    public void setMaildrop(Maildrop maildrop) {
        this.maildrop = maildrop;
    }
}
