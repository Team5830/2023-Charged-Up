package frc.robot.subsystems;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.ControlType;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants.ValueConstants;
import frc.robot.Constants.ArmPID;

public class Arm extends SubsystemBase {

    private CANSparkMax armMotorController;
    private RelativeEncoder armEncoder;
    private SparkMaxPIDController m_karmoterPID;
    private double karget;
    private double P,I,D;
    private double kFF;
    public Arm() {
    try{
        armMotorController = new CANSparkMax(ValueConstants.karmoter , CANSparkMax.MotorType.kBrushless);
        armMotorController.restoreFactoryDefaults();
        armEncoder = armMotorController.getEncoder();
        armEncoder.setPositionConversionFactor(8);
        armEncoder.setPosition(0.0);
        armMotorController.enableSoftLimit(CANSparkMax.SoftLimitDirection.kForward, true);
        armMotorController.enableSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, true);    
        armMotorController.setSoftLimit(CANSparkMax.SoftLimitDirection.kForward, ArmPID.ForwardLimit);
        armMotorController.setSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, ArmPID.ReverseLimit);
        
        m_karmoterPID = armMotorController.getPIDController();
        m_karmoterPID.setP(ArmPID.P);
        P = ArmPID.P;
        m_karmoterPID.setI(ArmPID.I);
        I = ArmPID.I;
        m_karmoterPID.setD(ArmPID.D);
        D = ArmPID.D;
        m_karmoterPID.setFF(ArmPID.FF);
        kFF = ArmPID.FF;
        m_karmoterPID.setOutputRange(ArmPID.MinOutput, ArmPID.MaxOutput);
        //m_karmoterPID.setPositionPIDWrappingMaxInput(180);
        //m_karmoterPID.setPositionPIDWrappingMinInput(-180);
        //m_karmoterPID.setPositionPIDWrappingEnabled(true);
        
        
        }catch (RuntimeException ex) {
            DriverStation.reportError("Error Configuring Drivetrain" + ex.getMessage(), true);
        }
    }
    public void updatePID(){
        double p = SmartDashboard.getNumber("Arm P", ArmPID.P);
        double i = SmartDashboard.getNumber("Arm I", ArmPID.I);
        double d = SmartDashboard.getNumber("Arm D", ArmPID.D);
        double ff = SmartDashboard.getNumber("Arm FF", ArmPID.FF);
        // if PID coefficients on SmartDashboard have changed, write new values to controller
        if((p != P)) { m_karmoterPID.setP(p); P = p;  }
        if((i != I)) { m_karmoterPID.setI(i); I = i; }
        if((d != D)) { m_karmoterPID.setD(d); D = d; }
        if((ff != kFF)) { m_karmoterPID.setFF(ff); kFF = ff; }  
        DriverStation.reportWarning("Updated PID", false);
    }
    
    public void move(double degrees) {
        karget = degrees;
        m_karmoterPID.setReference(karget, ControlType.kPosition);
    }
    public boolean AtTarget(){
        double curposition = armEncoder.getPosition();
        DriverStation.reportWarning(String.format("Position: %f",curposition),false);
        if ( Math.abs(curposition - karget) <=ArmPID.Tolerance){
            DriverStation.reportWarning("True",false);
            return true;
        }else{
            DriverStation.reportWarning(String.format("false: %f",Math.abs(curposition - karget)),false);
            return false;
        }
    }
    public double Position(){
        return armEncoder.getPosition();
    }

    public void Stop(){
        //armMotorController.set(0);
        armMotorController.stopMotor();
        SmartDashboard.putNumber("ArmPosition", armEncoder.getPosition());
    }
    public void increment(){
        karget = karget + 2;
        m_karmoterPID.setReference(karget, ControlType.kPosition);
    }
    public void decrement(){
        karget = karget - 2;
        m_karmoterPID.setReference(karget, ControlType.kPosition);
    }

    @Override
    public void periodic(){
        SmartDashboard.putNumber("ArmPosition", armEncoder.getPosition());
        SmartDashboard.getNumber("karget", karget );
    }
}