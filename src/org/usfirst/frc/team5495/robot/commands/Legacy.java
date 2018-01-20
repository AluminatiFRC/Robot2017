package org.usfirst.frc.team5495.robot.commands;

import org.usfirst.frc.team5495.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Legacy extends Command {
	final double ROTATE_SPEED = .75;
	
	double startAngle;
	double targetAngle;
	boolean rotateClockwise;
	double moveSpeed;
	double moveAngle;
	
    public Legacy(double rotation, boolean rotateClockwise, double moveSpeed) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.driveSubsystem);
    	this.rotateClockwise = rotateClockwise;
    	this.moveSpeed = moveSpeed;
    	this.moveAngle = rotation;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	this.startAngle = Robot.gyroSubsystem.getAngle();
    	if (this.rotateClockwise) {
    		this.targetAngle = this.startAngle + this.moveAngle;
    	} else {
    		this.targetAngle = this.startAngle - this.moveAngle;
    	}
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (this.rotateClockwise) {
    		Robot.driveSubsystem.arcadeDrive(-moveSpeed, ROTATE_SPEED);
    	} else {
    		Robot.driveSubsystem.arcadeDrive(-moveSpeed, -ROTATE_SPEED);
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	boolean rval = false;
    	double rawAngle = Robot.gyroSubsystem.getAngle();
    	
    	if (this.rotateClockwise) {
    		rval = rawAngle > targetAngle;
    	} else {
    		rval = rawAngle < targetAngle;
    	}
    	return rval;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
