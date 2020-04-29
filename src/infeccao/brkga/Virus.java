/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infeccao.brkga;

import brkga.Cromossomo;
import java.util.Random;

/**
 *
 * @author User
 */
public class Virus {

    private int taxaInfectividadeInicial = 2;
    private int taxaInfectividade;
    private int taxaInfectividadeMaxima = 5;
    private int tamanhoInicialVirus;
    private int tamanhoMaxVirus;
    //private int tamPopElite;
    private int tamSol;
    private float matriz[][];
    //private ArrayList<Cromossomo> populacaoElite = new ArrayList<>();
    private float[] virus;

    private Random r = new Random(System.nanoTime());

    public Virus(int tamanhoInicialVirus, int tamanhoMaxVirus) {

        this.tamanhoInicialVirus = tamanhoInicialVirus;
        this.tamanhoMaxVirus = tamanhoMaxVirus;
        virus = new float[tamanhoInicialVirus];
        for (int i = 0; i < tamanhoInicialVirus; i++) {
            virus[i] = r.nextFloat();
        }

        taxaInfectividade = taxaInfectividadeInicial;

    }

    public Cromossomo infeccao(Cromossomo cromossomoElite) {

        float cromossomoEliteTemp[] = cromossomoElite.getSolucao();
        this.matriz = cromossomoElite.getMatrizDistancia();
        this.tamSol = cromossomoElite.getTamSol();
        float cromossomoTemp[] = new float[this.tamSol];
        System.arraycopy(cromossomoEliteTemp, 0, cromossomoTemp, 0, this.tamSol);

        //float aptdaoAnterior = cromossomoElite.getAvaliacao();
        //int posNoCromossomo = r.nextInt((this.tamSol - tamanhoInicialVirus) - 1);

        Cromossomo melhorInfectado = new Cromossomo(cromossomoEliteTemp, matriz);

        //System.out.println("Cromossomo Selecionado:" + melhorInfectado.getAvaliacao());

        float[] cromossomoInfectado = cromossomoTemp;
        float aptidaoIndividuoInfectado = Float.MAX_VALUE;
        //System.out.println(aptidaoIndividuoInfectado);
        /*for (int i = 0; i < tamanhoInicialVirus; i++) {
            cromossomoInfectado[posNoCromossomo] = virus[i];
            posNoCromossomo++;
        }*/
        for (int i = 0; i < cromossomoInfectado.length - tamanhoInicialVirus + 1; i++) {

            float infectadoTemp[] = new float[this.tamSol];

            System.arraycopy(cromossomoInfectado, 0, infectadoTemp, 0, this.tamSol);

            int pos = i;

            for (int j = 0; j < virus.length; j++) {

                infectadoTemp[pos] = virus[j];
                pos++;

            }
          
            Cromossomo cromossomo_infectado = new Cromossomo(infectadoTemp, matriz);
            //System.out.println("Aptidao individuo infectado:" + aptidaoIndividuoInfectado);
            //System.out.println("Cromossomo:" + cromossomo_infectado.getAvaliacao());
            if (cromossomo_infectado.getAvaliacao() < aptidaoIndividuoInfectado) {

                //System.out.println("Melhorou a posição");
                aptidaoIndividuoInfectado = cromossomo_infectado.getAvaliacao();
                melhorInfectado = cromossomo_infectado;

                //System.out.println("Aptidao individuo infectado:" + aptidaoIndividuoInfectado);

                //System.out.println("Melhor posição:" + pos);

            }

        }

        /*
        if (aptdaoAnterior > melhorInfectado.getAvaliacao()) {
            System.out.println("Aptidão anterior:" + aptdaoAnterior);
            System.out.println("Aptidão nova:" + melhorInfectado.getAvaliacao());
            System.out.println("Melhorou!!!!");
        } else {
            System.out.println("Aptidão anterior:" + aptdaoAnterior);

            System.out.println("Aptidão nova:" + melhorInfectado.getAvaliacao());
            System.out.println("não melhorou");
        }
        //taxaInfectividade = taxaInfectividadeInicial;
         */
        if (melhorInfectado.getAvaliacao() < cromossomoElite.getAvaliacao()) {
            taxaInfectividade++;
            //System.out.println("Melhorou");
            //System.out.println("Melhor infectado: " + melhorInfectado.getAvaliacao());
            //System.out.println("Cromossomo Elite: " + cromossomoElite.getAvaliacao());
        } else {
            taxaInfectividade--;
            //System.out.println("N Melhorou");
            //System.out.println("Melhor infectado: " + melhorInfectado.getAvaliacao());
            //System.out.println("Cromossomo Elite: " + cromossomoElite.getAvaliacao());
        }

        //System.out.println("Melhor Infectado" + melhorInfectado.getSolucao());
        return melhorInfectado;
    }

    public float[] getVirus() {
        return virus;
    }

    public int getTaxaInfectividade() {
        return taxaInfectividade;
    }

    public int getTamanhoInicialVirus() {
        return tamanhoInicialVirus;
    }

    public int getTaxaInfectividadeInicial() {
        return taxaInfectividadeInicial;
    }

    public int getTaxaInfectividadeMaxima() {
        return taxaInfectividadeMaxima;
    }

}
