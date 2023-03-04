package frc.robot.commands;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;

    
public class TurnDash extends CommandBase {
        /**
       * Moves robot to the distance measured on the encoders
       */
      DriveTrain drive;
      double targetAngle;
      public TurnDash( DriveTrain m_drive) {
        drive = m_drive;
        addRequirements(drive);
      }
      @Override
      public void initialize() {
        drive.updatePID();
        targetAngle = SmartDashboard.getNumber("Turn Target", drive.getAngle());
        System.out.print(String.format("Turn AngleTarget %f",targetAngle));
        new Turn(targetAngle, drive);
      }
      
}
        
