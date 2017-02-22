package org.usfirst.frc6169.Test.subsystems;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;

public class SubMotor extends Subsystem {
	
	/*
	 * Class used for single motor operations (Speed Controller Type agnostic)
	 * 
	 * TODO: Add method to allow for variable speed control based on double controller input.
	 */
	
	//---------------------------CLASS VARIABLES
	//------------------------------------------
	
	private final SpeedController motor;
	private boolean intakeLatch; //Latches the intake motor
	private double stopTime;
	private double motorFSpeed; //Speed of the motor (forward)
	private double motorBSpeed; //Speed of the motor (backward)
	private double delayTime;
	private String latchedString;
	private String unlatchedString;

	
	//-------------------------CONSTRUCTORS
	//-------------------------------------
	public SubMotor(SpeedController motor, double latchedSpeed, double unlatchedSpeed, double delayTime,String state0, String state1){
		/*
		 * Constructor for SubMotor Class, used in latched/unlatched state. requires the following inputs:
		 * 
		 * SpeedController motor = The single motor controller defined in RobotMap
		 * double forwardSpeed = Speed of the motor when latched
		 * double backwardSpeed = Speed of the motor when unlatched
		 * double delayTime = Delay time between switching b/t latched/unlatched state
		 * String state0,state1 = String descriptor of latched/unlatched states, to be used in smart dashboard.
		 */
		this.motorFSpeed = latchedSpeed;
		this.motorBSpeed = unlatchedSpeed;
		this.motor = motor;
		this.delayTime = delayTime;
		this.latchedString = state0;
		this.unlatchedString = state1;
	}
	
	//-------------------------METHODS
	//--------------------------------
	@Override
	protected void initDefaultCommand() {
		
	}
	 
	public void runMotorLatched(Timer runtime, boolean latchButton, boolean unlatchButton){
		/*
		 * Run the motor latched/unlatched. Method takes two controller button inputs (as boolean)
		 * Timer runtime = total runtime of the autonomous/teleop opMode
		 * boolean latchButton = controller input for latching motor
		 * boolean unlatchButton = controller input for unlatching motor
		 */
		
		//Check button status and set latch state
		if(latchButton){
			this.stopTime = runtime.get();
			this.intakeLatch = true;

		}  else if (unlatchButton){
			this.stopTime = runtime.get();
			this.intakeLatch = false;
		}
		//depending on latch state, set motor speed, if switching latch state, allow motor cooldown time.
		if(this.intakeLatch){
				if((runtime.get() - stopTime) < this.delayTime){
					motor.set(0.0);
				} else {
					motor.set(this.motorFSpeed);
				}
		} else {
			if((runtime.get() - stopTime) < this.delayTime){
				motor.set(0.0);
			} else{
				motor.set(this.motorBSpeed); 
			}
		}
	}
	public String getLatchState(){
		/*
		 * Return a string of the latch state descriptor, used in smart dashboard
		 */
		if(this.intakeLatch){
			return this.latchedString;
		} else {
			return this.unlatchedString;
		}
	}
}
