package br.ce.wcaquino.servicos;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.exceptions.LocadoraException;

@RunWith(Parameterized.class)
public class CalculoValorLocacaoTest {

	private LocacaoService teste;
	
	@Parameter
	public List<Filme> filmes;
	
	@Parameter(value = 1)
	public Double valorLocacao;
	
	@Parameter(value = 2)
	public String name;

	@Before
	public void setup() {
		teste = new LocacaoService();
	}

	private static Filme filme1 = new Filme("Filme 1", 1, 4.00);
	private static Filme filme2 = new Filme("Filme 2", 1, 4.00);
	private static Filme filme3 = new Filme("Filme 3", 1, 4.00);
	private static Filme filme4 = new Filme("Filme 4", 1, 4.00);
	private static Filme filme5 = new Filme("Filme 5", 1, 4.00);
	private static Filme filme6 = new Filme("Filme 5", 1, 4.00);

	@Parameters (name = "{2}")
	public static Collection<Object[]> getParametros() {
		return Arrays.asList(new Object [][] {
			{Arrays.asList(filme1, filme2, filme3), 11.0, "3 Filmes: 25%"},
			{Arrays.asList(filme1, filme2, filme3, filme4), 13.0, "4 Filmes: 50%"},
			{Arrays.asList(filme1, filme2, filme3, filme4, filme5), 14.0, "5 Filmes: 75%"},
			{Arrays.asList(filme1, filme2, filme3, filme4, filme5, filme6), 14.0, "6 Filmes: 100%"}
			});
	}

	@Test
	public void devePagarNaLocacaoComDesconto() throws FilmeSemEstoqueException, LocadoraException {
		// cenario
		Usuario usuario = new Usuario("Usuario 1");

		// acao
		Locacao locacao = teste.alugarFilme(usuario, filmes);

		// verificacao
		assertThat(locacao.getValor(), is(valorLocacao));
	}

}