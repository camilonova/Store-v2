package com.nova.look.modules;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

import com.nova.look.StatusBar;
import com.nova.look.Store;
import com.nova.provider.gui.ProviderMenuBar;
import com.nova.provider.gui.ProviderTablePanel;
import com.nova.provider.gui.ProviderToolBar;
import com.nova.provider.log.ProviderCommand;
import com.nova.shared.command.Command;

/**
 * Esta clase carga el modulo Customer en el frame principal, cargando el menu,
 * la barra de herramientas, la tabla de los datos y la barra de estatus.
 * 
 * @author Camilo Nova
 * @version 1.0 Funcionalidad Basica
 */
public class ProviderGUI extends JPanel implements Modules {

	/**
	 * Representa el frame principal
	 */
	private Store principal;

	/**
	 * Representa el menu
	 */
	private ProviderMenuBar menuBar;

	/**
	 * Representa la barra de herramientas
	 */
	private ProviderToolBar toolBar;

	/**
	 * Representa el panel de los datos
	 */
	private ProviderTablePanel panel;

	/**
	 * Representa la barra de estado
	 */
	private StatusBar statusBar;

	/**
	 * Representa el comando del modulo
	 */
	private Command command;

	/**
	 * Constructor de la clase, carga los componentes del modulo.
	 * 
	 * @param own
	 *            Frame principal
	 * @since 1.0
	 */
	public ProviderGUI(Store own) {
		principal = own;
		command = new ProviderCommand(this);

		menuBar = new ProviderMenuBar(this);
		toolBar = new ProviderToolBar(this);
		panel = new ProviderTablePanel(this);
		statusBar = new StatusBar("Modulo de Proveedores Cargado!!!");

		add(toolBar);
		add(panel);
		add(statusBar);
		principal.setTitle(principal.getTitle() + " - Proveedores");
	}

	public String getIdentifier() {
		return "provider";
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
