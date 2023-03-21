package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.subsystems.*;
import frc.robot.Constants.MovePID;

public class PositioningSeq extends SequentialCommandGroup {
    private Arm m_arm;
    private Wrist m_wrist;
    private ExtendArm m_extend;
    private DriveTrain m_drive;

    public PositioningSeq(Arm arm, Wrist wrist, ExtendArm extendarm, DriveTrain drive,  double armangle, double wristangle, double extensiondistance, boolean wristFirst) {
        this.m_arm =  arm;
        this.m_wrist = wrist;
        this.m_extend = extendarm;
        this.m_drive = drive;
        if(wristFirst) {
            addCommands(
                new MoveExtension(-5, extendarm).withTimeout(2),
                new MoveWrist(wristangle, m_wrist).withTimeout(2),  
                new MoveArm(armangle, m_arm).withTimeout(2),  
                new MoveExtension(extensiondistance, extendarm).withTimeout(2)
            );

        } else {
            addCommands(
                new MoveExtension(-5, extendarm).withTimeout(2),
                new MoveArm(armangle, m_arm).withTimeout(2),
                new MoveWrist(wristangle, m_wrist).withTimeout(2),    
                new MoveExtension(extensiondistance, extendarm).withTimeout(2)
            );
        }
        
    }
}
