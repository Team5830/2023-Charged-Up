package frc.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
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
            addCommands(new MoveExtension(extensiondistance, extendarm));
        }
        //DriverStation.reportWarning(String.format("Arm: %0.2d Wrist: %0.2d Extension: %0.2d",armangle,wristangle,extensiondistance),false);
        addCommands(
            new MoveArm(armangle, m_arm),
            new MoveWrist(wristangle, m_wrist),
            new MoveExtension(extensiondistance, extendarm)
            );
    
    }
}
