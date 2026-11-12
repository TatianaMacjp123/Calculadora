package com.example.calculadora;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;

import java.math.BigDecimal;
import java.math.MathContext;

public class MainActivity extends AppCompatActivity {

    private EditText numeroUno;
    private EditText numeroDos;
    private TextView resultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.enableEdgeToEdge(getWindow());
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (vista, insets) -> {
            Insets barras = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            vista.setPadding(barras.left, barras.top, barras.right, barras.bottom);
            return insets;
        });

        numeroUno = findViewById(R.id.numeroUno);
        numeroDos = findViewById(R.id.numeroDos);
        resultado = findViewById(R.id.resultado);

        findViewById(R.id.btnSumar).setOnClickListener(vista -> calcular('+'));
        findViewById(R.id.btnRestar).setOnClickListener(vista -> calcular('-'));
        findViewById(R.id.btnMultiplicar).setOnClickListener(vista -> calcular('*'));
        findViewById(R.id.btnDividir).setOnClickListener(vista -> calcular('/'));
    }

    private void calcular(char operacion) {
        resultado.setText("Resultado");

        BigDecimal primero = leerNumero(numeroUno);
        if (primero == null) {
            return;
        }

        BigDecimal segundo = leerNumero(numeroDos);
        if (segundo == null) {
            return;
        }

        BigDecimal valor;
        switch (operacion) {
            case '+':
                valor = primero.add(segundo);
                break;
            case '-':
                valor = primero.subtract(segundo);
                break;
            case '*':
                valor = primero.multiply(segundo);
                break;
            case '/':
                if (segundo.signum() == 0) {
                    resultado.setText("No se puede dividir entre cero.");
                    return;
                }
                valor = primero.divide(segundo, MathContext.DECIMAL64);
                break;
            default:
                return;
        }

        String texto = valor.stripTrailingZeros().toPlainString().replace('.', ',');
        resultado.setText("Resultado: " + texto);
    }

    private BigDecimal leerNumero(EditText campo) {
        String texto = campo.getText().toString().trim().replace(',', '.');
        try {
            return new BigDecimal(texto);
        } catch (NumberFormatException error) {
            campo.setError("Escribe un número válido");
            campo.requestFocus();
            return null;
        }
    }
}
