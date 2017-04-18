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
	private double motorSSpeed; //Speed of the motor (Stop)
	private double delayTime;
	private String latchedString;
	private String unlatchedString;
	private String state2String;

	
	//-------------------------CONSTRUCTORS
	//-------------------------------------
	public SubMotor(SpeedController motor, double state0Speed, double state1Speed, double delayTime,String state0, String state1){
		/*
		 * Constructor for SubMotor Class, used in latched/unlatched state. requires the following inputs:
		 * 
		 * SpeedController motor = The single motor controller defined in RobotMap
		 * double state0Speed = Speed of the motor when in state 0
		 * double state1Speed = Speed of the motor when in state 1
		 * double delayTime = Delay time between switching b/t latched/unlatched state
		 * String state0,state1 = String descriptor of latched/unlatched states, to be used in smart dashboard.
		 */
		this.motorFSpeed = state0Speed;
		this.motorBSpeed = state1Speed;
		this.motor = motor;
		this.delayTime = delayTime;
		this.latchedString = state0;
		this.unlatchedString = state1;
	}
	
	public SubMotor(SpeedController motor, double state0Speed, double state1Speed, double state2Speed, double delayTime, String state0, String state1, String state2){
		
		this.motorFSpeed = state0Speed;
		this.motorBSpeed = state1Speed;
		this.motorSSpeed = state2Speed;
		this.motor = motor;
		this.delayTime = delayTime;
		this.latchedString = state0;
		this.unlatchedString = state1;
		this.state2String = state2;
	}
	
	//-------------------------METHODS
	//--------------------------------
	@Override
	protected void initDefaultCommand() {
		
	}
	 
	public void runMotorLatched(Timer runtime, boolean state0Button, boolean state1Button){
		/*
		 * Run the motor latched/unlatched. Method takes two controller button inputs (as boolean)
		 * Timer runtime = total runtime of the autonomous/teleop opMode
		 * boolean latchButton = controller input for latching motor
		 * boolean unlatchButton = controller input for unlatching motor
		 */
		
		//Check button status and set latch state
		if(state0Button){
			this.stopTime = runtime.get();
			this.intakeLatch = true;

		}  else if (state1Button){
			this.stopTime = runtime.get();
			this.intakeLatch = false;
		}
		//depending on latch state, set motor speed, if switching latch state, allow motor cooldown time.
		if(this.intakeLatch){
				if((runtime.get() - stopTime) < this.delayTime){
					motor.set(0.0);
				} else {

					motor.pidWrite(this.motorFSpeed);
				}
		} else {
			if((runtime.get() - stopTime) < this.delayTime){
				motor.set(0.0);
			} else{
				motor.pidWrite(this.motorBSpeed); 
			}
		}
	}
	
	public void motorThreeStateRun(Timer runtime, boolean state0Button, boolean state2Button, boolean state3Button){
		
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
	
	public void runMotor(double Speed){
		
	}
}
