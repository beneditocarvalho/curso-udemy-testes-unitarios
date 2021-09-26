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

	@Test(expected = Exception.class)
	public void testeLocacao_filmeSemEstoque() throws Exception {
		// cenario
		LocacaoService teste = new LocacaoService();
		Usuario usuario = new Usuario("José");
		Filme filme = new Filme("A volta dos que não foram", 0, 20.0);

		// acao

		Locacao locacao = teste.alugarFilme(usuario, filme);
	}

	@Test
	public void testeLocacao_filmeSemEstoque_2() {
		// cenario
		LocacaoService teste = new LocacaoService();
		Usuario usuario = new Usuario("Carvalho");
		Filme filme = new Filme("A volta dos que não foram", 0, 20.0);

		// acao

		try {
			Locacao locacao = teste.alugarFilme(usuario, filme);
			Assert.fail("Deveria ter lancado uma excecao");
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertThat(e.getMessage(), is("Filme sem estoque"));
		}
	}

	@Test
	public void testeLocacao_filmeSemEstoque_3() throws Exception {
		// cenario
		LocacaoService teste = new LocacaoService();
		Usuario usuario = new Usuario("José");
		Filme filme = new Filme("A volta dos que não foram", 0, 20.0);

		exception.expect(Exception.class);
		exception.expectMessage("Filme sem estoque");

		// acao
		Locacao locacao = teste.alugarFilme(usuario, filme);

	}
}