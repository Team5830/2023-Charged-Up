package frc.robot.commands;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class MoveExtensionDash extends CommandBase {
    private ExtendArm m_extendarm;
    public MoveExtensionDash (ExtendArm extendarm) {
        m_extendarm = extendarm;
        addRequirements(extendarm);
    }
    @Override
    public void initialize() {
      double target = SmartDashboard.getNumber("Extension Target", m_extendarm.getPosition());
      m_extendarm.updatePID();
      DriverStation.reportWarning(String.format("Target %f",target),false);
      m_extendarm.move(target);
    }

    @Override
    public void execute() {
      double target = SmartDashboard.getNumber("Extension Target", m_extendarm.getPosition());
      m_extendarm.move(target);
    }

    @Override
    public void end(boolean interrupted) {
      m_extendarm.Stop();
      
    }
    @Override
    public boolean isFinished() {
      return false;
    }
}
