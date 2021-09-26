package br.ce.wcaquino.servicos;

import static org.hamcrest.CoreMatchers.is;

import java.util.Date;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.utils.DataUtils;

public class LocacaoServiceTest {

	@Test
	public void teste() {

		// cenario
		LocacaoService teste = new LocacaoService();

		Usuario usuario = new Usuario("Benedito");
		Filme filme = new Filme("A volta dos que não foram", 3, 20.0);

		// acao
		Locacao locacao = teste.alugarFilme(usuario, filme);

		// verificacao
		Assert.assertThat(locacao.getValor(), CoreMatchers.is(20.0));
		Assert.assertThat(locacao.getValor(), CoreMatchers.is(CoreMatchers.not(5.0)));
		Assert.assertThat(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()), CoreMatchers.is(true));
		Assert.assertThat(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)), is(true));
	}
}