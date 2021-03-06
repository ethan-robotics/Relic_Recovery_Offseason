package org.firstinspires.ftc.teamcode.DriveModes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.RobotFunctions.MecanumHardware;

@TeleOp
public class FieldCentricMecanum extends OpMode {
    MecanumHardware robot = new MecanumHardware();

    public void init(){
        robot.init(hardwareMap);
    }

    public void loop(){
        robot.mecanumDrive.FieldCentricArcade(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x, robot.sensors.getHeading());
    }
}
