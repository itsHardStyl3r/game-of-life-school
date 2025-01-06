import javafx.util.converter.BooleanStringConverter;

public class CellStyleConverter extends BooleanStringConverter {
    @Override
    public String toString(Boolean b) {
        return b ? "-fx-background-color: green; -fx-padding: 5px;" :
                "-fx-background-color: lightgray; -fx-padding: 5px;";
    }

    @Override
    public Boolean fromString(String s) {
        return s.contains("green");
    }
}
