package org.firstinspires.ftc.teamcode.Testing;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.RobotFunctions.DataLogger;
import org.firstinspires.ftc.teamcode.RobotFunctions.Hardware;
import org.firstinspires.ftc.teamcode.RobotFunctions.calculators;
@TeleOp
public class CalculateSpeedDriveTest extends OpMode {//does what the name says
    DataLogger data = new DataLogger("speed test");
    calculators cal = new calculators();
    double leftSpeed, rightSpeed, preTime, leftPreDist, rightPreDist, leftDist, rightDist, curTime, timeDiff, leftDistDiff, rightDistDiff;
    int count;
    Hardware robot = new Hardware();
    @Override
    public void init(){
        robot.init(hardwareMap);

        data.addField("left speed");
        data.addField("right speed");
        data.newLine();

    }
    @Override
    public void init_loop(){
        resetStartTime();
    }
    @Override
    public void loop(){
        robot.driveTrain.arcadeDrive(gamepad1.left_stick_x, gamepad1.left_stick_y);

        leftDist = cal.Encoder2Ft(robot.driveTrain.left.getCurrentPosition());
        rightDist = cal.Encoder2Ft(robot.driveTrain.right.getCurrentPosition());
        curTime = getRuntime();

        timeDiff = curTime - preTime;
        leftDistDiff = leftDist - leftPreDist;
        rightDistDiff = rightDist - rightPreDist;

        telemetry.addData("left speed", robot.driveTrain.leftSpeed()); // only used on left motor because I'm lazy
        telemetry.addData("right speed", robot.driveTrain.rightSpeed());
        telemetry.update();

        leftPreDist = leftDist;
        rightPreDist = rightDist;
        preTime = curTime;
        count++;
    }
}
