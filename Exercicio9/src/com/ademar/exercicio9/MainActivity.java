package com.ademar.exercicio9;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends Activity {

	Spinner codigos, spnNomes;
	EditText edtQtd;
	Toast aviso;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		edtQtd = (EditText) findViewById(R.id.qtd);

		codigos = (Spinner) findViewById(R.id.codigos_spinner);
		ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter
				.createFromResource(this, R.array.codigos_array,
						android.R.layout.simple_spinner_item);
		arrayAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		codigos.setAdapter(arrayAdapter);

		aviso = new Toast(getApplicationContext());
	}

	public void calcular(View v) {
		try {

			Resources res = getResources();
			String[] nomes, sPrecos;
			List<Double> precos = new ArrayList<Double>();
			nomes = res.getStringArray(R.array.nomes_array);
			sPrecos = res.getStringArray(R.array.precos_array);
			for (String s : sPrecos)
				precos.add(Double.parseDouble(s));

			int qtd, sel;
			qtd = Integer.parseInt(edtQtd.getText().toString());

			sel = codigos.getSelectedItemPosition();

			AlertDialog.Builder dialogo = new AlertDialog.Builder(
					MainActivity.this);
			dialogo.setTitle(nomes[sel]);
			dialogo.setMessage(String.format(Locale.getDefault(), "%s: $%.2f",
					getString(R.string.total), precos.get(sel) * qtd));
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
		codigos.setSelection(0);
		edtQtd.setText("");
	}

}
