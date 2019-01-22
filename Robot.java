/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.CameraServer;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends IterativeRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
  VictorSP onSol = new VictorSP(0);
  VictorSP arkaSol = new VictorSP(1);
  VictorSP onSag = new VictorSP(2);
  VictorSP arkaSag = new VictorSP(3);
  VictorSP topAlim = new VictorSP (4);
  Joystick gamepad = new Joystick(1);
  Joystick attack3 = new Joystick(0);
  SpeedControllerGroup sol = new SpeedControllerGroup (onSol,arkaSol);
  SpeedControllerGroup sag = new SpeedControllerGroup (onSag,arkaSag);
  DifferentialDrive SoT = new DifferentialDrive(sol, sag);
  double leftSlow = -0.24;
  double rightSlow = 0.24;
  double rotateSpeed = 0.35;
  double rotateSpeedSlow = 0.25;
  AnalogGyro gyro = new AnalogGyro(0) ;
  
  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);
   // CameraServer.getInstance().startAutomaticCapture("USB Camera 1", 0);
    gyro.reset();
    
  
    
    
    
    
  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
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
    gyro.reset();
    System.out.println("Auto selected: " + m_autoSelected);
  
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    SmartDashboard.putNumber("Gyro",gyro.getAngle());
    if (Math.abs(gyro.getAngle()) <= 3) {
      sol.set((leftSlow - (gyro.getAngle()) / 15)*-1); // -1 ile çarpmadan önce geriye gidiyordu
      sag.set((rightSlow - (gyro.getAngle()) / 15)* -1);
     } else if (Math.abs(gyro.getAngle()) < 10) {
      if (gyro.getAngle() > 0) {
       sol.set(rightSlow);
       sag.set(1.1 * rightSlow);
      } else if (gyro.getAngle() < 0) {
       sol.set(1.1 * leftSlow);
       sag.set(rightSlow);
      }
     } else
      if (gyro.getAngle() > 0) {
       while (gyro.getAngle() > 10 && isAutonomous()) {
        sol.set(-rotateSpeed);
        sag.set(-rotateSpeed);
       }
      while (gyro.getAngle() > 0 && isAutonomous()) {
       sol.set(-rotateSpeedSlow);
       sag.set(-rotateSpeedSlow);
      }
      while (gyro.getAngle() < 0 && isAutonomous()) {
       sol.set(rotateSpeedSlow);
       sag.set(rotateSpeedSlow);
      }
     } else {
      while (gyro.getAngle() < -10 && isAutonomous()) {
       sol.set(rotateSpeed);
       sag.set(rotateSpeed);
      }
      while (gyro.getAngle() < 0 && isAutonomous()) {
       sol.set(rotateSpeedSlow);
       sag.set(rotateSpeedSlow);
      }
      while (gyro.getAngle() > 0 && isAutonomous()) {
       sol.set(-rotateSpeedSlow);
       sag.set(-rotateSpeedSlow);
      }
     }
    }
   
    
  

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
SoT.arcadeDrive(attack3.getY(), attack3.getX());
    if (gamepad.getRawButton(1)){
			topAlim.set(0.3);
		}
		else 
		{topAlim.stopMotor();}
		if (gamepad.getRawButton(2)){
			topAlim.set(-0.1);
		}
		else 
		{topAlim.stopMotor();}
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
