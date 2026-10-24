package cl.isidora.heroes.service;

import cl.isidora.heroes.dto.HeroeDto;

import java.util.ArrayList;

public interface IHeroesService {
    ArrayList<HeroeDto> obtenerHeroes();
    void crearHeroe(HeroeDto heroe);
    void actualizarHeroe(HeroeDto heroe);
    void eliminarHeroe(Integer id);
}