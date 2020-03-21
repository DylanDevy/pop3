package database.manager;

import database.entity.UserDetails;
import database.Connector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDetailsManager {
    private UserDetailsManager() {}

    public int createUserDetails(UserDetails userDetails) throws SQLException {
        String query = "INSERT INTO user_details(email, hash) VALUES(?, ?)";

        Connection connection = Connector.getConnection();
        if (connection == null) {
            throw new SQLException();
        }

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, userDetails.getEmail());
        ps.setString(2, userDetails.getHash());

        int inserted = ps.executeUpdate();

        ps.close();
        connection.close();

        return inserted;
    }

    public static class Builder {
        public UserDetailsManager build() {
            return new UserDetailsManager();
        }
    }
}
