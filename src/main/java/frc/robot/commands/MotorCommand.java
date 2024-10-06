package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.MotorSubsystem;

public class MotorCommand extends Command {
    private final MotorSubsystem motorSubsystem;
    private final double velocity;
    private final boolean force;
    public MotorCommand(MotorSubsystem motorSubsystem, double velocity, boolean force){
        this.motorSubsystem = motorSubsystem;
        this.velocity = velocity;
        this.force = force;
        addRequirements(motorSubsystem);
    }
    public MotorCommand(MotorSubsystem motorSubsystem, double velocity) {
        this(motorSubsystem, velocity, false);
    }

    @Override
    public void initialize() {
        this.motorSubsystem.setMotor(velocity, force);
    }

    @Override
    public void end(boolean interrupted) {
        this.motorSubsystem.turnOff();


    }

    @Override
    public boolean isFinished() {
        return !motorSubsystem.canTurn(velocity);
    }
}
