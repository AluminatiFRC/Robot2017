package org.usfirst.frc.team5495.robot.commands;

/**
 *
 */
public class TurnByDegreesCommand extends Legacy {	
	public TurnByDegreesCommand() {
		this(180, true);
	}
	
    public TurnByDegreesCommand(double degrees, boolean rotateClockwise) {
		super(degrees, rotateClockwise, 0);
    }
}
