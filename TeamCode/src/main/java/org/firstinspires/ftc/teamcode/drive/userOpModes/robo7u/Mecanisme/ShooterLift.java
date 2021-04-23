package org.firstinspires.ftc.teamcode.drive.userOpModes.robo7u.Mecanisme;

import com.arcrobotics.ftclib.hardware.ServoEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class ShooterLift {

    HardwareMap hwMap;

    double positionLeft, positionRight;
    double bottomPositionLeft = 0, topPositionLeft = 0.25;
    double bottomPositionRight = 0, topPositionRight = 0.25;

    //servo-uri privite din fata cu shooter-ul
    private Servo servoLeft;
    private Servo servoRight;

    //Functii de initializare + constructor

    public ShooterLift(HardwareMap ahwMap) {
        hwMap = ahwMap;
        servoLeft = hwMap.get(Servo.class, "servoLeft");
        servoRight = hwMap.get(Servo.class, "servoRight");

        initDirections();
        initPositions();
    }

    public void initDirections() {
        servoLeft.setDirection(Servo.Direction.FORWARD);
        servoRight.setDirection(Servo.Direction.REVERSE);
    }

    public void initPositions() {
        positionLeft = 0;
        positionRight = 0;
        servoLeft.setPosition(positionLeft);
        servoRight.setPosition(positionRight);
    }



    //Functii de control servo

    /**
     * Functie ce muta shooter-ul din pozitia actuala
     * in pozitia de inaltime minima setata
     */
    public void moveToBottomPosition() {
        positionLeft = bottomPositionLeft;
        positionRight = bottomPositionRight;
        servoLeft.setPosition(positionLeft);
        servoRight.setPosition(positionRight);
    }

    /**
     * Functie ce muta shooter-ul din pozitia actuala
     * in pozitia de inaltime maxima setata
     */
    public void moveToTopPosition() {
        positionLeft = topPositionLeft;
        positionRight = topPositionRight;
        servoLeft.setPosition(positionLeft);
        servoRight.setPosition(positionRight);
    }

    /**
     * Functie ce coboara shooter-ul 0.05 unitati
     */
    public void lowerPosition() {
        positionLeft -= 0.05;
        positionRight -= 0.05;
        servoLeft.setPosition(positionLeft);
        servoRight.setPosition(positionRight);
    }

    /**
     * Functie ce ridica shooter-ul 0.05 unitati
     */
    public void raisePosition() {
        positionLeft += 0.05;
        positionRight += 0.05;
        servoLeft.setPosition(positionLeft);
        servoRight.setPosition(positionRight);
    }
}
