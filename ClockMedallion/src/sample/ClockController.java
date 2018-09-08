package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.*;
import javafx.scene.transform.Rotate;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.MalformedURLException;
import java.util.Calendar;

public class ClockController {
    public AnchorPane clockView;
    public Circle backView;
    public Arc clockFace;
    public Shape secondHand;
    public Shape minuteHand;
    public Shape hourHand;
    public HBox digitalClick;
    public Label hourNumber;
    public Label minuteNumber;
    public Label secondNumber;
    public VBox settingsView;
    public Button regimeButton;
    public CheckBox customTime;
    public Slider clockPeriodSlider;
    public Spinner<Double> clockPeriodSpinner;
    public HBox timeSetter;
    public Spinner<Integer> hourTimeSetter;
    public Spinner<Integer> minuteTimeSetter;
    public Spinner<Integer> secondTimeSetter;
    public CheckBox customSpeed;
    public HBox speedSetter;
    public ColorPicker colorPicker;

    private Image clockImage;
    private Rotate hourRotate;
    private Rotate minuteRotate;
    private Rotate secondRotate;
    private Regime regime;
    private Clock clock;

    private SpinnerValueFactory<Integer> hourValueFactory;
    private SpinnerValueFactory<Integer> minuteValueFactory;
    private SpinnerValueFactory<Integer> secondValueFactory;

    @FXML
    protected void initialize() {
        //region clockView
        Group ticks = new Group();
        for (int i = 0; i < 60; i++) {
            Line hourTick;
            if(i % 5 == 0) {
                hourTick = new Line(0, -200, 0, -180);
                hourTick.setStrokeWidth(2.22);
            } else {
                hourTick = new Line(0, -200, 0, -190);
                hourTick.setStrokeWidth(1.11);
            }
            hourTick.setTranslateX(200);
            hourTick.setTranslateY(200);
            hourTick.getTransforms().add(new Rotate(i * 6));
            ticks.getChildren().add(hourTick);
        }
        clockView.getChildren().add(ticks);

        clockImage = new Image(getClass().getResource("resources/Chuev_Ivan.jpg").toString());
        backView.setFill(new ImagePattern(clockImage));
        //endregion

        //region clockAnimation
        hourRotate = new Rotate();
        minuteRotate = new Rotate();
        secondRotate = new Rotate();

        hourHand.getTransforms().add(hourRotate);
        minuteHand.getTransforms().add(minuteRotate);
        secondHand.getTransforms().add(secondRotate);
        //endregion

        //region customSpeed
        customSpeed.selectedProperty().addListener((observable, oldValue, newValue) -> { speedSetter.setDisable(oldValue); });
        SpinnerValueFactory<Double> periodValueFactory =
                new SpinnerValueFactory.DoubleSpinnerValueFactory(0.1, 10, 1, 0.1);
        clockPeriodSpinner.setValueFactory(periodValueFactory);

        clockPeriodSpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
            clockPeriodSlider.valueProperty().setValue(periodValueFactory.getValue());
        });
        clockPeriodSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            periodValueFactory.valueProperty().setValue(clockPeriodSlider.getValue());
        });
        //endregion

        //region customTime
        customTime.selectedProperty().addListener((observable, oldValue, newValue) -> {
            timeSetter.setDisable(oldValue);
            if(newValue) {
                hourValueFactory.setValue(clock.hourProperty().getValue());
                minuteValueFactory.setValue(clock.minuteProperty().getValue());
                secondValueFactory.setValue(clock.secondProperty().getValue());
            }
        });

        hourValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 0);
        minuteValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0);
        secondValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0);

        hourTimeSetter.setValueFactory(hourValueFactory);
        minuteTimeSetter.setValueFactory(minuteValueFactory);
        secondTimeSetter.setValueFactory(secondValueFactory);

        hourValueFactory.valueProperty().addListener((observable, oldValue, newValue) -> {
            hourRotate.setAngle(newValue * 15);
            hourNumber.setText(newValue.toString());
        });
        minuteValueFactory.valueProperty().addListener((observable, oldValue, newValue) -> {
            minuteRotate.setAngle(newValue * 6);
            minuteNumber.setText(newValue.toString());
        });
        secondValueFactory.valueProperty().addListener((observable, oldValue, newValue) -> {
            secondRotate.setAngle(newValue * 6);
            secondNumber.setText(newValue.toString());
        });
        //endregion

        //region clockPicker
        colorPicker.setValue(Color.GREEN);
        colorPicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            clockFace.setFill(newValue);
        });
        //endregion

        startClock(Calendar.getInstance(), 1000);
    }

    @FXML
    public void chooseImage(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JPG", "*.jpg"));
        File file = fileChooser.showOpenDialog(null);
        if(file == null)
            return;
        try {
            clockImage = new Image(file.toURI() .toURL().toString());
            backView.setFill(new ImagePattern(clockImage));
        } catch(MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void changeRegime(ActionEvent actionEvent) {
        if(regime.equals(Regime.Start)) {
            Calendar calendar = Calendar.getInstance();
            if(customTime.isSelected()) {
                calendar.set(Calendar.HOUR_OF_DAY, hourTimeSetter.getValue());
                calendar.set(Calendar.MINUTE, minuteTimeSetter.getValue());
                calendar.set(Calendar.SECOND, secondTimeSetter.getValue());
            }
            int period = 1000;
            if(customSpeed.isSelected())
                period /= clockPeriodSlider.getValue();
            startClock(calendar, period);
        } else {
            stopClock();
        }
    }

    private void startClock(Calendar calendar, int period) {
        clock = new Clock(calendar, period);

        hourRotate.angleProperty().bind(clock.hourAngleProperty());
        minuteRotate.angleProperty().bind(clock.minuteAngleProperty());
        secondRotate.angleProperty().bind(clock.secondAngleProperty());
        clockFace.lengthProperty().bind(clock.arcLengthProperty());

        hourNumber.textProperty().bind(clock.hourProperty().asString("%02d"));
        minuteNumber.textProperty().bind(clock.minuteProperty().asString("%02d"));
        secondNumber.textProperty().bind(clock.secondProperty().asString("%02d"));

        regime = Regime.Stop;
        regimeButton.setText(regime.name);
        settingsView.setDisable(true);
    }

    private void stopClock() {
        clockFace.lengthProperty().unbind();
        hourRotate.angleProperty().unbind();
        minuteRotate.angleProperty().unbind();
        secondRotate.angleProperty().unbind();

        hourNumber.textProperty().unbind();
        minuteNumber.textProperty().unbind();
        secondNumber.textProperty().unbind();

        regime = Regime.Start;
        regimeButton.setText(regime.name);
        settingsView.setDisable(false);
    }
}
