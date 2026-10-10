package dao;

import modelo.Prestamo;

import java.util.List;

/**
 * Define las operaciones de acceso a datos
 * para los préstamos.
 */
public interface PrestamoDAO {

    boolean create(Prestamo prestamo);

    List<Prestamo> readAll();

    boolean update(Prestamo prestamo);

    boolean delete(int id);
}