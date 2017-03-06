package org.usfirst.frc.team5495.robot.subsystems;

import org.usfirst.frc.team5495.robot.RobotMap;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class FuelSubsystem extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
//Main Drive Motor
	// Pnuematics for the feeders??? (check rules)
	// 1 Solenoid for top and 1 for the bottom working in opposition.
	// Features: Dispense and Collect solid fuel,
	// Collecting into hopper if need be.
	// Dispensing into low goal.
	final CANTalon feedermotorA = new CANTalon(RobotMap.FEEDER_MOTOR_A);
	final CANTalon feedermotorB = new CANTalon(RobotMap.FEEDER_MOTOR_B);
	final DoubleSolenoid feederSolenoid = new DoubleSolenoid(RobotMap.FEEDER_PISTON_A, RobotMap.FEEDER_PISTON_B);
	final double motorSpeed = .6;

    public void initDefaultCommand() {
    }
    
    public void CollectFuel(){
    	feedermotorA.set(-motorSpeed);
    	feedermotorB.set(motorSpeed);
    	feederSolenoid.set(DoubleSolenoid.Value.kForward);
    }
    
    public void DispenseFuel(){
    	feedermotorA.set(-motorSpeed);
    	feedermotorB.set(motorSpeed);
    	feederSolenoid.set(DoubleSolenoid.Value.kReverse);
    	
    }
    
    public void Stop() {
    	feedermotorA.set(0);
    	feedermotorB.set(0);
    }
    
    public void Eject() {
    	feedermotorA.set(motorSpeed);
    	feedermotorB.set(-motorSpeed);
    }
    
}


