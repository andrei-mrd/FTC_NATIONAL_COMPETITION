package org.firstinspires.ftc.teamcode.drive.userOpModes.robo7u.Mecanisme;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

class Claw {

    double closedPosition = 1, openedPosition = 0;

    HardwareMap hwMap;
    Servo clawServo;

    Claw(HardwareMap ahwMap) {
        hwMap = ahwMap;
        clawServo = hwMap.get(Servo.class, "clawServo");

        clawServo.setDirection(Servo.Direction.FORWARD);
    }

    public void closeClaw() {
        clawServo.setPosition(closedPosition);
    }

    public void openClaw() {
        clawServo.setPosition(openedPosition);
    }
}

public class Arm {

    final double TICKS_PER_REV = 3480;

    HardwareMap hwMap;

    DcMotor armMotor;
    public Claw claw;

    public Arm(HardwareMap ahwMap) {
        hwMap = ahwMap;
        claw = new Claw(hwMap);

        armMotor = hwMap.get(DcMotor.class, "armMotor");
    }

    public void teleOpRetract() {
        armMotor.setPower(0.6);
    }

    public void teleOpExtend() {
        armMotor.setPower(-0.6);
    }

    public void stopArm() {
        armMotor.setPower(0);
    }

    public void midPosition() {

    }

    public void retractedPosition() {

    }

    public void extendedPosition() {

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

    public void openClaw() {
        claw.openClaw();
    }

    public void closeClaw() {
        claw.closeClaw();
    }

    public void extendAndDropWobble() {
        extend(150);
        claw.openClaw();
    }

    public void takeWobbleAndRetract() {
        claw.closeClaw();
        retract(150);
    }

}
