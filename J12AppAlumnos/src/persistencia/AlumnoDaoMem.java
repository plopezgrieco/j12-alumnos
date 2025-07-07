package persistencia;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import modelo.Alumno;
import util.CargaAlumnos;

public class AlumnoDaoMem implements AlumnoDao {
	
	private Map<String, Alumno> almacen;
	
	public AlumnoDaoMem() {
		almacen = new HashMap<String, Alumno>();
		Alumno[] alArray = CargaAlumnos.getArrayAlumnos();
		for (Alumno alumno : alArray) {
			almacen.put(alumno.getDni(), alumno);
		}
	}

	@Override
	public void delete(Alumno alumno) {
		deleteById(alumno.getDni());
	}

	@Override
	public void deleteById(String dni) {
		almacen.remove(dni.toUpperCase());
	}

	@Override
	public Set<Alumno> findAll() {
		Set<Alumno> resu = new TreeSet<Alumno>(Alumno.ordenAlfabetico());
		resu.addAll(almacen.values());
		return resu;
	}

	@Override
	public Set<Alumno> findAllByNombre(String nombre) {
		Set<Alumno> resu = new TreeSet<Alumno>(Alumno.ordenAlfabetico());
		for (Alumno alumno : almacen.values()) {
			if (alumno.getApellido1().toLowerCase().contains(nombre.toLowerCase()) || 
					alumno.getApellido2().toLowerCase().contains(nombre.toLowerCase()) || 
					alumno.getNombre().toLowerCase().contains(nombre.toLowerCase())) {
				resu.add(alumno);
			}
		}
		return resu;
	}

	@Override
	public Alumno findById(String dni) {
		return almacen.get(dni.toUpperCase());
	}

	@Override
	public void save(Alumno alumno) {
		alumno.setDni(alumno.getDni().toUpperCase());
		almacen.put(alumno.getDni(), alumno);
	}
}
