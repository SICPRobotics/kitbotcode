package frc.robot.commands;



import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Joe;
import frc.robot.subsystems.CANDrivetrain;

import frc.robot.subsystems.PWMLauncher;

public class EasyAuto extends CommandBase{
    private final CANDrivetrain mDriveTrain;
    private final Joe joe; 
    private Intake intake;
    private Timer timer;
    private PWMLauncher launcher;
    private final float starttime = System.nanoTime()/1000000000;


  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public EasyAuto(CANDrivetrain mDriveTrain, Joe joe, Intake intake, PWMLauncher launcher){
    this.mDriveTrain = mDriveTrain;
    this.joe = joe;
    this.intake = intake;
    this.launcher = launcher;
    this.timer = new Timer();

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(mDriveTrain, joe, intake, launcher);

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
        float time = System.nanoTime()/1000000000 - starttime;
            if(time <1.5 && time > 0){ 
                launcher.setBoth(1); 
            } 
            if(time < 3 && time > 1.5){
                launcher.setBoth(1); 
                joe.setMotor(.5);
            }
            if(time <4.5 && time > 3){
                launcher.setBoth(0); 
                joe.setMotor(0);
                mDriveTrain.arcadeDrive(-.25, time);
            }
        }                 






  // Called once the command ends or is interrupted.

  @Override
  public void end(boolean interrupted) {
      this.timer.reset();
      this.joe.turnOff();
      this.mDriveTrain.arcadeDrive(0, 0);
      this.launcher.turnOff();
      intake.turnOff();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return this.timer.get() > 15;
  }
}