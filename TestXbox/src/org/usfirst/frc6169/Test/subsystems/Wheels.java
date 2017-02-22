package org.usfirst.frc6169.Test.subsystems;

import org.usfirst.frc6169.Test.RobotMap;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;

import edu.wpi.first.wpilibj.command.Subsystem;


/**
 *
 */
public class Wheels extends Subsystem {

	//Do we need all these?
    private final SpeedController leftFront = RobotMap.wheelsLeftFront;
    private final SpeedController leftBack = RobotMap.wheelsLeftBack;
    private final SpeedController rightFront = RobotMap.wheelsRightFront;
    private final SpeedController rightBack = RobotMap.wheelsRightBack;
    private final RobotDrive robotDrive4 = RobotMap.wheelsRobotDrive4;
    


    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }
    
    
    // Drive robot as tank drive based on left + right controller sticks
    public void takeJoystickInputs(double controllerLeft, double controllerRight){
    	robotDrive4.tankDrive(controllerLeft, controllerRight);
    }

    
    //Stop Motors
    public void stop(){
    	robotDrive4.drive(0,0);
    }
    
}

