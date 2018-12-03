package no.airahimi.hashing_directories;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class MainGUI extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(final Stage primaryStage) {
        primaryStage.setTitle("Checksum");

        final FileChooser fileChooser = new FileChooser();
        final DirectoryChooser directoryChooser = new DirectoryChooser();
        final TextField fileTextField = new TextField("Click to select file to checksum.");
        final TextField hashTextField = new TextField("Click to select checksum file.");

        fileTextField.setEditable(false);
        hashTextField.setEditable(false);


        final ToggleGroup fileGroup = new ToggleGroup();
        final ToggleButton fileButton = new ToggleButton("FILE");
        fileButton.setGraphic(new ImageView());
        final ToggleButton folderButton = new ToggleButton("FOLDER");
        final ToggleGroup functionGroup = new ToggleGroup();
        final ToggleButton hashButton = new ToggleButton("HASH");
        final ToggleButton checkButton = new ToggleButton("CHECK");

        fileButton.setMinWidth(59);
        folderButton.setMinWidth(59);
        fileButton.setToggleGroup(fileGroup);
        folderButton.setToggleGroup(fileGroup);
        fileButton.setSelected(true);
        hashButton.setMinWidth(59);
        checkButton.setMinWidth(59);
        hashButton.setToggleGroup(functionGroup);
        checkButton.setToggleGroup(functionGroup);
        hashButton.setSelected(true);

        fileTextField.setOnMouseClicked(
            e-> {
                if (fileButton.isSelected()) {
                    fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
                    fileChooser.setTitle("Select file to check or hash");
                    File file = fileChooser.showOpenDialog(primaryStage);
                    if (file != null) {
                        fileTextField.setText(file.getAbsolutePath());
                    }
                } else if (folderButton.isSelected()) {
                    directoryChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
                    directoryChooser.setTitle("Select folder to check or hash");
                    File file = directoryChooser.showDialog(primaryStage);
                    if (file != null && file.isDirectory()) {
                        fileTextField.setText(file.getAbsolutePath());
                    }
                }

            }
        );

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

        hashTextField.setOnMouseClicked(
            e-> {
                fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
                fileChooser.setTitle("Select hash file");
                File file = fileChooser.showOpenDialog(primaryStage);
                if (file != null) {
                    hashTextField.setText(file.getAbsolutePath());
                }
            }
        );



        final GridPane inputGridPane = new GridPane();
        GridPane.setConstraints(fileButton, 0, 0);
        GridPane.setConstraints(folderButton, 1, 0);
        GridPane.setConstraints(fileTextField, 2, 0);
        GridPane.setConstraints(hashButton, 0, 1);
        GridPane.setConstraints(checkButton, 1, 1);
        GridPane.setConstraints(hashTextField, 2, 1);

        inputGridPane.getChildren().addAll(fileButton, folderButton, fileTextField, hashButton, checkButton, hashTextField);
        inputGridPane.setHgap(10);
        inputGridPane.setVgap(10);

        final Pane rootGroup = new VBox(12);
        Scene scene = new Scene(rootGroup, 720, 445);
        rootGroup.prefHeightProperty().bind(scene.heightProperty());
        rootGroup.prefWidthProperty().bind(scene.widthProperty());
        rootGroup.getChildren().addAll(inputGridPane);
        rootGroup.setPadding(new Insets(12, 12, 12 , 12));


        primaryStage.widthProperty().addListener(
                (obs, oldVal, newVal) -> {
                    inputGridPane.prefWidthProperty().setValue(primaryStage.getWidth());
                    fileButton.prefWidthProperty().bind(inputGridPane.widthProperty().divide(6));

                    folderButton.prefWidthProperty().bind(inputGridPane.widthProperty().divide(6));
                    fileTextField.prefWidthProperty().bind(inputGridPane.widthProperty().divide(3).multiply(2));
                }
        );

        primaryStage.heightProperty().addListener(
                (obs, oldVal, newVal) -> {
                    inputGridPane.prefHeightProperty().setValue(primaryStage.getHeight());
                }
        );


        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
