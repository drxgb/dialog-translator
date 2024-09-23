package com.drxgb.dialogtranslator.util;

import java.util.Optional;

import com.drxgb.dialogtranslator.App;
import com.drxgb.dialogtranslator.service.manager.StyleManager;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
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
		
		Stage stage = app.getStage();
		StringBuilder title = new StringBuilder();
		StringBuilder header = new StringBuilder();
		Alert alert = new Alert(
				AlertType.WARNING,
				"This action is irreversible.",
				ButtonType.YES, ButtonType.NO
		);
		
		title.append("Remove ")
			.append(context)
			.append(" - ")
			.append(name);

		header.append("Are you sure you want remove \"")
			.append(name)
			.append("\"?");
		
		alert.initOwner(stage);
		alert.setTitle(title.toString());
		alert.setHeaderText(header.toString());
		decorateAlert(alert);
		
		return alert.showAndWait();
	}
	
	
	/**
	 * Mostra um alerta de erro.
	 * 
	 * @param t A exceção capturada.
	 * @param fatal A aplicação deve encerrar imediatamente.
	 */
	public static void showError(Throwable t, boolean fatal)
	{
		final App app = App.getInstance();
		
		Alert alert = new Alert(AlertType.ERROR);
		DialogPane pane = alert.getDialogPane();
		Stage mainStage = app.getStage();
		Stage dialogStage = (Stage) pane.getScene().getWindow();
		Image icon = new Image(App.class.getResourceAsStream("icon/error.png"));
		
		alert.initOwner(mainStage);
		alert.setTitle("Fatal error");
		alert.setHeaderText(null);
		alert.setContentText(t.getMessage());
		decorateAlert(alert);
		
		dialogStage.getIcons().clear();
		dialogStage.getIcons().add(icon);
		
		alert.showAndWait();
		
		if (fatal)
		{
			System.exit(1);
		}
	}
	
	
	/**
	 * Mostra um alerta de erro não fatal, ou seja,
	 * mesmo com o erro encontrado, a aplicação
	 * continuará em execução normalmente.
	 * 
	 * @param t A exceção capturada.
	 */
	public static void showError(Throwable t)
	{
		showError(t, false);
	}
	
	
	/**
	 * Decora o alerta com o estilo padrão.
	 * 
	 * @param alert O alerta a ser decorado.
	 */
	public static void decorateAlert(Alert alert)
	{
		final App app = App.getInstance();
		
		DialogPane dialog = alert.getDialogPane();
		DialogStyleDecorator decorator = new DialogStyleDecorator(dialog);
		Stage stage = app.getStage();
		StyleManager styleManager = app.getStyleManager();
		
		if (stage != null && styleManager != null)
		{
			decorator.applyIcons(stage.getIcons());
			decorator.applyStyleSheets(styleManager.getObservedStyleList());
			decorator.applyButtonStyleClass("btn-primary");
		}
	}
}
