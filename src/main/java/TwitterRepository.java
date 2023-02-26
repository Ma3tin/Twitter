import jakarta.faces.context.FacesContext;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TwitterRepository {
    public void createTweet(String author, String content) {
        try (
                Connection connection = DriverManager.getConnection("jdbc:mariadb://localhost/twitter?user=root&password=");
                PreparedStatement preparedStatement = connection.prepareStatement("""
                        INSERT INTO tweets (author, content, likes, created_at, updated_at)
                        VALUES (?, ?, '0', now(), now())
                        """)
        ) {


            preparedStatement.setString(1, author);
            preparedStatement.setString(2, content);
            preparedStatement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    public void addLike(int id) {
        try (
                Connection connection = DriverManager.getConnection("jdbc:mariadb://localhost/twitter?user=root&password=");
                PreparedStatement preparedStatement = connection.prepareStatement("""
                                       UPDATE tweets
                                       SET likes = likes + 1
                                       WHERE tweet_id = ?;
                        """)
        ) {
            preparedStatement.setInt(1, id);
            preparedStatement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Tweet> getAll() {
        try (
                Connection connection = DriverManager.getConnection("jdbc:mariadb://localhost/twitter?user=root&password=");
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("""
                                        select t.tweet_id, t.author, t.content, t.likes, t.created_at, t.updated_at
                                        from tweets as t;
                        """)
        ) {
            ArrayList<Tweet> tweets = new ArrayList<>();

            while (resultSet.next()) {
                tweets.add(new Tweet(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getInt(4),
                        resultSet.getString(5),
                        resultSet.getString(6)
                ));
            }

            return tweets;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void deleteTweet(int id) {
        try (

                Connection connection = DriverManager.getConnection("jdbc:mariadb://localhost/twitter?user=root&password=");
                PreparedStatement preparedStatement = connection.prepareStatement("""
                        DELETE FROM tweets
                        WHERE tweet_id = ?;
                        """
                );


        ) {
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void editTweet(int id, String author, String content) {
        try (
                Connection connection = DriverManager.getConnection("jdbc:mariadb://localhost/twitter?user=root&password=");
                PreparedStatement preparedStatement = connection.prepareStatement("""
                                        UPDATE tweets
                                        SET author = ?, content = ?, updated_at = now()
                                        WHERE tweet_id = ?;
                        """)
        ) {

            preparedStatement.setString(1, author);
            preparedStatement.setString(2, content);
            preparedStatement.setInt(3, id);

            preparedStatement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Tweet getTweet(int id) {
        try (
                Connection connection = DriverManager.getConnection("jdbc:mariadb://localhost/twitter?user=root&password=");
                PreparedStatement preparedStatement = connection.prepareStatement("""
                                        select t.tweet_id, t.author, t.content, t.likes, t.created_at, t.updated_at
                                        from tweets as t
                                        where t.tweet_id = ?
                        """)
        ) {


            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                resultSet.next();
                return new Tweet(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getInt(4),
                        resultSet.getString(5),
                        resultSet.getString(6)
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
