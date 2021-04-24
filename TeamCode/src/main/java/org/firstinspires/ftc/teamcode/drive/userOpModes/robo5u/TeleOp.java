package org.firstinspires.ftc.teamcode.drive.userOpModes.robo5u;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@Disabled
@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "TeleOp5u")
public class TeleOp extends LinearOpMode {

    DcMotor leftFront;
    DcMotor rightFront;
    DcMotor leftRear;
    DcMotor rightRear;
    DcMotor shooter;
    DcMotor intake;
    DcMotor wobble;
    Servo wobbleServoSus;
    Servo wobbleServoJos;
    Servo shooterServo;

    double runtimeActual = -2000;
    ElapsedTime runtime = new ElapsedTime();

    SampleMecanumDrive drive;

    int fata = 1;

    float G1Y1, G1X1, G1X2;

    public void powerTurn() {
        if(runtime.milliseconds() - runtimeActual > 100) {
            //drive.turn(Math.toRadians(-6));
            runtimeActual = runtime.milliseconds();
        }
    }

    public void initialize() {

        drive = new SampleMecanumDrive(hardwareMap);

        //leftFront = hardwareMap.get(DcMotor.class, "leftFront");
        //rightFront = hardwareMap.get(DcMotor.class, "rightFront");
        //leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
        //leftRear = hardwareMap.get(DcMotor.class, "leftRear");
        //rightRear = hardwareMap.get(DcMotor.class, "rightRear");
        //leftRear.setDirection(DcMotorSimple.Direction.REVERSE);

        shooter = hardwareMap.get(DcMotor.class, "shooter");
        intake = hardwareMap.get(DcMotor.class, "intake");
        wobble = hardwareMap.get(DcMotor.class, "wobble");

        wobbleServoSus = hardwareMap.get(Servo.class, "wobbleServoSus");
        wobbleServoJos = hardwareMap.get(Servo.class, "wobbleServoJos");

        shooterServo = hardwareMap.get(Servo.class, "shooterServo");
        shooter.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    @Override
    public void runOpMode() throws InterruptedException {
        initialize();

        double highGoalCap = 1;
        double powerShotCap = 0.8;



        //if(gamepad1.b) {
        //    drive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //    powerTurn();
        //}

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        runtime.reset();

        while(opModeIsActive()) {

            double wobblePower = 0.25;
            double intakePower = 0.9;

            double power1 = gamepad1.left_trigger * highGoalCap;
            double power2 = gamepad1.right_trigger * highGoalCap;

            if(gamepad1.a) {
                shooterServo.setPosition(0.5);
            } else if(gamepad1.y) {
                shooterServo.setPosition(0);
            }

            if(gamepad2.dpad_up) {
                wobble.setPower(wobblePower);
            } else if(gamepad2.dpad_down) {
                wobble.setPower(-wobblePower);
            } else {
                wobble.setPower(0);
            }

            if(gamepad2.x) {
                wobbleServoSus.setPosition(1);
                wobbleServoJos.setPosition(0);
            } else if(gamepad2.b) {
                wobbleServoSus.setPosition(0);
                wobbleServoJos.setPosition(1);
            }


            if(gamepad2.left_bumper) {
                intake.setPower(intakePower);
            } else if(gamepad2.right_bumper) {
                intake.setPower(-intakePower);
            } else {
                intake.setPower(0);
            }

            if(power1 > 0) {
                shooter.setPower(power1);
            } else if(power2 > 0){
                shooter.setPower(-power2);
            } else {
                shooter.setPower(0);
            }

            G1Y1 = -gamepad1.left_stick_y;
            G1X1 = gamepad1.left_stick_x;
            G1X2 = gamepad1.right_stick_x;

            switch (fata) {
                case 1:
                    leftRear.setDirection(DcMotorSimple.Direction.REVERSE);
                    leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
                    rightRear.setDirection(DcMotorSimple.Direction.FORWARD);
                    rightFront.setDirection(DcMotorSimple.Direction.FORWARD);
                    leftFront.setPower(G1Y1 + G1X1 + G1X2);
                    leftRear.setPower(G1Y1 - G1X1 + G1X2);
                    rightFront.setPower(G1Y1 - G1X1 - G1X2);
                    rightRear.setPower(G1Y1 + G1X1 - G1X2);
                    break;
                case 2:
                    leftRear.setDirection(DcMotorSimple.Direction.FORWARD);
                    leftFront.setDirection(DcMotorSimple.Direction.FORWARD);
                    rightRear.setDirection(DcMotorSimple.Direction.REVERSE);
                    rightFront.setDirection(DcMotorSimple.Direction.REVERSE);
                    leftFront.setPower(G1Y1 + G1X1 - G1X2);
                    leftRear.setPower(G1Y1 - G1X1 - G1X2);
                    rightFront.setPower(G1Y1 - G1X1 + G1X2);
                    rightRear.setPower(G1Y1 + G1X1 + G1X2);
                    break;
            }

            if(gamepad1.left_stick_button) {
                fata = 1;
            } else if(gamepad1.right_stick_button) {
                fata = 2;
            }

            telemetry.addData("Putere Max", highGoalCap);
            telemetry.addData("Fata:", fata);
            telemetry.update();
        }
    }
}
