package org.firstinspires.ftc.teamcode.DriveModes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.RobotFunctions.Calculators;
import org.firstinspires.ftc.teamcode.RobotFunctions.TankHardware;
import org.firstinspires.ftc.teamcode.RobotFunctions.Point;

@TeleOp
public class GetPosition extends OpMode {
    TankHardware robot = new TankHardware();
    Point position = new Point(0, 0);
    Calculators cal = new Calculators();
    Point origin = new Point(0, 0);


    @Override
    public void init(){
        robot.init(hardwareMap);
    }

    @Override
    public void loop(){
        robot.driveTrain.arcadeDrive(gamepad1.left_stick_x, gamepad1.left_stick_y);

        position = robot.GetPosition();

        telemetry.addData("x", position.getX());
        telemetry.addData("y", position.getY());
        //telemetry.addData("heading", robot.sensors.getHeading());
        telemetry.addData("bl encoder", robot.driveTrain.bl.getCurrentPosition());
        telemetry.addData("br encoder", robot.driveTrain.br.getCurrentPosition());
        telemetry.addData("distance from start", cal.PointDistance(origin, position));

        telemetry.update();



    }
}
