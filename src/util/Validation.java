package util;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.util.LinkedHashMap;
import java.util.regex.Pattern;


public class Validation {

    public static Object validate(LinkedHashMap<JFXTextField, Pattern> map, JFXButton btn) {
        btn.setDisable(true);
        for (JFXTextField textFieldKey : map.keySet()) {
            Pattern patternValue = map.get(textFieldKey);
            if (!patternValue.matcher(textFieldKey.getText()).matches()) {
                if (!textFieldKey.getText().isEmpty()) {
                    textFieldKey.getParent().setStyle("-fx-border-color: red");
                    ((Pane) textFieldKey.getParent()).getChildren().get(1).setStyle("-fx-text-fill: red");
                }
                return textFieldKey;
            }
            textFieldKey.getParent().setStyle("-fx-border-color: green");
            ((Pane) textFieldKey.getParent()).getChildren().get(1).setStyle("-fx-text-fill: green");
        }
        btn.setDisable(false);
        return true;
    }
}
