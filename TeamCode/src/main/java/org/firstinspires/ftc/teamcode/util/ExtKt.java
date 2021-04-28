package org.firstinspires.ftc.teamcode.util;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.arcrobotics.ftclib.geometry.Rotation2d;
import com.arcrobotics.ftclib.geometry.Translation2d;
import com.arcrobotics.ftclib.kinematics.wpilibkinematics.ChassisSpeeds;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineStart;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.GlobalScope;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
        mv = {1, 1, 16},
        bv = {1, 0, 3},
        k = 2,
        xi = 2,
        d1 = {"\u00000\n\u0000\n\u0002\u0010\u0006\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u001c\u0010\u000b\u001a\u00020\f2\u0006\u0010\u000b\u001a\u00020\r2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\f0\u000f\u001a\n\u0010\u0010\u001a\u00020\u0001*\u00020\u0001\u001a\n\u0010\u0011\u001a\u00020\u0002*\u00020\u0012\u001a\n\u0010\u0013\u001a\u00020\u0012*\u00020\u0012\u001a\n\u0010\u0014\u001a\u00020\u0002*\u00020\u0002\u001a\n\u0010\u0015\u001a\u00020\u0012*\u00020\u0002\u001a\n\u0010\u0016\u001a\u00020\u0012*\u00020\u0017\u001a\n\u0010\u0018\u001a\u00020\u0001*\u00020\u0001\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\"\u0015\u0010\u0005\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0004\"\u0015\u0010\u0007\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\b\u0010\u0004\"\u0015\u0010\t\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\n\u0010\u0004¨\u0006\u0019"},
        d2 = {"rotationDeg", "", "Lcom/arcrobotics/ftclib/geometry/Pose2d;", "getRotationDeg", "(Lcom/arcrobotics/ftclib/geometry/Pose2d;)D", "rotationRad", "getRotationRad", "x", "getX", "y", "getY", "delay", "", "", "block", "Lkotlin/Function0;", "toDegrees", "toFTCLib", "Lcom/acmerobotics/roadrunner/geometry/Pose2d;", "toInches", "toMeters", "toRR", "toRRPose2d", "Lcom/arcrobotics/ftclib/kinematics/wpilibkinematics/ChassisSpeeds;", "toRadians", "TeamCode_debug"}
)
public final class ExtKt {
    @NotNull
    public static final Pose2d toRR(@NotNull com.arcrobotics.ftclib.geometry.Pose2d $this$toRR) {
        Intrinsics.checkNotNullParameter($this$toRR, "$this$toRR");
        Translation2d var10002 = $this$toRR.getTranslation();
        Intrinsics.checkNotNullExpressionValue(var10002, "this.translation");
        double var1 = var10002.getY();
        Translation2d var10003 = $this$toRR.getTranslation();
        Intrinsics.checkNotNullExpressionValue(var10003, "this.translation");
        return new Pose2d(var1, var10003.getX(), $this$toRR.getHeading());
    }

    @NotNull
    public static final com.arcrobotics.ftclib.geometry.Pose2d toFTCLib(@NotNull Pose2d $this$toFTCLib) {
        Intrinsics.checkNotNullParameter($this$toFTCLib, "$this$toFTCLib");
        return new com.arcrobotics.ftclib.geometry.Pose2d(-$this$toFTCLib.getX(), -$this$toFTCLib.getY(), new Rotation2d($this$toFTCLib.getHeading()));
    }

    public static final double toRadians(double $this$toRadians) {
        return Math.toRadians($this$toRadians);
    }

    public static final double toDegrees(double $this$toDegrees) {
        return Math.toDegrees($this$toDegrees);
    }

    public static final double getX(@NotNull com.arcrobotics.ftclib.geometry.Pose2d $this$x) {
        Intrinsics.checkNotNullParameter($this$x, "$this$x");
        Translation2d var10000 = $this$x.getTranslation();
        Intrinsics.checkNotNullExpressionValue(var10000, "this.translation");
        return var10000.getX();
    }

    public static final double getY(@NotNull com.arcrobotics.ftclib.geometry.Pose2d $this$y) {
        Intrinsics.checkNotNullParameter($this$y, "$this$y");
        Translation2d var10000 = $this$y.getTranslation();
        Intrinsics.checkNotNullExpressionValue(var10000, "this.translation");
        return var10000.getY();
    }

    public static final double getRotationDeg(@NotNull com.arcrobotics.ftclib.geometry.Pose2d $this$rotationDeg) {
        Intrinsics.checkNotNullParameter($this$rotationDeg, "$this$rotationDeg");
        Rotation2d var10000 = $this$rotationDeg.getRotation();
        Intrinsics.checkNotNullExpressionValue(var10000, "this.rotation");
        return var10000.getDegrees();
    }

    public static final double getRotationRad(@NotNull com.arcrobotics.ftclib.geometry.Pose2d $this$rotationRad) {
        Intrinsics.checkNotNullParameter($this$rotationRad, "$this$rotationRad");
        Rotation2d var10000 = $this$rotationRad.getRotation();
        Intrinsics.checkNotNullExpressionValue(var10000, "this.rotation");
        return var10000.getRadians();
    }

    @NotNull
    public static final Pose2d toRRPose2d(@NotNull ChassisSpeeds $this$toRRPose2d) {
        Intrinsics.checkNotNullParameter($this$toRRPose2d, "$this$toRRPose2d");
        return new Pose2d($this$toRRPose2d.vxMetersPerSecond, $this$toRRPose2d.vyMetersPerSecond, $this$toRRPose2d.omegaRadiansPerSecond);
    }

    @NotNull
    public static final Pose2d toInches(@NotNull Pose2d $this$toInches) {
        Intrinsics.checkNotNullParameter($this$toInches, "$this$toInches");
        return new Pose2d($this$toInches.getX() / 0.025400051D, $this$toInches.getY() / 0.025400051D, $this$toInches.getHeading());
    }

    @NotNull
    public static final com.arcrobotics.ftclib.geometry.Pose2d toMeters(@NotNull com.arcrobotics.ftclib.geometry.Pose2d thisToMeters) {
        Intrinsics.checkNotNullParameter(thisToMeters, "thisToMeters");
        return new com.arcrobotics.ftclib.geometry.Pose2d(thisToMeters.getTranslation().getX() * 0.025400051D, thisToMeters.getTranslation().getY() * 0.025400051D, new Rotation2d(thisToMeters.getHeading()));
    }

    /*
    public static final void delay(final long delay, @NotNull final Function0 block) {
        Intrinsics.checkNotNullParameter(block, "block");
        BuildersKt.launch$default((CoroutineScope)GlobalScope.INSTANCE, (CoroutineContext)null, (CoroutineStart)null, (Function2)(new Function2((Continuation)null) {
            private CoroutineScope p$;
            Object L$0;
            int label;

            @Nullable
            public final Object invokeSuspend(@NotNull Object $result) {
                Object var3 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                CoroutineScope $this$launch;
                switch(this.label) {
                    case 0:
                        ResultKt.throwOnFailure($result);
                        $this$launch = this.p$;
                        long var10000 = delay;
                        this.L$0 = $this$launch;
                        this.label = 1;
                        if (DelayKt.delay(var10000, this) == var3) {
                            return var3;
                        }
                        break;
                    case 1:
                        $this$launch = (CoroutineScope)this.L$0;
                        ResultKt.throwOnFailure($result);
                        break;
                    default:
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }

                block.invoke();
                return Unit.INSTANCE;
            }

            @NotNull
            public final Continuation create(@Nullable Object value, @NotNull Continuation completion) {
                Intrinsics.checkNotNullParameter(completion, "completion");
                Function2 var3 = new <anonymous constructor>(completion);
                var3.p$ = (CoroutineScope)value;
                return var3;
            }

            public final Object invoke(Object var1, Object var2) {
                return ((<undefinedtype>)this.create(var1, (Continuation)var2)).invokeSuspend(Unit.INSTANCE);
            }
        }), 3, (Object)null);
    }
     */
}
