package com.ademar.exercicio2;

import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends Activity {

	EditText quantidade;
	Spinner codigos;
	Toast aviso;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		codigos = (Spinner) findViewById(R.id.codigos_spinner);
		// Cria um ArrayAdapter usando o array de string e um layout de spinner padrão
		ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter
				.createFromResource(this, R.array.codigos_array,
						android.R.layout.simple_spinner_item);
		// Especifica o layout para ser usado quando a lista de escolhas aparecer
		arrayAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Aplica o arrayAdapter no spinner
		codigos.setAdapter(arrayAdapter);

		quantidade = (EditText) findViewById(R.id.qtd);

		aviso = new Toast(getApplicationContext());
	}

	public void calcular(View v) {
		try {
			double precoTotal;
			int cod, qtd;
			cod = Integer.parseInt(codigos.getSelectedItem().toString());
			qtd = Integer.parseInt(quantidade.getText().toString());

			switch (cod) {
			case 1001:
				precoTotal = 5.32 * qtd;
				break;
			case 1324:
				precoTotal = 6.45 * qtd;
				break;
			case 6548:
				precoTotal = 2.37 * qtd;
				break;
			case 1987:
				precoTotal = 5.32 * qtd;
				break;
			case 7623:
				precoTotal = 6.45 * qtd;
				break;
			default:
				return;
			}

			AlertDialog.Builder dialogo = new AlertDialog.Builder(
					MainActivity.this);
			dialogo.setTitle("Total");
			dialogo.setMessage(String.format(Locale.getDefault(), "$%.2f",
					precoTotal));
			dialogo.setNegativeButton("OK", null);

			ocultarTeclado();

			dialogo.show();
		} catch (Exception e) {
			novoAvisoCentro(getString(R.string.aviso));
		}
	}

	private void novoAvisoCentro(String msg) {
		aviso.cancel();
		aviso = Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT);
		aviso.setGravity(Gravity.CENTER, 0, 0);
		aviso.show();
	}

	@Override
	protected void onPause() {
		super.onPause();

		aviso.cancel();
		ocultarTeclado();
	}

	private void ocultarTeclado() {
		((InputMethodManager) getApplicationContext().getSystemService(
				Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(null, 0);
	}

	public void limpar(View v) {
		codigos.setSelection(0);
		quantidade.setText("");
	}
}
