package org.firstinspires.ftc.teamcode.Testing;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.RobotFunctions.TankHardware;
import org.firstinspires.ftc.teamcode.RobotFunctions.Calculators;

/**
 * Created by ethan on 3/6/18.
 */
@Autonomous
public class encoder extends OpMode {
    DcMotor left;
    DcMotor right;
    DcMotor frontLeft;
    DcMotor frontRight;

    int encoderLeftDist;
    int encoderRightDist;
    double leftDist;
    double rightDist;

    TankHardware h = new TankHardware();

    Calculators convert = new Calculators();
    @Override
    public void init(){
        left = hardwareMap.get(DcMotor.class, "bl");
        right = hardwareMap.get(DcMotor.class, "br");
        frontLeft = hardwareMap.get(DcMotor.class, "fl");
        frontRight = hardwareMap.get(DcMotor.class, "fr");

        left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    @Override
    public void loop(){
        left.setPower(0.4);
        right.setPower(-0.4);
        frontRight.setPower(-0.4);
        frontLeft.setPower(0.4);

        telemetry.addData("bl Pos", left.getCurrentPosition());
        telemetry.addData("br pos", right.getCurrentPosition());

        encoderLeftDist = (left.getCurrentPosition() + frontLeft.getCurrentPosition()) / 2;
        encoderRightDist = (right.getCurrentPosition() + frontRight.getCurrentPosition()) / 2;

        leftDist = convert.Encoder2Inches(encoderLeftDist);
        rightDist = convert.Encoder2Inches(encoderRightDist);

        telemetry.addData("bl distance", "% ft", leftDist );
        telemetry.addData("br distance", "% ft", rightDist );
    }
}
