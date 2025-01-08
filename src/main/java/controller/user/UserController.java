package controller.user;


import db.DBConnection;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserController implements UserService {

    private static UserController instance;
    private UserController(){}

    public static UserController getInstance() {
        return instance==null?instance=new UserController():instance;

    }


    @Override
    public boolean addUser(User user) {
        String SQl = "INSERT INTO users (username, email, Password) VALUES (?,?,?)";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement(SQl);
            psTm.setObject(1,user.getUsername());
            psTm.setObject(2,user.getEmail());
            psTm.setObject(3,user.getPassword());

            return psTm.executeUpdate()>0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
