package maildrop.repository;

import database.DatabaseConnection;
import maildrop.entity.Message;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageRepository {
    public List<Message> findAllByToEmail(String toEmail) throws SQLException {
        String query = "SELECT * FROM messages WHERE to_email = ? AND NOT deleted ORDER BY date_sent ASC";

        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) {
            throw new SQLException();
        }

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, toEmail);
        ResultSet rs = ps.executeQuery();

        List<Message> messages = new ArrayList<>();
        Message message;
        Timestamp time;
        while (rs.next()) {
            message = new Message();
            message.setId(rs.getInt("id"));
            message.setUserIdReceived((Integer) rs.getObject("user_id_received"));
            time = rs.getTimestamp("date_received");
            message.setDateReceived(time != null ? time.toInstant() : null);
            message.setFromEmail(rs.getString("from_email"));
            message.setToEmail(rs.getString("to_email"));
            message.setSubject(rs.getString("subject"));
            message.setDateSent(rs.getTimestamp("date_sent").toInstant());
            message.setMimeVersion(rs.getString("mime_version"));
            message.setContentType(rs.getString("content_type"));
            message.setCharset(rs.getString("charset"));
            message.setContent(rs.getString("content"));
            message.setDeleted(rs.getBoolean("deleted"));
            time = rs.getTimestamp("date_deleted");
            message.setDateDeleted(time != null ? time.toInstant() : null);

            messages.add(message);
        }

        rs.close();
        ps.close();
        connection.close();

        return messages;
    }

    public static class Builder {
        public MessageRepository build() {
            return new MessageRepository();
        }
    }
}
