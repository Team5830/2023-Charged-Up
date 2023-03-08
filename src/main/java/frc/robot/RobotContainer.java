// RobotBuilder Version: 5.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

// ROBOTBUILDER TYPE: RobotContainer.

package frc.robot;

import frc.robot.Constants.*;
import frc.robot.commands.*;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.button.CommandGenericHID;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj.Joystick;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS


/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {

    private static RobotContainer m_robotContainer = new RobotContainer();

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    // The robot's subsystems
    public final Pneumatics m_pneumatics = new Pneumatics();
    public final DriveTrain m_driveTrain = new DriveTrain();
    public final Arm m_arm = new Arm();
    public final Wrist m_wrist = new Wrist();
    public final ExtendArm m_extension = new ExtendArm();

  // Joysticks
  private final Joystick rightJoystick = new Joystick(1);
  private final Joystick leftJoystick = new Joystick(0);
  private final CommandGenericHID xroller = new CommandGenericHID(2);
  public double maxspeed = 1.0;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

  
  // A chooser for autonomous commands
  SendableChooser<Command> m_chooser = new SendableChooser<>();

  /**
  * The container for the robot.  Contains subsystems, OI devices, and commands.
  */
  private RobotContainer() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=SMARTDASHBOARD
    // Smartdashboard Subsystems
    // SmartDashboard.putData(m_pneumatics);
    SmartDashboard.putData(m_driveTrain);
    SmartDashboard.putData(m_arm);


    // SmartDashboard Buttons
    SmartDashboard.putData("Autonomous Command", new AutonomousCommand());
    SmartDashboard.putData("Open Manipulator", new OpenManipulator( m_pneumatics ));
    SmartDashboard.putData("Close Manipulator", new CloseManipulator( m_pneumatics ));
    SmartDashboard.putData("moveArm", new MoveArmDash(m_arm));
    SmartDashboard.putData("extend", new MoveExtensionDash(m_extension));
    SmartDashboard.putData("moveWrist", new MoveWristDash(m_wrist));
    SmartDashboard.putData("Brake", new Brake(m_driveTrain));
    SmartDashboard.putData("Brake Release", new InstantCommand(m_driveTrain::brakerelease));
    SmartDashboard.putData("Position1", new Positioning(m_arm,m_wrist,m_extension,m_driveTrain, Position1.armAngle, Position1.wristAngle, Position1.extensionDistance));
    SmartDashboard.putData("Position2", new Positioning(m_arm,m_wrist,m_extension, m_driveTrain, Position2.armAngle, Position2.wristAngle, Position2.extensionDistance));
    SmartDashboard.putData("Position3", new Positioning(m_arm,m_wrist,m_extension, m_driveTrain, Position3.armAngle, Position3.wristAngle, Position3.extensionDistance));
    SmartDashboard.putData("Position4", new Positioning(m_arm,m_wrist,m_extension, m_driveTrain, Position4.armAngle, Position4.wristAngle, Position4.extensionDistance));
    SmartDashboard.putData("Position5", new Positioning(m_arm,m_wrist,m_extension, m_driveTrain, Position5.armAngle, Position5.wristAngle, Position5.extensionDistance));
    //SmartDashboard.putData("Move", new MoveDash( m_driveTrain) );

    SmartDashboard.putData("Move", new Move(1.0, m_driveTrain) );
    SmartDashboard.putData("Turn", new TurnDash( m_driveTrain) );
    SmartDashboard.putNumber("ArmTarget", 0);
    SmartDashboard.putNumber("Arm P", Constants.ArmPID.P);
    SmartDashboard.putNumber("Arm I", Constants.ArmPID.I);
    SmartDashboard.putNumber("Arm D", Constants.ArmPID.D);
    SmartDashboard.putNumber("Arm FF", Constants.ArmPID.FF);
    SmartDashboard.putNumber("WristTarget", 0);
    SmartDashboard.putNumber("Wrist P", Constants.WristPID.P);
    SmartDashboard.putNumber("Wrist I", Constants.WristPID.I);
    SmartDashboard.putNumber("Wrist D", Constants.WristPID.D);
    SmartDashboard.putNumber("Wrist FF", Constants.WristPID.FF);
    SmartDashboard.putNumber("Extend P", ExtendPID.P);
    SmartDashboard.putNumber("Extend I", ExtendPID.I);
    SmartDashboard.putNumber("Extend D", ExtendPID.D);
    SmartDashboard.putNumber("Extend FF", ExtendPID.FF);
    SmartDashboard.putNumber("Extension Target",0);
    SmartDashboard.putNumber("Move Target", 0);
    SmartDashboard.putNumber("Turn Target", 0);

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=SMARTDASHBOARD

    // Configure the button bindings
    configureButtonBindings();
    //m_driveTrain.setDefaultCommand(new Drive( leftJoystick.getY(),  rightJoystick.getY(), m_driveTrain));
    m_driveTrain.setDefaultCommand(new Drive(leftJoystick::getY, rightJoystick::getY, m_driveTrain));
    
    // Configure default commands
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=SUBSYSTEM_DEFAULT_COMMAND


    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=SUBSYSTEM_DEFAULT_COMMAND

    // Configure autonomous sendable chooser
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=AUTONOMOUS

    //m_chooser.setDefaultOption("Autonomous Command", new AutonomousCommand());

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=AUTONOMOUS

    SmartDashboard.putData("Auto Mode", m_chooser);
  }

  public static RobotContainer getInstance() {
    return m_robotContainer;
  }

  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link xroller}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=BUTTONS
      // Create some buttons
      Trigger button1 = xroller.button(1);
      Trigger button2 = xroller.button(2);
      Trigger button3 = xroller.button(3);
      Trigger button4 = xroller.button(4);
      Trigger button5 = xroller.button(5);
      Trigger button6 = xroller.button(6);
      Trigger button7 = xroller.button(7);
      Trigger button8 = xroller.button(8);
      //Driver right Joystick max override
      Trigger Driverbutton5 = new JoystickButton(rightJoystick, 5);
      Trigger Driverbutton1 = new JoystickButton(rightJoystick, 1);

      button3.onTrue(new Positioning(m_arm,m_wrist,m_extension, m_driveTrain, Position1.armAngle, Position1.wristAngle, Position1.extensionDistance));
      button4.onTrue(new Positioning(m_arm,m_wrist,m_extension, m_driveTrain, Position2.armAngle, Position2.wristAngle, Position2.extensionDistance));
      button1.onTrue(new Positioning(m_arm,m_wrist,m_extension, m_driveTrain, Position3.armAngle, Position3.wristAngle, Position3.extensionDistance));
      button2.onTrue(new Positioning(m_arm,m_wrist,m_extension, m_driveTrain, Position4.armAngle, Position4.wristAngle, Position4.extensionDistance));
      button5.onTrue(new Positioning(m_arm,m_wrist,m_extension, m_driveTrain, Position5.armAngle, Position5.wristAngle, Position5.extensionDistance));
      // button6.whileTrue(new InstantCommand( m_wrist::decrement).repeatedly());
      // button8.whileTrue(new InstantCommand( m_wrist::increment).repeatedly());
      //change button 7
      button7.whileTrue(new Positioning(m_arm,m_wrist,m_extension, m_driveTrain, Position6.armAngle, Position6.wristAngle, Position6.extensionDistance));
      Driverbutton1.whileTrue(new ConditionalCommand(new CloseManipulator(m_pneumatics), new OpenManipulator(m_pneumatics), m_pneumatics::manipulator_open));
      xroller.povDown().and(button6).whileTrue( new InstantCommand( m_wrist::increment).repeatedly());
      xroller.povDown().and(button8).whileTrue(new InstantCommand( m_wrist::decrement).repeatedly());
      xroller.povRight().and(button6).whileTrue( new InstantCommand( m_extension::increment).repeatedly());
      xroller.povRight().and(button8).whileTrue(new InstantCommand( m_extension::decrement).repeatedly());
      xroller.povLeft().and(button6).whileTrue(new InstantCommand( m_arm::increment).repeatedly());
      xroller.povLeft().and(button8).whileTrue(new InstantCommand( m_arm::decrement).repeatedly());
      Driverbutton5.onTrue(new InstantCommand(m_driveTrain::OverrideMax));
    
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=BUTTONS
  }

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=FUNCTIONS
    
public Joystick getLeftJoystick() {
        return leftJoystick;
    }

public Joystick getRightJoystick() {
        return rightJoystick;
    }


    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=FUNCTIONS

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
  */
  public Command getAutonomousCommand() {
    // The selected command will be run in autonomous
    //return m_chooser.getSelected();
    return new SequentialCommandGroup(
      new Positioning(m_arm,m_wrist,m_extension, m_driveTrain, Position4.armAngle, Position4.wristAngle, Position4.extensionDistance),
      new OpenManipulator( m_pneumatics ),
      new WaitCommand(0.75),
      new CloseManipulator( m_pneumatics ),
      new Positioning(m_arm,m_wrist,m_extension,m_driveTrain, Position1.armAngle, Position1.wristAngle, Position1.extensionDistance),
      new Move(-2.5, m_driveTrain)
    );
  }
  

}

