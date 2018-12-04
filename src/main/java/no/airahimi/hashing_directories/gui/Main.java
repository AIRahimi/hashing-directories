package no.airahimi.hashing_directories.gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
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

        final Pane rootGroup = new VBox(12);
        initUI(rootGroup);
        rootGroup.setPadding(new Insets(12, 12, 12 , 12));

        Scene scene = new Scene(rootGroup, 720, 445);


        scene.widthProperty().addListener(
                (obs, oldVal, newVal) -> {
                }
        );

        scene.heightProperty().addListener(
                (obs, oldVal, newVal) -> {

        }
        );

        File file = new File("src/main/resources/Main.css");
        scene.getStylesheets().add("file:///" + file.getAbsolutePath().replace("\\", "/"));

        primaryStage.setScene(scene);
        primaryStage.setMinWidth(460);
        primaryStage.setMinHeight(350);
        primaryStage.minWidthProperty().bind(scene.heightProperty().multiply(1.5));
        primaryStage.minHeightProperty().bind(scene.widthProperty().divide(1.5));
        primaryStage.show();
    }

    private void initUI(Pane root) {
        final GridPane gridPane = initGridPane();
        final TextArea textArea = initTextArea();
        final HBox hBox  = initHBox();

        root.getChildren().addAll(gridPane, textArea, hBox);
    }

    private GridPane initGridPane() {
        final FileChooser fileChooser = new FileChooser();
        final DirectoryChooser directoryChooser = new DirectoryChooser();

        final TextField fileTextField = new TextField("No file/folder selected.");
        final TextField hashTextField = new TextField("No file selected at the moment.");

        fileTextField.setEditable(false);
        hashTextField.setEditable(false);

        final ToggleGroup fileGroup = new ToggleGroup();
        final ToggleGroup functionGroup = new ToggleGroup();

        final ToggleButton fileButton = new ToggleButton();
        final ToggleButton folderButton = new ToggleButton();
        final ToggleButton hashButton = new ToggleButton("HASH");
        final ToggleButton checkButton = new ToggleButton("CHECK");

        hashButton.setMinWidth(60);
        checkButton.setMinWidth(60);
        hashButton.setToggleGroup(functionGroup);
        checkButton.setToggleGroup(functionGroup);

        final ImageView fileView = new ImageView(new Image("file:src/main/resources/fileicon.png"));
        final ImageView folderView = new ImageView(new Image("file:src/main/resources/foldericon.png"));

        fileView.setPreserveRatio(true);
        fileButton.setGraphic(fileView);
        fileButton.setMinWidth(60);
        fileButton.setToggleGroup(fileGroup);
        folderView.setPreserveRatio(true);
        folderButton.setGraphic(folderView);
        folderButton.setMinWidth(60);
        folderButton.setToggleGroup(fileGroup);



        /**
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
        */

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


        //Width-Responsivity
        //inputGridPane.prefWidthProperty().setValue(primaryStage.getWidth());

        fileView.fitWidthProperty().bindBidirectional(fileButton.minWidthProperty());
        folderView.fitWidthProperty().bindBidirectional(folderButton.minWidthProperty());

        fileButton.prefWidthProperty().bind(inputGridPane.widthProperty().divide(6));
        folderButton.prefWidthProperty().bind(inputGridPane.widthProperty().divide(6));
        fileTextField.prefWidthProperty().bind(inputGridPane.widthProperty().divide(6).multiply(4));

        hashButton.prefWidthProperty().bind(inputGridPane.widthProperty().divide(6));
        checkButton.prefWidthProperty().bind(inputGridPane.widthProperty().divide(6));
        fileTextField.prefWidthProperty().bind(inputGridPane.widthProperty().divide(6).multiply(4));

        /*

        resetView.fitWidthProperty().bindBidirectional(resetButton.minWidthProperty());
        playView.fitWidthProperty().bindBidirectional(playButton.minWidthProperty());
        saveView.fitWidthProperty().bindBidirectional(saveButton.minWidthProperty());

        resetButton.prefWidthProperty().bind(inputGridPane.widthProperty().divide(3));
        playButton.prefWidthProperty().bind(inputGridPane.widthProperty().divide(3));
        saveButton.prefWidthProperty().bind(inputGridPane.widthProperty().divide(3));*/

        return inputGridPane;
    }

    private TextArea initTextArea() {

        TextArea textArea = new TextArea(" hey");
        textArea.setEditable(false);

        /**
         // Height-Responsivity
         textArea.prefHeightProperty().bind(
         scene.heightProperty().subtract(inputGridPane.getHeight()));*/

        return textArea;

    }

    private HBox initHBox() {
        HBox hBox = new HBox();

        final ImageView resetView = new ImageView(new Image("file:src/main/resources/reseticon.png"));
        final ImageView playView = new ImageView(new Image("file:src/main/resources/playicon.png"));
        final ImageView saveView = new ImageView(new Image("file:src/main/resources/saveicon.png"));

        resetView.setPreserveRatio(true);
        playView.setPreserveRatio(true);
        saveView.setPreserveRatio(true);

        final Button resetButton = new Button();
        final Button playButton = new Button();
        final Button saveButton = new Button();

        resetButton.setGraphic(resetView);
        playButton.setGraphic(playView);
        saveButton.setGraphic(saveView);
        resetButton.setMinWidth(60);
        playButton.setMinWidth(60);
        saveButton.setMinWidth(60);
        hBox.setSpacing(12);
        hBox.getChildren().addAll(resetButton, playButton, saveButton);

        return hBox;
    }

}
