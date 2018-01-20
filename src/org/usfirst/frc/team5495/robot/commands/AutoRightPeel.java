package org.usfirst.frc.team5495.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoRightPeel extends CommandGroup {

    public AutoRightPeel() {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.
    	addSequential(new ForwardByDistanceCommand(-(48.0 / 12)));
    	addSequential(new TurnByDegreesCommand(103.75, false));
    	//addParallel( new ResetGyroCommand());
    	addSequential(new ForwardByDistanceCommand(-(70 / 12)));
    	addSequential(new WaitCommand(3));
    	addSequential(new Legacy(125, false, .75));
    	//addParallel(new ResetGyroCommand());

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    }
}
