package org.firstinspires.ftc.teamcode.DriveModes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.RobotFunctions.DataLogger;
import org.firstinspires.ftc.teamcode.RobotFunctions.Calculators;

/**
 * Created by ethan on 3/22/18.
 */
@TeleOp
public class ArcadeDriveLogger extends OpMode {
    DcMotor left;
    DcMotor right;
    DcMotor frontLeft;
    DcMotor frontRight;

    double averageLeft, averageRight;

    Calculators convert = new Calculators();

    int count;

    DataLogger data = new DataLogger("data");

    @Override
    public void init(){
        left = hardwareMap.get(DcMotor.class, "bl");
        right = hardwareMap.get(DcMotor.class, "br");
        frontLeft = hardwareMap.get(DcMotor.class, "fl");
        frontRight = hardwareMap.get(DcMotor.class, "fr");

        right.setDirection(DcMotorSimple.Direction.REVERSE);
        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);

        data.addField("time");
        data.addField("bl position");
        data.addField("br position");
        data.newLine();



    }
    @Override
    public void loop(){
        left.setPower(gamepad1.left_stick_y - gamepad1.left_stick_x);
        right.setPower(gamepad1.left_stick_y + gamepad1.left_stick_x);
        frontLeft.setPower(gamepad1.left_stick_y - gamepad1.left_stick_x);
        frontRight.setPower(gamepad1.left_stick_y + gamepad1.left_stick_x);

        telemetry.addData("bl motor pow", left.getPower());
        telemetry.addData("br motor pow", right.getPower());

        averageLeft = (left.getCurrentPosition() + frontLeft.getCurrentPosition()) / 2;
        averageRight = (right.getCurrentPosition() + frontRight.getCurrentPosition()) / 2;

        if(count == 20){
            data.addField(getRuntime());
            data.addField(convert.Encoder2Ft(averageLeft));
            data.addField(convert.Encoder2Ft(averageRight));
            data.newLine();

            count = 0;
        }
        count++;
    }
}
