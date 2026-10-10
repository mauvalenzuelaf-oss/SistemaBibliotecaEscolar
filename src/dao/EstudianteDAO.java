package dao;

import modelo.Estudiante;

import java.util.List;

/**
 * Define las operaciones de acceso a datos
 * para los estudiantes.
 */
public interface EstudianteDAO {

    boolean create(Estudiante estudiante);

    List<Estudiante> readAll();

    boolean update(Estudiante estudiante);

    boolean delete(int id);
}