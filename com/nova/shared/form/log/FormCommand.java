package com.nova.shared.form.log;

import javax.swing.JOptionPane;

import com.nova.shared.form.gui.Form;
import com.nova.shared.form.gui.FormTablePanel;

/**
 * Ejecuta los comandos del formato de ventas
 * 
 * @author Camilo Nova
 * @version 1.0
 */
public class FormCommand {

	/**
	 * Formato de ventas
	 */
	private final Form dataInput;

	/**
	 * Panel de datos
	 */
	private final FormTablePanel dataTable;

	/**
	 * Construye un comando para el formato
	 * 
	 * @param dataInput
	 *            Formato de ventas
	 * @param dataTable
	 *            Panel de datos
	 * @since 1.0
	 */
	public FormCommand(Form dataInput, FormTablePanel dataTable) {
		this.dataInput = dataInput;
		this.dataTable = dataTable;
	}

	/**
	 * Registra los datos del formato
	 * 
	 * @since 1.0
	 */
	public void registerFormat() {
		FormDataManager manager = new FormDataManager(dataInput);
		if (manager.isOperationCompleted()) {
			dataInput.reload();
		}
	}

	/**
	 * Sube una posicion el elemento seleccionado en la tabla
	 * 
	 * @since 1.0
	 */
	public void upSelectedItem() {
		int selectedRow = dataTable.getSelectedRow();

		if (selectedRow != -1) {
			if (dataTable.getModel().moveRowUp(selectedRow))
				dataTable.setSelectedCell(selectedRow - 1, 0);
		} else {
			JOptionPane.showMessageDialog(dataInput,
					"Debe seleccionar una fila");
		}
	}

	/**
	 * Baja una posicion el elemento seleccionado en la tabla
	 * 
	 * @since 1.0
	 */
	public void downSelectedItem() {
		int selectedRow = dataTable.getSelectedRow();

		if (selectedRow != -1) {
			if (dataTable.getModel().moveRowDown(selectedRow))
				dataTable.setSelectedCell(selectedRow + 1, 0);
		} else {
			JOptionPane.showMessageDialog(dataInput,
					"Debe seleccionar una fila");
		}
	}

	/**
	 * Elimina la fila seleccionada de la tabla
	 * 
	 * @since 1.0
	 */
	public void removeSelectedRow() {
		if (JOptionPane.showConfirmDialog(dataInput,
				"Esta seguro de eliminar la fila seleccionada?") == JOptionPane.YES_OPTION) {
			int selectedRow = dataTable.getSelectedRow();

			if (selectedRow != -1) {
				dataTable.getModel().removeRow(selectedRow);
				dataInput.updateTotals();
			} else {
				JOptionPane.showMessageDialog(dataInput,
						"Debe seleccionar una fila");
			}
		}
	}

	/**
	 * Elimina todas las filas de la tabla
	 * 
	 * @since 1.0
	 */
	public void removeAllRows() {
		if (JOptionPane.showConfirmDialog(dataInput,
				"Esta seguro de eliminar todos los registros?") == JOptionPane.YES_OPTION) {
			dataTable.getModel().removeAllRows();
			dataInput.updateTotals();
		}
	}
}
