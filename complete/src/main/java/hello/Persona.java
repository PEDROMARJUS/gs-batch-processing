package hello;

//Formato del registro de datos definido en la clase, ya se puede instanciar a través de un constructor o estableciendo las propiedades
public class Persona {
    private String nombre;
    private String apellido1;
    private String apellido2;

    public Persona() {

    }

    public Persona(String nombre, String apellido1, String apellido2) {
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public String getApellido2() {
        return apellido2;
    }

    @Override
    public String toString() {
        return "nombre: " + nombre + ", apellido1: " + apellido1 + ", apellido2: " + apellido2;
    }

}