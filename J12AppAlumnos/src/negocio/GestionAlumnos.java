package negocio;

import java.util.Set;

import modelo.Alumno;

public interface GestionAlumnos {

	public Set<Alumno> getAlumnos();

	public Set<Alumno> getAprobados();
	
	public Set<Alumno> getSB();
	
	public Set<Alumno> getNT();
	
	public Set<Alumno> getAP();
	
	public Set<Alumno> getIN();
	
	public double getMediaNotas();

	public double getMediaAprobados();

	

	public Set<Alumno> getAlumnosByNombre(String nombre);
	
	public Alumno getAlumnoByDni(String dni);
	
	public void save(Alumno alumno);
	
	public void delete(Alumno alumno);

}
