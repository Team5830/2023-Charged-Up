// RobotBuilder Version: 5.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

// ROBOTBUILDER TYPE: Command.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

import java.util.function.DoubleSupplier;
import frc.robot.commands.*;
import frc.robot.subsystems.*;
import frc.robot.Constants.*;
import edu.wpi.first.wpilibj2.command.InstantCommand;
// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS

// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS

/**
 *
 */
public class AutonomousCommand2 extends SequentialCommandGroup {

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS

    public AutonomousCommand2(Arm m_arm, Wrist m_wrist, ExtendArm m_extension, DriveTrain m_driveTrain,
            Pneumatics m_pneumatics) {
        addCommands(
                new Positioning(m_arm, m_wrist, m_extension, m_driveTrain, Position4.armAngle, Position4.wristAngle,
                        Position4.extensionDistance, false),
                new OpenManipulator(m_pneumatics),
                new WaitCommand(0.75),
                new CloseManipulator(m_pneumatics),
                new Positioning(m_arm, m_wrist, m_extension, m_driveTrain, Position1.armAngle, Position1.wristAngle,
                        Position1.extensionDistance, true),
                new Move(-2.5, m_driveTrain),
                new Balance(m_driveTrain));
    }

}
