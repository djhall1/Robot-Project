// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc6169.Test.commands;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc6169.Test.RobotMap;

/**
 *
 */
public class AutonomousCommand extends Command {
	private Timer autoTimer;
	public DriverStation DS;
    private final RobotDrive robotDrive4 = RobotMap.wheelsRobotDrive4;
	int location;
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS
 
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
    public AutonomousCommand() {
    autoTimer.start();
    
    
    }

    
    
    // Called just before this Command runs the first time
    protected void initialize() {
    autoTimer = new Timer();
    location = DS.getLocation();
    
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(location == 1 || location == 3){
    		autoTimer.reset();
    		while(autoTimer.get() < 3){
    			robotDrive4.drive(1,0);
    		}
    		robotDrive4.drive(0, 0);
    	}
    
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
