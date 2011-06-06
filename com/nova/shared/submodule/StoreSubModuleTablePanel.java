package com.nova.shared.submodule;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

import com.nova.look.submodules.SubModules;

/**
 * Representa el panel donde se ubica la tabla para los submodulos de la
 * aplicacion
 * 
 * @author Camilo Nova
 * @version 1.1
 */
public abstract class StoreSubModuleTablePanel extends JScrollPane {

	/**
	 * Representa la tabla de datos
	 */
	protected JTable tabla;

	/**
	 * Representa el modelo de la tabla
	 */
	private StoreSubModuleTableModel modelo;

	/**
	 * Representa el modelo de las celdas
	 */
	protected DefaultTableCellRenderer cellRenderer;

	/**
	 * Representa las columnas de la tabla
	 */
	protected TableColumn columnasTabla;

	/**
	 * Representa el submodulo dueño
	 */
	private final SubModules owner;

	/**
	 * Construye un panel donde se muestra una tabla con los datos del
	 * submodulo. Si el item a mostrar es null, no se crea modelo de tabla y
	 * debe ser creado independiente.
	 * 
	 * @param own
	 *            Submodulo propietario
	 * @param lastFirst
	 *            Verdadero si el ultimo elemento debe ser mostrado primero
	 * @since 1.0
	 */
	public StoreSubModuleTablePanel(SubModules own, boolean lastFirst) {
		owner = own;
		if (owner.getItemToShow() != null) {
			modelo = new StoreSubModuleTableModel(owner.getIdentifier(), owner
					.getFilter(), owner.getItemToShow(), lastFirst);
			tabla = new JTable(modelo);
		} else {
			modelo = null;
			tabla = new JTable();
		}

		tabla.getTableHeader().setReorderingAllowed(false);
		tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		setViewportView(tabla);
	}

	/**
	 * Le da las propiedades de alineacion y tamaño a las columnas de la tabla.
	 * 
	 * @since 1.0
	 */
	protected abstract void setColumnsProperties();

	/**
	 * Actualiza los datos de la tabla, filtrando los datos por el parametro
	 * recibido
	 * 
	 * @param itemID
	 *            Fitro de los datos
	 * @since 1.0
	 */
	public void updateData(String itemID) {
		modelo.updateData(itemID);
		owner.setItemToShow(itemID);
	}

	/**
	 * Retorna el ID de la fila seleccionada
	 * 
	 * @return ID de la fila seleccionada, null si no esta seleccionada ninguna
	 *         fila
	 * @since 1.0
	 */
	public String getSelectedID() {
		if (tabla.getSelectedRow() != -1)
			return (String) tabla.getModel().getValueAt(tabla.getSelectedRow(),
					0);
		return null;
	}

	/**
	 * Retorna la fila seleccionada, si no hay fila seleccionada retorna -1
	 * 
	 * @return indice de la fila seleccionada
	 * @since 1.1
	 */
	public int getSelectedRow() {
		return tabla.getSelectedRow();
	}

	/**
	 * Retorna la columna seleccionada, si no hay columna seleccionada retorna
	 * -1
	 * 
	 * @return indice de la columna seleccionada
	 * @since 1.1
	 */
	public int getSelectedColumn() {
		return tabla.getSelectedColumn();
	}

	/**
	 * Metodo que selecciona la celda indicada por los parametros
	 * 
	 * @param row
	 *            Fila a seleccionar
	 * @param col
	 *            Columna a seleccionar
	 * @since 1.0
	 */
	public void setSelectedCell(int row, int col) {
		if (row > -1 && col > -1) {
			tabla.setColumnSelectionInterval(col, col);
			tabla.setRowSelectionInterval(row, row);
			tabla.scrollRectToVisible(tabla.getCellRect(row, col, true));
		}
	}
}