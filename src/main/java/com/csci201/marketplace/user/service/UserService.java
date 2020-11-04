package com.csci201.marketplace.user.service;

import com.csci201.marketplace.user.dao.UserDAO;
import com.csci201.marketplace.user.dao.UserMapper;
import com.csci201.marketplace.user.model.User;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class UserService implements UserDAO { //implements DAO, interacts with USER
//    public static UserDAO instance = null;
    public static List<User> users = new ArrayList<User>();
    public static DataSource dataSource = null;
    public static JdbcTemplate jdbcTemplateObject = null;


    private int getInfo(User user)
    {
        String sql =
                "INSERT INTO Users (user_id, " +
                        "    name, " +
                        "    email, " +
                        "    password) " +
                        "VALUES (?, ?, ?, ?)";
        Object[] params = {user.getUserID(), user.getName(), user.getEmail(), user.getPassword()};
        int[] types = new int[] {
                Types.INTEGER,
                Types.VARCHAR,
                Types.VARCHAR,
                Types.VARCHAR
        };
        int row = jdbcTemplateObject.update(sql, params, types);
        return row;
    }
    //@Autowired
    public static void setDataSource(DataSource ds) {
		dataSource = ds;
		jdbcTemplateObject = new JdbcTemplate(ds);
    }

    public static List<User> listAll() {
        return new ArrayList<User>(users);
    }

    public void getAll() {
        String SQL = "SELECT * FROM Users";
        List<User> li = jdbcTemplateObject.query(SQL, new UserMapper());
    }

    @Override
    public User get(int id)
    {
        for(User user : users)
            if(user.getUserID() == id) return user;
        String SQL = "SELECT * FROM Users WHERE User.user_id = " + id;
        List<User> li = jdbcTemplateObject.query(SQL, new UserMapper());
        return li.get(0);
    }

    @Override //login functionality
    public User get(int id, String password) {
        for(User user : users)
            if(user.getUserID() == id && password.compareTo(user.getPassword()) == 0) return user;
        String SQL = "SELECT * FROM Users WHERE User.user_id = " + id + "User.password=" + password;
        List<User> li = jdbcTemplateObject.query(SQL, new UserMapper());
        return li.get(0);
    }

    @Override
    public boolean delete(int id)
    {
        for(User user : users) {
            if(user.getUserID() == id)
            {
                users.remove(user);
                String deleteQuery = "DELETE FROM Users WHERE id = ?";
                jdbcTemplateObject.update(deleteQuery, id);
                return true;
            }
        }
        return false;
    }

    @Override
    public int update(User user) {
        int id = user.getUserID();
        int counter = 0;
        for (User us : users) {
            if (us.getUserID() == id) {
                users.set(counter, user);
                int row = getInfo(user);
                return row;
            }
            counter++;
        }
        return 0;
    }

    //sign up functionality
    @Override
    public int add(User user) {
        for(User us : users)
            if(user.getUserID() == us.getUserID()) return 0;
        users.add(user);
        int row = getInfo(user);
        return row;
    }
}