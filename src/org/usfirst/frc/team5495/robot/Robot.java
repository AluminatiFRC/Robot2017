
package org.usfirst.frc.team5495.robot;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.command.WaitCommand;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team5495.robot.commands.ForwardByDistanceCommand;
import org.usfirst.frc.team5495.robot.commands.PeelAwayCommand;
import org.usfirst.frc.team5495.robot.commands.AutoCenterPeelLeft;
import org.usfirst.frc.team5495.robot.commands.AutoCenterPeelRight;
import org.usfirst.frc.team5495.robot.commands.AutoCenterStay;
import org.usfirst.frc.team5495.robot.commands.AutoLeftPeel;
import org.usfirst.frc.team5495.robot.commands.AutoLeftStay;
import org.usfirst.frc.team5495.robot.commands.AutoRightPeel;
import org.usfirst.frc.team5495.robot.commands.AutoRightStay;
import org.usfirst.frc.team5495.robot.commands.DriveCommand;
import org.usfirst.frc.team5495.robot.subsystems.DriveSubsystem;
import org.usfirst.frc.team5495.robot.subsystems.FuelSubsystem;
import org.usfirst.frc.team5495.robot.subsystems.GyroscopeSubsystem;
import org.usfirst.frc.team5495.robot.subsystems.WinchSubsystem;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	public static final DriveSubsystem driveSubsystem = new DriveSubsystem();
	public static final FuelSubsystem FuelSubsystem = new FuelSubsystem();
	public static final WinchSubsystem WinchSubsystem = new WinchSubsystem();
	public static final GyroscopeSubsystem gyroSubsystem = new GyroscopeSubsystem();

	public static OI oi;
	public static final Compressor compressor = new Compressor(RobotMap.COMPRESSOR_PORT);

	Command autonomousCommand;
	SendableChooser<Command> chooser = new SendableChooser<>();

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		oi = new OI();
		//CameraServer.getInstance().startAutomaticCapture("LifeCam", "cam0");
		//CameraServer.getInstance().startAutomaticCapture();
		//CameraServer.getInstance().startAutomaticCapture("LifeCam", "cam1");
		
		CameraServer camServer = CameraServer.getInstance();
		UsbCamera camera = camServer.startAutomaticCapture();
		//camera.setResolution(640, 480);
	
		chooser.addDefault("Center Stay", new AutoCenterStay());
		chooser.addObject("Do Nothing", new WaitCommand(60*3));
		
		chooser.addObject("Left, Stay", new AutoLeftStay());
		chooser.addObject("Left, Peel Left", new AutoLeftPeel());
		
		chooser.addObject("Right, Stay", new AutoRightStay());
		chooser.addObject("Right, Peel Right", new AutoRightPeel());
		
		chooser.addDefault("Center, Peel Left", new AutoCenterPeelLeft());
		chooser.addDefault("Center, Peel Right", new AutoCenterPeelRight());
		SmartDashboard.putData("Auto mode", chooser);
}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		autonomousCommand = chooser.getSelected();

		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */

		// schedule the autonomous command (example)
		if (autonomousCommand != null)
			autonomousCommand.start();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		GyroscopeSubsystem.MainGyro.PerformAdjustment();

		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (autonomousCommand != null)
			autonomousCommand.cancel();
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		GyroscopeSubsystem.MainGyro.PerformAdjustment();

		
		Scheduler.getInstance().run();
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
}
