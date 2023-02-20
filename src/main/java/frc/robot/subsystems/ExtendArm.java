package frc.robot.subsystems;

import java.util.concurrent.CancellationException;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.ControlType;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.*;

public class ExtendArm extends SubsystemBase{
    private CANSparkMax extemoroller;
    private RelativeEncoder extencoder;
    private SparkMaxPIDController extemPIDer;
    private double extensionTarget = 0;
    private double eP,eI,eD,eFF;
    
    public ExtendArm() {
        try{
            extemoroller = new CANSparkMax(ValueConstants.extemoroller, CANSparkMax.MotorType.kBrushless);
            extemoroller.restoreFactoryDefaults();
            extencoder = extemoroller.getEncoder();
            extemPIDer = extemoroller.getPIDController();
            extencoder.setPosition(0.0);
            extemPIDer.setP(ExtendPID.eP);
            eP = ExtendPID.eP;
            extemPIDer.setI(ExtendPID.eI);
            eI = ExtendPID.eI;
            extemPIDer.setD(ExtendPID.eD);
            eD = ExtendPID.eD;
            extemPIDer.setFF(ExtendPID.eFF);
            eFF = ExtendPID.eFF;
            extemoroller.enableSoftLimit(CANSparkMax.SoftLimitDirection.kForward, true);
            extemoroller.enableSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, true);    
            extemoroller.setSoftLimit(CANSparkMax.SoftLimitDirection.kForward, ExtendPID.eForwardLimit);
            extemoroller.setSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, ExtendPID.eReverseLimit);
            extemoroller.setIdleMode(CANSparkMax.IdleMode.kBrake);
        }catch (RuntimeException ex){
            DriverStation.reportError("Error Configuring Extension Motor"+ex.getMessage(), true);
        }
    }

    public void updatePID(){

    }

    public void move(double distance){
        extensionTarget = distance;
        extemPIDer.setReference(extensionTarget, ControlType.kPosition);
    }

    public double getPosition(){
        return extencoder.getPosition();
    }
    public void Stop() {
        extemoroller.stopMotor();
    }

    @Override
    public void periodic(){
        SmartDashboard.putNumber("Extension Position", extencoder.getPosition());
        SmartDashboard.getNumber("Extension Target", extensionTarget);
    }
}
