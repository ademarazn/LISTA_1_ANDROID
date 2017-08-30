package com.ademar.exercicio8;

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

	EditText edtSalario, edtSalarioMinimo;
	Toast aviso;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		edtSalario = (EditText) findViewById(R.id.salario);
		edtSalarioMinimo = (EditText) findViewById(R.id.salarioMin);
		aviso = new Toast(getApplicationContext());

		edtSalario.setFilters(new InputFilter[] { new DecimalDigitsInputFilter(
				2) });
		edtSalarioMinimo
				.setFilters(new InputFilter[] { new DecimalDigitsInputFilter(2) });
	}

	public void calcular(View v) {
		try {
			double salario, salarioMin, qtd;
			salario = Double.parseDouble(edtSalario.getText().toString());
			salarioMin = Double.parseDouble(edtSalarioMinimo.getText()
					.toString());
			qtd = salario / salarioMin;

			AlertDialog.Builder dialogo = new AlertDialog.Builder(
					MainActivity.this);
			dialogo.setTitle(getString(R.string.qtd));
			dialogo.setMessage(String.format(Locale.getDefault(), "%.2f", qtd));
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
		edtSalarioMinimo.setText("");
		edtSalario.setText("");
		edtSalarioMinimo.requestFocus();
	}

}
