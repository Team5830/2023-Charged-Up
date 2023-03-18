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
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.*;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SerialPort;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.ControlType;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.DriverStation;

// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS

// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS

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
  private double lP, lI, lD, rP, rI, rD;
  private double maxspeed;
  private DifferentialDriveKinematics kinematics;
  private DifferentialDriveOdometry odometry;
  private Field2d m_field = new Field2d();


  /**
  *
  */
  public DriveTrain() {
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS

    try {
      ahrs = new AHRS(SerialPort.Port.kUSB1);

      leftLeadMotorController = new CANSparkMax(ValueConstants.kLeftMotor1Port, CANSparkMax.MotorType.kBrushless);
      leftLeadMotorController.restoreFactoryDefaults();
      leftLeadEncoder = leftLeadMotorController.getEncoder();
      leftLeadEncoder.setPositionConversionFactor(18.84 * 2.54 / 8.33 / 100);

      leftFollowMotorController = new CANSparkMax(ValueConstants.kLeftMotor2Port, CANSparkMax.MotorType.kBrushless);
      leftFollowMotorController.restoreFactoryDefaults();

      m_drivetrainPIDcontleft = leftLeadMotorController.getPIDController();
      maxspeed = Constants.MovePID.HighSpeed;

      leftFollowMotorController.follow(leftLeadMotorController);

      leftMotorControllerGroup = new MotorControllerGroup(leftLeadMotorController, leftFollowMotorController);
      addChild("Left Motor Controller Group", leftMotorControllerGroup);

      rightLeadMotorController = new CANSparkMax(ValueConstants.kRightMotor1Port, CANSparkMax.MotorType.kBrushless);
      rightLeadMotorController.restoreFactoryDefaults();
      rightLeadEncoder = rightLeadMotorController.getEncoder();
      rightLeadEncoder.setPositionConversionFactor(18.84 * 2.54 / 8.33 / 100);

      rightLeadEncoder.setVelocityConversionFactor(0.008); // Set this to scale from RPM's into meter per second
      rightLeadEncoder.setVelocityConversionFactor(0.008); // Set this to scale from RPM's into meter per second

      kinematics = new DifferentialDriveKinematics(Units.inchesToMeters(Constants.ValueConstants.width));

      m_drivetrainPIDcontright = rightLeadMotorController.getPIDController();
      rightFollowMotorController = new CANSparkMax(ValueConstants.kRightMotor2Port, CANSparkMax.MotorType.kBrushless);
      rightFollowMotorController.restoreFactoryDefaults();

      rightFollowMotorController.follow(rightLeadMotorController);
      rightMotorControllerGroup = new MotorControllerGroup(rightLeadMotorController, rightFollowMotorController);
      rightMotorControllerGroup.setInverted(true);
      rightLeadEncoder.setPosition(0);
      leftLeadEncoder.setPosition(0);

      odometry = new DifferentialDriveOdometry(ahrs.getRotation2d(), leftLeadEncoder.getPosition(),
          rightLeadEncoder.getPosition());

      m_drive = new DifferentialDrive(leftMotorControllerGroup, rightMotorControllerGroup);
      m_drivetrainPIDcontleft.setP(MovePID.lP);
      m_drivetrainPIDcontleft.setI(MovePID.lI);
      m_drivetrainPIDcontleft.setD(MovePID.lD);
      m_drivetrainPIDcontright.setP(MovePID.rP);
      m_drivetrainPIDcontright.setI(MovePID.rI);
      m_drivetrainPIDcontright.setD(MovePID.rD);
      SmartDashboard.putData("Field", m_field);
      
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
    if ((lp != lP)) { m_drivetrainPIDcontleft.setP(lp); lP = lp; }
    if ((li != lI)) { m_drivetrainPIDcontleft.setI(li); lI = li; }
    if ((ld != lD)) { m_drivetrainPIDcontleft.setD(ld); lD = ld; }
    if ((rp != rP)) { m_drivetrainPIDcontright.setP(rp); rP = rp; }
    if ((ri != rI)) { m_drivetrainPIDcontright.setI(ri); rI = ri; }
    if ((rd != rD)) { m_drivetrainPIDcontright.setD(rd); rD = rd; }
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
    m_drive.tankDrive(-leftspeed, -rightspeed, true);
  }

  public void ArcadeDrive(double linear, double angular) {
    ChassisSpeeds chassisSpeeds = new ChassisSpeeds(linear, 0, angular);

    // Convert to wheel speeds
    DifferentialDriveWheelSpeeds wheelSpeeds = kinematics.toWheelSpeeds(chassisSpeeds);

    double leftVelocity = wheelSpeeds.leftMetersPerSecond;
    double rightVelocity = wheelSpeeds.rightMetersPerSecond;

    TankDrive(leftVelocity, rightVelocity);
  }

  public void SetMaxSpeed(double max) {
    maxspeed = max;
  }
    public void OverrideMax(){
      if (MovePID.LowSpeed < maxspeed ){
        maxspeed=MovePID.LowSpeed;  
      } else{
        maxspeed=MovePID.HighSpeed;
      }
    }
  public double getPitch() {
    return ahrs.getPitch();
  }

  public double getAngle() {
    return ahrs.getAngle();
  }

  public void resetHeading() {
    ahrs.reset();
  }

  public Pose2d getPose() {
    return odometry.getPoseMeters();
  }

  public double getDistance() {
    try {
      return (leftLeadEncoder.getPosition() -
          rightLeadEncoder.getPosition()) / 2;
    } catch (RuntimeException ex) {
      DriverStation.reportError("Error Getting Position" + ex.getMessage(), true);
      leftMotorControllerGroup.stopMotor();
      rightMotorControllerGroup.stopMotor();

      return 0;
    }
  }

  public void resetEncoders() {
    leftLeadEncoder.setPosition(0);
    rightLeadEncoder.setPosition(0);
  }

  @Override
  public void periodic() {
    m_drive.feed();

    odometry.update(ahrs.getRotation2d(), leftLeadEncoder.getPosition(), rightLeadEncoder.getPosition());
    m_field.setRobotPose(odometry.getPoseMeters());
    SmartDashboard.putNumber("Distance", getDistance());
    SmartDashboard.putNumber("Pitch", getPitch());
    SmartDashboard.putNumber("Angle", ahrs.getAngle());
    SmartDashboard.putNumber("Max Speed", maxspeed);
  }

  public void brake() {
    double rightarget = rightLeadEncoder.getPosition();
    double leftarget = leftLeadEncoder.getPosition();
    m_drivetrainPIDcontleft.setReference(leftarget, ControlType.kPosition);
    m_drivetrainPIDcontright.setReference(rightarget, ControlType.kPosition);
  }

  public void brakerelease() {
    rightLeadMotorController.stopMotor();
    leftLeadMotorController.stopMotor();
  }

  public DifferentialDriveWheelSpeeds getWheelSpeeds() {
    return new DifferentialDriveWheelSpeeds(leftLeadEncoder.getVelocity(), rightLeadEncoder.getVelocity());
  }

  public void tankDriveVolts(double leftVolts, double rightVolts) {
    leftMotorControllerGroup.setVoltage(leftVolts);
    rightMotorControllerGroup.setVoltage(rightVolts);
    m_drive.feed();
  }
}
