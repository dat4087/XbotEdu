package competition.subsystems.drive.commands;

import javax.inject.Inject;

import xbot.common.command.BaseCommand;
import competition.subsystems.drive.DriveSubsystem;
import competition.subsystems.pose.PoseSubsystem;

public class DriveToPositionCommand extends BaseCommand {

    DriveSubsystem drive;
    PoseSubsystem pose;
    double currentPosition;
    double setpoint;
    double kp, ki, kd;
    double positionError;
    static final double error_threshold = 0.2;
    double driveP;

    @Inject
    public DriveToPositionCommand(DriveSubsystem driveSubsystem, PoseSubsystem pose) {
        this.drive = driveSubsystem;
        this.pose = pose;
    }

    public void setTargetPosition(double position) {
        // This method will be called by the test, and will give you a goal distance.
        // You'll need to remember this target position and use it in your calculations.
        this.setpoint = position;
    }

    @Override
    public void initialize() {
        // If you have some one-time setup, do it here.
        currentPosition = pose.getPosition();
        kp = 1000.0;
        ki = 0.0;
        kd = 0.0;
        positionError = setpoint-currentPosition;
    }

    @Override
    public void execute() {
        // Here you'll need to figure out a technique that:
        // - Gets the robot to move to the target position
        // - Hint: use pose.getPosition() to find out where you are
        // - Gets the robot stop (or at least be moving really really slowly) at the
        // target position

        // How you do this is up to you. If you get stuck, ask a mentor or student for
        // some hints!
        currentPosition = pose.getPosition();
        positionError = setpoint - currentPosition;
        driveP = positionError*kp;
        drive.frontLeft.simpleSet(driveP);

    }

    @Override
    public boolean isFinished() {
        // Modify this to return true once you have met your goal,
        // and you're moving fairly slowly (ideally stopped)
        if (Math.abs(positionError) <= error_threshold) {
            drive.frontLeft.simpleSet(0.0);
            return true;
        } else return false;
    }

}
