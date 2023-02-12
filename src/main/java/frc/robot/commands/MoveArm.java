package frc.robot.commands;
import frc.robot.subsystems.Arm;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class MoveArm extends CommandBase{
    private Arm m_arm;
    public MoveArm( Arm arm_in) {
        m_arm = arm_in;
        addRequirements(m_arm);
        m_arm.armtarget = SmartDashboard.getNumber("ArmTarget", 0);
        m_arm.moveArm();
    }
    @Override
    public boolean isFinished(){
        return m_arm.atTarget();
    }

}
