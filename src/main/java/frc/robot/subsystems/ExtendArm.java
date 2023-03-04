package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.ControlType;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.*;

public class ExtendArm extends SubsystemBase{
    private CANSparkMax extemoroller;
    private RelativeEncoder extencoder;
    private SparkMaxPIDController extemPIDer;
    private double extensionTarget = 0;
    private double P,I,D,FF;
    public boolean extended;
    
    public ExtendArm() {
        try{
            extemoroller = new CANSparkMax(ValueConstants.extemoroller, CANSparkMax.MotorType.kBrushless);
            extemoroller.restoreFactoryDefaults();
            extencoder = extemoroller.getEncoder();
            extemPIDer = extemoroller.getPIDController();
            extencoder.setPositionConversionFactor(1.62);
            extencoder.setPosition(0.0);
            extemPIDer.setP(ExtendPID.P);
            P = ExtendPID.P;
            extemPIDer.setI(ExtendPID.I);
            I = ExtendPID.I;
            extemPIDer.setD(ExtendPID.D);
            D = ExtendPID.D;
            extemPIDer.setFF(ExtendPID.FF);
            FF = ExtendPID.FF;
            extemoroller.enableSoftLimit(CANSparkMax.SoftLimitDirection.kForward, true);
            extemoroller.enableSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, true);    
            extemoroller.setSoftLimit(CANSparkMax.SoftLimitDirection.kForward, ExtendPID.ForwardLimit);
            extemoroller.setSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, ExtendPID.ReverseLimit);
            extemoroller.setIdleMode(CANSparkMax.IdleMode.kBrake);
            extended = false;
            move(0);
        }catch (RuntimeException ex){
            DriverStation.reportError("Error Configuring Extension Motor"+ex.getMessage(), true);
        }
    }

    public void updatePID(){
        double p = SmartDashboard.getNumber("Extend P", ExtendPID.P);
        double i = SmartDashboard.getNumber("Extend I", ExtendPID.I);
        double d = SmartDashboard.getNumber("Extend D", ExtendPID.D);
        double ff = SmartDashboard.getNumber("Extend FF", ExtendPID.FF);
        // if PID coefficients on SmartDashboard have changed, write new values to controller
        if((p != P)) { extemPIDer.setP(p); P = p;  }
        if((i != I)) { extemPIDer.setI(i); I = i; }
        if((d != D)) { extemPIDer.setD(d); D = d; }
        if((ff != FF)) { extemPIDer.setFF(ff); FF = ff; }  
        DriverStation.reportWarning("Updated PID", false);
    }

    public void move(double distance){
        extensionTarget = -distance;
        extemPIDer.setReference(extensionTarget, ControlType.kPosition);
    }

    public double getPosition(){
        return -extencoder.getPosition();
    }
    public void Stop() {
        extemoroller.stopMotor();
    }
    public boolean AtTarget(){
        double curposition = extencoder.getPosition();
        DriverStation.reportWarning(String.format("Position: %f",curposition),false);
        if ( Math.abs(curposition - extensionTarget) <=ExtendPID.Tolerance){
            DriverStation.reportWarning("True",false);
            return true;
        }else{
            DriverStation.reportWarning(String.format("false: %f",Math.abs(curposition - extensionTarget)),false);
            return false;
        }
    }
    public void increment(){
        extensionTarget = extensionTarget + 4.0;
        extemPIDer.setReference(extensionTarget, ControlType.kPosition);
    }
    public void decrement(){
        extensionTarget = extensionTarget - 4.0;
        extemPIDer.setReference(extensionTarget, ControlType.kPosition);
    }

    @Override
    public void periodic(){
        SmartDashboard.putNumber("Extension Position", getPosition());
        SmartDashboard.getNumber("Extension Target", extensionTarget);
        if (getPosition()>10.0){
            extended = true;
          }
          else{
            extended = false;
          }
        SmartDashboard.putBoolean("Extended", extended);
    }
}
