package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.subsystems.*;
import frc.robot.Constants.MovePID;

public class Positioning extends SequentialCommandGroup {
    private Arm m_arm;
    private Wrist m_wrist;
    private ExtendArm m_extend;
    private DriveTrain m_drive;
    private boolean wristPosition = false;

    public Positioning(Arm arm, Wrist wrist, ExtendArm extendarm, DriveTrain drive,  double armangle, double wristangle, double extensiondistance, boolean wristFirst) {
        this.m_arm =  arm;
        this.m_wrist = wrist;
        this.m_extend = extendarm;
        this.m_drive = drive;
        addRequirements(m_arm, m_wrist, m_extend);
        /*if (extensiondistance > 10.0){
            m_drive.SetMaxSpeed(MovePID.LowSpeed);
        } else {
            m_drive.SetMaxSpeed(MovePID.HighSpeed);
        }*/
        if(wristFirst) {
            addCommands(
                new MoveExtension(-3, extendarm).withTimeout(5),
                new MoveArm(armangle, m_arm).withTimeout(5),
                new MoveWrist(wristangle, m_wrist).withTimeout(5),
                new MoveExtension(extensiondistance, extendarm).withTimeout(5)/* 
                Commands.sequence(
                    new WaitCommand(0.25),
                    new MoveWrist(wristangle, m_wrist).withTimeout(5)
                    ),
                Commands.sequence(
                    new WaitCommand(0.25),
                    new MoveExtension(extensiondistance, extendarm).withTimeout(5)
                )*/
            );

        } else {
            addCommands(
                new MoveWrist(wristangle, m_wrist).withTimeout(5),
                new MoveExtension(-3, extendarm).withTimeout(5),
                new MoveArm(armangle, m_arm).withTimeout(5),    
                    //new WaitCommand(0.5),
                new MoveExtension(extensiondistance, extendarm).withTimeout(5)
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
