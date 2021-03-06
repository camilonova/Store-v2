package com.nova.customer.log;

import javax.swing.JOptionPane;

import com.nova.look.modules.Modules;
import com.nova.shared.command.Command;
import com.nova.shared.table.StoreRemoveRow;

/**
 * Esta clase funciona como puente entre los clientes de los comandos y los
 * comandos.
 * 
 * @author Camilo Nova
 * @version 1.0 Funcionalidad Basica
 */
public class CustomerCommand extends Command {

	/**
	 * Constructor. Recibe la instancia del modulo propietario
	 * 
	 * @param own
	 *            Modulo propietario
	 * @since 1.0
	 */
	public CustomerCommand(Modules own) {
		super(own);
	}

	@Override
	public void add() {
		new CustomerDataInput(owner).showAddDialog();
	}

	@Override
	public void edit() {
		if (owner.getTableSelectedID() == null)
			JOptionPane.showMessageDialog(null,
					"Debe seleccionar un registro!!!", "Error",
					JOptionPane.WARNING_MESSAGE);
		else
			new CustomerDataInput(owner).showUpdateDialog();
	}

	@Override
	public void remove() {
		new StoreRemoveRow(owner);
	}

	@Override
	public void see() {
		// Sin Implementacion
	}
}
