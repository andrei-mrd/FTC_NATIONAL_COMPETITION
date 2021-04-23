package org.firstinspires.ftc.teamcode.drive.userOpModes.robo7u.Mecanisme;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

class Claw {

    double closedPosition = 1, openedPosition = 0;

    HardwareMap hwMap;
    Servo servo1;
    Servo servo2;

    Claw(HardwareMap ahwMap) {
        hwMap = ahwMap;
        servo1 = hwMap.get(Servo.class, "servo1");
        servo2 = hwMap.get(Servo.class, "servo2");

        servo1.setDirection(Servo.Direction.FORWARD);
        servo2.setDirection(Servo.Direction.REVERSE);
    }

    public void close() {
        servo1.setPosition(closedPosition);
        servo2.setPosition(closedPosition);
    }

    public void open() {
        servo1.setPosition(openedPosition);
        servo2.setPosition(openedPosition);
    }
}

public class Arm {

    final double TICKS_PER_REV = 3480;

    HardwareMap hwMap;

    DcMotor armMotor;
    Claw claw;

    public Arm(HardwareMap ahwMap) {
        hwMap = ahwMap;
        claw = new Claw(hwMap);

        armMotor = hwMap.get(DcMotor.class, "armMotor");
    }

    public void retract(int degrees) {
        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        double distance = degrees / 360;
        int target = (int)(distance * TICKS_PER_REV);
        double power = 0.4;

        armMotor.setTargetPosition(target);

        armMotor.setPower(power);

        armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        while(armMotor.isBusy());
    }

    public void extend(int degrees) {
        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        double distance = degrees / 360;
        int target = (int)(distance * TICKS_PER_REV);
        double power = 0.4;

        armMotor.setTargetPosition(-target);

        armMotor.setPower(power);

        armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        while(armMotor.isBusy());
    }

    public void extendAndDropWobble() {
        extend(150);
        claw.open();
    }

    public void takeWobbleAndRetract() {
        claw.close();
        retract(150);
    }

}
