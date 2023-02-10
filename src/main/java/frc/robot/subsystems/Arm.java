package frc.robot.subsystems;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
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
    public Arm() {
    try{
        armMotorController = new CANSparkMax(DriveConstants.karmoter , CANSparkMax.MotorType.kBrushless);
        armMotorController.restoreFactoryDefaults();
        armEncoder = armMotorController.getEncoder();
        armEncoder.setPositionConversionFactor(8);
        wristMotorController = new CANSparkMax(DriveConstants.kwristmoter , CANSparkMax.MotorType.kBrushless);
        wristMotorController.restoreFactoryDefaults();
        wristEncoder = armMotorController.getEncoder();
        wristEncoder.setPositionConversionFactor(8);
        m_karmoterPID.setP(ArmPID.P);
        m_karmoterPID.setI(ArmPID.I);
        m_karmoterPID.setD(ArmPID.D);
        m_karmoterPID.setOutputRange(ArmPID.MinOutput, ArmPID.MaxOutuput);
        m_kwristmoterPID.setP(WristPID.P);
        m_kwristmoterPID.setI(WristPID.I);
        m_kwristmoterPID.setD(WristPID.D);
        m_kwristmoterPID.setOutputRange(WristPID.MinOutput, WristPID.MaxOutuput);
    }catch (RuntimeException ex) {
        DriverStation.reportError("Error Configuring Drivetrain" + ex.getMessage(), true);
    }
    }
    public void moveArm() {
        armtarget = 90.0;
        m_karmoterPID.setReference(armtarget, ControlType.kPosition);    
    }
    public void moveWrist() {
        wristarget = 90.0;
        m_kwristmoterPID.setReference(wristarget, ControlType.kPosition);    
    }
}