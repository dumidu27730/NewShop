package controller;

import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;
import org.jasypt.util.text.BasicTextEncryptor;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginFormController {

    @FXML
    private TextField txtEmail;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUserName;

    @FXML
    void btnForgotPasswordOnAction(ActionEvent event) {

    }

    @FXML
    void btnLoginOnAction(ActionEvent event) throws SQLException {

        String Key="1234";

        BasicTextEncryptor basicTextEncryptor= new BasicTextEncryptor();
        basicTextEncryptor.setPassword(Key);

        String SQL = "SELECT * FROM users WHERE email=" + "'" + txtEmail.getText() + "'";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            ResultSet resultSet = connection.createStatement().executeQuery(SQL);
            resultSet.next();
            User user = new User(resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)
            );
            System.out.println(user);
            if (user != null) {
                if (basicTextEncryptor.encrypt(user.getPassword()).equals(txtPassword.getText())) {
                    System.out.println("Login!");
                    Stage stage = new Stage();
                    stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/dash_form.fxml"))));
                    stage.show();
                } else {
                    new Alert(Alert.AlertType.ERROR,"Check your Password").show();
                    System.out.println("Check your password");
                }
            } else {
                new Alert(Alert.AlertType.ERROR,"User Not Found").show();
                System.out.println("user Not found!");
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void btnSignupOnAction(ActionEvent event) {
        Stage stage = new Stage();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/signup_form.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.show();

    }

}
