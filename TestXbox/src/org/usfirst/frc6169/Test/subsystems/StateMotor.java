package org.usfirst.frc6169.Test.subsystems;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;

public class StateMotor extends Subsystem{
	// State-based motor control (hopefully)
	// 3 States right now, build to easily extend states.
	// Can add add'l functionality to output strings.
	
	private SpeedController motor; // motor to control
	private double delayTime; //Time to switch between states
	private Timer delayTimer = new Timer(); //Timer for delay time
	
	private double state0Speed; //State 0,1, 2... speeds
	private double state1Speed;
	private double state2Speed;
	
	private boolean state0Input; //Input 0
	private boolean state1Input;
	private boolean state2Input;
	
	
	private int numStates; // number of currently used states.
	
	
	private int currentState; // Current state of motor
	
	//-------------------------------------------------------------
	// CONSTRUCTORS
	// ----------------------------------------------------------
	
	public StateMotor(SpeedController motor, double delayTime, double state0Speed, double state1Speed, boolean state0Input, boolean state1Input ){
		//Two-State motor
		delayTimer.start();
		this.state0Speed = state0Speed;
		this.state1Speed = state1Speed;
		this.state0Input = state0Input;
		this.state1Input = state1Input;
		this.delayTime = delayTime;
		this.motor = motor;
		this.numStates = 2;
	}

	public StateMotor(SpeedController motor, double state0Speed, double state1Speed, double state2Speed, boolean state0Input, boolean state1Input, boolean state2Input, double delayTime){
		delayTimer.start();
		//Three-State motor
		this.state0Speed = state0Speed;
		this.state1Speed = state1Speed;
		this.state2Speed = state2Speed;
		this.state0Input = state0Input;
		this.state1Input = state1Input;
		this.state2Input = state2Input;
		this.delayTime = delayTime;
		this.motor = motor;
		this.numStates = 3;
	}
	
	private boolean checkState(){
		// Check the current state of the motor, return TRUE if state has
		// changed
		int lastState = currentState;
		if (this.numStates == 2){
			this.currentState = twoStateChange();
		} else if (this.numStates == 3){
			this.currentState = threeStateChange();
		}
		if (this.currentState != lastState){
			return true;
		} else {
			return false;
		}
	}
	
	private int twoStateChange(){
		// Check inputs, if they change, change state
		if (state0Input){
			return 0;
		} else if (state1Input){
			return 1;
		} else {
			return currentState;
		}
	}
	private int threeStateChange(){
		// Check inputs, if they change, change state
		if (state0Input){
			return 0;
		} else if (state1Input){
			return 1;
		} else if (state2Input){
			return 2;
		} else {
			return currentState;
		}
	}
	
	
	
	private void twoStateDrive(){
		// drive motor with two states
		
		if (delayTimer.get() > delayTime){
			if (currentState == 0){
				motor.pidWrite(state0Speed);
			}
			if (currentState == 1){
				motor.pidWrite(state1Speed);
				
			}
		}
	}	
	
	private void threeStateDrive(){
		// drive motor with three states
		if (delayTimer.get() > delayTime){
			if (currentState == 0){
				motor.pidWrite(state0Speed);
			} else if (currentState == 1){
				motor.pidWrite(state1Speed);
			} else if (currentState == 2){
				motor.pidWrite(state2Speed);
			}
		}
	}
	
	public void runMotor(){
		
		// if states have changed, invoke delay time
		if (checkState()) {
			delayTimer.reset();
		}
		
		// run motors based on number of states configured.
		if (numStates == 2){
			twoStateDrive();
		} else if (numStates == 3){
			threeStateDrive();
		}
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}

}
