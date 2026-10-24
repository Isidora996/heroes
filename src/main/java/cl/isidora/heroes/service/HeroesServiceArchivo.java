package cl.isidora.heroes.service;

import cl.isidora.heroes.dao.HeroesDao;
import cl.isidora.heroes.dto.HeroeDto;

import java.util.ArrayList;

public class HeroesServiceArchivo implements IHeroesService {
    private HeroesDao heroesDao;

    public HeroesServiceArchivo() {
        this.heroesDao = new HeroesDao();
    }

    @Override
    public ArrayList<HeroeDto> obtenerHeroes() {
        return this.heroesDao.obtenerHeroes();
    }

    @Override
    public void crearHeroe(HeroeDto heroe) {
        ArrayList<HeroeDto> listado = this.heroesDao.obtenerHeroes();
        listado.add(heroe);
        this.heroesDao.guardarHeroes(listado);
    }

    @Override
    public void actualizarHeroe(HeroeDto heroe) {
        ArrayList<HeroeDto> listado = this.heroesDao.obtenerHeroes();
        for (HeroeDto h : listado) {
            if (h.getId().equals(heroe.getId())) {
                h.setNombre(heroe.getNombre());
                break;
            }
        }
        this.heroesDao.guardarHeroes(listado);
    }

    @Override
    public void eliminarHeroe(Integer id) {
        ArrayList<HeroeDto> listado = this.heroesDao.obtenerHeroes();
        for (int i = 0; i < listado.size(); i++) {
            if (listado.get(i).getId().equals(id)) {
                listado.remove(i);
                break;
            }
        }
        this.heroesDao.guardarHeroes(listado);
    }
}