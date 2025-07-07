package tmp;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import modelo.Alumno;
import persistencia.AlumnoDao;
import persistencia.AlumnoDaoMem;

public class GeneraCSV {
	public static void main(String[] args) {
		AlumnoDao dao = new AlumnoDaoMem();
		dao.findAll().forEach(System.out::println);
		String linea;
		try(BufferedWriter bw = new BufferedWriter(new FileWriter("alumnos.csv"))){
			for (Alumno alumno : dao.findAll()) {
				linea = alumno.getApellido1() + "," + alumno.getApellido2() + "," +
						alumno.getNombre() + "," + alumno.getDni() + "," + alumno.getNota() + "\n";
				bw.write(linea);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
