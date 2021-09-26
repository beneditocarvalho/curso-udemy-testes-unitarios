package br.ce.wcaquino.servicos;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.servicos.LocacaoService;
import br.ce.wcaquino.utils.DataUtils;

public class LocacaoServiceTest {
	
	@Test
	public void teste() {
		
		//cenario
		LocacaoService teste = new LocacaoService();
		 
		Usuario usuario = new Usuario("Benedito");
		Filme filme = new Filme("A volta dos que não foram", 3, 20.0);
		
		//acao
		Locacao locacao = teste.alugarFilme(usuario, filme);
		
		//verificacao
		Assert.assertEquals(20.0, locacao.getValor(), 0.01);
		Assert.assertTrue(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()));
		Assert.assertTrue(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)));
	}
}