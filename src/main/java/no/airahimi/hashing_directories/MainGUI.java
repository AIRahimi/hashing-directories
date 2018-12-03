package no.airahimi.hashing_directories;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.Desktop;

public class MainGUI extends Application {

    private Desktop desktop = Desktop.getDesktop();

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(final Stage primaryStage) {
        primaryStage.setTitle("Checksum");

        final FileChooser fileChooser = new FileChooser();
        final TextArea fileNameArea = new TextArea("File name");

        final ToggleGroup toggleGroup = new ToggleGroup();
        ToggleButton hashButton = new ToggleButton("HASH");
        ToggleButton checkButton = new ToggleButton("CHECK");

        hashButton.setToggleGroup(toggleGroup);
        checkButton.setToggleGroup(toggleGroup);
        hashButton.setSelected(true);

        hashButton.setOnAction(
                e-> {

                }
        );
        checkButton.setOnAction(
                e-> {

                }
        );

        final GridPane inputGridPane = new GridPane();
        GridPane.setConstraints(hashButton, 0, 0);
        GridPane.setConstraints(checkButton, 1, 0);
        GridPane.setConstraints(fileNameArea, 2, 0);
        inputGridPane.getChildren().addAll(hashButton, checkButton, fileNameArea);
        inputGridPane.setHgap(10);

        final Pane rootGroup = new VBox(12);
        rootGroup.getChildren().addAll(inputGridPane);
        rootGroup.setPadding(new Insets(12, 12, 12 , 12));

        primaryStage.setScene(new Scene(rootGroup, 720, 445));
        primaryStage.show();
    }
}
