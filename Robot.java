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
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.DriverStation;







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
	private static final String kCustomAuto2 = "My Auto 2";
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
	VictorSP armLift = new VictorSP(4);
	VictorSP rightIntake = new VictorSP(5);
	VictorSP leftIntake = new VictorSP(6);
	VictorSP intakeLift = new VictorSP (7);
	VictorSP armCloser = new VictorSP (8);
	JoystickButton button1;
	JoystickButton button2;
	Alliance color  = DriverStation.getInstance().getAlliance();
	int x=1 ;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		m_chooser.addDefault("Main Option", kDefaultAuto);
		m_chooser.addObject("Side Option One", kCustomAuto);
		m_chooser.addObject("Side Option Two", kCustomAuto2);
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
		timer.reset();
		timer.start();
		String gameData;
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		System.out.println("Auto selected: " + m_autoSelected);
		int station;
		station = DriverStation.getInstance().getLocation();
	} 

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
	
		
		
		
		while(isAutonomous() && isEnabled())
		{
			String gameData;
			gameData = DriverStation.getInstance().getGameSpecificMessage();
			System.out.println("Auto selected: " + m_autoSelected);
			int station;
			station = DriverStation.getInstance().getLocation();
			switch (m_autoSelected) {
				case kCustomAuto:
/*					if (station == 2 && station == 3){
						if(gameData.length()> 0)
						{
							if (gameData.charAt(0)== 'R'){
								if (x < 50) {
								SoT.arcadeDrive(-0.5, 0.0); 
								intakeLift.set(0.25);
								x=x+10;}
							if (x >= 50) {
								intakeLift.set(0.25);
								x=x+5;
							}
							if(x >= 75){
						     rightIntake.set(-0.5);
						    leftIntake.set(0.5);
						    x=x+10;
							}
							
							if(x==100){
				    					SoT.arcadeDrive(0.0, 0.0);
				    					Timer.delay(1.5);
				    		
				    	        	timer.reset();
				    			}
							}
							}
						}
			
					else{
						if (timer.get() < 3.0) {
					
						SoT.arcadeDrive(-0.5, 0.0); // drive towards heading 0
					} 
						else{
    				SoT.arcadeDrive(0.0, 0.0);
    				Timer.delay(1.5);
    		
    	        	timer.reset();}
			} */
					// Put custom auto code here

					break;
				case kDefaultAuto:
					default:
						if (timer.get() < 3.50) 
							{
							SoT.arcadeDrive(-0.5, 0.0); // drive towards heading 0
							}
						
		    			else{
		    				SoT.arcadeDrive(0,0);
		    	        	timer.reset();
			
		    				}
				// Put default auto code here
				break;
					case kCustomAuto2:
						if(gameData.length()> 0)
						{
							
							if (gameData.charAt(0)== 'R'){
								if(x<3000)
								{SoT.arcadeDrive(-0.5,0.0 );
								x=x+1;}
								if(x>3000){
									rightIntake.set(-0.3);
									leftIntake.set(0.3);}
								x=x+5;
							}
								if(x>4000 )
									
								{intakeLift.stopMotor();
								rightIntake.set(0);
								leftIntake.set(0);
								SoT.arcadeDrive(0,0);
						}
							
			}
						else
						{
							
						}
						}
			

						
						}	
		}
		
	

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {Scheduler.getInstance().run();
	
	
	while(isOperatorControl() && isEnabled())
	{
		if (gamepad.getRawButton(7)){
			armCloser.set(0.3);
		}
		else 
		{armCloser.stopMotor();}
		if (gamepad.getRawButton(8)){
			armCloser.set(-0.1);
		}
		else 
		{armCloser.stopMotor();}
		/*if(limitSwitchSstop.get()) //Stopper robotun kolu belirli bir yere kadar kalktiginda switch tarafindan algilandiginda kendisini durdurmasi icin.
	    {
	          rightIntake.set(0.0);// kolu durdur
	          leftIntake.set(0.0);} */
		SoT.arcadeDrive(stick.getY(),stick.getX());
		if(gamepad.getRawButton(4)== true && gamepad.getRawButton(3) == false)
		{
			armLift.set(0.3);
		}
		
		//Down
		else if(gamepad.getRawButton(3)== true && gamepad.getRawButton(4) == false)
		{
			armLift.set(-0.3);
		}
		else{
			armLift.stopMotor();
		}
		
		//***** Lift Mechanism Up/Down Motor - GamePad Button 1 and 2 *****//
		
				//Up
		
				if(gamepad.getRawButton(2)== true && gamepad.getRawButton(1) == false)
				{
					rightIntake.set(1.0);
					leftIntake.set(-1.0);
				}
				
				//Down
				else if(gamepad.getRawButton(1)== true && gamepad.getRawButton(2) == false)
				{
					rightIntake.set(-1.0);
					leftIntake.set(1.0);
				}
				else{
					
					rightIntake.stopMotor();
					leftIntake.stopMotor();
				}
				
				//***** Arm Open/Close - GamePad Button 5 and 6 *****//
				
				//Up
				if(gamepad.getRawButton(5)== true && gamepad.getRawButton(6) == false)
				{
					intakeLift.set(0.5);
				}
					
				else if (gamepad.getRawButton(6)== true && gamepad.getRawButton(5)== false)	
				{ 
					intakeLift.set(-0.5);
				}
				
				
				//Down
				
				else{
					
					intakeLift.stopMotor();
					
				}
						
				
				
					
				}
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
	
	
	

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
}
