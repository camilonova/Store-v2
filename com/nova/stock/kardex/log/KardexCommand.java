package com.nova.stock.kardex.log;

import javax.swing.JOptionPane;

import com.nova.core.Core;
import com.nova.look.modules.Modules;
import com.nova.look.submodules.KardexGUI;
import com.nova.look.submodules.SubModules;
import com.nova.shared.command.Command;

/**
 * Esta clase funciona como puente entre los clientes de los comandos y los
 * comandos.
 * 
 * @author Camilo Nova
 * @version 1.0 Funcionalidad Basica
 * @version 1.1 Kardex
 */
public class KardexCommand extends Command {

	/**
	 * Constructor. Recibe la instancia del modulo propietario
	 * 
	 * @param own
	 *            Modulo propietario
	 * @since 1.0
	 */
	public KardexCommand(Modules own) {
		super(own);
	}

	/**
	 * Comprueba el producto pasado por parametro
	 * 
	 * @since 1.1
	 */
	public void checkKardexProduct() {
		if (new KardexDataManager().checkSingleRecord(((SubModules) owner)
				.getItemToShow())) {
			owner.updateDataTable(((SubModules) owner).getItemToShow());
			owner.setStatusBarText("Registro "
					+ ((SubModules) owner).getItemToShow()
					+ " Verificado Correctamente");
		}
	}

	/**
	 * Agrega un nuevo registro al kardex
	 */
	@Override
	public void add() {
		new KardexDataInput((KardexGUI) owner)
				.showAddDialog(((SubModules) owner).getItemToShow());
	}

	/**
	 * Edita el registro seleccionado del kardex
	 */
	@Override
	public void edit() {
		if (owner.getTableSelectedID() == null)
			JOptionPane.showMessageDialog(null,
					"Debe seleccionar un registro!!!", "Error",
					JOptionPane.WARNING_MESSAGE);
		else if (Core.getDataAt("kardex", "Proceso",
				owner.getTableSelectedID(), "Origen").equals("Automatico"))
			JOptionPane.showMessageDialog(null,
					"Solo puede editar registros ingresados manualmente!!!",
					"Error", JOptionPane.WARNING_MESSAGE);
		else {
			String data[][] = Core.getFilteredTableData("kardex", "Proceso",
					"DESC", "Codigo", ((SubModules) owner).getItemToShow());
			if (!data[0][Core.getColumnIndex("kardex", "Proceso")].equals(owner
					.getTableSelectedID())) {
				JOptionPane.showMessageDialog(null,
						"Solamente el ultimo registro es modificable!!!",
						"Error", JOptionPane.WARNING_MESSAGE);
			} else
				new KardexDataInput((KardexGUI) owner)
						.showUpdateDialog(((SubModules) owner).getItemToShow());
		}
	}

	@Override
	public void remove() {
		// Sin Implementacion
	}

	@Override
	public void see() {
		// Sin Implementacion
	}
}