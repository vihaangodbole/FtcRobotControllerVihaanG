package org.firstinspires.ftc.teamcode.Mechanisms;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "Motor Toggle TeleOp", group = "Linear Opmode")
public class Onoff extends LinearOpMode {

    // 1. Declare the motor variable
    private DcMotor myMotor = null;

    // 2. Setup variables to track the toggle states
    private boolean motorState = false;
    private boolean lastXState = false;

    @Override
    public void runOpMode() throws InterruptedException {

        // 3. Initialize the motor from the hardware map
        // "test_motor" must EXACTLY match the name configured on your Driver Station app
        myMotor = hardwareMap.get(DcMotor.class, "test_motor");

        // Optional: Set motor to brake when power is 0 so it stops immediately
        myMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        telemetry.addData("Status", "Initialized. Press Play.");
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // Run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            // Check the current live state of the X button on gamepad 1
            boolean currentXState = gamepad1.x;

            // Detect rising edge: X is pressed down NOW, but wasn't on the previous loop
            if (currentXState && !lastXState) {
                motorState = !motorState; // Flipped: true becomes false, false becomes true
            }

            // Save the current state to use as "last state" for the next fast loop
            lastXState = currentXState;

            // Set motor power based on the evaluated state
            if (motorState) {
                myMotor.setPower(1.0); // Motor Turns ON at full speed
            } else {
                myMotor.setPower(0.0); // Motor Turns OFF
            }

            // Send feedback data back to the Driver Station phone screen
            telemetry.addData("Motor Active", motorState);
            telemetry.addData("Press X", "To Toggle On/Off");
            telemetry.update();
        }
    }
}

