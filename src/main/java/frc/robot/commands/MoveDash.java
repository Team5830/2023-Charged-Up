package frc.robot.commands;

//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;

public class MoveDash extends CommandBase {
    /**
   * Moves robot to the distance measured on the encoders
   */
  DriveTrain drive;
  double target;
  public MoveDash( DriveTrain m_drive) {
    drive = m_drive;
    addRequirements(drive);
    drive.updatePID();
    target = 1.0;
  }
  @Override
  public void initialize() {
    drive.updatePID();
    target = 1.0;
    //target = SmartDashboard.getNumber("Move Target", drive.getDistance());
    //drive.resetEncoders(); allready in move
    System.out.print(String.format("Move Target %f",target));
    new Move(target, drive);
  }
  
}
