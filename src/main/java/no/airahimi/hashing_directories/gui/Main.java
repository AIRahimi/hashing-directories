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
import no.airahimi.hashing_directories.Check;
import no.airahimi.hashing_directories.Hash;

import java.io.*;

public class Main extends Application {

    public static final String NEW_LINE = System.getProperty("line.separator");

    public static void main(String[] args) {
        Application.launch(args);
    }

    private FileChooser fileChooser;

    private TextField fileTextField;
    private TextField hashTextField;

    private ToggleButton fileButton;
    private ToggleButton folderButton;
    private ToggleButton hashButton;
    private ToggleButton checkButton;

    private TextArea textArea;

    @Override
    public void start(final Stage primaryStage) {
        primaryStage.setTitle("Checksum");

        final VBox rootGroup = new VBox(12);
        initUI(rootGroup, primaryStage);
        rootGroup.setPadding(new Insets(12, 12, 12 , 12));
        rootGroup.setMinSize(720, 445);

        Scene scene = new Scene(rootGroup);

        File file = new File("src/main/resources/Main.css");
        scene.getStylesheets().add("file:///" + file.getAbsolutePath().replace("\\", "/"));

        primaryStage.setScene(scene);
        primaryStage.setMinHeight(445.0);
        primaryStage.setMinWidth(720.0);
        primaryStage.show();
    }

    private void initUI(Pane root, Stage stage) {
        final GridPane gridPane = initGridPane(stage);
        final TextArea textArea = initTextArea(stage);
        final HBox hBox  = initHBox(stage);

        root.getChildren().addAll(gridPane, textArea, hBox);


    }

    private GridPane initGridPane(Stage stage) {
        final GridPane inputGridPane = new GridPane();
        inputGridPane.setHgap(10);
        inputGridPane.setVgap(10);

        fileChooser = new FileChooser();
        final DirectoryChooser directoryChooser = new DirectoryChooser();

        fileTextField = new TextField("No file/folder selected.");
        hashTextField = new TextField("No file selected at the moment.");

        fileTextField.setEditable(false);
        hashTextField.setEditable(false);


        final ImageView fileView = new ImageView(new Image("file:src/main/resources/fileicon.png"));
        final ImageView folderView = new ImageView(new Image("file:src/main/resources/foldericon.png"));

        fileView.setPreserveRatio(true);
        folderView.setPreserveRatio(true);
        fileView.setSmooth(true);
        folderView.setSmooth(true);
        fileView.setCache(true);
        folderView.setCache(true);

        // init ToggleButtons
        fileButton = new ToggleButton();
        folderButton = new ToggleButton();
        hashButton = new ToggleButton("HASH");
        checkButton = new ToggleButton("CHECK");

        // Set Image/Icon
        fileButton.setGraphic(fileView);
        folderButton.setGraphic(folderView);
        // Set min width
        hashButton.setMinWidth(60);
        checkButton.setMinWidth(60);
        fileButton.setMinWidth(60);
        folderButton.setMinWidth(60);

        fileView.fitWidthProperty().bindBidirectional(fileButton.minWidthProperty());
        folderView.fitWidthProperty().bindBidirectional(folderButton.minWidthProperty());



        final ToggleGroup fileGroup = new ToggleGroup();
        final ToggleGroup functionGroup = new ToggleGroup();

        hashButton.setToggleGroup(functionGroup);
        checkButton.setToggleGroup(functionGroup);
        fileButton.setToggleGroup(fileGroup);
        folderButton.setToggleGroup(fileGroup);

        fileButton.prefWidthProperty().bind(inputGridPane.widthProperty().divide(6));
        folderButton.prefWidthProperty().bind(inputGridPane.widthProperty().divide(6));
        fileTextField.prefWidthProperty().bind(inputGridPane.widthProperty().divide(6).multiply(4));

        hashButton.prefWidthProperty().bind(inputGridPane.widthProperty().divide(6));
        checkButton.prefWidthProperty().bind(inputGridPane.widthProperty().divide(6));
        fileTextField.prefWidthProperty().bind(inputGridPane.widthProperty().divide(6).multiply(4));

        fileButton.setOnAction(
                e-> {
                    fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
                    fileChooser.setTitle("Select file");
                    File file = fileChooser.showOpenDialog(stage);

                    if (file != null)
                        fileTextField.setText(file.getAbsolutePath());
                }
        );
        folderButton.setOnAction(
                e-> {
                    directoryChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
                    directoryChooser.setTitle("Select folder");
                    File file = directoryChooser.showDialog(stage);

                    if (file != null && file.isDirectory())
                        fileTextField.setText(file.getAbsolutePath());
                }
        );

        checkButton.setOnAction(
                e-> {
                    fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
                    fileChooser.setTitle("Select hash file to scan for changes");
                    File file = fileChooser.showOpenDialog(stage);

                    if (file != null)
                        hashTextField.setText(file.getAbsolutePath());
                }

        );

        hashButton.setOnAction(
                e-> {
                    fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
                    fileChooser.setTitle("Select file to write hashes to");
                    fileChooser.getExtensionFilters().addAll(
                            new FileChooser.ExtensionFilter("Checksum file", "*.check"),
                            new FileChooser.ExtensionFilter("All files", "*.*")
                    );
                    File file = fileChooser.showSaveDialog(stage);

                    if (file != null)
                        hashTextField.setText(file.getAbsolutePath());
                }
        );

        GridPane.setConstraints(fileButton, 0, 0);
        GridPane.setConstraints(folderButton, 1, 0);
        GridPane.setConstraints(fileTextField, 2, 0);
        GridPane.setConstraints(hashButton, 0, 1);
        GridPane.setConstraints(checkButton, 1, 1);
        GridPane.setConstraints(hashTextField, 2, 1);

        inputGridPane.getChildren().addAll(fileButton, folderButton, fileTextField,
                hashButton, checkButton, hashTextField);

        return inputGridPane;
    }

    private TextArea initTextArea(Stage stage) {
        textArea = new TextArea(readme());
        textArea.setEditable(false);
        VBox.setVgrow(textArea, Priority.ALWAYS);
        return textArea;

    }

    private HBox initHBox(Stage stage) {
        HBox hBox = new HBox(12);
        final ImageView resetView = new ImageView(new Image("file:src/main/resources/reseticon.png"));
        final ImageView playView = new ImageView(new Image("file:src/main/resources/playicon.png"));
        final ImageView saveView = new ImageView(new Image("file:src/main/resources/saveicon.png"));

        resetView.setPreserveRatio(true);
        playView.setPreserveRatio(true);
        saveView.setPreserveRatio(true);
        resetView.setSmooth(true);
        playView.setSmooth(true);
        saveView.setSmooth(true);
        playView.setSmooth(true);

        final Button resetButton = new Button();
        final Button playButton = new Button();
        final Button saveButton = new Button();

        resetView.fitWidthProperty().bindBidirectional(resetButton.minWidthProperty());
        playView.fitWidthProperty().bindBidirectional(playButton.minWidthProperty());
        saveView.fitWidthProperty().bindBidirectional(saveButton.minWidthProperty());

        resetButton.setGraphic(resetView);
        playButton.setGraphic(playView);
        saveButton.setGraphic(saveView);
        resetButton.setMinWidth(60);
        playButton.setMinWidth(60);
        saveButton.setMinWidth(60);
        resetButton.setMaxWidth(Double.MAX_VALUE);
        playButton.setMaxWidth(Double.MAX_VALUE);
        saveButton.setMaxWidth(Double.MAX_VALUE);

        HBox.setHgrow(resetButton, Priority.ALWAYS);
        HBox.setHgrow(playButton, Priority.ALWAYS);
        HBox.setHgrow(saveButton, Priority.ALWAYS);

        resetButton.setOnAction(
                e -> {
                    fileTextField.setText("No file/folder selected.");
                    hashTextField.setText("No file selected at the moment.");
                    fileButton.setSelected(false);
                    folderButton.setSelected(false);
                    hashButton.setSelected(false);
                    checkButton.setSelected(false);
                    textArea.setText(readme());
                }
        );

        playButton.setOnAction(
                e -> {
                    if ((fileButton.isSelected() || folderButton.isSelected()) &&
                            (hashButton.isSelected() || checkButton.isSelected())) {

                        if (hashButton.isSelected()) {
                            Hash hash = new Hash(new File(fileTextField.getText()), new File(hashTextField.getText()));
                            hash.scanDirectory();

                            textArea.setText(hash.getOutputString());
                        } else if (checkButton.isSelected()) {
                            Check check = new Check(new File(fileTextField.getText()), new File(hashTextField.getText()));
                            check.scanDirectory();

                            textArea.setText(check.getOutputString());
                        }

                    } else {
                        textArea.appendText("\n\nError:");
                    }

                    if (!fileButton.isSelected() && !folderButton.isSelected())
                        textArea.appendText("\nPlease select a file or folder to hash");

                    if (!hashButton.isSelected() && !checkButton.isSelected())
                        textArea.appendText("\nPlease select a file to check hashes or a file to save hash output to.");

                }
        );

        saveButton.setOnAction(
                e -> {
                    FileChooser fileChooser = new FileChooser();
                    FileChooser.ExtensionFilter extFilter =
                            new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
                    fileChooser.getExtensionFilters().add(extFilter);

                    fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
                    fileChooser.setTitle("Select file to save output to");
                    File file = fileChooser.showSaveDialog(stage);

                    if (file != null) {
                        try {
                            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
                            bufferedWriter.write(textArea.getText());
                            bufferedWriter.close();
                        } catch (IOException ex) {
                            System.err.println(ex.toString());
                        }
                    }
                }
        );

        hBox.getChildren().addAll(resetButton, playButton, saveButton);

        return hBox;
    }

    private String readme() {
        StringBuilder sb = new StringBuilder();
        sb.append("Manual - Checksum").append(NEW_LINE);
        sb.append("This is the manual").append(NEW_LINE);
        sb.append("ye").append(NEW_LINE);
        sb.append("lol lol");
        return sb.toString();
    }

}
