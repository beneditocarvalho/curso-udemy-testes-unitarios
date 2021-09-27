package br.ce.wcaquino.servicos;

import static org.hamcrest.CoreMatchers.is;

import java.util.Date;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
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

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Rule
	public ErrorCollector error = new ErrorCollector();

	@Test
	public void testeLocacao_filmeEmEstoque() throws Exception {

		// cenario
		LocacaoService teste = new LocacaoService();
		Usuario usuario = new Usuario("Benedito");
		Filme filme = new Filme("A volta dos que não foram", 1, 20.0);

		// acao

		Locacao locacao = teste.alugarFilme(usuario, filme);

		// verificacao
		error.checkThat(locacao.getValor(), CoreMatchers.is(20.0));
		error.checkThat(locacao.getValor(), CoreMatchers.is(CoreMatchers.not(5.0)));
		error.checkThat(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()), CoreMatchers.is(true));
		error.checkThat(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)),
				is(true));
	}

	@Test(expected = FilmeSemEstoqueException.class) // teste onde apenas essa exceção ocorre
	public void testeLocacao_filmeSemEstoque() throws Exception {
		// cenario
		LocacaoService teste = new LocacaoService();
		Usuario usuario = new Usuario("José");
		Filme filme = new Filme("A volta dos que não foram", 0, 20.0);

		// acao

		Locacao locacao = teste.alugarFilme(usuario, filme);
	}

	@Test // teste onde consegue verificar excecao e mensagem então código segue fluxo
	public void testLocacao_usuarioVazio() throws FilmeSemEstoqueException {
		// cenario
		LocacaoService teste = new LocacaoService();
		Filme filme = new Filme("A volta dos que não foram", 1, 20.0);

		// acao

		try {
			Locacao locacao = teste.alugarFilme(null, filme);
			Assert.fail();
		} catch (LocadoraException e) {
			Assert.assertThat(e.getMessage(), is("Usuario Vazio"));
		}
		
		System.out.println("Operação continua após a exceção");

	}

	@Test // teste onde e definido qual excessao e qual mensagem deve chegar e encerra o processo
	public void testLocacao_filmeVazio() throws FilmeSemEstoqueException, LocadoraException {
		// cenario
		LocacaoService teste = new LocacaoService();
		Usuario usuario = new Usuario("José");

		exception.expect(LocadoraException.class);
		exception.expectMessage("Filme Vazio");
		
		// acao
		Locacao locacao = teste.alugarFilme(usuario, null);

	}

}