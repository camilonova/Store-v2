package com.nova.shared.table;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import com.nova.core.Core;
import com.nova.look.modules.Modules;

/**
 * Esta clase elimina un registro de la tabla.
 * 
 * @author Camilo Nova
 * @version 1.0 Funcionalidad Basica
 */
public class StoreRemoveRow extends JDialog {

	/**
	 * Representa el ID del elemento a eliminar
	 */
	private String ID;

	/**
	 * Muestra un dialogo de confirmacion de la eliminacion y posteriormente
	 * elimina el usuario y actualiza la tabla
	 * 
	 * @param owner
	 *            Modulo propietario
	 */
	public StoreRemoveRow(Modules owner) {
		ID = owner.getTableSelectedID();

		if (ID == null)
			JOptionPane.showMessageDialog(null,
					"Debe seleccionar un registro!!!", "Error",
					JOptionPane.WARNING_MESSAGE);
		else {
			int seleccion = JOptionPane.showConfirmDialog(null,
					"Esta seguro de eliminar el registro " + ID, "Eliminar...",
					JOptionPane.YES_NO_OPTION);
			if (seleccion == JOptionPane.YES_OPTION) {
				String colName = Core.getColumnNames(owner.getIdentifier())
						.get(0);
				Core.removeRow(owner.getIdentifier(), colName, ID);
				owner.updateDataTable("Registro " + ID + " Eliminado!!!");
			}
		}
	}
}
