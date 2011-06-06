package com.nova.look.modules;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

import com.nova.look.StatusBar;
import com.nova.look.Store;
import com.nova.sale.gui.SaleMenuBar;
import com.nova.sale.gui.SaleTablePanel;
import com.nova.sale.gui.SaleToolBar;
import com.nova.sale.log.SaleCommand;
import com.nova.shared.command.Command;

/**
 * Esta clase carga el modulo de ventas en el frame principal, cargando el menu,
 * la barra de herramientas, la tabla de datos y la barra de estado.
 * 
 * @author Camilo Nova
 * @version 1.0 Funcionalidad Basica
 */
public class SaleGUI extends JPanel implements Modules {

	/**
	 * Representa el frame principal
	 */
	private Store principal;

	/**
	 * Representa el menu
	 */
	private SaleMenuBar menuBar;

	/**
	 * Representa la barra de herramientas
	 */
	private SaleToolBar toolBar;

	/**
	 * Representa el panel de los datos
	 */
	private SaleTablePanel panel;

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
	 * @param owner
	 *            Frame principal
	 * @since 1.0
	 */
	public SaleGUI(Store owner) {
		principal = owner;
		command = new SaleCommand(this);

		menuBar = new SaleMenuBar(this);
		toolBar = new SaleToolBar(this);
		panel = new SaleTablePanel(this);
		statusBar = new StatusBar("Modulo de Ventas Cargado!!!");

		add(toolBar);
		add(panel);
		add(statusBar);
		principal.setTitle(principal.getTitle() + " - Ventas");
	}

	public String getIdentifier() {
		return "invoice";
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