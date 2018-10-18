import java.util.ArrayList;

public class Sistema {
	private Ficha [] fichasAzules;
	private Ficha [] fichasRojas;
        private ArrayList<Jugador> listaJugadores;
        
        
        
        
	public Sistema(){
		fichasAzules = new Ficha[9];
		fichasRojas = new Ficha[9];
		for(int i = 0; i<8 ;i++) {
			fichasAzules[i] = new Ficha(Integer.toString(i+1), i+1, "Azul");
			fichasRojas[fichasRojas.length-1-i]= new Ficha(Integer.toString(i+1), 1, "Roja");
		}
                
                listaJugadores = new ArrayList<>();
                
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
    
        
        
        
        
        
}
