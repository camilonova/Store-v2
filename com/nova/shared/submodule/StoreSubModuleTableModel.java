package com.nova.shared.submodule;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import com.nova.core.Core;
import com.nova.parameter.GlobalConstants;

/**
 * Representa el modelo de la tabla para los submodulos de la aplicacion. Su
 * aplicacion principal es la de ver los datos del submodulo. Ademas de obtener
 * los datos directamente desde la base de datos, con lo cual la modificacion o
 * actualizacion de los datos debe hacerse desde otra clase
 * 
 * @author Camilo Nova
 * @version 1.0
 */
public class StoreSubModuleTableModel extends AbstractTableModel {

	/**
	 * Representa los nombres de las columnas de la tabla
	 */
	protected ArrayList<String> encabezados;

	/**
	 * Datos mostrados en la tabla
	 */
	protected ResultSet data;

	/**
	 * Tabla a la que esta relacionado el modelo
	 */
	private final String table;

	/**
	 * Columna con la cual filtrar los datos
	 */
	private final String keyColumn;

	/**
	 * Orden de los datos a mostrar
	 */
	private final String order;

	/**
	 * Constructor. Inicializa la tabla utilizando los parametros pasados
	 * 
	 * @param identifier
	 *            Identificador del Submodulo
	 * @param keyCol
	 *            Columna filtro
	 * @param keyVal
	 *            Dato comun para el filtro
	 * @param lastFirst
	 *            Verdadero si el ultimo elemento debe ser mostrado de primero
	 * @since 1.0
	 */
	public StoreSubModuleTableModel(String identifier, String keyCol,
			String keyVal, boolean lastFirst) {
		this.table = identifier;
		this.keyColumn = keyCol;
		this.order = lastFirst ? GlobalConstants.ORDER_DESCENDANT
				: GlobalConstants.ORDER_ASCENDANT;
		encabezados = Core.getColumnNames(table);
		encabezados.remove(keyCol);

		updateData(keyVal);
	}

	/**
	 * Actualiza los datos de la tabla
	 * 
	 * @param keyVal
	 *            Dato comun para el filtro
	 * @since 1.0
	 */
	public void updateData(String keyVal) {
		data = Core.getTableData(table, Core.getProperty("ORDER FOR " + table),
				order, keyColumn, keyVal);
		fireTableDataChanged();
	}

	public int getRowCount() {
		try {
			data.last();
			return data.getRow();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public int getColumnCount() {
		return encabezados.size();
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		try {
			data.absolute(rowIndex + 1);
			return data.getString(columnIndex + 1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "";
	}

	@Override
	public String getColumnName(int column) {
		return encabezados.get(column);
	}
}
