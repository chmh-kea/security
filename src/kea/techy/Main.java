package kea.techy;

import kea.techy.data.Sql;
import kea.techy.data.User;

import java.io.InputStream;
import java.io.PrintStream;
import java.sql.*;
import java.util.Scanner;

public class Main
{
    private static Main app;
    private PrintStream out;
    private InputStream in;
    private Scanner sc;
    private Sql sql;

    private User user;

    public Main()
    {
        out = System.out;
        in = System.in;
        sc = new Scanner(in);
        sql = new Sql();
    }

    private void commands()
    {
        while (true)
        {
            if (sc.hasNext())
            {
                switch (sc.next())
                {
                    case "login":
                        requestLogin();
                        break;
                    case "signup":
                        requestSignup();
                        break;
                    case "exit":
                        return;
                    default: out.println("Command not known");
                    break;
                }
            }

        }
    }

    private void requestLogin()
    {

        // While user has not been retreived
        while (user == null)
        {
            out.println("Please enter");

            String username = null;
            String password = null;

            // While username & password have not been entered
            while (username == null && password == null)
            {
                try
                {
                    out.print("username: ");
                    if(sc.hasNext())
                        username = sc.next();
                    out.print("password: ");
                    if(sc.hasNext())
                        password = sc.next();
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
            try
            {
                // Try getting User
                user = sql.getUser(username, password, true, true);
            }
            catch (SQLException ex)
            {
                ex.printStackTrace();
            }
            // User has NOT been gotten
            if (user == null)
                out.println("username and/or password was incorrect");
        }

        // User has been gotten
        out.println(user.toString());
    }

    private void requestSignup()
    {
        while (user == null)
        {
            out.println("Please enter");

            User newUser = new User();

            try
            {
                out.print("firstname: ");
                if (sc.hasNext())
                    newUser.setFirstname(sc.next());
                out.print("lastname: ");
                if (sc.hasNext())
                    newUser.setLastname(sc.next());
                out.print("username: ");
                if (sc.hasNext())
                    newUser.setUsername(sc.next());
                out.print("password: ");
                if (sc.hasNext())
                    newUser.setPassword(sc.next());
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }

            try
            {
                user = sql.createUser(newUser, true);
            }
            catch (SQLException ex)
            {
                ex.printStackTrace();
            }

            if (user == null)
                out.println("failed creating user");
        }

        out.println(user.toString());
    }


    public static void main(String[] args)
    {
        app = new Main();
        app.out.println("App has started");

        app.commands();

    }


//    private class Sql
//    {
//        private final String URL = "jdbc:mysql://%s/%s?autoReconnect=true&useSSL=false";
//        private String dbname = "tech";
//        private String host = "localhost:4200";
//        private String user = "root";
//        private String pass = "pass1234";
//        private String url;
//
//        //private Connection conn;
//        private Statement statement;
//        private PreparedStatement prepared;
//
//        public Sql()
//        {
//            url = String.format(URL, host, dbname);
//            try
//            {
//                Class.forName("com.mysql.jdbc.Driver");
//            } catch (ClassNotFoundException ex)
//            {
//                ex.printStackTrace();
//                //out.print(ex.getMessage());
//            }
//        }
//
//        private Connection newConn() throws SQLException
//        {
//            return DriverManager.getConnection(url, user, pass);
//        }
//
//        private ResultSet getUnsafe(Connection conn, String query) throws SQLException
//        {
//            statement = conn.createStatement();
//            return statement.executeQuery(query);
//        }
//
//        public ResultSet getSafe(Connection conn, String query) throws SQLException
//        {
//            PreparedStatement statement = conn.prepareStatement(query);
//            //statement.setsomething
//
//            return null;
//        }
//
//        public User getUser(String username, String password) throws SQLException
//        {
//            User user = null;
//            ResultSet result;
//
//            try(Connection conn = newConn())
//            {
//                result = getUnsafe(conn, String.format("SELECT * FROM tech.users WHERE username='%s' AND password='%s'", username, password));
//                if (result.next())
//                    user = new User(result);
//            }
//            return user;
//        }
//    }
//
//    public class User
//    {
//        private int id;
//        private String firstname;
//        private String lastname;
//        private String username;
//        private String password;
//
//        public User()
//        {
//
//        }
//
//        public User(ResultSet result) throws SQLException
//        {
//            int i = 1;
//            id = result.getInt(i++);
//            firstname = result.getString(i++);
//            lastname = result.getString(i++);
//            username = result.getString(i++);
//            //password = result.getString(i++);
//        }
//
//        public User(int id, String firstname, String lastname, String username, String password)
//        {
//            this.id = id;
//            this.firstname = firstname;
//            this.lastname = lastname;
//            this.username = username;
//            this.password = password;
//        }
//
//        public int getId()
//        {
//            return id;
//        }
//        public void setId(int id)
//        {
//            this.id = id;
//        }
//        public String getFirstname()
//        {
//            return firstname;
//        }
//        public void setFirstname(String firstname)
//        {
//            this.firstname = firstname;
//        }
//        public String getLastname()
//        {
//            return lastname;
//        }
//        public void setLastname(String lastname)
//        {
//            this.lastname = lastname;
//        }
//        public String getUsername()
//        {
//            return username;
//        }
//        public void setUsername(String username)
//        {
//            this.username = username;
//        }
//        public String getPassword()
//        {
//            return password;
//        }
//        public void setPassword(String password)
//        {
//            this.password = password;
//        }
//
//        @Override
//        public String toString()
//        {
//            return "User{" +
//                    "firstname='" + firstname + '\'' +
//                    ", lastname='" + lastname + '\'' +
//                    ", username='" + username + '\'' +
//                    '}';
//        }
//    }

    private class Crypto
    {
        //One way hash

        // w salt
    }

}
