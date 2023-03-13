// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.Constants.LevelPID;
import frc.robot.subsystems.DriveTrain;

public class Level extends PIDCommand {
  /** Creates a new Level. */

  public Level(double LevelTarget, DriveTrain drive) {

    super(new PIDController(LevelPID.P, LevelPID.I, LevelPID.D),
        drive::getPitch, LevelTarget, output -> drive.TankDrive( -output, -output), drive);

    // Set the controller tolerance - the delta tolerance ensures the robot is
    // stationary at the
    // setpoint before it is considered as having reached the reference
    //drive.SetMaxSpeed(0.2);
    getController()
        .setTolerance(LevelPID.LevelTolerance, LevelPID.Level);

    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return getController().atSetpoint();
  }
}
