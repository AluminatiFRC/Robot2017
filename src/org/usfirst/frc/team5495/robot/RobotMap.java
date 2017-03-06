package org.usfirst.frc.team5495.robot;

import edu.wpi.first.wpilibj.Joystick.AxisType;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	// For example to map the left and right motors, you could define the
	// following variables to use with your drivetrain subsystem.
	
	// CAN Ports
	public static final int LEFT_MOTOR1 = 3;
	public static final int LEFT_MOTOR2 = 4;
	public static final int RIGHT_MOTOR1 = 1;
	public static final int RICHT_MOTOR2 = 2;
	public static final int FEEDER_MOTOR_A = 5;
	public static final int FEEDER_MOTOR_B = 6;
	public static final int WINCH_MOTOR = 7;
	
	// Joystick Ports
	public static final int JOYSTICK_DRIVE_PORT = 0;
	public static final int JOYSTICK_CONTROL_PORT = 1;
	
	public static final int HALF_TURN_BUTTON = 7;
	public static final int COLLECT_BUTTON = 3;
	public static final int DISPENSE_BUTTON = 4;
	public static final int REVERSE_BUTTON = 11;
	public static final int CRAWL_BUTTON = 12;
	public static final int WINCH_BUTTON = 9;
	public static final AxisType MOVE_AXIS = AxisType.kY;
	public static final AxisType ROTATE_AXIS = AxisType.kZ;
	public static final int EJECT_BUTTON = 5;
	public static final int GEAR_BUTTON = 1;

	public static final int SECONDARY_COLLECT_BUTTON = 5;
	public static final int SECONDARY_DISPENSE_BUTTON = 6;
	public static final int SECONDARY_WINCH_BUTTON = 4;
	public static final int SECONDARY_EJECT_BUTTON = 2;
	
	// Pneumatic Ports
	public static final int HIGH_GEAR = 0;
	public static final int LOW_GEAR = 1;
	public static final int FEEDER_PISTON_A = 2;
	public static final int FEEDER_PISTON_B = 3;
	
	// Misc.
	public static final int COMPRESSOR_PORT = 0;
	public static final int LEFT_ENCODER_FIRST = 0;
	public static final int LEFT_ENCODER_SECOND = 1;
	public static final int RIGHT_ENCODER_FIRST = 2;
	public static final int RIGHT_ENCODER_SECOND = 3;

	// If you are using multiple modules, make sure to define both the port
	// number and the module. For example you with a rangefinder:
	// public static int rangefinderPort = 1;
	// public static int rangefinderModule = 1;
}
