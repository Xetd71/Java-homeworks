package simpleTorrent.client.gui;

import javafx.beans.InvalidationListener;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import simpleTorrent.client.Client;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.regex.Pattern;

public class ClientGUIController implements Initializable {
    public TextField serverIP;
    public TextField portTextField;
    public Button connectToServerButton;
    public ListView files;
    public Button selectDirectoryButton;
    public Button downloadSelectedButton;
    public Label errors;
    public ProgressBar progressBar;
    public Label selectedDirectoryLabel;

    private Stage primaryStage;
    private StringProperty selectedDirectory = new SimpleStringProperty("");
    private Client client;
    private DoubleProperty progress = new SimpleDoubleProperty();

    public ClientGUIController(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        primaryStage.setOnCloseRequest(ev -> {
            if(client != null) {
                try {
                    client.closeConnection();
                } catch(IOException e) {
                    e.printStackTrace();
                }
            }
        });
        selectDirectoryButton.setOnAction(e -> selectDirectory());
        connectToServerButton.setOnAction(e -> connectToServer());
        downloadSelectedButton.setOnAction(e -> downloadFile());
        progressBar.progressProperty().bind(progress);
        selectedDirectoryLabel.textProperty().bind(selectedDirectory);
    }

    private void selectDirectory() {
        DirectoryChooser dc = new DirectoryChooser();
        File dir = dc.showDialog(primaryStage);
        selectedDirectory.setValue(dir == null ? "" : dir.getAbsolutePath());
    }

    private static final Pattern IP = Pattern.compile("^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");

    private void connectToServer() {
        if(connectToServerButton.getText().equals("Close connection")) {
            try {
                client.closeConnection();
                files.setItems(FXCollections.observableArrayList());
                connectToServerButton.setText("Connect to server");
            } catch(IOException e) {
                errors.setText("Something went wrong, can't close connection");
            }
            return;
        }

        int port;
        if(!IP.matcher(serverIP.getText()).matches()) {
            errors.setText("Wrong IP address");
            return;
        }
        try {
            port = Integer.parseInt(portTextField.getText());
        } catch(NumberFormatException ex) {
            errors.setText("wrong port number");
            return;
        }
        errors.setText("");

        try {
            client = new Client(serverIP.getText(), port);
            List<String> s = client.loadFiles();
            files.setItems(FXCollections.observableArrayList(s));
            connectToServerButton.setText("Close connection");
        } catch(IOException e) {
            errors.setText("Connection error");
        }
    }

    private void downloadFile() {
        if(files.getItems().isEmpty()) {
            errors.setText("please, load files");
            return;
        }
        Object file = files.getSelectionModel().getSelectedItem();
        if(file == null) {
            errors.setText("please, select file");
            return;
        }
        if(selectedDirectory.getValue().isEmpty()) {
            errors.setText("please, select directory to save");
            return;
        }
        errors.setText("");

        long n = -1;
        try {
            n = client.Ask((String)file);
            Alert alert = new Alert(
                    Alert.AlertType.CONFIRMATION,
                    "Are you sure you want to download file with size " + n / 1024 + "Kb",
                    ButtonType.YES,
                    ButtonType.NO
            );
            alert.showAndWait();
            if (alert.getResult() == ButtonType.NO)
                n = -1;
        } catch(IOException e) {
            e.printStackTrace();
        }

        long k = n;
        downloadSelectedButton.setDisable(true);
        Thread downloadTask = new Thread(() -> {
            try {
               client.downloadFile((String)file, selectedDirectory.getValue(), progress, k);
            } catch(IOException e) {
                errors.setText("Can't download file");
            } finally {
                progress.setValue(0);
                downloadSelectedButton.setDisable(false);
            }
        });
        downloadTask.setDaemon(true);
        downloadTask.start();
    }
}
