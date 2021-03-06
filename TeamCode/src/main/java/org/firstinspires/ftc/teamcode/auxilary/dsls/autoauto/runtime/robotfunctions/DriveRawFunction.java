package org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.runtime.robotfunctions;

import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.values.*;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.runtime.NativeRobotFunction;
import org.firstinspires.ftc.teamcode.managers.FeatureManager;

public class DriveRawFunction extends NativeRobotFunction {
    private org.firstinspires.ftc.teamcode.managers.movement.MovementManager manager;

    public DriveRawFunction(FeatureManager manager) {
        this.manager = (org.firstinspires.ftc.teamcode.managers.movement.MovementManager)manager;
    }

    @Override
    public AutoautoPrimitive call(AutoautoPrimitive[] args) {
        if(args.length == 4) {if(args[0] instanceof AutoautoNumericValue&&args[1] instanceof AutoautoNumericValue&&args[2] instanceof AutoautoNumericValue&&args[3] instanceof AutoautoNumericValue) {manager.driveRaw(((AutoautoNumericValue)args[0]).getFloat(),((AutoautoNumericValue)args[1]).getFloat(),((AutoautoNumericValue)args[2]).getFloat(),((AutoautoNumericValue)args[3]).getFloat()); return new AutoautoUndefined();}}throw new org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.runtime.errors.AutoautoNoNativeMethodOverloadException("No driveRaw with 4 args");
    }
}