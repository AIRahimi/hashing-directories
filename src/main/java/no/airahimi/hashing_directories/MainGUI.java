package no.airahimi.hashing_directories;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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

public class MainGUI extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(final Stage primaryStage) {
        primaryStage.setTitle("Checksum");

        final FileChooser fileChooser = new FileChooser();
        final DirectoryChooser directoryChooser = new DirectoryChooser();
        final TextField fileTextField = new TextField("No files or folder selected at the moment.");
        final TextField hashTextField = new TextField("Click to select checksum file.");

        fileTextField.setEditable(false);
        hashTextField.setEditable(false);

        final ToggleGroup fileGroup = new ToggleGroup();
        final ToggleButton fileButton = new ToggleButton();
        final ImageView fileView = new ImageView(new Image("file:src/main/resources/fileicon.png"));
        fileView.fitWidthProperty().bindBidirectional(fileButton.minWidthProperty());
        fileView.setPreserveRatio(true);
        fileButton.setGraphic(fileView);

        final ToggleButton folderButton = new ToggleButton();
        final ImageView folderView = new ImageView(new Image("file:src/main/resources/foldericon.png"));
        folderView.fitWidthProperty().bindBidirectional(folderButton.minWidthProperty());
        folderView.setPreserveRatio(true);
        folderButton.setGraphic(folderView);

        final ToggleGroup functionGroup = new ToggleGroup();

        final ToggleButton hashButton = new ToggleButton("HASH");
        fileButton.setMinWidth(59);
        folderButton.setMinWidth(59);
        fileButton.setToggleGroup(fileGroup);
        folderButton.setToggleGroup(fileGroup);
        fileButton.setSelected(true);
        final ToggleButton checkButton = new ToggleButton("CHECK");
        hashButton.setMinWidth(59);
        checkButton.setMinWidth(59);
        hashButton.setToggleGroup(functionGroup);
        checkButton.setToggleGroup(functionGroup);
        hashButton.setSelected(true);

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

                    hashButton.prefWidthProperty().bind(inputGridPane.widthProperty().divide(6));
                    checkButton.prefWidthProperty().bind(inputGridPane.widthProperty().divide(6));
                    fileTextField.prefWidthProperty().bind(inputGridPane.widthProperty().divide(3).multiply(2));

                }
        );

        primaryStage.heightProperty().addListener(
                (obs, oldVal, newVal) -> {
                    inputGridPane.prefHeightProperty().setValue(primaryStage.getHeight());
                }
        );


        primaryStage.setScene(scene);
        primaryStage.setMinWidth(460);
        primaryStage.setMinHeight(350);
        primaryStage.show();
    }

}
