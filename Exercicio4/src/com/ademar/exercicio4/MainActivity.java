package com.ademar.exercicio4;

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

	EditText edtNumId, edtNota1, edtNota2, edtNota3, edtMe;
	Toast aviso;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		edtNumId = (EditText) findViewById(R.id.numId);
		edtNota1 = (EditText) findViewById(R.id.nota1);
		edtNota2 = (EditText) findViewById(R.id.nota2);
		edtNota3 = (EditText) findViewById(R.id.nota3);
		edtMe = (EditText) findViewById(R.id.me);
		aviso = new Toast(getApplicationContext());

		edtNota1.setFilters(new InputFilter[] { new DecimalDigitsInputFilter(3) });
		edtNota2.setFilters(new InputFilter[] { new DecimalDigitsInputFilter(3) });
		edtNota3.setFilters(new InputFilter[] { new DecimalDigitsInputFilter(3) });
		edtMe.setFilters(new InputFilter[] { new DecimalDigitsInputFilter(3) });
	}

	public void calcular(View v) {
		try {
			String numId = edtNumId.getText().toString();
			double nota1, nota2, nota3, me, ma;
			char conc;

			nota1 = Double.parseDouble(edtNota1.getText().toString());
			nota2 = Double.parseDouble(edtNota2.getText().toString());
			nota3 = Double.parseDouble(edtNota3.getText().toString());
			me = Double.parseDouble(edtMe.getText().toString());

			ma = (nota1 + (nota2 * 2) + (nota3 * 3) + me) / 7;

			if (ma >= 9.0) {
				conc = 'A';
			} else if (ma >= 7.5 && ma < 9.0) {
				conc = 'B';
			} else if (ma >= 6.0 && ma < 7.5) {
				conc = 'C';
			} else if (ma >= 4.0 && ma < 6.0) {
				conc = 'D';
			} else {
				conc = 'E';
			}

			String situacao = conc == 'A' || conc == 'B' || conc == 'C' ? getString(R.string.aprovado)
					: getString(R.string.reprovado);

			AlertDialog.Builder dialogo = new AlertDialog.Builder(
					MainActivity.this);
			dialogo.setTitle(getString(R.string.aviso_titulo));
			dialogo.setMessage(String.format(
					Locale.getDefault(),
					"%s: %s\n%s: %.3f\n%s: %.3f\n%s: %.3f\n%s: %.3f\n%s: %.3f\n%s: %c\n%s: %s",
					getString(R.string.numId), numId,
					getString(R.string.nota1), nota1,
					getString(R.string.nota2), nota2,
					getString(R.string.nota3), nota3,
					getString(R.string.me), me,
					getString(R.string.ma), ma,
					getString(R.string.conceito), conc,
					getString(R.string.situacao), situacao));
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
		edtNumId.setText("");
		edtNota1.setText("");
		edtNota2.setText("");
		edtNota3.setText("");
		edtMe.setText("");
		edtNumId.requestFocus();
	}

}
