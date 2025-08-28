package com.example.practicasplashscreen;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
public class MainActivity2 extends AppCompatActivity {

    Button btnJugar;  // Botón para iniciar el juego
    EditText etNombre1, etNombre2;  // Campos de texto para ingresar los nombres de los jugadores

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);  // Establece el layout de esta actividad

        // Asocia los elementos de la interfaz con las variables en el código
        btnJugar = findViewById(R.id.btnJugar);  // Botón "Jugar"
        etNombre1 = findViewById(R.id.etNombre1);  // Campo de texto para el nombre del jugador 1
        etNombre2 = findViewById(R.id.etNombre2);  // Campo de texto para el nombre del jugador 2

        // Establecer un listener para el botón "Jugar"
        btnJugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Obtener los textos ingresados y quitar los espacios al principio y final
                String nombre1 = etNombre1.getText().toString().trim();
                String nombre2 = etNombre2.getText().toString().trim();

                // Verificar si alguno de los campos está vacío
                if (nombre1.isEmpty() || nombre2.isEmpty()) {
                    // Si algún campo está vacío, mostrar un mensaje de error con un AlertDialog
                    new AlertDialog.Builder(MainActivity2.this)
                            .setTitle("Error")  // Título del diálogo de error
                            .setMessage("Debe poner nombre en ambos campos.")  // Mensaje de error
                            .setPositiveButton("OK", null)  // Botón "OK" para cerrar el diálogo
                            .setCancelable(false)  // El diálogo no puede cerrarse tocando fuera de él
                            .show();  // Muestra el diálogo
                } else {
                    // Si ambos nombres están completos, crear una Intent para ir a MainActivity3
                    Intent intent = new Intent(MainActivity2.this, MainActivity3.class);
                    // Enviar los nombres de los jugadores a la siguiente actividad como extras
                    intent.putExtra("nombre1", nombre1);
                    intent.putExtra("nombre2", nombre2);
                    // Iniciar la actividad MainActivity3 con los datos enviados
                    startActivity(intent);
                }
            }
        });
    }
}

