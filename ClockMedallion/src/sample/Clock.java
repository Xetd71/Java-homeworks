package sample;

import javafx.application.Platform;
import javafx.beans.property.*;

import java.lang.management.PlatformLoggingMXBean;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

class Clock {
    private int _time;
    private boolean _image;
    private boolean _isStart;
    private static final int MILLIS_IN_DAY = 86400; // 24 * 36000

    private DoubleProperty _secondAngleProperty = new SimpleDoubleProperty();
    private DoubleProperty _minuteAngleProperty = new SimpleDoubleProperty();
    private DoubleProperty _hourAngleProperty = new SimpleDoubleProperty();
    private DoubleProperty _arcLengthProperty = new SimpleDoubleProperty();

    private IntegerProperty _secondProperty = new SimpleIntegerProperty();
    private IntegerProperty _minuteProperty = new SimpleIntegerProperty();
    private IntegerProperty _hourProperty = new SimpleIntegerProperty();

    Clock(int seconds, int period) {
        _time = seconds;
        _image = true;
        _isStart = true;
        _secondAngleProperty.setValue(getSecondAngle());
        _minuteAngleProperty.setValue(getMinuteAngle());
        _hourAngleProperty.setValue(getHourAngle());

        new Timer(true).schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        Platform.runLater( () -> {
                            _time = (_time + 1) % MILLIS_IN_DAY;

                            _secondAngleProperty.set(getSecondAngle());
                            _minuteAngleProperty.set(getMinuteAngle());
                            _hourAngleProperty.set(getHourAngle());
                            _arcLengthProperty.set(getArcLength());

                            _secondProperty.set(_time % 60);
                            _minuteProperty.set(_time % 3600 / 60);
                            _hourProperty.set(_time / 3600);
                        });
                    }
                },
                0,
                period
        );
    }

    Clock(Calendar calendar, int period) {
        this(
            calendar.get(Calendar.HOUR_OF_DAY) * 3600
                    + calendar.get(Calendar.MINUTE) * 60
                    + calendar.get(Calendar.SECOND),
            period
        );
    }

    private double getSecondAngle() {
        return _time % 60 * 6;
    }

    private double getMinuteAngle() {
        return _time % 3600 / 10.;
    }

    private double getHourAngle() {
        return _time / 120.;
    }

    private double getArcLength() {
        double angle = getSecondAngle();
        if(angle == 0) {
            _image = !_image;
            _isStart = false;
        } else if(_isStart) {
            return 360;
        }
        return _image ? - angle : 360 - angle;
    }

    DoubleProperty secondAngleProperty() {
        return _secondAngleProperty;
    }

    DoubleProperty minuteAngleProperty() {
        return _minuteAngleProperty;
    }

    DoubleProperty hourAngleProperty() {
        return _hourAngleProperty;
    }

    DoubleProperty arcLengthProperty() {
        return _arcLengthProperty;
    }

    IntegerProperty secondProperty() {
        return _secondProperty;
    }

    IntegerProperty minuteProperty() {
        return _minuteProperty;
    }

    IntegerProperty hourProperty() {
        return _hourProperty;
    }

}