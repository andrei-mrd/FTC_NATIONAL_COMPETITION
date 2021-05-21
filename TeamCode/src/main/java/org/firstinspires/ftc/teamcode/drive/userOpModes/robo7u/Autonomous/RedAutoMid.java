package org.firstinspires.ftc.teamcode.drive.userOpModes.robo7u.Autonomous;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.acmerobotics.roadrunner.trajectory.TrajectoryBuilder;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.drive.userOpModes.robo7u.util.AiCitizensOpMode;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Vector;

import kotlin.jvm.internal.Intrinsics;

@Autonomous(name = "BiletuAlaSigur")
public final class RedAutoMid extends AiCitizensOpMode {

    public RedAutoMid() {
        super(Side.RED, StartLine.MID);
    }

    @NotNull
    private Pose2d startPose = new Pose2d(-58.0, -30, Math.toRadians(0));

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
                .splineTo(new Vector2d(0, -14), Math.toRadians(0))
                .build();

        Trajectory shootSecond = drive.trajectoryBuilder(shootFirst.end())
                .lineTo(new Vector2d(0, 5))
                .build();

        Trajectory shootThird = drive.trajectoryBuilder(shootSecond.end())
                .lineTo(new Vector2d(0, 23))
                .build();

        Trajectory dropFirstWobble = drive.trajectoryBuilder(shootThird.end())
                .lineToSplineHeading(new Pose2d(12, -37, Math.toRadians(100)))
                .build();

        /*
        .addDisplacementMarker(() -> {
                    mechanisms.arm.closeClaw();
                    sleep(200);
                    mechanisms.arm.retract(60);
                })
                .build();
         */

        Trajectory goToSecondWobble = drive.trajectoryBuilder(dropFirstWobble.end())
                .lineToLinearHeading(new Pose2d(-76, -6, Math.toRadians(80)))
                .build();

        /*
        .addDisplacementMarker(() -> {
                    mechanisms.arm.extend(210);
                })
         */


        Trajectory getSecondWobble = drive.trajectoryBuilder(goToSecondWobble.end())
                .back(11)
                .build();

        Trajectory dropSecondWobble = drive.trajectoryBuilder(getSecondWobble.end())
                .lineToLinearHeading(new Pose2d(20, -32, Math.toRadians(80)))
                .build();

        trajList.add(shootFirst);
        trajList.add(shootSecond);
        trajList.add(shootThird);
        trajList.add(dropFirstWobble);
        trajList.add(goToSecondWobble);
        trajList.add(getSecondWobble);
        trajList.add(dropSecondWobble);

        return getTrajList();
    }

    @NotNull
    @Override
    public ArrayList red1() {
        Trajectory shootFirst = drive.trajectoryBuilder(startPose)
                .splineTo(new Vector2d(0, -14), Math.toRadians(0))
                .build();

        Trajectory shootSecond = drive.trajectoryBuilder(shootFirst.end())
                .lineTo(new Vector2d(0, 5))
                .build();

        Trajectory shootThird = drive.trajectoryBuilder(shootSecond.end())
                .lineTo(new Vector2d(0, 23))
                .build();

        Trajectory dropFirstWobble = drive.trajectoryBuilder(shootThird.end())
                .lineToSplineHeading(new Pose2d(52, -8, Math.toRadians(75)))
                .build();

        Trajectory takeRing = drive.trajectoryBuilder(dropFirstWobble.end())
                .lineToSplineHeading(new Pose2d(-20, -20, Math.toRadians(0)))
                .build();

        Trajectory pushBack = drive.trajectoryBuilder(takeRing.end())
                .lineToSplineHeading(new Pose2d(-32, -20, Math.toRadians(0)))
                .build();

        /*
        .addDisplacementMarker(() -> {
                    mechanisms.arm.closeClaw();
                    sleep(200);
                    mechanisms.arm.retract(60);
                })
                .build();
         */

        Trajectory goToSecondWobble = drive.trajectoryBuilder(pushBack.end())
                .lineToLinearHeading(new Pose2d(-67, 3, Math.toRadians(80)))
                .build();

        /*
        .addDisplacementMarker(() -> {
                    mechanisms.arm.extend(210);
                })
         */

        Trajectory getSecondWobble = drive.trajectoryBuilder(goToSecondWobble.end())
                .back(5)
                .build();

        Trajectory dropSecondWobble = drive.trajectoryBuilder(getSecondWobble.end())
                .lineToLinearHeading(new Pose2d(21, -5, Math.toRadians(160)))
                .build();

        trajList.add(shootFirst); //0
        trajList.add(shootSecond); //1
        trajList.add(shootThird); //2
        trajList.add(dropFirstWobble); //3
        trajList.add(takeRing); //4
        trajList.add(pushBack); //5
        trajList.add(goToSecondWobble); //6
        trajList.add(getSecondWobble); //7
        trajList.add(dropSecondWobble); //8

        return getTrajList();
    }

    @NotNull
    @Override
    public ArrayList red4() {
        Trajectory shootFirst = drive.trajectoryBuilder(startPose)
                .splineTo(new Vector2d(0, -14), Math.toRadians(0))
                .build();

        Trajectory shootSecond = drive.trajectoryBuilder(shootFirst.end())
                .lineTo(new Vector2d(0, 5))
                .build();

        Trajectory shootThird = drive.trajectoryBuilder(shootSecond.end())
                .lineTo(new Vector2d(0, 23))
                .build();

        Trajectory dropFirstWobble = drive.trajectoryBuilder(shootThird.end())
                .lineToSplineHeading(new Pose2d(65, -45, Math.toRadians(90)))
                .build();


        Trajectory goToSecondWobble = drive.trajectoryBuilder(dropFirstWobble.end())
                .lineToLinearHeading(new Pose2d(-25, 4, Math.toRadians(-31)))
                .build();

        Trajectory getSecondWobble = drive.trajectoryBuilder(goToSecondWobble.end())
                .back(6)
                .build();

        Trajectory dropSecondWobble = drive.trajectoryBuilder(getSecondWobble.end())
                .lineToLinearHeading(new Pose2d(60, -36, Math.toRadians(100)))
                .build();

        Trajectory park = drive.trajectoryBuilder(dropFirstWobble.end())
                .forward(30)
                .build();

        /*
        .addDisplacementMarker(() -> {
                    mechanisms.arm.closeClaw();
                    sleep(200);
                    mechanisms.arm.retract(60);
                })
                .build();
         */

        /*
        .addDisplacementMarker(() -> {
                    mechanisms.arm.extend(210);
                })
         */

        trajList.add(shootFirst); //0
        trajList.add(shootSecond); //1
        trajList.add(shootThird); //2

        trajList.add(dropFirstWobble); //3
        trajList.add(goToSecondWobble); //4
        trajList.add(getSecondWobble); //5
        trajList.add(dropSecondWobble); //6
        trajList.add(park);
        return getTrajList();
    }
}
