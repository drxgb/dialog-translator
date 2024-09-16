package com.drxgb.dialogtranslator.util;

import java.util.Optional;

import com.drxgb.dialogtranslator.App;
import com.drxgb.dialogtranslator.service.StyleManager;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

/**
 * Utilitário para formar alertas.
 * 
 * @author Dr.XGB
 * @version 1.0.0
 */
public abstract class Alerts
{
	/*
	 * ===========================================================
	 * 			*** MÉTODOS PÚBLICOS ESTÁTICOS ***
	 * ===========================================================
	 */
	
	/**
	 * Cria um alerta de confirmação de remoção de um modelo e
	 * entrega o resultado da decisão tomada pelo usuário.
	 * 
	 * @param context Tipo do conteúdo a ser removido.
	 * @param name Nome do conteúdo a ser removido.
	 * @return O resultado da descisão do usuário.
	 */
	public static Optional<ButtonType> deletionAlertResult(String context, String name)
	{
		final App app = App.getInstance();
		
		Alert alert = new Alert(
				AlertType.WARNING,
				"This action is irreversible.",
				ButtonType.YES, ButtonType.NO
		);
		DialogPane dialog = alert.getDialogPane();
		DialogStyleDecorator decorator = new DialogStyleDecorator(dialog);
		StringBuilder title = new StringBuilder();
		StringBuilder header = new StringBuilder();
		Stage mainStage = app.getStage();
		StyleManager styleManager = app.getStyleManager();
		
		title.append("Remove ")
			.append(context)
			.append(" - ")
			.append(name);

		header.append("Are you sure you want remove \"")
			.append(name)
			.append("\"?");
		
		alert.setTitle(title.toString());
		alert.setHeaderText(header.toString());
		
		decorator.applyIcons(mainStage.getIcons());
		decorator.applyStyleSheets(styleManager.getObservedStyleList());
		decorator.applyButtonStyleClass("btn-primary");
		
		return alert.showAndWait();
	}
}
