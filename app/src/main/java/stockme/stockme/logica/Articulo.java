package stockme.stockme.logica;

/**
 * Created by JuanMiguel on 03/03/2016.
 */
public class Articulo {
    private int id;
    private String nombre;
    private String marca;
    private String supermercado;
    private float precio;

    public static String ID = "Id";
    public static String NOMBRE = "Nombre";
    public static String MARCA = "Marca";
    public static String SUPERMERCADO = "Supermercado";
    public static String PRECIO = "Precio";

    public Articulo(){
        this.id = 0;
        this.nombre = "";
        this.marca = "";
        this.supermercado = "";
        this.precio = 0.0f;
    }

    public Articulo(int id, String nombre, String marca, String supermercado, float precio) {
        this.id = id;
        this.nombre = nombre;
        this.marca = marca;
        this.supermercado = supermercado;
        this.precio = precio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getSupermercado() {
        return supermercado;
    }

    public void setSupermercado(String supermercado) {
        this.supermercado = supermercado;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }
}
