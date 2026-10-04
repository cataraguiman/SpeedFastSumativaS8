package modelo;

import java.time.LocalDate;
import java.time.LocalTime;

public class Entrega {

    private int id;
    private int idPedido;
    private int idRepartidor;
    private LocalDate fecha;
    private LocalTime hora;

    // Constructor vacío.
    public Entrega() {
    }

    // Constructor con atributos.
    public Entrega(int id, int idPedido, int idRepartidor,
                   LocalDate fecha, LocalTime hora) {
        this.id = id;
        this.idPedido = idPedido;
        this.idRepartidor = idRepartidor;
        this.fecha = fecha;
        this.hora = hora;
    }

    // Constructor para registrar una nueva entrega.
    public Entrega(int idPedido, int idRepartidor,
                   LocalDate fecha, LocalTime hora) {
        this.idPedido = idPedido;
        this.idRepartidor = idRepartidor;
        this.fecha = fecha;
        this.hora = hora;
    }

    // Getter y Setter.

    public int getId() {
        return id;
    }

    public Entrega setId(int id) {
        this.id = id;
        return this;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public Entrega setIdPedido(int idPedido) {
        this.idPedido = idPedido;
        return this;
    }

    public int getIdRepartidor() {
        return idRepartidor;
    }

    public Entrega setIdRepartidor(int idRepartidor) {
        this.idRepartidor = idRepartidor;
        return this;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public Entrega setFecha(LocalDate fecha) {
        this.fecha = fecha;
        return this;
    }

    public LocalTime getHora() {
        return hora;
    }

    public Entrega setHora(LocalTime hora) {
        this.hora = hora;
        return this;
    }
}