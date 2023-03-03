// RobotBuilder Version: 5.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

// ROBOTBUILDER TYPE: Subsystem.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup; 
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.*;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SerialPort;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.ControlType;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.DriverStation;

// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS


/**
 *
 */
public class DriveTrain extends SubsystemBase {
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    private DifferentialDrive m_drive;
    private AHRS ahrs;
    private CANSparkMax leftLeadMotorController;
    private CANSparkMax leftFollowMotorController;
    private CANSparkMax rightLeadMotorController;
    private CANSparkMax rightFollowMotorController;
    private MotorControllerGroup leftMotorControllerGroup;
    private MotorControllerGroup rightMotorControllerGroup;
    private RelativeEncoder leftLeadEncoder;
    private RelativeEncoder rightLeadEncoder;
    private SparkMaxPIDController m_drivetrainPIDcontleft;
    private SparkMaxPIDController m_drivetrainPIDcontright;
    private double lP,lI,lD,rP,rI,rD;
    private double maxspeed;
    private boolean extended;
    
    /**
    *
    */
    public DriveTrain() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS


    try{
      ahrs = new AHRS(SerialPort.Port.kUSB1);

      leftLeadMotorController = new CANSparkMax(ValueConstants.kLeftMotor1Port , CANSparkMax.MotorType.kBrushless);
      leftLeadMotorController.restoreFactoryDefaults();
      leftLeadEncoder = leftLeadMotorController.getEncoder();
      leftLeadEncoder.setPositionConversionFactor(18.84*2.54/8.33);
      leftFollowMotorController = new CANSparkMax(ValueConstants.kLeftMotor2Port, CANSparkMax.MotorType.kBrushless);
      leftFollowMotorController.restoreFactoryDefaults();
    
      m_drivetrainPIDcontleft = leftLeadMotorController.getPIDController();
      maxspeed = 1;
      extended = false;

      leftFollowMotorController.follow(leftLeadMotorController);

      leftMotorControllerGroup = new MotorControllerGroup(leftLeadMotorController, leftFollowMotorController);
      addChild("Left Motor Controller Group", leftMotorControllerGroup);
      

      rightLeadMotorController = new CANSparkMax(ValueConstants.kRightMotor1Port, CANSparkMax.MotorType.kBrushless);
      rightLeadMotorController.restoreFactoryDefaults();
      rightLeadEncoder = rightLeadMotorController.getEncoder();
      rightLeadEncoder.setPositionConversionFactor(18.84*2.54/8.33);
      
      m_drivetrainPIDcontright = rightLeadMotorController.getPIDController();
      rightFollowMotorController = new CANSparkMax(ValueConstants.kRightMotor2Port, CANSparkMax.MotorType.kBrushless);
      rightFollowMotorController.restoreFactoryDefaults();
    
      rightFollowMotorController.follow(rightLeadMotorController);
      rightMotorControllerGroup = new MotorControllerGroup(rightLeadMotorController, rightFollowMotorController);
      rightMotorControllerGroup.setInverted(true);
      rightLeadEncoder.setPosition(0);
      leftLeadEncoder.setPosition(0);
    
      m_drive = new DifferentialDrive(leftMotorControllerGroup, rightMotorControllerGroup);
      m_drivetrainPIDcontleft.setP(MovePID.lP);
      m_drivetrainPIDcontleft.setI(MovePID.lI);
      m_drivetrainPIDcontleft.setD(MovePID.lD);
      m_drivetrainPIDcontright.setP(MovePID.rP);
      m_drivetrainPIDcontright.setI(MovePID.rI);
      m_drivetrainPIDcontright.setD(MovePID.rD);
    } catch (RuntimeException ex) {
      DriverStation.reportError("Error Configuring Drivetrain" + ex.getMessage(), true);
    }
    addChild("Right Motor Controller Group", rightMotorControllerGroup);
    }
    public void updatePID() {
      double lp = SmartDashboard.getNumber("Left P", MovePID.lP);
      double li = SmartDashboard.getNumber("Left I", MovePID.lI);
      double ld = SmartDashboard.getNumber("Left D", MovePID.lD);
      double rp = SmartDashboard.getNumber("Right P", MovePID.rP);
      double ri = SmartDashboard.getNumber("Right I", MovePID.rI);
      double rd = SmartDashboard.getNumber("Right D", MovePID.rD);
      if((lp != lP)) { m_drivetrainPIDcontleft.setP(lp); lP = lp; }
      if((li != lI)) { m_drivetrainPIDcontleft.setI(li); lI = li; }
      if((ld != lD)) { m_drivetrainPIDcontleft.setD(ld); lD = ld; }
      if((rp != rP)) { m_drivetrainPIDcontright.setP(rp); rP = rp; }
      if((ri != rI)) { m_drivetrainPIDcontright.setI(ri); rI = ri; }
      if((rd != rD)) { m_drivetrainPIDcontright.setD(rd); rD = rd; }
    }
    public void TankDrive(double leftspeed, double rightspeed) {
        if (leftspeed > maxspeed) {
          leftspeed = maxspeed;
        }
        if (leftspeed < -maxspeed) {
          leftspeed = -maxspeed;
        }
        if (rightspeed > maxspeed) {
          rightspeed = maxspeed;
        }
        if (rightspeed < -maxspeed) {
          rightspeed = -maxspeed;
        }
        // System.out.println("%0.2f %0.2f",leftspeed,rightspeed);
        m_drive.tankDrive(-leftspeed, -rightspeed, true);
      }

    public double getPitch() {
        return ahrs.getPitch();
    }

    public double getAngle() {
      return ahrs.getAngle();
    }

    public void resetHeading(){
      ahrs.reset();
    }

    public double getDistance() {
      try{
        return (
            leftLeadEncoder.getPosition() -
            rightLeadEncoder.getPosition()
        ) / 2;
      }catch(RuntimeException ex) {
        DriverStation.reportError("Error Getting Position" + ex.getMessage(), true);
        return 0;
      }
    }
    
    public void resetEncoders() {
        leftLeadEncoder.setPosition(0);
        rightLeadEncoder.setPosition(0);
    }
    public void OverrideMax(){
      maxspeed=1;
    }
    @Override
    public void periodic() {
        SmartDashboard.putNumber("Pitch", getPitch());
        SmartDashboard.putNumber("Angle", ahrs.getAngle());
        SmartDashboard.putNumber("Distance", getDistance());
        SmartDashboard.putNumber("rightposition", -rightLeadEncoder.getPosition());
        SmartDashboard.putNumber("leftposition", leftLeadEncoder.getPosition());
        SmartDashboard.getBoolean("Extended", extended);
        if(extended = true){
          maxspeed = 0.5;
        }
        else{
          maxspeed = 1;
        }
    }
    public void brake() {
     double rightarget = -rightLeadEncoder.getPosition();
     double leftarget = leftLeadEncoder.getPosition();
     m_drivetrainPIDcontleft.setReference(leftarget, ControlType.kPosition);
     m_drivetrainPIDcontright.setReference(rightarget, ControlType.kPosition);
    }
    public void brakerelease(){
       rightLeadMotorController.stopMotor();
       leftLeadMotorController.stopMotor();
    }
}

































