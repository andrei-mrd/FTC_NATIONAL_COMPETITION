package org.firstinspires.ftc.teamcode.drive.userOpModes.robo7u.TeleOps.AutomatedTeleOps;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.canvas.Canvas;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.arcrobotics.ftclib.geometry.Rotation2d;
import com.arcrobotics.ftclib.geometry.Transform2d;
import com.arcrobotics.ftclib.geometry.Translation2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.spartronics4915.lib.T265Camera;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.drive.userOpModes.robo7u.Mecanisme.Arm;
import org.firstinspires.ftc.teamcode.drive.userOpModes.robo7u.Mecanisme.Intake;
import org.firstinspires.ftc.teamcode.drive.userOpModes.robo7u.Mecanisme.Mechanisms;
import org.firstinspires.ftc.teamcode.drive.userOpModes.robo7u.Mecanisme.Shooter;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "CompetitionTeleOp")
public class CompetitionTeleOp extends LinearOpMode {

    //sasiu
    SampleMecanumDrive drive;

    enum Mode {
        DRIVER_CONTROL,
        AUTOMATIC_CONTROL
    }

    Mode currentMode = Mode.DRIVER_CONTROL;

    Pose2d poseEstimate;

    Vector2d powerShotsLocation = new Vector2d(0, -20);
    Vector2d highGoalLocation = new Vector2d(0, -36);

    double targetHeading = Math.toRadians(0);

    ElapsedTime runtime;

    //mecanisme
    Mechanisms mechanisms;

    //variabile pentru puteri
    double G1Y1, G1X1, G1X2;
    double LF, LR, RF, RR;

    //alte variabile
    boolean isPoseSet = false;
    double runtimeActual;

    //constante
    final double INCH_TO_METERS = 0.0254;
    final int robotRadius = 9;

    @Override
    public void runOpMode() throws InterruptedException {

        initialize();

        waitForStart();

        if(isStopRequested()) {
            return;
        }

        runtime.reset();

        while(opModeIsActive()) {

            drive.update();

            poseEstimate = drive.getPoseEstimate();

            runtimeActual = runtime.milliseconds();

            switch(currentMode) {
                case DRIVER_CONTROL:

                    //drive.setWeightedDrivePower(new Pose2d(-gamepad1.left_stick_y, -gamepad1.left_stick_x, -gamepad1.right_stick_x));

                    listenForAutomaticControl();

                    powerMotors();

                    listenForMechanisms();

                    updateTelemetry();

                    break;

                case AUTOMATIC_CONTROL:

                    listenForDriverControl();

                    break;
            }

        }
    }

    public void initialize() {
        //cream mecanismele
        mechanisms = new Mechanisms(hardwareMap);

        //cream si initializam sasiul
        drive = new SampleMecanumDrive(hardwareMap);
        drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        drive.toggleShooterFrontFace();

        runtime = new ElapsedTime();

    }

    public void listenForDriverControl() {

        if(gamepad1.x) {
            drive.cancelFollowing();
            currentMode = Mode.DRIVER_CONTROL;
        }

        if(!drive.isBusy()) {
            currentMode = Mode.DRIVER_CONTROL;
        }

    }

    public void listenForAutomaticControl() {

        if(gamepad1.a) {
            Trajectory traj1 = drive.trajectoryBuilder(poseEstimate)
                    .splineTo(highGoalLocation, targetHeading)
                    .build();

            drive.followTrajectoryAsync(traj1);

            currentMode = Mode.AUTOMATIC_CONTROL;
        } else if(gamepad1.b) {
            Trajectory traj1 = drive.trajectoryBuilder(poseEstimate)
                    .lineTo(powerShotsLocation)
                    .build();

            drive.followTrajectoryAsync(traj1);

            currentMode = Mode.AUTOMATIC_CONTROL;
        } else if(gamepad1.y) {
            drive.turnAsync(Math.toRadians(0));
            currentMode = Mode.AUTOMATIC_CONTROL;
        }

    }

    public void updateTelemetry() {
        telemetry.addData("mode", currentMode);
        telemetry.addData("x", poseEstimate.getX());
        telemetry.addData("y", poseEstimate.getY());
        telemetry.addData("heading", poseEstimate.getHeading());
        telemetry.update();
    }

    //motoare sasiu
    public void powerMotors() {
        G1Y1 = -gamepad1.left_stick_y;
        G1X1 = gamepad1.left_stick_x;
        G1X2 = gamepad1.right_stick_x;

        LF = G1Y1 + G1X1 + G1X2;
        LR = G1Y1 - G1X1 + G1X2;
        RF = G1Y1 - G1X1 - G1X2;
        RR = G1Y1 + G1X1 - G1X2;

        drive.setMotorPowers(LF, LR, RR, RF);
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
            mechanisms.shooter.launcher.powerFlywheel(false);
        } else if(gamepad1.right_trigger > 0) {
            mechanisms.shooter.launcher.reversePowerFlywheel(false);
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
