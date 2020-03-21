package database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Init {
    public static void main(String[] args) {
        Connection connection = Connector.getConnection();
        if (connection == null) {
            System.out.println("Cannot create connection");
            System.exit(-1);
        }

        int queriesExecuted = 0;
        Statement statement;
        try {
            for (String query : getTableQueries()) {
                statement = connection.createStatement();
                statement.executeUpdate(query);
                System.out.println("Executed:");
                System.out.println(query);
                queriesExecuted++;
                statement.close();
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("Total queries executed: " + queriesExecuted);
    }

    private static String[] getTableQueries() {
        return new String[]{
                "CREATE TABLE IF NOT EXISTS user_details (" +
                        "id INT AUTO_INCREMENT," +
                        "email VARCHAR(100) NOT NULL," +
                        "hash VARCHAR(150) NOT NULL," +
                        "PRIMARY KEY (id)," +
                        "UNIQUE INDEX idx_user_details_email(email)" +
                        ");"
                ,
                "CREATE TABLE IF NOT EXISTS messages (" +
                        "id INT AUTO_INCREMENT," +
                        "received_by_user_id INT," +
                        "date_received DATETIME," +
                        "from_email VARCHAR(100) NOT NULL," +
                        "to_email VARCHAR(100) NOT NULL," +
                        "subject VARCHAR(100) NOT NULL," +
                        "date_sent DATETIME NOT NULL," +
                        "mime_version VARCHAR(10) NOT NULL," +
                        "content_type VARCHAR(30) NOT NULL," +
                        "charset VARCHAR(30) NOT NULL," +
                        "content TEXT," +
                        "deleted BIT," +
                        "PRIMARY KEY (id)," +
                        "INDEX idx_messages_to_email(to_email)," +
                        "FOREIGN KEY (received_by_user_id) REFERENCES user_details(id)" +
                        ");"
                ,
        };
    }
}
