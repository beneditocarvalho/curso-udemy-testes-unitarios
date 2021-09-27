package br.ce.wcaquino.servicos;

import static org.hamcrest.CoreMatchers.is;

import java.util.Date;

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
	public void testeLocacao_filmeEmEstoque() throws Exception {

		// cenario
		teste = new LocacaoService();
		Usuario usuario = new Usuario("Benedito");
		Filme filme = new Filme("A volta dos que n�o foram", 1, 20.0);

		System.out.println("Teste!");

		// acao

		Locacao locacao = teste.alugarFilme(usuario, filme);

		// verificacao
		error.checkThat(locacao.getValor(), CoreMatchers.is(20.0));
		error.checkThat(locacao.getValor(), CoreMatchers.is(CoreMatchers.not(5.0)));
		error.checkThat(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()), CoreMatchers.is(true));
		error.checkThat(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)),
				is(true));
	}

	@Test(expected = FilmeSemEstoqueException.class) // teste onde apenas essa exce��o ocorre
	public void testeLocacao_filmeSemEstoque() throws Exception {
		// cenario
		teste = new LocacaoService();
		Usuario usuario = new Usuario("Jos�");
		Filme filme = new Filme("A volta dos que n�o foram", 0, 20.0);

		// acao

		Locacao locacao = teste.alugarFilme(usuario, filme);
	}

	@Test // teste onde consegue verificar excecao e mensagem ent�o c�digo segue fluxo
	public void testLocacao_usuarioVazio() throws FilmeSemEstoqueException {
		// cenario
		teste = new LocacaoService();
		Filme filme = new Filme("A volta dos que n�o foram", 1, 20.0);

		// acao

		try {
			Locacao locacao = teste.alugarFilme(null, filme);
			Assert.fail();
		} catch (LocadoraException e) {
			Assert.assertThat(e.getMessage(), is("Usuario Vazio"));
		}

	}

	@Test // teste onde e definido qual excessao e qual mensagem deve chegar e encerra o
			// processo
	public void testLocacao_filmeVazio() throws FilmeSemEstoqueException, LocadoraException {
		// cenario
		teste = new LocacaoService();
		Usuario usuario = new Usuario("Jos�");

		exception.expect(LocadoraException.class);
		exception.expectMessage("Filme Vazio");

		// acao
		Locacao locacao = teste.alugarFilme(usuario, null);

	}

}