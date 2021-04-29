package org.firstinspires.ftc.teamcode.drive.userOpModes.robo7u.Autonomous;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.acmerobotics.roadrunner.trajectory.TrajectoryBuilder;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.drive.userOpModes.robo7u.util.AiCitizensOpMode;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import kotlin.jvm.internal.Intrinsics;

@Autonomous(name = "RedAutoMid")
public final class RedAutoMid extends AiCitizensOpMode {

    public RedAutoMid() {
        super(Side.RED, StartLine.MID);
    }

    @NotNull
    private Pose2d startPose = new Pose2d(-63.0, -12, Math.toRadians(180));

    @NotNull
    public Pose2d getStartPose() {
        return this.startPose;
    }

    public void setStartPose(@NotNull Pose2d var1) {
        Intrinsics.checkNotNullParameter(var1, "<set-?>");
        this.startPose = var1;
    }

    @NotNull
    @Override
    public ArrayList red0() {
        Trajectory shootFirst = drive.trajectoryBuilder(startPose)
                .addDisplacementMarker(() -> {
                    mechanisms.shooter.launcher.powerFlywheel(true);
                })
                .lineTo(new Vector2d(-16, 18))
                .addDisplacementMarker(() -> {
                    mechanisms.shooter.launcher.shoot(runtime);
                })
                .build();

        Trajectory shootSecond = drive.trajectoryBuilder(shootFirst.end())
                .build();

        Trajectory shootThird = drive.trajectoryBuilder(shootSecond.end())
                .build();

        Trajectory dropFirstWobble = drive.trajectoryBuilder(shootThird.end())
                .build();;

        Trajectory moveBack = drive.trajectoryBuilder(dropFirstWobble.end())
                .build();;

        Trajectory getSecondWobble = drive.trajectoryBuilder(moveBack.end())
                .build();;

        Trajectory dropSecondWobble = drive.trajectoryBuilder(getSecondWobble.end())
                .build();;

        Trajectory moveBack2 = drive.trajectoryBuilder(dropSecondWobble.end())
                .build();;

        trajList.add(shootFirst);
        trajList.add(shootSecond);
        trajList.add(shootThird);
        trajList.add(dropFirstWobble);
        trajList.add(moveBack);
        trajList.add(getSecondWobble);
        trajList.add(dropSecondWobble);
        trajList.add(moveBack2);

        return getTrajList();
    }

    @NotNull
    @Override
    public ArrayList red1() {
        Trajectory shootFirst = drive.trajectoryBuilder(startPose)
                .addDisplacementMarker(() -> {
                    mechanisms.shooter.launcher.powerFlywheel(true);
                })
                .lineTo(new Vector2d(-16, 18))
                .addDisplacementMarker(() -> {
                    mechanisms.shooter.launcher.shoot(runtime);
                })
                .build();

        Trajectory shootSecond = drive.trajectoryBuilder(shootFirst.end())
                .build();

        Trajectory shootThird = drive.trajectoryBuilder(shootSecond.end())
                .build();

        Trajectory dropFirstWobble = drive.trajectoryBuilder(shootThird.end())
                .build();;

        Trajectory moveBack = drive.trajectoryBuilder(dropFirstWobble.end())
                .build();;

        Trajectory getSecondWobble = drive.trajectoryBuilder(moveBack.end())
                .build();;

        Trajectory dropSecondWobble = drive.trajectoryBuilder(getSecondWobble.end())
                .build();;

        Trajectory moveBack2 = drive.trajectoryBuilder(dropSecondWobble.end())
                .build();;

        trajList.add(shootFirst);
        trajList.add(shootSecond);
        trajList.add(shootThird);
        trajList.add(dropFirstWobble);
        trajList.add(moveBack);
        trajList.add(getSecondWobble);
        trajList.add(dropSecondWobble);
        trajList.add(moveBack2);

        return getTrajList();
    }

    @NotNull
    @Override
    public ArrayList red4() {
        Trajectory shootFirst = drive.trajectoryBuilder(startPose)
                .addDisplacementMarker(() -> {
                    mechanisms.shooter.launcher.powerFlywheel(true);
                })
                .lineTo(new Vector2d(-16, 18))
                .addDisplacementMarker(() -> {
                    mechanisms.shooter.launcher.shoot(runtime);
                })
                .build();

        Trajectory shootSecond = drive.trajectoryBuilder(shootFirst.end())
                .build();

        Trajectory shootThird = drive.trajectoryBuilder(shootSecond.end())
                .build();

        Trajectory dropFirstWobble = drive.trajectoryBuilder(shootThird.end())
                .build();;

        Trajectory moveBack = drive.trajectoryBuilder(dropFirstWobble.end())
                .build();;

        Trajectory getSecondWobble = drive.trajectoryBuilder(moveBack.end())
                .build();;

        Trajectory dropSecondWobble = drive.trajectoryBuilder(getSecondWobble.end())
                .build();;

        Trajectory moveBack2 = drive.trajectoryBuilder(dropSecondWobble.end())
                .build();;

        trajList.add(shootFirst);
        trajList.add(shootSecond);
        trajList.add(shootThird);
        trajList.add(dropFirstWobble);
        trajList.add(moveBack);
        trajList.add(getSecondWobble);
        trajList.add(dropSecondWobble);
        trajList.add(moveBack2);

        return getTrajList();
    }
}
