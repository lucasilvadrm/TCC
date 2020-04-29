/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brkga;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import leitura.LeituraScanner;

/**
 *
 * @author User
 */
public class Principal {

    public static void main(String[] args) throws IOException {

        int tamPop = 200;
        int tamPopMutante = (int) (tamPop * .1);//20% da população
        int tamPopElite = (int) (0.2 * tamPop); //30% da população
        int tamPopFilhos = tamPop - tamPopElite - tamPopMutante;
        float vicioProbabilistico = 0.7f;
        int numeroGeracoes = 0;
        float[][] mapa = LeituraScanner.leitura();
        int tamSol = mapa.length;

        long tempoInicial = System.currentTimeMillis(); // tempo inicial de execução
        ArrayList<Cromossomo> populacao = new ArrayList<>();
        for (int i = 0; i < tamPop; i++) {
            populacao.add(new Cromossomo(tamSol, mapa));
        }
        float melhorAvaliacao = Float.MAX_VALUE;
        while (numeroGeracoes < 3000) {

            Collections.sort(populacao);

            ArrayList<Cromossomo> populacaoElite = new ArrayList<>();
            ArrayList<Cromossomo> populacaoNaoElite = new ArrayList<>();
            //adiciona 30% dos melhores cromossmomos de uma população na lista populacaoElite, o restante é add na lista populacaoNaoElite
            int contador = 0;
            for (Cromossomo c : populacao) {
                if (contador < tamPopElite) {
                    populacaoElite.add(c);
                    contador++;
                } else {
                    populacaoNaoElite.add(c);
                }
            }

            //adiciona a população elite à novaPopulação
            ArrayList<Cromossomo> novaPopulacao = new ArrayList<>();
            novaPopulacao.addAll(populacaoElite);

            //populacao mutante
            ArrayList<Cromossomo> populacaoMutante = new ArrayList<>();
            for (int i = 0; i < tamPopMutante; i++) {
                populacaoMutante.add(new Cromossomo(tamSol, mapa));
            }
            novaPopulacao.addAll(populacaoMutante);

            Cruzamento cruzamento = new Cruzamento(populacaoElite, populacaoNaoElite, vicioProbabilistico);
            //Cromossomo filho = cruzamento.nextFilho();

            //criando população de filhos
            ArrayList<Cromossomo> populacaoFilho = new ArrayList<>();
            for (int i = 0; i < tamPopFilhos; i++) {
                populacaoFilho.add(cruzamento.nextFilho());
            }
            //adicionando a população de filhos à nova população
            novaPopulacao.addAll(populacaoFilho);

            populacao = novaPopulacao;

            numeroGeracoes++;

            Cromossomo melhor = Collections.min(populacao);
            if (melhor.getAvaliacao() < melhorAvaliacao) {
                System.out.print("Geracao " + numeroGeracoes + ": ");
                melhorAvaliacao = melhor.getAvaliacao();
                System.out.println(melhorAvaliacao);
            }
            
            /*System.out.println("População elite: "+populacaoElite.size());
            System.out.println("População filhos: "+populacaoFilho.size());
            //System.out.println("Pop infectados: "+populacaoInfectados.size());
            System.out.println("Pop mutantes: "+populacaoMutante.size());
            System.out.println("Pop n elite: "+populacaoNaoElite.size());*/
        }
        //pegando o cromossomo com menor avaliação
        Cromossomo melhor = Collections.min(populacao);

        for (float f : melhor.getSolucao()) {
            System.out.print(f + " ");
        }
        System.out.println(" --> " + melhor.getAvaliacao());

        melhor.mostrarTrajeto();
        System.out.println();
        System.out.println("AVALIAÇÃO: " + melhor.getAvaliacao());

        long tempoFinal = System.currentTimeMillis();
        //long tempoTotal = ((tempoFinal - temp)) / 1000;
        //System.out.println("\nTempo total de execução: " + tempoTotal);
        System.out.printf("Tempo de execução: %.3f s%n", (tempoFinal - tempoInicial) / 1000d);
    }
}
