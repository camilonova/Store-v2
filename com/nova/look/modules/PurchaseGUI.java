package com.nova.look.modules;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

import com.nova.look.StatusBar;
import com.nova.look.Store;
import com.nova.purchase.gui.PurchaseMenuBar;
import com.nova.purchase.gui.PurchaseTablePanel;
import com.nova.purchase.gui.PurchaseToolBar;
import com.nova.purchase.log.PurchaseCommand;
import com.nova.shared.command.Command;

/**
 * Esta clase carga el modulo de cotizaciones en el frame principal, cargando el
 * menu, la barra de herramientas, la tabla de datos y la barra de estado.
 * 
 * @author Camilo Nova
 * @version 1.0 Funcionalidad Basica
 */
public class PurchaseGUI extends JPanel implements Modules {

	/**
	 * Representa el frame principal
	 */
	private Store principal;

	/**
	 * Representa el menu
	 */
	private PurchaseMenuBar menuBar;

	/**
	 * Representa la barra de herramientas
	 */
	private PurchaseToolBar toolBar;

	/**
	 * Representa el panel de los datos
	 */
	private PurchaseTablePanel panel;

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
	public PurchaseGUI(Store owner) {
		principal = owner;
		command = new PurchaseCommand(this);

		menuBar = new PurchaseMenuBar(this);
		toolBar = new PurchaseToolBar(this);
		panel = new PurchaseTablePanel(this);
		statusBar = new StatusBar("Modulo de Compras Cargado!!!");

		add(toolBar);
		add(panel);
		add(statusBar);
		principal.setTitle(principal.getTitle() + " - Compras");
	}

	public String getIdentifier() {
		return "purchase";
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