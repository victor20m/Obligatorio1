import java.util.ArrayList;

/*
 * Grupo M2A:
 * Erik Fernandez (225511)
 * Victor Munareto (226829)
 */

public class Tablero {
    private Ficha[][] matriz;
    private Sistema sist;
    private int contadorMovimientos;

    //En el constructor de tablero se le asignan las 16 fichas que fueron inicializadas en la clase Sistema
    public Tablero(Sistema sistema) {
        matriz = new Ficha[8][9];
        this.sist = sistema;
        this.getMatriz()[0] = sist.getFichasAzules();
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

    //Movimientos de fichas rojas
    public void moverDerechaRoja(int filaFicha, int columnaFicha, Ficha fichaSeleccionada) {
        if (filaFicha > 0 && columnaFicha < matriz[0].length - 1) {
            if (matriz[filaFicha - 1][columnaFicha + 1] == null) {

                matriz[filaFicha][columnaFicha] = null;
                matriz[filaFicha - 1][columnaFicha + 1] = fichaSeleccionada;
                fichaSeleccionada.setFila(filaFicha - 1);
                fichaSeleccionada.setColumna(columnaFicha + 1);
            }
        }
    }

    public void moverAdelanteRoja(int filaFicha, int columnaFicha, Ficha fichaSeleccionada) {
        if (filaFicha > 0) {
            if (matriz[filaFicha - 1][columnaFicha] == null) {

                matriz[filaFicha][columnaFicha] = null;
                matriz[filaFicha - 1][columnaFicha] = fichaSeleccionada;
                fichaSeleccionada.setFila(filaFicha - 1);
                fichaSeleccionada.setColumna(columnaFicha);
            }
        }
    }

    public void moverIzquierdaRoja(int filaFicha, int columnaFicha, Ficha fichaSeleccionada) {
        if (filaFicha > 0 && columnaFicha > 0) {
            if (matriz[filaFicha - 1][columnaFicha - 1] == null) {

                matriz[filaFicha][columnaFicha] = null;
                matriz[filaFicha - 1][columnaFicha - 1] = fichaSeleccionada;
                fichaSeleccionada.setFila(filaFicha - 1);
                fichaSeleccionada.setColumna(columnaFicha - 1);
            }
        }
    }

    //Movimientos de fichas azules
    public void moverAdelanteAzul(int filaFicha, int columnaFicha, Ficha fichaSeleccionada) {
        if (filaFicha < this.getMatriz().length - 1) {
            if (matriz[filaFicha + 1][columnaFicha] == null) {

                matriz[filaFicha][columnaFicha] = null;
                matriz[filaFicha + 1][columnaFicha] = fichaSeleccionada;
                fichaSeleccionada.setFila(filaFicha + 1);
                fichaSeleccionada.setColumna(columnaFicha);
            }
        }
    }

    public void moverDerechaAzul(int filaFicha, int columnaFicha, Ficha fichaSeleccionada) {
        if (filaFicha < this.getMatriz().length - 1 && columnaFicha < this.getMatriz()[0].length - 1) {
            if (matriz[filaFicha + 1][columnaFicha + 1] == null) {

                matriz[filaFicha][columnaFicha] = null;
                matriz[filaFicha + 1][columnaFicha + 1] = fichaSeleccionada;
                fichaSeleccionada.setFila(filaFicha + 1);
                fichaSeleccionada.setColumna(columnaFicha + 1);
            }
        }
    }

    public void moverIzquierdaAzul(int filaFicha, int columnaFicha, Ficha fichaSeleccionada) {
        if (filaFicha < this.getMatriz().length - 1 && columnaFicha > 0) {
            if (matriz[filaFicha + 1][columnaFicha - 1] == null) {

                matriz[filaFicha][columnaFicha] = null;
                matriz[filaFicha + 1][columnaFicha - 1] = fichaSeleccionada;
                fichaSeleccionada.setFila(filaFicha + 1);
                fichaSeleccionada.setColumna(columnaFicha - 1);
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
                if (matriz[i][j] != null) {
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

    public ArrayList<Integer> movimientosPosibles(Ficha ficha) {
        ArrayList<Integer> movimientos = new ArrayList<>();
        movimientos.add(sumaHorizontal(ficha));
        movimientos.add(sumaVertical(ficha));
        movimientos.add(sumaDiagonalPrimaria(ficha));
        movimientos.add(sumaDiagonalSecundaria(ficha));
        for (int i = 0; i < movimientos.size(); i++) {
            if (movimientos.get(i) < 1 || movimientos.get(i) > 8 || movimientos.get(i) == ficha.getValor()) {
                movimientos.remove(i);
                i--;
            }
        }
        return movimientos;
    }

    private int sumaHorizontal(Ficha ficha) {
        int suma = 0;
        for (int i = 0; i < this.getMatriz()[0].length; i++) {
            if (this.getMatriz()[ficha.getPosicionFila()][i] != null) {
                suma += matriz[ficha.getPosicionFila()][i].getValor();
            }
        }
        return suma;
    }

    private int sumaVertical(Ficha ficha) {
        int suma = 0;
        for (int i = 0; i < this.getMatriz().length; i++) {
            if (this.getMatriz()[i][ficha.getColumna()] != null) {
                suma += matriz[i][ficha.getColumna()].getValor();
            }
        }
        return suma;
    }

    private int sumaDiagonalPrimaria(Ficha ficha) {
        int suma = ficha.getValor();
        int fila = ficha.getPosicionFila();
        int columna = ficha.getColumna();
        int fila2 = ficha.getPosicionFila();
        int columna2 = ficha.getColumna();
        for (int i = 1; i < 10; i++) {
            if (fila > 0 && columna > 0) {
                fila -= 1;
                columna -= 1;
                if (matriz[fila][columna] != null) {
                    suma += matriz[fila][columna].getValor();
                }
            }

            if (fila2 < matriz.length - 1 && columna2 < matriz[0].length - 1) {
                fila2 += 1;
                columna2 += 1;
                if (matriz[fila2][columna2] != null) {
                    suma += matriz[fila2][columna2].getValor();
                }
            }
        }
        return suma;
    }

    private int sumaDiagonalSecundaria(Ficha ficha) {
        int suma = ficha.getValor();
        int fila = ficha.getPosicionFila();
        int columna = ficha.getColumna();
        int fila2 = ficha.getPosicionFila();
        int columna2 = ficha.getColumna();
        for (int i = 1; i < 10; i++) {
            if (fila > 0 && columna < matriz[0].length - 1) {
                fila -= 1;
                columna += 1;
                if (matriz[fila][columna] != null) {
                    suma += matriz[fila][columna].getValor();
                }
            }
            if (fila2 < matriz.length - 1 && columna2 > 0) {
                fila2 += 1;
                columna2 -= 1;
                if (matriz[fila2][columna2] != null) {
                    suma += matriz[fila2][columna2].getValor();
                }
            }
        }
        return suma;
    }

    public int getContadorMovimientos() {
        return contadorMovimientos;
    }

    public void setContadorMovimientos(int contadorMovimientos) {
        this.contadorMovimientos = contadorMovimientos;
    }
}
