package com.nova.look.modules;

import javax.swing.JFrame;
import javax.swing.JMenuBar;

import com.nova.shared.command.Command;

/**
 * Esta inteface define los metodos que todos los modulos deben implementar para
 * su correcto acoplamiento a la aplicacion.
 * 
 * @author Camilo Nova
 * @version 1.0 Funcionalidad Basica
 * @version 1.1 Actualizar Datos, getTableSelectedID(),
 *          setSelectedCell(),getTableData(), setTableData()
 * @version 1.2 Comando unico para cada modulo
 * @version 1.3 Submodulos
 */
public interface Modules {

	/**
	 * Retorna el identificador del modulo para relacionarlo con la tabla en la
	 * base de datos
	 * 
	 * @return Nombre tabla relacionada al modulo
	 * @since 1.0
	 */
	public String getIdentifier();

	/**
	 * Retorna el menu del modulo.
	 * 
	 * @return JMenuBar del modulo
	 * @since 1.0
	 */
	public JMenuBar getMenuBar();

	/**
	 * Muestra el texto en la barra de estado
	 * 
	 * @param text
	 *            Texto a mostrar
	 * @since 1.0
	 */
	public void setStatusBarText(String text);

	/**
	 * Le informa al panel principal que elimine el contenido de este modulo y
	 * lo libere de la memoria.
	 * 
	 * @since 1.0
	 */
	public void exit();

	/**
	 * Actualiza los datos de la tabla
	 * 
	 * @param message
	 *            Mensaje a mostrar
	 * @since 1.1
	 */
	public void updateDataTable(String message);

	/**
	 * Retorna el ID de la fila selecionada en la tabla
	 * 
	 * @return ID, null si no hay nada seleccionado
	 * @since 1.1
	 */
	public String getTableSelectedID();

	/**
	 * Selecciona la celda indentificada por los parametros
	 * 
	 * @param x
	 *            Fila a seleccionar
	 * @param y
	 *            Columna a seleccionar
	 * @since 1.1
	 */
	void setSelectedCell(int x, int y);

	/**
	 * Retorna el comando del modulo
	 * 
	 * @return Comando del modulo
	 * @since 1.2
	 */
	public Command getCommand();

	/**
	 * Retorna el frame principal para ser usado como parent en los submodulos
	 * 
	 * @return Frame principal
	 * @since 1.3
	 */
	public JFrame getMainFrame();
}