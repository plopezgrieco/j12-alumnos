package tests;

import java.util.Collection;

import modelo.Alumno;
import persistencia.AlumnoDao;
import persistencia.AlumnoDaoMem;

public class TestAlumnoDao {
	public static void main(String[] args) {
		
		AlumnoDao dao = new AlumnoDaoMem();
		muestraAlumnos(dao.findAll());
		muestraAlumnos(dao.findAllByNombre("pe"));
		
		dao.deleteById("86748384C");
		dao.deleteById("82898682N");
		dao.deleteById("37137990M");
		muestraAlumnos(dao.findAll());
		System.out.println(dao.findById("82225055D"));
		System.out.println(dao.findById("82898682N"));
	}
	
	public static void muestraAlumnos(Collection<Alumno> alumnos) {
		for (Alumno alumno : alumnos) {
			System.out.println(alumno);
		}
		System.out.println("-------------");
		System.out.println("Cant: " + alumnos.size());
		System.out.println("-------------");
	}
}
