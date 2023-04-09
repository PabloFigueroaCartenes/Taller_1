/**
 *  Taller 1
 *  Programa que imita al protector de pantalla "Mistify" de Windows 3.x
 * @Author Pablo Ignacio Figueroa Cártenes
 */

import edu.princeton.cs.stdlib.StdDraw;

public class Main {
    public static void main(String[] args) {

        float min = -1.0f;
        float max = 1.0f;

        float [] posicion = new float[4];
        posicion[0] = (float) (2*Math.random()-1); posicion[1] = (float) (2*Math.random()-1);
        posicion[2] = (float) (2*Math.random()-1); posicion[3] = (float) (2*Math.random()-1);
        StdDraw.setXscale(min, max);
        StdDraw.setYscale(min, max);
        StdDraw.enableDoubleBuffering();
        // Interfaz (luz) para el angulo de rebote
        while ( true ) {
            posicion = dibujo(posicion);
        }
    }

    /**
     *  Metodo para comprobar si algun extremo de la linea colisiona con el borde
     *  de la ventada
     * @param x Posición x de un extremo de la linea
     * @param y Posición y de un extremo de la linea
     * @return Valor de verdad si el extremo colisiono con algun borde.
     */
    public static boolean choque(float x, float y) {
        boolean colision = false;
        if ( x >= 1.0f || x <= -1.0f || y >= 1.0f || y <= -1.0f ) {
            colision = true;
        }
        return colision;
    }

    /**
     * Metodo para obtener el vector dirección posterior a una colisión con el
     * borde de la ventana
     * @return Vector dirección de un extremo de la linea
     */
    public static float [] choque() {
        int [] distintos = {-1,1};
        int [] opuestos = {-1,-1};
        int [] inc = new int[2];

        int selector = (int) Math.floor(4*Math.random());
        switch ( selector ) {
            case 0:
                inc = distintos;
                break;
            case 1:
                inc[0] = distintos[0] * opuestos[0];
                inc[1] = distintos[1] * opuestos[1];
                break;
            case 2:
                inc = opuestos;
                break;
            case 3:
                inc[0] = opuestos[0]*opuestos[0];
                inc[1] = opuestos[1]*opuestos[1];
                break;
        }
        float [] INC = {10*inc[0] * (float) Math.random(), 10*inc[1] * (float) Math.random()};
        return INC;
    }

    /**
     *  Metodo para corregir y mantener la posición de los extremos de la linea posterior
     *  a una colisión con el borde de la ventana dentro de estos..
     * @param pos Vector de 4 valores, Posiciones x e y de cada extremo de la linea
     * @param inc Incremento de un extremo de la linea
     * @param inc2 Incremento del otro extremo de la linea
     * @return El mismo vector de entrada
     */
    public static float [] limites(float [] pos, float [] inc, float [] inc2) {
        while ( choque(pos[0],pos[1]) ^ choque(pos[2],pos[3]) ) {
            if (pos[0] >= 1.0f) {
                pos[0] -= inc[0] * 0.001f;
                return pos;
            } else if (pos[1] >= 1.0f) {
                pos[1] -= inc[1] * 0.001f;
                return pos;
            } else if (pos[0] <= -1.0f) {
                pos[0] -= inc[0] * 0.001f;
                return pos;
            } else if (pos[1] <= -1.0f) {
                pos[1] -= inc[1] * 0.001f;
                return pos;
            } else if ( pos[2] >= 1.0f ) {
                pos[2] -= inc2[0] * 0.001f;
                return pos;
            } else if ( pos[3] >= 1.0f ) {
                pos[3] -= inc2[1] * 0.001f;
                return pos;
            } else if ( pos[2] <= -1.0f ) {
                pos[2] -= inc2[0] * 0.001f;
                return pos;
            } else if ( pos[3] <= -1.0f ) {
                pos[3] -= inc2[1] * 0.001f;
                return pos;
            }
        }
        return pos;
    }

    /**
     *  Metodo que actualiza la posición de la linea y la dibuja en la pantalla.
     * @param pos Vector de 4 valores, Posiciones x e y de cada extremo de la linea
     * @return El mismo Vector.
     */
    public static float [] dibujo(float [] pos) {

        float [] inc = choque();
        float [] inc2 = choque();
        int contador = 0;
        while ( !choque(pos[0], pos[1]) && !choque(pos[2], pos[3]) ) {
            pos[0] += inc[0] * 0.0001f;
            pos[2] += inc2[0] * 0.0001f;
            pos[1] += inc[1] * 0.0001f;
            pos[3] += inc2[1] * 0.0001f;
            if ( contador % 200 == 0 ) {
                StdDraw.clear();
            }
            if (contador % 25 == 0 ) {
                StdDraw.line(pos[0],pos[1],pos[2],pos[3]);
            }
            if ( contador % 2 == 0 ) {
                StdDraw.show();
            }
            contador++;
        }
        return limites(pos, inc, inc2);
    }
}