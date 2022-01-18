package to.projekt.to2021projekt.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import org.hibernate.Session;
import org.hibernate.Transaction;
import to.projekt.to2021projekt.FXutils.SceneProvider;
import to.projekt.to2021projekt.FXutils.StageProvider;
import to.projekt.to2021projekt.hibernate.SessionProvider;
import to.projekt.to2021projekt.models.User;
import to.projekt.to2021projekt.util.MailController;
import to.projekt.to2021projekt.util.MailType;
import to.projekt.to2021projekt.validators.RegisterValidator;
import to.projekt.to2021projekt.validators.Validator;
import org.springframework.security.crypto.bcrypt.*;

import javax.mail.MessagingException;
import java.util.Map;
import java.util.Optional;

public class RegisterController {

    @FXML
    private Button RegisterButton;

    @FXML
    private Label RepeatPasswordLabel;

    @FXML
    private Button backtoLoginButton;

    @FXML
    private Label emailErrorLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private TextField emailTextInput;

    @FXML
    private PasswordField passwordField;

    @FXML
    private VBox registerPanel;

    @FXML
    private Label registerPasswordError;

    @FXML
    private Label repeatPasswordError;

    @FXML
    private PasswordField repeatPasswordField;

    private void cleanErrors(){
        this.emailErrorLabel.setText("");
        this.repeatPasswordError.setText("");
        this.registerPasswordError.setText("");
    }

    @FXML
    public void backToLoginPanel(MouseEvent event) {
        SceneProvider.getInstance().loadScene("view/login-view.fxml", "stylesheets/login-panel.css");
        StageProvider.getInstance().getStage().setScene(SceneProvider.getInstance().getScene());
    }

    @FXML
    public void register(MouseEvent event) {
        this.cleanErrors();
        Validator validator = new RegisterValidator();
        boolean result = validator.validate(SceneProvider.getInstance().getScene().getRoot());
        this.showErrors(validator.getErrors());
        if(result){
            continueRegister(this.emailTextInput.getText(), this.passwordField.getText());
        }
    }

    private void showDialog(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Register Successful");
        alert.setHeaderText(null);
        alert.setContentText("Your registration was successful. Now you can login to your new account.");
        ButtonType buttonTypeOK = new ButtonType("OK");
        alert.getButtonTypes().setAll(buttonTypeOK);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeOK) {
            SceneProvider.getInstance().loadScene("view/login-view.fxml", "stylesheets/login-panel.css");
            StageProvider.getInstance().getStage().setScene(SceneProvider.getInstance().getScene());
        }

    }

    private void continueRegister(String login, String password){
        String salt = BCrypt.gensalt();
        String hashedPassword = BCrypt.hashpw(password, salt);
        User newUser = new User(login, hashedPassword);
        Session session = SessionProvider.getSession();
        Transaction tx = session.beginTransaction();
        session.save(newUser);
        tx.commit();
        session.close();
        showDialog();
        sendNotification();
    }

    private void showErrors(Map<String, String> errors){
        errors.forEach((label, error) -> {
            if(label == "emailErrorLabel"){
                this.emailErrorLabel.setText(error);
            }
            if(label == "registerPasswordError"){
                this.registerPasswordError.setText(error);
            }
            if(label == "repeatPasswordError"){
                this.repeatPasswordError.setText(error);
            }
        });
    }
    private void sendNotification(){
        MailController mailController = new MailController();
        mailController.setReceiverEmail(this.emailTextInput.getText());
        try {
            mailController.sendMail(MailType.RegisterNotification);
        } catch (MessagingException e) {
            System.out.println("mail Error");
        }
    }

}
