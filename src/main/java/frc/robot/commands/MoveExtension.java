package frc.robot.commands;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.*;

public class MoveExtension extends CommandBase {
    private ExtendArm m_extendarm;
    private double position;
    public MoveExtension (double positin, ExtendArm extendarm) {
        m_extendarm = extendarm;
        addRequirements(m_extendarm);
        position = positin;
    }
    @Override
    public void initialize() {
      m_extendarm.updatePID();
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
