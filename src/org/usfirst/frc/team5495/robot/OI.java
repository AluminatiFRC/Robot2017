package org.usfirst.frc.team5495.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

import org.usfirst.frc.team5495.robot.commands.CollectSolidFuelCommand;
import org.usfirst.frc.team5495.robot.commands.CrawlModeCommand;
import org.usfirst.frc.team5495.robot.commands.DispenseSolidFuelCommand;
import org.usfirst.frc.team5495.robot.commands.DriveCommand;
import org.usfirst.frc.team5495.robot.commands.EjectCommand;
import org.usfirst.frc.team5495.robot.commands.TurnByDegreesCommand;
import org.usfirst.frc.team5495.robot.commands.ReverseDriveCommand;
import org.usfirst.frc.team5495.robot.commands.WinchCommand;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	//// CREATING BUTTONS
	// One type of button is a joystick button which is any button on a
	//// joystick.
	// You create one by telling it which joystick it's on and which button
	// number it is.
	public Joystick driveStick = new Joystick(RobotMap.JOYSTICK_DRIVE_PORT);
	Button HalfTurnButton = new JoystickButton(driveStick, RobotMap.HALF_TURN_BUTTON);
	Button DispenseButton = new JoystickButton(driveStick, RobotMap.COLLECT_BUTTON);
	Button CollectButton = new JoystickButton(driveStick, RobotMap.DISPENSE_BUTTON);
	Button ReverseButton = new JoystickButton(driveStick, RobotMap.REVERSE_BUTTON);
	Button CrawlButton = new JoystickButton(driveStick, RobotMap.CRAWL_BUTTON);
	Button WinchButton = new JoystickButton(driveStick, RobotMap.WINCH_BUTTON);
	Button EjectButton = new JoystickButton(driveStick, RobotMap.EJECT_BUTTON);

	public Joystick controllStick = new Joystick(RobotMap.JOYSTICK_CONTROL_PORT);
	Button ControlCollectButton = new JoystickButton(controllStick, RobotMap.SECONDARY_COLLECT_BUTTON);
	Button ControlDispenseButton = new JoystickButton(controllStick, RobotMap.SECONDARY_DISPENSE_BUTTON);
	Button ControlWinchButton = new JoystickButton(controllStick, RobotMap.SECONDARY_WINCH_BUTTON);
	Button ControlEjectButton = new JoystickButton(controllStick, RobotMap.SECONDARY_EJECT_BUTTON);

	// There are a few additional built in buttons you can use. Additionally,
	// by subclassing Button you can create custom triggers and bind those to
	// commands the same as any other Button.

	//// TRIGGERING COMMANDS WITH BUTTONS
	// Once you have a button, it's trivial to bind it to a button in one of
	// three ways:

	// Start the command when the button is pressed and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenPressed(new ExampleCommand());

	// Run the command while the button is being held down and interrupt it once
	// the button is released.
	// button.whileHeld(new ExampleCommand());

	// Start the command when the button is released and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenReleased(new ExampleCommand());
	
	public OI() {
		HalfTurnButton.whenPressed(new TurnByDegreesCommand());
		CollectButton.whileHeld(new CollectSolidFuelCommand());
		DispenseButton.whileHeld(new DispenseSolidFuelCommand());
		ReverseButton.toggleWhenPressed(new ReverseDriveCommand());
		CrawlButton.toggleWhenPressed(new CrawlModeCommand());
		WinchButton.toggleWhenPressed(new WinchCommand());
		EjectButton.whileHeld(new EjectCommand());

		ControlCollectButton.whileHeld(new CollectSolidFuelCommand());
		ControlDispenseButton.whileHeld(new DispenseSolidFuelCommand());
		ControlWinchButton.whileHeld(new WinchCommand());
		ControlEjectButton.whileHeld(new EjectCommand());
	}
}
