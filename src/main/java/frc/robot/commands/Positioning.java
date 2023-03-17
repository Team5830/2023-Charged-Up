package frc.robot.commands;

//import edu.wpi.first.wpilibj.DriverStation; Ha Ha Ha, Good Bye Sucker!
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.subsystems.*;

public class Positioning extends SequentialCommandGroup {
    private Arm m_arm;
    private Wrist m_wrist;
    private ExtendArm m_extend;
    private DriveTrain m_drive;

    public Positioning(Arm arm, Wrist wrist, ExtendArm extendarm, DriveTrain drive,  double armangle, double wristangle, double extensiondistance, boolean wristFirst) {
        this.m_arm =  arm;
        this.m_wrist = wrist;
        this.m_extend = extendarm;
        this.m_drive = drive;
        addRequirements(m_arm, m_wrist, m_extend);
        if (extensiondistance > 10.0){
            m_drive.SetMaxSpeed(0.2);
        } else {
            m_drive.SetMaxSpeed(0.9);
        }
        if(wristFirst) {
            addCommands(
                new MoveExtension(-3, extendarm).withTimeout(1),
                new MoveWrist(wristangle, m_wrist).withTimeout(1),  
                new MoveArm(armangle, m_arm).withTimeout(1),  
                    //new WaitCommand(0.5),
                new MoveExtension(extensiondistance, extendarm).withTimeout(1)
            );

        } else {
            addCommands(
                new MoveExtension(-3, extendarm).withTimeout(1),
                new MoveArm(armangle, m_arm).withTimeout(1),
                new MoveWrist(wristangle, m_wrist).withTimeout(1),    
                    //new WaitCommand(0.5),
                new MoveExtension(extensiondistance, extendarm).withTimeout(1)
            );
        }
        /* 
        if (m_extend.extended){
            addCommands(
                new ScheduleCommand( new MoveExtension(extensiondistance, extendarm)).andThen(Commands.waitSeconds(2)),
                Commands.parallel(
                    new MoveArm(armangle, m_arm),
                    new MoveWrist(wristangle, m_wrist)
                    )
                );
        } else{
            addCommands(
                new ScheduleCommand( Commands.parallel(
                        new MoveArm(armangle, m_arm),
                        new MoveWrist(wristangle, m_wrist))
                    ).andThen(Commands.waitSeconds(2)),
                    new MoveExtension(extensiondistance, extendarm)
                );
        }
        //System.out.print(String.format("Target Arm: %.2f Extension: %.2f Wrist: %.2f \n",armangle,extensiondistance, wristangle));
        
        */
    }
}
