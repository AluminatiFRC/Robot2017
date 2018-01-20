package org.usfirst.frc.team5495.robot.commands;

import org.usfirst.frc.team5495.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class RotateByTimeCommand extends Command {
	final double ROTATE_SPEED = .75;
	
	long delayTime;
	long targetTime;
	int direction;
	
    public RotateByTimeCommand(double timeInSeconds) {
    	requires(Robot.driveSubsystem);
    	this.delayTime = (long)(timeInSeconds * 1000);
    	this.direction = 1;
    }

    public RotateByTimeCommand(double timeInSeconds, int direction) {
    	requires(Robot.driveSubsystem);
    	this.delayTime = (long)(timeInSeconds * 1000);
    	this.direction = direction < 0 ? -1 : 1;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	this.targetTime = System.currentTimeMillis() + this.delayTime;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.driveSubsystem.arcadeDrive(0, this.direction * ROTATE_SPEED);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return System.currentTimeMillis() >= this.targetTime;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
