package org.firstinspires.ftc.teamcode.drive.userOpModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "TeleOpSample")
public class TeleOpSample extends LinearOpMode {
    DcMotor shooter;
    DcMotor intake;
    DcMotor wobble;
    Servo wobbleServoSus;
    Servo wobbleServoJos;
    Servo shooterServo;

    boolean power;

    double runtimeActual = -2000;
    ElapsedTime runtime = new ElapsedTime();

    SampleMecanumDrive drive;

    int fata = 1;

    double G1Y1, G1X1, G1X2;
    double LF, LR, RF, RR;

    public void powerTurn() {
        if(runtime.milliseconds() - runtimeActual > 100) {
            drive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            drive.turn(Math.toRadians(-8));
            drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            runtimeActual = runtime.milliseconds();
        }
    }

    public void initialize() {

        drive = new SampleMecanumDrive(hardwareMap);
        drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

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
        double powerShotCap = 0.85;

        double power1;
        double power2;

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

            if(gamepad1.left_bumper) {
                power = true;
            } else if(gamepad1.right_bumper) {
                power = false;
            }

            if(power) {
                power1 = gamepad1.left_trigger * highGoalCap;
                power2 = gamepad1.right_trigger * highGoalCap;
            } else {
                power1 = gamepad1.left_trigger * powerShotCap;
                power2 = gamepad1.right_trigger * powerShotCap;
            }

            if(gamepad1.b) {
                powerTurn();
            }

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


            if(gamepad2.left_trigger > 0) {
                intake.setPower(intakePower);
            } else if(gamepad2.right_trigger > 0) {
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

            switch(fata) {
                case 1:
                    SampleMecanumDrive.leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
                    SampleMecanumDrive.leftRear.setDirection(DcMotorSimple.Direction.REVERSE);
                    SampleMecanumDrive.rightFront.setDirection(DcMotorSimple.Direction.FORWARD);
                    SampleMecanumDrive.rightRear.setDirection(DcMotorSimple.Direction.FORWARD);
                    LF = G1Y1 + G1X1 + G1X2;
                    LR = G1Y1 - G1X1 + G1X2;
                    RF = G1Y1 - G1X1 - G1X2;
                    RR = G1Y1 + G1X1 - G1X2;
                    drive.setMotorPowers(LF, LR , RR, RF);
                    break;
                case 2:
                    SampleMecanumDrive.leftFront.setDirection(DcMotorSimple.Direction.FORWARD);
                    SampleMecanumDrive.leftRear.setDirection(DcMotorSimple.Direction.FORWARD);
                    SampleMecanumDrive.rightFront.setDirection(DcMotorSimple.Direction.REVERSE);
                    SampleMecanumDrive.rightRear.setDirection(DcMotorSimple.Direction.REVERSE);
                    LF = G1Y1 + G1X1 - G1X2;
                    LR = G1Y1 - G1X1 - G1X2;
                    RF = G1Y1 - G1X1 + G1X2;
                    RR = G1Y1 + G1X1 + G1X2;
                    drive.setMotorPowers(LF, LR , RR, RF);
                    break;
            }

            if(gamepad1.left_stick_button) {
                fata = 1;
            } else if(gamepad1.right_stick_button) {
                fata = 2;
            }

            if(power) {
                telemetry.addData("Putere Max", highGoalCap);
            } else {
                telemetry.addData("Putere Max", powerShotCap);
            }
            telemetry.addData("Fata:", fata);
            telemetry.update();
        }
    }
}
