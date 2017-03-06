package org.usfirst.frc.team5495.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team5495.robot.Robot;
import org.usfirst.frc.team5495.robot.RobotMap;

/**
 *
 */
public class DriveCommand extends Command {
	public DriveCommand() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.driveSubsystem);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		//System.out.println("Drive command initialized.");
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		double rotateValue = Robot.oi.driveStick.getAxis(RobotMap.ROTATE_AXIS);
		double moveValue = Robot.oi.driveStick.getAxis(RobotMap.MOVE_AXIS);
		double cubicTrim = -Robot.oi.driveStick.getRawAxis(3);
		cubicTrim = (cubicTrim + 1) / 2.0;
		//System.out.format("CubicTrim: %f", cubicTrim);
		
		
		Robot.driveSubsystem.arcadeDrive(moveValue, rotateValue, cubicTrim);
		boolean buttonA = Robot.oi.driveStick.getRawButton(RobotMap.GEAR_BUTTON);
		Robot.driveSubsystem.SetGear(buttonA ? 1: 0);
		if (!buttonA) {
			rotateValue *= 0.5;
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		//System.out.println("Drive command ended.");
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		//System.out.println("Drive command interrupted.");
		end();
	}
}
