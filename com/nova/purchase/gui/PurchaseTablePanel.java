package com.nova.purchase.gui;

import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import com.nova.look.modules.Modules;
import com.nova.shared.table.StoreTablePanel;

/**
 * Esta clase es el panel donde se muestra la tabla con los datos del modulo de
 * cotizaciones
 * 
 * @author Camilo Nova
 * @version 1.0 Funcionalidad basica
 */
public class PurchaseTablePanel extends StoreTablePanel {

	/**
	 * Construye el panel para el modulo
	 * 
	 * @param own
	 *            Modulo propietario
	 * @since 1.0
	 */
	public PurchaseTablePanel(Modules own) {
		super(own);
	}

	@Override
	protected void setColumnsProperties() {
		cellRenderer = new DefaultTableCellRenderer();
		cellRenderer.setHorizontalAlignment(SwingConstants.RIGHT);

		columnasTabla = tabla.getColumn("Compra");
		columnasTabla.setCellRenderer(cellRenderer);

		columnasTabla = tabla.getColumn("Cliente");
		columnasTabla.setPreferredWidth(250);

		columnasTabla = tabla.getColumn("N° Items");
		columnasTabla.setCellRenderer(cellRenderer);
		columnasTabla.setPreferredWidth(80);

		columnasTabla = tabla.getColumn("N° Unidades");
		columnasTabla.setCellRenderer(cellRenderer);
		columnasTabla.setPreferredWidth(100);

		columnasTabla = tabla.getColumn("Subtotal");
		columnasTabla.setCellRenderer(cellRenderer);

		columnasTabla = tabla.getColumn("Impuesto");
		columnasTabla.setCellRenderer(cellRenderer);

		columnasTabla = tabla.getColumn("Gran Total");
		columnasTabla.setCellRenderer(cellRenderer);
	}
}