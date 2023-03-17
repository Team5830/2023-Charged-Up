package frc.robot.commands;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.*;
import frc.robot.Constants.MovePID;;

public class MoveExtension extends CommandBase {
    private ExtendArm m_extendarm;
    private DriveTrain m_drive;
    private double position;
    public MoveExtension (double positin, ExtendArm extendarm, DriveTrain drivetrain) {
        m_extendarm = extendarm;
        addRequirements(m_extendarm);
        position = positin;
        m_drive = drivetrain;
    }
    @Override
    public void initialize() {
      m_extendarm.updatePID();
      if (position> 10.0){
        m_drive.SetMaxSpeed(MovePID.LowSpeed);
      }else{
        m_drive.SetMaxSpeed(MovePID.HighSpeed);
      }
      m_extendarm.move(position);
      System.out.print("Started MoveExtension Command");
    }
    /* 
    @Override
    public void execute() {
      m_extendarm.move(position);
    }
    */
    @Override
    public void end(boolean interrupted) {
      //m_extendarm.Stop();
      System.out.print("Stopped MoveExtension Command");
      SmartDashboard.putBoolean("Extended", m_extendarm.extended);
      SmartDashboard.putNumber("Extension Position", m_extendarm.getPosition());
    }
    @Override
    public boolean isFinished() {
      return m_extendarm.AtTarget();
    }
}
