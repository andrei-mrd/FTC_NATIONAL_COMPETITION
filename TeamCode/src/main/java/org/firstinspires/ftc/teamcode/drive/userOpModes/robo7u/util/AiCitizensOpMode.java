package org.firstinspires.ftc.teamcode.drive.userOpModes.robo7u.util;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.drive.userOpModes.Vision.EasyOpenVision;
import org.firstinspires.ftc.teamcode.drive.userOpModes.robo7u.Mecanisme.Mechanisms;
import org.firstinspires.ftc.teamcode.util.t265;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import kotlin.jvm.internal.Intrinsics;

public abstract class AiCitizensOpMode extends LinearOpMode {

    public AiCitizensOpMode() {

    }

    public AiCitizensOpMode(@NotNull AiCitizensOpMode.Side culoare, @NotNull AiCitizensOpMode.StartLine linie) {
        super();
        Intrinsics.checkNotNullParameter(culoare, "culoare");
        Intrinsics.checkNotNullParameter(linie, "linie");
        this.culoare = culoare;
        this.linie = linie;
        this.runtime = new ElapsedTime();
        this.trajList = new ArrayList();
    }

    public SampleMecanumDrive drive;
    public Mechanisms mechanisms;

    Side culoare;
    StartLine linie;

    public Pose2d startPose;

    public ElapsedTime runtime;

    public ArrayList<Trajectory> trajList;

    @Override
    public void runOpMode() throws InterruptedException {

        //initializam camera intel
        t265.initialize(hardwareMap);

        //initializam camera de detectie
        EasyOpenVision.initEasyVision(hardwareMap);

        //initializam dashboard-ul
        FtcDashboard dashboard = FtcDashboard.getInstance();

        //detectam si salvam inaltimea inelelor
        int height = EasyOpenVision.getDetectedPosition();

        //initializam sasiul si mecanismele
        mechanisms = new Mechanisms(hardwareMap);
        drive = new SampleMecanumDrive(hardwareMap);

        mechanisms.arm.openClaw();

        if(culoare == Side.RED) {
            startPose = new Pose2d(-63, -30, Math.toRadians(0));
        }

        drive.setPoseEstimate(startPose);
        /*
        while(height != 5) {
            telemetry.addData("inaltime", height);
            telemetry.update();
        }
        */

/*
        while(height != 5) {
            height = EasyOpenVision.getDetectedPosition();
            telemetry.addData("Inaltime: ", height);
            telemetry.addData("Status: ", "Click play to start");
            telemetry.update();
        }
*/
        waitForStart();

        runtime.reset();

        height = EasyOpenVision.getDetectedPosition();
        //height = 0;
        EasyOpenVision.closeCamera();

        telemetry.addData("Inaltime: ", height);
        telemetry.update();

        if(culoare == Side.RED) {
            switch(height) {
                case 0:
                    runTraj0(red0(), drive);
                    break;
                case 1:
                    runTraj1(red1(), drive);
                    break;
                case 4:
                default:
                    runTraj4(red4(), drive);
                    break;
            }
        }

        if(culoare == Side.BLUE) {
            switch(height) {
                case 0:
                    runTraj0(blue0(), drive);
                    break;
                case 1:
                    runTraj1(blue1(), drive);
                    break;
                case 4:
                default:
                    runTraj4(blue4(), drive);
                    break;
            }
        }

    }

    @NotNull
    public Pose2d getStartPose() {
        Pose2d pozitie = this.startPose;
        if (pozitie == null) {
            Intrinsics.throwUninitializedPropertyAccessException("startPose");
        }

        return pozitie;
    }

    public void setStartPose(@NotNull Pose2d pozitie) {
        Intrinsics.checkNotNullParameter(pozitie, "<Trajectory>");
        this.startPose = pozitie;
    }

    @NotNull
    public final ElapsedTime getCurrentTime() {
        return this.runtime;
    }

    @NotNull
    public final ArrayList<Trajectory> getTrajList() {
        return this.trajList;
    }

    public final void setTrajList(@NotNull ArrayList lista) {
        Intrinsics.checkNotNullParameter(lista, "<Trajectory>");
        this.trajList = lista;
    }

    private final void runTraj4(ArrayList<Trajectory> trajectories, SampleMecanumDrive drive) {
        Collection var3 = (Collection)trajectories;
        boolean var4 = false;
        if (!var3.isEmpty()) {
            Iterator var6 = trajectories.iterator();

            int counter = 0;

            while(var6.hasNext()) {
                com.acmerobotics.roadrunner.trajectory.Trajectory trajectory = (Trajectory)var6.next();
                if (!this.opModeIsActive() || this.isStopRequested()) {
                    break;
                }

                if(counter == 0) {
                    mechanisms.shooter.launcher.powerFlywheel(true);
                }

                drive.followTrajectory(trajectory);

                if(counter == 0) {
                    mechanisms.shooter.launcher.moveToShootingPosition();
                    sleep(250);
                    mechanisms.shooter.launcher.moveToRetractedPosition();
                }

                if(counter == 1) {
                    mechanisms.shooter.launcher.moveToShootingPosition();
                    sleep(250);
                    mechanisms.shooter.launcher.moveToRetractedPosition();
                }

                if(counter == 2) {
                    mechanisms.shooter.launcher.moveToShootingPosition();
                    sleep(250);
                    mechanisms.shooter.launcher.moveToRetractedPosition();
                    mechanisms.shooter.launcher.stopFlywheel();
                }

                if(counter == 3) {
                    mechanisms.arm.extend(170);
                    mechanisms.arm.closeClaw();
                    mechanisms.arm.retract(60);
                }

                if(counter == 4)  {
                    mechanisms.arm.extend(80);
                }

                if(counter == 5) {
                    mechanisms.arm.openClaw();
                    sleep(400);
                    mechanisms.arm.retract(60);
                }

                if(counter == 6) {
                    mechanisms.arm.closeClaw();
                    mechanisms.arm.retract(80);
                }

                counter++;
            }
        }

    }

    private final void runTraj1(ArrayList<Trajectory> trajectories, SampleMecanumDrive drive) {
        Collection var3 = (Collection)trajectories;
        boolean var4 = false;
        if (!var3.isEmpty()) {
            Iterator var6 = trajectories.iterator();

            int counter = 0;

            while(var6.hasNext()) {
                com.acmerobotics.roadrunner.trajectory.Trajectory trajectory = (Trajectory)var6.next();
                if (!this.opModeIsActive() || this.isStopRequested()) {
                    break;
                }

                if(counter == 0) {
                    mechanisms.shooter.launcher.powerFlywheel(true);
                }

                if(counter == 3) {
                    mechanisms.arm.extend(150);
                }

                if(counter == 4) {
                    mechanisms.intake.powerOuttake(1);
                    mechanisms.shooter.launcher.powerFlywheel(true);
                    mechanisms.arm.extend(30);
                }

                drive.followTrajectory(trajectory);

                if(counter == 0) {
                    mechanisms.shooter.launcher.moveToShootingPosition();
                    sleep(250);
                    mechanisms.shooter.launcher.moveToRetractedPosition();
                }

                if(counter == 1) {
                    mechanisms.shooter.launcher.moveToShootingPosition();
                    sleep(250);
                    mechanisms.shooter.launcher.moveToRetractedPosition();
                }

                if(counter == 2) {
                    mechanisms.shooter.launcher.moveToShootingPosition();
                    sleep(250);
                    mechanisms.shooter.launcher.moveToRetractedPosition();
                    mechanisms.shooter.launcher.stopFlywheel();
                }

                if(counter == 3) {
                    mechanisms.arm.closeClaw();
                }

                if(counter == 5) {
                    sleep(650);
                    mechanisms.intake.stopIntake();
                    mechanisms.shooter.launcher.moveToShootingPosition();
                    sleep(200);
                    mechanisms.shooter.launcher.moveToRetractedPosition();
                    mechanisms.shooter.launcher.stopFlywheel();
                }

                if(counter == 6) {
                    mechanisms.arm.extend(10);
                }

                if(counter == 7) {
                    mechanisms.arm.openClaw();
                    sleep(200);
                    mechanisms.arm.retract(60);
                }

                if(counter == 8) {
                    mechanisms.arm.closeClaw();
                    sleep(400);
                    mechanisms.arm.retract(50);
                }

                counter++;
            }
        }

    }

    private final void runTraj0(ArrayList<Trajectory> trajectories, SampleMecanumDrive drive) {
        Collection var3 = (Collection)trajectories;
        boolean var4 = false;
        int counter = 0;
        if (!var3.isEmpty()) {
            Iterator var6 = trajectories.iterator();

            while(var6.hasNext()) {

                com.acmerobotics.roadrunner.trajectory.Trajectory trajectory = (Trajectory)var6.next();
                if (!this.opModeIsActive() || this.isStopRequested()) {
                    break;
                }

                if(counter == 0) {
                    mechanisms.shooter.launcher.powerFlywheel(true);
                }

                if(counter == 3) {
                    mechanisms.arm.extend(170);
                }

                drive.followTrajectory(trajectory);

                if(counter == 0) {
                    mechanisms.shooter.launcher.moveToShootingPosition();
                    sleep(250);
                    mechanisms.shooter.launcher.moveToRetractedPosition();
                }

                if(counter == 1) {
                    mechanisms.shooter.launcher.moveToShootingPosition();
                    sleep(250);
                    mechanisms.shooter.launcher.moveToRetractedPosition();
                }

                if(counter == 2) {
                    mechanisms.shooter.launcher.moveToShootingPosition();
                    sleep(250);
                    mechanisms.shooter.launcher.moveToRetractedPosition();
                    mechanisms.shooter.launcher.stopFlywheel();
                }

                if(counter == 3) {
                    mechanisms.arm.closeClaw();
                }

                if(counter == 4) {
                    mechanisms.arm.extend(20);
                }

                if(counter == 5) {
                    mechanisms.arm.openClaw();
                    sleep(200);
                    mechanisms.arm.retract(30);
                }

                if(counter == 6) {
                    mechanisms.arm.closeClaw();
                    sleep(400);
                    mechanisms.arm.retract(80);
                }

                counter++;
            }
        }

    }

    @NotNull
    public ArrayList<Trajectory> red0() {
        return this.trajList;
    }

    @NotNull
    public ArrayList<Trajectory> red1() {
        return this.trajList;
    }

    @NotNull
    public ArrayList<Trajectory> red4() {
        return this.trajList;
    }


    @NotNull
    public ArrayList blue0() {
        return this.trajList;
    }

    @NotNull
    public ArrayList blue1() {
        return this.trajList;
    }

    @NotNull
    public ArrayList blue4() {
        return this.trajList;
    }

    @NotNull
    public final AiCitizensOpMode.Side getSide() {
        return this.culoare;
    }

    public enum Side {
        RED,
        BLUE
    }

    public enum StartLine {
        MID,
        EXT
    }
}
