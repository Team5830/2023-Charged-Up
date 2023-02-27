// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;
import frc.robot.subsystems.Wrist;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class MoveWristDash extends CommandBase {
  /** Creates a new MoveWristDash. */
  Wrist wrist;
  public MoveWristDash(Wrist m_wrist) {
    // Use addRequirements() here to declare subsystem dependencies.
    wrist = m_wrist;
    addRequirements(wrist);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    wrist.updatePID();
    double target = SmartDashboard.getNumber("WristTarget", wrist.Position());
    wrist.move(target);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double target = SmartDashboard.getNumber("WristTarget", wrist.Position());
    wrist.move(target);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    wrist.Stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    //return wrist.AtTarget();
    return false;
  }
}
