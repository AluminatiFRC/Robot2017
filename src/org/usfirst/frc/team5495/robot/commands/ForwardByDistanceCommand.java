package org.usfirst.frc.team5495.robot.commands;

import org.usfirst.frc.team5495.robot.Robot;
import org.usfirst.frc.team5495.robot.RobotMap;
import org.usfirst.frc.team5495.robot.subsystems.DriveSubsystem;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team5495.robot.Robot;


/**
 **/
 
public class ForwardByDistanceCommand extends Command {
	final double DRIFT_CORRECTION = -0.01;
	final double MOVE_SPEED = 1;
	final long SLERP_DURATION = 500;
	final double STOPPING_DISTANCE = 1;
	final double MIN_SPEED = 0.3;

	double startDistance;
	double targetDistance;
	double moveDistance;
	double decellerationDistance;
	long lerpStartTime;
	boolean decelerating;
	boolean doneDriving;
	
    public ForwardByDistanceCommand(double distance) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.driveSubsystem);
    	this.moveDistance = distance;
    }
    
    // Called just before this Command runs the first time
    protected void initialize() {
    	decelerating = false;
    	doneDriving = false;

    	if (this.moveDistance < 0)
    	{
    		Robot.driveSubsystem.SetReverseDrive(true);
    	}
    	this.startDistance = Robot.driveSubsystem.getDistance(0);
    	this.targetDistance = this.startDistance + this.moveDistance;
    	this.decellerationDistance = this.targetDistance - STOPPING_DISTANCE;
    	if (moveDistance < 0) {
    		this.decellerationDistance = this.targetDistance + STOPPING_DISTANCE;
    	}
    	lerpStartTime = System.currentTimeMillis();
		System.out.format("Init -- Move: %f Start: %f\n", this.startDistance, this.moveDistance);
		System.out.format("Init -- Target: %f Actual: %f\n", this.targetDistance, Robot.driveSubsystem.getDistance(0));
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double speed = -MOVE_SPEED;

    	// Ease-in (accelerate)
    	long deltaTime = System.currentTimeMillis() - lerpStartTime;
    	if (deltaTime < SLERP_DURATION)
    	{
    		double interpolation = (-Math.cos(((double)deltaTime / SLERP_DURATION) * Math.PI) +1) / 2;
    		speed = -MOVE_SPEED * interpolation;
    	}
    	
    	// Ease-out (decelerate)
		double deltaDistance = Robot.driveSubsystem.getDistance(0) - decellerationDistance;
    	if (moveDistance < 0){
    		deltaDistance = decellerationDistance - Robot.driveSubsystem.getDistance(0);
    	}

		if (deltaDistance >= -1.5) {
    		if (!decelerating)
    		{
    			System.out.println("Start Stopping!");
    			decelerating = true;
    			lerpStartTime = System.currentTimeMillis();
    		}

    		//deltaTime = System.currentTimeMillis() - lerpStartTime;
    		//double interpolation = 1 - (double)deltaTime / SLERP_DURATION;
    		double interpolation = 1 - deltaDistance / STOPPING_DISTANCE;
    		interpolation = Math.max(interpolation, 0);
    		interpolation = Math.min(interpolation, 1);
    		interpolation = (-Math.cos(interpolation * Math.PI)+1) / 2;
    		interpolation = (1-MIN_SPEED) * interpolation + MIN_SPEED;
    		//System.out.format("delta distance: %f, interpolation: %f\n", deltaDistance, interpolation);
    		speed = -MOVE_SPEED * interpolation;
    	}
    	
		Robot.driveSubsystem.arcadeDrive(speed, DRIFT_CORRECTION);
		System.out.format("Target: %f Actual: %f\n", this.targetDistance, Robot.driveSubsystem.getDistance(0));
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if (this.moveDistance > 0)
    	{
        	return this.targetDistance < Robot.driveSubsystem.getDistance(0);
    	} else {
        	return this.targetDistance > Robot.driveSubsystem.getDistance(0);
    	}
    }

    // Called once after isFinished returns true
    protected void end() {
		Robot.driveSubsystem.SetReverseDrive(false);
		Robot.driveSubsystem.arcadeDrive(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}