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
        drivetrain.SetMaxSpeed(climbspeed);
        if (drivetrain.getPitch()>-8 && drivetrain.getAngle()<8){
            drivetrain.brake();
        }
        if(drivetrain.getPitch()<-8){
            drivetrain.TankDrive(-climbspeed, -climbspeed);
        }
        if(drivetrain.getPitch()>8){
            drivetrain.TankDrive(climbspeed, climbspeed);
        }
    }
    @Override
    public boolean isFinished() {
        if(Math.abs(drivetrain.getPitch())<8.0) {
            return true;
                }
        else {
            return false;
        }
    }
}
