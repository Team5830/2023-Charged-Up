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


import frc.robot.commands.*;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup; 
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DriveConstants;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SerialPort;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;

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
    private RelativeEncoder leftFollowEncoder;
    private RelativeEncoder rightLeadEncoder;
    private RelativeEncoder rightFollowEncoder;
    
    /**
    *
    */
    public DriveTrain() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS


    try{
      ahrs = new AHRS(SerialPort.Port.kUSB1);

      leftLeadMotorController = new CANSparkMax(DriveConstants.kLeftMotor1Port , CANSparkMax.MotorType.kBrushless);
      leftLeadMotorController.restoreFactoryDefaults();
      leftLeadEncoder = leftLeadMotorController.getEncoder();
      leftLeadEncoder.setPositionConversionFactor(18.84*2.54/8.33);
      leftFollowMotorController = new CANSparkMax(DriveConstants.kLeftMotor2Port, CANSparkMax.MotorType.kBrushless);
      leftFollowMotorController.restoreFactoryDefaults();
      leftFollowEncoder = leftFollowMotorController.getEncoder();

      leftFollowMotorController.follow(leftLeadMotorController);

      leftMotorControllerGroup = new MotorControllerGroup(leftLeadMotorController, leftFollowMotorController);
      addChild("Left Motor Controller Group", leftMotorControllerGroup);
      //leftMotorControllerGroup.setInverted(true);


      rightLeadMotorController = new CANSparkMax(DriveConstants.kRightMotor1Port, CANSparkMax.MotorType.kBrushless);
      rightLeadMotorController.restoreFactoryDefaults();
      rightLeadEncoder = rightLeadMotorController.getEncoder();
      rightLeadEncoder.setPositionConversionFactor(18.84*2.54/8.33);
      rightFollowMotorController = new CANSparkMax(DriveConstants.kRightMotor2Port, CANSparkMax.MotorType.kBrushless);
      rightFollowMotorController.restoreFactoryDefaults();
      rightFollowEncoder = rightFollowMotorController.getEncoder();

      rightFollowMotorController.follow(rightLeadMotorController);

      rightMotorControllerGroup = new MotorControllerGroup(rightLeadMotorController, rightFollowMotorController);
      rightMotorControllerGroup.setInverted(true);
      m_drive = new DifferentialDrive(leftMotorControllerGroup, rightMotorControllerGroup);
    } catch (RuntimeException ex) {
      DriverStation.reportError("Error Configuring Drivetrain" + ex.getMessage(), true);
    }
    addChild("Right Motor Controller Group", rightMotorControllerGroup);
    }

    public void TankDrive(double leftspeed, double rightspeed) {
        if (leftspeed > 1.0) {
          leftspeed = 1.0;
        }
        if (leftspeed < -1.0) {
          leftspeed = -1.0;
        }
        if (rightspeed > 1.0) {
          rightspeed = 1.0;
        }
        if (rightspeed < -1.0) {
          rightspeed = -1.0;
        }
        // System.out.println("%0.2f %0.2f",leftspeed,rightspeed);
        m_drive.tankDrive(-leftspeed, -rightspeed, true);
      }

    public double getPitch() {
        return ahrs.getPitch();
    }

    public double getDistance() {
        return (
            leftLeadEncoder.getPosition() +
            leftFollowEncoder.getPosition() +
            rightLeadEncoder.getPosition() +
            rightFollowEncoder.getPosition()
        ) / 4;
      }
    
    public void resetEncoders() {
        leftLeadEncoder.setPosition(0);
        leftFollowEncoder.setPosition(0);
        rightLeadEncoder.setPosition(0);
        rightFollowEncoder.setPosition(0);
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Pitch", getPitch());
        SmartDashboard.putNumber("Distance", getDistance());
        SmartDashboard.putNumber("rightposition", rightLeadEncoder.getPosition());
        SmartDashboard.putNumber("leftposition", leftLeadEncoder.getPosition());
    }
}

