package org.firstinspires.ftc.teamcode; // Declară pachetul (folderul logic) în care se află acest fișier

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode; // Clasa de bază pentru moduri de operare liniare (pas cu pas)
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;       // Adnotare care definește un OpMode de tip TeleOp (controlat manual)
import com.qualcomm.robotcore.hardware.Servo;                // Clasa pentru controlul unui servo

@TeleOp(name = "Control Servo cu Init Fix", group = "Test") // Numele OpMode-ului și grupul în care apare în Driver Station
public class ServoControlOpMode extends LinearOpMode {       // Începe clasa principală, care extinde LinearOpMode

    private Servo cleste;                  // Declara o variabilă pentru servo-ul care controlează cleștele
    private boolean esteDeschis = false;   // Variabil pentru a reține dacă cleștele e deschis sau închis

    // 👉 Inițializare fixă: poziții predefinite pentru servo
    private final double INIT_POSITION = 0.0;    // Poziția inițială (de regulă închis)
    private final double OPEN_POSITION = 0.2;    // Poziția în care cleștele este deschis
    private final double CLOSED_POSITION = 0.0;  // Poziția în care cleștele este închis

    @Override
    public void runOpMode() throws InterruptedException { // Metoda care se execută când rulează OpMode-ul

        cleste = hardwareMap.get(Servo.class, "cleste"); // Maparea servo-ului din configurarea robotului

        cleste.setPosition(INIT_POSITION); // Setează servo-ul în poziția inițială indiferent de poziția actuală
        esteDeschis = false;               // Starea implicită este "închis"

        telemetry.addLine("Servo inițializat la poziția centrală"); // Mesaj pe ecran pentru operator
        telemetry.addData("Poziție INIT:", INIT_POSITION);          // Afișează valoarea poziției inițiale
        telemetry.update(); // Actualizează ecranul Driver Station cu informațiile

        waitForStart(); // Așteaptă ca utilizatorul să apese "Start" în Driver Station

        while (opModeIsActive()) { // Buclă care rulează continuu cât timp opMode-ul este activ

            if (gamepad1.square) { // Dacă este apăsat butonul pătrat de pe gamepad1

                if (!esteDeschis) {                    // Dacă cleștele este închis
                    cleste.setPosition(OPEN_POSITION); // Deschide cleștele
                    esteDeschis = true;                // Actualizează starea
                } else {                               // Dacă cleștele este deja deschis
                    cleste.setPosition(CLOSED_POSITION); // Închide cleștele
                    esteDeschis = false;                 // Actualizează starea
                }

                sleep(300); // Pauză de 300 ms pentru debounce (evită activări duble de la o singură apăsare)
            }

            telemetry.addData("Poziție servo:", cleste.getPosition());        // Afișează poziția curentă a servo-ului
            telemetry.addData("Stare:", esteDeschis ? "Deschis" : "Închis"); // Afișează starea sub formă de text
            telemetry.update(); // Actualizează ecranul cu noile informații
        }
    }
}