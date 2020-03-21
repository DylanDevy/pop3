package database.repository;

import database.entity.UserDetails;
import database.service.Connector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDetailsRepository {
    private UserDetailsRepository() {}

    public UserDetails findByName(String name) throws SQLException {
        String query = "SELECT * FROM user_details WHERE name = ?";

        Connection connection = Connector.getConnection();
        if (connection == null) {
            throw new SQLException();
        }

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, name);
        ResultSet rs = ps.executeQuery();

        UserDetails userDetails = null;
        if (rs.next()) {
            userDetails = new UserDetails();
            userDetails.setId(rs.getInt("id"));
            userDetails.setName(rs.getString("name"));
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
