package org.firstinspires.ftc.teamcode.drive.userOpModes.robo7u.Mecanisme;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Launcher {
    HardwareMap hwMap;
    Servo launcher;

    double retractedPosition = 0.47;
    double shootPosition = 0.65;

    public Launcher(HardwareMap ahwMap) {
        hwMap = ahwMap;
        launcher = hwMap.get(Servo.class, "launchServo");
        launcher.setPosition(retractedPosition);
    }

    public void shoot(ElapsedTime runtime) {

        double runtimeActual = runtime.milliseconds();

        launcher.setPosition(shootPosition);

        while(runtime.milliseconds() - runtimeActual < 100);

        launcher.setPosition(retractedPosition);
    }
}
