package com.ademar.exercicio7;

import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	EditText edtNumero;
	Toast aviso;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		edtNumero = (EditText) findViewById(R.id.numero);
		aviso = new Toast(getApplicationContext());
	}

	public void calcular(View v) {
		try {
			long num = Long.parseLong(edtNumero.getText().toString());
			String res = "";

			for (int i = 1; i <= 10; i++) {
				if (i < 10) {
					res += String.format(Locale.getDefault(), "%d x %d = %d\n",
							i, num, i * num);
				} else {
					res += String.format(Locale.getDefault(), "%d x %d = %d",
							i, num, i * num);
				}
			}

			AlertDialog.Builder dialogo = new AlertDialog.Builder(
					MainActivity.this);
			dialogo.setTitle(getString(R.string.tabuada));
			dialogo.setMessage(res);
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
		edtNumero.setText("");
	}

}
