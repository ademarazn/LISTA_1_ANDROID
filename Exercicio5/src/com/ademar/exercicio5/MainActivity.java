package com.ademar.exercicio5;

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

	EditText edtCodigo, edtSalBase, edtTempo;
	Toast aviso;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		edtCodigo = (EditText) findViewById(R.id.codigo);
		edtSalBase = (EditText) findViewById(R.id.salarioBase);
		edtTempo = (EditText) findViewById(R.id.tempo);
		aviso = new Toast(getApplicationContext());

		edtSalBase.setFilters(new InputFilter[] { new DecimalDigitsInputFilter(2) });
		edtTempo.setFilters(new InputFilter[] { new DecimalDigitsInputFilter(1) });
	}

	public void calcular(View v) {
		try {
			String codigo;
			char categ;
			double salBase, salLiq, imposto;
			float tempo;
			int grat;
			codigo = edtCodigo.getText().toString();
			salBase = Double.parseDouble(edtSalBase.getText().toString());
			tempo = Float.parseFloat(edtTempo.getText().toString());

			imposto = calcularImposto(salBase);
			grat = calcularGratificacao(salBase, tempo);
			salLiq = (salBase - imposto) + grat;
			categ = classificarFuncionario(salLiq);

			AlertDialog.Builder dialogo = new AlertDialog.Builder(
					MainActivity.this);
			dialogo.setTitle(getString(R.string.aviso));
			dialogo.setMessage(String.format(
					Locale.getDefault(),
					"%s: %s\n%s: $%.2f\n%s: %.1f %s\n%s: $%.2f\n%s: $%d\n%s: $%.2f\n%s: %c",
					getString(R.string.codigo), codigo,
					getString(R.string.salarioBase), salBase,
					getString(R.string.tempo), tempo, getString(R.string.anos),
					getString(R.string.imposto), imposto,
					getString(R.string.gratificacao), grat,
					getString(R.string.salarioLiq), salLiq,
					getString(R.string.categoria), categ));
			dialogo.setNegativeButton("OK", null);

			ocultarTeclado();

			dialogo.show();
		} catch (NumberFormatException e) {
			novoAvisoCentro(getString(R.string.aviso_texto));
		} catch (Exception e) {
			novoAvisoCentro(e.getLocalizedMessage());
		}
	}

	public double calcularImposto(double salario) {
		if (salario < 200.00) {
			return 0.0;
		} else if (salario <= 450.00) {
			return salario * 0.03;
		} else if (salario < 700.00) {
			return salario * 0.08;
		} else {
			return salario * 0.12;
		}
	}

	public int calcularGratificacao(double salario, float tempo) {
		if (salario > 500.00) {
			if (tempo <= 3) {
				return 20;
			} else {
				return 30;
			}
		} else {
			if (tempo <= 3) {
				return 23;
			} else if (tempo < 6) {
				return 35;
			} else {
				return 33;
			}
		}
	}

	public char classificarFuncionario(double salarioLiq) {
		if (salarioLiq <= 350.00) {
			return 'A';
		} else if (salarioLiq < 600.00) {
			return 'B';
		} else {
			return 'C';
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
		edtCodigo.setText("");
		edtSalBase.setText("");
		edtTempo.setText("");
		edtCodigo.requestFocus();
	}

}
