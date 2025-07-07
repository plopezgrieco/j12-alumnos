package negocio;

import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

import modelo.Alumno;
import persistencia.AlumnoDao;
import persistencia.AlumnoDaoMem;

public class GestionAlumnosImpl implements GestionAlumnos {

	private AlumnoDao dao;
	
	public GestionAlumnosImpl() {
		dao = new AlumnoDaoMem();
	}
	
	@Override
	public Set<Alumno> getAprobados() {
		Set<Alumno> aprobados = new TreeSet<Alumno>(Alumno.ordenAlfabetico());
		for (Alumno alumno : dao.findAll()) {
			if (alumno.getNota() >= 5)
				aprobados.add(alumno);
		}
		return aprobados;
	}

	private double getMediaNotasFromCollection(Collection<Alumno> alumnos) {
		double suma = 0;
		for (Alumno alumno : alumnos)
			suma += alumno.getNota();
		return suma / alumnos.size();
	}
	
	@Override
	public double getMediaNotas() {
		return getMediaNotasFromCollection(dao.findAll());
	}

	@Override
	public double getMediaAprobados() {
		return getMediaNotasFromCollection(getAprobados());
	}

	@Override
	public Set<Alumno> getAlumnos() {
		return dao.findAll();
	}

	@Override
	public Set<Alumno> getAlumnosByNombre(String nombre) {
		return dao.findAllByNombre(nombre);
	}

	@Override
	public Alumno getAlumnoByDni(String dni) {
		return dao.findById(dni);
	}

	@Override
	public void save(Alumno alumno) {
		dao.save(alumno);
	}

	@Override
	public void delete(Alumno alumno) {
		dao.delete(alumno);
	}

	@Override
	public Set<Alumno> getSB() {
		Set<Alumno> resu = new TreeSet<Alumno>(Alumno.ordenAlfabetico());
		for (Alumno alumno : dao.findAll()) {
			if (alumno.getNota() >= 9)
				resu.add(alumno);
		}
		return resu;
	}

	@Override
	public Set<Alumno> getNT() {
		Set<Alumno> resu = new TreeSet<Alumno>(Alumno.ordenAlfabetico());
		for (Alumno alumno : dao.findAll()) {
			if (alumno.getNota() >= 8 && alumno.getNota() < 9)
				resu.add(alumno);
		}
		return resu;
	}

	@Override
	public Set<Alumno> getAP() {
		Set<Alumno> resu = new TreeSet<Alumno>(Alumno.ordenAlfabetico());
		for (Alumno alumno : dao.findAll()) {
			if (alumno.getNota() >= 5 && alumno.getNota() < 8)
				resu.add(alumno);
		}
		return resu;
	}

	@Override
	public Set<Alumno> getIN() {
		Set<Alumno> resu = new TreeSet<Alumno>(Alumno.ordenAlfabetico());
		for (Alumno alumno : dao.findAll()) {
			if (alumno.getNota() < 5)
				resu.add(alumno);
		}
		return resu;
	}

}
