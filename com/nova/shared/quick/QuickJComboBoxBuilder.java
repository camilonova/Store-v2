package com.nova.shared.quick;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import com.nova.core.Core;

/**
 * Esta clase es una generalizacion de los JComboBox que se utilizan en las
 * interfaces de usuario, pero con funcionalidades especiales desarrolladas para
 * las necesidades de la aplicacion. La parte mas importante de esta clase, es
 * la carga dinamica de las interfaces de entrada de nuevos datos la cual hace
 * muy flexible de implementar. La primera parte de las funcionalidades
 * especiales se encarga de crear al final de la lista un elemento con el cual
 * se pueda agregar un elemento rapidamente.
 * 
 * @author Camilo Nova
 * @version 1.1
 */
public class QuickJComboBoxBuilder extends JComboBox {

	/**
	 * Tabla que identifica el tipo de datos e interfaz
	 */
	final String table;

	/**
	 * Columna a recuperar los datos de la tabla
	 */
	final String column;

	/**
	 * Interfaz de entrada de datos dinamica
	 */
	QuickAdd frame;

	/**
	 * Crea un JComboBox adaptado con funcionalidades especiales. Inicialmente
	 * es la de agregar elementos rapidamente
	 * 
	 * @param tableData
	 *            Tabla a la que pertenece el dato
	 * @param columnData
	 *            Columna a mostrar
	 * @since 1.0
	 */
	public QuickJComboBoxBuilder(String tableData, String columnData) {
		table = tableData;
		column = columnData;

		ArrayList<String> data = Core.getAllColumnData(table, column);
		data.add(" Agregar...");
		setModel(new DefaultComboBoxModel(data.toArray()));

		addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getItem() == " Agregar..."
						&& e.getStateChange() == ItemEvent.SELECTED) {
					try {
						frame = (QuickAdd) Class.forName(
								"com.nova.shared.quick.QuickAdd_" + table)
								.newInstance();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					String insertedText = frame.getInsertedText();
					if (insertedText.length() > 0) {
						removeAllItems();
						ArrayList<String> datos = Core.getAllColumnData(table,
								column);
						setModel(new DefaultComboBoxModel(datos.toArray()));
						insertItemAt(" Agregar...", datos.size());
						repaint();
						setSelectedItem(insertedText);
					} else
						setSelectedIndex(0);
				}
			}
		});
	}
}
// FIXME Terminar aqui!!!
/*
 * final BasicComboBoxEditor editor = new BasicComboBoxEditor();
 * editor.addActionListener(new ActionListener() { public void
 * actionPerformed(ActionEvent e) { //System.out.println(editor.getItem()); }
 * }); final JTextField component = (JTextField) editor.getEditorComponent();
 * 
 * component.addKeyListener(new KeyAdapter() { public void keyReleased(KeyEvent
 * e) { //removeAllItems(); for (int i = 0; i < datos.length; i++) {
 * if(datos[i].contains(component.getText())) System.out.println(datos[i]);
 * //add(new JTextField(datos[i])); } showPopup(); } });
 * 
 * setEditor(editor); setEditable(true);
 */