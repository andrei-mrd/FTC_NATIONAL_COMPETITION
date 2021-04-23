package org.firstinspires.ftc.teamcode.drive.userOpModes.robo7u.Mecanisme;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.drive.userOpModes.robo7u.AutonomieAndrey.Wheel;

public class Shooter1 {

    HardwareMap hwMap;
    public ShooterLift shooterLift;
    public Launcher launcher;
    public Wheel wheel;

    public Shooter1(HardwareMap ahwMap) {
        hwMap = ahwMap;
        shooterLift = new ShooterLift(hwMap);
        launcher = new Launcher(hwMap);
        wheel = new Wheel(hwMap);
    }
}
