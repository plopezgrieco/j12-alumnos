package vista.swing;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Set;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import modelo.Alumno;
import negocio.GestionAlumnos;
import negocio.GestionAlumnosImpl;

public class VInicial extends JFrame {
	private static final long serialVersionUID = 1L;
	private JTextField tBuscaAlumno;
	JButton bBuscar;
	private JTextField tBuscaPorDNI;
	private JButton bBuscarDNI;
	private JLabel cantidadAlumnos;
	private JButton bAgregar;
	private JButton bEliminar;
	private JButton bEditar;
	private JButton bConsultar;
	private JButton bSalir;
	private JLabel nombre;
	private JLabel dni;
	JRadioButton[] rbFiltroNotas;
	private JRadioButton rbOculto;
	private final int TODOS = 0;
	private final int APROBADOS = 1;
	private final int SB = 2;
	private final int NT = 3;
	private final int AP = 4;
	private final int IN = 5;
	private ButtonGroup rbgResultados;
	private ArrayList<Alumno> listaAlumnos;
	private Font labelFont = new Font(Font.DIALOG, Font.BOLD, 16);
	private Font textFont = new Font(Font.DIALOG_INPUT, Font.ITALIC, 16);
	private Font listaFont = new Font(Font.DIALOG, Font.PLAIN, 16);
	JTable tabla;
	private DefaultTableModel modelo;
	private Object[][] datosTabla;// = new String[0][0];
	private String[] titulosTabla = { "Primer Apellido", "Segundo Apellido", "Nombre",
			"DNI/DOC", "Nota" };
	private Alumno alumnoActual;
	private int AGREGAR = 0;
	private int EDITAR = 1;
	private int CONSULTAR = 2;
	private int ELIMINAR = 3;
	int filaActualTabla;
	
	private GestionAlumnos negocio = new GestionAlumnosImpl();

	public VInicial() {
		super("Gestión de Alumnos");
		setResizable(false);
		int ancho = 850;
		setBounds(100, 10, ancho, 746);
		getContentPane().setLayout(null);

		ManejaEventos manejador = new ManejaEventos();

		armaMenu();
		
		int x1 = 10;
		int y1 =3;
		nombre = new JLabel("Alumno: ");
		nombre.setBounds(x1, y1, 80, 30);
		nombre.setFont(labelFont);
		add(nombre);

		dni = new JLabel("Documento: ");
		dni.setBounds(x1+490, y1, 140, 30);
		dni.setFont(labelFont);
		add(dni);

		tBuscaAlumno = new JTextField();
		tBuscaAlumno.setBounds(x1, y1+30, 350, 30);
		tBuscaAlumno.setFont(textFont);
		add(tBuscaAlumno);
		tBuscaAlumno.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				bBuscar.doClick();
			}
		});
		tBuscaAlumno.addKeyListener(manejador);

		bBuscar = new JButton("Buscar");
		bBuscar.setBounds(x1+360, y1+30, 80, 30);
		add(bBuscar);
		bBuscar.addActionListener(manejador);
		bBuscar.setEnabled(false);
		
		tBuscaPorDNI = new JTextField();
		tBuscaPorDNI.setBounds(x1+490, y1+30, 240, 30);
		tBuscaPorDNI.setFont(textFont);
		add(tBuscaPorDNI);
		tBuscaPorDNI.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				bBuscarDNI.doClick();
			}
		});
		tBuscaPorDNI.addKeyListener(manejador);

		bBuscarDNI = new JButton("Buscar");
		bBuscarDNI.setBounds(x1+737, y1+30, 80, 30);
		add(bBuscarDNI);
		bBuscarDNI.addActionListener(manejador);
		bBuscarDNI.setEnabled(false);
		
		rbFiltroNotas = new JRadioButton[6];
		rbFiltroNotas[TODOS] = new JRadioButton("Todos");
		rbFiltroNotas[APROBADOS] = new JRadioButton("Aprobados");
		rbFiltroNotas[SB] = new JRadioButton("SB");
		rbFiltroNotas[NT] = new JRadioButton("NT");
		rbFiltroNotas[AP] = new JRadioButton("AP");
		rbFiltroNotas[IN] = new JRadioButton("IN");
		
		rbOculto = new JRadioButton();
		rbOculto.setSelected(true);
		rbOculto.setVisible(false);
		
		rbgResultados = new ButtonGroup();
		rbgResultados.add(rbOculto);
		int xRadio = x1;
		int tamRadio;
		for (int i = 0; i< rbFiltroNotas.length; i++) {
			if(i < 2)
				tamRadio = 90;
			else
				tamRadio = 45;
			rbFiltroNotas[i].setBounds(xRadio, y1+68, tamRadio, 15);
			rbgResultados.add(rbFiltroNotas[i]);
			add(rbFiltroNotas[i]);
			xRadio += (tamRadio + 10);
			rbFiltroNotas[i].addActionListener(manejador);
			
		}
		
		modelo = new DefaultTableModel(datosTabla, titulosTabla);
		tabla = new JTable(modelo);
		tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabla.setFont(this.listaFont);
		tabla.addMouseListener(manejador);
		tabla.addKeyListener(manejador);
		tabla.setAutoCreateRowSorter(true);
		tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tablaSetEditableFalse();
		actulizaTabla();

		JScrollPane barras = new JScrollPane(tabla,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		barras.setBounds(10, 90, ancho-30, 530);
		add(barras);

		bAgregar = new JButton("Agregar");
		bAgregar.setBounds(10, 10, 90, 30);
		bAgregar.addActionListener(manejador);

		bEditar = new JButton("Editar");
		bEditar.setBounds(110, 10, 90, 30);
		bEditar.addActionListener(manejador);
		bEditar.setEnabled(false);

		bEliminar = new JButton("Eliminar");
		bEliminar.setBounds(210, 10, 90, 30);
		bEliminar.addActionListener(manejador);
		bEliminar.setEnabled(false);

		bConsultar = new JButton("Detalle");
		bConsultar.setBounds(310, 10, 90, 30);
		bConsultar.addActionListener(manejador);
		bConsultar.setEnabled(false);

		bSalir = new JButton("Salir");
		bSalir.setBounds(737, 10, 80, 30);
		bSalir.addActionListener(manejador);

		cantidadAlumnos = new JLabel();
		cantidadAlumnos.setBounds(480, 10, 250, 30);
		cantidadAlumnos.setFont(labelFont);

		JPanel panelBotones = new JPanel();
		panelBotones.setBounds(10, 630, 890, 50);
		panelBotones.setLayout(null);
		panelBotones.setBackground(Color.LIGHT_GRAY);
		panelBotones.add(bAgregar);
		panelBotones.add(bEditar);
		panelBotones.add(bEliminar);
		panelBotones.add(bConsultar);
		panelBotones.add(bSalir);
		panelBotones.add(cantidadAlumnos);

		add(panelBotones);
		setVisible(true);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent arg0) {
				bSalir.doClick();
			}
		});

	}
	
	private void armaMenu(){
		JMenu menuArchivo = new JMenu( "Archivo" ); 
		menuArchivo.setMnemonic( 'A' ); 

		JMenuItem opciones = new JMenuItem( "Opciones..." );
		opciones.setMnemonic( 'O' );
		menuArchivo.add(opciones);
		opciones.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				opciones();
			}
		});

		JMenuItem elementoAcercaDe = new JMenuItem( "Acerca de..." );
		elementoAcercaDe.setMnemonic( 'c' );
		menuArchivo.add( elementoAcercaDe );
		elementoAcercaDe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null, "Gestion Alumnos 1.0\n");				
			}
		});
		
		JMenuItem elementoSalir = new JMenuItem("Salir");
		elementoSalir.setMnemonic('S');
		menuArchivo.add(elementoSalir);
		elementoSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				bSalir.doClick();
			}
		});
		
		JMenuBar barra = new JMenuBar();
		setJMenuBar( barra );
		barra.add( menuArchivo );
		
		
	}
	
	private void tablaSetEditableFalse(){
		for (int c = 0; c < tabla.getColumnCount(); c++)
		{
		    Class<?> col_class = tabla.getColumnClass(c);
		    tabla.setDefaultEditor(col_class, null);        // remove editor
		}
	}

	private void actulizaTabla() {
		DefaultTableCellRenderer alDerecha = new DefaultTableCellRenderer();
		alDerecha.setHorizontalAlignment(SwingConstants.RIGHT);
		DefaultTableCellRenderer alCentrado = new DefaultTableCellRenderer();
		alCentrado.setHorizontalAlignment(SwingConstants.CENTER);

		modelo.setDataVector(datosTabla, titulosTabla);
		tabla.getColumn("Primer Apellido").setMinWidth(200);
		tabla.getColumn("Segundo Apellido").setMinWidth(200);
		tabla.getColumn("Nombre").setMinWidth(200);
		tabla.getColumn("DNI/DOC").setMinWidth(120);
		tabla.getColumn("Nota").setMinWidth(80);

		tabla.getColumn("DNI/DOC").setCellRenderer(alCentrado);
		tabla.getColumn("Nota").setCellRenderer(alDerecha);
	}
	
	void cargaTabla(){
		if (datosTabla.length != 0) {
			alumnoActual = listaAlumnos.get(tabla
				.convertRowIndexToModel(tabla.getSelectedRow()));
		}else{
			alumnoActual = null;
			bEditar.setEnabled(false);
			bEliminar.setEnabled(false);
			bConsultar.setEnabled(false);
			tBuscaAlumno.requestFocus();

		}
	}
	
	private void opciones(){
		new VentanaOpciones(this);
	}
	
	private Set<Alumno> filtraAlumnos(){
		Set<Alumno> resu;
		int filtro = -1;
		for (int i = 0; i < rbFiltroNotas.length; i++) {
			if (rbFiltroNotas[i].isSelected()) {
				filtro = i;
			}
		}
		switch(filtro) {
		case TODOS:
			resu = negocio.getAlumnos();
			break;
		case APROBADOS:
			resu = negocio.getAprobados();
			break;
		case SB:
			resu = negocio.getSB();
			break;
		case NT:
			resu = negocio.getNT();
			break;
		case AP:
			resu = negocio.getAP();
			break;
		case IN:
			resu = negocio.getIN();
			break;
		default:
			resu = negocio.getAlumnos();
		}
		return resu;
	}

	private class ManejaEventos extends MouseAdapter implements ActionListener,
			KeyListener, ItemListener {

		public void keyPressed(KeyEvent arg0) {
		}

		public void keyReleased(KeyEvent eve) {
			if (eve.getSource() == tBuscaAlumno || eve.getSource() == tBuscaPorDNI) {
				rbOculto.setSelected(true);
				if (((JTextField)eve.getSource()).getText().length() == 0) {
					for (JRadioButton radio : rbFiltroNotas) {
						radio.setEnabled(true);
					}
					if (eve.getSource() == tBuscaAlumno) {
						bBuscar.setEnabled(false);
						tBuscaPorDNI.setEnabled(true);
					} else {
						bBuscarDNI.setEnabled(false);
						tBuscaAlumno.setEnabled(true);
					}
				}else {
					for (JRadioButton radio : rbFiltroNotas) {
						radio.setEnabled(false);
					}
					if (eve.getSource() == tBuscaAlumno) {
						bBuscar.setEnabled(true);
						tBuscaPorDNI.setEnabled(false);
					} else {
						bBuscarDNI.setEnabled(true);
						tBuscaAlumno.setEnabled(false);
					}
				}
			}
		}
		
		public void mouseClicked(MouseEvent eve) {
			if (eve.getSource() == tabla && eve.getClickCount() == 2) {
				bConsultar.doClick();
			}
		}

		public void mousePressed(MouseEvent eve) {
			if (eve.getSource() == tabla) {
				alumnoActual = listaAlumnos.get(tabla
						.convertRowIndexToModel(tabla.getSelectedRow()));
				bEditar.setEnabled(true);
				bEliminar.setEnabled(true);
				bConsultar.setEnabled(true);
				bConsultar.requestFocus();
			}
		}
		
		public void keyTyped(KeyEvent eve) {
			if (eve.getSource() == tabla) {
				if (eve.getKeyChar() == KeyEvent.VK_ENTER) {
					alumnoActual = listaAlumnos.get(tabla
							.convertRowIndexToModel(tabla.getSelectedRow()));
					bEditar.setEnabled(true);
					bEliminar.setEnabled(true);
					bConsultar.setEnabled(true);
					bConsultar.requestFocus();
				} else if (eve.getKeyChar() == KeyEvent.VK_TAB) {
					bEditar.setEnabled(true);
					bEditar.requestFocus();
					bEliminar.setEnabled(true);
					bEliminar.requestFocus();
					bConsultar.setEnabled(true);
					bConsultar.requestFocus();
				}
			}
		}

		private Object[][] alumnosToMatriz() {
			ArrayList<Alumno> lista = listaAlumnos;
			Object[] filas = new Object[lista.size()];
			Object[][] resultado;
			resultado = new Object[filas.length][];
			for (int i = 0; i < filas.length; i++) {
				resultado[i] = alumnoToArray(lista.get(i));
			}
			return resultado;
		}
		
		private Object[] alumnoToArray(Alumno alu){
			Object[] res = null;
			if (alu != null) {
				res = new Object[5];
				res[0] = alu.getApellido1();
				res[1] = alu.getApellido2();
				res[2] = alu.getNombre();
				res[3] = alu.getDni();
				res[4] = String.format("%5.2f", alu.getNota());
			}
			return res;
		}

		public void actionPerformed(ActionEvent ev) {
			if (ev.getSource() == bBuscar || ev.getSource() == bBuscarDNI) {
				listaAlumnos = new ArrayList<>();
				if(ev.getSource() == bBuscar)
					if(!tBuscaAlumno.getText().equals("")){
						listaAlumnos.addAll(negocio.getAlumnosByNombre(tBuscaAlumno.getText()));
					}else{
						listaAlumnos.addAll(filtraAlumnos());
					}
				else {
					Alumno buscado = negocio.getAlumnoByDni(tBuscaPorDNI.getText());
					if (buscado != null)
						listaAlumnos.add(buscado);
				}
				datosTabla = alumnosToMatriz();
				cantidadAlumnos.setText("Encontrados: " + datosTabla.length + " alumnos");
				actulizaTabla();
				if (datosTabla.length == 0) {
					bEditar.setEnabled(false);
					bEliminar.setEnabled(false);
					bConsultar.setEnabled(false);
					;
				}
			}
			if (ev.getSource() == bAgregar) {
				new VentanaEdicion(negocio, alumnoActual, AGREGAR, VInicial.this);
			}
			if (ev.getSource() == bEditar) {
				filaActualTabla = tabla.getSelectedRow();
				new VentanaEdicion(negocio, alumnoActual, EDITAR, VInicial.this);
			}
			if (ev.getSource() == bEliminar) {
				filaActualTabla = tabla.getSelectedRow();
				new VentanaEdicion(negocio, alumnoActual, ELIMINAR, VInicial.this);
			}
			if (ev.getSource() == bConsultar) {
				filaActualTabla = tabla.getSelectedRow();
				new VentanaEdicion(negocio, alumnoActual, CONSULTAR, VInicial.this);
			}
			if (ev.getSource() == bSalir) {
				if (JOptionPane.showConfirmDialog(null,
						"Desea salir de la aplicación", "Salir de la App", 2) == 0){
					System.exit(0);
				}
			}
			if (ev.getSource().getClass() == JRadioButton.class) {
				if (ev.getSource() != rbOculto) {
					listaAlumnos = new ArrayList<Alumno>(filtraAlumnos());
					datosTabla = alumnosToMatriz();
					cantidadAlumnos.setText("Encontrados: " + datosTabla.length + " alumnos");
					actulizaTabla();
				}
			}
		}

		@Override
		public void itemStateChanged(ItemEvent e) {
			System.out.println(((JRadioButton)e.getSource()).getText());
			if (e.getSource() != rbOculto) {
				listaAlumnos = new ArrayList<Alumno>(filtraAlumnos());
				datosTabla = alumnosToMatriz();
				cantidadAlumnos.setText("Encontrados: " + datosTabla.length + " alumnos");
				actulizaTabla();
			}
		}
	}
}
