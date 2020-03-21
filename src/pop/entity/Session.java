package pop.entity;

import database.entity.UserDetails;
import pop.enumeration.State;

public class Session {
    private State state;
    private UserDetails userDetails;

    public Session() {
        this.state = State.AUTHORIZATION;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }
}
