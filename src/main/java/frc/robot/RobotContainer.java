// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.RepeatCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants.LauncherConstants;
import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.Autos;
import frc.robot.commands.LaunchNote;
import frc.robot.commands.MotorCommand;
import frc.robot.commands.PrepareLaunch;
import frc.robot.subsystems.CANDrivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Joe;
import frc.robot.subsystems.PWMDrivetrain;
import frc.robot.subsystems.PWMLauncher;

// import frc.robot.subsystems.CANDrivetrain;
// import frc.robot.subsystems.CANLauncher;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems are defined here.
  private final CANDrivetrain m_drivetrain = new CANDrivetrain();
  // private final CANDrivetrain m_drivetrain = new CANDrivetrain();
  private final PWMLauncher m_launcher = new PWMLauncher();
  private final Intake intake = new Intake();
  private final Joe joe = new Joe();
  // private final CANLauncher m_launcher = new CANLauncher();

  /*The gamepad provided in the KOP shows up like an XBox controller if the mode switch is set to X mode using the
   * switch on the top.*/
  

  //private final CommandXboxController m_driverController = new CommandXboxController(OperatorConstants.kDriverControllerPort);
  private final CommandXboxController m_operatorController =
      new CommandXboxController(OperatorConstants.kOperatorControllerPort);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the trigger bindings
    configureBindings();
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be accessed via the
   * named factory methods in the Command* classes in edu.wpi.first.wpilibj2.command.button (shown
   * below) or via the Trigger constructor for arbitary conditions
   */
  private void configureBindings() {
    // Set the default command for the drivetrain to drive using the joysticks
    m_drivetrain.setDefaultCommand(
        new RunCommand(
            () ->
                m_drivetrain.arcadeDrive(
                    -m_operatorController.getLeftY()*Math.abs(m_operatorController.getLeftY()), 
                    -m_operatorController.getRightX()*Math.abs(m_operatorController.getRightX())),
            m_drivetrain));

    /*Create an inline sequence to run when the operator presses and holds the A (green) button. Run the PrepareLaunch
     * command for 1 seconds and then run the LaunchNote command */

    // intakes the intake
    m_operatorController
        .x()
        .whileTrue(new ParallelCommandGroup(new MotorCommand(intake, .3), new MotorCommand(joe, -.25)));
            // CHANGE JOE VALUES

    // when release x intake polybelt A LITTLE
    m_operatorController.x().onFalse(
      new MotorCommand(joe, 0).withTimeout(.5).andThen(new MotorCommand(joe, .2).withTimeout(.35)));


    // launches note
    m_operatorController
        .a()
        .whileTrue(
            new LaunchNote(m_launcher)
                .withTimeout(LauncherConstants.kLauncherDelay)
                .andThen(new ParallelCommandGroup(new MotorCommand(joe, -.5), new LaunchNote(m_launcher))));
                // CHANGE JOE VALUES
    

    // outtakes the intake
    m_operatorController
        .y()
        .whileTrue(new ParallelCommandGroup(new MotorCommand(intake, .3), new MotorCommand(joe, .25)));
        // CHANGE JOE VALUES


     // intakes shooter
    m_operatorController.rightBumper().whileTrue(m_launcher.getIntakeCommand());
    
    // intakes polybelt
    m_operatorController.b().whileTrue(new MotorCommand(joe, .2));
    // CHANGE JOE VALUES (CHANGED)

    // runs everyything so note shoot out (testing purposes)
    /*m_operatorController.leftBumper()
    .whileTrue(
      new ParallelCommandGroup(
        new LaunchNote(m_launcher), new MotorCommand(joe, -.5), new MotorCommand(intake, .3))); */


    /* m_operatorController
        .b()
        .whileTrue(
            new PrepareLaunch(m_launcher)
                .withTimeout(LauncherConstants.kLauncherDelay)
                .andThen(new LaunchNote(m_launcher))
                .handleInterrupt(() -> m_launcher.stop())); */

    // Set up a binding to run the intake command while the operator is pressing and holding the
    // left Bumper
     
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return Autos.exampleAuto(m_drivetrain, m_launcher, joe);
  }
}
