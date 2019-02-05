import java.util.*;

/*
 * Grupo M2A:
 * Erik Fernandez (225511)
 * Victor Munareto (226829)
*/

public class Juego {

    public static void main(String[] args) {

        Sistema respaldo = new Sistema();
        Tablero tab = new Tablero(respaldo);
        mostrarSwitch(respaldo, tab);

    }

    private static void crearJugador(Sistema respaldo, Tablero tab) {
        Boolean jugadorCreado = false;

        Scanner input = new Scanner(System.in);
        Jugador j1 = new Jugador();

        while (!jugadorCreado) {

            System.out.println("Por favor ingrese el nombre del nuevo jugador");
            j1.setNombre(input.nextLine());
            System.out.println("Por favor ingrese el alias del nuevo jugador");
            j1.setAlias(input.nextLine());
            j1.setPuntos(0);
            if (respaldo.getListaJugadores().contains(j1)) {
                System.out.println("Jugador ya existente intentelo de nuevo ");
            } else {
                jugadorCreado = true;
            }
        }

        System.out.println("Jugador Creado");
        respaldo.getListaJugadores().add(j1);
        Juego.mostrarSwitch(respaldo, tab);

    }

    private static void mostrarSwitch(Sistema respaldo, Tablero tab) {
        Juego.impimirMenu();
        Scanner input = new Scanner(System.in);
        boolean loop = true;
        while (loop) {
            String opcion = input.nextLine();
            switch (opcion) {
                //Crear Jugador
                case "1":
                    Juego.crearJugador(respaldo, tab);
                    break;
                //Nueva Partida
                case "2":
                    if (respaldo.getListaJugadores().size() >= 2) {
                        respaldo.getListaPartidas().add(new Partida());
                        Partida laPartida = respaldo.getListaPartidas().get(respaldo.getListaPartidas().size() - 1);
                        while (laPartida.getJugador2() == null || laPartida.getJugador2() == null) {
                            Juego.elegirJugadores(respaldo, input, laPartida);
                        }
                        seleccionarModoDeJuego(laPartida, tab);
                    } else {
                        System.out.println("Registre al menos dos jugadores");
                        impimirMenu();
                        break;
                    }
                    break;
                //Mostrar Lista de partidas
                case "3":
                    if(!respaldo.getListaPartidas().isEmpty()){
                        System.out.println("Elija la partida a replicar");
                        imprimirListaPartidas(respaldo);
                        tab.setMatriz(resetearFichas(respaldo, tab));
                        replicarPartida(seleccionarPartida(input, respaldo),input, respaldo, tab);
                    }else{
                        System.out.println("No existen partidas creadas");
                    }
                    break;
                //Ranking de jugadores
                case "4":
                    if (respaldo.getListaJugadores().isEmpty()) {
                        System.out.println("No hay jugadores registrados");
                    } else {
                        Juego.ordenarYMostrarJugadores(respaldo);
                    }
                    break;
                //Elegir jugadores para la partida
                case "5":
                    loop = false;
                    break;
                default:
                    System.out.println("Ingrese una opción válida");
                    impimirMenu();
                    break;
            }
        }
    }

    private static void elegirJugadores(Sistema respaldo, Scanner input, Partida laPartida) {
        ArrayList<Jugador> elegirLista = respaldo.getListaJugadores();
        System.out.println("Seleccione primer jugador a participar en la partida");
        imprimirListaJugadores(respaldo);
        int opcion = verificarNumero(input, "Ingrese un numero entre 1 y " + elegirLista.size(), input.nextInt(), 0, elegirLista.size());
        for (int i = 0; i < elegirLista.size(); i++) {
            if (i + 1 == opcion) {
                laPartida.setJugador1(elegirLista.get(i));
            }
        }
        System.out.println(laPartida.getJugador1());
        System.out.println("Seleccione el segundo jugador a participar en la partida: ");
        imprimirListaJugadores(respaldo);
        opcion = verificarNumero(input, "Ingrese un numero entre 1 y " + elegirLista.size(), input.nextInt(), 0, elegirLista.size());
        laPartida.setJugador2(elegirLista.get(opcion - 1));
        while (laPartida.getJugador1().equals(elegirLista.get(opcion - 1)) || laPartida.getJugador2() == null) {
            System.out.println("Elija otro jugador");
            imprimirListaJugadores(respaldo);
            opcion = verificarNumero(input, "Ingrese un numero entre 1 y " + elegirLista.size(), input.nextInt(), 0, elegirLista.size());
            laPartida.setJugador2(elegirLista.get(opcion - 1));
        }
    }

    private static void jugar(Partida laPartida, Tablero tab) {
        Scanner input = new Scanner(System.in);
        int turnos = 1;
        boolean partidaTerminada = false;
        String color = "Rojo";
        while (!partidaTerminada) {
            mostrarMatriz(laPartida, tab);
            elegirYMover(laPartida, input, tab, color);
            turnos++;
            if (turnos % 2 == 0) {
                color = "Azul";
            } else {
                color = "Rojo";
            }
            partidaTerminada = verificarGanador(laPartida, tab);
            if (laPartida.getGanador() != null) {
                partidaTerminada = true;
            }
            System.out.println("Jugador siguiente: " + color);

        }
        System.out.println("---------------------------\n|GANADOR: " + laPartida.getGanador() + "\n---------------------------");
    }

    private static void imprimirOpcionesJugador() {
        System.out.println("Mover ficha: numero + direccion (ej: 2A mover ficha 2 adelante");
        System.out.println("Cambiar vista de Matriz: VERR(modo reducido) o VERN(modo normal)");
        System.out.println("Rendirse: X");
    }

    private static void imprimirListaJugadores(Sistema respaldo) {
        for (int i = 0; i < respaldo.getListaJugadores().size(); i++) {
            System.out.println((i + 1) + ". " + respaldo.getListaJugadores().get(i).getAlias());
        }
    }

    private static void elegirYMover(Partida laPartida, Scanner input, Tablero tab, String color) {
        String fichaElegida;
        int valor;
        char direccion;
        ArrayList<Integer> movimientosPosibles = new ArrayList<>();

        //Primer movimiento del turno
        imprimirOpcionesJugador();
        System.out.println("Elija la ficha que desea mover o finalice el turno ingresando X:");
        fichaElegida = input.nextLine().toUpperCase();
        laPartida.getMovimientos().add(fichaElegida);
        if (fichaElegida.equalsIgnoreCase("VERN") || fichaElegida.equalsIgnoreCase("VERR")) {
            laPartida.setVision(fichaElegida);
            mostrarMatriz(laPartida, tab);
            imprimirOpcionesJugador();
            fichaElegida = input.nextLine().toUpperCase();
            laPartida.getMovimientos().add(fichaElegida);

        }
        if (!fichaElegida.equalsIgnoreCase("X")) {
            valor = Character.getNumericValue(fichaElegida.charAt(0));
            direccion = fichaElegida.charAt(1);
            verificarJugada(laPartida, input, tab, valor, color, direccion);
            movimientosPosibles = tab.movimientosPosibles(tab.buscarFicha(valor, color));
        }
        if (fichaElegida.equalsIgnoreCase("X")) {
            if (color.equals("Rojo")) {
                laPartida.setGanador(laPartida.getJugador2().getAlias());
                laPartida.getJugador2().setPuntos(laPartida.getJugador2().getPuntos() + 1);
            } else {
                laPartida.setGanador(laPartida.getJugador1().getAlias());
                laPartida.getJugador1().setPuntos(laPartida.getJugador1().getPuntos() + 1);
            }
        }
        if (color.equals("Rojo")) {
            laPartida.getJugador1().setMovimientosAcumulados(laPartida.getJugador1().getMovimientosAcumulados() + 1);
        } else {
            laPartida.getJugador2().setMovimientosAcumulados(laPartida.getJugador2().getMovimientosAcumulados() + 1);
        }
        //Si existen mas movimientos posibles, se activa el siguiente loop:
        loopMovimientos(laPartida, tab, movimientosPosibles, input, color);

    }

    private static void loopMovimientos(Partida laPartida, Tablero tab, ArrayList<Integer> movimientosPosibles, Scanner input, String color) {
        while (true) {
            mostrarMatriz(laPartida, tab);
            if (movimientosPosibles.isEmpty()) {
                break;
            }
            if (color.equals("Rojo")) {
                laPartida.getJugador1().setMovimientosAcumulados(laPartida.getJugador1().getMovimientosAcumulados() + 1);
            } else {
                laPartida.getJugador2().setMovimientosAcumulados(laPartida.getJugador2().getMovimientosAcumulados() + 1);
            }

            for (int i = 0; i < movimientosPosibles.size(); i++) {
                System.out.println("Movimiento posible: ficha " + movimientosPosibles.get(i));
            }
            System.out.println("Elija nueva ficha para mover o finalice el turno ingresando N");
            String fichaElegida = input.nextLine().toUpperCase();
            laPartida.getMovimientos().add(fichaElegida);
            if (fichaElegida.equalsIgnoreCase("X")) {
                if (color.equals("Rojo")) {
                    laPartida.setGanador(laPartida.getJugador2().getAlias());
                    laPartida.getJugador2().setPuntos(laPartida.getJugador2().getPuntos() + 1);
                } else {
                    laPartida.setGanador(laPartida.getJugador1().getAlias());
                    laPartida.getJugador1().setPuntos(laPartida.getJugador1().getPuntos() + 1);
                }
                break;
            }
            if (fichaElegida.toUpperCase().equals("N")) {
                break;
            }
            if (fichaElegida.equalsIgnoreCase("VERN") || fichaElegida.equalsIgnoreCase("VERR")) {
                laPartida.setVision(fichaElegida);
                mostrarMatriz(laPartida, tab);
                imprimirOpcionesJugador();
                fichaElegida = input.nextLine();
                laPartida.getMovimientos().add(fichaElegida);
            }
            char direccion = fichaElegida.charAt(1);
            int valor = Character.getNumericValue(fichaElegida.charAt(0));
            if (movimientosPosibles.contains(valor)) {
                verificarJugada(laPartida, input, tab, valor, color, direccion);
                movimientosPosibles = tab.movimientosPosibles(tab.buscarFicha(valor, color));
            } else {
                System.out.println("Elija una opcion correcta");
            }
        }
    }

    private static void verificarJugada(Partida laPartida, Scanner input, Tablero tab, int valor, String color, char direccion) {
        int fila;
        int columna;
        int fila2;
        int columna2;
        boolean movio = false;
        String fichaElegida;
        while (!movio) {
            Ficha aux = tab.buscarFicha(valor, color);
            fila = aux.getPosicionFila();
            columna = aux.getColumna();
            moverFicha(tab, direccion, tab.buscarFicha(valor, color));
            fila2 = aux.getPosicionFila();
            columna2 = aux.getColumna();
            if (fila == fila2 && columna2 == columna && !tab.movimientosPosibles(tab.buscarFicha(valor, color)).isEmpty()) {

                System.out.println("Ya existe una ficha en este lugar o excede el limite de la matriz, inserte un nuevo movimiento");
                fichaElegida = input.nextLine().toUpperCase();
                if(fichaElegida.equalsIgnoreCase("X")){
                    break;
                }
                laPartida.getMovimientos().add(fichaElegida);
                direccion = fichaElegida.charAt(1);
            }
            if (fila == fila2 && columna2 == columna && tab.movimientosPosibles(tab.buscarFicha(valor, color)).isEmpty()) {
                System.out.println("Ya existe una ficha en este lugar o excede el limite de la matriz, inserte un nuevo movimiento");
                fichaElegida = input.nextLine().toUpperCase();
                if(fichaElegida.equalsIgnoreCase("X")){
                    break;
                }
                laPartida.getMovimientos().add(fichaElegida);
                valor = Character.getNumericValue(fichaElegida.charAt(0));
                direccion = fichaElegida.charAt(1);
            } else {
                movio = true;
            }
        }
    }

    private static void repetirVerificarJugada(Partida laPartida, Tablero tab, int valor, String color, char direccion) {
        int fila;
        int columna;
        int fila2;
        int columna2;
        boolean movio = false;
        String fichaElegida;
        while (!movio) {
            Ficha aux = tab.buscarFicha(valor, color);
            fila = aux.getPosicionFila();
            columna = aux.getColumna();
            moverFicha(tab, direccion, tab.buscarFicha(valor, color));
            fila2 = aux.getPosicionFila();
            columna2 = aux.getColumna();
            if (fila == fila2 && columna2 == columna && !tab.movimientosPosibles(tab.buscarFicha(valor, color)).isEmpty()) {

                System.out.println("Ya existe una ficha en este lugar o excede el limite de la matriz, inserte un nuevo movimiento");
                fichaElegida = laPartida.getMovimientos().get(tab.getContadorMovimientos());
                tab.setContadorMovimientos(tab.getContadorMovimientos() + 1);
                direccion = fichaElegida.charAt(1);
            }
            if (fila == fila2 && columna2 == columna && tab.movimientosPosibles(tab.buscarFicha(valor, color)).isEmpty()) {
                System.out.println("Ya existe una ficha en este lugar o excede el limite de la matriz, inserte un nuevo movimiento");
                fichaElegida = laPartida.getMovimientos().get(tab.getContadorMovimientos());
                ;
                tab.setContadorMovimientos(tab.getContadorMovimientos() + 1);
                valor = Character.getNumericValue(fichaElegida.charAt(0));
                direccion = fichaElegida.charAt(1);
            } else {
                movio = true;
            }
        }
    }

    private static void moverFicha(Tablero tab, char direccion, Ficha ficha) {

        int filaFicha = ficha.getPosicionFila();
        int columnaFicha = ficha.getColumna();
        switch (direccion) {
            case 'I':
                if (ficha.getColor().equals("Azul")) {
                    tab.moverIzquierdaAzul(filaFicha, columnaFicha, ficha);
                } else {
                    tab.moverIzquierdaRoja(filaFicha, columnaFicha, ficha);
                }
                break;
            case 'A':
                if (ficha.getColor().equals("Azul")) {
                    tab.moverAdelanteAzul(filaFicha, columnaFicha, ficha);
                } else {
                    tab.moverAdelanteRoja(filaFicha, columnaFicha, ficha);
                }
                break;
            case 'D':
                if (ficha.getColor().equals("Azul")) {
                    tab.moverDerechaAzul(filaFicha, columnaFicha, ficha);
                } else {
                    tab.moverDerechaRoja(filaFicha, columnaFicha, ficha);
                }
                break;
            default:
                break;
        }
    }

    private static void seleccionarModoDeJuego(Partida laPartida, Tablero tab) {
        Boolean error = true;

        System.out.println("Seleccione un modo de Juego");
        System.out.println("1. Quien suma mas");
        System.out.println("2. Llegar primero al otro lado");
        System.out.println("3. Llevar todas tus fichas al lado contrario");
        Scanner input = new Scanner(System.in);
        while (error) {
            int opcion = input.nextInt();
            if (opcion <= 3) {
                laPartida.setModo(opcion);
                if (opcion == 1) {
                    System.out.println("Ingrese los turnos máximos");
                    opcion = input.nextInt();
                    laPartida.setCantidadMovimientos(opcion);
                }
                error = false;
            } else {
                System.out.println("Opcion no valida intentelo nuevamente");
                System.out.println("1. Quien suma mas");
                System.out.println("2. Llegar primero al otro lado");
                System.out.println("3. Llevar todas tus fichas al lado contrario");
            }
        }
        jugar(laPartida, tab);
    }

    private static boolean modoUno(Partida laPartida, Tablero tab) {
        Boolean gano = false;
        int sumaRojo = 0;
        int sumaAzul = 0;
        int limiteMovimientos1 = laPartida.getJugador1().getMovimientosAcumulados();
        int limiteMovimientos2 = laPartida.getJugador2().getMovimientosAcumulados();
        if (laPartida.getCantidadMovimientos() == limiteMovimientos1 || laPartida.getCantidadMovimientos() == limiteMovimientos2) {
            for (int i = 0; i < tab.getMatriz()[0].length; i++) {
                if (tab.getMatriz()[0][i] != null) {
                    if (tab.getMatriz()[0][i].getColor().equals("Rojo")) {
                        sumaRojo += tab.getMatriz()[0][i].getValor();
                    }
                }
                if (tab.getMatriz()[7][i] != null) {
                    if (tab.getMatriz()[7][i].getColor().equals("Azul")) {
                        sumaAzul += tab.getMatriz()[7][i].getValor();
                    }
                }
            }
            if (sumaRojo > sumaAzul) {
                gano = true;
                laPartida.getJugador1().setPuntos(laPartida.getJugador1().getPuntos() + 1);
                laPartida.setGanador(laPartida.getJugador1().getAlias());
            } else if (sumaAzul > sumaRojo) {
                gano = true;
                laPartida.getJugador2().setPuntos(laPartida.getJugador2().getPuntos() + 1);
                laPartida.setGanador(laPartida.getJugador2().getAlias());
            } else {
                gano = true;
                laPartida.getJugador1().setPuntos(laPartida.getJugador1().getPuntos() + 1);
                laPartida.getJugador2().setPuntos(laPartida.getJugador2().getPuntos() + 1);
                laPartida.setGanador("EMPATE");
            }
        }
        return gano;
    }

    private static boolean modoDos(Partida laPartida, Tablero tab) {
        Boolean gano = false;

        for (int columnas = 0; columnas < tab.getMatriz()[0].length; columnas++) {
            if (tab.getMatriz()[0][columnas] != null && tab.getMatriz()[0][columnas].getColor().equalsIgnoreCase("Rojo")) {
                gano = true;
                laPartida.getJugador1().setPuntos(laPartida.getJugador1().getPuntos() + 1);
                laPartida.setGanador(laPartida.getJugador1().getAlias());
            }
        }
        for (int columnas = 0; columnas < tab.getMatriz()[0].length; columnas++) {
            if (tab.getMatriz()[7][columnas] != null && tab.getMatriz()[7][columnas].getColor().equalsIgnoreCase("Azul")) {
                gano = true;
                laPartida.getJugador2().setPuntos(laPartida.getJugador2().getPuntos() + 1);
                laPartida.setGanador(laPartida.getJugador2().getAlias());
            }
        }

        return gano;
    }

    private static boolean modoTres(Partida laPartida, Tablero tab) {
        int contadorRojo = 0;
        int contadorAzul = 0;
        boolean ganador = false;
        for (int i = 0; i < tab.getMatriz()[0].length; i++) {
            if (tab.getMatriz()[0][i].getColor().equals("Rojo")) {
                contadorRojo += 1;
            }
            if (tab.getMatriz()[7][i].getColor().equals("Azul")) {
                contadorAzul += 1;
            }
        }
        if (contadorRojo == 8) {
            laPartida.getJugador1().setPuntos(laPartida.getJugador1().getPuntos() + 1);
            laPartida.setGanador(laPartida.getJugador1().getAlias());
            ganador = true;
        }
        if (contadorAzul == 8) {
            laPartida.getJugador2().setPuntos(laPartida.getJugador2().getPuntos() + 1);
            laPartida.setGanador(laPartida.getJugador2().getAlias());
            ganador = true;
        }

        return ganador;
    }

    private static void replicarPartida(int seleccionarPartida, Scanner input, Sistema respaldo, Tablero tab) {
        Partida laPartida = respaldo.getListaPartidas().get(seleccionarPartida);
        laPartida.setVision("VERN");
        int turnos = 1;
        boolean partidaTerminada = false;
        String color = "Rojo";
        mostrarMatriz(laPartida, tab);
        input.nextLine();
        while (!partidaTerminada) {
            replicarElegirYMover(input, laPartida, tab, color);
            turnos++;
            if (turnos % 2 == 0) {
                color = "Azul";
            } else {
                color = "Rojo";
            }
            partidaTerminada = verificarGanador(laPartida, tab);
            if (laPartida.getJugador1().getMovimientosAcumulados() == turnos) {
                partidaTerminada = true;
            }
            System.out.println("Jugador siguiente: " + color);
        }
        System.out.println("---------------------------\n|GANADOR: " + laPartida.getGanador() + "\n---------------------------");
    }

    private static void replicarElegirYMover(Scanner input, Partida laPartida, Tablero tab, String color) {
        String fichaElegida;
        int valor;
        char direccion;
        ArrayList<Integer> movimientosPosibles = new ArrayList<>();

        //Primer movimiento del turno
        fichaElegida = laPartida.getMovimientos().get(tab.getContadorMovimientos());
        tab.setContadorMovimientos(tab.getContadorMovimientos() + 1);
        if (fichaElegida.equalsIgnoreCase("VERN") || fichaElegida.equalsIgnoreCase("VERR")) {
            laPartida.setVision(fichaElegida);
            input.nextLine();
            mostrarMatriz(laPartida, tab);
            imprimirOpcionesJugador();
            fichaElegida = laPartida.getMovimientos().get(tab.getContadorMovimientos());
            tab.setContadorMovimientos(tab.getContadorMovimientos() + 1);

        }
        if (!fichaElegida.equalsIgnoreCase("X")) {
            valor = Character.getNumericValue(fichaElegida.charAt(0));
            direccion = fichaElegida.charAt(1);
            repetirVerificarJugada(laPartida, tab, valor, color, direccion);
            movimientosPosibles = tab.movimientosPosibles(tab.buscarFicha(valor, color));
        }
        if (fichaElegida.equalsIgnoreCase("X")) {
            System.out.println();
        }
        if (color.equals("Rojo")) {
            laPartida.getJugador1().setMovimientosAcumulados(laPartida.getJugador1().getMovimientosAcumulados() + 1);
        } else {
            laPartida.getJugador2().setMovimientosAcumulados(laPartida.getJugador2().getMovimientosAcumulados() + 1);
        }
        //Si existen mas movimientos posibles, se activa el siguiente loop:
        replicarLoopMovimientos(input, fichaElegida, laPartida, tab, movimientosPosibles, color);
    }

    private static void replicarLoopMovimientos(Scanner input, String fichaElegida, Partida laPartida, Tablero tab, ArrayList<Integer> movimientosPosibles, String color) {
        while (true) {
            input.nextLine();
            mostrarMatriz(laPartida, tab);
            if (movimientosPosibles.isEmpty()) {
                break;
            }
            if (fichaElegida.equalsIgnoreCase("X")) {
                break;
            }
            if (color.equals("Rojo")) {
                laPartida.getJugador1().setMovimientosAcumulados(laPartida.getJugador1().getMovimientosAcumulados() + 1);
            } else {
                laPartida.getJugador2().setMovimientosAcumulados(laPartida.getJugador2().getMovimientosAcumulados() + 1);
            }

            fichaElegida = laPartida.getMovimientos().get(tab.getContadorMovimientos());
            tab.setContadorMovimientos(tab.getContadorMovimientos() + 1);
            if (fichaElegida.toUpperCase().equals("N")) {
                break;
            }
            if (fichaElegida.equalsIgnoreCase("VERN") || fichaElegida.equalsIgnoreCase("VERR")) {
                laPartida.setVision(fichaElegida);
                fichaElegida = laPartida.getMovimientos().get(tab.getContadorMovimientos());
                tab.setContadorMovimientos(tab.getContadorMovimientos() + 1);
            }
            char direccion = fichaElegida.charAt(1);
            int valor = Character.getNumericValue(fichaElegida.charAt(0));
            if (movimientosPosibles.contains(valor)) {
                repetirVerificarJugada(laPartida, tab, valor, color, direccion);
                movimientosPosibles = tab.movimientosPosibles(tab.buscarFicha(valor, color));
            }
        }
    }

    private static void mostrarMatriz(Partida laPartida, Tablero tab) {
        if (laPartida.getVision().equalsIgnoreCase("VERR")) {
            mostrarMatrizSimple(tab.getMatriz());
        } else {
            mostrarMatrizNormal(tab.getMatriz());
        }
    }

    private static void mostrarMatrizNormal(Ficha[][] matriz) {
        for (int i = 0; i < matriz.length; i++) {
            System.out.println("+-+-+-+-+-+-+-+-+-+");
            for (int j = 0; j < matriz[0].length; j++) {
                if (matriz[i][j] == null) {
                    System.out.print("| ");
                } else {
                    System.out.print("|");
                    System.out.print(matriz[i][j]);
                }
            }
            System.out.println("|");
        }
        System.out.println("+-+-+-+-+-+-+-+-+-+");
    }

    private static void mostrarMatrizSimple(Ficha[][] matriz) {
        for (int filas = 0; filas < matriz.length; filas++) {
            System.out.println("");
            for (int columnas = 0; columnas < matriz[0].length; columnas++) {
                if (matriz[filas][columnas] == null) {
                    System.out.print("- ");

                } else {
                    System.out.print(matriz[filas][columnas]+" ");
                }
            }
        }
        System.out.println();
    }

    private static void ordenarYMostrarJugadores(Sistema respaldo) {
        Collections.sort(respaldo.getListaJugadores());
        for (int i = 0; i < respaldo.getListaJugadores().size(); i++) {
            System.out.println(respaldo.getListaJugadores().get(i));
        }
    }

    private static void impimirMenu() {
        System.out.println("Bienvenido a Sumas!");
        System.out.println("Por favor, elija la opcion deseada:");
        System.out.println("1. Registrar Jugador");
        System.out.println("2. Jugar Partida");
        System.out.println("3. Replicar Partida");
        System.out.println("4. Ranking de jugadores");
        System.out.println("5. Finalizar programa");

    }

    private static void imprimirListaPartidas(Sistema respaldo){
        for(int i = 0; i<respaldo.getListaPartidas().size(); i++){
            System.out.println((i+1)+". "+respaldo.getListaPartidas().get(i));
        }
    }

    private static int seleccionarPartida(Scanner input, Sistema respaldo){
        int opcion = verificarNumero(input, "Inserte una opcion entre 1 y "+respaldo.getListaPartidas().size(), input.nextInt(), 0, respaldo.getListaPartidas().size());
        return opcion-1;
    }

    private static boolean verificarGanador(Partida laPartida, Tablero tab) {
        boolean partidaTerminada = false;
        switch (laPartida.getModo()) {
            case 1:
                partidaTerminada = modoUno(laPartida, tab);
                break;
            case 2:
                partidaTerminada = modoDos(laPartida, tab);
                break;
            case 3:
                partidaTerminada = modoTres(laPartida, tab);
                break;
        }
        return partidaTerminada;
    }

    private static int verificarNumero(Scanner input, String mensaje, int numero, int rangoMin, int rangoMax) {
        while (numero < rangoMin || numero > rangoMax) {
            System.out.println(mensaje);
            numero = input.nextInt();
        }
        return numero;
    }

    public static Ficha[][] resetearFichas(Sistema respaldo, Tablero tab) {
        Ficha[] fichasAzules = new Ficha[9];
        Ficha[] fichasRojas = new Ficha[9];
        for (int i = 0; i < 8; i++) {
            fichasAzules[i + 1] = new Ficha(Integer.toString(i + 1), i + 1, "Azul");
            fichasRojas[fichasRojas.length - 2 - i] = new Ficha(Integer.toString(i + 1), i + 1, "Rojo");
        }
        Ficha[][] matriz = tab.getMatriz();
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[0].length; j++) {
                matriz[i][j] = null;
            }
        }
        matriz[0] = fichasAzules;
        matriz[7] = fichasRojas;

        return matriz;
    }

}