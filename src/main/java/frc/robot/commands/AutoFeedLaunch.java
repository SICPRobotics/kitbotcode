package frc.robot.commands;



import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.subsystems.MotorSubsystem;
import frc.robot.subsystems.PWMLauncher;
import frc.robot.subsystems.Joe;
import frc.robot.subsystems.Intake;
import frc.robot.Constants;


public class AutoFeedLaunch extends Command{
    private PWMLauncher m_launcher;
    private Joe joe;
    private double startingTime;
    private double currentTime;
    private double elapsedTime;


    public AutoFeedLaunch(PWMLauncher m_launcher, Joe joe) { 
        this.m_launcher = m_launcher; 
        this.joe = joe;
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        startingTime = System.nanoTime()/1000000000;
        joe.setMotor(-.5);
        m_launcher.setBoth(1);
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        currentTime = System.nanoTime()/1000000000;
        elapsedTime = currentTime - startingTime;

       joe.setMotor(-.5);
       m_launcher.setBoth(1);

        if(elapsedTime > 1.5){
            m_launcher.setBoth(0);
            joe.setMotor(0);
        }
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        m_launcher.setBoth(0);
        joe.setMotor(0);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        if(elapsedTime > 1.5){
            return true;
        }
        
        return false;
    }

    
}