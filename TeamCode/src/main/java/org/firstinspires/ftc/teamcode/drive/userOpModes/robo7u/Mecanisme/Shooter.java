package org.firstinspires.ftc.teamcode.drive.userOpModes.robo7u.Mecanisme;

import com.qualcomm.robotcore.hardware.HardwareMap;

public class Shooter {

    HardwareMap hwMap;
    public ShooterLift shooterLift;
    public Launcher launcher;

    public Shooter(HardwareMap ahwMap) {
        hwMap = ahwMap;
        shooterLift = new ShooterLift(hwMap);
        launcher = new Launcher(hwMap);
    }
}
