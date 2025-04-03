package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class LinearTeleOp extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        // Initialization code goes here
        boolean pressed;
        DcMotor motorOne;
        DcMotorEx motorTwo;

        motorOne = hardwareMap.get(DcMotor.class, "motor_one"); // Configure the same in the driver hub
        motorTwo = hardwareMap.get(DcMotorEx.class, "motor_two");  // Configure the same in the driver hub
        //
        // MOTOR DIRECTIONS
        //
        // The motor is by default set to forward only use this if you,
        // preveausly set the direction to reverse.
        motorOne.setDirection(DcMotorSimple.Direction.FORWARD);
        motorOne.setDirection(DcMotorSimple.Direction.REVERSE);
        //
        // MOTOR BRAKES
        //
        motorOne.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        // Hard stop when this line is set
        motorOne.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        // Slowly stops the motors
        //
        // MOTOR MODES
        //
        motorOne.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        // Tells the motor to use its encoder to manage any power set to it
        motorOne.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        // Tells the motor to don t use the encoder that is pluged into
        motorOne.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        // Resets the value and place it to 0 everytime

        waitForStart(); // it waits till the start button it's pressed

        while(opModeIsActive()){
            // Happens repeatedly while the program is running
            // Functions goes here
            motorOne.setPower(1); // Takes value from [-1 ; 1]
            // If direction was set first to reverse that means -1 will be 1 and 1 will be -1
            motorTwo.setVelocity(150); // Only for DcMotorEx class
            // this can be set to any value that means ex: 150 ticks per second
            // this is more accurate than the set power but it s not necesary
            // gamepad1 - Main driver
            // gamepad2 - Second driver
            pressed = gamepad1.triangle; // pressed will be set to value true when that button is
            // pressed and false when it s not pressed
            if (gamepad1.triangle) {
                motorOne.setPower(0.5);
            }// If on main driver controller the triangle button is presseed than you will do the
            // action set above
            // gamepad1.left_trigger - it s a float number so it his value depends on how
            // hard you pressed it

        }
    }
}
