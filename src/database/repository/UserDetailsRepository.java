package database.repository;

import database.entity.UserDetails;
import database.Connector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDetailsRepository {
    private UserDetailsRepository() {}

    public UserDetails findByEmail(String email) throws SQLException {
        String query = "SELECT * FROM user_details WHERE email = ?";

        Connection connection = Connector.getConnection();
        if (connection == null) {
            throw new SQLException();
        }

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, email);
        ResultSet rs = ps.executeQuery();

        UserDetails userDetails = null;
        if (rs.next()) {
            userDetails = new UserDetails();
            userDetails.setId(rs.getInt("id"));
            userDetails.setEmail(rs.getString("email"));
            userDetails.setHash(rs.getString("hash"));
        }

        rs.close();
        ps.close();
        connection.close();

        return userDetails;
    }

    public static class Builder {
        public UserDetailsRepository build() {
            return new UserDetailsRepository();
        }
    }
}
