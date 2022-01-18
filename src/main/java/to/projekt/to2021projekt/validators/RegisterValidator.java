package to.projekt.to2021projekt.validators;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.security.crypto.bcrypt.BCrypt;
import to.projekt.to2021projekt.hibernate.SessionProvider;
import to.projekt.to2021projekt.models.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegisterValidator implements Validator{

    private boolean validateLogin(String login){
        String regex = "^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        if(!login.matches(regex)){
            errors.put("emailErrorLabel", "Incorrect email");
            return false;
        }
        if(login.length() > 50){
            errors.put("emailErrorLabel", "email is too long");
            return false;
        }
        Session session = SessionProvider.getSession();
        String hql = "FROM User U WHERE U.email = :user_email";
        Query query = session.createQuery(hql);
        query.setParameter("user_email", login);
        List results = query.list();
        if(results.size() == 1){
            errors.put("emailErrorLabel", "this email is already taken");
            return false;
        }
        return true;
    }

    private boolean validatePassword(String password){
        if(password.length() <= 5 || password.length() >= 50){
            errors.put("registerPasswordError", "password length must be between 5 and 50");
            return false;
        }
        return true;
    }

    private boolean validateRepeatPassword(String password, String repeat){
        if(!password.equals(repeat)){
            errors.put("repeatPasswordError", "repeated password is wrong");
            return false;
        }
        return true;
    }

    @Override
    public boolean validate(Parent parent) {
        boolean result = true;
        String password = "";
        for(Node c: parent.getChildrenUnmodifiable()){
            if(c.getId() != null && c.getId().equals("emailTextInput")){
                result = validateLogin(((TextField) c).getText()) && result;
            }
            if(c.getId() != null && c.getId().equals("passwordField")){
                result = validatePassword(((TextField) c).getText()) && result;
                password = ((TextField) c).getText();
            }
        }
        for(Node c: parent.getChildrenUnmodifiable()){
            if(c.getId() != null && c.getId().equals("repeatPasswordField")){
                result = validateRepeatPassword(((TextField) c).getText(), password) && result;
            }
        }
        return result;
    }

    @Override
    public Map<String, String> getErrors() {
        return this.errors;
    }

    private Map<String, String> errors = new HashMap<>();
}
