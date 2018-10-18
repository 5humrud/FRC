/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team2905.robot;

import edu.wpi.first.wpilibj.IterativeRobot;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;






/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends IterativeRobot {
	private static final String kDefaultAuto = "Default";
	private static final String kCustomAuto = "My Auto";
	private String m_autoSelected;
	private SendableChooser<String> m_chooser = new SendableChooser<>();
	private SpeedControllerGroup left = new SpeedControllerGroup(new VictorSP(0), new VictorSP(1));
	private SpeedControllerGroup right = new SpeedControllerGroup(new VictorSP(2), new VictorSP(3));
	DifferentialDrive  SoT = new DifferentialDrive(right , left);
	Joystick stick = new Joystick(0);
	Joystick gamepad = new Joystick(1);
	private Timer timer = new Timer();
	DigitalInput limitSwitchSlow;
	DigitalInput limitSwitchSstop;
	VictorSP armMechanismUpDownMotor = new VictorSP(4);
	VictorSP rightLiftMotor = new VictorSP(5);
	VictorSP leftLiftMotor = new VictorSP(6);	
	JoystickButton button1;
	JoystickButton button2;
	Alliance color  = DriverStation.getInstance().getAlliance();

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		m_chooser.addDefault("Default Auto", kDefaultAuto);
		m_chooser.addObject("My Auto", kCustomAuto);
		SmartDashboard.putData("Auto choices", m_chooser);
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString line to get the auto name from the text box below the Gyro
	 *
	 * <p>You can add additional auto modes by adding additional comparisons to
	 * the switch structure below with additional strings. If using the
	 * SendableChooser make sure to add them to the chooser code above as well.
	 */
	@Override
	public void autonomousInit() {
		m_autoSelected = m_chooser.getSelected();
		// autoSelected = SmartDashboard.getString("Auto Selector",
		// defaultAuto);
		timer.reset();
		timer.start();
		System.out.println("Auto selected: " + m_autoSelected);
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		while(isAutonomous() && isEnabled())
			switch (m_autoSelected) {
				case kCustomAuto:
					// Put custom auto code here
					break;
				case kDefaultAuto:
					default:if (timer.get() < 15.00) {
						SoT.arcadeDrive(-0.75, 0.0); // drive towards heading 0
						leftLiftMotor.set(0.75);} 
		    			else{
		    				SoT.arcadeDrive(0.0, 0.0);
		    				Timer.delay(1.5);
		    		
		    	        	timer.reset();
			
			}
				// Put default auto code here
				break;
		}
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {Scheduler.getInstance().run();
	
	
	while(isOperatorControl() && isEnabled())
	{
		/*if(limitSwitchSstop.get()) //Stopper robotun kolu belirli bir yere kadar kalktiginda switch tarafindan algilandiginda kendisini durdurmasi icin.
	    {
	          rightLiftMotor.set(0.0);// kolu durdur
	          leftLiftMotor.set(0.0);} */
		SoT.arcadeDrive(stick.getY(),stick.getX());
		boolean colormsg = true;
		}
				
				
				
			
		
		
		// Getting Team Color
		/*Alliance color;
		color = DriverStation.getInstance().getAlliance(); 
		
		if(color == DriverStation.Alliance.Blue)
		{
			DriverStation.reportWarning("Team color is Blue", colormsg);
		}
		else
		{
			DriverStation.reportWarning("Team color is Red", colormsg);
		}*/
		//Getting Location Number
		
		//***** Arm Mechanism Up/Down Motor - GamePad Button 3 and 4 *****//
		
		//Up
		if(gamepad.getRawButton(4)== true && gamepad.getRawButton(3) == false)
		{
			armMechanismUpDownMotor.set(0.1);
		}
		
		//Down
		else if(gamepad.getRawButton(3)== true && gamepad.getRawButton(4) == false)
		{
			armMechanismUpDownMotor.set(-0.1);
		}
		else{
			armMechanismUpDownMotor.stopMotor();
		}
		
		//***** Lift Mechanism Up/Down Motor - GamePad Button 1 and 2 *****//
		
				//Up
				if(gamepad.getRawButton(2)== true && gamepad.getRawButton(1) == false)
				{
					rightLiftMotor.set(0.1);
					leftLiftMotor.set(-0.1);
				}
				
				//Down
				else if(gamepad.getRawButton(1)== true && gamepad.getRawButton(2) == false)
				{
					rightLiftMotor.set(-0.1);
					leftLiftMotor.set(0.1);
				}
				else{
					
					rightLiftMotor.stopMotor();
					leftLiftMotor.stopMotor();
				}
				
				//***** Arm Open/Close - GamePad Button 5 and 6 *****//
				
				//Up
				/*if(gamepad.getRawButton(5)== true && gamepad.getRawButton(6) == false)
				{
					rightArmValve.set(true);
					leftArmValve.set(true);
				}
				
				//Down
				
				else{
					
					rightArmValve.set(false);
					leftArmValve.set(false);
				}
	}*/
}
	

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
}
