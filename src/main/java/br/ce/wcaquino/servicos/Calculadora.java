package br.ce.wcaquino.servicos;

import br.ce.wcaquino.exceptions.DividirPorZeroException;

public class Calculadora {

	public int somar(int a, int b) {
		return a + b;
	}

	public int subtrair(int a, int b) {
		return a - b;
	}

	public int divide(int a, int b) throws DividirPorZeroException {
		if(b == 0) {
			throw new DividirPorZeroException();
		}
		return a / b;
	}
	
}
