package com.nova.stock.kardex.gui;

import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import com.nova.look.submodules.SubModules;
import com.nova.shared.submodule.StoreSubModuleTablePanel;

/**
 * Representa el panel donde se muestra la tabla con los datos del submodulo de
 * kardex
 * 
 * @author Camilo Nova
 * @version 1.0
 */
public class KardexTablePanel extends StoreSubModuleTablePanel {

	/**
	 * Construye el panel para el submodulo
	 * 
	 * @param own
	 *            Submodulo
	 * @since 1.0
	 */
	public KardexTablePanel(SubModules own) {
		super(own, true);
		setColumnsProperties();
	}

	@Override
	protected void setColumnsProperties() {
		cellRenderer = new DefaultTableCellRenderer();
		cellRenderer.setHorizontalAlignment(SwingConstants.RIGHT);

		columnasTabla = tabla.getColumn("Proceso");
		columnasTabla.setCellRenderer(cellRenderer);

		columnasTabla = tabla.getColumn("Fecha");
		columnasTabla.setCellRenderer(cellRenderer);

		columnasTabla = tabla.getColumn("Operacion");
		columnasTabla.setPreferredWidth(200);

		columnasTabla = tabla.getColumn("Unidades");
		columnasTabla.setCellRenderer(cellRenderer);

		columnasTabla = tabla.getColumn("Valor Unitario");
		columnasTabla.setCellRenderer(cellRenderer);

		columnasTabla = tabla.getColumn("Valor Total");
		columnasTabla.setCellRenderer(cellRenderer);

		columnasTabla = tabla.getColumn("Saldo");
		columnasTabla.setCellRenderer(cellRenderer);

		columnasTabla = tabla.getColumn("Costo");
		columnasTabla.setCellRenderer(cellRenderer);

		columnasTabla = tabla.getColumn("Total");
		columnasTabla.setCellRenderer(cellRenderer);
	}
}
