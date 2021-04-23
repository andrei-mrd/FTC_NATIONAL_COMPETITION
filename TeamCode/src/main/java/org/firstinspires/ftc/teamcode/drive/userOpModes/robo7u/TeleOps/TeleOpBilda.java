package org.firstinspires.ftc.teamcode.drive.userOpModes.robo7u.TeleOps;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.canvas.Canvas;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.arcrobotics.ftclib.geometry.Pose2d;
import com.arcrobotics.ftclib.geometry.Rotation2d;
import com.arcrobotics.ftclib.geometry.Transform2d;
import com.arcrobotics.ftclib.geometry.Translation2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.spartronics4915.lib.T265Camera;

@TeleOp(name = "TeleOpBilda")
public class TeleOpBilda extends LinearOpMode {

    private static T265Camera slamra = null;
    private final FtcDashboard dashboard = FtcDashboard.getInstance();

    ElapsedTime runtime = new ElapsedTime();
    double runtimeActual = -2000;

    //global variables
    double powerCap = 1;
    double positionLeft, positionRight, launchPosition;
    double lowestPositionLeft = 0, highestPositionLeft = 0.25;
    double lowestPositionRight = 0, highestPositionRight = 0.25;
    final double INCH_TO_METERS = 0.0254;
    final int robotRadius = 9;

    //sasiu
    DcMotor leftFront;
    DcMotor rightFront;
    DcMotor leftRear;
    DcMotor rightRear;

    //intake
    DcMotor intake;

    //shooter
    DcMotorEx shooter;

    //shooter servos
    Servo servoLeft;
    Servo servoRight;
    Servo launchServo;

    float G1Y1, G1X1, G1X2;

    public void init_t265() {
        if(slamra == null) {
            //initializam camera si comunicam pozitia centrului camerei fata de centrul robotului
            slamra = new T265Camera(new Transform2d(new Translation2d(-0.225, 0), new Rotation2d(0)), 0, hardwareMap.appContext);
            //comunicam camerei pozitia de start pe teren
            //DIMENSIUNILE SUNT IN METRI, PENTRU VALORI IN INCH MULTIPLICAM CU VARIABILA "INCH_TO_METERS"
            slamra.setPose(new Pose2d(new Translation2d(-72 * INCH_TO_METERS, 0), new Rotation2d(0)));
        }
    }

    /**
     * Functie apelata la finalul initializarii pentru
     * a reseta shooter-ul pe pozitia de start
     */
    public void resetShooterPosition() {
        positionLeft = 0;
        servoLeft.setPosition(positionLeft);
        positionRight = 0;
        servoRight.setPosition(positionRight);
    }

    /**
     * Functie ce muta shooter-ul din pozitia actuala
     * in pozitia de inaltime minima setata
     */
    public void moveToLowestPosition() {
        positionLeft = lowestPositionLeft;
        positionRight = lowestPositionRight;
        servoLeft.setPosition(positionLeft);
        servoRight.setPosition(positionRight);
    }

    /**
     * Functie ce muta shooter-ul din pozitia actuala
     * in pozitia de inaltime maxima setata
     */
    public void moveToHighestPosition() {
        positionLeft = highestPositionLeft;
        positionRight = highestPositionRight;
        servoLeft.setPosition(positionLeft);
        servoRight.setPosition(positionRight);
    }

    /**
     * Functie ce coboara shooter-ul 0.05 unitati
     */
    public void lowerPosition() {
        positionLeft -= 0.05;
        positionRight -= 0.05;
        servoLeft.setPosition(positionLeft);
        servoRight.setPosition(positionRight);
    }

    /**
     * Functie ce ridica shooter-ul 0.05 unitati
     */
    public void raisePosition() {
        positionLeft += 0.05;
        positionRight += 0.05;
        servoLeft.setPosition(positionLeft);
        servoRight.setPosition(positionRight);
    }

    /**
     * Functie apelata la finalul initializarii pentru a readuce servo-ul de lansare pe pozitia de start
     */
    public void resetLaunchPosition() {
        launchPosition = 0.46;
        launchServo.setPosition(launchPosition);
    }


    /**
     * Functie apelata inainte de inceperea opmode-ului pentru a initializa si asigura functionarea in parametri ok
     * a tuturor componentelor de pe robot
     */
    public void initialize() {

        shooter = hardwareMap.get(DcMotorEx.class, "shooter");

        servoLeft = hardwareMap.get(Servo.class, "servoLeft");
        servoLeft.setDirection(Servo.Direction.FORWARD);
        servoRight = hardwareMap.get(Servo.class, "servoRight");
        servoRight.setDirection(Servo.Direction.REVERSE);

        launchServo = hardwareMap.get(Servo.class, "launchServo");

        leftFront = hardwareMap.get(DcMotor.class, "leftFront");
        rightFront = hardwareMap.get(DcMotor.class, "rightFront");
        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
        leftRear = hardwareMap.get(DcMotor.class, "leftRear");
        rightRear = hardwareMap.get(DcMotor.class, "rightRear");
        leftRear.setDirection(DcMotorSimple.Direction.REVERSE);

        intake = hardwareMap.get(DcMotor.class, "intake");

        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftRear.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightRear.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        resetShooterPosition();
        resetLaunchPosition();
    }

    @Override
    public void runOpMode() {
        initialize();

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        runtime.reset();

        while(opModeIsActive()) {

            if(gamepad1.y) {
                highestPositionLeft = positionLeft;
                highestPositionRight = positionRight;
            } else if(gamepad1.x) {
                lowestPositionLeft = positionLeft;
                lowestPositionRight = positionRight;
            }

            if(gamepad1.a && (runtime.milliseconds() - runtimeActual > 100)) {
                launchPosition = 0.47;
                launchServo.setPosition(launchPosition);
                runtimeActual = runtime.milliseconds();
            } else if(gamepad1.b && (runtime.milliseconds() - runtimeActual > 100)) {
                launchPosition = 0.65;
                launchServo.setPosition(launchPosition);
                runtimeActual = runtime.milliseconds();
            }

            if(gamepad1.dpad_down && (runtime.milliseconds() - runtimeActual > 500)) {
                lowerPosition();
                runtimeActual = runtime.milliseconds();
            } else if(gamepad1.dpad_up && (runtime.milliseconds() - runtimeActual > 500)) {
                raisePosition();
                runtimeActual = runtime.milliseconds();
            }

            if(gamepad1.left_bumper && (runtime.milliseconds() - runtimeActual > 500)) {
                moveToLowestPosition();
                runtimeActual = runtime.milliseconds();
            } else if(gamepad1.right_bumper && (runtime.milliseconds() - runtimeActual > 500)) {
                moveToHighestPosition();
                runtimeActual = runtime.milliseconds();
            }

            if(gamepad1.left_trigger > 0) {
                shooter.setPower(powerCap);
            } else if(gamepad1.right_trigger > 0) {
                shooter.setPower(-powerCap);
            } else {
                shooter.setPower(0);
            }

            G1Y1 = -gamepad1.left_stick_y;
            G1X1 = gamepad1.left_stick_x;
            G1X2 = gamepad1.right_stick_x;

            leftRear.setDirection(DcMotorSimple.Direction.REVERSE);
            leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
            rightRear.setDirection(DcMotorSimple.Direction.FORWARD);
            rightFront.setDirection(DcMotorSimple.Direction.FORWARD);
            leftFront.setPower(G1Y1 + G1X1 + G1X2);
            leftRear.setPower(G1Y1 - G1X1 + G1X2);
            rightFront.setPower(G1Y1 - G1X1 - G1X2);
            rightRear.setPower(G1Y1 + G1X1 - G1X2);

            if(gamepad1.left_trigger > 0) {
                intake.setPower(gamepad1.left_trigger);
            } else if(gamepad1.right_trigger > 0){
                intake.setPower(-gamepad1.right_trigger);
            }

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
            telemetry.addData("PozitieLeft ", servoLeft.getPosition());
            telemetry.addData("PozitieRight ", servoRight.getPosition());
            telemetry.addData("LaunchPos ", launchServo.getPosition());
            telemetry.update();
        }
    }
}