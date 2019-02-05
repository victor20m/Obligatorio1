import java.util.ArrayList;
import java.util.Date;

/*
 * Grupo M2A:
 * Erik Fernandez (225511)
 * Victor Munareto (226829)
 */

public class Partida implements Comparable<Partida>{
    private Jugador jugador1;
    private Jugador jugador2;
    private ArrayList<String> movimientos;
    private String vision = "VERN";
    private int modo;
    private String ganador;
    private int cantidadMovimientos;
    private Date fecha;

    Partida() {
        movimientos = new ArrayList<>();
        fecha = new Date();
    }

    public Jugador getJugador1() {
        return jugador1;
    }


    public void setJugador1(Jugador jugador1) {
        this.jugador1 = jugador1;
    }

    public Jugador getJugador2() {
        return jugador2;
    }

    public void setJugador2(Jugador jugador2) {
        this.jugador2 = jugador2;
    }

    public ArrayList<String> getMovimientos() {
        return movimientos;
    }

    public void setMovimientos(ArrayList<String> movimientos) {
        this.movimientos = movimientos;
    }

    public String getVision() {
        return vision;
    }

    public void setVision(String vision) {
        this.vision = vision;
    }

    public int getModo() {
        return modo;
    }

    public void setModo(int modo) {
        this.modo = modo;
    }

    public String getGanador() {
        return ganador;
    }

    public void setGanador(String ganador) {
        this.ganador = ganador;
    }

    public int getCantidadMovimientos() {
        return cantidadMovimientos;
    }

    public void setCantidadMovimientos(int cantidadMovimientos) {
        this.cantidadMovimientos = cantidadMovimientos;
    }

    public Date getDate() {
        return fecha;
    }

    public void setDate(Date date) {
        this.fecha = date;
    }

    @Override
    public String toString() {
        return "Partida: "+jugador1.getAlias()+ " vs " + jugador2.getAlias()+" Fecha: "+fecha;
    }

    @Override
    public int compareTo(Partida o) {
        return this.fecha.compareTo(o.getDate());
    }

}
