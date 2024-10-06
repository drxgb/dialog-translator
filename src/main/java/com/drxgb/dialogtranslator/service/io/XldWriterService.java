package com.drxgb.dialogtranslator.service.io;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import com.drxgb.dialogtranslator.model.Language;
import com.drxgb.dialogtranslator.model.Phrase;
import com.drxgb.dialogtranslator.model.PhraseGroup;
import com.drxgb.util.ByteContainer;

/**
 * Responsável por salvar arquivos XLD.
 * 
 * @author Dr.XGB
 * @version 1.0.0
 */
public class XldWriterService extends BinaryWriterService
{
	/*
	 * ===========================================================
	 * 			*** CONSTRUTORES ***
	 * ===========================================================
	 */

	public XldWriterService(List<Language> languages, List<PhraseGroup> groups)
	{
		super(languages, groups);
	}

	/*
	 * ===========================================================
	 * 			*** MÉTODOS IMPLEMENTADOS ***
	 * ===========================================================
	 */

	/**
	 * Salva o arquivo XLD.
	 * 
	 * @see com.drxgb.dialogtranslator.service.io.WriterService#write(java.io.OutputStream)
	 */
	@Override
	public void write(OutputStream output) throws IOException
	{
		List<Language> languages = getLanguages();
		List<PhraseGroup> groups = getGroups();
		Language master;
		byte[] languagesBytes;
		
		// Inicializar escrita
		initStream(output);
		
		master = languages.stream().filter(l -> l.isMaster()).findFirst().get();
		
		// Escrever dados do cabeçalho
		writeString("XLD");
		setCharset("UTF-16LE");
		languagesBytes = getBytesFromList(languages, l -> l.getName());
		
		writeInt(0x20);		
		writeInt(languages.size());
		writeInt(0x20 + languagesBytes.length);
		writeInt(groups.size());
		writeInt(languages.indexOf(master));
		skip(0x08);
		
		// Escrever idiomas
		writeBytes(languagesBytes);
		
		// Escrever grupos de frases
		writePhraseGroups();
	}
	
	
	/*
	 * ===========================================================
	 * 			*** MÉTODOS PRIVADOS ***
	 * ===========================================================
	 */
	
	/**
	 * Escreve os grupos de frase.
	 * 
	 * @throws IOException Quando não é possível escrever no arquivo.
	 */
	private void writePhraseGroups() throws IOException
	{
		List<PhraseGroup> groups = getGroups();
		byte[] groupBytes;
		
		for (PhraseGroup group : groups)
		{
			groupBytes = getPhraseGroupBytes(group);
			writeString(group.getName());
			writeInt(groupBytes.length);
			writeBytes(groupBytes);
		};
	}
	
	
	/**
	 * Recebe os bytes do bloco do grupo de frases.
	 * 
	 * @param group O grupo de frases.
	 * @return O bloco do grupo de frases.
	 */
	private byte[] getPhraseGroupBytes(PhraseGroup group)
	{
		List<Phrase> phrases = group.getPhrases();
		
		if (phrases.isEmpty())
		{
			return new byte[0];
		}

		ByteContainer container = new ByteContainer();
		byte[] keyBytes;
		byte[] phraseBytes;
		
		for (Phrase phrase : phrases)
		{
			keyBytes = getBytesFromString(phrase.getKey());
			phraseBytes = getPhraseBytes(phrase);
			
			addBytesToContainer(container, keyBytes);
			addBytesToContainer(container, getBytesFromInteger(phraseBytes.length));
			addBytesToContainer(container, phraseBytes);
		}
		
		return container.toByteArray();
	}
	
	
	/**
	 * Recebe os bytes do bloco da frase.
	 * 
	 * @param phrase A frase.
	 * @return O bloco da frase.
	 */
	private byte[] getPhraseBytes(Phrase phrase)
	{
		List<Language> languages = getLanguages();
		ByteContainer container = new ByteContainer();
		String text;
		
		for (Language language : languages)
		{
			text = phrase.getTexts().get(language);
			addBytesToContainer(container, getBytesFromString(text));
		}
		
		return container.toByteArray();
	}
}
