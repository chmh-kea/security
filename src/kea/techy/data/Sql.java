package kea.techy.data;

import kea.techy.Crypto;

import java.sql.*;
import java.util.Base64;

public class Sql
{
    private final String URL = "jdbc:mysql://%s/%s?autoReconnect=true&useSSL=false";
    private String dbname = "tech";
    private String host = "localhost:4200"; // Change port = localhost:xxxx
    private String user = "root"; // Change
    private String pass = "pass1234"; // Change
    private String url;

    //private Connection conn;
    private Statement statement;
    private PreparedStatement prepared;

    public Sql()
    {
        url = String.format(URL, host, dbname);
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex)
        {
            ex.printStackTrace();
            //out.print(ex.getMessage());
        }
    }

    private Connection getConn() throws SQLException
    {
        return DriverManager.getConnection(url, user, pass);
    }

//    private ResultSet getUnsafe(Connection conn, String query) throws SQLException
//    {
//        statement = conn.createStatement();
//        return statement.executeQuery(query);
//    }

//    private ResultSet getSafe(Connection conn, String query) throws SQLException
//    {
//        PreparedStatement statement = conn.prepareStatement();
//
//        //statement.setsomething
//
//        return null;
//    }

    public User getUser(String username, String password, boolean safe, boolean hash) throws SQLException
    {
        User user = null;
        ResultSet result = null;
        //String query = "SELECT * FROM tech.users WHERE username='%s' AND password='%s'";

        try(Connection conn = getConn())
        {
            if (safe && !hash)
            {
                PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM tech.users WHERE username=? AND password=?");
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                result = preparedStatement.executeQuery();
            }
            else if (safe && hash)
            {
                byte[] salt = null;

                PreparedStatement getSalt = conn.prepareStatement("SELECT salt FROM tech.users WHERE username=?");
                getSalt.setString(1,username);
                result = getSalt.executeQuery();

                if (result.next())
                    salt = Base64.getDecoder().decode(result.getString(1));

                if (salt != null)
                {
                    PreparedStatement getUser = conn.prepareStatement("SELECT * FROM tech.users WHERE username=? AND password=?");
                    getUser.setString(1,username);
                    getUser.setString(2,Base64.getEncoder().encodeToString(Crypto.hashPassword(password.toCharArray(), salt)));
                    result = getUser.executeQuery();
                }
                else
                    result = null;
                    //throw new SQLException("No user or salt found");
            }
            else
            {
                Statement statement = conn.createStatement();
                String q = String.format("SELECT * FROM tech.users WHERE username='%s' AND password='%s'", username, password);
                result = statement.executeQuery(q);
                //result = getUnsafe(conn, String.format(query, username, password));
            }

            if (result.next())
                user = new User(result);
        }
        return user;
    }

    public User createUser(User user, boolean hash) throws SQLException
    {
        ResultSet resultSet = null;

        String password = user.getPassword();
        byte[] salt = null;
        String query = "INSERT INTO tech.users (firstname, lastname, username, password, salt) VALUES (?, ?, ?,";


        if (hash)
        {
            salt = Crypto.generateSalt();
            password = Base64.getEncoder().encodeToString(Crypto.hashPassword(password.toCharArray(), salt));

            query += " ?, ?)";
        }
        else
            query += "?, null)";

        try(Connection conn = getConn())
        {
            // Insert query and define to get generated keys
            PreparedStatement preparedStatement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1,user.getFirstname());
            preparedStatement.setString(2,user.getLastname());
            preparedStatement.setString(3,user.getUsername());
            preparedStatement.setString(4,password);
            if (hash)
                preparedStatement.setString(5, Base64.getEncoder().encodeToString(salt));

            if (0 < preparedStatement.executeUpdate());
                resultSet = preparedStatement.getGeneratedKeys(); // Get the generated id

            if (resultSet.next())
            {
                user.setId(resultSet.getInt(1));
                user.setPassword("");
                return user;
            }
        }
        return null;
    }
}
