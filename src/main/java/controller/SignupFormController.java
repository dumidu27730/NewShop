package controller;

import controller.user.UserController;
import controller.user.UserService;
import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.User;
import org.jasypt.util.text.BasicTextEncryptor;
import sun.security.krb5.internal.crypto.DesCbcCrcEType;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SignupFormController {

    public PasswordField txtPassword1;
    @FXML
    private TextField txtEmail;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUserName;

    UserService service = UserController.getInstance();


    @FXML
    void btnLoginOnAction(ActionEvent event) throws SQLException {
        if (txtPassword.getText().equals(txtPassword.getText())){


            String Key="1234";

            BasicTextEncryptor basicTextEncryptor= new BasicTextEncryptor();
            basicTextEncryptor.setPassword(Key);

            Connection connection = DBConnection.getInstance().getConnection();
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM users WHERE email=" + "'" + txtEmail.getText() + "'");

            if (!resultSet.next()){
                User user = new User(txtUserName.getText(),txtEmail.getText(),basicTextEncryptor.encrypt(txtPassword.getText()));

                if (service.addUser(user)){
                    new Alert(Alert.AlertType.INFORMATION,"User Added !!").show();
                }else{
                    new Alert(Alert.AlertType.ERROR,"User Not Added !!").show();
                }

            }else{
                System.out.println(true);
            }

        }else {
            System.out.println(false);
        }
    }

}
