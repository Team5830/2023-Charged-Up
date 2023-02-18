package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;

public class Brake extends CommandBase{
    DriveTrain drivetrain;
    public Brake(DriveTrain m_DriveTrain){
        drivetrain = m_DriveTrain;
        addRequirements(drivetrain);
    }
    @Override
    public void initialize() {
        drivetrain.updatePID();
        drivetrain.brake();
    }
    @Override
    public void end(boolean interrupted) {
        drivetrain.brakerelease();
    }
}