package org.usfirst.frc6169.Test.commands;

import java.awt.Robot;

import edu.wpi.first.wpilibj.command.TimedCommand;


public class AutonomousCommand extends TimedCommand{

	@Override
	protected void execute() {
		// TODO Auto-generated method stub
		org.usfirst.frc6169.Test.Robot.wheels.driveRobotForward(1.0);
	}

	public AutonomousCommand(String name, double timeout) {
		super(name, timeout);
		// TODO Auto-generated constructor stub
	}

}
