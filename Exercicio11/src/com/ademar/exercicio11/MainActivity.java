package com.ademar.exercicio11;

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

	Spinner planetas;
	EditText edtPeso;
	Toast aviso;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		edtPeso = (EditText) findViewById(R.id.peso);
		planetas = (Spinner) findViewById(R.id.planetas_spinner);
		aviso = new Toast(getApplicationContext());

		ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter
				.createFromResource(this, R.array.planetas_array,
						android.R.layout.simple_spinner_item);
		arrayAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		planetas.setAdapter(arrayAdapter);
	}

	public void calcular(View v) {
		try {
			int sel = planetas.getSelectedItemPosition();
			double pPlaneta, pTerra;
			pTerra = Double.parseDouble(edtPeso.getText().toString());

			switch (sel) {
			case 0:
				pPlaneta = pTerra / 10 * 0.37;
				break;
			case 1:
				pPlaneta = pTerra / 10 * 0.88;
				break;
			case 2:
				pPlaneta = pTerra / 10 * 0.38;
				break;
			case 3:
				pPlaneta = pTerra / 10 * 2.64;
				break;
			case 4:
				pPlaneta = pTerra / 10 * 1.15;
				break;
			case 5:
				pPlaneta = pTerra / 10 * 1.17;
				break;
			default:
				pPlaneta = 0;
				break;
			}

			AlertDialog.Builder dialogo = new AlertDialog.Builder(
					MainActivity.this);
			dialogo.setTitle(getString(R.string.aviso));
			dialogo.setMessage(String.format(Locale.getDefault(), "%.2f",
					pPlaneta));
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
		planetas.setSelection(0);
		edtPeso.setText("");
	}
}
