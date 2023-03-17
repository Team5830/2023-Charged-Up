package frc.robot.commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.*;

public class Positioning extends SequentialCommandGroup {
    private Arm m_arm;
    private Wrist m_wrist;
    private ExtendArm m_extend;

    public Positioning(Arm arm, Wrist wrist, ExtendArm extendarm, DriveTrain m_driveTrain, double armangle, double wristangle, double extensiondistance, boolean wristFirst) {
        this.m_arm =  arm;
        this.m_wrist = wrist;
        this.m_extend = extendarm;
        addRequirements(m_arm, m_wrist, m_extend);
        
        if(wristFirst) {
            addCommands(
                new MoveExtension(-3, extendarm, m_driveTrain).withTimeout(1),
                new MoveWrist(wristangle, m_wrist).withTimeout(1),  
                new MoveArm(armangle, m_arm).withTimeout(1),  
                    //new WaitCommand(0.5),
                new MoveExtension(extensiondistance, extendarm, m_driveTrain).withTimeout(1)
            );

        } else {
            addCommands(
                new MoveExtension(-3, extendarm, m_driveTrain).withTimeout(1),
                new MoveArm(armangle, m_arm).withTimeout(1),
                new MoveWrist(wristangle, m_wrist).withTimeout(1),    
                    //new WaitCommand(0.5),
                new MoveExtension(extensiondistance, extendarm, m_driveTrain).withTimeout(1)
            );
        }
    }
}
