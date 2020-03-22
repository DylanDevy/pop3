package user.repository;

import database.DatabaseConnection;
import user.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepository {
    private UserRepository() {}

    public User findByEmail(String email) throws SQLException {
        String query = "SELECT * FROM users WHERE email = ?";

        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) {
            throw new SQLException();
        }

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, email);
        ResultSet rs = ps.executeQuery();

        User user = null;
        if (rs.next()) {
            user = new User();
            user.setId(rs.getInt("id"));
            user.setEmail(rs.getString("email"));
            user.setHash(rs.getString("hash"));
        }

        rs.close();
        ps.close();
        connection.close();

        return user;
    }

    public static class Builder {
        public UserRepository build() {
            return new UserRepository();
        }
    }
}
