package com.nova.quote.log;

import javax.swing.JOptionPane;

import com.nova.look.modules.Modules;
import com.nova.look.submodules.QuoteFormatGUI;
import com.nova.shared.command.Command;

/**
 * Esta clase funciona como puente entre los clientes de los comandos y los
 * comandos.
 * 
 * @author Camilo Nova
 * @version 1.0 Funcionalidad Basica
 */
public class QuoteCommand extends Command {

	/**
	 * Constructor de la clase
	 * 
	 * @param own
	 */
	public QuoteCommand(Modules own) {
		super(own);
	}

	@Override
	public void add() {
		new QuoteFormatGUI(owner.getMainFrame(), null);
		owner.updateDataTable(null);
	}

	@Override
	public void see() {
		if (owner.getTableSelectedID() == null)
			JOptionPane.showMessageDialog(null,
					"Debe seleccionar un registro!!!", "Error",
					JOptionPane.WARNING_MESSAGE);
		else
			new QuoteFormatGUI(owner.getMainFrame(), owner.getTableSelectedID());
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
