public class Jugador implements Comparable<Jugador> {

    String alias;

    public void setAlias(String alias) {
        this.alias = alias;
    }
    String nombre;
    int Puntos;

    public String getNombre() {
        return nombre;
    }

    public int getPuntos() {
        return Puntos;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPuntos(int Puntos) {
        this.Puntos = Puntos;
    }

    public String getAlias() {
        return alias;
    }

    @Override
    public boolean equals(Object o) {

        Boolean bool = false;

        if (this.getPuntos() == ((Jugador) o).getPuntos()) {
            bool = true;
        }
        return bool;
    }

    @Override

    public int compareTo(Jugador j1) {
        return this.getPuntos() - j1.getPuntos();
    }

    @Override
    public String toString() {
        return this.getAlias()+ " " + this.getNombre()+ " "+ this.getPuntos();
    }

}
