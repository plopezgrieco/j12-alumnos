package vista;

import java.util.Collection;
import java.util.Scanner;

import modelo.Alumno;
import negocio.GestionAlumnos;
import negocio.GestionAlumnosImpl;

public class MenuPrincipal {

	private GestionAlumnos neg;
	private Scanner teclado = new Scanner(System.in);
	
	public MenuPrincipal() {
		neg = new GestionAlumnosImpl();
		menu();
	}
	
	private void menu() {
		boolean salir = false;
		int opcion;
		do {
			System.out.println("Gestión de Alumnos");
			System.out.println("------------------");
			System.out.println("Menú Principal");
			System.out.println("1 - Agregar Alumno");
			System.out.println("2 - Modificar Alumno");
			System.out.println("3 - Listar Alumnos");
			System.out.println("4 - Listar Alumnos Aprobados");
			System.out.println("5 - Buscar Alumno");
			System.out.println("6 - Eliminar Alumno");
			System.out.println("0 - Salir");
			System.out.print("Opcion: ");
			opcion = teclado.nextInt();
			teclado.nextLine();
			
			switch(opcion) {
			case 1:
				agregaAlumno();
				break;
			case 2:
				editarAlumno();
				break;
			case 3:
				listarAlumnos();
				break;
			case 4:
				listarAlumnosAprobados();
				break;
			case 5:
				buscarAlumno();
				break;
			case 6:
				eliminarAlumno();
				break;
			case 0:
				salir = true;
				break;
			}
		}while(!salir);
	}
	
	//Opcion 1
	private void agregaAlumno() {
		System.out.println("\nAGREGAR NUEVO ALUMNO");
		System.out.print("DNI: ");
		String dni = teclado.nextLine().toUpperCase();
		if (neg.getAlumnoByDni(dni) != null) {
			System.out.println("El Alumno ya existe!!");
			return;
		}
		System.out.print("Nombre: ");
		String nombre = teclado.nextLine();
		System.out.print("Primer Apellido: ");
		String ape1 = teclado.nextLine();
		System.out.print("Segundo Apellido: ");
		String ape2 = teclado.nextLine();
		System.out.print("Nota: ");
		double nota = teclado.nextDouble();
		teclado.nextLine();
		
		Alumno nuevo = new Alumno(nombre, ape1, ape2, dni, nota);
		neg.save(nuevo);
		System.out.println("El alumno se ha insertado con exito");
	}
	
	//Opcion 2
	private void editarAlumno() {
		System.out.println("\nEDITAR ALUMNO");
		Alumno buscado = buscarAlumnoPideDni();
		if(buscado == null) {
			System.out.println("El alumno no existe");
			return;
		}
		Alumno tmp = buscado.clone();
		
		boolean salir = false;
		int opcion;
		do {
			System.out.println("1 - Nombre: " + buscado.getNombre());
			System.out.println("2 - Apellido 1: " + buscado.getApellido1());
			System.out.println("3 - Apellido 2: " + buscado.getApellido2());
			System.out.println("4 - Nota: " + buscado.getNota());
			System.out.println("9 - Actualizar cambios!");
			System.out.println("0 - Descartar cambios!");
			
			System.out.print("Opcion: ");
			opcion = teclado.nextInt();
			teclado.nextLine();
			
			switch(opcion) {
			case 1:
				System.out.print("Nombre: ");
				String nombre = teclado.nextLine();
				tmp.setNombre(nombre);
				break;
			case 2:
				System.out.print("Apellido 1: ");
				String ape1 = teclado.nextLine();
				tmp.setApellido1(ape1);
				break;
			case 3:
				System.out.print("Apellido 2: ");
				String ape2 = teclado.nextLine();
				tmp.setApellido2(ape2);
				break;
			case 4:
				System.out.print("Nota: ");
				double nota = teclado.nextDouble();
				teclado.nextLine();
				tmp.setNota(nota);
				break;
			case 9:
				neg.save(tmp);
				salir = true;
				break;
			case 0:
				salir = true;
			}
		} while(!salir);
	}
	
	//opcion 3
	private void listarAlumnos() {
		System.out.println("\nLISTADO DE TODOS LOS ALUMNOS");
		listado(neg.getAlumnos());
	}
	
	//opcion 4
	private void listarAlumnosAprobados() {
		System.out.println("\nLISTADO ALUMNOS APROBADOS");
		listado(neg.getAprobados());
	}
	
	//opcion 5
	private void buscarAlumno() {
		System.out.print("Ingrese alumno a buscar: ");
		String buscar = teclado.nextLine();
		listado(neg.getAlumnosByNombre(buscar));
	}
	
	//Opcion 6
	private void eliminarAlumno() {
		System.out.println("\nELIMINAR ALUMNO");
		Alumno buscado = buscarAlumnoPideDni();
		if (buscado == null) {
			System.out.println("El Alumno no existe!!");
			return;
		}
		neg.delete(buscado);
		System.out.println(buscado.getNombre() + " " + buscado.getApellido1() + 
				" se ha eliminado");
	}
	
	private Alumno buscarAlumnoPideDni() {
		System.out.print("DNI: ");
		String dni = teclado.nextLine().toUpperCase();
		return neg.getAlumnoByDni(dni);
	}
	
	private void listado(Collection<Alumno> alumnos) {
		int ancho = 61;
		System.out.println("=".repeat(ancho));
		System.out.println(String.format("%-26s%-20s%-10s%5s", "APELLIDOS", "NOMBRE", "DNI", "NOTA"));
		System.out.println("-".repeat(ancho));
		for (Alumno alumno : alumnos) {
			System.out.println(alumno);
		}
		System.out.println("-".repeat(ancho));
		System.out.println(String.format("%56s%5d", "Cantidad alumnos: ", alumnos.size()));
		System.out.println("=".repeat(ancho));
		System.out.println();
	}
}
