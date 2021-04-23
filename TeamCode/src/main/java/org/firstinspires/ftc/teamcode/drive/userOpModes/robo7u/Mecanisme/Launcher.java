package org.firstinspires.ftc.teamcode.drive.userOpModes.robo7u.Mecanisme;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Launcher {

    HardwareMap hwMap;
    Servo trigger;
    DcMotor flywheel;

    double retractedPosition = 0.47;
    double shootPosition = 0.65;

    double highGoalCap = 1;
    double powerShotCap = 0.85;

    /**
     * Constructor pentru launcher, apelat de clasa shooter la initializarea
     * modului de operare
     * @param ahwMap hardwaremap-ul modului de operare
     */
    public Launcher(HardwareMap ahwMap) {
        hwMap = ahwMap;
        trigger = hwMap.get(Servo.class, "trigger");
        trigger.setPosition(retractedPosition);

        flywheel = hwMap.get(DcMotor.class, "flywheel");
        flywheel.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        flywheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        flywheel.setDirection(DcMotorSimple.Direction.FORWARD);
    }


    /**
     * Functie pentru shooting automat
     * @param runtime runtime-ul din modul de operare
     */
    public void shoot(ElapsedTime runtime) {

        double runtimeActual = runtime.milliseconds();

        trigger.setPosition(shootPosition);

        while(runtime.milliseconds() - runtimeActual < 100);

        trigger.setPosition(retractedPosition);
    }


    /**
     * Functie pentru mutarea manuala a shooter-ului
     * pe pozitia din spate
     */
    public void moveToRetractedPosition() {
        trigger.setPosition(retractedPosition);
    }


    /**
     * Functie pentru mutarea manuala a shooter-ului
     * pe pozitia de shooting
     */
    public void moveToShootingPosition() {
        trigger.setPosition(shootPosition);
    }

    /**
     * Functie pentru operare flywheel in sens normal
     */
    public void powerFlywheel(boolean powershots) {
        flywheel.setPower(powershots ? powerShotCap : highGoalCap);
    }

    /**
     * Functie pentru operare flywheel in sens opus
     */
    public void reversePowerFlywheel(boolean powershots) {
        flywheel.setPower(powershots ? -powerShotCap : -highGoalCap);
    }

    /**
     * Functie pentru oprire flyhweel
     */
    public void stopFlywheel() {
        flywheel.setPower(0);
    }

}
