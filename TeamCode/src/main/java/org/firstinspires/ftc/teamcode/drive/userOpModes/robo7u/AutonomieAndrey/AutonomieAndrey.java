package org.firstinspires.ftc.teamcode.drive.userOpModes.robo7u.AutonomieAndrey;

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

@Autonomous(name = "AutonomieAndreyIncercare")
public class AutonomieAndrey extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        int startpos = 0;

        SampleMecanumDrive drive;

        Pose2d startPos = new Pose2d(-63, -15, Math.toRadians(0));

        //Shooter

        public void intialize() {

            drive = new SampleMecanumDrive(hardwareMap);
            drive.setPoseEstimate(startPos);
            EasyOpenVision.initEasyVision(hardwareMap);

        }

    }
}
