package com.ademar.exercicio6;

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

	EditText edtDistTotal, edtLitros;
	Toast aviso;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		edtDistTotal = (EditText) findViewById(R.id.distTotal);
		edtLitros = (EditText) findViewById(R.id.litros);
		aviso = new Toast(getApplicationContext());

		edtDistTotal
				.setFilters(new InputFilter[] { new DecimalDigitsInputFilter(1) });
		edtLitros
				.setFilters(new InputFilter[] { new DecimalDigitsInputFilter(3) });
	}

	public void calcular(View v) {
		try {
			double distTotal, litros, consumo;
			distTotal = Double.parseDouble(edtDistTotal.getText().toString());
			litros = Double.parseDouble(edtLitros.getText().toString());
			consumo = distTotal / litros;

			AlertDialog.Builder dialogo = new AlertDialog.Builder(
					MainActivity.this);
			dialogo.setTitle(getString(R.string.aviso));
			dialogo.setMessage(String.format(Locale.getDefault(),
					"%s: %.3f Km/L", getString(R.string.consumo), consumo));
			dialogo.setNegativeButton("OK", null);

			ocultarTeclado();

			dialogo.show();
		} catch (NumberFormatException e) {
			novoAvisoCentro(getString(R.string.aviso_texto));
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
		edtDistTotal.setText("");
		edtLitros.setText("");
		edtDistTotal.requestFocus();
	}

}
