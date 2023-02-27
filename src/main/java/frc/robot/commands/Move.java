package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.Constants.MovePID;
import frc.robot.subsystems.DriveTrain;

/**
 * A command that will turn the robot to the specified angle.
 */

public class Move extends PIDCommand {
  /**
   * Moves robot to the distance measured on the LIDAR
   *
   * @param targetDistanceMeters The Target distance in Meters
   * @param drive                The drive subsystem to use
   * @param kMoveP               Proportional feedback parameter
   * @param kMoveI               Integral feedback parameter
   * @param kMoveD               Derivative feedback parameter
   */

  public Move(double targetDistanceMeters, DriveTrain drive) {
    super(new PIDController(MovePID.lP, MovePID.lI, MovePID.lD),
        drive::getDistance, targetDistanceMeters, output -> drive.TankDrive(output, output), drive);

    drive.updatePID();
    drive.resetEncoders();

    // Set the controller tolerance - the delta tolerance ensures the robot is
    // stationary at the
    // setpoint before it is considered as having reached the reference
    getController()
        .setTolerance(MovePID.lAlignTolerance, MovePID.lMaxAlignSpeed);
  }

  @Override
  public boolean isFinished() {
    // End when the controller is at the reference.
    return getController().atSetpoint();
  }
}
//done.