package frc.robot.commands;

//import edu.wpi.first.wpilibj.DriverStation; Ha Ha Ha, Good Bye Sucker!
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.*;

public class Positioning extends ParallelDeadlineGroup {
    private Arm m_arm;
    private Wrist m_wrist;
    private ExtendArm m_extend;
    private DriveTrain m_drive;

    public Positioning(Arm arm, Wrist wrist, ExtendArm extendarm, DriveTrain drive,  double armangle, double wristangle, double extensiondistance) {
        super(new RunCommand(drive::feed, drive));
        this.m_arm =  arm;
        this.m_wrist = wrist;
        this.m_extend = extendarm;
        this.m_drive = drive;
        addRequirements(m_arm, m_wrist, m_extend, m_drive);
    
        if (extensiondistance > 10.0){
            m_drive.SetMaxSpeed(0.2);
        } else {
            m_drive.SetMaxSpeed(0.7);
        }
        //addCommands(    new RunCommand(m_drive::feed, m_drive) );
        if (m_arm.Position() > 100 ){
            setDeadline(
                Commands.sequence(
                    new MoveWrist(wristangle, m_wrist).withTimeout(5),
                    new MoveExtension(-3, extendarm).withTimeout(5),
                    new MoveArm(armangle, m_arm).withTimeout(5),
                    //new WaitCommand(0.5),
                    new MoveExtension(extensiondistance, extendarm).withTimeout(5)
                )
            );
        } else {
            setDeadline(
                Commands.sequence(
                    new MoveExtension(-3, extendarm).withTimeout(5),
                    new MoveWrist(wristangle, m_wrist).withTimeout(5),    
                    new MoveArm(armangle, m_arm).withTimeout(5),
                        //new WaitCommand(0.5),
                    new MoveExtension(extensiondistance, extendarm).withTimeout(5)
                )
            );
        }
    } 
    
}
