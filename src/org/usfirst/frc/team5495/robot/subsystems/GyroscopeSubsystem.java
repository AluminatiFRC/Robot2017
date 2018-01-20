package org.usfirst.frc.team5495.robot.subsystems;

import org.usfirst.frc.team5495.robot.CorrectedGyro;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class GyroscopeSubsystem extends Subsystem {
	public static final CorrectedGyro MainGyro = new CorrectedGyro(new ADXRS450_Gyro());
	
    public void initDefaultCommand() {
    }
    
	public double getAngle() {
		if (MainGyro == null) {
			return 0;
		}
		System.out.format("MainGyro %f\n", MainGyro.getAngle());
		return MainGyro.getAngle();
		
	}
}

