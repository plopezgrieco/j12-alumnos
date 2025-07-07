package tests;

import java.util.HashMap;
import java.util.Map;

import modelo.Alumno;

public class TestMap {
	public static void main(String[] args) {
		Map<String, Alumno> map = new HashMap<String, Alumno>();
		
		Alumno nuevo = new Alumno("Pablo", "Lopez", "Grieco", "X5672432B", 7.5);
		
		map.put(nuevo.getDni(), nuevo);
		for (Alumno alumno : map.values()) {
			System.out.println(alumno);
		}
		
		Alumno tmp = nuevo.clone();
		System.out.println(tmp);
		
		tmp.setNota(5);
		map.put(tmp.getDni(), tmp);
		
		for (Alumno alumno : map.values()) {
			System.out.println(alumno);
		}
	}
}
