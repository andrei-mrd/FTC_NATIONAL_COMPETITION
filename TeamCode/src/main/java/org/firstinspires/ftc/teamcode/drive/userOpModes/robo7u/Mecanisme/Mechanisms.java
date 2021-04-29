package org.firstinspires.ftc.teamcode.drive.userOpModes.robo7u.Mecanisme;

import com.qualcomm.robotcore.hardware.HardwareMap;

public class Mechanisms {

    public Arm arm;
    public Intake intake;
    public Shooter shooter;

    HardwareMap hwMap;

    public Mechanisms(HardwareMap ahwMap) {
        hwMap = ahwMap;

        arm = new Arm(hwMap);
        intake = new Intake(hwMap);
        shooter = new Shooter(hwMap);
    }
}
