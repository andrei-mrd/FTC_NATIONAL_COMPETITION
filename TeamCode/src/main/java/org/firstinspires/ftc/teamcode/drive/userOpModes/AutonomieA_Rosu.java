package org.firstinspires.ftc.teamcode.drive.userOpModes;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.drive.userOpModes.Vision.EasyOpenVision;

@Autonomous(name = "AutonomieMain")
public class AutonomieA_Rosu extends LinearOpMode {

    SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
    EasyOpenVision vision = new EasyOpenVision();
    DcMotor shooter;
    DcMotor intake;

    Servo shooterServo;

    Pose2d startPos = new Pose2d(-63, -17, Math.toRadians(0));

    public void initialize() {
        drive.setPoseEstimate(startPos);
        vision.initEasyVision(hardwareMap);

        shooter = hardwareMap.get(DcMotor.class, "shooter");
        intake = hardwareMap.get(DcMotor.class, "intake");

        shooterServo = hardwareMap.get(Servo.class, "shooterServo");
        shooterServo.setPosition(0.0);
    }

    public void shoot() {
        shooter.setPower(1);
        sleep(500);
        shooterServo.setPosition(0.25);
        sleep(200);
        shooter.setPower(0);
        shooterServo.setPosition(0);
    }

    Trajectory toCameraScan = drive.trajectoryBuilder(startPos)
            .lineTo(new Vector2d(-24, -17))
            .build();

    Trajectory toWobbleDropA = drive.trajectoryBuilder(toCameraScan.end())
            .splineToLinearHeading(new Pose2d(0, -36), Math.toRadians(0))
            .addDisplacementMarker(() -> {
                for(int i = 1 ; i <= 3 ; i++) {
                    shoot();
                }
            })
            .splineToConstantHeading(new Vector2d(12, -60), Math.toRadians(0))
            .build();

    Trajectory toWobbleDropB = drive.trajectoryBuilder(toCameraScan.end())
            .splineToLinearHeading(new Pose2d(0, -36), Math.toRadians(0))
            .addDisplacementMarker(() -> {
                for(int i = 1 ; i <= 3 ; i++) {
                    shoot();
                }
            })
            .lineTo(new Vector2d(36, -36))
            .build();

    Trajectory toWobbleDropC = drive.trajectoryBuilder(toCameraScan.end())
            .splineToLinearHeading(new Pose2d(0, -36), Math.toRadians(0))
            .addDisplacementMarker(() -> {
                for(int i = 1 ; i <= 3 ; i++) {
                    shoot();
                }
            })
            .splineToConstantHeading(new Vector2d(60, -60), Math.toRadians(0))
            .build();

    Trajectory toParkingSpotA = drive.trajectoryBuilder(toWobbleDropA.end().plus(new Pose2d(0, 0, Math.toRadians(180))))
            .lineToLinearHeading(new Pose2d(0, 0, Math.toRadians(0)))
            .build();

    Trajectory toParkingSpotB = drive.trajectoryBuilder(toWobbleDropB.end().plus(new Pose2d(0, 0, Math.toRadians(180))))
            .lineToLinearHeading(new Pose2d(0, 0, Math.toRadians(0)))
            .build();

    Trajectory toParkingSpotC = drive.trajectoryBuilder(toWobbleDropC.end().plus(new Pose2d(0, 0, Math.toRadians(180))))
            .lineToLinearHeading(new Pose2d(0, 0, Math.toRadians(0)))
            .build();

    @Override
    public void runOpMode() throws InterruptedException {
        initialize();
        telemetry.addData("Status", "initialized");
        waitForStart();

        drive.followTrajectory(toCameraScan);
        int position = vision.getDetectedPosition();

        if(position == 0) {
            drive.followTrajectory(toWobbleDropA);
        } else if(position == 1) {
            drive.followTrajectory(toWobbleDropB);
        } else if(position == 4) {
            drive.followTrajectory(toWobbleDropC);
        }

        drive.turn(180);

        if(position == 0) {
            drive.followTrajectory(toParkingSpotA);
        } else if(position == 1) {
            drive.followTrajectory(toParkingSpotB);
        } else if(position == 4) {
            drive.followTrajectory(toParkingSpotC);
        }
    }
}
