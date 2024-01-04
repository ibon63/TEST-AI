package eus.ehu.tta.matidatziapp.view.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.graphics.Color;
import android.widget.Button;
import androidx.gridlayout.widget.GridLayout;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import android.view.Gravity;
import android.widget.Toast;
import java.util.Collections;

import eus.ehu.tta.matidatziapp.R;

import java.util.Random;
import java.util.ArrayList;
import java.util.List;

public class Lectoescritura5Activity extends AppCompatActivity {

    Button btn5ToLecto4;
    Button btn5ToLecto6;
    Button btnStartGameL5;
    GridLayout gridLayoutL5;

    //Palabras para la sopa de letras
    String[] palabras = {"HOLA", "MUNDO", "JAVA", "ANDROID"};

    //Tamaño de la cuadrícula
    int size = 7;

    //Matriz de letras
    char[][] letras;

    //Lista de textviews para mostrar las letras
    List<TextView> textViews;

    //Lista de palabras encontradas
    List<String> encontradas;

    //Tiempo de inicio del juego
    long startTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lectoescritura5);

        btn5ToLecto4 = findViewById(R.id.btn5ToLecto4);
        btn5ToLecto6 = findViewById(R.id.btn5ToLecto6);
        btnStartGameL5 = findViewById(R.id.btnStartGameL5);
        gridLayoutL5 = findViewById(R.id.gridLayoutL5);

        btn5ToLecto4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toLecto4();
            }
        });

        btn5ToLecto6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toLecto6();
            }
        });

        btnStartGameL5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startGame();
            }
        });

    }

    private void toLecto4() {
        Intent intent = new Intent(this, Lectoescritura4Activity.class);
        startActivity(intent);
    }

    private void toLecto6() {
        Intent intent = new Intent(this, Lectoescritura6Activity.class);
        startActivity(intent);
    }

    //Método para iniciar el juego
    private void startGame() {
        //Inicializar la matriz de letras
        letras = new char[size][size];

        //Inicializar la lista de textviews
        textViews = new ArrayList<>();

        //Inicializar la lista de palabras encontradas
        encontradas = new ArrayList<>();

        //Generar la cuadrícula de letras
        generarCuadricula();

        //Mostrar la cuadrícula en la interfaz
        mostrarCuadricula();

        //Guardar el tiempo de inicio
        startTime = System.currentTimeMillis();
    }

    //Método para generar la cuadrícula de letras
    private void generarCuadricula() {
        //Crear un objeto random
        Random random = new Random();

        //Crear una matriz de booleanos para indicar las posiciones ocupadas
        boolean[][] ocupadas = new boolean[size][size];

        //Recorrer las palabras
        for (String palabra : palabras) {
            //Convertir la palabra a mayúsculas
            palabra = palabra.toUpperCase();

            //Crear una lista de direcciones posibles
            List<Integer> direcciones = new ArrayList<>();
            for (int i = 0; i < 8; i++) {
                direcciones.add(i);
            }

            //Barajar la lista de direcciones
            Collections.shuffle(direcciones);

            //Crear una variable para indicar si se ha colocado la palabra
            boolean colocada = false;

            //Recorrer la lista de direcciones hasta colocar la palabra o agotar las opciones
            for (int direccion : direcciones) {
                //Crear una variable para indicar si hay espacio suficiente
                boolean hayEspacio = true;

                //Crear una variable para indicar si hay interferencia de otras letras
                boolean hayInterferencia = false;

                //Calcular el incremento de fila y columna según la dirección
                int incFila = 0;
                int incColumna = 0;
                switch (direccion) {
                    case 0: //Arriba
                        incFila = -1;
                        break;
                    case 1: //Arriba derecha
                        incFila = -1;
                        incColumna = 1;
                        break;
                    case 2: //Derecha
                        incColumna = 1;
                        break;
                    case 3: //Abajo derecha
                        incFila = 1;
                        incColumna = 1;
                        break;
                    case 4: //Abajo
                        incFila = 1;
                        break;
                    case 5: //Abajo izquierda
                        incFila = 1;
                        incColumna = -1;
                        break;
                    case 6: //Izquierda
                        incColumna = -1;
                        break;
                    case 7: //Arriba izquierda
                        incFila = -1;
                        incColumna = -1;
                        break;
                }

                //Crear una lista de posiciones iniciales posibles
                List<Integer> posiciones = new ArrayList<>();
                for (int i = 0; i < size * size; i++) {
                    posiciones.add(i);
                }

                //Barajar la lista de posiciones
                Collections.shuffle(posiciones);

                //Recorrer la lista de posiciones hasta encontrar una válida o agotar las opciones
                for (int posicion : posiciones) {
                    //Calcular la fila y la columna de la posición inicial
                    int fila = posicion / size;
                    int columna = posicion % size;

                    //Comprobar si hay espacio suficiente para colocar la palabra en la dirección elegida
                    hayEspacio = true;
                    for (int i = 0; i < palabra.length(); i++) {
                        //Calcular la fila y la columna de la letra actual
                        int filaLetra = fila + i * incFila;
                        int columnaLetra = columna + i * incColumna;

                        //Comprobar si la fila y la columna están dentro de los límites de la cuadrícula
                        if (filaLetra < 0 || filaLetra >= size || columnaLetra < 0 || columnaLetra >= size) {
                            //No hay espacio suficiente
                            hayEspacio = false;
                            break;
                        }
                    }

                    //Si hay espacio suficiente, comprobar si hay interferencia de otras letras
                    if (hayEspacio) {
                        hayInterferencia = false;
                        for (int i = 0; i < palabra.length(); i++) {
                            //Calcular la fila y la columna de la letra actual
                            int filaLetra = fila + i * incFila;
                            int columnaLetra = columna + i * incColumna;

                            //Comprobar si la posición está ocupada por otra letra distinta a la actual
                            if (ocupadas[filaLetra][columnaLetra] && letras[filaLetra][columnaLetra] != palabra.charAt(i)) {
                                //Hay interferencia de otra letra
                                hayInterferencia = true;
                                break;
                            }
                        }
                    }

                    //Si hay espacio suficiente y no hay interferencia, colocar la palabra en la cuadrícula y actualizar la matriz de booleanos
                    if (hayEspacio && !hayInterferencia) {
                        for (int i = 0; i < palabra.length(); i++) {
                            //Calcular la fila y la columna de la letra actual
                            int filaLetra = fila + i * incFila;
                            int columnaLetra = columna + i * incColumna;

                            //Colocar la letra en la cuadrícula
                            letras[filaLetra][columnaLetra] = palabra.charAt(i);

                            //Marcar la posición como ocupada
                            ocupadas[filaLetra][columnaLetra] = true;
                        }

                        //Indicar que se ha colocado la palabra
                        colocada = true;

                        //Salir del bucle de posiciones
                        break;
                    }
                }

                //Si se ha colocado la palabra, salir del bucle de direcciones
                if (colocada) {
                    break;
                }
            }

            //Si no se ha podido colocar la palabra, mostrar un mensaje de error
            if (!colocada) {
                Toast.makeText(this, "No se ha podido colocar la palabra " + palabra, Toast.LENGTH_SHORT).show();
            }
        }

        //Rellenar el resto de la cuadrícula con letras aleatorias
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                //Si la posición está vacía, generar una letra aleatoria
                if (!ocupadas[i][j]) {
                    letras[i][j] = (char) ('A' + random.nextInt(26));
                }
            }
        }
    }


    //Método para mostrar la cuadrícula en la interfaz
    private void mostrarCuadricula() {
        //Limpiar el gridLayoutL5
        gridLayoutL5.removeAllViews();

        //Recorrer la matriz de letras
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                //Obtener la letra actual
                char letra = letras[i][j];

                //Crear un textview para mostrar la letra
                TextView textView = new TextView(this);
                textView.setText(String.valueOf(letra));
                textView.setTextSize(24);
                textView.setTextColor(Color.BLACK);
                textView.setGravity(Gravity.CENTER);

                //Asignar un listener al textview
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Llamar al método para seleccionar una letra
                        seleccionarLetra(textView);
                    }
                });

                //Añadir el textview a la lista de textviews
                textViews.add(textView);

                //Crear los parámetros para el textview
                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.width = 0;
                params.height = 0;
                params.columnSpec = GridLayout.spec(j, 1f);
                params.rowSpec = GridLayout.spec(i, 1f);
                params.setMargins(2, 2, 2, 2);

                //Añadir el textview al gridLayoutL5 con los parámetros
                gridLayoutL5.addView(textView, params);
            }
        }
    }

    //Método para seleccionar una letra
    private void seleccionarLetra(TextView textView) {
        //Obtener el índice del textview en la lista de textviews
        int index = textViews.indexOf(textView);

        //Comprobar si el textview está seleccionado o no
        if (textView.isSelected()) {
            //El textview está seleccionado, deseleccionarlo
            textView.setSelected(false);
            textView.setBackgroundColor(Color.WHITE);
        } else {
            //El textview no está seleccionado, seleccionarlo
            textView.setSelected(true);
            textView.setBackgroundColor(Color.YELLOW);
        }

        //Crear una variable para guardar la palabra formada por las letras seleccionadas
        String palabra = "";

        //Recorrer la lista de textviews y obtener las letras seleccionadas
        for (TextView tv : textViews) {
            if (tv.isSelected()) {
                palabra += tv.getText();
            }
        }

        //Comprobar si la palabra es una de las palabras buscadas
        if (palabra.length() > 0 && !encontradas.contains(palabra)) {
            for (String p : palabras) {
                if (p.equalsIgnoreCase(palabra)) {
                    //La palabra es correcta, añadirla a la lista de encontradas
                    encontradas.add(palabra);

                    //Cambiar el color de fondo de las letras seleccionadas a verde
                    for (TextView tv : textViews) {
                        if (tv.isSelected()) {
                            tv.setBackgroundColor(Color.GREEN);
                        }
                    }

                    //Mostrar un mensaje de felicitación
                    Toast.makeText(this, "¡Has encontrado la palabra " + palabra + "!", Toast.LENGTH_SHORT).show();

                    //Comprobar si se han encontrado todas las palabras
                    if (encontradas.size() == palabras.length) {
                        //Se han encontrado todas las palabras, mostrar el resultado del juego
                        mostrarResultado();
                    }

                    //Salir del bucle
                    break;
                }
            }
        }
    }

    //Método para mostrar el resultado del juego
    private void mostrarResultado() {
        //Calcular el tiempo empleado en el juego
        long endTime = System.currentTimeMillis();
        long time = endTime - startTime;

        //Crear un mensaje con el resultado
        String mensaje = "¡Enhorabuena! Has encontrado todas las palabras en " + time / 1000 + " segundos.";

        //Mostrar el mensaje en un toast
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
    }

    //Método para obtener el desplazamiento en fila según la dirección
    private int getDeltaFila(int direccion) {
        //Crear un array con los valores para cada dirección
        //Las direcciones son: 0 arriba, 1 arriba-derecha, 2 derecha, 3 abajo-derecha, 4 abajo, 5 abajo-izquierda, 6 izquierda, 7 arriba-izquierda
        int[] deltaFila = {-1, -1, 0, 1, 1, 1, 0, -1};

        //Devolver el valor correspondiente a la dirección
        return deltaFila[direccion];
    }

    //Método para obtener el desplazamiento en columna según la dirección
    private int getDeltaColumna(int direccion) {
        //Crear un array con los valores para cada dirección
        //Las direcciones son: 0 arriba, 1 arriba-derecha, 2 derecha, 3 abajo-derecha, 4 abajo, 5 abajo-izquierda, 6 izquierda, 7 arriba-izquierda
        int[] deltaColumna = {0, 1, 1, 1, 0, -1, -1, -1};

        //Devolver el valor correspondiente a la dirección
        return deltaColumna[direccion];
    }


}