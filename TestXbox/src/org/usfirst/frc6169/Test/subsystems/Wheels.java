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
    private boolean startLatch = false;
    private boolean stopLatch = false;
	public boolean lastStartStopState;


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
    
    public void changeDriveState(boolean controllerForward, boolean controllerBackward, double yRight, double yLeft){
    	// Change from forward-drive to backward-drive, reverses motors to it's not confusing on the controller itself.
    	// Robot has to be completely stopped (No input on left + right Y axis) for drivestate to switch over.
    	boolean forward = controllerForward;
    	boolean backward = controllerBackward;
    	
    	if (forward){
    		this.startLatch = true;
    		this.stopLatch = false;
    	} else if (backward){
    		this.startLatch = false;
    		this.stopLatch = true;
    	}
    	if(startLatch){
    		RobotMap.wheelsRightFront.setInverted(false);
    		RobotMap.wheelsLeftFront.setInverted(false);
    	} else if (stopLatch){
    		RobotMap.wheelsRightFront.setInverted(true);
    		RobotMap.wheelsLeftFront.setInverted(true);
    	}
    }
    public void driveRobotForward(double speed){
    	robotDrive4.drive(speed, 0); // When we get gyro, we can develop an error-based drive on angle from heading
    }
    	
} 

