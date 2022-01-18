package to.projekt.to2021projekt.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import org.hibernate.Session;
import org.hibernate.query.Query;
import to.projekt.to2021projekt.FXutils.SceneProvider;
import to.projekt.to2021projekt.FXutils.StageProvider;
import to.projekt.to2021projekt.hibernate.SessionProvider;
import to.projekt.to2021projekt.models.User;
import to.projekt.to2021projekt.validators.LoginValidator;
import to.projekt.to2021projekt.validators.Validator;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class LoginController {

    private User loggedUser;

    @FXML
    private Hyperlink linkToRegister;

    @FXML
    private Button loginButton;

    @FXML
    private Label passwordError;

    @FXML
    private Label loginLabel;

    @FXML
    private TextField loginTextField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label passwordLabel;

    @FXML
    private Label loginErrorLabel;

    @FXML
    public void redirectToRegistration(MouseEvent event) {
        SceneProvider.getInstance().loadScene("view/register-view.fxml", "stylesheets/register-panel.css");
        StageProvider.getInstance().getStage().setScene(SceneProvider.getInstance().getScene());
    }

    private void showDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Login Successful");
        alert.setHeaderText(null);
        alert.setContentText("Login Succeed");
        ButtonType buttonTypeOK = new ButtonType("OK");
        alert.getButtonTypes().setAll(buttonTypeOK);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeOK) {
            SessionProvider.setLoggedUser(this.loggedUser);
            SceneProvider.getInstance().loadScene("view/start-view.fxml", "stylesheets/menu-panel.css");
            StageProvider.getInstance().getStage().setScene(SceneProvider.getInstance().getScene());
        }
    }

    private void logUser(){

    }

    @FXML
    public void login(MouseEvent event) {
        this.cleanErrors();
        Validator loginValidator = new LoginValidator();
        boolean result = loginValidator.validate(SceneProvider.getInstance().getScene().getRoot());
        this.showErrors(loginValidator.getErrors());
        if(result){
            Session session = SessionProvider.getSession();
            String hql = "FROM User U WHERE U.email = :user_email";
            Query query = session.createQuery(hql);
            query.setParameter("user_email", this.loginTextField.getText());
            List<User> results = query.list();
            this.loggedUser = results.get(0);
            showDialog();
        }
    }

    private void cleanErrors(){
        this.loginErrorLabel.setText("");
        this.passwordError.setText("");
    }

    private void showErrors(Map<String, String> errors){
        errors.forEach((label, error) -> {
            if(label == "loginErrorLabel"){
                this.loginErrorLabel.setText(error);
            }
            else if(label == "passwordError"){
                this.passwordError.setText(error);
            }

        });
    }

}
