package br.ce.wcaquino.matchers;

import static java.util.Calendar.DAY_OF_MONTH;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import br.ce.wcaquino.utils.DataUtils;

public class DiaSemanaMatcher extends TypeSafeMatcher<Date> {

	private Integer diaSemana;

	public DiaSemanaMatcher(Integer diaSemana) {
		this.diaSemana = diaSemana;
	}

	public void describeTo(Description description) {
		Calendar data = Calendar.getInstance();
		data.set(Calendar.DAY_OF_WEEK, diaSemana);
		String dataExtenso = data.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, new Locale("pt", "BR"));
		description.appendText(dataExtenso);
	}
	
	public static Date adicionarDias(Date data, int dias) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(data);
		calendar.add(DAY_OF_MONTH, dias);
		return calendar.getTime();
	}
	
	public static Date obterDataComDiferencaDias(int dias) {
		return adicionarDias(new Date(), dias);
	}

	@Override
	protected boolean matchesSafely(Date data) {
		return DataUtils.verificarDiaSemana(data, diaSemana);
	}

}