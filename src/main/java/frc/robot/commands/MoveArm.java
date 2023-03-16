<<<<<<< HEAD
package frc.robot.commands;
import frc.robot.subsystems.Arm;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class MoveArm extends CommandBase{
    private Arm m_arm;
    public MoveArm( Arm arm_in) {
        m_arm = arm_in;
        addRequirements(m_arm);
        m_arm.armtarget = SmartDashboard.getNumber("ArmTarget", 0);
        m_arm.moveArm();
    }
    @Override
    public boolean isFinished(){
        return m_arm.atTarget();
    }

=======
// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;
import frc.robot.subsystems.Arm;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class MoveArm extends CommandBase {
  /** Creates a new MoveArm. */
  Arm arm;
  double target;
  public MoveArm(double degrees, Arm m_arm) {
    target = degrees;
    // Use addRequirements() here to declare subsystem dependencies.
    arm = m_arm;
    addRequirements(arm);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    arm.updatePID();
    arm.move(target);
  }

  // Called every time the scheduler runs while the command is scheduled.
  //@Override
  //public void execute() {
  //
  //}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    // Called once the command ends or is interrupted.
    arm.Stop();
    
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    //return arm.AtTarget();
    return false;
  }
>>>>>>> ab4eb6f499f2ddcd4083987f3543491328963707
}
