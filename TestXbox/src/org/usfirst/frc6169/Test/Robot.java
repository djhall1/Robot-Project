// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc6169.Test;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc6169.Test.commands.*;
import org.usfirst.frc6169.Test.subsystems.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */

/*
 * MAIN ROBOT CODE, this is what is run when code is loaded
 */

public class Robot extends IterativeRobot {

    Command autonomousCommand;


    public static OI oi; //I/O Class

    public static Wheels wheels; //Wheel class
    public static SubMotor intake; //Intake motor class
    public static SubMotor flywheel; //Flywheel motor class
    public static SubMotor ballRegulator; //Ball regulator motor class
    public static int location;
    public Timer teleOpTimer; //Global run timer 
    public Timer autoTimer; // autonomous timer
    public static Thread visionThread; //Vision class (used for USB webcam)
	public static DriverStation DS;
	public int count;
	public double rTrigger;
//	public double lTrigger;
	public int delayCount;
	public double flyWheelSpeedModifier = 1.0;
	SendableChooser<Command> chooser = new SendableChooser<>();
	public boolean autonomousSwitch;


    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    RobotMap.init();
    	rTrigger = 0;
    //	lTrigger = 0;
    	//Instantiate the motor classes    	
        wheels = new Wheels();
        intake = new SubMotor(RobotMap.intakeFront, 0.27,-0.27, 0.5,"Backwards","Forwards");
        flywheel = new SubMotor(RobotMap.flywheel, -0.2, 0.38, 0.0,"Backwards","Forwards");
        ballRegulator = new SubMotor(RobotMap.ballRegulator, -1.5, 1.5, 0.0, "Forwards","Backwards");

        DS = DriverStation.getInstance();
        location = DS.getLocation();

        //Instantiate global Timer class
        teleOpTimer = new Timer();
        autoTimer = new Timer();
        //Instantiate Vision:

        //Vision Processing

        visionThread = new Thread(() -> {
			// Get the UsbCamera from CameraServer
			UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
			// Set the resolution
			camera.setResolution(320, 180);

			// Get a CvSink. This will capture Mats from the camera
			CvSink cvSink = CameraServer.getInstance().getVideo();
			// Setup a CvSource. This will send images back to the Dashboard
			CvSource outputStream = CameraServer.getInstance().putVideo("Rectangle", 320, 240);

			// Mats are very memory expensive. Lets reuse this Mat.
			Mat mat = new Mat();

			// This cannot be 'true'. The program will never exit if it is. This
			// lets the robot stop this thread when restarting robot code or
			// deploying.
			while (!Thread.interrupted()) {
				// Tell the CvSink to grab a frame from the camera and put it
				// in the source mat.  If there is an error notify the output.
				if (cvSink.grabFrame(mat) == 0) {
					// Send the output the error.
					outputStream.notifyError(cvSink.getError());
					// skip the rest of the current iteration
					continue;
				}
				// Put a rectangle on the image
				Imgproc.rectangle(mat, new Point(100, 100), new Point(400, 400),
						new Scalar(255, 255, 255), 5);
				// Give the output stream a new image to display
				outputStream.putFrame(mat);
			}
		});
		visionThread.setDaemon(true);
		visionThread.start();

        //(which it very likely will), subsystems are not guaranteed to be
        // constructed yet. Thus, their requires() statements may grab null
        // pointers. Bad news. Don't move it.
		//Instantiate OI
        oi = new OI();

        // instantiate the command used for the autonomous period

       // autonomousCommand = new AutonomousCommand("Auto",0.7);
        
        //Initialize Smart Dashboard Objects to be used.
        SmartDashboard.putString("Intake State", "Null"); //Dashboard object for Intake state
        SmartDashboard.putString("Flywheel State","Null"); //Dashboard object for Flywheel state
        SmartDashboard.putString("Ball Regulator State","Null"); //Dashboard object for Bell regulator state
        chooser.addDefault("Auto Off", null);
        chooser.addDefault("Straight Auto", new AutonomousCommand("Auto",0.7));
        SmartDashboard.putData("Auto Mode", chooser);
        


    }

    /**
     * This function is called when the disabled button is hit.
     * You can use it to reset subsystems before shutting down.
     */
    public void disabledInit(){

    }

    public void disabledPeriodic() {
        Scheduler.getInstance().run();
    }

    public void autonomousInit() {
        // schedule the autonomous command (example)
    	SmartDashboard.getBoolean("Auto. Switch", false);
        if (autonomousCommand != null && autonomousSwitch) autonomousCommand.start();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    public void teleopInit() {
        // This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (autonomousCommand != null) autonomousCommand.cancel();
        //Start global teleop timer.
        teleOpTimer.start();
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
    	rTrigger = oi.getXboxRTrigger();
    //	lTrigger = oi.getXboxLTrigger();

        //Run Subsystems based on joystick commands
    	
    	//Speed modifier for drive
        if(rTrigger < 0.8){
        	wheels.takeJoystickInputs(oi.getXboxYLeft()/1.5 ,oi.getXboxYRight()/1.5);
        	}
        if(rTrigger > 0.8){
        	wheels.takeJoystickInputs(oi.getXboxYLeft(), oi.getXboxYRight());
        }

        
    	//switchFlyState();
    	
        intake.runMotorLatched(teleOpTimer, oi.getXboxAButton(), oi.getXboxBButton());
        /*
        if(lTrigger > 0.8){
            flywheel.runMotorLatched(teleOpTimer, oi.getXboxXButton(), oi.getXboxYButton());
        }
        
        if(lTrigger < 0.8){
            flywheel.runMotorLatched(teleOpTimer, oi.getXboxXButton(), oi.getXboxYButton());
        }
        */
        
        flywheel.runMotorLatched(teleOpTimer, oi.getXboxXButton(), oi.getXboxYButton());
        ballRegulator.runMotorLatched(teleOpTimer, oi.getXboxRBumper(), oi.getXboxLBumper());
        wheels.changeDriveState(oi.getStart(), oi.getStop(), oi.getXboxYRight(), oi.getXboxYLeft());
        
        //Put information to smart dashboard

        SmartDashboard.putString("Intake State", intake.getLatchState());
        SmartDashboard.putString("Flywheel State", flywheel.getLatchState());
        SmartDashboard.putString("Ball Regulator", ballRegulator.getLatchState());
        
        }

    
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }
}
