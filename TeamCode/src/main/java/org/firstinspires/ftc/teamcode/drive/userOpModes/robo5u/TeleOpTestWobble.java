package org.firstinspires.ftc.teamcode.drive.userOpModes.robo5u;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@Disabled
@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "TeleOp Test Wobble")
public class TeleOpTestWobble extends LinearOpMode {

    DcMotor wobble;

    final int TICKS_PER_REV = 2800;
    final double diameter = 3.9; //inches
    //final double circumference = Math.PI * diameter;

    public void move(double position, boolean direction) {
        wobble.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        double distance = position / 360;
        int target = (int)(distance * TICKS_PER_REV);
        double power = -0.16;

        wobble.setTargetPosition(target);

        wobble.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        if (direction) {
            wobble.setPower(-0.16);
        } else if (!direction) {
            wobble.setPower(0.16);
        }

        while(wobble.isBusy()) {
            telemetry.addData("Position", wobble.getCurrentPosition());
            telemetry.update();
        }
    }

    public void initialize() {
        wobble = hardwareMap.get(DcMotor.class, "wobble");
        wobble.setDirection(DcMotorSimple.Direction.REVERSE);
        //wobble.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //wobble.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    @Override
    public void runOpMode() throws InterruptedException {
        initialize();

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        double incStep = 0.02;
        double pow = 0.0;
        boolean nodoub = true;

        while(opModeIsActive()) {
            if(nodoub) {
                move(160, true);
            }
            nodoub = false;
            /*
            if (gamepad1.dpad_up && nodoub) {pow += incStep; nodoub = false;} else
            if (gamepad1.dpad_down && nodoub) {pow -= incStep;nodoub = false;} else {
                nodoub = true;
            }
            wobble.setPower(pow);
            sleep(100);
             */
        }
    }
}
