package com.nova.stock.log;

import javax.swing.JOptionPane;

import com.nova.look.modules.Modules;
import com.nova.look.submodules.KardexGUI;
import com.nova.shared.command.Command;
import com.nova.stock.kardex.log.KardexDataManager;

/**
 * Esta clase funciona como puente entre los clientes de los comandos y los
 * comandos.
 * 
 * @author Camilo Nova
 * @version 1.0 Funcionalidad Basica
 * @version 1.1 Kardex
 */
public class StockCommand extends Command {

	/**
	 * Constructor. Recibe la instancia del modulo propietario
	 * 
	 * @param own
	 *            Modulo propietario
	 * @since 1.0
	 */
	public StockCommand(Modules own) {
		super(own);
	}

	/**
	 * Muestra el kardex del producto seleccionado
	 * 
	 * @since 1.1
	 */
	public void kardex() {
		if (owner.getTableSelectedID() == null)
			JOptionPane.showMessageDialog(null,
					"Debe seleccionar un registro!!!", "Error",
					JOptionPane.WARNING_MESSAGE);
		else
			new KardexGUI(owner.getMainFrame(), owner.getTableSelectedID());
		owner.updateDataTable(null);
	}

	/**
	 * Comprueba todos los registros del kardex
	 * 
	 * @since 1.1
	 */
	public void checkAllKardex() {
		if (new KardexDataManager().checkAllRecords())
			owner
					.updateDataTable("Kardex comprobado correctamente, borrados algunos registros inconsistentes");
		else
			owner
					.updateDataTable("Kardex comprobado correctamente, no se encontraron registros inconsistentes");
	}

	@Override
	public void add() {
		new StockDataInput(owner).showAddDialog();
	}

	@Override
	public void edit() {
		if (owner.getTableSelectedID() == null)
			JOptionPane.showMessageDialog(null,
					"Debe seleccionar un registro!!!", "Error",
					JOptionPane.WARNING_MESSAGE);
		else
			new StockDataInput(owner).showUpdateDialog();
	}

	@Override
	public void remove() {
		if (new KardexDataManager()
				.deleteAllRecords(owner.getTableSelectedID()))
			owner.updateDataTable("Registro " + owner.getTableSelectedID()
					+ " Eliminado");
		else
			owner
					.setStatusBarText("Ha ocurrido un error al eliminar el producto!!!");
	}

	@Override
	public void see() {
		// Sin Implementacion
	}
}
