public class Tablero {
	Ficha[][] matriz;
	Sistema sist;

	public Tablero(Sistema sistema) {
		matriz = new Ficha[8][9];
		this.sist = sistema;
		this.getMatriz()[0]= sist.getFichasAzules();
		this.getMatriz()[7] = sist.getFichasRojas();
	}

	public Ficha[][] getMatriz() {
		return matriz;
	}

	public void setMatriz(Ficha[][] matriz) {
		this.matriz = matriz;
	}

	/*
	 * Se pasa la ficha que el jugador quiere mover y la direccion deseada. Se
	 * verifica que antes de mover la ficha, la posicion siguiente esta vacia (sea
	 * null) y que no se vaya del limite de la matriz.
	 */
	public void moverFicha(String direccion, Ficha ficha) {
		Ficha fichaSeleccionada = ficha;

		int filaFicha = fichaSeleccionada.getPosicionFila();
		int columnaFicha = fichaSeleccionada.getColumna();
		switch (direccion) {
		case "I":
			if(ficha.getColor().equals("Azul")){
				moverIzquierdaAzul(filaFicha,columnaFicha,fichaSeleccionada);
			}else{
				moverIzquierdaRoja(filaFicha, columnaFicha, fichaSeleccionada);
			}

			break;
		case "A":
			if(ficha.getColor().equals("Azul")){
				moverAdelanteAzul(filaFicha,columnaFicha,fichaSeleccionada);
			}else{
				moverAdelanteRoja(filaFicha, columnaFicha,fichaSeleccionada);
			}
			break;
		case "D":
			if(ficha.getColor().equals("Azul")){
				moverDerechaAzul(filaFicha,columnaFicha,fichaSeleccionada);
			}else{
				moverDerechaRoja(filaFicha,columnaFicha,fichaSeleccionada);
			}
			break;
		}
	}

	//Movimientos de fichas rojas
	public void moverDerechaRoja(int filaFicha, int columnaFicha, Ficha fichaSeleccionada){
		if (filaFicha > 0 && columnaFicha < matriz[0].length-1) {
			if(matriz[filaFicha - 1][columnaFicha+1] == null){

				matriz[filaFicha][columnaFicha] = null;
				matriz[filaFicha-1][columnaFicha+1] = fichaSeleccionada;
				fichaSeleccionada.setFila(filaFicha-1);
				fichaSeleccionada.setColumna(columnaFicha+1);
			}
		}
	}
	public void moverAdelanteRoja(int filaFicha, int columnaFicha, Ficha fichaSeleccionada){
		if (filaFicha > 0) {
			if(matriz[filaFicha - 1][columnaFicha] == null){

				matriz[filaFicha][columnaFicha] = null;
				matriz[filaFicha-1][columnaFicha] = fichaSeleccionada;
				fichaSeleccionada.setFila(filaFicha-1);
				fichaSeleccionada.setColumna(columnaFicha);
			}
		}
	}
	public void moverIzquierdaRoja(int filaFicha, int columnaFicha, Ficha fichaSeleccionada){
		if (filaFicha > 0 && columnaFicha > 0) {
			if(matriz[filaFicha - 1][columnaFicha - 1] == null){

				matriz[filaFicha][columnaFicha] = null;
				matriz[filaFicha-1][columnaFicha-1] = fichaSeleccionada;
				fichaSeleccionada.setFila(filaFicha-1);
				fichaSeleccionada.setColumna(columnaFicha-1);
			}
		}
	}

	//Movimientos de fichas azules
	public void moverAdelanteAzul(int filaFicha, int columnaFicha, Ficha fichaSeleccionada){
		if (filaFicha < this.getMatriz().length-1) {
			if(matriz[filaFicha + 1][columnaFicha] == null){

				matriz[filaFicha][columnaFicha] = null;
				matriz[filaFicha+1][columnaFicha] = fichaSeleccionada;
				fichaSeleccionada.setFila(filaFicha+1);
				fichaSeleccionada.setColumna(columnaFicha);
			}
		}
	}
	public void moverDerechaAzul(int filaFicha, int columnaFicha, Ficha fichaSeleccionada){
		if (filaFicha < this.getMatriz().length-1 && columnaFicha < this.getMatriz()[0].length) {
			if(matriz[filaFicha + 1][columnaFicha+1] == null){

				matriz[filaFicha][columnaFicha] = null;
				matriz[filaFicha+1][columnaFicha+1] = fichaSeleccionada;
				fichaSeleccionada.setFila(filaFicha+1);
				fichaSeleccionada.setColumna(columnaFicha+1);
			}
		}
	}
	public void moverIzquierdaAzul(int filaFicha, int columnaFicha, Ficha fichaSeleccionada){
		if (filaFicha < this.getMatriz().length-1 && columnaFicha > 0) {
			if(matriz[filaFicha + 1][columnaFicha-1] == null){

				matriz[filaFicha][columnaFicha] = null;
				matriz[filaFicha+1][columnaFicha-1] = fichaSeleccionada;
				fichaSeleccionada.setFila(filaFicha+1);
				fichaSeleccionada.setColumna(columnaFicha-1);
			}
		}
	}

	/*
	 * Pide el valor de la ficha y el numero de jugador para buscar la ficha deseada
	 * y que pertenezca al jugador que accede a dicha ficha. Retorna la ficha
	 * buscada
	 */
	public Ficha buscarFicha(int valor, String color) {
		Ficha ficha = null;
		for (int i = 0; i < matriz.length; i++) {
			for (int j = 0; j < matriz[0].length; j++) {
				if(matriz[i][j] != null){
					if (matriz[i][j].getValor() == valor && matriz[i][j].getColor().equals(color)) {

						ficha = matriz[i][j];
						ficha.setColumna(j);
						ficha.setFila(i);
					}
				}

			}
		}
		return ficha;
	}
}
