package com.nova.look;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.UIManager;

import com.nova.core.Core;
import com.nova.parameter.GlobalConstants;

/**
 * Clase que representa la interfaz base para los modulos de la aplicacion.
 * 
 * @author Camilo Nova
 * @version 1.0 Funcionalidad basica
 * @version 1.1 Creacion panel central
 * @version 1.2 Agregar y quitar modulos
 */
public class Store extends JFrame {

	/**
	 * Representa la barra izquierda
	 */
	private LeftToolBar izqToolBar;

	/**
	 * Representa la barra derecha
	 */
	private RightToolBar derToolBar;

	/**
	 * Representa el panel central
	 */
	private JPanel centerPanel;

	/**
	 * Representa el panel que contiene la interfaz de un modulo cargado.
	 */
	private Component modulePanel[];

	/**
	 * Representa las posiciones posibles de los componentes
	 */
	private String orientation[] = { "North", "Center", "South" };

	/**
	 * Titulo de la ventana principal
	 */
	private String titulo;

	/**
	 * Crea la interfaz base para los modulos
	 * 
	 * @since 1.0
	 */
	public Store() {
		derToolBar = new RightToolBar(this);
		izqToolBar = new LeftToolBar(this);
		makeCenterPanel();

		add(izqToolBar, BorderLayout.WEST);
		add(derToolBar, BorderLayout.EAST);
		add(centerPanel, BorderLayout.CENTER);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				Core.exit();
				dispose();
				System.exit(0);
			}
		});
		
		titulo = "Store v" + Core.getProperty("VERSION") + " - Nova Inc. - "
				+ Core.getProperty("OWNER");

		setTitle(titulo);
		setSize(800, 575);
		setExtendedState(MAXIMIZED_BOTH);
		setIconImage(GlobalConstants.STORE_ICON_IMAGE);
		setVisible(true);
	}

	/**
	 * Crea el panel central de la interfaz
	 * TODO Hacer que el tamano y la ubicacion de los paneles
	 * sea completamente relativa y proporcional a las dimensiones
	 * de la ventana.
	 * 
	 * @since 1.1
	 */
	private void makeCenterPanel() {
		JPanel supPanel = new JPanel();
		JPanel lowPanel = new JPanel();
		centerPanel = new JPanel();

		supPanel.setBackground(Color.WHITE);
		supPanel.add(new JLabel(GlobalConstants.OWNER_LOGO));
		supPanel.setPreferredSize(new Dimension(600, 200));
		supPanel.setBorder(BorderFactory.createRaisedBevelBorder());

		lowPanel.setBackground(Color.WHITE);
		lowPanel.add(new JLabel("Tip: " + Core.getTip()));
		lowPanel.setPreferredSize(new Dimension(600, 30));
		lowPanel.setBorder(BorderFactory.createEtchedBorder());

		centerPanel.add(supPanel);
		centerPanel.add(makeMidPanel());
		centerPanel.add(lowPanel);
	}

	/**
	 * Crea el componente ubicado en la parte central de la interfaz inicial de
	 * la aplicacion.
	 * 
	 * @return Componente central de la interfaz
	 * @since 1.2
	 */
	private JComponent makeMidPanel() {
		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setText("\nBienvenido " + StoreSession.getUserName()
				+ "\nSu ultimo acceso fue: "
				+ StoreSession.getUserLastTimeIn());
		textArea.setPreferredSize(new Dimension(500, 310));

		return textArea;
	}

	/**
	 * Muestra en el frame el panel que contiene el modulo el cual se recibe por
	 * parametro
	 * 
	 * @param panel
	 *            Modulo a mostrar
	 * @param menuBar
	 *            Menu del modulo
	 * @since 1.2
	 */
	public void setModulo(JPanel panel, JMenuBar menuBar) {
		modulePanel = panel.getComponents();

		remove(centerPanel);
		remove(izqToolBar);
		remove(derToolBar);

		setJMenuBar(menuBar);

		for (int i = 0; i < modulePanel.length; i++)
			add(modulePanel[i], orientation[i]);
		if (modulePanel[1] != null)
			modulePanel[1].transferFocus();

		setVisible(true);
	}

	/**
	 * Elimina el modulo que actualmente se esta mostrando y muestra nuevamente
	 * la interfaz inicial de la aplicacion.
	 * 
	 * @since 1.2
	 */
	public void removeModulo() {
		setJMenuBar(null);
		for (int i = 0; i < modulePanel.length; i++)
			remove(modulePanel[i]);
		modulePanel = null;
		System.gc();

		add(izqToolBar, BorderLayout.WEST);
		add(derToolBar, BorderLayout.EAST);
		add(centerPanel, BorderLayout.CENTER);
		izqToolBar.requestFocus();

		setTitle(titulo);
		setVisible(true);
	}

	/**
	 * Metodo principal de la aplicacion. Ejecuta Store
	 * 
	 * @param args
	 *            Sin uso
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(GlobalConstants.CUSTOMIZED_LOOK_AND_FEEL);
		} catch (Exception e) {
			System.out.println("Ejecutando Look & Feel Alternativo Store GUI Lite");
		}

		StoreSession session = new StoreSession();
		if (StoreSession.getUserID() != null)
			try {
				// TODO Hacer que sea una unica instancia de la clase
				Class.forName(session.getLoader()).newInstance();
			} catch (Exception e) {
				System.exit(1);
			}
		else
			System.exit(0);
	}
}