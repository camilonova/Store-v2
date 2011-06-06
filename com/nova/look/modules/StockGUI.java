package com.nova.look.modules;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

import com.nova.look.StatusBar;
import com.nova.look.Store;
import com.nova.shared.command.Command;
import com.nova.stock.gui.StockMenuBar;
import com.nova.stock.gui.StockTablePanel;
import com.nova.stock.gui.StockToolBar;
import com.nova.stock.log.StockCommand;

/**
 * Esta clase carga el modulo Stock en el frame principal, cargando el menu, la
 * barra de herramientas, la tabla de los datos y la barra de estado.
 * 
 * @author Camilo Nova
 * @version 1.0 Funcionalidad Basica
 */
public class StockGUI extends JPanel implements Modules {

	/**
	 * Representa el frame principal
	 */
	private Store principal;

	/**
	 * Representa el menu
	 */
	private StockMenuBar menuBar;

	/**
	 * Representa la barra de herramientas
	 */
	private StockToolBar toolBar;

	/**
	 * Representa el panel de los datos
	 */
	private StockTablePanel panel;

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
	public StockGUI(Store owner) {
		principal = owner;
		command = new StockCommand(this);

		menuBar = new StockMenuBar(this);
		toolBar = new StockToolBar(this);
		panel = new StockTablePanel(this);
		statusBar = new StatusBar("Modulo de Inventario Cargado!!!");

		add(toolBar);
		add(panel);
		add(statusBar);
		principal.setTitle(principal.getTitle() + " - Inventario");
	}

	public String getIdentifier() {
		return "stock";
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