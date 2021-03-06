package org.firstinspires.ftc.teamcode.drive.userOpModes.robo7u.TeleOps.NormalTeleOps;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.canvas.Canvas;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.arcrobotics.ftclib.geometry.Pose2d;
import com.arcrobotics.ftclib.geometry.Rotation2d;
import com.arcrobotics.ftclib.geometry.Transform2d;
import com.arcrobotics.ftclib.geometry.Translation2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.spartronics4915.lib.T265Camera;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.drive.userOpModes.robo7u.Mecanisme.Mechanisms;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "TeleOp")
public class TeleOp extends LinearOpMode {

    ElapsedTime runtime;

    //Camera Intel
    private static T265Camera slamra = null;
    Translation2d translation;
    Rotation2d rotation;
    T265Camera.CameraUpdate up;

    //mecanisme
    Mechanisms mechanisms;

    //sasiu
    SampleMecanumDrive drive;

    //variabile pentru puteri
    double G1Y1, G1X1, G1X2;
    double LF, LR, RF, RR;

    //alte variabile
    boolean isPoseSet = false;
    double runtimeActual;

    //constante
    final double INCH_TO_METERS = 0.0254;

    //Ftc Dashboard
    private final FtcDashboard dashboard = FtcDashboard.getInstance();
    TelemetryPacket packet;
    Canvas field;
    final int robotRadius = 9;

    @Override
    public void runOpMode() throws InterruptedException {
        initialize();

        waitForStart();

        runtime.reset();

        while(opModeIsActive()) {

            powerMotors();

            updateDashboard();

            listenForMechanisms();

            updateTelemetry();
        }
    }

    public void initialize() {
        //cream mecanismele
        mechanisms = new Mechanisms(hardwareMap);

        //cream si initializam sasiul
        drive = new SampleMecanumDrive(hardwareMap);
        drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        SampleMecanumDrive.leftFront.setDirection(DcMotorSimple.Direction.FORWARD);
        SampleMecanumDrive.leftRear.setDirection(DcMotorSimple.Direction.FORWARD);
        SampleMecanumDrive.rightFront.setDirection(DcMotorSimple.Direction.REVERSE);
        SampleMecanumDrive.rightRear.setDirection(DcMotorSimple.Direction.REVERSE);

        runtime = new ElapsedTime();

        //facem si pornim camera
        if(slamra == null) {
            //initializam camera si comunicam pozitia centrului camerei fata de centrul robotului
            slamra = new T265Camera(new Transform2d(new Translation2d(-0.225, 0), new Rotation2d(0)), 0, hardwareMap.appContext);
            slamra.setPose(new Pose2d(new Translation2d(-63 * INCH_TO_METERS, -15*INCH_TO_METERS), new Rotation2d(0)));

            /*
            while(!isPoseSet) {
                if(slamra.getLastReceivedCameraUpdate().confidence == T265Camera.PoseConfidence.High) {

                    //comunicam camerei pozitia de start pe teren
                    //DIMENSIUNILE SUNT IN METRI, PENTRU VALORI IN INCH MULTIPLICAM CU VARIABILA "INCH_TO_METERS"
                    slamra.setPose(new Pose2d(new Translation2d(-72 * INCH_TO_METERS, 0), new Rotation2d(0)));

                    isPoseSet = true;
                }
            }

             */

            slamra.start();
        }
    }

    //motoare sasiu
    public void powerMotors() {

        if(gamepad1.left_stick_button) {
            drive.toggleIntakeFrontFace();
        } else if(gamepad1.right_stick_button) {
            drive.toggleShooterFrontFace();
        }

        /*
        if(drive.getFace() == true) {
            G1Y1 = -gamepad1.left_stick_y;
            G1X1 = gamepad1.left_stick_x;
            G1X2 = gamepad1.right_stick_x;

            LF = G1Y1 + G1X1 - G1X2;
            LR = G1Y1 - G1X1 - G1X2;
            RF = G1Y1 - G1X1 + G1X2;
            RR = G1Y1 + G1X1 + G1X2;
        } else {
            G1Y1 = -gamepad1.left_stick_y;
            G1X1 = gamepad1.left_stick_x;
            G1X2 = gamepad1.right_stick_x;

            LF = G1Y1 + G1X1 + G1X2;
            LR = G1Y1 - G1X1 + G1X2;
            RF = G1Y1 - G1X1 - G1X2;
            RR = G1Y1 + G1X1 - G1X2;
        }

         */

        G1Y1 = -gamepad1.left_stick_y;
        G1X1 = gamepad1.left_stick_x;
        G1X2 = gamepad1.right_stick_x;

        LF = G1Y1 + G1X1 - G1X2;
        LR = G1Y1 - G1X1 - G1X2;
        RF = G1Y1 - G1X1 + G1X2;
        RR = G1Y1 + G1X1 + G1X2;

        drive.setMotorPowers(LF, LR, RR, RF);
    }

    public void updateDashboard() {
        packet = new TelemetryPacket();
        field = packet.fieldOverlay();

        up = slamra.getLastReceivedCameraUpdate();

        if(up == null) return;

        translation = new Translation2d(up.pose.getTranslation().getX() / INCH_TO_METERS, up.pose.getTranslation().getY() / INCH_TO_METERS);
        rotation = up.pose.getRotation();

        field.strokeCircle(translation.getX(), translation.getY(), robotRadius);
        double arrowX = rotation.getCos() * robotRadius, arrowY = rotation.getSin() * robotRadius;
        double x1 = translation.getX() + arrowX  / 2, y1 = translation.getY() + arrowY / 2;
        double x2 = translation.getX() + arrowX, y2 = translation.getY() + arrowY;
        field.strokeLine(x1, y1, x2, y2);

        dashboard.sendTelemetryPacket(packet);
    }

    public void updateTelemetry() {
        telemetry.addData("X", translation.getX());
        telemetry.addData("Y", translation.getY());
        telemetry.addData("Heading", rotation.getDegrees());
        telemetry.addData("Runtime", runtime);
        telemetry.addData("Pozitie", mechanisms.shooter.shooterLift.getPositions());
        telemetry.update();
    }

    public void listenForLimitsUpdates() {
        if(gamepad1.y) {
            mechanisms.shooter.shooterLift.updateTopPosition();
        } else if(gamepad1.x) {
            mechanisms.shooter.shooterLift.updateBottomPosition();
        }
    }

    public void listenForShooting() {

        //pentru flywheel
        if(gamepad1.left_trigger > 0) {
            mechanisms.shooter.launcher.powerFlywheel(true);
        } else if(gamepad1.right_trigger > 0) {
            mechanisms.shooter.launcher.powerFlywheel(false);
        } else {
            mechanisms.shooter.launcher.stopFlywheel();
        }

        //pentru servo-ul de launch
        //versiune cu shooting manual
        if(gamepad1.a && (runtime.milliseconds() - runtimeActual > 100)) {
            mechanisms.shooter.launcher.moveToRetractedPosition();
            runtimeActual = runtime.milliseconds();
        } else if(gamepad1.b && (runtime.milliseconds() - runtimeActual > 100)) {
            mechanisms.shooter.launcher.moveToShootingPosition();
            runtimeActual = runtime.milliseconds();
        }

        if(gamepad1.x && (runtime.milliseconds() - runtimeActual > 100)) {

            mechanisms.shooter.launcher.moveToShootingPosition();

            runtimeActual = runtime.milliseconds();

            sleep(200);

            mechanisms.shooter.launcher.moveToRetractedPosition();

        }
    }

    public void listenForLiftMovement() {
        //Pentru miscari mici, din 0.05 in 0.05
        if(gamepad1.dpad_down && (runtime.milliseconds() - runtimeActual > 500)) {
            mechanisms.shooter.shooterLift.lowerPosition();
            runtimeActual = runtime.milliseconds();
        } else if(gamepad1.dpad_up && (runtime.milliseconds() - runtimeActual > 500)) {
            mechanisms.shooter.shooterLift.raisePosition();
            runtimeActual = runtime.milliseconds();
        }

        //Pentru miscari radicale, de la pozitia actuala la limita aleasa
        if(gamepad1.left_bumper && (runtime.milliseconds() - runtimeActual > 500)) {
            mechanisms.shooter.shooterLift.moveToBottomPosition();
            runtimeActual = runtime.milliseconds();
        } else if(gamepad1.right_bumper && (runtime.milliseconds() - runtimeActual > 500)) {
            mechanisms.shooter.shooterLift.moveToTopPosition();
            runtimeActual = runtime.milliseconds();
        }
    }

    public void listenForIntakeMovement() {
        if(gamepad2.left_trigger > 0) {
            mechanisms.intake.powerIntake(1);
        } else if(gamepad2.right_trigger > 0) {
            mechanisms.intake.powerOuttake(1);
        } else {
            mechanisms.intake.stopIntake();
        }
    }

    public void listenForArmMovement() {

        if(gamepad2.dpad_up) {
            mechanisms.arm.teleOpExtend();
        } else if(gamepad2.dpad_down) {
            mechanisms.arm.teleOpRetract();
        } else {
            mechanisms.arm.stopArm();
        }
        //gionutz ti a lasat rame
        if(gamepad2.a) {
            mechanisms.arm.openClaw();
        } else if(gamepad2.y) {
            mechanisms.arm.closeClaw();
        }
    }

    public void listenForArmEncoderMovement() {

        if(gamepad2.dpad_up) {
            mechanisms.arm.extend(210);
        } else if(gamepad2.dpad_down) {
            mechanisms.arm.retract(210);
        }

        //gionutz ti a lasat rame
        if(gamepad2.a) {
            mechanisms.arm.openClaw();
        } else if(gamepad2.y) {
            mechanisms.arm.closeClaw();
        }
    }

    public void listenForMechanisms() {

        listenForLimitsUpdates();

        listenForShooting();

        listenForLiftMovement();

        listenForArmMovement();

        listenForIntakeMovement();

    }

}
