package vista.swing;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.text.JTextComponent;

import modelo.Alumno;
import negocio.GestionAlumnos;

@SuppressWarnings("serial")
public class VentanaEdicion extends JFrame {

	private JPanel contentPane;
	private JTextField tNombre;
	private JTextField tApellido1;
	private JTextField tApellido2;
	private JTextField tDni;
	private JTextField tNota;
	private JButton btnGrabar;
	private JButton btnCancelar;
	private JPanel panelDatos;
	private JPanel panelBotones;

	private ManejaEventos manejador;
	private boolean edicion = false;
	private GestionAlumnos negocio;
	private VInicial padre;
	private Alumno alumnoActual;
	private int modo;
	private int AGREGAR = 0;
	private int EDITAR = 1;
	private int CONSULTAR = 2;
	private int ELIMINAR = 3;

	public VentanaEdicion(GestionAlumnos negocio, Alumno alumnoActual,
			int modo, VInicial padre) {
		setTitle("Alumnos");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 927, 315);
		setResizable(false);
		setVisible(true);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		this.padre = padre;
		this.modo = modo;
		this.negocio = negocio;
		bloquearPadre();
		manejador = new ManejaEventos();

		panelDatos = new JPanel();
		panelDatos.setBackground(new Color(224, 255, 255));
		panelDatos.setForeground(Color.BLACK);
		panelDatos.setBounds(12, 13, 890, 180);
		contentPane.add(panelDatos);
		panelDatos.setLayout(null);

		if (modo == AGREGAR)
			alumnoActual = new Alumno();
		else
			this.alumnoActual = negocio.getAlumnoByDni(alumnoActual.getDni());

		//fila 1
		int xCol1 = 35;
		int xCol2 = xCol1 + 220;
		int xCol3 = xCol2 + 300;
		int yFila = 13;
		JLabel lblDni = new JLabel("Documento");
		lblDni.setBounds(xCol1, yFila, 150, 16);
		panelDatos.add(lblDni);

		tDni = new JTextField();
		tDni.setBounds(xCol1, yFila + 20, 205, 22);
		panelDatos.add(tDni);
		tDni.setColumns(10);
		tDni.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if(modo == AGREGAR && negocio.getAlumnoByDni(tDni.getText()) != null) {
					JOptionPane.showMessageDialog(null, "Ya existe un alumno con este DNI");
				}
			}
		});
	
		yFila += 50;
		//fila 2
		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(xCol1, yFila, 56, 16);
		panelDatos.add(lblNombre);

		tNombre = new JTextField();
		tNombre.setBounds(xCol1, yFila + 20, 210, 22);
		panelDatos.add(tNombre);
		tNombre.setColumns(10);

		JLabel lblApellido1 = new JLabel("Primer apellido");
		lblApellido1.setBounds(xCol2, yFila, 150, 16);
		panelDatos.add(lblApellido1);

		tApellido1 = new JTextField();
		tApellido1.setBounds(xCol2, yFila + 20, 290, 22);
		panelDatos.add(tApellido1);
		tApellido1.setColumns(10);

		JLabel lblApellido2 = new JLabel("Segundo apellido");
		lblApellido2.setBounds(xCol3, yFila, 150, 16);
		panelDatos.add(lblApellido2);

		tApellido2 = new JTextField();
		tApellido2.setBounds(xCol3, yFila + 20, 290, 22);
		panelDatos.add(tApellido2);
		tApellido2.setColumns(10);

		//fila 3
		yFila += 50;
		JLabel lblNota = new JLabel("Nota");
		lblNota.setBounds(xCol1, yFila, 56, 16);
		panelDatos.add(lblNota);

		tNota = new JTextField();
		tNota.setBounds(xCol1, yFila + 20, 116, 22);
		panelDatos.add(tNota);
		tNota.setColumns(10);

		tNota.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent arg0) {
				try {
					Double.parseDouble(tNota.getText());
				} catch (NumberFormatException e) {
					tNota.setText("0");
				}
			}
		});

		panelBotones = new JPanel();
		panelBotones.setBackground(Color.LIGHT_GRAY);
		panelBotones.setBounds(12, 206, 890, 58);
		panelPreparaListeners();
		contentPane.add(panelBotones);
		panelBotones.setLayout(null);

		if (modo != ELIMINAR) {
			btnGrabar = new JButton("Grabar");
			btnGrabar.setEnabled(false);
		} else {
			btnGrabar = new JButton("Eliminar");
			btnGrabar.setEnabled(true);
		}
		btnGrabar.setBounds(40, 13, 90, 30);
		panelBotones.add(btnGrabar);
		btnGrabar.addActionListener(manejador);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(145, 13, 90, 30);
		panelBotones.add(btnCancelar);
		btnCancelar.addActionListener(manejador);

		panelSetEditable(false);

		if (modo != AGREGAR) {
			cargaDatos();
		}else {
			panelSetEditable(true);
		}
		
		if (modo == EDITAR) {
			panelSetEditable(true);
			tDni.setEditable(false);
		}

		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent arg0) {
				btnCancelar.doClick();;
			}
		});

	}

	private void inicializaPantalla() {
		for (int i = 0; i < panelDatos.getComponentCount(); i++) {
			if (panelDatos.getComponent(i) instanceof JTextComponent) {
				((JTextComponent) panelDatos.getComponent(i)).setText("");
			}
		}
		btnGrabar.setEnabled(false);
	}

	private void cargaDatos() {
		tNombre.setText(alumnoActual.getNombre());
		tApellido1.setText(alumnoActual.getApellido1());
		tApellido2.setText(alumnoActual.getApellido2());
		tDni.setText(alumnoActual.getDni());
		tNota.setText(alumnoActual.getNota() + "");
	}

	private void bloquearPadre() {
		padre.setEnabled(false);
		padre.setFocusableWindowState(false);
	}

	private void salir() {
		padre.setEnabled(true);
		padre.setFocusableWindowState(true);
		padre.rbFiltroNotas[0].doClick();
		if (modo == AGREGAR) {
			padre.tabla.changeSelection(padre.tabla.getRowCount() - 1, 0,
					false, false);
		}
		if (modo == EDITAR || modo == CONSULTAR || modo == ELIMINAR) {
			padre.tabla.changeSelection(padre.filaActualTabla, 0, false, false);
		}
		padre.tabla.requestFocus();
		padre.cargaTabla();
		dispose();
	}

	private void panelSetEditable(boolean editable) {
		for (int i = 0; i < panelDatos.getComponentCount(); i++) {
			if (panelDatos.getComponent(i) instanceof JTextComponent) {
				((JTextComponent) panelDatos.getComponent(i))
						.setEditable(editable);
			}
		}
	}

	private void panelPreparaListeners() {
		for (int i = 0; i < panelDatos.getComponentCount(); i++) {
			if (panelDatos.getComponent(i) instanceof JTextComponent) {
				((JTextComponent) panelDatos.getComponent(i))
						.addKeyListener(manejador);
			}
		}
	}

	private boolean verificaCamposObligatorios() {
		if (tDni.getText().trim().equals("")
				|| tNombre.getText().trim().equals("")
				|| tApellido1.getText().trim().equals("")
				|| tNota.getText().trim().equals("")) {
			return false;
		} else {
			return true;
		}
	}

	private Alumno armaAlumno() {
		if (modo == AGREGAR) {
			alumnoActual = new Alumno();
		}
		alumnoActual.setNombre(tNombre.getText());
		alumnoActual.setApellido1(tApellido1.getText());
		alumnoActual.setApellido2(tApellido2.getText());
		alumnoActual.setDni(tDni.getText().toUpperCase());
		alumnoActual.setNota(Double.parseDouble(tNota.getText()));
		return alumnoActual;
	}
	

	private class ManejaEventos extends MouseAdapter implements KeyListener,
			ActionListener {

		public void keyPressed(KeyEvent ev) {
		}

		public void keyTyped(KeyEvent ev) {
			if (ev.getKeyChar() == KeyEvent.VK_ESCAPE) {
				btnCancelar.doClick();
			}else if (modo != CONSULTAR && modo != ELIMINAR) {
				btnGrabar.setEnabled(true);
				edicion = true;
			}
		}

		public void mouseClicked(MouseEvent eve) {
			
		}

		public void actionPerformed(ActionEvent ev) {
			if (ev.getSource() == btnCancelar) {
				if (!edicion || JOptionPane.showConfirmDialog(null,
						"Desea abandonar la ventana\n Se perderán los cambios realizados",
						"Salir de Contactos", 2) == 0) {
					salir();
				}
			}

			if (ev.getSource() == btnGrabar) {
				if (modo == ELIMINAR) {
					negocio.delete(alumnoActual);
					JOptionPane.showMessageDialog(null,
							alumnoActual.getNombre() + " "
									+ alumnoActual.getApellido1() + " "
									+ alumnoActual.getApellido2() + " "
									+ "Se ha eliminado");
					if (padre.filaActualTabla != 0){
						padre.filaActualTabla--;
					}
					salir();
				} else {
					if (verificaCamposObligatorios()) {
						Alumno nuevo = armaAlumno();
						if (modo == AGREGAR && negocio.getAlumnoByDni(tDni.getText()) != null) {
							JOptionPane.showMessageDialog(null,
									"Ya existe un alumno con ese DNI");
						} else {
							negocio.save(nuevo);
							if (JOptionPane
									.showConfirmDialog(
											null,
											"El Alumno ha sido grabado con Exito\nDesea cargar más alumnos",
											"Salir de Alumnos", 2) == 0) {
								inicializaPantalla();
							} else {
								salir();
							}
						}
						if (modo == EDITAR) {
								negocio.save(alumnoActual);
								JOptionPane
										.showMessageDialog(null,
												"El Alumno ha sido modificado con Exito");
								salir();
						}
					} else {
						JOptionPane.showMessageDialog(null,
								"Debe ingresar un Nombre y Apellido válidos");
					}
				}
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			
		}
	}

}
