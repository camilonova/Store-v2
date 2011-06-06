package com.nova.look.submodules;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

import com.nova.look.StatusBar;
import com.nova.parameter.GlobalConstants;
import com.nova.shared.command.Command;
import com.nova.stock.kardex.gui.KardexMenuBar;
import com.nova.stock.kardex.gui.KardexSelectionPanel;
import com.nova.stock.kardex.gui.KardexTablePanel;
import com.nova.stock.kardex.gui.KardexToolBar;
import com.nova.stock.kardex.log.KardexCommand;

/**
 * Representa el subModulo de kardex, el cual se utiliza para llevar un
 * seguimiento de las mercancias en el inventario, este tipo de modulos se
 * cargan en un dialogo provenientes del frame principal
 * 
 * @author Camilo Nova
 * @version 1.0 Funcionalidad Basica
 */
public class KardexGUI extends JDialog implements SubModules {

	/**
	 * Representa el frame principal
	 */
	private final JFrame mainFrame;

	/**
	 * Representa el codigo del primer articulo a mostrar
	 */
	private String shownItemID;

	/**
	 * Representa el comando del modulo
	 */
	private KardexCommand command;

	/**
	 * Representa el menu
	 */
	private KardexMenuBar menuBar;

	/**
	 * Representa la barra de herramientas
	 */
	private KardexToolBar toolBar;

	/**
	 * Representa el panel central
	 */
	private KardexTablePanel panel;

	/**
	 * Representa la barra de estado
	 */
	private StatusBar statusBar;

	/**
	 * Interfaz de entrada de datos
	 */
	private KardexSelectionPanel kardexSelectionPanel;

	/**
	 * Crea una interfaz para ver los datos del kardex
	 * 
	 * @param mainFrame
	 *            Frame principal
	 * @param firstItem
	 *            Item a mostrar primero
	 * @since 1.0
	 */
	public KardexGUI(JFrame mainFrame, String firstItem) {
		super(mainFrame, true);
		this.mainFrame = mainFrame;
		command = new KardexCommand(this);
		setItemToShow(firstItem);

		menuBar = new KardexMenuBar(this);
		toolBar = new KardexToolBar(this);
		panel = new KardexTablePanel(this);
		statusBar = new StatusBar("SubModulo Kardex Cargado!!!");
		kardexSelectionPanel = new KardexSelectionPanel(this, panel);

		JPanel centerPanel = new JPanel();
		panel.setPreferredSize(new Dimension(590, 220));
		centerPanel.add(kardexSelectionPanel, BorderLayout.NORTH);
		centerPanel.add(panel, BorderLayout.CENTER);

		setJMenuBar(menuBar);
		add(toolBar, BorderLayout.NORTH);
		add(centerPanel, BorderLayout.CENTER);
		add(statusBar, BorderLayout.SOUTH);

		setSize(600, 400);
		setLocation((GlobalConstants.SCREEN_SIZE_WIDTH - getWidth()) / 2,
				(GlobalConstants.SCREEN_SIZE_HEIGHT - getHeight()) / 2);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		setVisible(true);
	}

	public String getItemToShow() {
		return shownItemID;
	}

	public void setItemToShow(String newItemID) {
		shownItemID = newItemID;
	}

	public String getIdentifier() {
		return "kardex";
	}

	public String getFilter() {
		return "Codigo";
	}

	public JMenuBar getMenuBar() {
		return menuBar;
	}

	public void setStatusBarText(String text) {
		statusBar.setText(text);
	}

	public void exit() {
		dispose();
	}

	public void updateDataTable(String itemID) {
		kardexSelectionPanel.loadData(itemID);
		panel.updateData(itemID);
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
		return mainFrame;
	}

	public ArrayList<String> getDataForInput() {
		/*
		 * No tiene implementacion debido a que el encargado de insertar datos
		 * es Stock
		 */
		return null;
	}

	public String getNextConsecutiveNumber() {
		return null;
	}
}