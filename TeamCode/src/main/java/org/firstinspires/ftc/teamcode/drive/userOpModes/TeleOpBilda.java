package org.firstinspires.ftc.teamcode.drive.userOpModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "TeleOpBilda")
public class TeleOpBilda extends LinearOpMode {

    DcMotor leftFront;
    DcMotor rightFront;
    DcMotor leftRear;
    DcMotor rightRear;
    DcMotor intake;

    float G1Y1, G1X1, G1X2;

    public void initialize() {

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
    }

    @Override
    public void runOpMode() {
        initialize();

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        while(opModeIsActive()) {


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
        }
    }
}