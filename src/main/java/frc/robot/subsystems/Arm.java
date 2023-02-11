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
    private double wristarget;
    private double armtarget;
    private double P,I,D;
    private double kFF;
    public Arm() {
    try{
        armMotorController = new CANSparkMax(DriveConstants.karmoter , CANSparkMax.MotorType.kBrushless);
        armMotorController.restoreFactoryDefaults();
        armEncoder = armMotorController.getEncoder();
        armEncoder.setPositionConversionFactor(8);
        armMotorController.enableSoftLimit(CANSparkMax.SoftLimitDirection.kForward, true);
        armMotorController.enableSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, true);    
        armMotorController.setSoftLimit(CANSparkMax.SoftLimitDirection.kForward, ArmPID.ArmForwardLimit);
        armMotorController.setSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, ArmPID.ArmReverseLimit);
        armtarget = 0;
        wristMotorController = new CANSparkMax(DriveConstants.kwristmoter , CANSparkMax.MotorType.kBrushless);
        wristMotorController.restoreFactoryDefaults();
        wristEncoder = armMotorController.getEncoder();
        wristEncoder.setPositionConversionFactor(8);

        m_karmoterPID = armMotorController.getPIDController();
        m_karmoterPID.setP(ArmPID.P);
        m_karmoterPID.setI(ArmPID.I);
        m_karmoterPID.setD(ArmPID.D);
        m_karmoterPID.setFF(ArmPID.FF);
        m_karmoterPID.setOutputRange(ArmPID.MinOutput, ArmPID.MaxOutput);
        m_karmoterPID.setPositionPIDWrappingMaxInput(180);
        m_karmoterPID.setPositionPIDWrappingMinInput(-180);
        m_karmoterPID.setPositionPIDWrappingEnabled(true);
        m_kwristmoterPID.setOutputRange(WristPID.MinOutuput, WristPID.MaxOutuput);
        SmartDashboard.putNumber("Forward Soft Limit", armMotorController.getSoftLimit(CANSparkMax.SoftLimitDirection.kForward));
        SmartDashboard.putNumber("Reverse Soft Limit", armMotorController.getSoftLimit(CANSparkMax.SoftLimitDirection.kReverse));
        SmartDashboard.putNumber("ArmTarget", armtarget);
        SmartDashboard.putNumber("P Gain", ArmPID.P);
        SmartDashboard.putNumber("I Gain", ArmPID.I);
        SmartDashboard.putNumber("D Gain", ArmPID.D);

    }catch (RuntimeException ex) {
        DriverStation.reportError("Error Configuring Drivetrain" + ex.getMessage(), true);
    }
    }
    public void moveArm() {
        m_karmoterPID.setReference(armtarget, ControlType.kPosition);    
    }
    public void moveWrist() {
        m_kwristmoterPID.setReference(wristarget, ControlType.kPosition);    
    }

    @Override
    public void periodic(){
        SmartDashboard.putNumber("ArmPosition", armEncoder.getPosition());
        SmartDashboard.putNumber("WristPosition", wristEncoder.getPosition());
        SmartDashboard.getNumber("ArmTarget", armtarget );
        
        armMotorController.setSoftLimit(CANSparkMax.SoftLimitDirection.kForward, (float)SmartDashboard.getNumber("Forward Soft Limit", 15));
        armMotorController.setSoftLimit(CANSparkMax.SoftLimitDirection.kReverse,(float)SmartDashboard.getNumber("Reverse Soft Limit", 0));
        double p = SmartDashboard.getNumber("P Gain", ArmPID.P);
        double i = SmartDashboard.getNumber("I Gain", ArmPID.I);
        double d = SmartDashboard.getNumber("D Gain", ArmPID.D);
        double ff = SmartDashboard.getNumber("Feed Forward", ArmPID.FF);
        double max = SmartDashboard.getNumber("Max Output", ArmPID.MaxOutput);
        double min = SmartDashboard.getNumber("Min Output", ArmPID.MinOutput);

        // if PID coefficients on SmartDashboard have changed, write new values to controller
        if((p != P)) { m_karmoterPID.setP(p); P = p; }
        if((i != I)) { m_karmoterPID.setI(i); I = i; }
        if((d != D)) { m_karmoterPID.setD(d); D = d; }
        if((ff != kFF)) { m_karmoterPID.setFF(ff); kFF = ff; }
        if((max != ArmPID.MaxOutput) || (min != ArmPID.MinOutput)) { 
        m_karmoterPID.setOutputRange(min, max);
        }
    }
}