/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.NetworkTable; 
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private Command m_autonomousCommand;
  private RobotContainer m_robotContainer;
  private NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
  private NetworkTableEntry tx = table.getEntry("tx");
  private NetworkTableEntry ty = table.getEntry("ty");
  private NetworkTableEntry ta = table.getEntry("ta");

  //read values periodically
  private double x = tx.getDouble(0.0);
  private double y = ty.getDouble(0.0);
  private double area = ta.getDouble(0.0);

   // Telemetry variables
   private double m_lastPort0 = 1000.0;
   private double m_lastPort1 = 1000.0;
   private double m_lastPort2 = 1000.0;
   private double m_lastPort3 = -1.0;
   private double m_lastPort4 = -1.0;
   private double m_lastPort5 = -1.0;
   private double m_lastPort6 = -1.0;
   private double m_lastPort7 = -1.0;
   private double m_lastPort8 = -1.0;
   private double m_lastPort9 = -1.0;

  /**
   * Update telemetry feedback for a real number value. If the value has not changed, no update is sent
   *
   * @param port      (int) The port 0 - 9 to write to.
   * @param key       (String) The key for the telemetry.
   * @param var       (double) The number to be reported.
   * @param lastValue (double) The last value reported.
   * @return (double) Returns {@code var}
   */
  @SuppressWarnings("unused")
  private double dashboardTelemetry(int port, String key, double var, double lastValue) {
    if (var != lastValue) {
      SmartDashboard.putString(String.format("DB/String %d", port), String.format("%s: %10.6f", key, var));
    }
    return var;
  }

  /**
   * Update telemetry feedback for an integer value. If the value has not changed, no update is sent
   *
   * @param port      (int) The port 0 - 9 to write to.
   * @param key       (String) The key for the telemetry.
   * @param var       (int) The integer to be reported.
   * @param lastValue (int) The last value reported.
   * @return (int) Returns {@code var}
   */
  @SuppressWarnings("unused")
  private int dashboardTelemetry(int port, String key, int var, int lastValue) {
    if (var != lastValue) {
      SmartDashboard.putString(String.format("DB/String %d", port), String.format("%s: %d", key, var));
    }
    return var;
  }

  /**
   * Update telemetry feedback for a string value. If the value has not changed, no update is sent
   *
   * @param port      (int) The port 0 - 9 to write to.
   * @param key       (String) The key for the telemetry.
   * @param var       (String) The string to be reported.
   * @param lastValue (String) The last value reported.
   * @return (String) Returns {@code var}
   */
  @SuppressWarnings("unused")
  private String dashboardTelemetry(int port, String key, String var, String lastValue) {
    if (!var.equals(lastValue)) {
      SmartDashboard.putString(String.format("DB/String %d", port), String.format("%s: %s", key, var));
    }
    return var;
  }

  /**
   * Update telemetry feedback for a boolean value. If the value has not changed, no update is sent
   *
   * @param port      (int) The port 0 - 9 to write to.
   * @param key       (String) The key for the telemetry.
   * @param var       (boolean) The boolean to be reported.
   * @param lastValue (boolean) The last value reported.
   * @return (boolean) Returns {@code var}
   */
  @SuppressWarnings("unused")
  private boolean dashboardTelemetry(int port, String key, boolean var, boolean lastValue) {
    if (var != lastValue) {
      SmartDashboard.putString(String.format("DB/String %d", port),
          String.format("%s: %s", key, var ? "on" : "off"));
    }
    return var;
  }

  private void displayTelemetry() { 
    m_lastPort0 = dashboardTelemetry(0, "tx", x, m_lastPort0);
    m_lastPort1 = dashboardTelemetry(1, "ty", y, m_lastPort1);
    m_lastPort2 = dashboardTelemetry(2, "ta", area, m_lastPort2);
    
  }

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
    // autonomous chooser on the dashboard.
    m_robotContainer = new RobotContainer();
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
    // commands, running already-scheduled commands, removing finished or interrupted commands,
    // and running subsystem periodic() methods.  This must be called from the robot's periodic
    // block in order for anything in the Command-based framework to work.
    CommandScheduler.getInstance().run();
    displayTelemetry();

    table = NetworkTableInstance.getDefault().getTable("limelight");
    tx = table.getEntry("tx");
    ty = table.getEntry("ty");
    ta = table.getEntry("ta");

  //read values periodically
    x = tx.getDouble(0.0);
    y = ty.getDouble(0.0);
    area = ta.getDouble(0.0);

  }

  /**
   * This function is called once each time the robot enters Disabled mode.
   */
  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
  }

  /**
   * This autonomous runs the autonomous command selected by your {@link RobotContainer} class.
   */
  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
  }

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
