/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brkga;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author User
 */
public class Cruzamento {

    private final ArrayList<Cromossomo> populacaoElite;
    private final ArrayList<Cromossomo> populacaoNaoElite;

    private final float vicio;

    private final int tamPopElite;
    private final int tamPopNaoElite;
    private final int tamSol;
    private final float matriz[][];

    private static final Random r = new Random();

    public Cruzamento(ArrayList<Cromossomo> populacaoElite, ArrayList<Cromossomo> populacaoNaoElite, float vicio) {
        this.populacaoElite = populacaoElite;
        this.tamPopElite = populacaoElite.size();
        this.populacaoNaoElite = populacaoNaoElite;
        this.tamPopNaoElite = populacaoNaoElite.size();
        this.vicio = vicio;
        this.tamSol = populacaoElite.get(0).getTamSol();
        this.matriz = populacaoElite.get(0).getMatrizDistancia();
    }

    public Cromossomo nextFilho() {
        Cromossomo paiElite = populacaoElite.get(r.nextInt(tamPopElite));
        Cromossomo paiNaoElite = populacaoNaoElite.get(r.nextInt(tamPopNaoElite));

        float vetorPaiElite[] = paiElite.getSolucao();
        float vetorPaiNaoElite[] = paiNaoElite.getSolucao();

        float[] vetorFilho = new float[tamSol];
        for (int i = 0; i < tamSol; i++) {
            if (r.nextFloat() <= vicio) {
                vetorFilho[i] = vetorPaiElite[i];
            } else {
                vetorFilho[i] = vetorPaiNaoElite[i];
            }
        }

        Cromossomo filho = new Cromossomo(vetorFilho, matriz);

        return filho;
    }
}
