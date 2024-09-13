package com.drxgb.dialogtranslator.service;

import java.io.OutputStream;
import java.util.List;

import com.drxgb.dialogtranslator.model.Language;
import com.drxgb.dialogtranslator.model.PhraseGroup;

/**
 * 
 */
public class XldWriterService extends WriterService
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
	 * @see com.drxgb.dialogtranslator.service.WriterService#write(java.io.OutputStream)
	 */
	@Override
	public void write(OutputStream input)
	{
		// TODO Salvar arquivo XLD
	}
}
