package com.nova.provider.gui;

import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import com.nova.look.modules.ProviderGUI;
import com.nova.shared.table.StoreTablePanel;

/**
 * Esta clase es el panel donde se muestra la tabla con los datos del modulo de
 * proveedores
 * 
 * @author Camilo Nova
 * @version 1.0 Funcionalidad basica
 */
public class ProviderTablePanel extends StoreTablePanel {

	/**
	 * Construye el panel para el modulo
	 * 
	 * @param own
	 *            Modulo propietario
	 * @since 1.0
	 */
	public ProviderTablePanel(ProviderGUI own) {
		super(own);
	}

	@Override
	protected void setColumnsProperties() {
		cellRenderer = new DefaultTableCellRenderer();
		cellRenderer.setHorizontalAlignment(SwingConstants.RIGHT);

		columnasTabla = tabla.getColumn("ID");
		columnasTabla.setCellRenderer(cellRenderer);
		columnasTabla.setPreferredWidth(30);

		columnasTabla = tabla.getColumn("Nombre Empresa");
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

		columnasTabla = tabla.getColumn("Representante");
		columnasTabla.setPreferredWidth(250);

		columnasTabla = tabla.getColumn("Contacto");
		columnasTabla.setCellRenderer(cellRenderer);

		columnasTabla = tabla.getColumn("Descripcion Productos");
		columnasTabla.setPreferredWidth(250);

		columnasTabla = tabla.getColumn("Modificado por");
		columnasTabla.setPreferredWidth(100);
	}
}
