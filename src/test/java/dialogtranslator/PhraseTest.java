package dialogtranslator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.drxgb.dialogtranslator.model.Language;
import com.drxgb.dialogtranslator.model.Phrase;
import com.drxgb.dialogtranslator.model.PhraseText;

/**
 * Responsável por fazer testes de criaçao de frases.
 * 
 * @author Dr.XGB
 * @version 1.0.0
 */
class PhraseTest
{
	/**
	 * Testa se pode criar um texto por idioma.
	 */
	@Test
	void canCreateTextPerLanguage()
	{
		final String EN_HELLO = "Hello!";
		final String PT_HELLO = "Olá!";
		
		Language en = new Language("English", true);
		Language pt = new Language("Português");
		Phrase phrase = new Phrase("Hello");
		
		List<PhraseText> texts = new ArrayList<>();
		
		texts.add(new PhraseText(EN_HELLO, phrase, en));
		texts.add(new PhraseText(PT_HELLO, phrase, pt));
		
		PhraseText hello = texts.stream()
				.filter(p -> p.getPhrase() == phrase && p.getLanguage() == en)
				.findFirst()
				.get();
		
		assertEquals(EN_HELLO, hello.getText());
	}

}
