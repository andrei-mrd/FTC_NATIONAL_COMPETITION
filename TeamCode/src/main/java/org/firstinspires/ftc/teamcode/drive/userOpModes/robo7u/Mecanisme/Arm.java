package org.firstinspires.ftc.teamcode.drive.userOpModes.robo7u.Mecanisme;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Arm {

    HardwareMap hwMap;

    DcMotor armMotor;

    Servo servo1;
    Servo servo2;

    public Arm(HardwareMap ahwMap) {
        hwMap = ahwMap;

        armMotor = hwMap.get(DcMotor.class, "armMotor");
        servo1 = hwMap.get(Servo.class, "servo1");
        servo2 = hwMap.get(Servo.class, "servo2");
    }
}
