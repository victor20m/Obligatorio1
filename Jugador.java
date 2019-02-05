/*
 * Grupo M2A:
 * Erik Fernandez (225511)
 * Victor Munareto (226829)
 */

public class Jugador implements Comparable<Jugador> {

    private String nombre;
    private String alias;
    private int puntos;
    private int movimientosAcumulados;

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getNombre() {
        return nombre;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPuntos(int Puntos) {
        this.puntos = Puntos;
    }

    public String getAlias() {
        return alias;
    }

    public int getMovimientosAcumulados() {
        return movimientosAcumulados;
    }

    public void setMovimientosAcumulados(int movimientosAcumulados) {
        this.movimientosAcumulados = movimientosAcumulados;
    }

    @Override
    public boolean equals(Object o) {

        Boolean bool = false;

        if (this.getAlias().equals(((Jugador) o).getAlias())) {
            bool = true;
        }
        return bool;
    }

    @Override
    public int compareTo(Jugador j1) {
        return j1.getPuntos() - this.getPuntos();
    }

    @Override
    public String toString() {
        return "Alias: "+this.getAlias() + " Nombre: " + this.getNombre() + " Partidas ganadas: " + this.getPuntos();
    }

}
