package no.airahimi.hashing_directories;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
        final TextField fileTextField = new TextField("Click to select file/folder for checksum");
        final TextField hashTextField = new TextField("Click to select checksum file.");

        fileTextField.setEditable(false);
        fileTextField.setMinWidth(279);
        fileTextField.setMaxWidth(279);
        hashTextField.setEditable(false);
        hashTextField.setMinWidth(279);
        hashTextField.setMaxWidth(279);

        final ToggleGroup toggleGroup = new ToggleGroup();
        ToggleButton hashButton = new ToggleButton("HASH");
        ToggleButton checkButton = new ToggleButton("CHECK");

        hashButton.setMinWidth(53);
        checkButton.setMinWidth(53);
        hashButton.setToggleGroup(toggleGroup);
        checkButton.setToggleGroup(toggleGroup);
        hashButton.setSelected(true);

        hashButton.setOnAction(
                e-> {
                    fileTextField.setText("You clicked hashButton!");
                    System.out.println("You clicked hashbutton");
                }
        );
        checkButton.setOnAction(
                e-> {
                    fileTextField.setText("You clicked checkButton!");
                    System.out.println("You clicked checkbutton");
                }
        );

        fileTextField.setOnMouseClicked(
                e-> {
                    fileTextField.setText("You clicked me!");
                    System.out.println("You clicked file text field");
                    System.out.println(fileTextField.getWidth());
                }
        );

        hashTextField.setOnMouseClicked(
                e-> {
                    hashTextField.setText("You clicked me!");
                    System.out.println("You clicked hash text field");
                    System.out.println(hashTextField.getWidth());
                }
        );


        final GridPane inputGridPane = new GridPane();
        GridPane.setConstraints(hashButton, 0, 0);
        GridPane.setConstraints(checkButton, 1, 0);
        GridPane.setConstraints(fileTextField, 2, 0);
        GridPane.setConstraints(hashTextField, 3, 0);

        inputGridPane.getChildren().addAll(hashButton, checkButton, fileTextField, hashTextField);
        inputGridPane.setHgap(10);

        final Pane rootGroup = new VBox(12);
        rootGroup.getChildren().addAll(inputGridPane);
        rootGroup.setPadding(new Insets(12, 12, 12 , 12));

        primaryStage.setScene(new Scene(rootGroup, 720, 445));
        primaryStage.setResizable(false);
        primaryStage.show();

        System.out.println(hashButton.getWidth());
        System.out.println(checkButton.getWidth());
        System.out.println(fileTextField.getWidth());
    }
}
