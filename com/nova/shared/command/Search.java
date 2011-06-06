package com.nova.shared.command;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import com.nova.core.Core;
import com.nova.look.modules.Modules;
import com.nova.parameter.GlobalConstants;
import com.nova.shared.inputCheckers.TextCheckerField;

/**
 * Esta clase muestra una ventana para el ingreso de una cadena a buscar en los
 * datos de la tabla. La cadena a buscar es convertida a minusculas y es buscada
 * en los datos de la tabla tambien convertidos a minusculas.
 * <p>
 * Los datos son almacenados temporalmente en un vector en el cual se puede
 * navegar por los resultados obtenidos.
 * 
 * @author Camilo Nova
 * @version 1.0 Funcionalidad Basica
 * @version 1.1 Descoplamiento total del comando buscar
 */
public class Search extends JDialog {

	/**
	 * Boton de buscar
	 */
	private JButton searchBtn = new JButton(GlobalConstants.SEARCH_ICON_BIG);

	/**
	 * Boton de anterior en la busqueda
	 */
	private JButton backBtn = new JButton(GlobalConstants.PREVIOUS_ICON_BIG);

	/**
	 * Boton de siguiente en la busqueda
	 */
	private JButton nextBtn = new JButton(GlobalConstants.NEXT_ICON_BIG);

	/**
	 * Item de buscar
	 */
	private JMenuItem searchItm = new JMenuItem("Buscar",
			GlobalConstants.SEARCH_ICON_SMALL);

	/**
	 * Item de anterior en la busqueda
	 */
	private JMenuItem backItm = new JMenuItem("Anterior",
			GlobalConstants.PREVIOUS_ICON_SMALL);

	/**
	 * Item de siguiente en la busqueda
	 */
	private JMenuItem nextItm = new JMenuItem("Siguiente",
			GlobalConstants.NEXT_ICON_SMALL);

	/**
	 * Campo donde se indica que buscar
	 */
	TextCheckerField buscarFld;

	/**
	 * Item de mostrar solo resultados
	 */
	private JRadioButton soloResultadosRbtn = new JRadioButton(
			"Mostrar resultados en la tabla");

	/**
	 * Item de resaltar los resultados
	 */
	private JRadioButton resaltarResultadosRbtn = new JRadioButton(
			"Resaltar los resultados", true);

	/**
	 * Grupo de items
	 */
	private ButtonGroup resultadosGrp = new ButtonGroup();

	/**
	 * Representa el boton de buscar
	 */
	private JButton buscarBtn = new JButton("Buscar");

	/**
	 * Representa el boton de cancelar
	 */
	private JButton cancelarBtn = new JButton("Cancelar");

	/**
	 * Representa el modulo propietario
	 */
	@SuppressWarnings("unused")
	private final Modules owner;

	/**
	 * Constructor de la clase, construye la interfaz grafica y ejecuta el
	 * algoritmo de busqueda en los datos si el usuario ha hecho click en el
	 * boton de buscar.
	 * 
	 * @param own
	 *            Modulo Propietario
	 * @since 1.0
	 */
	public Search(Modules own) {
		owner = own;

		buscarBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscar(buscarFld.getText());
				dispose();
			}
		});

		soloResultadosRbtn
				.setToolTipText("Mostrar en la tabla solamente los resultados obtenidos");
		resultadosGrp.add(soloResultadosRbtn);
		resaltarResultadosRbtn
				.setToolTipText("Resaltar en la tabla los resultados encontrados");
		resultadosGrp.add(resaltarResultadosRbtn);

		cancelarBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		buscarFld = new TextCheckerField(buscarBtn, cancelarBtn);

		JPanel upperPanel = new JPanel();
		JPanel middlePanel = new JPanel();
		JPanel lowerPanel = new JPanel();

		buscarFld.setPreferredSize(new Dimension(250, 20));

		upperPanel.setBorder(BorderFactory.createTitledBorder("Buscar por:"));
		upperPanel.add(new JLabel("Texto:"), BorderLayout.WEST);
		upperPanel.add(buscarFld, BorderLayout.CENTER);

		middlePanel.setLayout(new FlowLayout());
		middlePanel.setBorder(BorderFactory
				.createTitledBorder("Mostrar resultados"));
		middlePanel.add(resaltarResultadosRbtn);
		middlePanel.add(soloResultadosRbtn);

		lowerPanel.add(buscarBtn, BorderLayout.WEST);
		lowerPanel.add(cancelarBtn, BorderLayout.EAST);

		add(upperPanel, BorderLayout.NORTH);
		add(middlePanel, BorderLayout.CENTER);
		add(lowerPanel, BorderLayout.SOUTH);
		
		//----------------------------------------------
		searchBtn.setEnabled(false);
		searchItm.setEnabled(false);
		backBtn.setEnabled(false);
		backItm.setEnabled(false);
		nextBtn.setEnabled(false);
		nextItm.setEnabled(false);
		//----------------------------------------------

		pack();
		setTitle("Buscar...");
		setLocation((GlobalConstants.SCREEN_SIZE_WIDTH - getWidth()) / 2,
				(GlobalConstants.SCREEN_SIZE_HEIGHT - getHeight()) / 2);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		setModal(true);
		setAlwaysOnTop(true);
	}

	// TODO Finish this command
	void buscar(String text) {
		String lowerCaseText = text.toLowerCase();
		ResultSet set = Core.search("customer", lowerCaseText);
		try {
			while (set.next())
				System.out.println(set.getString("Nombre"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Metodo que muestra el resultado anterior
	 * 
	 * @since 1.0
	 */
	public void back() {
		/*
		 * String[] s = ((String) lista.previous()).split("-");
		 * owner.setSelectedCell(Integer.parseInt(s[0]),
		 * Integer.parseInt(s[1])); updateButtonsState();
		 */}

	/**
	 * Metodo que muestra el resultado siguiente
	 * 
	 * @since 1.0
	 */
	public void next() {
		/*
		 * String[] s = ((String) lista.next()).split("-");
		 * owner.setSelectedCell(Integer.parseInt(s[0]),
		 * Integer.parseInt(s[1])); updateButtonsState();
		 */}

	/**
	 * Metodo que actualiza el estado de los botones de siguiente y anterior en
	 * la barra de herramientas.
	 * 
	 * @since 1.0
	 */
	@SuppressWarnings("unused")
	private void updateButtonsState() {
		/*
		 * backBtn.setEnabled(lista.hasPrevious());
		 * backItm.setEnabled(lista.hasPrevious());
		 * nextBtn.setEnabled(lista.hasNext());
		 * nextItm.setEnabled(lista.hasNext());
		 */}

	/**
	 * Muestra la ventana de buscar
	 * 
	 * @since 1.1
	 */
	public void executeSearch() {
		buscarFld.setSelectionStart(0);
		buscarFld.setSelectionEnd(buscarFld.getText().length());
		setVisible(true);
	}

	/**
	 * Retorna la instancia del boton de buscar
	 * 
	 * @return Boton de buscar
	 * @since 1.1
	 */
	public JButton getSearchButton() {
		searchBtn.setToolTipText("Buscar");
		searchBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				executeSearch();
			}
		});
		return searchBtn;
	}

	/**
	 * Retorna la instancia del boton de atras
	 * 
	 * @return Boton de atras
	 * @since 1.1
	 */
	public JButton getBackButton() {
		backBtn.setToolTipText("Resultado Anterior");
		backBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				back();
			}
		});
		backBtn.setEnabled(false);
		return backBtn;
	}

	/**
	 * Retorna la instancia del boton de siguiente
	 * 
	 * @return Boton de siguiente
	 * @since 1.1
	 */
	public JButton getNextButton() {
		nextBtn.setToolTipText("Resultado Siguiente");
		nextBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				next();
			}
		});
		nextBtn.setEnabled(false);

		return nextBtn;
	}

	/**
	 * Retorna la instancia del item de buscar
	 * 
	 * @return Item de buscar
	 * @since 1.1
	 */
	public JMenuItem getSearchItem() {
		searchItm.setToolTipText("Buscar");
		searchItm.setMnemonic('b');
		searchItm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				executeSearch();
			}
		});
		return searchItm;
	}

	/**
	 * Retorna la instancia del item de anterior
	 * 
	 * @return Item de anterior
	 * @since 1.1
	 */
	public JMenuItem getBackItem() {
		backItm.setToolTipText("Resultado Anterior");
		backItm.setMnemonic('a');
		backItm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				back();
			}
		});
		backItm.setEnabled(false);
		return backItm;
	}

	/**
	 * Retorna la instancia del item de siguiente
	 * 
	 * @return Item de siguiente
	 * @since 1.1
	 */
	public JMenuItem getNextItem() {
		nextItm.setToolTipText("Resultado Siguiente");
		nextItm.setMnemonic('s');
		nextItm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				next();
			}
		});
		nextItm.setEnabled(false);
		return nextItm;
	}
}
