package org.firstinspires.ftc.teamcode.drive.userOpModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "Shooter")
public class shooter extends LinearOpMode {
    DcMotor leftFront;
    DcMotor rightFront;
    DcMotor leftRear;
    DcMotor rightRear;
    DcMotor shooter;
    DcMotor intake;
    Servo wobbleServoSus;
    Servo wobbleServoJos;
    public void initialize() {
        //leftFront = hardwareMap.get(DcMotor.class, "leftFront");
        //rightFront = hardwareMap.get(DcMotor.class, "rightFront");
        //leftRear = hardwareMap.get(DcMotor.class, "leftRear");
        //rightRear = hardwareMap.get(DcMotor.class, "rightRear");
        shooter = hardwareMap.get(DcMotor.class, "shooter");
        intake = hardwareMap.get(DcMotor.class, "intake");

        wobbleServoSus = hardwareMap.get(Servo.class, "wobbleServoSus");
        wobbleServoJos = hardwareMap.get(Servo.class, "wobbleServoJos");

        shooter.setDirection(DcMotorSimple.Direction.REVERSE);
    }
    @Override
    public void runOpMode() throws InterruptedException {
        initialize();
        telemetry.addData("Status", "initialize");
        waitForStart();
        while(opModeIsActive()) {
            int directie = 1;
            double power1 = gamepad1.left_trigger;
            double power2 = gamepad1.right_trigger;
            //shooter.setPower(power2);

            if(power1 > 0) {
                intake.setPower(power1);
            } else if(power2 > 0){
                intake.setPower(-power2);
            } else {
                intake.setPower(0);
            }


            if(gamepad1.a) {
                directie = 2;
            }

            if(gamepad1.x) {
                directie = 1;
            }

            if(directie == 1) {
                wobbleServoSus.setPosition(1);
                wobbleServoJos.setPosition(0);
            } else if(directie == 2) {
                wobbleServoSus.setPosition(0);
                wobbleServoJos.setPosition(1);
            }

            telemetry.addData("Directie", directie);
            telemetry.update();
        }
    }
}
