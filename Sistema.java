import java.util.ArrayList;

/*
 * Grupo M2A:
 * Erik Fernandez (225511)
 * Victor Munareto (226829)
 */

public class Sistema {
    private Ficha[] fichasAzules;
    private Ficha[] fichasRojas;
    private ArrayList<Jugador> listaJugadores;
    private ArrayList<Partida> listaPartidas;

    //En el constructor de sistema se crea la lista de fichas que seran usadas en el tablero
    public Sistema() {
        fichasAzules = new Ficha[9];
        fichasRojas = new Ficha[9];
        for (int i = 0; i < 8; i++) {
            fichasAzules[i + 1] = new Ficha(Integer.toString(i + 1), i + 1, "Azul");
            fichasRojas[fichasRojas.length - 2 - i] = new Ficha(Integer.toString(i + 1), i + 1, "Rojo");
        }
        listaJugadores = new ArrayList<>();
        listaPartidas = new ArrayList<>();
    }

    public ArrayList<Jugador> getListaJugadores() {
        return listaJugadores;
    }

    public void setListaJugadores(ArrayList<Jugador> listaJugadores) {
        this.listaJugadores = listaJugadores;
    }

    public Ficha[] getFichasAzules() {
        return fichasAzules;
    }

    public void setFichasAzules(Ficha[] fichasAzules) {
        this.fichasAzules = fichasAzules;
    }

    public Ficha[] getFichasRojas() {
        return fichasRojas;
    }

    public void setFichasRojas(Ficha[] fichasRojas) {
        this.fichasRojas = fichasRojas;
    }

    public ArrayList<Partida> getListaPartidas() {
        return listaPartidas;
    }

    public void setListaPartidas(ArrayList<Partida> listaPartidas) {
        this.listaPartidas = listaPartidas;
    }
}
