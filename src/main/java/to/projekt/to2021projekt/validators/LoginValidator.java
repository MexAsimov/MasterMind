package to.projekt.to2021projekt.validators;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import to.projekt.to2021projekt.hibernate.SessionProvider;
import to.projekt.to2021projekt.models.User;
import org.springframework.security.crypto.bcrypt.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginValidator implements Validator{

    private Map<String, String> errors = new HashMap<>();

    private boolean validateLogin(String login){
        String regex = "^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        if(!login.matches(regex)){
            errors.put("loginErrorLabel", "Incorrect email");
            return false;
        }
        if(login.length() > 50){
            errors.put("loginErrorLabel", "login is too long");
            return false;
        }
        return true;
    }

    private boolean validatePassword(String password, String login){
        if(password.length() <= 5 || password.length() >= 50){
            errors.put("passwordError", "password length must be between 5 and 50");
            return false;
        }

        Session session = SessionProvider.getSession();
        String hql = "FROM User U WHERE U.email = :user_email";
        Query query = session.createQuery(hql);
        query.setParameter("user_email", login);
        List<User> results = query.list();
        session.close();
        if(results.size() == 0){
            errors.put("passwordError", "incorrect login or password");
            return false;
        }
        boolean passwordMatch = BCrypt.checkpw(password, results.get(0).getHashedPassword());
        if(!passwordMatch){
            errors.put("passwordError", "incorrect login or password");
            return false;
        }
        return true;
    }

    @Override
    public boolean validate(Parent parent) {
        boolean result = true;
        String login = "";
        for(Node c: parent.getChildrenUnmodifiable()){
            if(c.getId() != null && c.getId().equals("loginTextField")){
                result = validateLogin(((TextField) c).getText()) && result;
                login = ((TextField) c).getText();
            }
        }
        for(Node c: parent.getChildrenUnmodifiable()){
            if(c.getId() != null && c.getId().equals("passwordField")){
                result = validatePassword(((TextField) c).getText(), login) && result;
            }
        }
        return result;
    }

    @Override
    public Map<String, String> getErrors() {
        return this.errors;
    }
}
