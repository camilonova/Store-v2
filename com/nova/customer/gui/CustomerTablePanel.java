package com.nova.customer.gui;

import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import com.nova.look.modules.CustomerGUI;
import com.nova.shared.table.StoreTablePanel;

/**
 * Esta clase es el panel donde se muestra la tabla con los datos del modulo de
 * clientes
 * 
 * @author Camilo Nova
 * @version 1.0 Funcionalidad basica
 */
public class CustomerTablePanel extends StoreTablePanel {

	/**
	 * Construye el panel para el modulo
	 * 
	 * @param own
	 *            Modulo propietario
	 * @since 1.0
	 */
	public CustomerTablePanel(CustomerGUI own) {
		super(own);
	}

	@Override
	protected void setColumnsProperties() {
		cellRenderer = new DefaultTableCellRenderer();
		cellRenderer.setHorizontalAlignment(SwingConstants.RIGHT);

		columnasTabla = tabla.getColumn("ID");
		columnasTabla.setCellRenderer(cellRenderer);
		columnasTabla.setPreferredWidth(30);

		columnasTabla = tabla.getColumn("Nombre");
		columnasTabla.setPreferredWidth(350);

		columnasTabla = tabla.getColumn("NIT/CC");
		columnasTabla.setCellRenderer(cellRenderer);
		columnasTabla.setPreferredWidth(100);

		columnasTabla = tabla.getColumn("Telefono");
		columnasTabla.setCellRenderer(cellRenderer);

		columnasTabla = tabla.getColumn("Fax");
		columnasTabla.setCellRenderer(cellRenderer);

		columnasTabla = tabla.getColumn("Movil");
		columnasTabla.setCellRenderer(cellRenderer);

		columnasTabla = tabla.getColumn("Direccion");
		columnasTabla.setPreferredWidth(200);

		columnasTabla = tabla.getColumn("Modificado por");
		columnasTabla.setPreferredWidth(100);
	}
}
