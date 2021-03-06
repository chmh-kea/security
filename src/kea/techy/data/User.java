package kea.techy.data;

import java.sql.ResultSet;
import java.sql.SQLException;

public class User
{
    private int id;
    private String firstname;
    private String lastname;
    private String username;
    private String password;

    public User()
    {

    }

    public User(ResultSet result) throws SQLException
    {
        int i = 1;
        id = result.getInt(i++);
        firstname = result.getString(i++);
        lastname = result.getString(i++);
        username = result.getString(i++);
        //password = result.getString(i++);
    }

    public User(int id, String firstname, String lastname, String username, String password)
    {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
    }

    public int getId()
    {
        return id;
    }
    public void setId(int id)
    {
        this.id = id;
    }
    public String getFirstname()
    {
        return firstname;
    }
    public void setFirstname(String firstname)
    {
        this.firstname = firstname;
    }
    public String getLastname()
    {
        return lastname;
    }
    public void setLastname(String lastname)
    {
        this.lastname = lastname;
    }
    public String getUsername()
    {
        return username;
    }
    public void setUsername(String username)
    {
        this.username = username;
    }
    public String getPassword()
    {
        return password;
    }
    public void setPassword(String password)
    {
        this.password = password;
    }

    @Override
    public String toString()
    {
        return "User{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
