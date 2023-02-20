package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.ControlType;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants.*;

public class Wrist extends SubsystemBase {

    private CANSparkMax wristMotorController;   
    private RelativeEncoder wristEncoder;
    private SparkMaxPIDController m_kwristmoterPID;
    private double wristarget;
    private double P,I,D;
    private double kFF;
    public Wrist() {
    try{
        wristMotorController = new CANSparkMax(ValueConstants.kwristmoter , CANSparkMax.MotorType.kBrushless);
        wristMotorController.restoreFactoryDefaults();
        wristEncoder = wristMotorController.getEncoder();
        wristEncoder.setPositionConversionFactor(80);
        wristEncoder.setPosition(0.0);
        wristMotorController.enableSoftLimit(CANSparkMax.SoftLimitDirection.kForward, true);
        wristMotorController.enableSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, true);    
        wristMotorController.setSoftLimit(CANSparkMax.SoftLimitDirection.kForward, WristPID.ForwardLimit);
        wristMotorController.setSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, WristPID.ReverseLimit);
        wristMotorController.setIdleMode(CANSparkMax.IdleMode.kBrake);
        

        m_kwristmoterPID = wristMotorController.getPIDController();
        m_kwristmoterPID.setP(WristPID.P);
        P = WristPID.P;
        m_kwristmoterPID.setI(WristPID.I);
        I = WristPID.I;
        m_kwristmoterPID.setD(WristPID.D);
        D = WristPID.D;
        m_kwristmoterPID.setFF(WristPID.FF);
        kFF = WristPID.FF;
        m_kwristmoterPID.setOutputRange(WristPID.MinOutput, WristPID.MaxOutput);
        m_kwristmoterPID.setPositionPIDWrappingMaxInput(180);
        m_kwristmoterPID.setPositionPIDWrappingMinInput(-180);
        m_kwristmoterPID.setPositionPIDWrappingEnabled(true);
                
        }catch (RuntimeException ex) {
            DriverStation.reportError("Error Configuring Drivetrain" + ex.getMessage(), true);
        }
    }
    public void updatePID(){
        double p = SmartDashboard.getNumber("Wrist P", WristPID.P);
        double i = SmartDashboard.getNumber("Wrist I", WristPID.I);
        double d = SmartDashboard.getNumber("Wrist D", WristPID.D);
        double ff = SmartDashboard.getNumber("Wrist FF", WristPID.FF);
        // if PID coefficients on SmartDashboard have changed, write new values to controller
        if((p != P)) { m_kwristmoterPID.setP(p); P = p; }
        if((i != I)) { m_kwristmoterPID.setI(i); I = i; }
        if((d != D)) { m_kwristmoterPID.setD(d); D = d; }
        if((ff != kFF)) { m_kwristmoterPID.setFF(ff); kFF = ff; }   
    }
    
    public void move(double degrees) {
        wristarget = degrees;
        m_kwristmoterPID.setReference(wristarget, ControlType.kPosition);    
    }
    public boolean AtTarget(){
        double curposition = wristEncoder.getPosition();
        DriverStation.reportWarning(String.format("Position: %f",curposition),false);
        if ( Math.abs(curposition - wristarget) <=WristPID.Tolerance){
            DriverStation.reportWarning("True",false);
            return true;
        }else{
            DriverStation.reportWarning(String.format("false: %f",Math.abs(curposition - wristarget)),false);
            return false;
        }
    }
    public double Position(){
        return wristEncoder.getPosition();
    }

    public void Stop(){
        wristMotorController.set(0);
    }

    @Override
    public void periodic(){
        SmartDashboard.putNumber("WristPosition", wristEncoder.getPosition());
        SmartDashboard.getNumber("wristarget", wristarget );
    }
}