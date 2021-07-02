package org.firstinspires.ftc.teamcode.util;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.localization.Localizer;
import com.arcrobotics.ftclib.geometry.Rotation2d;
import com.qualcomm.robotcore.hardware.HardwareMap;
import kotlin.Metadata;
import kotlin.NotImplementedError;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
        mv = {1, 1, 16},
        bv = {1, 0, 3},
        k = 1,
        xi = 2,
        d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\u000f\u001a\u00020\u0010H\u0016R$\u0010\b\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\u00058V@VX\u0096\u000e¢\u0006\f\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u0016\u0010\r\u001a\u0004\u0018\u00010\u00058VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\n¨\u0006\u0011"},
        d2 = {"Lorg/firstinspires/ftc/teamcode/util/IntelLocalizer;", "Lcom/acmerobotics/roadrunner/localization/Localizer;", "hardwareMap", "Lcom/qualcomm/robotcore/hardware/HardwareMap;", "startPose2d", "Lcom/acmerobotics/roadrunner/geometry/Pose2d;", "(Lcom/qualcomm/robotcore/hardware/HardwareMap;Lcom/acmerobotics/roadrunner/geometry/Pose2d;)V", "value", "poseEstimate", "getPoseEstimate", "()Lcom/acmerobotics/roadrunner/geometry/Pose2d;", "setPoseEstimate", "(Lcom/acmerobotics/roadrunner/geometry/Pose2d;)V", "poseVelocity", "getPoseVelocity", "update", "", "TeamCode_debug"}
)
public final class IntelLocalizer implements Localizer {

    @NotNull
    public Pose2d getPoseEstimate() {
        return t265.INSTANCE.getPose();
    }

    public void setPoseEstimate(@NotNull Pose2d value) {
        Intrinsics.checkNotNullParameter(value, "value");
        t265.INSTANCE.getCam().setPose(new com.arcrobotics.ftclib.geometry.Pose2d(value.getX(), value.getY(), new Rotation2d(value.getHeading())));
    }

    @Nullable
    public Pose2d getPoseVelocity() {
        String var1 = "Not yet implemented";
        boolean var2 = false;
        try {
            throw (Throwable)(new NotImplementedError("An operation is not implemented: " + var1));
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        return new Pose2d();
    }

    public void update() {
        t265.INSTANCE.update();
    }

    public IntelLocalizer(@NotNull HardwareMap hardwareMap, @NotNull Pose2d startPose2d) {
        super();
        //t265.initialize(hardwareMap);
        Intrinsics.checkNotNullParameter(hardwareMap, "hardwareMap");
        Intrinsics.checkNotNullParameter(startPose2d, "startPose2d");
    }
}
