package frc.robot.commands;

import frc.robot.subsystems.Wrist;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class MoveWrist extends CommandBase {
  /** Creates a new Movewrist. */
  Wrist wrist;
  double target;
  public MoveWrist(double degrees, Wrist m_wrist) {
    target = degrees;
    // Use addRequirements() here to declare subsystem dependencies.
    wrist = m_wrist;
    addRequirements(wrist);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    wrist.updatePID();
    wrist.move(target);
    System.out.print("Started MoveWrist Command");
  }

  // Called every time the scheduler runs while the command is scheduled.
  //@Override
  //public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    System.out.print("Stopped MoveWrist Command");
    SmartDashboard.putNumber("WristPosition", wrist.Position());
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return wrist.AtTarget();
  }
}
 