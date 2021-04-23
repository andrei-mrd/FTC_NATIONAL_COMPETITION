package org.firstinspires.ftc.teamcode.drive.userOpModes.robo5u;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.drive.userOpModes.Vision.EasyOpenVision;

@Autonomous(name = "AutonomieMainPowerShots")
public class AutonomieMainPowerShots extends LinearOpMode {

    int position = 0;

    SampleMecanumDrive drive;

    DcMotor shooter;
    //DcMotor intake;
    DcMotor arm;

    Servo shooterServo;
    Servo armServoUp;
    Servo armServoDown;

    Pose2d startPos = new Pose2d(-60, -19, Math.toRadians(0));

    final int TICKS_PER_REV = 2700;

    public void initialize() {

        drive = new SampleMecanumDrive(hardwareMap);

        drive.setPoseEstimate(startPos);
        EasyOpenVision.initEasyVision(hardwareMap);


        shooter = hardwareMap.get(DcMotor.class, "shooter");
        shooter.setDirection(DcMotorSimple.Direction.REVERSE);
        //intake = hardwareMap.get(DcMotor.class, "intake");


        arm = hardwareMap.get(DcMotor.class, "wobble");
        arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        arm.setDirection(DcMotorSimple.Direction.REVERSE);


        shooterServo = hardwareMap.get(Servo.class, "shooterServo");
        shooterServo.setPosition(0.0);


        armServoUp = hardwareMap.get(Servo.class, "wobbleServoSus");
        armServoUp.setPosition(0.0);



        armServoDown = hardwareMap.get(Servo.class, "wobbleServoJos");
        armServoDown.setPosition(1.0);
    }

    public void shoot() {
        shooter.setPower(0.85);
        sleep(2000);
        shooterServo.setPosition(1);
        sleep(500);
        shooter.setPower(0);
        shooterServo.setPosition(0);
    }

    public void move(double position, boolean clockwise) {
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        double distance = position / 360;
        int target = (int)(distance * TICKS_PER_REV);
        double power = 0.16;

        if(clockwise) {
            arm.setTargetPosition(-target);
        } else {
            arm.setTargetPosition(target);
        }

        arm.setPower(power);

        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        while(arm.isBusy()) {
            telemetry.addData("Brat", "going to position");
            telemetry.update();
        }
    }

    public void ASUPosition(boolean opened) {
        if(opened) {
            armServoUp.setPosition(1);
        } else {
            armServoUp.setPosition(0);
        }
    }

    public void ASDPosition(boolean opened) {
        if(opened) {
            armServoDown.setPosition(0);
        } else {
            armServoDown.setPosition(1);
        }
    }

    public Trajectory toCameraScan(Pose2d startPos) {
        return drive.trajectoryBuilder(startPos)
                .forward(36)
                .build();
    }

    public Trajectory toShootingSpot(Pose2d cameraScanPos) {
        return drive.trajectoryBuilder(cameraScanPos)
                .forward(24)
                .build();
    }

    public Trajectory toWobbleDrop(int position) {
        switch(position) {
            case 0:
                return drive.trajectoryBuilder(toShootingSpot(toCameraScan(startPos).end()).end())
                        .forward(30)
                        .build();

            case 1:
                return drive.trajectoryBuilder(toShootingSpot(toCameraScan(startPos).end()).end())
                        .forward(35)
                        .build();
            case 4:
            default:
                return drive.trajectoryBuilder(toShootingSpot(toCameraScan(startPos).end()).end())
                        .forward(58)
                        .build();
        }
    }

    public Trajectory leaveWobbleDrop() {
        switch(position) {
            case 0:
                return drive.trajectoryBuilder(toWobbleDrop(position).end())
                        .back(30)
                        .build();
            case 1:
                return drive.trajectoryBuilder(toWobbleDrop(position).end())
                        .back(25)
                        .build();
            default:
            case 4:
                return drive.trajectoryBuilder(toWobbleDrop(position).end())
                        .back(50)
                        .build();

        }
    }

    public Trajectory toParkingSpot(Trajectory leaveWobbleDrop) {
        switch(position) {
            case 1:
                return drive.trajectoryBuilder(leaveWobbleDrop.end())
                        .lineToLinearHeading(new Pose2d(12, -24, Math.toRadians(0)))
                        .build();
            case 4:
            default:

                return drive.trajectoryBuilder(leaveWobbleDrop.end())
                        .lineToLinearHeading(new Pose2d(12, -48, Math.toRadians(0)))
                        .build();
        }
    }

    @Override
    public void runOpMode() throws InterruptedException {

        initialize();
        telemetry.addData("Status", "initialized");
        waitForStart();

        Trajectory toPark = drive.trajectoryBuilder(new Pose2d())
                .back(23)
                .build();


        drive.followTrajectory(toCameraScan(startPos));
        position = EasyOpenVision.getDetectedPosition();

        telemetry.addData("Pozitie:", position);
        telemetry.update();

        drive.followTrajectory(toShootingSpot(toCameraScan(startPos).end()));

        drive.turn(Math.toRadians(-8));
        shoot();
        drive.turn(Math.toRadians(9));
        shoot();
        drive.turn(Math.toRadians(10));
        shoot();
        drive.turn(Math.toRadians(-30));

        switch(position) {
            case 0:
                drive.turn(Math.toRadians(-67));
                break;
            case 1:
                drive.turn(Math.toRadians(-25));
                break;
            case 4:
                drive.turn(Math.toRadians(-40));
                break;
        }

        drive.followTrajectory(toWobbleDrop(position));

        /*
        switch(position) {
            case 0:
                drive.followTrajectory(toWobbleDrop(position));
                break;
            case 1:
                drive.followTrajectory(toWobbleDrop(position));
                break;
            case 4:
                drive.followTrajectory(toWobbleDrop(position));
                break;
        }
         */

        move(135, true);
        ASUPosition(true);
        ASDPosition(true);

        switch(position) {
            case 0:
                drive.followTrajectory(leaveWobbleDrop());
                //drive.turn(Math.toRadians(67));
                move(135, false);
                ASUPosition(true);
                ASDPosition(true);
                break;
            case 1:
                drive.followTrajectory(leaveWobbleDrop());
                //drive.turn(Math.toRadians(25));
                //drive.followTrajectory(toParkingSpot(leaveWobbleDrop()));
                move(135, false);
                ASUPosition(true);
                ASDPosition(true);
                break;
            case 4:
                drive.followTrajectory(leaveWobbleDrop());
                //drive.turn(Math.toRadians(39));
                move(135, false);
                ASUPosition(true);
                ASDPosition(true);
                //drive.followTrajectory(toPark);
                //drive.followTrajectory(toParkingSpot(leaveWobbleDrop()));
                break;
        }
    }
}
