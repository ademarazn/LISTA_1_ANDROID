package com.ademar.exercicio12;

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

	EditText edtCargo, edtSalario;
	Toast aviso;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		edtCargo = (EditText) findViewById(R.id.cargo);
		edtSalario = (EditText) findViewById(R.id.salario);
		aviso = new Toast(getApplicationContext());

		edtSalario.setFilters(new InputFilter[] { new DecimalDigitsInputFilter(
				2) });
	}

	public void calcular(View v) {
		try {
			double salario, novoSalario;
			salario = Double.parseDouble(edtSalario.getText().toString());
			String cargo = edtCargo.getText().toString();

			if (cargo.equalsIgnoreCase(getString(R.string.tecnico))) {
				novoSalario = salario * 1.15;
			} else if (cargo.equalsIgnoreCase(getString(R.string.gerente))) {
				novoSalario = salario * 1.13;
			} else {
				novoSalario = salario * 1.11;
			}

			AlertDialog.Builder dialogo = new AlertDialog.Builder(
					MainActivity.this);
			dialogo.setTitle(getString(R.string.titulo));
			dialogo.setMessage(String.format(Locale.getDefault(),
					"%s: $%.2f\n%s: $%.2f", getString(R.string.salario),
					salario, getString(R.string.salario_novo), novoSalario));
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
		edtSalario.setText("");
		edtCargo.setText("");
		edtSalario.requestFocus();
	}

}
