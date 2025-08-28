package com.example.practicasplashscreen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this); // Esta línea está comentada, pero puede habilitar la interfaz de usuario a pantalla completa en dispositivos con pantallas grandes
        setContentView(R.layout.activity_main); // Se establece el layout para esta actividad, usando el archivo de recursos activity_main.xml

        // Usamos un Handler para ejecutar una tarea después de un retraso
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Crea una nueva Intent para navegar a MainActivity2 después de 4 segundos
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                startActivity(intent); // Inicia la nueva actividad (MainActivity2)
            }
        }, 4000); // 4000 milisegundos = 4 segundos de espera antes de cambiar de actividad
    }
}
