package frc.robot.commands;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
public class TimerCommand extends CommandBase{
    private Timer timer;
    public TimerCommand(){

    }
    @Override
    public void initialize() {
        timer.start();
    }
    @Override
    public void end(boolean interrupted) {
        timer.stop();
        System.out.print(String.format("Timer: %f seconds",timer.get()));
    }
}
