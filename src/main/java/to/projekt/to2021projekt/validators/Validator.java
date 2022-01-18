package to.projekt.to2021projekt.validators;

import javafx.scene.Parent;

import java.util.Map;

public interface Validator {
    boolean validate(Parent parent);
    Map<String, String> getErrors();
}
