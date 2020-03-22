package database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Init {
    public static void main(String[] args) {
        Connection connection = DatabaseConnection.getConnection();
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
                "CREATE TABLE IF NOT EXISTS users (" +
                        "id INT AUTO_INCREMENT," +
                        "email VARCHAR(100) NOT NULL," +
                        "hash VARCHAR(150) NOT NULL," +
                        "PRIMARY KEY (id)," +
                        "UNIQUE INDEX idx_users_email(email)" +
                        ");"
                ,
                "CREATE TABLE IF NOT EXISTS messages (" +
                        "id INT AUTO_INCREMENT," +
                        "user_id_received INT," +
                        "date_received TIMESTAMP," +
                        "from_email VARCHAR(100) NOT NULL," +
                        "to_email VARCHAR(100) NOT NULL," +
                        "subject VARCHAR(100) NOT NULL," +
                        "date_sent TIMESTAMP NOT NULL," +
                        "mime_version VARCHAR(10) NOT NULL," +
                        "content_type VARCHAR(30) NOT NULL," +
                        "charset VARCHAR(30) NOT NULL," +
                        "content TEXT," +
                        "deleted BIT DEFAULT b'0'," +
                        "date_deleted TIMESTAMP," +
                        "PRIMARY KEY (id)," +
                        "INDEX idx_messages_to_email(to_email)," +
                        "INDEX idx_messages_date_sent(date_sent)," +
                        "FOREIGN KEY (user_id_received) REFERENCES users(id)" +
                        ");"
                ,
                "INSERT INTO users (" +
                        "email," +
                        "hash" +
                        ") VALUES (" +
                        "'test@test.com'," +
                        "'N2uLS0K/lTPcBzDZ5q6wMMi0XUbZ2/n1eIuHJGx3odc=:iuEz46JGKUU5BWruwplYn8aKEq9wwLkef7fVUZqDr7c='" +
                        ");"
                ,
                "INSERT INTO users (" +
                        "email," +
                        "hash" +
                        ") VALUES (" +
                        "'user@test.com'," +
                        "'Y7VRag+iQXuc3ouysmeTRICCuZLBlx1jDCZFRsZC3Fo=:KrsO6XDVD7Bc3FYqh/xCmma42PoPYxTNuBd6xVxUY0c='" +
                        ");"
                ,
                "INSERT INTO users (" +
                        "email," +
                        "hash" +
                        ") VALUES (" +
                        "'email@test.com'," +
                        "'AYC6aKMfrmYsVCuBbTiS33EoHp/FD/Lo1uTMXUR8vrk=:JEdLO01jw58S0Dh3UjjRE2maIASBNXcaqEYETkCaxmQ='" +
                        ");"
                ,
                "INSERT INTO messages (" +
                        "from_email," +
                        "to_email," +
                        "subject," +
                        "date_sent," +
                        "mime_version," +
                        "content_type," +
                        "charset," +
                        "content" +
                        ") VALUES (" +
                        "'person123@email.com'," +
                        "'test@test.com'," +
                        "'A question'," +
                        "'2020-03-21 23:00:00'," +
                        "'1.0'," +
                        "'text/plain'," +
                        "'iso-8859-1'," +
                        "'Did you pick up the mail?'" +
                        ");"
                ,
                "INSERT INTO messages (" +
                        "from_email," +
                        "to_email," +
                        "subject," +
                        "date_sent," +
                        "mime_version," +
                        "content_type," +
                        "charset," +
                        "content" +
                        ") VALUES (" +
                        "'anotheremail@email.com'," +
                        "'test@test.com'," +
                        "'Placeholder subject text'," +
                        "'2020-03-21 23:20:00'," +
                        "'1.0'," +
                        "'text/plain'," +
                        "'utf-8'," +
                        "'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut " +
                        "labore et dolore magna aliqua.\nUt enim ad minim veniam, quis nostrud exercitation ullamco " +
                        "laboris nisi ut aliquip ex ea commodo consequat.\nDuis aute irure dolor in reprehenderit in " +
                        "voluptate velit esse cillum dolore eu fugiat nulla pariatur. \nExcepteur sint occaecat " +
                        "cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.'" +
                        ");"
                ,
                "INSERT INTO messages (" +
                        "user_id_received," +
                        "date_received," +
                        "from_email," +
                        "to_email," +
                        "subject," +
                        "date_sent," +
                        "mime_version," +
                        "content_type," +
                        "charset," +
                        "content," +
                        "deleted," +
                        "date_deleted" +
                        ") VALUES (" +
                        "1," +
                        "'2020-03-20 18:54:29'," +
                        "'differentemain@domain.com'," +
                        "'test@test.com'," +
                        "'Placeholder subject text'," +
                        "'2020-03-20 18:10:50'," +
                        "'1.0'," +
                        "'text/plain'," +
                        "'utf-8'," +
                        "'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut " +
                        "labore et dolore magna aliqua.\nUt enim ad minim veniam, quis nostrud exercitation ullamco " +
                        "laboris nisi ut aliquip ex ea commodo consequat.\nDuis aute irure dolor in reprehenderit'," +
                        "1," +
                        "'2020-03-20 19:35:00'" +
                        ");"
                ,
                "INSERT INTO messages (" +
                        "from_email," +
                        "to_email," +
                        "subject," +
                        "date_sent," +
                        "mime_version," +
                        "content_type," +
                        "charset," +
                        "content" +
                        ") VALUES (" +
                        "'testing@email.com'," +
                        "'test@test.com'," +
                        "'Subject'," +
                        "'2020-03-22 10:00:00'," +
                        "'1.0'," +
                        "'text/plain'," +
                        "'utf-8'," +
                        "'Lorem ipsum dolor sit amet, consectetur adipiscing elit,\n sed do eiusmod tempor incididunt ut' " +
                        ");"
                ,
                "INSERT INTO messages (" +
                        "from_email," +
                        "to_email," +
                        "subject," +
                        "date_sent," +
                        "mime_version," +
                        "content_type," +
                        "charset," +
                        "content" +
                        ") VALUES (" +
                        "'testing@email.com'," +
                        "'user@test.com'," +
                        "'Subject123'," +
                        "'2020-03-22 10:00:42'," +
                        "'1.0'," +
                        "'text/plain'," +
                        "'utf-8'," +
                        "'Lorem ipsum dolor sit amet, consectetur adipiscing elit,\n sed do eiusmod'" +
                        ");"
                ,
        };
    }
}
