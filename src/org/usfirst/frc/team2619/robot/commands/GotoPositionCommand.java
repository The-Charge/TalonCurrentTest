
package org.usfirst.frc.team2619.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team2619.robot.Robot;

/**
 *
 */
public class GotoPositionCommand extends Command
{
	private double position;

    public GotoPositionCommand() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.armSubsystem);
        SmartDashboard.putNumber("Position setpoint (user input)", 10);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	//Read the smart dashboard
    	position = SmartDashboard.getNumber("Position setpoint (user input)", 10);
    	Robot.armSubsystem.setSetpoint(position);
    	Robot.armSubsystem.enable();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.armSubsystem.disable();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
