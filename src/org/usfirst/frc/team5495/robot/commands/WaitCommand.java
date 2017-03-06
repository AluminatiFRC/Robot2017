package org.usfirst.frc.team5495.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class WaitCommand extends Command {

	long delayTime;
	long targetTime;
	
	
    public WaitCommand(double delay) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	this.delayTime = (long) (delay * 1000);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	//get the current time
    	targetTime = System.currentTimeMillis() + delayTime;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        //return false;
    	//checks the time and compares it to desired time
    	return System.currentTimeMillis() >= targetTime;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
