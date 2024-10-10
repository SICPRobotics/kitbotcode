package frc.robot.subsystems;


import edu.wpi.first.wpilibj.DigitalInput;
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


public class Intake extends SubsystemBaseWrapper implements MotorSubsystem {
    TalonFX intake;
    public DigitalInput limitSwitch = new DigitalInput(0);

    public Intake(){
        // change out value of pivot id once plugged in
        this.intake = new TalonFX(Constants.Intake.intakeID);

        this.intake.setNeutralMode(NeutralModeValue.Brake);
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