package org.firstinspires.ftc.teamcode.RobotFunctions.subsystems;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.stormbots.MiniPID;

import org.firstinspires.ftc.teamcode.RobotFunctions.Calculators;

/**
 * This class adds hardware and methods for a tank drivetrain
 * The constructor needs a linear opmode, because any loops in this class are stopped when the opmode is stopped
 * Motors are declared as DcMotorEx because the DcMotorEx provides more useful functions
 *
 * TODO: clean up and add methods, switch from mini pid to custom pid
 * @author ethan
 */


public class DriveTrain {
    //public DcMotorEx bl, br, fl, fr;
    HardwareMap map;
    LinearOpMode linOpmode;
    Calculators cal = new Calculators();

    public DcMotorEx bl, br, fl, fr;

    double gamepadX, gamepadY;
    MiniPID pid1 = new MiniPID(0, 0, 0);
    MiniPID pid2 = new MiniPID(0, 0, 0);
    MiniPID pid3 = new MiniPID(0, 0, 0);
    MiniPID pid4 = new MiniPID(0, 0, 0); //1= back bl, 2 = front bl, 3 = back br, 4 = front br

    double leftOut, rightOut, frontLeftOut, frontRightOut;

    public enum motor_mode {
        run_to_position, run_with_encoder, run_without_encoder
    }

    public enum motor {
        bl, br, fl, fr
    }

    public DriveTrain(HardwareMap map, LinearOpMode linOpmode){ //drivetrain init function for hardware class
        this.map = map;
        this.linOpmode = linOpmode;
        bl = (DcMotorEx) map.dcMotor.get("bl");
        br = (DcMotorEx) map.dcMotor.get("br");
        fl = (DcMotorEx) map.dcMotor.get("fl");
        fr = (DcMotorEx) map.dcMotor.get("fr");
    }

    public DriveTrain(HardwareMap map){ //drivetrain init function for hardware class
        this.map = map;
        this.linOpmode = linOpmode;
        bl = (DcMotorEx) map.dcMotor.get("bl");
        br = (DcMotorEx) map.dcMotor.get("br");
        fl = (DcMotorEx) map.dcMotor.get("fl");
        fr = (DcMotorEx) map.dcMotor.get("fr");
    }

    public void arcadeDrive(double gamepadX, double gamepadY){//it drives
        this.gamepadX = gamepadX;
        this.gamepadY = gamepadY;

        bl.setPower(-gamepadY + gamepadX);
        br.setPower(-gamepadY - gamepadX);
        fl.setPower(-gamepadY + gamepadX);
        fr.setPower(-gamepadY - gamepadX);
    }

    int encoderDist;
    public void encoderMove(double distance, double power){ //in inches, set to negative to go backwards
        encoderDist = cal.Inches2Encoder(distance);
        setMode(motor_mode.run_to_position);
        bl.setPower(power);
        br.setPower(power);
        fl.setPower(power);
        fr.setPower(power);

        bl.setTargetPosition(bl.getCurrentPosition() + encoderDist);
        br.setTargetPosition(bl.getCurrentPosition() + encoderDist);
        fl.setTargetPosition(bl.getCurrentPosition() + encoderDist);
        fr.setTargetPosition(bl.getCurrentPosition() + encoderDist);

        while(linOpmode.opModeIsActive() && (bl.isBusy() && br.isBusy()) && fl.isBusy() && fr.isBusy()){} //waits for motors to stop moving
    }






    public void resetEncoders(){
        bl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        br.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        bl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        br.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        fl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        fr.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

    }

    public void SetBrake(){
        bl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        br.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        fl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        fr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

    }

    public void setMode(motor_mode mode){
        if(mode == motor_mode.run_without_encoder){
            bl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            br.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            fl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            fr.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        } else if(mode == motor_mode.run_with_encoder){
            bl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            br.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            fl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            fr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        } else if(mode == motor_mode.run_to_position){
            bl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            br.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            fl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            fr.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }
    }

    double output;
    double Kp;
    double Ki;
    double Kd;

    public void runProfile(double output){
        this.output = output;

        Kp = 0.03;
        Ki = 0.000007;
        Kd = 0;

        bl.setDirection(DcMotorSimple.Direction.REVERSE);
        fl.setDirection(DcMotorSimple.Direction.REVERSE);

        pid1 = new MiniPID(Kp, Ki, Kd);
        pid2 = new MiniPID(Kp, Ki, Kd);
        pid3 = new MiniPID(Kp, Ki, Kd);
        pid4 = new MiniPID(Kp, Ki, Kd);

        pid1.setDirection(true);
        pid2.setDirection(true);

        leftOut = pid1.getOutput(speed(motor.bl), output) + leftOut; // for velocity pid, adds pid out to previous output for better control
        rightOut = pid3.getOutput(speed(motor.br), output) + rightOut;
        frontLeftOut = pid2.getOutput(speed(motor.fl), output) + frontLeftOut;
        frontRightOut = pid4.getOutput(speed(motor.fr), output) + frontRightOut;

        bl.setPower(leftOut);
        br.setPower(rightOut);
        fl.setPower(frontLeftOut);
        fr.setPower(frontRightOut);

    }

    MiniPID Bl, Br, Fl, Fr, angle;
    double leftPow, rightPow, frontLeftPow, frontRightPow;
    double leftTgt, rightTgt, headingOut;

    public void setSpeeds(double leftSpeed, double rightSpeed, double heading, double headingTgt){
        bl.setDirection(DcMotorSimple.Direction.REVERSE);
        fl.setDirection(DcMotorSimple.Direction.REVERSE);

        Bl = new MiniPID(Kp, Ki, Kd);
        Br = new MiniPID(Kp, Ki, Kd);
        Fl = new MiniPID(Kp, Ki, Kd);
        Fr = new MiniPID(Kp, Ki, Kd);
        angle = new MiniPID(0.03, 0.000007, 0);

        headingOut = angle.getOutput((heading + 90) % 360, headingTgt);

        leftTgt = leftSpeed - headingOut;
        rightTgt = rightSpeed + headingOut;

        Br.setDirection(true);
        Fr.setDirection(true);

        leftPow = Bl.getOutput(speed(motor.bl), leftTgt) + leftPow; //for velocity pid, adds pid out to previous output for better control
        rightPow = Br.getOutput(speed(motor.br), rightTgt) + rightPow;
        frontLeftPow = Fl.getOutput(speed(motor.fl), leftTgt) + frontLeftPow;
        frontRightPow = Fr.getOutput(speed(motor.fr), rightTgt) + frontRightPow;

        bl.setPower(leftPow);
        br.setPower(rightPow);
        fl.setPower(frontLeftPow);
        fr.setPower(frontRightPow);
    }

    double prevTime, time, deltaTime;
    double leftPos, leftPreviousDist, leftDist;
    double rightPos, rightPreviousDist, rightDist;
    double frontLeftPos, frontLeftPreviousDist, frontLeftDist;
    double frontRightPos, frontRightPreviousDist, frontRightDist;
    double speed;
    public double speed(motor motor){//calculates linear speed of each wheel (ft/sec)
        time = System.currentTimeMillis() / 1000.0; //decimal needed because of int/int
        deltaTime = time - prevTime;
        if(motor == DriveTrain.motor.bl){
            leftPos = bl.getCurrentPosition();
            leftDist = cal.Encoder2Ft(leftPos);
            speed = -(leftDist - leftPreviousDist) / (deltaTime);
            leftPreviousDist = leftDist;
        } else if(motor == DriveTrain.motor.br){
            rightPos = br.getCurrentPosition();
            rightDist = cal.Encoder2Ft(rightPos);
            speed = -(rightDist - rightPreviousDist) / (deltaTime);
            rightPreviousDist = rightDist;
        } else if(motor == DriveTrain.motor.fl){
            frontLeftPos = fl.getCurrentPosition();
            frontLeftDist = cal.Encoder2Ft(frontLeftPos);
            speed = -(frontLeftDist - frontLeftPreviousDist) / (deltaTime);
            frontLeftPreviousDist = frontLeftDist;
        } else if(motor == DriveTrain.motor.fr){
            frontRightPos = fr.getCurrentPosition();
            frontRightDist = cal.Encoder2Ft(frontRightPos);
            speed = -(frontRightDist - frontRightPreviousDist) / (deltaTime);
            frontRightPreviousDist = frontRightDist;
        }
        prevTime = time;

        return speed;
    }












}
