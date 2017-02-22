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


import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.VictorSP;
// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {

    public static SpeedController wheelsLeftFront; //Drive Left Front
    public static SpeedController wheelsLeftBack; //Drive Lefft Back
    public static SpeedController wheelsRightFront; //Drive Right Front
    public static SpeedController wheelsRightBack; //Drive Right Back
    public static RobotDrive wheelsRobotDrive4; //Robot Drive subsystem
    
    public static SpeedController intakeFront; // intake motor
    public static SpeedController flywheel; //flywheel motor
    public static SpeedController ballRegulator; //ball regulator motor



    public static void init() {

    	
    	//Instantiate the new motors on init()
        wheelsLeftFront = new VictorSP(5);
        LiveWindow.addActuator("Wheels", "LeftFront", (VictorSP) wheelsLeftFront);
        
        wheelsLeftBack = new VictorSP(3);
        LiveWindow.addActuator("Wheels", "LeftBack", (VictorSP) wheelsLeftBack);
        
        wheelsRightFront = new VictorSP(2);
        LiveWindow.addActuator("Wheels", "RightFront", (VictorSP) wheelsRightFront);
        
        wheelsRightBack = new VictorSP(1);
        LiveWindow.addActuator("Wheels", "RightBack", (VictorSP) wheelsRightBack);
                
        wheelsRobotDrive4 = new RobotDrive(wheelsLeftFront, wheelsLeftBack,
              wheelsRightFront, wheelsRightBack);
       
        intakeFront = new VictorSP(9);
        LiveWindow.addActuator("Intake", "Front", (VictorSP) intakeFront);
        
        flywheel = new VictorSP(8);
        LiveWindow.addActuator("Flywheel", "Front", (VictorSP) flywheel);
        
        ballRegulator = new VictorSP(6);
        
        
        //Customize the motor configurations
        wheelsRobotDrive4.setSafetyEnabled(true);
        wheelsRobotDrive4.setExpiration(0.1);
        wheelsRobotDrive4.setSensitivity(0.5);
        wheelsRobotDrive4.setMaxOutput(1.0);
        
        wheelsLeftBack.setInverted(true);
        wheelsRightBack.setInverted(true);
        




    }
}
