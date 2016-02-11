
package org.usfirst.frc.team2619.robot.subsystems;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class ArmSubsystem extends Subsystem implements PIDOutput, PIDSource
{
    CANTalon armMotor;
    PIDController positionController;
    double maxCurrent = 20;
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    
    public ArmSubsystem()
    {
    	armMotor = new CANTalon(19);
    	armMotor.changeControlMode(TalonControlMode.Current);
    	positionController = new PIDController(0,0,0,this,this);
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void writeDefaultDashboardValues()
    {
    	SmartDashboard.putNumber("PositionP (user input)", 0);
    	SmartDashboard.putNumber("PositionI (user input)", 0);
    	SmartDashboard.putNumber("PositionD (user input)", 0);
    	SmartDashboard.putNumber("CurrentP (user input)", 0);
    	SmartDashboard.putNumber("CurrentI (user input)", 0);
    	SmartDashboard.putNumber("CurrentD (user input)", 0);
    	SmartDashboard.putNumber("Max Current (user input)", 20);
    	
    }
    
    public void readDashboardControlValues()
    {
    	double PositionP =SmartDashboard.getNumber("PositionP (user input)", 0);
    	double PositionI =SmartDashboard.getNumber("PositionI (user input)", 0);
    	double PositionD =SmartDashboard.getNumber("PositionD (user input)", 0);
    	double CurrentP =SmartDashboard.getNumber("CurrentP (user input)", 0);
    	double CurrentI =SmartDashboard.getNumber("CurrentI (user input)", 0);
    	double CurrentD =SmartDashboard.getNumber("CurrentD (user input)", 0);
    	maxCurrent = SmartDashboard.getNumber("Max Current (user input)", 20);
    	armMotor.setPID(CurrentP, CurrentI, CurrentD);
    	positionController.setPID(PositionP, PositionI, PositionD);
    }
    
    public void resetEncoders()
    {
    	armMotor.setEncPosition(0);
    	armMotor.ClearIaccum();
    }
    
    public void enable()
    {
    	readDashboardControlValues();
    	positionController.enable();
    }
    
    public void disable()
    {
    	positionController.disable();
    }
    
    public void setSetpoint(double setPoint)
    {
    	positionController.setSetpoint(setPoint);
    }

	@Override
	public void pidWrite(double output) {
		output = Math.min(output, maxCurrent);
		output = Math.max(output * -1, maxCurrent);
		SmartDashboard.putNumber("Current Setpoint", output);
		armMotor.set(output);
	}

	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {}

	@Override
	public PIDSourceType getPIDSourceType() 
	{
		return PIDSourceType.kDisplacement;
	}

	@Override
	public double pidGet() {
		double value = armMotor.getEncPosition();
		SmartDashboard.putNumber("Position Measured Value", value);
		return value;
	}
    
    
}

