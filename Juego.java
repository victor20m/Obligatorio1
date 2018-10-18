import java.util.*;

public class Juego {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        Sistema respaldo = new Sistema();

        Tablero tab = new Tablero(respaldo);
        mostrarMatrizNormal(tab.getMatriz());

    }
    private static void mostrarMatrizNormal(Ficha[][] matriz){
        for(int i = 0; i<matriz.length;i++) {
            System.out.println("+-+-+-+-+-+-+-+-+-+");
            for(int j = 0; j<matriz[0].length;j++) {
                if(matriz[i][j]==null) {
                    System.out.print("| ");
                }else {
                    System.out.print("|");
                    System.out.print(matriz[i][j]);
                }
            }
            System.out.println("|");
        }
        System.out.println("+-+-+-+-+-+-+-+-+-+");
    }

    public static void crearJugador(Sistema respaldo, Scanner input) {

        Jugador j1 = new Jugador();
        System.out.println("Por favor ingrese el nombre del nuevo jugador");
        j1.setNombre(input.nextLine());
        System.out.println("Por favor ingrese el alias del nuevo jugador");
        j1.setAlias(input.nextLine());
        j1.setPuntos(0);

        System.out.println("Jugador Creado");

        respaldo.getListaJugadores().add(j1);

        Juego.mostrarSwitch(input, respaldo);

    }

    public static void mostrarSwitch(Scanner input, Sistema respaldo) {

        int opcion = input.nextInt();

        switch (opcion) {
            //Crear Jugador
            case 1:
                Juego.crearJugador(respaldo, input);
                break;
            //Nueva Partida
            case 2:
                break;
            //Mostrar Lista de partidas
            case 3:
                break;
            //Ranking de jugadores
            case 4:
                Juego.ordenarYMostrarJugadores(respaldo);
                break;
            //Fin
            case 5:
                break;
        }
    }

    public static void ordenarYMostrarJugadores(Sistema respaldo) {
        Collections.sort(respaldo.getListaJugadores());
        for(int i=0; i<respaldo.getListaJugadores().size();i++){
            System.out.println(respaldo.getListaJugadores().get(i));
        }
    }
}
