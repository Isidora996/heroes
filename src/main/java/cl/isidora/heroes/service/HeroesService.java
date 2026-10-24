package cl.isidora.heroes.service;

import cl.isidora.heroes.dto.HeroeDto;

import java.util.ArrayList;

public class HeroesService implements IHeroesService {
    private ArrayList<HeroeDto> heroes;

    public HeroesService() {
        this.heroes = new ArrayList<>();
    }

    @Override
    public ArrayList<HeroeDto> obtenerHeroes() {
        return this.heroes;
    }

    @Override
    public void crearHeroe(HeroeDto heroe) {
        this.heroes.add(heroe);
    }

    @Override
    public void actualizarHeroe(HeroeDto heroe) {
        for (HeroeDto h : this.heroes) {
            if (h.getId().equals(heroe.getId())) {
                h.setNombre(heroe.getNombre());
                return;
            }
        }
    }

    @Override
    public void eliminarHeroe(Integer id) {
        for (int i = 0; i < this.heroes.size(); i++) {
            if (this.heroes.get(i).getId().equals(id)) {
                this.heroes.remove(i);
                return;
            }
        }
    }
}