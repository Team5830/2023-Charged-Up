package frc.robot.commands;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class MoveExtension extends CommandBase {
    private ExtendArm m_extendarm;
    private double position;
    public MoveExtension (double positin, ExtendArm extendarm) {
        m_extendarm = extendarm;
        addRequirements(extendarm);
        position =positin;
    }
    @Override
    public void initialize() {
      m_extendarm.updatePID();
      m_extendarm.move(position);
    }

    @Override
    public void execute() {
      m_extendarm.move(position);
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
