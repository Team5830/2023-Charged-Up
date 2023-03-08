package frc.robot.commands;

//import edu.wpi.first.wpilibj.DriverStation; Ha Ha Ha, Good Bye Sucker!
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.*;

public class Positioning extends SequentialCommandGroup {
    private Arm m_arm;
    private Wrist m_wrist;
    private ExtendArm m_extend;
    private DriveTrain m_drive;

    public Positioning(Arm arm, Wrist wrist, ExtendArm extendarm, DriveTrain drive,  double armangle, double wristangle, double extensiondistance) {
        this.m_arm =  arm;
        this.m_wrist = wrist;
        this.m_extend = extendarm;
        this.m_drive = drive;
        addRequirements(m_arm, m_wrist, m_extend, m_drive);
        if (10.0<extensiondistance){
            m_drive.SetMaxSpeed(0.5);
        }
        else{
            m_drive.SetMaxSpeed(1);
        }
        addCommands(
            new MoveExtension(0, extendarm).withTimeout(10),
            Commands.parallel(
                new MoveArm(armangle, m_arm).withTimeout(10),
                new MoveWrist(wristangle, m_wrist).withTimeout(10)
            ),
            new MoveExtension(extensiondistance, extendarm).withTimeout(10)
        );
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
