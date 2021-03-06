package org.firstinspires.ftc.teamcode.DriveModes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

/**
 * Created by ethan on 3/9/18.
 */
@TeleOp
public class EncoderDrive extends OpMode {
    DcMotor left;
    DcMotor right;
    DcMotor frontLeft;
    DcMotor frontRight;
    @Override
    public void init(){
        left = hardwareMap.get(DcMotor.class, "bl");
        right = hardwareMap.get(DcMotor.class, "br");
        frontLeft = hardwareMap.get(DcMotor.class, "fl");
        frontRight = hardwareMap.get(DcMotor.class, "fr");


        right.setDirection(DcMotorSimple.Direction.REVERSE);
        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);


        left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }
    @Override
    public void loop(){
        left.setPower(gamepad1.left_stick_y - gamepad1.left_stick_x);
        right.setPower(gamepad1.left_stick_y + gamepad1.left_stick_x);
        frontLeft.setPower(gamepad1.left_stick_y - gamepad1.left_stick_x);
        frontRight.setPower(gamepad1.left_stick_y + gamepad1.left_stick_x);

        telemetry.addData("bl motor pow", left.getPower());
        telemetry.addData("br motor pow", right.getPower());



    }
}
