package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.Constants.TurnPID;
import frc.robot.subsystems.DriveTrain;

/** A command that will turn the robot to the specified angle. */
public class Turn extends PIDCommand {
  /**
   * Turns to robot to the specified angle.
   *
   * @param targetAngleDegrees The angle to turn to
   * @param drive The drive subsystem to use
   */
  public Turn(double targetAngleDegrees, DriveTrain drive) {
    super(
        new PIDController(TurnPID.P, TurnPID.I, TurnPID.D),
        // Close loop on heading
        drive::getAngle,
        // Set reference to target
        targetAngleDegrees,
        // Pipe output to turn robot
        output -> drive.TankDrive(output, -output),
        // Require the drive
        drive);
    drive.resetHeading();
    // Set the controller to be continuous (because it is an angle controller)
    getController().enableContinuousInput(-180, 180);
    // Set the controller tolerance - the delta tolerance ensures the robot is stationary at the
    // setpoint before it is considered as having reached the reference
    getController()
        .setTolerance(TurnPID.Tolerance, TurnPID.TurnRateTolerance);
  }

  @Override
  public boolean isFinished() {
    // End when the controller is at the reference.
    return getController().atSetpoint();
  }
}
