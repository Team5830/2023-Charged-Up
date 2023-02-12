package frc.robot.subsystems;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.ControlType;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants.*;

public class Arm extends SubsystemBase {

    private CANSparkMax armMotorController;
    private CANSparkMax wristMotorController;   
    private RelativeEncoder armEncoder;
    private RelativeEncoder wristEncoder;
    private SparkMaxPIDController m_karmoterPID;
    private SparkMaxPIDController m_kwristmoterPID;
    public double wristarget = 0;
    public double armtarget = 0;
    private double P,I,D;
    private double kP,kI,kD,kff,kmax,kmin;
    private float ArmForwardLimit, ArmReverseLimit;
    public Arm() {
    try{
        armMotorController = new CANSparkMax(DriveConstants.karmoter , CANSparkMax.MotorType.kBrushless);
        armMotorController.restoreFactoryDefaults();
        armEncoder = armMotorController.getEncoder();
        armEncoder.setPositionConversionFactor(8);
        armEncoder.setPosition(0);
        //armMotorController.enableSoftLimit(CANSparkMax.SoftLimitDirection.kForward, true);
        //armMotorController.enableSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, true);    
        //armMotorController.setSoftLimit(CANSparkMax.SoftLimitDirection.kForward, ArmPID.ArmForwardLimit);
        //armMotorController.setSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, ArmPID.ArmReverseLimit);
        ArmForwardLimit = ArmPID.ArmForwardLimit;
        ArmReverseLimit = ArmPID.ArmReverseLimit;
        wristMotorController = new CANSparkMax(DriveConstants.kwristmoter , CANSparkMax.MotorType.kBrushless);
        wristMotorController.restoreFactoryDefaults();
        wristEncoder = armMotorController.getEncoder();
        wristEncoder.setPositionConversionFactor(8/9);

        m_karmoterPID = armMotorController.getPIDController();
        P = ArmPID.P;
        I = ArmPID.I;
        D = ArmPID.D;

        m_karmoterPID.setP(ArmPID.P);
        m_karmoterPID.setI(ArmPID.I);
        m_karmoterPID.setD(ArmPID.D);
        m_karmoterPID.setFF(ArmPID.FF);
        m_karmoterPID.setOutputRange(ArmPID.MinOutput, ArmPID.MaxOutput);
        m_karmoterPID.setPositionPIDWrappingMaxInput(180);
        m_karmoterPID.setPositionPIDWrappingMinInput(-180);
        m_karmoterPID.setPositionPIDWrappingEnabled(true);
        m_kwristmoterPID.setOutputRange(WristPID.MinOutuput, WristPID.MaxOutuput);
                
    }catch (RuntimeException ex) {
        DriverStation.reportError("Error Configuring Drivetrain" + ex.getMessage(), true);
    }
    }
    public void moveArm() {
        //SmartDashboard.getNumber("ArmTarget", armtarget );
        //armtarget = 90.0;
        /* 
        float ArmForwardLimitNew = (float)SmartDashboard.getNumber("Forward Soft Limit", ArmPID.ArmForwardLimit);
        if (ArmForwardLimit != ArmForwardLimitNew) {
            armMotorController.setSoftLimit(CANSparkMax.SoftLimitDirection.kForward, ArmForwardLimitNew);
        }
        float ArmReverseLimitNew = (float)SmartDashboard.getNumber("Forward Soft Limit", ArmPID.ArmForwardLimit);
        if (ArmForwardLimit != ArmReverseLimitNew) {        
            armMotorController.setSoftLimit(CANSparkMax.SoftLimitDirection.kReverse,ArmReverseLimitNew);
        }
        */
        double p = SmartDashboard.getNumber("P Gain", ArmPID.P);
        double i = SmartDashboard.getNumber("I Gain", ArmPID.I);
        double d = SmartDashboard.getNumber("D Gain", ArmPID.D);
        double ff = SmartDashboard.getNumber("Feed Forward", ArmPID.FF);
        double max = SmartDashboard.getNumber("Max Output", ArmPID.MaxOutput);
        double min = SmartDashboard.getNumber("Min Output", ArmPID.MinOutput);
        //if PID coefficients on SmartDashboard have changed, write new values to controller
        if((p != P)) { m_karmoterPID.setP(p); P = p;}
        if((i != I)) { m_karmoterPID.setI(i); I = i; }
        if((d != D)) { m_karmoterPID.setD(d); D = d; }
        //if((ff != kFF)) { m_karmoterPID.setFF(ff); kFF = ff; }
        if((max != ArmPID.MaxOutput) || (min != ArmPID.MinOutput)) { 
            m_karmoterPID.setOutputRange(min, max);
        }
        m_karmoterPID.setReference(armtarget, ControlType.kPosition); 
    }

    public boolean atTarget(){
        if ( (armEncoder.getPosition() - 1.0 <= armtarget) && (armtarget <= armEncoder.getPosition()+1.0)){
            return true;
        }else{
            return false;
        }
    }

    public void moveWrist() {
        SmartDashboard.getNumber("WrisTarget", wristarget);
        m_kwristmoterPID.setReference(wristarget, ControlType.kPosition);    
    }

    @Override
    public void periodic(){
        SmartDashboard.putNumber("ArmPosition", armEncoder.getPosition());
        SmartDashboard.putNumber("WristPosition", wristEncoder.getPosition());
        
        
        
        //wristMotorController.setSoftLimit(CANSparkMax.SoftLimitDirection.kForward, (float)SmartDashboard.getNumber("Forward Soft Limit", 15));
        //wristMotorController.setSoftLimit(CANSparkMax.SoftLimitDirection.kReverse,(float)SmartDashboard.getNumber("Reverse Soft Limit", 0));
        /* 
        double kp = SmartDashboard.getNumber("kP Gain", WristPID.P);
        double ki = SmartDashboard.getNumber("kI Gain", WristPID.I);
        double kd = SmartDashboard.getNumber("kD Gain", WristPID.D);
        double kff = SmartDashboard.getNumber("kFeed Forward", WristPID.kFF);
        double kmax = SmartDashboard.getNumber("kMax Output", WristPID.MaxOutuput);
        double kmin = SmartDashboard.getNumber("kMin Output", WristPID.MinOutuput);

        // if PID coefficients on SmartDashboard have changed, write new values to controller
        if((kp != P)) { m_kwristmoterPID.setP(kp); P = kp; }
        if((ki != I)) { m_kwristmoterPID.setI(ki); I = ki; }
        if((kd != D)) { m_kwristmoterPID.setD(kd); D = kd; }
        if((kff != kFF)) { m_kwristmoterPID.setFF(ff); kFF = kff; }
        if((kmax != WristPID.MaxOutuput) || (kmin != WristPID.MinOutuput)) { 
        m_kwristmoterPID.setOutputRange(kmin, kmax);
        }
        */
    }
}