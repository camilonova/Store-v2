package com.nova.look.modules;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

import com.nova.customer.gui.CustomerMenuBar;
import com.nova.customer.gui.CustomerTablePanel;
import com.nova.customer.gui.CustomerToolBar;
import com.nova.customer.log.CustomerCommand;
import com.nova.look.StatusBar;
import com.nova.look.Store;
import com.nova.shared.command.Command;

/**
 * Esta clase carga el modulo Customer en el frame principal, cargando el menu,
 * la barra de herramientas, la tabla de los datos y la barra de estatus.
 * 
 * @author Camilo Nova
 * @version 1.0 Funcionalidad Basica
 */
public class CustomerGUI extends JPanel implements Modules {

	/**
	 * Representa el frame principal
	 */
	private Store principal;

	/**
	 * Representa el menu
	 */
	private CustomerMenuBar menuBar;

	/**
	 * Representa la barra de herramientas
	 */
	private CustomerToolBar toolBar;

	/**
	 * Representa el panel de los datos
	 */
	private CustomerTablePanel panel;

	/**
	 * Representa la barra de estado
	 */
	private StatusBar statusBar;

	/**
	 * Representa el comando del modulo
	 */
	private CustomerCommand command;

	/**
	 * Constructor de la clase, carga los componentes del modulo.
	 * 
	 * @param own
	 *            Frame principal
	 * @since 1.0
	 */
	public CustomerGUI(Store own) {
		principal = own;
		command = new CustomerCommand(this);

		menuBar = new CustomerMenuBar(this);
		toolBar = new CustomerToolBar(this);
		panel = new CustomerTablePanel(this);
		statusBar = new StatusBar("Modulo de Clientes Cargado!!!");

		add(toolBar);
		add(panel);
		add(statusBar);
		principal.setTitle(principal.getTitle() + " - Clientes");
	}

	public String getIdentifier() {
		return "customer";
	}

	public JMenuBar getMenuBar() {
		return menuBar;
	}

	public void setStatusBarText(String text) {
		statusBar.setText(text);
	}

	public void exit() {
		principal.removeModulo();
	}

	public void updateDataTable(String message) {
		panel.updateData(message);
	}

	public String getTableSelectedID() {
		return panel.getSelectedID();
	}

	public void setSelectedCell(int x, int y) {
		panel.setSelectedCell(x, y);
	}

	public Command getCommand() {
		return command;
	}

	public JFrame getMainFrame() {
		return principal;
	}
}
