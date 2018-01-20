package org.usfirst.frc.team5495.robot.subsystems;

import org.usfirst.frc.team5495.robot.RobotMap;
import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class WinchSubsystem extends Subsystem {
	final CANTalon motor1 = new CANTalon (RobotMap.WINCH_MOTOR);
	
	protected void initDefaultCommand() {
	}
	
	public void RotateWinch() {
	    motor1.set(-1.0);
	}
	
	public void StopWinch() {
		motor1.set(0);
	}
}

