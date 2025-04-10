package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name = "Specimen - Humanzone", group = "Autonomous")
public class FirstAuto extends LinearOpMode {

    private DcMotorEx frontLeft, frontRight, backLeft, backRight;

    @Override
    public void runOpMode() throws InterruptedException {

        frontLeft = hardwareMap.get(DcMotorEx.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotorEx.class, "frontRight");
        backLeft = hardwareMap.get(DcMotorEx.class, "backLeft");
        backRight = hardwareMap.get(DcMotorEx.class, "backRight");

        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.REVERSE);

        DcMotorEx[] motors = {frontLeft, frontRight, backLeft, backRight};
        for (DcMotorEx motor : motors) {
            motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }

        telemetry.addLine("Ready to run auto");
        telemetry.update();

        waitForStart();

        double countsPerRev = 537.7;
        double wheelDiameterInches = 3.8;
        double countsPerInch = countsPerRev / (wheelDiameterInches * Math.PI);

        int forward22 = (int)(22 * countsPerInch);
        int forward15 = (int)(15 * countsPerInch);
        int forward10 = (int)(10 * countsPerInch);
        int forward5 =  (int)(5 * countsPerInch);
        int backward15 = (int)(-15 * countsPerInch);
        int right12 = (int)(12 * countsPerInch);

        moveAndSleep(forward22, 0.5);
        rotate90Degrees(0.5); sleep(500);
        moveAndSleep(forward10, 0.5);
        rotate90Degrees(0.5); sleep(500);
        moveAndSleep(forward5, 0.5);
        strafeAndSleep(right12, 0.5);
        moveAndSleep(backward15, 0.5);
        moveAndSleep(forward15, 0.5);
        strafeAndSleep(right12, 0.5);
        moveAndSleep(backward15, 0.5);
        moveAndSleep(forward15, 0.5);
        strafeAndSleep(right12, 0.5);
        moveAndSleep(backward15, 0.5);

        telemetry.addLine("Auto path complete!");
        telemetry.update();
    }

    private void moveAndSleep(int ticks, double power) {
        moveInches(ticks, power);
        sleep(500);
    }

    private void strafeAndSleep(int ticks, double power) {
        strafeInches(ticks, power);
        sleep(500);
    }

    private void moveInches(int ticks, double power) {
        DcMotorEx[] motors = {frontLeft, frontRight, backLeft, backRight};

        for (DcMotorEx motor : motors) {
            motor.setTargetPosition(motor.getCurrentPosition() + ticks);
            motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motor.setPower(power);
        }

        while (opModeIsActive() && frontLeft.isBusy()) {
            telemetry.addData("Moving to", frontLeft.getTargetPosition());
            telemetry.addData("Current", frontLeft.getCurrentPosition());
            telemetry.update();
        }

        for (DcMotorEx motor : motors) {
            motor.setPower(0);
            motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }

    private void strafeInches(int ticks, double power) {
        frontLeft.setTargetPosition(frontLeft.getCurrentPosition() + ticks);
        backLeft.setTargetPosition(backLeft.getCurrentPosition() - ticks);
        frontRight.setTargetPosition(frontRight.getCurrentPosition() - ticks);
        backRight.setTargetPosition(backRight.getCurrentPosition() + ticks);

        DcMotorEx[] motors = {frontLeft, frontRight, backLeft, backRight};

        for (DcMotorEx motor : motors) {
            motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motor.setPower(power);
        }

        while (opModeIsActive() && frontLeft.isBusy()) {
            telemetry.addData("Strafing to", frontLeft.getTargetPosition());
            telemetry.addData("Current", frontLeft.getCurrentPosition());
            telemetry.update();
        }

        for (DcMotorEx motor : motors) {
            motor.setPower(0);
            motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }

    private void rotate90Degrees(double power) {

        double countsPerRev = 537.7;
        double wheelDiameterInches = 3.8;
        double countsPerInch = countsPerRev / (wheelDiameterInches * Math.PI);

        double wheelBaseDiameter = 14.0;
        double turnCircumference = Math.PI * wheelBaseDiameter;
        double distancePerWheel = turnCircumference / 4;
        int ticks = (int)(distancePerWheel * countsPerInch);

        frontLeft.setTargetPosition(frontLeft.getCurrentPosition() - ticks);
        backLeft.setTargetPosition(backLeft.getCurrentPosition() - ticks);
        frontRight.setTargetPosition(frontRight.getCurrentPosition() + ticks);
        backRight.setTargetPosition(backRight.getCurrentPosition() + ticks);

        DcMotorEx[] motors = {frontLeft, frontRight, backLeft, backRight};

        for (DcMotorEx motor : motors) {
            motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motor.setPower(power);
        }

        while (opModeIsActive() && frontLeft.isBusy()) {
            telemetry.addData("Rotating to", frontLeft.getTargetPosition());
            telemetry.addData("Current", frontLeft.getCurrentPosition());
            telemetry.update();
        }

        for (DcMotorEx motor : motors) {
            motor.setPower(0);
            motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }
}