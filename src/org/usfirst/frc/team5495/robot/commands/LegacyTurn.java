package org.usfirst.frc.team5495.robot.commands;

import org.usfirst.frc.team5495.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class LegacyTurn extends Command {
	final double ROTATE_SPEED = .75;
	final double ROTATE_DRIFT = 19.6;

	double targetAngle;
	double moveAngle;
	boolean rotateClockwise;
	double moveSpeed;
	double lastAngle;

    public LegacyTurn(double rotation, boolean rotateClockwise) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	this(rotation, rotateClockwise, .75);
    }
    
    public LegacyTurn (double rotation, boolean rotateClockwise, double moveSpeed) {
    	requires(Robot.driveSubsystem);
    	this.moveAngle = rotation;
    	this.rotateClockwise = rotateClockwise;
    	this.moveSpeed = moveSpeed;
    }
  
    // Called just before this Command runs the first time
    protected void initialize() {
    	double rawAngle = Robot.gyroSubsystem.getAngle();
    	double startAngle = NormalizeAngle(rawAngle);
    	if (this.rotateClockwise) {
    		this.targetAngle = NormalizeAngle(startAngle + this.moveAngle);
 //   		if (Math.abs(this.moveAngle) > ROTATE_DRIFT + 5) {
 //       		this.targetAngle -= ROTATE_DRIFT;
 //   		}
    	} else {
    		this.targetAngle = NormalizeAngle(startAngle - this.moveAngle);
 //   		if (Math.abs(this.moveAngle) > ROTATE_DRIFT + 5) {
 //       		this.targetAngle += ROTATE_DRIFT;
 //   		}
    	}
    	
    	this.lastAngle = startAngle;
    	System.out.format("Init:    current: %f raw: %f target %f\n", startAngle, rawAngle, targetAngle);
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
    	double newAngle = NormalizeAngle(rawAngle);
    	
    	System.out.format("last: %f current: %f raw: %f target %f\n", lastAngle, newAngle, rawAngle, targetAngle);
    	
    	if (this.rotateClockwise) {
    		rval = lastAngle < this.targetAngle && newAngle >= targetAngle;
    	} else {
    		rval = lastAngle > this.targetAngle && newAngle <= targetAngle;
    	}
    	
        lastAngle = newAngle;
        return rval;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
    
    // Ensure angle is between 0 and 360 degrees (inclusive).
    private double NormalizeAngle(double angle)
    {
    	while (angle < 0)  angle += 360;
    	while (angle > 360) angle -= 360;
    	return angle;
    }
}
