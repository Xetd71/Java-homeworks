package simpleTorrent.server.gui;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import simpleTorrent.server.Server;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;

public class ServerGUIController implements Initializable {
    public Label serverIP;
    public TextField portTextField;
    public Button selectDirectoryButton;
    public Label selectedDirectoryLabel;
    public Button startServerButton;
    public Label errors;

    private Server server;
    private Stage primaryStage;
    private volatile StringProperty selectedDirectory = new SimpleStringProperty("");
    private Thread serverThread;

    ServerGUIController(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        primaryStage.setOnCloseRequest(ev -> {
            if(server != null) try {
                server.stop();
            } catch(IOException e1) {
                e1.printStackTrace();
            }
        });
        try {
            InetAddress localHostAddress = InetAddress.getLocalHost();
            serverIP.setText(localHostAddress.getHostAddress());
        } catch(UnknownHostException e) {
            e.printStackTrace();
        }
        selectedDirectoryLabel.textProperty().bind(selectedDirectory);
        selectDirectoryButton.setOnAction(e -> selectDirectory());
        startServerButton.setOnAction(e -> startServer());
    }

    private void selectDirectory() {
        DirectoryChooser dc = new DirectoryChooser();
        File dir = dc.showDialog(primaryStage);
        selectedDirectory.setValue(dir == null ? "" : dir.getAbsolutePath());
    }

    private void startServer() {
        if(startServerButton.getText().equals("Start server")) {
            int port;
            try {
                port = Integer.parseInt(portTextField.getText());
                if(selectedDirectory.getValue().isEmpty()){
                    errors.setText("directory does't selected");
                    return;
                }
            } catch(NumberFormatException e) {
                errors.setText("wrong port number");
                return;
            }
            errors.setText("");

            try {
                server = new Server(port, 50, new File(selectedDirectory.getValue()));
                serverThread = new Thread(() -> server.start());
                serverThread.start();
                startServerButton.setText("Stop server");
            } catch(IOException e) {
                errors.setText("Can't start server");
            }
        } else {
            try {
                server.stop();
                startServerButton.setText("Start server");
            } catch(IOException e) {
                errors.setText("Can't stop server");
            }
        }
    }
}
