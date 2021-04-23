package org.firstinspires.ftc.teamcode.drive.userOpModes.robo5u;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class intake extends LinearOpMode {
    DcMotor shooter;
    DcMotor intake;
    public void initialize() {
        shooter = hardwareMap.get(DcMotor.class, "shooter");
        intake = hardwareMap.get(DcMotor.class, "intake");

        shooter.setDirection(DcMotorSimple.Direction.REVERSE);
    }
    @Override
    public void runOpMode() throws InterruptedException {
        initialize();
        telemetry.addData("Status", "initialize");
        waitForStart();

        while(opModeIsActive()) {
            if(gamepad1.a) {
                intake.setPower(-gamepad1.left_trigger);
            }
            shooter.setPower(gamepad1.right_trigger);
            intake.setPower(gamepad1.left_trigger);
        }
    }
}
