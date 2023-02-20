package frc.robot.commands;
import java.lang.reflect.InaccessibleObjectException;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.*;

public class Positioning extends SequentialCommandGroup {
    private Arm m_arm;
    private Wrist m_wrist;
    private ExtendArm m_extend;

    public Positioning(Arm arm, Wrist wrist, ExtendArm extendarm, double armangle, double wristangle, double extensiondistance) {
        this.m_arm =  arm;
        this.m_wrist = wrist;
        this.m_extend = extendarm;
        addRequirements(arm, wrist, extendarm);
        if (m_extend.getPosition()>0.1){
            addCommands(new MoveExtension(extendarm, extensiondistance));
        }
        addCommands(
            new MoveArm(armangle, m_arm),
            new MoveWrist(wristangle, m_wrist),
            new MoveExtension(extendarm, extensiondistance)
            );
    
    }


}
