package com.nova.purchase.log;

import javax.swing.JOptionPane;

import com.nova.look.modules.Modules;
import com.nova.look.submodules.PurchaseFormatGUI;
import com.nova.shared.command.Command;

/**
 * Esta clase funciona como puente entre los clientes de los comandos y los
 * comandos.
 * 
 * @author Camilo Nova
 * @version 1.0 Funcionalidad Basica
 */
public class PurchaseCommand extends Command {

	/**
	 * Constructor de la clase
	 * 
	 * @param own
	 */
	public PurchaseCommand(Modules own) {
		super(own);
	}

	@Override
	public void add() {
		new PurchaseFormatGUI(owner.getMainFrame(), null);
		owner.updateDataTable(null);
	}

	@Override
	public void see() {
		if (owner.getTableSelectedID() == null)
			JOptionPane.showMessageDialog(null,
					"Debe seleccionar un registro!!!", "Error",
					JOptionPane.WARNING_MESSAGE);
		else
			new PurchaseFormatGUI(owner.getMainFrame(), owner
					.getTableSelectedID());
	}

	@Override
	public void edit() {
		// Sin Implementacion
	}

	@Override
	public void remove() {
		// Sin Implementacion
	}
}
