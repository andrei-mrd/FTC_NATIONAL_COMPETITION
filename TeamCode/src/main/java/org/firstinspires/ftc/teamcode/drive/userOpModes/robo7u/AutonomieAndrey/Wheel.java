package org.firstinspires.ftc.teamcode.drive.userOpModes.robo7u.AutonomieAndrey;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Wheel {
    HardwareMap hwMap;

    DcMotor wheelMotor;

    public Wheel(HardwareMap ahwMap) {
        hwMap = ahwMap;

        wheelMotor = hwMap.get(DcMotor.class, "wheelMotor");
    }
}
