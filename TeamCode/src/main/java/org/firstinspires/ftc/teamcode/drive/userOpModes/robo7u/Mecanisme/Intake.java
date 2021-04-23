package org.firstinspires.ftc.teamcode.drive.userOpModes.robo7u.Mecanisme;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Intake {

    HardwareMap hwMap;

    DcMotor geckoMotor;
    DcMotor gearedMotor;

    public Intake(HardwareMap ahwMap) {
        hwMap = ahwMap;

        geckoMotor = hwMap.get(DcMotor.class, "geckoMotor");
        gearedMotor = hwMap.get(DcMotor.class, "gearedMotor");
    }

    /**
     * Functie care mananca covrigi
     * @param power primit de la controller
     */
    public void powerIntake(double power) {
        geckoMotor.setPower(power);
        gearedMotor.setPower(-power);
    }

    /**
     * Functie care vomita covrigi
     * @param power primit de la controller
     */
    public void powerOuttake(double power) {
        geckoMotor.setPower(-power);
        gearedMotor.setPower(power);
    }
}
