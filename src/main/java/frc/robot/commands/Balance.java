package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.*;

public class Balance extends CommandBase {
    DriveTrain drivetrain;
    double climbspeed = 0.3;
    public Balance(DriveTrain m_DriveTrain){
        drivetrain = m_DriveTrain;
        addRequirements(drivetrain);
    }
    @Override
    public void execute() {
        if (drivetrain.getPitch()>-12 && drivetrain.getAngle()<12){
            drivetrain.brake();
        }
        if(drivetrain.getPitch()<-12){
            drivetrain.TankDrive(-climbspeed, -climbspeed);
        }
        if(drivetrain.getPitch()>12){
            drivetrain.TankDrive(climbspeed, climbspeed);
        }
    }
}
