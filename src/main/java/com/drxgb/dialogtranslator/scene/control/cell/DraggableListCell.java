package com.drxgb.dialogtranslator.scene.control.cell;

import java.io.Serializable;

import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;

/**
 * Representa uma célula de lista que pode ser arrastável pelo mouse.
 * 
 * @param <T> O tipo do conteúdo da célula.
 */
public abstract class DraggableListCell<T extends Serializable> extends ListCell<T>
{
	/*
	 * ===========================================================
	 * 			*** CONSTANTES ***
	 * ===========================================================
	 */
	
	private static final DataFormat DRAGGABLE_ITEM = new DataFormat("drag-item");
	
	
	/*
	 * ===========================================================
	 * 			*** CONSTRUTORES ***
	 * ===========================================================
	 */
	
	/**
	 * Cria a célula de item arrastável.
	 */
	@SuppressWarnings("unchecked")
	public DraggableListCell()
	{
		ListCell<T> cell = this;
		final String draggedClass = "dragged";
		
		// Iniciar detecção do mouse quando arrastado.
		setOnDragDetected(ev ->
		{
			if (getItem() == null)
			{
				return;
			}
			
			Dragboard dragboard = startDragAndDrop(TransferMode.MOVE);
			ClipboardContent content = new ClipboardContent();
			
			content.put(DRAGGABLE_ITEM, getItem());
			dragboard.setContent(content);
			
			ev.consume();
		});
		
		// Início da entrada ao item enquanto arrasta.
		setOnDragOver(ev ->
		{
			if (ev.getGestureSource() != cell && ev.getDragboard().hasContent(DRAGGABLE_ITEM))
			{
				ev.acceptTransferModes(TransferMode.MOVE);
			}
			
			ev.consume();
		});
		
		// Ação ao passar o mouse sobre o item enquanto arrasta.
		setOnDragEntered(ev ->
		{
			if (ev.getGestureSource() != cell && ev.getDragboard().hasContent(DRAGGABLE_ITEM))
			{
				getStyleClass().add(draggedClass);
			}
			
			ev.consume();
		});
		
		// Ação ao tirar o mouse sobre o item enquanto arrasta.
		setOnDragExited(ev ->
		{
			if (ev.getGestureSource() != cell && ev.getDragboard().hasContent(DRAGGABLE_ITEM))
			{
				getStyleClass().remove(draggedClass);
			}
			
			ev.consume();
		});
		
		// Ação ao soltar o item arrastado.
		setOnDragDropped(ev ->
		{
			if (getItem() == null)
			{
				return;
			}
			
			Dragboard dragboard = ev.getDragboard();
			boolean success = false;
			
			if (dragboard.hasContent(DRAGGABLE_ITEM))
			{
				ObservableList<T> items = getListView().getItems();
				T source = getItem();
				T target = (T) dragboard.getContent(DRAGGABLE_ITEM);

				final int sourceIndex = items.indexOf(source);
				final int targetIndex = items.indexOf(target);
				
				items.set(sourceIndex, target);
				items.set(targetIndex, source);
				getListView().getSelectionModel().select(sourceIndex);

				success = true;
			}
			
			ev.setDropCompleted(success);
			ev.consume();
		});
		
		// Finalizar ação de arrastar item.
		setOnDragDone(DragEvent::consume);
	}
}
