package com.nova.shared.table;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

import com.nova.core.Core;
import com.nova.look.modules.Modules;

/**
 * Esta clase es una mejora del sistema de paneles de la version anterior, en
 * esta clase se fusiona la funcionalidad de los paneles y se pone a disposicion
 * de todos los modulos de la aplicacion.
 * 
 * @author Camilo Nova
 * @version 1.0 Funcionalidad Basica
 */
public abstract class StoreTablePanel extends JScrollPane {

	/**
	 * Representa la tabla de datos
	 */
	protected JTable tabla;

	/**
	 * Representa el modelo de la tabla de datos
	 */
	protected StoreTableModel modelo;

	/**
	 * Representa el modelo de las celdas
	 */
	protected DefaultTableCellRenderer cellRenderer;

	/**
	 * Representa las columnas de la tabla
	 */
	protected TableColumn columnasTabla;

	/**
	 * Representa el ordenamiento de las columnas
	 */
	protected StoreTableSorter sorter;

	/**
	 * Representa el modulo dueño
	 */
	protected final Modules owner;

	/**
	 * Construye un panel donde se muestra una tabla con los datos del modulo.
	 * 
	 * @param own
	 *            Modulo propietario
	 * @since 1.0
	 */
	public StoreTablePanel(Modules own) {
		owner = own;
		modelo = new StoreTableModel(own);
		tabla = new JTable(modelo);
		sorter = new StoreTableSorter(tabla);

		tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		setColumnsProperties();
		setViewportView(tabla);
	}

	/**
	 * Le da las propiedades de alineacion y tamaño a las columnas de la tabla.
	 * 
	 * @since 1.0
	 */
	protected abstract void setColumnsProperties();

	/**
	 * Actualiza los datos de la tabla y muestra la cadena recibida por
	 * parametro
	 * 
	 * @param message
	 *            Mensaje a mostrar en la barra de estado
	 * @since 1.0
	 */
	public void updateData(String message) {
		int column = sorter.getSelectedColumnIndex();
		if (column == -1)
			column = Core.getColumnIndex(owner.getIdentifier(), Core
					.getProperty("ORDER FOR " + owner.getIdentifier()));

		int row = tabla.getSelectedRow();
		int col = tabla.getSelectedColumn();
		modelo.update(modelo.getColumnName(column), sorter.getOrientaton());
		setSelectedCell(row, col);

		if (message == null)
			owner.setStatusBarText("Datos Cargados, Leidos "
					+ Core.getRowsCount(owner.getIdentifier()) + " registros.");
		else
			owner.setStatusBarText(message);
	}

	/**
	 * Retorna el valor de la primera columna de la fila seleccionada
	 * 
	 * @return Valor de la primera columna en la fila seleccionada, null si no
	 *         hay fila seleccionada.
	 * @since 1.0
	 */
	public String getSelectedID() {
		if (tabla.getSelectedRow() != -1)
			return (String) modelo.getValueAt(tabla.getSelectedRow(), 0);
		return null;
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
		if (row > -1 && row < tabla.getRowCount() && col > -1
				&& col < tabla.getColumnCount()) {
			tabla.setColumnSelectionInterval(col, col);
			tabla.setRowSelectionInterval(row, row);
			tabla.scrollRectToVisible(tabla.getCellRect(row, col, true));
		}
	}
}