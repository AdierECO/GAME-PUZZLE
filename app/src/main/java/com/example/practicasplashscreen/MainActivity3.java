package com.example.practicasplashscreen;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
public class MainActivity3 extends AppCompatActivity {

    // Declaración de variables para las cartas, imágenes y demás componentes de la interfaz
    ImageView[] cartas = new ImageView[12];  // Array de 12 cartas (Imágenes)
    int[] imagenes = { R.drawable.img, R.drawable.img_1, R.drawable.img_2, R.drawable.img_3,
            R.drawable.img_4, R.drawable.img_5 };

    boolean[] volteadas = new boolean[12];  // Array para llevar control de las cartas volteadas
    TextView tvj1, tvj2;  // TextViews para mostrar los nombres de los jugadores
    RadioButton rbJ1, rbJ2;  // RadioButtons para indicar el turno de los jugadores
    Button btndatos;  // Botón para volver a la pantalla de inicio
    int turno = 1;  // Variable para controlar el turno (1 para jugador 1, 2 para jugador 2)
    int puntajeJ1 = 0, puntajeJ2 = 0;  // Variables para los puntajes de los jugadores
    int ultimaCarta = -1;  // Variable para almacenar el índice de la última carta volteada
    int cartaActual = -1;  // Variable para almacenar el índice de la carta actual
    int parejasEncontradas = 0;  // Contador de las parejas encontradas
    Handler handler = new Handler();  // Handler para retrasos (cuando las cartas se voltean)
    boolean cartasAbiertas = false; // Variable para verificar si hay cartas abiertas

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);  // Asocia el layout de la actividad

        // Inicializa las cartas y ajusta su tamaño para que ocupen todo el espacio disponible
        for (int i = 0; i < 12; i++) {
            int resID = getResources().getIdentifier("img" + (i + 1), "id", getPackageName());
            // Obtiene los recursos de las cartas
            cartas[i] = findViewById(resID);  // Asocia la carta a la variable correspondiente

            // Ajusta el tamaño de las cartas programáticamente
            ViewGroup.LayoutParams params = cartas[i].getLayoutParams();
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            params.height = ViewGroup.LayoutParams.MATCH_PARENT;
            cartas[i].setLayoutParams(params);

            final int index = i;
            // Establece un listener para cada carta que voltea la carta cuando se hace clic
            cartas[i].setOnClickListener(view -> voltearCarta(index));
        }

        // Inicializa las vistas y componentes de la interfaz
        tvj1 = findViewById(R.id.tvNombre1);
        tvj2 = findViewById(R.id.tvNombre2);
        rbJ1 = findViewById(R.id.rb1);
        rbJ2 = findViewById(R.id.rb2);
        btndatos = findViewById(R.id.btnDatos);

        // Se asegura de que el jugador 1 empiece
        rbJ1.setChecked(true);
        rbJ2.setChecked(false);
        btndatos.setEnabled(false);  // Deshabilita el botón para ir a la pantalla anterior

        // Establece el listener para el botón de datos para volver a MainActivity2
        btndatos.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity3.this, MainActivity2.class);
            startActivity(intent);
        });

        // Obtiene los nombres de los jugadores desde la actividad anterior (MainActivity2)
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            tvj1.setText(bundle.getString("nombre1"));
            tvj2.setText(bundle.getString("nombre2"));
        }
    }

    // Método para voltear una carta
    private void voltearCarta(int index) {
        // Si hay dos cartas abiertas, no se puede voltear otra
        if (cartasAbiertas) return;

        // Si la carta ya está volteada, no hacer nada
        if (volteadas[index]) return;

        // Asocia la imagen de la carta con el índice
        int imagenId = imagenes[index % imagenes.length];
        cartas[index].setImageResource(imagenId);
        volteadas[index] = true;  // Marca la carta como volteada

        // Si es la primera carta, guarda su índice
        if (ultimaCarta == -1) {
            ultimaCarta = index;
        } else {
            // Si ya hay una carta volteada, marca la carta actual y verifica si son iguales
            cartaActual = index;
            cartasAbiertas = true;  // Marca que las cartas están abiertas
            handler.postDelayed(this::verificarPareja, 1000);  // Verifica la pareja después de un segundo
        }
    }

    // Método para verificar si las dos cartas son una pareja
    private void verificarPareja() {
        if (imagenes[ultimaCarta % imagenes.length] == imagenes[cartaActual % imagenes.length]) {
            // Si las cartas son iguales, sumamos un punto al jugador correspondiente
            if (turno == 1) puntajeJ1++;
            else puntajeJ2++;

            parejasEncontradas++;
            // Si todas las parejas han sido encontradas, muestra al ganador
            if (parejasEncontradas == 6) {
                mostrarGanador();
            }
        } else {
            // Si las cartas no son iguales, las volteamos nuevamente
            cartas[ultimaCarta].setImageResource(R.drawable.logo2);
            cartas[cartaActual].setImageResource(R.drawable.logo2);
            volteadas[ultimaCarta] = false;
            volteadas[cartaActual] = false;
            cambiarTurno();  // Cambia el turno
        }

        // Resetea las cartas abiertas y los índices de las cartas
        ultimaCarta = -1;
        cartaActual = -1;
        cartasAbiertas = false;
    }

    // Método para cambiar el turno entre los jugadores
    private void cambiarTurno() {
        turno = (turno == 1) ? 2 : 1;  // Cambia el turno entre los jugadores
        rbJ1.setChecked(turno == 1);  // Marca al jugador 1 como activo si es su turno
        rbJ2.setChecked(turno == 2);  // Marca al jugador 2 como activo si es su turno
    }

    // Método para mostrar el ganador al final del juego
    private void mostrarGanador() {
        String mensaje;
        if (puntajeJ1 > puntajeJ2) {
            mensaje = "¡Ganó " + tvj1.getText().toString() + " con " + puntajeJ1 + " puntos!";
        } else if (puntajeJ2 > puntajeJ1) {
            mensaje = "¡Ganó " + tvj2.getText().toString() + " con " + puntajeJ2 + " puntos!";
        } else {
            mensaje = "¡Empate! Ambos jugadores tienen " + puntajeJ1 + " puntos.";
        }

        btndatos.setEnabled(true);  // Habilita el botón de datos para ir a la pantalla de inicio

        // Muestra un diálogo con el resultado del juego
        new AlertDialog.Builder(this)
                .setTitle("Fin del Juego")
                .setMessage(mensaje)
                .setPositiveButton("OK", null)
                .setCancelable(false)
                .show();
    }
}


