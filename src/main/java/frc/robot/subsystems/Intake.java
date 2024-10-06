package frc.robot.subsystems;


import edu.wpi.first.wpilibj2.command.CommandScheduler;

import com.ctre.phoenix6.signals.ControlModeValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import java.util.ArrayList;

import com.ctre.phoenix6.hardware.TalonFX;

import frc.robot.Constants;
import frc.robot.SubsystemBaseWrapper;


public class Intake extends SubsystemBaseWrapper implements MotorSubsystem{
    CANSparkMax intake;

    public Intake(){
        // change out value of pivot id once plugged in
        this.intake = new CANSparkMax(Constants.Intake.intakeID, MotorType.kBrushed);

        this.intake.setIdleMode(IdleMode.kBrake);
    }

    @Override
    public void setMotor(double value, boolean force) {
        this.intake.set(value);
    }

    @Override
    public void turnOff() {
        this.intake.set(0);
    }


    @Override
    public void periodic() {

    }



}