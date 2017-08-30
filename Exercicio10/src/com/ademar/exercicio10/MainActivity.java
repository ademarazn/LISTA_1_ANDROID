package com.ademar.exercicio10;

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

	EditText edtOperacao, edtNumero1, edtNumero2;
	Toast aviso;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		edtOperacao = (EditText) findViewById(R.id.operacao);
		edtNumero1 = (EditText) findViewById(R.id.numero1);
		edtNumero2 = (EditText) findViewById(R.id.numero2);
		aviso = new Toast(getApplicationContext());
	}

	public void calcular(View v) {
		try {
			Integer op;
			double res, num1, num2;
			String oper;

			op = Integer.parseInt(edtOperacao.getText().toString());
			num1 = Double.parseDouble(edtNumero1.getText().toString());
			num2 = Double.parseDouble(edtNumero2.getText().toString());

			switch (op) {
			case 0:
				oper = getString(R.string.soma);
				res = num1 + num2;
				break;
			case 1:
				oper = getString(R.string.subtracao);
				res = num1 - num2;
				break;
			case 2:
				oper = getString(R.string.multiplicacao);
				res = num1 * num2;
				break;
			case 3:
				oper = getString(R.string.divisao);
				res = num1 / num2;
				break;
			case 4:
				oper = getString(R.string.media);
				res = (num1 + num2) / 2;
				break;
			default:
				new AlertDialog.Builder(MainActivity.this)
						.setTitle(getString(R.string.aviso))
						.setMessage(getString(R.string.valor_errado))
						.setNegativeButton("OK", null).show();
				return;
			}

			AlertDialog.Builder dialogo = new AlertDialog.Builder(
					MainActivity.this);
			dialogo.setTitle(oper);
			dialogo.setMessage(String.format(Locale.getDefault(), "%.2f", res));
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
		edtOperacao.setText("");
		edtNumero1.setText("");
		edtNumero2.setText("");
		edtOperacao.requestFocus();
	}

}
