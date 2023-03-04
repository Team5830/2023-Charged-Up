// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;
import frc.robot.subsystems.Arm;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.DriverStation;

public class MoveArmDash extends CommandBase {
  /** Creates a new MoveArmDash. */
  Arm arm;
  public MoveArmDash(Arm m_arm) {
    // Use addRequirements() here to declare subsystem dependencies.
    arm = m_arm;
    addRequirements(arm);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    arm.updatePID();
    double target = SmartDashboard.getNumber("ArmTarget", arm.Position());

    DriverStation.reportWarning(String.format("Target %f",target),false);
    arm.move(target);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double target = SmartDashboard.getNumber("ArmTarget", arm.Position());
    arm.move(target);
  }

  // Called once the command ends or is interrupted.
  //@Override
  //public void end(boolean interrupted) {}
  @Override
  public void end(boolean interrupted) {
    // Called once the command ends or is interrupted.
    //arm.Stop();
    
  }
  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    //return arm.AtTarget();
    return false;
  }
}
