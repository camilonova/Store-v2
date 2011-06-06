package com.nova.stock.gui;

import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import com.nova.look.modules.StockGUI;
import com.nova.shared.table.StoreTablePanel;

/**
 * Esta clase es el panel donde se muestra la tabla con los datos del modulo de
 * inventario
 * 
 * @author Camilo Nova
 * @version 1.0 Funcionalidad basica
 */
public class StockTablePanel extends StoreTablePanel {

	/**
	 * Construye el panel para el modulo
	 * 
	 * @param own
	 *            Modulo propietario
	 * @since 1.0
	 */
	public StockTablePanel(StockGUI own) {
		super(own);
	}

	@Override
	protected void setColumnsProperties() {
		cellRenderer = new DefaultTableCellRenderer();
		cellRenderer.setHorizontalAlignment(SwingConstants.RIGHT);

		columnasTabla = tabla.getColumn("Codigo");
		columnasTabla.setCellRenderer(cellRenderer);
		columnasTabla.setPreferredWidth(100);

		columnasTabla = tabla.getColumn("Referencia");
		columnasTabla.setPreferredWidth(100);

		columnasTabla = tabla.getColumn("Categoria");
		columnasTabla.setPreferredWidth(100);

		columnasTabla = tabla.getColumn("Cantidad");
		columnasTabla.setCellRenderer(cellRenderer);

		columnasTabla = tabla.getColumn("Minimo");
		columnasTabla.setCellRenderer(cellRenderer);

		columnasTabla = tabla.getColumn("Descripcion");
		columnasTabla.setPreferredWidth(400);

		columnasTabla = tabla.getColumn("N° por Empaque");
		columnasTabla.setCellRenderer(cellRenderer);

		columnasTabla = tabla.getColumn("Impuesto");
		columnasTabla.setCellRenderer(cellRenderer);

		columnasTabla = tabla.getColumn("Utilidad %");
		columnasTabla.setCellRenderer(cellRenderer);

		columnasTabla = tabla.getColumn("Precio Venta");
		columnasTabla.setCellRenderer(cellRenderer);

		columnasTabla = tabla.getColumn("Proveedor");
		columnasTabla.setPreferredWidth(250);

		columnasTabla = tabla.getColumn("Costo");
		columnasTabla.setCellRenderer(cellRenderer);

		columnasTabla = tabla.getColumn("Caracteristicas");
		columnasTabla.setPreferredWidth(300);

		columnasTabla = tabla.getColumn("Ruta Imagen");
		columnasTabla.setPreferredWidth(300);

		columnasTabla = tabla.getColumn("Modificado por");
		columnasTabla.setPreferredWidth(100);
	}
}