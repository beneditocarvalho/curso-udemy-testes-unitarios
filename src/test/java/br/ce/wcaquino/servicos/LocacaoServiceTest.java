package br.ce.wcaquino.servicos;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.exceptions.LocadoraException;
import br.ce.wcaquino.utils.DataUtils;

public class LocacaoServiceTest {

	private LocacaoService teste;

	private static int count = 0;

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Rule
	public ErrorCollector error = new ErrorCollector();

	@Before
	public void setup() {
		System.out.println("Before");
		teste = new LocacaoService();
		count++;
		System.out.println(count);
	}

	@After
	public void tearDown() {
		System.out.println("After");
	}

	@BeforeClass
	public static void setupClass() {
		System.out.println("Before Class");
	}

	@AfterClass
	public static void tearDownClass() {
		System.out.println("After Class");
	}

	@Test
	public void deveAlugarFilmeComSucesso() throws Exception {

		// cenario
		Usuario usuario = new Usuario("Benedito");
		List<Filme> filmes = Arrays.asList(new Filme("A volta dos que não foram", 1, 20.0));

		System.out.println("Teste!");

		// acao

		Locacao locacao = teste.alugarFilme(usuario, filmes);

		// verificacao
		error.checkThat(locacao.getValor(), CoreMatchers.is(20.0));
		error.checkThat(locacao.getValor(), CoreMatchers.is(CoreMatchers.not(5.0)));
		error.checkThat(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()), CoreMatchers.is(true));
		error.checkThat(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)),
				is(true));
	}

	@Test(expected = FilmeSemEstoqueException.class) // teste onde apenas essa exceção ocorre
	public void naoDeveAlugarFilmeSemEstoque() throws Exception {
		// cenario
		Usuario usuario = new Usuario("José");
		List<Filme> filmes = Arrays.asList(new Filme("A volta dos que não foram", 0, 20.0));

		// acao

		Locacao locacao = teste.alugarFilme(usuario, filmes);
	}

	@Test // teste onde consegue verificar excecao e mensagem então código segue fluxo
	public void naoDeveAlugarFilmeSemUsuario() throws FilmeSemEstoqueException {
		// cenario
		List<Filme> filmes = Arrays.asList(new Filme("A volta dos que não foram", 1, 20.0));

		// acao

		try {
			Locacao locacao = teste.alugarFilme(null, filmes);
			Assert.fail();
		} catch (LocadoraException e) {
			Assert.assertThat(e.getMessage(), is("Usuario Vazio"));
		}

	}

	@Test // teste onde e definido qual excessao e qual mensagem deve chegar e encerra o
			// processo
	public void naoDeveAlugarFilmeSemFilme() throws FilmeSemEstoqueException, LocadoraException {
		// cenario
		Usuario usuario = new Usuario("José");

		exception.expect(LocadoraException.class);
		exception.expectMessage("Filme Vazio");

		// acao
		Locacao locacao = teste.alugarFilme(usuario, null);
	}

	@Test
	public void devePagar75PctNoFilme3() throws FilmeSemEstoqueException, LocadoraException {
		// cenario
		Usuario usuario = new Usuario("Usuario 1");
		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 2, 4.0), new Filme("Filme 2", 3, 4.0),
				new Filme("Filme 3", 5, 4.0));

		// acao
		Locacao locacao = teste.alugarFilme(usuario, filmes);

		// verificacao
		assertThat(locacao.getValor(), is(11.0));
	}

	@Test
	public void devePagar50PctNoFilme4() throws FilmeSemEstoqueException, LocadoraException {
		// cenario
		Usuario usuario = new Usuario("Usuario 1");
		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 2, 4.0), new Filme("Filme 2", 3, 4.0),
				new Filme("Filme 3", 5, 4.0), new Filme("Filme 3", 5, 4.0));

		// acao
		Locacao locacao = teste.alugarFilme(usuario, filmes);

		// verificacao
		assertThat(locacao.getValor(), is(13.0));
	}

	@Test
	public void devePagar25PctNoFilme5() throws FilmeSemEstoqueException, LocadoraException {
		// cenario
		Usuario usuario = new Usuario("Usuario 1");
		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 2, 4.0), new Filme("Filme 2", 3, 4.0),
				new Filme("Filme 3", 5, 4.0), new Filme("Filme 3", 5, 4.0), new Filme("Filme 3", 5, 4.0));

		// acao
		Locacao locacao = teste.alugarFilme(usuario, filmes);

		// verificacao
		assertThat(locacao.getValor(), is(14.0));
	}

	@Test
	public void devePagarNadaNoFilme6() throws FilmeSemEstoqueException, LocadoraException {
		// cenario
		Usuario usuario = new Usuario("Usuario 1");
		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 2, 4.0), new Filme("Filme 2", 3, 4.0),
				new Filme("Filme 3", 5, 4.0), new Filme("Filme 3", 5, 4.0), new Filme("Filme 3", 5, 4.0),
				new Filme("Filme 3", 5, 4.0));

		// acao
		Locacao locacao = teste.alugarFilme(usuario, filmes);

		// verificacao
		assertThat(locacao.getValor(), is(14.0));
	}
}