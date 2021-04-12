package org.firstinspires.ftc.teamcode.drive.userOpModes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.canvas.Canvas;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.arcrobotics.ftclib.geometry.Rotation2d;
import com.arcrobotics.ftclib.geometry.Transform2d;
import com.arcrobotics.ftclib.geometry.Translation2d;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.spartronics4915.lib.T265Camera;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@TeleOp(name = "T265Sample", group = "Iterative OpMode")
public class T265Sample extends OpMode {
    private static T265Camera slamra = null;
    SampleMecanumDrive drive;
    double G1Y1, G1X1, G1X2;
    double LF, LR, RF, RR;

    private final FtcDashboard dashboard = FtcDashboard.getInstance();

    @Override
    public void init() {
        drive = new SampleMecanumDrive(hardwareMap);
        drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        if(slamra == null) {
            slamra = new T265Camera(new Transform2d(new Translation2d(0.225, 0), new Rotation2d(0)), 0, hardwareMap.appContext);
        }
    }

    @Override
    public void init_loop() {

    }

    @Override
    public void start() {
        slamra.start();
    }

    public void loop() {
        G1Y1 = -gamepad1.left_stick_y;
        G1X1 = gamepad1.left_stick_x;
        G1X2 = gamepad1.right_stick_x;
        SampleMecanumDrive.leftFront.setDirection(DcMotorSimple.Direction.FORWARD);
        SampleMecanumDrive.leftRear.setDirection(DcMotorSimple.Direction.FORWARD);
        SampleMecanumDrive.rightFront.setDirection(DcMotorSimple.Direction.REVERSE);
        SampleMecanumDrive.rightRear.setDirection(DcMotorSimple.Direction.REVERSE);
        LF = G1Y1 + G1X1 - G1X2;
        LR = G1Y1 - G1X1 - G1X2;
        RF = G1Y1 - G1X1 + G1X2;
        RR = G1Y1 + G1X1 + G1X2;
        drive.setMotorPowers(LF, LR , RR, RF);

        final int robotRadius = 9;

        TelemetryPacket packet = new TelemetryPacket();
        Canvas field = packet.fieldOverlay();

        T265Camera.CameraUpdate up = slamra.getLastReceivedCameraUpdate();
        if(up == null) return;

        Translation2d translation = new Translation2d(up.pose.getTranslation().getX() / 0.0254, up.pose.getTranslation().getY() / 0.0254);
        Rotation2d rotation = up.pose.getRotation();

        field.strokeCircle(translation.getX(), translation.getY(), robotRadius);
        double arrowX = rotation.getCos() * robotRadius, arrowY = rotation.getSin() * robotRadius;
        double x1 = translation.getX() + arrowX  / 2, y1 = translation.getY() + arrowY / 2;
        double x2 = translation.getX() + arrowX, y2 = translation.getY() + arrowY;
        field.strokeLine(x1, y1, x2, y2);

        dashboard.sendTelemetryPacket(packet);
        telemetry.addData("X", translation.getX());
        telemetry.addData("Y", translation.getY());
        telemetry.addData("Heading", rotation.getDegrees());
        telemetry.update();
    }

    @Override
    public void stop() {
        slamra.stop();
    }
}
