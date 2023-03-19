package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.subsystems.*;
import frc.robot.Constants.MovePID;

public class Positioning extends SequentialCommandGroup{
    private Arm m_arm;
    private Wrist m_wrist;
    private ExtendArm m_extend;
    private DriveTrain m_drive;
    private MoveWrist wristcommand;
    private MoveArm armcommand;


    public Positioning(Arm arm, Wrist wrist, ExtendArm extendarm, DriveTrain drive,  double armangle, double wristangle, double extensiondistance, boolean wristFirst) {
        this.m_arm =  arm;
        this.m_wrist = wrist;
        this.m_extend = extendarm;
        this.m_drive = drive;
        if (extensiondistance > 10.0){
            m_drive.SetMaxSpeed(MovePID.LowSpeed);
        } else {
            m_drive.SetMaxSpeed(MovePID.HighSpeed);
        }
        //armcommand = new MoveArm(armangle, m_arm);
        if(wristFirst) {
            addCommands(
                Commands.parallel(
                    new MoveWrist(wristangle, m_wrist),
                    Commands.sequence(
                        new WaitUntilCommand(m_wrist::Safe), 
                        new MoveExtension(extensiondistance, m_extend)
                    ),
                    Commands.sequence(
                        new WaitUntilCommand(m_wrist::Safe),
                        new MoveArm(armangle, m_arm)
                    )
                )
            );
        } else {
            addCommands(
                new MoveExtension(-3, m_extend),
                Commands.parallel(
                    new MoveArm(armangle, m_arm),
                    Commands.sequence(
                        new WaitUntilCommand(m_arm::Safe),
                        new MoveExtension(extensiondistance, m_extend)
                    ),
                    Commands.sequence(
                        new WaitUntilCommand(m_arm::Safe),
                        new MoveWrist(wristangle, m_wrist) 
                    )
                )
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
