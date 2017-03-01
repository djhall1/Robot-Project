package org.usfirst.frc6169.Test.subsystems;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;

public class SubMotor extends Subsystem {
	
	/*
	 * Class used for single Victor motor operations
	 */
	
	private final SpeedController motor;
	private boolean intakeLatch; //Latches the intake motor
	private double stopTime;
	private double intakeFSpeed; //Speed of the intake.
	private double intakeBSpeed;
	private double delayTime;
	private String latchedString;
	private String unlatchedString;

	public SubMotor(SpeedController motor, double forwardSpeed, double backwardSpeed, double delayTime,String state0, String state1){
		//Use motor defined in RobotMap, define a speed tied to forward + backward buttons.
		this.intakeFSpeed = forwardSpeed;
		this.intakeBSpeed = backwardSpeed;
		this.motor = motor;
		this.delayTime = delayTime;
		this.latchedString = state0;
		this.unlatchedString = state1;
	}
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
	 
	public void runMotor(Timer runtime, boolean forwardButton, boolean backwardButton){
		if(forwardButton){
			this.stopTime = runtime.get();
			this.intakeLatch = true;

		}  else if (backwardButton){
			this.stopTime = runtime.get();
			this.intakeLatch = false;
		}
		if(this.intakeLatch){
				if((runtime.get() - stopTime) < this.delayTime){
					motor.set(0.0);
				} else {
					motor.pidWrite(this.intakeFSpeed);
				}
		} else {
			if((runtime.get() - stopTime) < this.delayTime){
				motor.set(0.0);
			} else{
				motor.pidWrite(this.intakeBSpeed); 
			}
		}
	}
	public String getLatchState(){
		if(this.intakeLatch){
			return this.latchedString;
		} else {
			return this.unlatchedString;
		}
	}
}
