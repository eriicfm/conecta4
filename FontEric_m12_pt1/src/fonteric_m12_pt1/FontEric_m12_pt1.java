package fonteric_m12_pt1;

import java.util.Scanner;

public class FontEric_m12_pt1 {

    public static void main(String[] args) {

        Scanner teclat = new Scanner(System.in);

        System.out.println("Com és diu el primer jugador?");
        String nomJugador1 = teclat.nextLine();
        char jugador1 = 'X';
        System.out.println("Com és diu el segon jugador?");
        String nomJugador2 = teclat.nextLine();
        char jugador2 = 'O';

        int files = getIntInput(teclat, "Introdueix les files: ");
        int columnes = getIntInput(teclat, "Introdueix les columnes: ");
        char[][] matriu = new char[files][columnes];
        inicialitzarTauler(matriu);

        boolean fiPartida = false;
        boolean tornJugador1 = true;
        int contTorn = 0;
        int totalc = files * columnes;

        System.out.println("\nCOMENÇA EL JOC:\n---------------\n");

        while (!fiPartida) {

            char jugadorActual = tornJugador1 ? jugador1 : jugador2;

            mostrarTauler(matriu, nomJugador1, nomJugador2);

            int fitxa = getIntInput(teclat, "\njugador (" + jugadorActual + "), introdueix una fitxa a una columna: ") - 1;
            
            if (fitxa >= columnes || fitxa < 0) {
                System.out.println("Columna no vàlida!");
                continue;
            }

            if (!colocarFitxa(matriu, fitxa, jugadorActual)) {
                System.out.println("Columna plena, escull una altra.");
                continue;
            }

            contTorn++;
            if (comprovarGuanyador(matriu, jugadorActual)) {
                mostrarTauler(matriu, nomJugador1, nomJugador2);
                System.out.println("Guanyador: " + jugadorActual);
                fiPartida = true;
            } else if (contTorn == totalc) {
                mostrarTauler(matriu, nomJugador1, nomJugador2);
                System.out.println("------\nEMPAT!\n------");
                fiPartida = true;
            }

            tornJugador1 = !tornJugador1;
        }

        System.out.println("Vols tornar a jugar?\n1. Si\n2. No");
        int jugar = teclat.nextInt();
        if (jugar == 1) {
            main(args); // Reiniciar el joc
        }
    }

    private static int getIntInput(Scanner teclat, String message) {
        System.out.println(message);
        while (!teclat.hasNextInt()) {
            teclat.next();
            System.out.println("Error, ha de ser un int");
        }
        return teclat.nextInt();
    }

    private static void inicialitzarTauler(char[][] matriu) {
        for (int i = 0; i < matriu.length; i++) {
            for (int j = 0; j < matriu[i].length; j++) {
                matriu[i][j] = ' ';
            }
        }
    }

    private static void mostrarTauler(char[][] matriu, String nomJugador1, String nomJugador2) {

        int files = matriu.length;
        int columnes = matriu[0].length;
        int filesPintades = files;

        for (int i = 0; i < (files * 2) + 1; i++) {
            if (i % 2 == 0) {
                System.out.printf("%2s", " ");
                for (int j = 0; j < (columnes * 4) + 1; j++) {
                    System.out.print("-");
                }
                System.out.println();
            } else {
                System.out.printf("%2s", filesPintades--);
                for (int j = 0; j < columnes; j++) {
                    System.out.print("| " + matriu[i / 2][j] + " ");
                }
                System.out.println("|");
            }
        }

        System.out.print("  ");
        for (int j = 0; j < columnes; j++) {
            System.out.printf("%3d ", (j + 1));
        }

        System.out.println();

        System.out.println("Jugador 1 (X): " + nomJugador1);
        System.out.println("Jugador 2 (O): " + nomJugador2);
    }

    private static boolean colocarFitxa(char[][] matriu, int columna, char jugador) {
        for (int i = matriu.length - 1; i >= 0; i--) {
            if (matriu[i][columna] == ' ') {
                matriu[i][columna] = jugador;
                return true;
            }
        }
        return false;
    }

    private static boolean comprovarGuanyador(char[][] matriu, char jugador) {
        return comprovarHoritzontal(matriu, jugador) || comprovarVertical(matriu, jugador) || comprovarDiagonal(matriu, jugador);
    }

    private static boolean comprovarHoritzontal(char[][] matriu, char jugador) {
        for (int i = 0; i < matriu.length; i++) {
            int recompte = 0;
            for (int j = 0; j < matriu[i].length; j++) {
                if (matriu[i][j] == jugador) {
                    recompte++;
                    if (recompte >= 4) {
                        return true;
                    }
                } else {
                    recompte = 0;
                }
            }
        }
        return false;
    }

    private static boolean comprovarVertical(char[][] matriu, char jugador) {
        for (int i = 0; i < matriu[0].length; i++) {
            int recompte = 0;
            for (int j = 0; j < matriu.length; j++) {
                if (matriu[j][i] == jugador) {
                    recompte++;
                    if (recompte >= 4) {
                        return true;
                    }
                } else {
                    recompte = 0;
                }
            }
        }
        return false;
    }

    private static boolean comprovarDiagonal(char[][] matriu, char jugador) {
        return comprovarDiagonalAscendent(matriu, jugador) || comprovarDiagonalDescendent(matriu, jugador);
    }

    private static boolean comprovarDiagonalAscendent(char[][] matriu, char jugador) {
        for (int i = 3; i < matriu.length; i++) {
            for (int j = 0; j < matriu[i].length - 3; j++) {
                if (matriu[i][j] == jugador && matriu[i - 1][j + 1] == jugador && matriu[i - 2][j + 2] == jugador && matriu[i - 3][j + 3] == jugador) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean comprovarDiagonalDescendent(char[][] matriu, char jugador) {
        for (int i = 0; i < matriu.length - 3; i++) {
            for (int j = 0; j < matriu[i].length - 3; j++) {
                if (matriu[i][j] == jugador && matriu[i + 1][j + 1] == jugador && matriu[i + 2][j + 2] == jugador && matriu[i + 3][j + 3] == jugador) {
                    return true;
                }
            }
        }
        return false;
    }
}
