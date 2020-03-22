package user.service;

import database.DatabaseConnection;
import user.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserManager {
    private UserManager() {}

    public int createUser(User user) throws SQLException {
        String query = "INSERT INTO users(email, hash) VALUES(?, ?)";

        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) {
            throw new SQLException();
        }

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, user.getEmail());
        ps.setString(2, user.getHash());

        int inserted = ps.executeUpdate();

        ps.close();
        connection.close();

        return inserted;
    }

    public static class Builder {
        public UserManager build() {
            return new UserManager();
        }
    }
}
