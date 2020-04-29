/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brkga;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 *
 * @author User
 */
public class Cromossomo implements Comparable<Cromossomo> {

    private final float[] solucao;
    private final int tamSol;
    private static float[][] matrizDistancia;

    private final float avaliacao;

    public Cromossomo(int tamSol, float[][] matrizDistancia) {
        this.tamSol = tamSol;
        this.matrizDistancia = matrizDistancia;
        Random r = new Random();
        this.solucao = new float[tamSol];
        for (int i = 0; i < this.tamSol; i++) {
            this.solucao[i] = r.nextFloat();
        }

        this.avaliacao = avaliarCromossomo();
    }

    public Cromossomo(float[] solucao, float[][] matrizDistancia) {
        this.solucao = solucao;
        this.tamSol = solucao.length;
        this.matrizDistancia = matrizDistancia;
        this.avaliacao = avaliarCromossomo();
    }

    public float getAvaliacao() {
        return avaliacao;
    }

    public float[] getSolucao() {
        return solucao;
    }

    public int getTamSol() {
        return solucao.length;
    }

    public float[][] getMatrizDistancia() {
        return matrizDistancia;
    }

    private float avaliarCromossomo() {
        ArrayList<Tupla> listaTuplas = new ArrayList<>();
        for (int i = 0; i < this.tamSol; i++) {
            Tupla t = new Tupla();

            t.valor = this.solucao[i];
            t.posicao = i;

            listaTuplas.add(t);
        }
        Collections.sort(listaTuplas);

        int A = listaTuplas.get(0).posicao;
        int B = listaTuplas.get(1).posicao;
        float acum = matrizDistancia[A][B];
        for (int i = 2; i < this.tamSol; i++) {
            A = B;
            B = listaTuplas.get(i).posicao;
            acum += matrizDistancia[A][B];
        }
        A = B;
        B = listaTuplas.get(0).posicao;
        acum += matrizDistancia[A][B];
        
        return acum;
    }

    public void mostrarTrajeto() {
        ArrayList<Tupla> listaTuplas = new ArrayList<>();
        for (int i = 0; i < this.tamSol; i++) {
            Tupla t = new Tupla();

            t.valor = this.solucao[i];
            t.posicao = i;

            listaTuplas.add(t);
        }
        Collections.sort(listaTuplas);
        for (Tupla t : listaTuplas) {
            System.out.print(t.posicao + " ");
        }
        System.out.println();
    }

    @Override
    public int compareTo(Cromossomo outroObjeto) {
        if (getAvaliacao() > outroObjeto.getAvaliacao()) {
            return 1;
        } else if (getAvaliacao() == outroObjeto.getAvaliacao()) {
            return 0;
        } else {
            return -1;
        }
    }

}
