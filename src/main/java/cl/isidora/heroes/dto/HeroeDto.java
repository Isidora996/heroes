package cl.isidora.heroes.dto;

public class HeroeDto {
    private Integer id;
    private String nombre;

    public HeroeDto() {
    }

    public HeroeDto(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "HeroeDto{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
