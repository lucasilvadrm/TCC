/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brkga;

/**
 *
 * @author User
 */
public class Tupla implements Comparable<Tupla> {//ordenar os indivíduos e manter as posições

    float valor;
    int posicao;
    
    public int compareTo(Tupla t) {
        if (this.valor > t.valor) {
            return 1;
        } else if (this.valor == t.valor) {
            return 0;
        } else {
            return -1;
        }
    }
}
