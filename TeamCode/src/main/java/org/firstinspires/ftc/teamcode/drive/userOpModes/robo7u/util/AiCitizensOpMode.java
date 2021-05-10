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

    public ArrayList trajList;

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
            startPose = new Pose2d(-65, 12, Math.toRadians(0));
        }

        drive.setPoseEstimate(startPose);

        while(height != 5) {
            height = EasyOpenVision.getDetectedPosition();
            telemetry.addData("Inaltime: ", height);
            telemetry.addData("Status: ", "Click play to start");
            telemetry.update();
        }

        waitForStart();

        runtime.reset();

        height = EasyOpenVision.getDetectedPosition();
        EasyOpenVision.closeCamera();

        telemetry.addData("Inaltime: ", height);
        telemetry.update();

        if(culoare == Side.RED) {
            switch(height) {
                case 0:
                    runTraj(red0(), drive);
                    break;
                case 1:
                    runTraj(red1(), drive);
                    break;
                case 4:
                default:
                    runTraj(red4(), drive);
                    break;
            }
        }

        if(culoare == Side.BLUE) {
            switch(height) {
                case 0:
                    runTraj(blue0(), drive);
                    break;
                case 1:
                    runTraj(blue1(), drive);
                    break;
                case 4:
                default:
                    runTraj(blue4(), drive);
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
    public final ArrayList getTrajList() {
        return this.trajList;
    }

    public final void setTrajList(@NotNull ArrayList lista) {
        Intrinsics.checkNotNullParameter(lista, "<Trajectory>");
        this.trajList = lista;
    }

    private final void runTraj(ArrayList trajectories, SampleMecanumDrive drive) {
        Collection var3 = (Collection)trajectories;
        boolean var4 = false;
        if (!var3.isEmpty()) {
            Iterator var6 = trajectories.iterator();

            while(var6.hasNext()) {
                com.acmerobotics.roadrunner.trajectory.Trajectory trajectory = (Trajectory)var6.next();
                if (!this.opModeIsActive() || this.isStopRequested()) {
                    break;
                }

                drive.followTrajectory(trajectory);
            }
        }

    }

    @NotNull
    public ArrayList red0() {
        return this.trajList;
    }

    @NotNull
    public ArrayList red1() {
        return this.trajList;
    }

    @NotNull
    public ArrayList red4() {
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
