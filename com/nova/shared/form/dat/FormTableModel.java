package com.nova.shared.form.dat;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import com.nova.shared.math.StoreMath;

/**
 * Esta clase representa el modelo de la tabla para el formato que crea unos
 * nuevos datos.
 * 
 * @author Camilo Nova
 * @version 1.0
 */
public class FormTableModel extends AbstractTableModel {

	/**
	 * Representa los encabezados de las columnas
	 */
	private ArrayList<String> encabezados;

	/**
	 * Representa los datos de la tabla
	 */
	private ArrayList<String[]> datos;

	/**
	 * Constructor. Construye un modelo de tabla utilizado para la creacion de
	 * un formato
	 * 
	 * @param dataForInput
	 *            Encabezados de la tabla
	 * @since 1.0
	 */
	public FormTableModel(ArrayList<String> dataForInput) {
		encabezados = dataForInput;
		datos = new ArrayList<String[]>();

		fireTableStructureChanged();
	}

	public int getRowCount() {
		return datos.size();
	}

	public int getColumnCount() {
		return encabezados.size();
	}

	@Override
	public String getColumnName(int column) {
		return encabezados.get(column);
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if (columnIndex == 0 || columnIndex == 2)
			return true;
		return false;
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		if (columnIndex == 0)
			return Integer.class;
		if (columnIndex == 2)
			return Double.class;
		return super.getColumnClass(columnIndex);
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		int cantidad = 0;
		double valor = 0;

		if (aValue == null)
			return;

		if (columnIndex == 0) {
			cantidad = ((Integer) aValue).intValue();			
			valor = StoreMath.parseDouble((String) getValueAt(rowIndex, 2));
		}
		if (columnIndex == 2) {
			cantidad = Integer.parseInt((String) getValueAt(rowIndex, 0));
			valor = StoreMath.round(((Double) aValue));
		}
		if (cantidad < 1 || valor < 1)
			return;

		//TODO Hacer que el tres numerico se valla pa la damier
		double base = StoreMath.parseDouble((String) getValueAt(rowIndex, 3));
		double impuesto = StoreMath
				.parseDouble((String) getValueAt(rowIndex, 4));
		double porcentaje = new StoreMath(impuesto, base, StoreMath.DIVIDE)
				.toDouble();

		//TODO Cambiar el acceso estatico a los miembros del array
		String[] data = datos.remove(rowIndex);
		data[0] = String.valueOf(cantidad);
		data[2] = StoreMath.parseDouble(valor);
		data[3] = new StoreMath(cantidad, valor, StoreMath.MULTIPLY).toString();
		data[4] = new StoreMath(StoreMath.parseDouble(data[3]), porcentaje,
				StoreMath.MULTIPLY).toString();
		data[5] = new StoreMath(StoreMath.parseDouble(data[3]),
				StoreMath.parseDouble(data[4]), StoreMath.ADD).toString();

		datos.add(rowIndex, data);
		fireTableDataChanged();
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		return datos.get(rowIndex)[columnIndex];
	}

	/**
	 * Agrega una nueva fila con los datos pasados por parametro
	 * 
	 * @param data
	 *            Datos de la nueva fila
	 * @since 1.0
	 */
	public void addRowValues(String[] data) {
		datos.add(data);
		fireTableDataChanged();
	}

	/**
	 * Elimina la fila con el indice pasado por parametro
	 * 
	 * @param rowIndex
	 *            Indice de la fila a eliminar
	 * @since 1.0
	 */
	public void removeRow(int rowIndex) {
		datos.remove(rowIndex);
		fireTableDataChanged();
	}

	/**
	 * Elimina todos los datos existentes en la tabla
	 * 
	 * @since 1.0
	 */
	public void removeAllRows() {
		datos = new ArrayList<String[]>();
		fireTableDataChanged();
	}

	/**
	 * Sube una posicion la fila con el indice pasado por parametro
	 * 
	 * @param rowIndex
	 *            Indice de la fila a subir una posicion
	 * @return True si la fila cambio de posicion, False de lo contrario
	 * @since 1.0
	 */
	public boolean moveRowUp(int rowIndex) {
		if (rowIndex > 0) {
			String[] data = datos.remove(rowIndex);
			datos.add(rowIndex - 1, data);
			fireTableDataChanged();
			return true;
		}
		return false;
	}

	/**
	 * Baja una posicion la fila con el indice pasado por parametro
	 * 
	 * @param rowIndex
	 *            Indice de la fila a bajar una posicion
	 * @return True si la fila cambio de posicion, False de lo contrario
	 * @since 1.0
	 */
	public boolean moveRowDown(int rowIndex) {
		if (rowIndex < datos.size() - 1) {
			String[] data = datos.remove(rowIndex);
			datos.add(rowIndex + 1, data);
			fireTableDataChanged();
			return true;
		}
		return false;
	}

	/**
	 * Retorna los datos contenidos en la tabla para su almacenamiento en Base
	 * de Datos
	 * 
	 * @return Datos contenidos en la tabla
	 * @since 1.0
	 */
	public ArrayList<String[]> getTableData() {
		return datos;
	}
}
