package org.firstinspires.ftc.teamcode.util;

import android.content.Context;
import android.util.Log;
import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.canvas.Canvas;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.arcrobotics.ftclib.geometry.Rotation2d;
import com.arcrobotics.ftclib.geometry.Transform2d;
import com.arcrobotics.ftclib.geometry.Translation2d;
import com.arcrobotics.ftclib.kinematics.wpilibkinematics.ChassisSpeeds;
import com.intel.realsense.librealsense.UsbUtilities;
import com.qualcomm.robotcore.eventloop.opmode.AnnotatedOpModeManager;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.spartronics4915.lib.T265Camera;
import com.spartronics4915.lib.T265Camera.CameraUpdate;
import com.spartronics4915.lib.T265Camera.PoseConfidence;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(
        mv = {1, 1, 16},
        bv = {1, 0, 3},
        k = 1,
        xi = 2,
        d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010&\u001a\u00020'2\u0006\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020+J\u0010\u0010&\u001a\u00020'2\u0006\u0010,\u001a\u00020-H\u0007J\u0006\u0010.\u001a\u00020'J\u0006\u0010 \u001a\u00020'R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u000b\u001a\u00020\f8F¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u0019\u0010\u000f\u001a\n \u0011*\u0004\u0018\u00010\u00100\u0010¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0018\u0010\u0014\u001a\u00020\u00158\u0002@\u0002X\u0083\u000e¢\u0006\b\n\u0000\u0012\u0004\b\u0016\u0010\u0002R$\u0010\u0019\u001a\u00020\u00182\u0006\u0010\u0017\u001a\u00020\u00188F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u001c\u0010\u001dR\u0011\u0010\u001e\u001a\u00020\u00188F¢\u0006\u0006\u001a\u0004\b\u001f\u0010\u001bR\u001a\u0010 \u001a\u00020!X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010#\"\u0004\b$\u0010%¨\u0006/"},
        d2 = {"Lorg/firstinspires/ftc/teamcode/util/t265;", "", "()V", "cam", "Lcom/spartronics4915/lib/T265Camera;", "getCam", "()Lcom/spartronics4915/lib/T265Camera;", "setCam", "(Lcom/spartronics4915/lib/T265Camera;)V", "cameraToRobot", "Lcom/arcrobotics/ftclib/geometry/Transform2d;", "confidence", "Lcom/spartronics4915/lib/T265Camera$PoseConfidence;", "getConfidence", "()Lcom/spartronics4915/lib/T265Camera$PoseConfidence;", "dash", "Lcom/acmerobotics/dashboard/FtcDashboard;", "kotlin.jvm.PlatformType", "getDash", "()Lcom/acmerobotics/dashboard/FtcDashboard;", "isStarted", "", "isStarted$annotations", "value", "Lcom/acmerobotics/roadrunner/geometry/Pose2d;", "pose", "getPose", "()Lcom/acmerobotics/roadrunner/geometry/Pose2d;", "setPose", "(Lcom/acmerobotics/roadrunner/geometry/Pose2d;)V", "speeds", "getSpeeds", "update", "Lcom/spartronics4915/lib/T265Camera$CameraUpdate;", "getUpdate", "()Lcom/spartronics4915/lib/T265Camera$CameraUpdate;", "setUpdate", "(Lcom/spartronics4915/lib/T265Camera$CameraUpdate;)V", "init", "", "context", "Landroid/content/Context;", "manager", "Lcom/qualcomm/robotcore/eventloop/opmode/AnnotatedOpModeManager;", "hardwareMap", "Lcom/qualcomm/robotcore/hardware/HardwareMap;", "resetPose", "TeamCode_debug"}
)
public final class t265 {
    @NotNull
    public static T265Camera cam;
    private static final Transform2d cameraToRobot;
    @NotNull
    public static CameraUpdate update;
    private static boolean isStarted;
    private static final FtcDashboard dash;
    public static final t265 INSTANCE;

    @NotNull
    public final T265Camera getCam() {
        T265Camera var10000 = cam;
        if (var10000 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("cam");
        }

        return var10000;
    }

    public final void setCam(@NotNull T265Camera var1) {
        Intrinsics.checkNotNullParameter(var1, "<set-?>");
        cam = var1;
    }

    @NotNull
    public final CameraUpdate getUpdate() {
        CameraUpdate var10000 = update;
        if (var10000 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("update");
        }

        return var10000;
    }

    public final void setUpdate(@NotNull CameraUpdate var1) {
        Intrinsics.checkNotNullParameter(var1, "<set-?>");
        update = var1;
    }

    /** @deprecated */
    // $FF: synthetic method
    @JvmStatic
    private static void isStarted$annotations() {
    }

    public final FtcDashboard getDash() {
        return dash;
    }

    @JvmStatic
    public static final void initialize(@NotNull HardwareMap hardwareMap) {
        Intrinsics.checkNotNullParameter(hardwareMap, "hardwareMap");
        T265Camera var10000;
        if (!isStarted) {
            try {
                UsbUtilities.grantUsbPermissionIfNeeded(hardwareMap.appContext);
                Log.i("INTEL LOCALIZER", "Camera INITIALIZING");
                cam = new T265Camera(cameraToRobot, 0.0D, hardwareMap.appContext);
                Log.i("INTEL LOCALIZER", "Camera INITALIZED");
                var10000 = cam;
                if (var10000 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("cam");
                }

                var10000.start();
            } catch (Exception var2) {
                if (Intrinsics.areEqual(var2, new RuntimeException("T265 camera is already started"))) {
                    isStarted = true;
                    return;
                }

                initialize(hardwareMap);
                isStarted = false;
                Log.e("INTEL LOCALIZER", var2.toString());
            }
        }

        var10000 = cam;
        if (var10000 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("cam");
        }

        CameraUpdate var3 = var10000.getLastReceivedCameraUpdate();
        Intrinsics.checkNotNullExpressionValue(var3, "cam.lastReceivedCameraUpdate");
        update = var3;
    }

    public final void init(@NotNull Context context, @NotNull AnnotatedOpModeManager manager) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(manager, "manager");
        T265Camera var10000;
        if (!isStarted) {
            try {
                UsbUtilities.grantUsbPermissionIfNeeded(context);
                Log.i("INTEL LOCALIZER", "Camera INITIALIZING");
                cam = new T265Camera(cameraToRobot, 0.0D, context);
                Log.i("INTEL LOCALIZER", "Camera INITALIZED");
                var10000 = cam;
                if (var10000 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("cam");
                }

                var10000.start();
            } catch (Exception var4) {
                if (Intrinsics.areEqual(var4, new RuntimeException("T265 camera is already started"))) {
                    isStarted = true;
                } else {
                    this.init(context, manager);
                    isStarted = false;
                    Log.e("INTEL LOCALIZER", var4.toString());
                }
            }
        }

        var10000 = cam;
        if (var10000 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("cam");
        }

        CameraUpdate var5 = var10000.getLastReceivedCameraUpdate();
        Intrinsics.checkNotNullExpressionValue(var5, "cam.lastReceivedCameraUpdate");
        update = var5;
    }

    @NotNull
    public final Pose2d getPose() {
        Pose2d var10000;
        CameraUpdate var10002 = update;
        if (var10002 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("update");
        }

        com.arcrobotics.ftclib.geometry.Pose2d var1 = var10002.pose;
        Intrinsics.checkNotNullExpressionValue(var1, "update.pose");
        double var2 = var1.getTranslation().getX();
        CameraUpdate var10003 = update;
        if (var10003 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("update");
        }

        com.arcrobotics.ftclib.geometry.Pose2d var3 = var10003.pose;
        Intrinsics.checkNotNullExpressionValue(var3, "update.pose");
        double var4 = var3.getTranslation().getY();
        CameraUpdate var10004 = update;
        if (var10004 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("update");
        }

        com.arcrobotics.ftclib.geometry.Pose2d var5 = var10004.pose;
        Intrinsics.checkNotNullExpressionValue(var5, "update.pose");
        //var10000.initialize(var2, var4, ExtKt.getRotationRad(var5) - ExtKt.toRadians(0.0D));
        var10000 = new Pose2d(var2, var4, Math.toRadians(0.0));
        return ExtKt.toInches(var10000);
    }

    public final void setPose(@NotNull Pose2d value) {
        Intrinsics.checkNotNullParameter(value, "value");
        T265Camera var10000 = cam;
        if (var10000 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("cam");
        }

        var10000.setPose(ExtKt.toMeters(new com.arcrobotics.ftclib.geometry.Pose2d(value.getX(), value.getY(), new Rotation2d(value.getHeading()))));
    }

    @NotNull
    public final Pose2d getSpeeds() {
        CameraUpdate var10000 = update;
        if (var10000 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("update");
        }

        ChassisSpeeds var1 = var10000.velocity;
        Intrinsics.checkNotNullExpressionValue(var1, "update.velocity");
        return ExtKt.toRRPose2d(var1);
    }

    @NotNull
    public final PoseConfidence getConfidence() {
        CameraUpdate var10000 = update;
        if (var10000 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("update");
        }

        PoseConfidence var1 = var10000.confidence;
        Intrinsics.checkNotNullExpressionValue(var1, "update.confidence");
        return var1;
    }

    public final void update() {
        T265Camera slamra = cam;
        if (slamra == null) {
            Intrinsics.throwUninitializedPropertyAccessException("cam");
        }

        CameraUpdate up = slamra.getLastReceivedCameraUpdate();
        Intrinsics.checkNotNullExpressionValue(up, "cam.lastReceivedCameraUpdate");
        update = up;
        Log.i("INTEL LOCALIZER", this.getPose().toString());

        TelemetryPacket packet;
        Canvas field;

        Translation2d translation;
        Rotation2d rotation;

        final int robotRadius = 9;

        final double INCH_TO_METERS = 0.0254;

        packet = new TelemetryPacket();
        field = packet.fieldOverlay();

        up = slamra.getLastReceivedCameraUpdate();

        if(up == null) return;

        translation = new Translation2d(up.pose.getTranslation().getX() / INCH_TO_METERS, up.pose.getTranslation().getY() / INCH_TO_METERS);
        rotation = up.pose.getRotation();

        field.strokeCircle(translation.getX(), translation.getY(), robotRadius);
        double arrowX = rotation.getCos() * robotRadius, arrowY = rotation.getSin() * robotRadius;
        double x1 = translation.getX() + arrowX  / 2, y1 = translation.getY() + arrowY / 2;
        double x2 = translation.getX() + arrowX, y2 = translation.getY() + arrowY;
        field.strokeLine(x1, y1, x2, y2);

        dash.sendTelemetryPacket(packet);

        /*
        packet.fieldOverlay().fillCircle(this.getPose().getX(), this.getPose().getY(), 9.0D);
        dash.sendTelemetryPacket(packet);
         */
    }

    public final void resetPose() {
        T265Camera var10000 = cam;
        if (var10000 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("cam");
        }

        var10000.setPose(new com.arcrobotics.ftclib.geometry.Pose2d(0.0D, 0.0D, new Rotation2d(0.0D)));
    }

    private t265() {
    }

    static {
        t265 var0 = new t265();
        INSTANCE = var0;
        cameraToRobot = new Transform2d(new Translation2d(-22.5, 0.0D), new Rotation2d(ExtKt.toRadians(0.0D)));
        dash = FtcDashboard.getInstance();
    }
}
