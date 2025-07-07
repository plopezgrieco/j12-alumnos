package persistencia;

import java.util.Set;

import modelo.Alumno;

public interface AlumnoDao {
	
	void delete(Alumno alumno);
	
	void deleteById(String dni);
	
	Set<Alumno> findAll();

	Set<Alumno> findAllByNombre(String nombre);
	
	Alumno findById(String dni);
	
	void save(Alumno alumno);
	
}
