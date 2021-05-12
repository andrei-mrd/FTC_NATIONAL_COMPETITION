package org.firstinspires.ftc.teamcode.drive.userOpModes.robo7u.Mecanisme;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

class Claw {

    public final double closedPosition = 1, openedPosition = 0;

    HardwareMap hwMap;
    Servo clawServo;

    Claw(HardwareMap ahwMap) {
        hwMap = ahwMap;
        clawServo = hwMap.get(Servo.class, "clawServo");

        clawServo.setDirection(Servo.Direction.FORWARD);
    }

    public double getPosition() {
        return clawServo.getPosition();
    }

    public double getOpenedPosition() {
        return openedPosition;
    }

    public double getClosedPosition() {
        return closedPosition;
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
        armMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        armMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public double getClawPosition() {
        return claw.getPosition();
    }

    public double getClawOpenedPosition() {
        return claw.getOpenedPosition();
    }

    public double getClawClosedPosition() {
        return claw.getClosedPosition();
    }

    public double getPosition() {
        return armMotor.getCurrentPosition();
    }

    public void teleOpRetract() {
        armMotor.setPower(0.7);
    }

    public void teleOpExtend() {
        armMotor.setPower(-0.7);
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

    public void retract(double degrees) {
        armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        double distance = degrees / 360;
        int target = (int)(distance * TICKS_PER_REV);
        double power = 0.4;

        armMotor.setTargetPosition(target);

        armMotor.setPower(power);

        armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        while(armMotor.isBusy());
        //armMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void extend(double degrees) {
        armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        double distance = degrees / 360;
        int target = (int)(distance * TICKS_PER_REV);
        double power = 0.4;

        armMotor.setTargetPosition(-target);

        armMotor.setPower(power);

        armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        while(armMotor.isBusy());
        //armMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
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
