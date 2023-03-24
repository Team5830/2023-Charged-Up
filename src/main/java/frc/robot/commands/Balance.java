package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.*;

public class Balance extends CommandBase {
    DriveTrain drivetrain;
    double climbspeed = 0.3;
    int stable = 0;
    public Balance(DriveTrain m_DriveTrain){
        drivetrain = m_DriveTrain;
        addRequirements(drivetrain);
    }
    @Override
    public void execute() {
        drivetrain.SetMaxSpeed(climbspeed);
        if (drivetrain.getPitch()>-12 && drivetrain.getAngle()<12){
            drivetrain.brake();
            stable = stable+1;
        }
        if(drivetrain.getPitch()<-12){
            drivetrain.TankDrive(-climbspeed, -climbspeed);
            stable = 0;
        }
        if(drivetrain.getPitch()>12){
            drivetrain.TankDrive(climbspeed, climbspeed);
            stable = 0;
        }
        if(Math.abs(drivetrain.getPitch())<4 && drivetrain.getAngle()>-4){
            drivetrain.SetMaxSpeed(0.125);
        }
    }
    @Override
    public boolean isFinished() {
        if(Math.abs(drivetrain.getPitch())<12.0 && stable>75) {
            return true;
                }
        else {
            return false;
        }
    }
}
