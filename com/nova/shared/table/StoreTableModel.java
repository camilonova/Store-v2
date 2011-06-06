package com.nova.shared.table;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import com.nova.core.Core;
import com.nova.look.modules.Modules;
import com.nova.parameter.GlobalConstants;

/**
 * Esta clase representa el modelo de la tabla, con lo cual se convierte en el
 * patron a seguir de la tabla, si se quisiera modificar alguna caracteristica
 * de la tabla, se debe hacer desde aqui.
 * 
 * @author Camilo Nova
 * @version 2.0 Optimizacion en la lectura de datos
 */
public class StoreTableModel extends AbstractTableModel {

	/**
	 * Representa los nombres de las columnas de la tabla
	 */
	protected ArrayList<String> encabezados;

	/**
	 * Datos mostrados en la tabla
	 */
	protected ResultSet data;

	/**
	 * Representa el modulo
	 */
	protected final Modules owner;

	/**
	 * Constructor de la clase. Le da el nombre a las columnas e inicializa las
	 * variables que almacenan los datos.
	 * 
	 * @param own
	 *            Modulo propietario
	 * @since 1.0
	 */
	public StoreTableModel(Modules own) {
		owner = own;
		encabezados = Core.getColumnNames(owner.getIdentifier());
		data = Core.getTableData(owner.getIdentifier(), Core
				.getProperty("ORDER FOR " + owner.getIdentifier()),
				GlobalConstants.ORDER_ASCENDANT);
		fireTableStructureChanged();
	}

	/**
	 * Actualiza los datos de la tabla
	 * 
	 * @param orderBy
	 *            Columna a ordenar
	 * @param type
	 *            GlobalConstants.ORDER_ASCENDANT o
	 *            GlobalConstants.ORDER_DESCENDANT
	 * @since 2.0
	 */
	public void update(String orderBy, String type) {
		data = Core.getTableData(owner.getIdentifier(), orderBy, type);
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

	@Override
	public String getColumnName(int column) {
		return encabezados.get(column);
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
}
