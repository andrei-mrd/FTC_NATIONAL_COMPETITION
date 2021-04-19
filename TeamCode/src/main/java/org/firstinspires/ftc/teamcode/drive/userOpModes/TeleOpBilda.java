package org.firstinspires.ftc.teamcode.drive.userOpModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "TeleOpBilda")
public class TeleOpBilda extends LinearOpMode {

    ElapsedTime runtime = new ElapsedTime();
    double runtimeActual = -2000;

    //global variables
    double powerCap = 1;
    double positionLeft, positionRight, launchPosition;
    double lowestPositionLeft = 0, highestPositionLeft = 0.25;
    double lowestPositionRight = 0, highestPositionRight = 0.25;

    //sasiu
    DcMotor leftFront;
    DcMotor rightFront;
    DcMotor leftRear;
    DcMotor rightRear;

    //intake
    DcMotor intake;

    //shooter
    DcMotorEx shooter;

    //shooter servos
    Servo shooterLeft;
    Servo shooterRight;
    Servo launchServo;

    float G1Y1, G1X1, G1X2;

    public void resetShooterPosition() {
        positionLeft = 0;
        shooterLeft.setPosition(positionLeft);
        positionRight = 0;
        shooterRight.setPosition(positionRight);
    }

    public void moveToLowestPosition() {
        positionLeft = lowestPositionLeft;
        positionRight = lowestPositionRight;
        shooterLeft.setPosition(positionLeft);
        shooterRight.setPosition(positionRight);
    }

    public void moveToHighestPosition() {
        positionLeft = highestPositionLeft;
        positionRight = highestPositionRight;
        shooterLeft.setPosition(positionLeft);
        shooterRight.setPosition(positionRight);
    }

    public void lowerPosition() {
        positionLeft -= 0.05;
        positionRight -= 0.05;
        shooterLeft.setPosition(positionLeft);
        shooterRight.setPosition(positionRight);
    }

    public void raisePosition() {
        positionLeft += 0.05;
        positionRight += 0.05;
        shooterLeft.setPosition(positionLeft);
        shooterRight.setPosition(positionRight);
    }

    public void resetLaunchPosition() {
        launchPosition = 0.47;
        launchServo.setPosition(launchPosition);
    }

    public void initialize() {

        shooter = hardwareMap.get(DcMotorEx.class, "shooter");

        shooterLeft = hardwareMap.get(Servo.class, "shooterLeft");
        shooterLeft.setDirection(Servo.Direction.FORWARD);
        shooterRight = hardwareMap.get(Servo.class, "shooterRight");
        shooterRight.setDirection(Servo.Direction.REVERSE);

        launchServo = hardwareMap.get(Servo.class, "launchServo");

        leftFront = hardwareMap.get(DcMotor.class, "leftFront");
        rightFront = hardwareMap.get(DcMotor.class, "rightFront");
        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
        leftRear = hardwareMap.get(DcMotor.class, "leftRear");
        rightRear = hardwareMap.get(DcMotor.class, "rightRear");
        leftRear.setDirection(DcMotorSimple.Direction.REVERSE);

        intake = hardwareMap.get(DcMotor.class, "intake");

        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftRear.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightRear.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        resetShooterPosition();
        resetLaunchPosition();
    }

    @Override
    public void runOpMode() {
        initialize();

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        runtime.reset();

        while(opModeIsActive()) {

            if(gamepad1.y) {
                highestPositionLeft = positionLeft;
                highestPositionRight = positionRight;
            } else if(gamepad1.x) {
                lowestPositionLeft = positionLeft;
                lowestPositionRight = positionRight;
            }

            if(gamepad1.a && (runtime.milliseconds() - runtimeActual > 100)) {
                launchPosition = 0.47;
                launchServo.setPosition(launchPosition);
                runtimeActual = runtime.milliseconds();
            } else if(gamepad1.b && (runtime.milliseconds() - runtimeActual > 100)) {
                launchPosition = 0.65;
                launchServo.setPosition(launchPosition);
                runtimeActual = runtime.milliseconds();
            }

            if(gamepad1.dpad_down && (runtime.milliseconds() - runtimeActual > 500)) {
                lowerPosition();
                runtimeActual = runtime.milliseconds();
            } else if(gamepad1.dpad_up && (runtime.milliseconds() - runtimeActual > 500)) {
                raisePosition();
                runtimeActual = runtime.milliseconds();
            }

            if(gamepad1.left_bumper && (runtime.milliseconds() - runtimeActual > 500)) {
                moveToLowestPosition();
                runtimeActual = runtime.milliseconds();
            } else if(gamepad1.right_bumper && (runtime.milliseconds() - runtimeActual > 500)) {
                moveToHighestPosition();
                runtimeActual = runtime.milliseconds();
            }

            if(gamepad1.left_trigger > 0) {
                shooter.setPower(powerCap);
            } else if(gamepad1.right_trigger > 0) {
                shooter.setPower(-powerCap);
            } else {
                shooter.setPower(0);
            }

            G1Y1 = -gamepad1.left_stick_y;
            G1X1 = gamepad1.left_stick_x;
            G1X2 = gamepad1.right_stick_x;

            leftRear.setDirection(DcMotorSimple.Direction.REVERSE);
            leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
            rightRear.setDirection(DcMotorSimple.Direction.FORWARD);
            rightFront.setDirection(DcMotorSimple.Direction.FORWARD);
            leftFront.setPower(G1Y1 + G1X1 + G1X2);
            leftRear.setPower(G1Y1 - G1X1 + G1X2);
            rightFront.setPower(G1Y1 - G1X1 - G1X2);
            rightRear.setPower(G1Y1 + G1X1 - G1X2);

            if(gamepad1.left_trigger > 0) {
                intake.setPower(gamepad1.left_trigger);
            } else if(gamepad1.right_trigger > 0){
                intake.setPower(-gamepad1.right_trigger);
            }

            telemetry.addData("PozitieLeft ", shooterLeft.getPosition());
            telemetry.addData("PozitieRight ", shooterRight.getPosition());
            telemetry.addData("LaunchPos ", launchServo.getPosition());
            telemetry.update();
        }
    }
}