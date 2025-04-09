package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode; // Import LinearOpMode base class
import com.qualcomm.robotcore.eventloop.opmode.TeleOp; // Import TeleOp annotation
import com.qualcomm.robotcore.hardware.DcMotor; // Import DcMotor class
import com.qualcomm.robotcore.hardware.DcMotorEx; // Import DcMotorEx for extended motor control

@TeleOp(name = "Simple driving", group = "TeleOp")
public class SimpleDriveOpMode extends LinearOpMode {

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

        telemetry.addLine("Ready");
        telemetry.update();

        waitForStart();

        double countsPerRev = 537.7;
        double wheelDiameterInches = 3.8;
        double countsPerInch = countsPerRev / (wheelDiameterInches * Math.PI);
        double inches = 10;
        int target = (int)(inches * countsPerInch);

        boolean hasMoved = false;

        while (opModeIsActive()) {

            double deadZone = 0.05;
            double y = Math.abs(gamepad1.left_stick_y) > deadZone ? -gamepad1.left_stick_y : 0.0;
            double x = Math.abs(gamepad1.left_stick_x) > deadZone ? gamepad1.left_stick_x * 1.1 : 0.0;
            double rx = Math.abs(gamepad1.right_stick_x) > deadZone ? gamepad1.right_stick_x : 0.0;

            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double frontLeftPower = (y + x + rx) / denominator;
            double backLeftPower = (y - x + rx) / denominator;
            double frontRightPower = (y - x - rx) / denominator;
            double backRightPower = (y + x - rx) / denominator;

            if (gamepad1.triangle && !hasMoved) {
                moveInches(target, 0.5);
                sleep(500);
                rotate90Degrees(0.5);
                sleep(500);
                moveInches(target, 0.5);
                sleep(500);
                rotate90Degrees(0.5);
                sleep(500);
                moveInches(target, 0.5);
                sleep(500);
                rotate90Degrees(0.5);
                sleep(500);
                moveInches(target, 0.5);
                sleep(500);
                rotate90Degrees(0.5);
                sleep(500);
                hasMoved = true;
            }

            if (gamepad1.square && !hasMoved){
                while (!gamepad1.square){
                    rotate90Degrees(0.5);
                }
                hasMoved = true;
            }

            if (!gamepad1.triangle && (y != 0 || x != 0 || rx != 0)) {
                frontLeft.setPower(frontLeftPower);
                frontRight.setPower(frontRightPower);
                backLeft.setPower(backLeftPower);
                backRight.setPower(backRightPower);
            } else if (!gamepad1.triangle) {
                frontLeft.setPower(0);
                frontRight.setPower(0);
                backLeft.setPower(0);
                backRight.setPower(0);
            }

            telemetry.addLine("Mecanum Drive Active");
            telemetry.update();
        }
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
            telemetry.addData("Current Position", frontLeft.getCurrentPosition());
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