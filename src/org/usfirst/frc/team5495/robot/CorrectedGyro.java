package org.usfirst.frc.team5495.robot;

import edu.wpi.first.wpilibj.interfaces.Gyro;

public class CorrectedGyro implements Gyro
{
	double gyroCompensation = 0;
	static final double GYRO_COMPENSATION_RATE = -.000045;

	Gyro _gyro;
	
	public CorrectedGyro(Gyro other) {
		_gyro = other;
	}
	
	public void PerformAdjustment()
	{
		if (_gyro != null)
		{
			gyroCompensation += GYRO_COMPENSATION_RATE;
		}
	}
	
	@Override
	public void calibrate() {
		_gyro.calibrate();
	}

	@Override
	public void reset() {
		_gyro.reset();
	}

	@Override
	public double getAngle() {
		return _gyro.getAngle() + gyroCompensation;
	}

	@Override
	public double getRate() {
		return _gyro.getRate();
	}

	@Override
	public void free() {
		_gyro.free();
	}
}
