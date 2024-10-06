package frc.robot.subsystems;


import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

import com.ctre.phoenix6.signals.ControlModeValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import java.util.ArrayList;

import com.ctre.phoenix6.hardware.TalonFX;

import frc.robot.Constants;
import frc.robot.SubsystemBaseWrapper;


public class Joe extends SubsystemBaseWrapper implements MotorSubsystem{
    private TalonFX Joe;

    public Joe(){
        // change out value of pivot id once plugged in
        this.Joe = new TalonFX(Constants.Joe.JoeID);

        this.Joe.setNeutralMode(NeutralModeValue.Brake);
    }

    @Override
    public void setMotor(double value, boolean force) {
        this.Joe.set(value);
    }

    @Override
    public void turnOff() {
        this.Joe.set(0);
    }

    @Override
    public void periodic() {

    }

}