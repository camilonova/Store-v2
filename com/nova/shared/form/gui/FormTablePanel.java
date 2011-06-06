package com.nova.shared.form.gui;

import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import com.nova.look.submodules.SubModules;
import com.nova.shared.form.dat.FormTableModel;
import com.nova.shared.submodule.StoreSubModuleTablePanel;

/**
 * Representa el panel donde se muestra la tabla con los datos del submodulo que
 * necesite el formato
 * 
 * @author Camilo Nova
 * @version 1.0
 */
public class FormTablePanel extends StoreSubModuleTablePanel {

	/**
	 * Modelo de la tabla
	 */
	private FormTableModel formTableModel;

	/**
	 * Construye el panel para el submodulo. Verifica si el panel es para
	 * entrada de datos y dependiendo de esto elige el modelo que se acomode a
	 * ese comportamiento
	 * 
	 * @param own
	 *            Submodulo
	 * @since 1.0
	 */
	FormTablePanel(SubModules own) {
		super(own, false);
		if (own.getItemToShow() == null) {
			formTableModel = new FormTableModel(own.getDataForInput());
			tabla.setModel(formTableModel);
			setColumnsProperties();
		} else
			setColumnsCustomProperties();
	}

	/**
	 * Retorna el modelo de la tabla
	 * 
	 * @return Modelo de la tabla
	 * @since 1.0
	 */
	public FormTableModel getModel() {
		return formTableModel;
	}

	/**
	 * Le da las propiedades de alineacion a las columnas del modelo de ver
	 * 
	 */
	private void setColumnsCustomProperties() {
		cellRenderer = new DefaultTableCellRenderer();
		cellRenderer.setHorizontalAlignment(SwingConstants.RIGHT);

		columnasTabla = tabla.getColumn("Proceso");
		columnasTabla.setCellRenderer(cellRenderer);
		columnasTabla.setPreferredWidth(50);

		columnasTabla = tabla.getColumn("Descripcion");
		columnasTabla.setPreferredWidth(300);

		columnasTabla = tabla.getColumn("Cantidad");
		columnasTabla.setCellRenderer(cellRenderer);
		columnasTabla.setPreferredWidth(60);

		columnasTabla = tabla.getColumn("Valor Unitario");
		columnasTabla.setCellRenderer(cellRenderer);
		columnasTabla.setPreferredWidth(80);

		columnasTabla = tabla.getColumn("Base");
		columnasTabla.setCellRenderer(cellRenderer);
		columnasTabla.setPreferredWidth(100);

		columnasTabla = tabla.getColumn("Impuesto");
		columnasTabla.setCellRenderer(cellRenderer);
		columnasTabla.setPreferredWidth(80);

		columnasTabla = tabla.getColumn("Valor Total");
		columnasTabla.setCellRenderer(cellRenderer);
		columnasTabla.setPreferredWidth(100);
	}

	@Override
	protected void setColumnsProperties() {
		cellRenderer = new DefaultTableCellRenderer();
		cellRenderer.setHorizontalAlignment(SwingConstants.RIGHT);

		columnasTabla = tabla.getColumn("Cantidad");
		columnasTabla.setCellRenderer(cellRenderer);

		columnasTabla = tabla.getColumn("Descripcion");
		columnasTabla.setPreferredWidth(350);

		columnasTabla = tabla.getColumn("Valor Unitario");
		columnasTabla.setCellRenderer(cellRenderer);

		columnasTabla = tabla.getColumn("Base");
		columnasTabla.setCellRenderer(cellRenderer);
		columnasTabla.setPreferredWidth(100);

		columnasTabla = tabla.getColumn("Impuesto");
		columnasTabla.setCellRenderer(cellRenderer);
		columnasTabla.setPreferredWidth(70);

		columnasTabla = tabla.getColumn("Subtotal");
		columnasTabla.setCellRenderer(cellRenderer);
		columnasTabla.setPreferredWidth(100);
	}
}
