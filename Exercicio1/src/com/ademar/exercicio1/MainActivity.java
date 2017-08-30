package com.ademar.exercicio1;

import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	EditText saldo;
	Toast aviso;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		saldo = (EditText) findViewById(R.id.saldo);
		aviso = new Toast(getApplicationContext());

		saldo.setFilters(new InputFilter[] { new DecimalDigitsInputFilter(2) });
	}

	public void calcular(View v) {
		try {
			double saldoMedio = Double.parseDouble(saldo.getText().toString());
			Double credito;
			String credit;

			if (saldoMedio >= 0 && saldoMedio <= 200) {
				credito = null;
			} else if (saldoMedio < 400) {
				credito = saldoMedio * 0.2;
			} else if (saldoMedio < 600) {
				credito = saldoMedio * 0.3;
			} else {
				credito = saldoMedio * 0.4;
			}

			credit = credito != null ? String.format(Locale.getDefault(),
					"$%.2f", credito) : "nenhum";
			AlertDialog.Builder dialogo = new AlertDialog.Builder(
					MainActivity.this);
			dialogo.setTitle(getString(R.string.aviso_titulo));
			dialogo.setMessage(String.format(Locale.getDefault(),
					"%s: $%.2f\n%s: %s", getString(R.string.saldo_medio),
					saldoMedio, getString(R.string.credito), credit));
			dialogo.setNegativeButton("OK", null);

			ocultarTeclado();

			dialogo.show();
		} catch (NumberFormatException e) {
			novoAvisoCentro(getString(R.string.aviso));
		} catch (Exception e) {
			novoAvisoCentro(e.getLocalizedMessage());
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
		saldo.setText("");
	}
}
