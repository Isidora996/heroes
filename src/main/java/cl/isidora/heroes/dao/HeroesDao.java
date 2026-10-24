package cl.isidora.heroes.dao;

import cl.isidora.heroes.dto.HeroeDto;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;

public class HeroesDao {
    private static final String NOMBRE_ARCHIVO = "heroes.json";
    private Path archivo;
    private Gson gson;

    public HeroesDao() {
        this.archivo = Path.of(System.getProperty("user.dir"), NOMBRE_ARCHIVO);
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        System.out.println("Archivo: " + this.archivo);
    }

    public void guardarHeroes(ArrayList<HeroeDto> heroes) {
        try {
            String datos = gson.toJson(heroes);
            Files.writeString(this.archivo, datos);
        } catch (IOException e) {
            System.out.println("Error al guardar: " + e.getMessage());
        }
    }

    public ArrayList<HeroeDto> obtenerHeroes() {
        ArrayList<HeroeDto> listado = new ArrayList<>();

        if (!Files.exists(this.archivo)) {
            return listado;
        }
        try {
            String datos = Files.readString(this.archivo);
            HeroeDto[] arreglo = gson.fromJson(datos, HeroeDto[].class);
            if (arreglo != null) {
                listado.addAll(Arrays.asList(arreglo));
            }
        } catch (IOException e) {
            System.out.println("Error al leer: " + e.getMessage());
        }
        return listado;
    }
}