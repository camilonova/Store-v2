package com.nova.look.submodules;

import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JMenuBar;

import com.nova.core.Core;
import com.nova.parameter.GlobalConstants;
import com.nova.shared.command.Command;
import com.nova.shared.form.gui.Form;

/**
 * Representa el submodulo de manejo de las cotizaciones
 * 
 * @author Camilo Nova
 * @version 1.0
 */
public class QuoteFormatGUI extends Form implements SubModules {

	/**
	 * Codigo del elemento a mostrar en el formato
	 */
	private String shownItemID;

	/**
	 * Frame principal donde se mostrara el formato
	 */
	private final JFrame mainFrame;

	/**
	 * Constructor de la clase, crea un formato para el manejo de cotizaciones
	 * en la aplicacion.
	 * 
	 * @param mainFrame
	 *            Frame donde mostrar el formato
	 * @param firstItem
	 *            El codigo de la cotizacion a mostrar. null si es nueva.
	 * @since 1.0
	 */
	public QuoteFormatGUI(JFrame mainFrame, String firstItem) {
		super(mainFrame, false, true);
		this.mainFrame = mainFrame;

		setItemToShow(firstItem);
		setSubmoduleOwner(this);
		setLayout(new FlowLayout());

		if (getItemToShow() == null)
			setTitle("Creacion de cotizacion nueva");
		else
			setTitle("Cotizacion N� " + getItemToShow());

		add(getToolBar());
		add(getInfoPanel());
		add(getInsertPanel());
		add(getDataTable());
		add(getTotalsPanel());

		setSize(800, 600);
		setLocation((GlobalConstants.SCREEN_SIZE_WIDTH - getWidth()) / 2,
				(GlobalConstants.SCREEN_SIZE_HEIGHT - getHeight()) / 2);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		setAlwaysOnTop(true);
		setVisible(true);
	}

	public String getItemToShow() {
		return shownItemID;
	}

	public void setItemToShow(String newItemID) {
		shownItemID = newItemID;
	}

	public String getFilter() {
		return "Cotizacion";
	}

	public String getIdentifier() {
		return "quote_items";
	}

	public JMenuBar getMenuBar() {
		return null;
	}

	public void setStatusBarText(String text) {
		// Sin Implementacion
	}

	public void exit() {
		dispose();
	}

	public void updateDataTable(String message) {
		// Sin Implementacion
	}

	public String getTableSelectedID() {
		// Sin Implementacion
		return null;
	}

	public void setSelectedCell(int x, int y) {
		// Sin Implementacion
	}

	public Command getCommand() {
		// Sin Implementacion
		return null;
	}

	public JFrame getMainFrame() {
		return mainFrame;
	}

	public ArrayList<String> getDataForInput() {
		ArrayList<String> list = new ArrayList<String>();
		list.add("Cantidad");
		list.add("Descripcion");
		list.add("Valor Unitario");
		list.add("Base");
		list.add("Impuesto");
		list.add("Subtotal");

		return list;
	}

	public String getNextConsecutiveNumber() {
		String factura = Core.getLastID("quote", getFilter());
		if (factura.equals("1"))
			return Core.getProperty("QUOTE FIRST NUMBER");
		return factura;
	}
}