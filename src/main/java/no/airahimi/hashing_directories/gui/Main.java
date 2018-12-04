package no.airahimi.hashing_directories.gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class Main extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(final Stage primaryStage) {
        primaryStage.setTitle("Checksum");

        final FileChooser fileChooser = new FileChooser();
        final DirectoryChooser directoryChooser = new DirectoryChooser();
        final TextField fileTextField = new TextField("No files or folder selected at the moment.");
        final TextField hashTextField = new TextField("No check-file or hash-file selected at the moment.");

        fileTextField.setEditable(false);
        hashTextField.setEditable(false);

        final ToggleGroup fileGroup = new ToggleGroup();
        final ToggleButton fileButton = new ToggleButton();
        final ImageView fileView = new ImageView(new Image("file:src/main/resources/fileicon.png"));
        fileView.setPreserveRatio(true);
        fileButton.setGraphic(fileView);
        fileButton.setMinWidth(60);
        fileButton.setToggleGroup(fileGroup);

        final ToggleButton folderButton = new ToggleButton();
        final ImageView folderView = new ImageView(new Image("file:src/main/resources/foldericon.png"));
        folderView.setPreserveRatio(true);
        folderButton.setGraphic(folderView);
        folderButton.setMinWidth(60);
        folderButton.setToggleGroup(fileGroup);

        final ToggleGroup functionGroup = new ToggleGroup();
        final ToggleButton hashButton = new ToggleButton("HASH");
        final ToggleButton checkButton = new ToggleButton("CHECK");
        hashButton.setMinWidth(60);
        checkButton.setMinWidth(60);
        hashButton.setToggleGroup(functionGroup);
        checkButton.setToggleGroup(functionGroup);

        final TextArea textArea = new TextArea(" hey");


        fileButton.setOnAction(
                e-> {
                    fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
                    fileChooser.setTitle("Select file");
                    File file = fileChooser.showOpenDialog(primaryStage);

                    if (file != null)
                        fileTextField.setText(file.getAbsolutePath());
                }
        );
        folderButton.setOnAction(
                e-> {
                    directoryChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
                    directoryChooser.setTitle("Select folder");
                    File file = directoryChooser.showDialog(primaryStage);

                    if (file != null && file.isDirectory())
                        fileTextField.setText(file.getAbsolutePath());
                }
        );

        checkButton.setOnAction(
                e-> {
                    fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
                    fileChooser.setTitle("Select hash file to scan for changes");
                    File file = fileChooser.showOpenDialog(primaryStage);

                    if (file != null)
                        hashTextField.setText(file.getAbsolutePath());
                }

        );

        hashButton.setOnAction(
            e-> {
                fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
                fileChooser.setTitle("Select file to write hashes to");
                File file = fileChooser.showSaveDialog(primaryStage);

                if (file != null)
                    hashTextField.setText(file.getAbsolutePath());
            }
        );

        hashTextField.setOnMouseClicked(
            e-> {
                fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
                fileChooser.setTitle("Select hash file");
                File file = fileChooser.showOpenDialog(primaryStage);

                if (file != null)
                    hashTextField.setText(file.getAbsolutePath());
            }
        );



        final GridPane inputGridPane = new GridPane();
        GridPane.setConstraints(fileButton, 0, 0);
        GridPane.setConstraints(folderButton, 1, 0);
        GridPane.setConstraints(fileTextField, 2, 0);
        GridPane.setConstraints(hashButton, 0, 1);
        GridPane.setConstraints(checkButton, 1, 1);
        GridPane.setConstraints(hashTextField, 2, 1);

        inputGridPane.getChildren().addAll(fileButton, folderButton, fileTextField,
                hashButton, checkButton, hashTextField);
        inputGridPane.setHgap(10);
        inputGridPane.setVgap(10);

        final Pane rootGroup = new VBox(12);
        Scene scene = new Scene(rootGroup, 720, 445);
        rootGroup.prefHeightProperty().bind(scene.heightProperty());
        rootGroup.prefWidthProperty().bind(scene.widthProperty());
        rootGroup.getChildren().addAll(inputGridPane, textArea);
        rootGroup.setPadding(new Insets(12, 12, 12 , 12));


        primaryStage.widthProperty().addListener(
                (obs, oldVal, newVal) -> {
                    inputGridPane.prefWidthProperty().setValue(primaryStage.getWidth());

                    fileButton.prefWidthProperty().bind(inputGridPane.widthProperty().divide(6));
                    folderButton.prefWidthProperty().bind(inputGridPane.widthProperty().divide(6));
                    fileTextField.prefWidthProperty().bind(inputGridPane.widthProperty().divide(3).multiply(2));

                    hashButton.prefWidthProperty().bind(inputGridPane.widthProperty().divide(6));
                    checkButton.prefWidthProperty().bind(inputGridPane.widthProperty().divide(6));
                    fileTextField.prefWidthProperty().bind(inputGridPane.widthProperty().divide(3).multiply(2));

                    fileView.fitWidthProperty().bindBidirectional(fileButton.minWidthProperty());
                    folderView.fitWidthProperty().bindBidirectional(folderButton.minWidthProperty());
                }
        );
        primaryStage.heightProperty().addListener(
                (obs, oldVal, newVal) -> textArea.prefHeightProperty().bind(
                        primaryStage.heightProperty().subtract(inputGridPane.getHeight()))
        );



        File file = new File("src/main/resources/Main.css");
        scene.getStylesheets().add("file:///" + file.getAbsolutePath().replace("\\", "/"));

        primaryStage.setScene(scene);
        primaryStage.setMinWidth(460);
        primaryStage.setMinHeight(350);
        primaryStage.show();
    }

}
