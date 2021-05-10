package org.firstinspires.ftc.teamcode.drive.userOpModes.robo7u.Autonomous;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.arcrobotics.ftclib.geometry.Rotation2d;
import com.arcrobotics.ftclib.geometry.Transform2d;
import com.arcrobotics.ftclib.geometry.Translation2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.spartronics4915.lib.T265Camera;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.drive.userOpModes.Vision.EasyOpenVision;
import org.firstinspires.ftc.teamcode.drive.userOpModes.robo7u.Mecanisme.Arm;
import org.firstinspires.ftc.teamcode.drive.userOpModes.robo7u.Mecanisme.Intake;
import org.firstinspires.ftc.teamcode.drive.userOpModes.robo7u.Mecanisme.Shooter;

@Autonomous(name = "AutonomieCentered")
public class AutonomieCentered extends LinearOpMode {

    ElapsedTime runtime;

    //variabile ajutatoare
    boolean isPoseSet;

    //pozitia detectata la randomizare
    int rings = 0;

    //Sasiu + mecanisme
    SampleMecanumDrive drive;
    Intake intake;
    Arm arm;
    Shooter shooter;

    //Camera Intel
    private static T265Camera slamra = null;
    Translation2d translation;
    Rotation2d rotation;
    T265Camera.CameraUpdate up;

    //pozitia de start
    Pose2d startPos;

    public void initialize() {
        //initializam runtime-ul
        runtime = new ElapsedTime();

        //constante
        final double INCH_TO_METERS = 0.0254;

        //initializam sasiul
        startPos = new Pose2d(-63, -15, Math.toRadians(0));
        drive = new SampleMecanumDrive(hardwareMap);
        drive.setPoseEstimate(startPos);

        //initializam camera de detectie
        EasyOpenVision.initEasyVision(hardwareMap);

        //initializam camera intel
        isPoseSet = false;
        if(slamra == null) {
            //initializam camera si comunicam pozitia centrului camerei fata de centrul robotului
            slamra = new T265Camera(new Transform2d(new Translation2d(-0.225, 0), new Rotation2d(0)), 0, hardwareMap.appContext);

            while(!isPoseSet) {
                if(slamra.getLastReceivedCameraUpdate().confidence == T265Camera.PoseConfidence.High) {

                    //comunicam camerei pozitia de start pe teren
                    //DIMENSIUNILE SUNT IN METRI, PENTRU VALORI IN INCH MULTIPLICAM CU VARIABILA "INCH_TO_METERS"
                    slamra.setPose(new com.arcrobotics.ftclib.geometry.Pose2d(new Translation2d(-72 * INCH_TO_METERS, 0), new Rotation2d(0)));

                    isPoseSet = true;
                }
            }

            slamra.start();
        }

    }

    @Override
    public void runOpMode() throws InterruptedException {
        initialize();

        waitForStart();

        runtime.reset();

        //traiectorie default pentru scan
        drive.followTrajectory(traj_detectie);

        rings = EasyOpenVision.getDetectedPosition();

        switch(rings) {
            default:
            case 0:
                //mergem de la scanare la zona de drop si lasam wobble-ul
                drive.followTrajectory(traj01);
                arm.extendAndDropWobble();

                //mergem spre al doilea wobble si il luam pe robot
                drive.followTrajectory(traj02);
                arm.takeWobbleAndRetract();

                //mergem spre zona de drop si lasam si al doilea wobble
                drive.followTrajectory(traj03);
                arm.extendAndDropWobble();

                //rotim robotul 90 de grade pentru shooting
                drive.turn(Math.toRadians(-90));

                //tragem 3 inele
                shooter.launcher.powerFlywheel(false);
                //shooter.launcher.shoot(runtime);
                sleep(200);
                //shooter.launcher.shoot(runtime);
                sleep(200);
                //shooter.launcher.shoot(runtime);
                sleep(200);
                shooter.launcher.stopFlywheel();

                //ne deplasam pe linia alba si retragem bratul
                drive.followTrajectory(traj04);
                arm.takeWobbleAndRetract();
                break;
            case 1:
                drive.followTrajectory(traj11);
                drive.followTrajectory(traj12);
                drive.followTrajectory(traj13);
                drive.turn(Math.toRadians(-90));
                drive.followTrajectory(traj14);
                break;
            case 4:
                drive.followTrajectory(traj41);
                drive.followTrajectory(traj42);
                drive.followTrajectory(traj43);
                drive.turn(Math.toRadians(-90));
                drive.followTrajectory(traj44);
                break;
        }
    }

    //Traiectorie pentru scanare

    Trajectory traj_detectie = drive.trajectoryBuilder(startPos)
            .lineTo(new Vector2d(-24, -15))
            .build();

    //Traiectorii pentru cazul cu 0 inele

    Trajectory traj01 = drive.trajectoryBuilder(traj_detectie.end())
            .splineTo(new Vector2d(12, -60), Math.toRadians(0))
            .build();

    Trajectory traj02 = drive.trajectoryBuilder(traj01.end())
            .strafeTo(new Vector2d(-36, -48))
            .build();

    Trajectory traj03 = drive.trajectoryBuilder(traj02.end())
            .splineTo(new Vector2d(12, -36), Math.toRadians(90))
            .build();

    Trajectory traj04 = drive.trajectoryBuilder(drive.getPoseEstimate())
            .lineTo(new Vector2d(16, -36))
            .build();



    //Traiectorii pentru cazul cu 1 inel

    Trajectory traj11 = drive.trajectoryBuilder(traj_detectie.end())
            .splineTo(new Vector2d(12, -60), Math.toRadians(0))
            .build();

    Trajectory traj12 = drive.trajectoryBuilder(traj11.end())
            .strafeTo(new Vector2d(-36, -48))
            .build();

    Trajectory traj13 = drive.trajectoryBuilder(traj12.end())
            .splineTo(new Vector2d(12, -36), Math.toRadians(90))
            .build();

    Trajectory traj14 = drive.trajectoryBuilder(drive.getPoseEstimate())
            .lineTo(new Vector2d(16, -36))
            .build();



    //Traiectorii pentru cazul cu 4 inele

    Trajectory traj41 = drive.trajectoryBuilder(traj_detectie.end())
            .splineTo(new Vector2d(12, -60), Math.toRadians(0))
            .build();

    Trajectory traj42 = drive.trajectoryBuilder(traj41.end())
            .strafeTo(new Vector2d(-36, -48))
            .build();

    Trajectory traj43 = drive.trajectoryBuilder(traj42.end())
            .splineTo(new Vector2d(12, -36), Math.toRadians(90))
            .build();

    Trajectory traj44 = drive.trajectoryBuilder(drive.getPoseEstimate())
            .lineTo(new Vector2d(16, -36))
            .build();
}
