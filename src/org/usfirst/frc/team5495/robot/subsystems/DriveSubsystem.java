package org.usfirst.frc.team5495.robot.subsystems;

import org.usfirst.frc.team5495.robot.Robot;
import org.usfirst.frc.team5495.robot.RobotMap;
import org.usfirst.frc.team5495.robot.commands.DriveCommand;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.hal.HAL;
import edu.wpi.first.wpilibj.hal.FRCNetComm.tInstances;
import edu.wpi.first.wpilibj.hal.FRCNetComm.tResourceType;
import edu.wpi.first.wpilibj.interfaces.Gyro;

/**
 *
 */
public class DriveSubsystem extends Subsystem {
	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	final double CRAWL_SCALE = 1;
	final double DISTANCE_PER_REVOLUTION = (4.0 * Math.PI) / 12; // measured in feet
	final double DISTANCE_PER_PULSE = DISTANCE_PER_REVOLUTION / 1000;
	final double MINIMUM_MOTOR_SPEED = .3;
	final CANTalon motorLeftFront = new CANTalon(RobotMap.LEFT_MOTOR1);
	final CANTalon motorLeftBack = new CANTalon(RobotMap.LEFT_MOTOR2);
	final CANTalon motorRightFront = new CANTalon(RobotMap.RIGHT_MOTOR1);
	final CANTalon motorRightBack = new CANTalon(RobotMap.RICHT_MOTOR2);
	final RobotDrive robotDrive = new RobotDrive(motorLeftFront, motorLeftBack, motorRightFront, motorRightBack);
	final DoubleSolenoid solenoid = new DoubleSolenoid(RobotMap.HIGH_GEAR, RobotMap.LOW_GEAR);
	public final Encoder leftEnc = new Encoder(RobotMap.LEFT_ENCODER_FIRST, RobotMap.LEFT_ENCODER_SECOND, false,
			Encoder.EncodingType.k4X);
	public final Encoder rightEnc = new Encoder(RobotMap.RIGHT_ENCODER_FIRST, RobotMap.RIGHT_ENCODER_SECOND, false,
			Encoder.EncodingType.k4X);
	boolean isReverseEnabled = false;
	boolean isCrawlEnabled = false;
	double crawlTrim = 1;

	public DriveSubsystem() {
		motorLeftFront.setInverted(true);
		motorLeftBack.setInverted(true);
		motorRightFront.setInverted(true);
		motorRightBack.setInverted(true);
		
		InitializeEncoders();
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		setDefaultCommand(new DriveCommand());
	}

	public void SetReverseDrive(boolean input) {
		isReverseEnabled = input;
	}

	public void SetCrawlMode(boolean input) {
		isCrawlEnabled = input;
	}
	
	public void arcadeDrive(double moveValue, double rotateValue) {
		Robot.driveSubsystem.arcadeDrive(moveValue, rotateValue, 0);
	}

	public void arcadeDrive(double moveValue, double rotateValue, double cubicTrim) {
		//System.out.format("Steering Input: %f %f\r\n", moveValue, rotateValue);

		//System.out.format("Left: %f (%f) [%d] Right: %f (%f) [%d]\r\n",
		//		leftEnc.getRate(), leftEnc.getDistance(), leftEnc.getRaw(),
		//		rightEnc.getRate(), rightEnc.getDistance(), rightEnc.getRaw());		
		if (isReverseEnabled == true) {
			moveValue = -moveValue;
		}

		if (isCrawlEnabled == true) {
			crawlTrim = -Robot.oi.driveStick.getRawAxis(3);
			crawlTrim = (crawlTrim + 1) / 2.0;
			crawlTrim = (crawlTrim + .7) + .3;
			moveValue = moveValue * CRAWL_SCALE * crawlTrim;
			//rotateValue = rotateValue * CRAWL_SCALE * crawlTrim;
		}

		if (!isCrawlEnabled) {
			moveValue = cubicTrim * Math.pow(moveValue, 3) + (1 - cubicTrim) * moveValue;
			//rotateValue = cubicTrim * Math.pow(rotateValue, 3) + (1 - cubicTrim) * rotateValue;
		}
		
		//System.out.format("Motor Speed Requested: %f %f\r\n", moveValue, rotateValue);
				
		if (moveValue > 0.03) {
			moveValue = (1 - MINIMUM_MOTOR_SPEED) * moveValue + MINIMUM_MOTOR_SPEED;
		} else if (moveValue < -0.03) {
			moveValue = (1 - MINIMUM_MOTOR_SPEED) * moveValue - MINIMUM_MOTOR_SPEED;
		} else {
			moveValue = 0;
		}

		//System.out.format("Motor Speed Actual: %f %f\r\n", moveValue, rotateValue);
		robotDrive.arcadeDrive(moveValue, rotateValue, true);
	}

	private void arcadeDrivePID(double moveValue, double rotateValue, boolean squaredInputs) {

		double leftMotorSpeed;
		double rightMotorSpeed;

		if (moveValue > 1.0) {
			moveValue = 1.0;
		}
		if (moveValue < -1.0) {
			moveValue = -1.0;
		}
		if (rotateValue > 1.0) {
			rotateValue = 1.0;
		}
		if (rotateValue < -1.0) {
			rotateValue = -1.0;
		}

		if (squaredInputs) {
			// square the inputs (while preserving the sign) to increase fine
			// control
			// while permitting full power
			if (moveValue >= 0.0) {
				moveValue = moveValue * moveValue;
			} else {
				moveValue = -(moveValue * moveValue);
			}
			if (rotateValue >= 0.0) {
				rotateValue = rotateValue * rotateValue;
			} else {
				rotateValue = -(rotateValue * rotateValue);
			}
		}

		if (moveValue > 0.0) {
			if (rotateValue > 0.0) {
				leftMotorSpeed = moveValue - rotateValue;
				rightMotorSpeed = Math.max(moveValue, rotateValue);
			} else {
				leftMotorSpeed = Math.max(moveValue, -rotateValue);
				rightMotorSpeed = moveValue + rotateValue;
			}
		} else {
			if (rotateValue > 0.0) {
				leftMotorSpeed = -Math.max(-moveValue, rotateValue);
				rightMotorSpeed = moveValue + rotateValue;
			} else {
				leftMotorSpeed = moveValue - rotateValue;
				rightMotorSpeed = -Math.max(-moveValue, -rotateValue);
			}
		}

		double encL = leftEnc.get();
		double encR = rightEnc.get();

		double ratioL = (encL / leftMotorSpeed);
		double ratioR = (encR / rightMotorSpeed);

		if (ratioL > ratioR) {
			rightMotorSpeed = rightMotorSpeed * (ratioL / ratioR);
		}
		if (ratioL < ratioR) {
			leftMotorSpeed = leftMotorSpeed * (ratioR / ratioL);
		}

		robotDrive.setLeftRightMotorOutputs(leftMotorSpeed, rightMotorSpeed);
		leftEnc.reset();
		rightEnc.reset();
	}

	public void SetGear(int gear) {

		if (gear == 0) {
			solenoid.set(DoubleSolenoid.Value.kReverse);
		} else {
			solenoid.set(DoubleSolenoid.Value.kForward);
		}
	}

	public void InitializeEncoders() {
		leftEnc.setMaxPeriod(.1);
		leftEnc.setMinRate(10);
		leftEnc.setDistancePerPulse(DISTANCE_PER_PULSE * leftEnc.getEncodingScale());
		leftEnc.setReverseDirection(false);
		leftEnc.setSamplesToAverage(10);
		leftEnc.reset();
		
		rightEnc.setMaxPeriod(.1);
		rightEnc.setMinRate(10);
		rightEnc.setDistancePerPulse(DISTANCE_PER_PULSE * rightEnc.getEncodingScale());
		rightEnc.setReverseDirection(false);
		rightEnc.setSamplesToAverage(10);
		rightEnc.reset();
	}

	public double getDistance(int value) {
		//0 = average 1 = right 2= left
		if (value == 0) {
			return (-rightEnc.getDistance() + leftEnc.getDistance()) / 2;
		} else if (value == 1) {
			return rightEnc.getDistance();
		} else if (value == 2) {
			return leftEnc.getDistance();
		} else {
			System.err.println("ERROR: invalid value for getDistance.  Expected 0 or 1 or 2");
			return 0;
		}
	}
}
