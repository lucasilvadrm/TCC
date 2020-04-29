/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infeccao.brkga;

import brkga.Cromossomo;
import brkga.Cruzamento;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import leitura.LeituraScanner;

/**
 *
 * @author User
 */
public class PrincipalInfeccao {

    public static void main(String[] args) throws IOException {

        int tamPop = 200;
        int tamPopMutante = (int) (tamPop * .1);//20% da população
        int tamPopElite = (int) (0.2 * tamPop); //30% da população
        int tamPopFilhos = (int) (0.4 * tamPop);//40% da população
        int tamPopInfectados = (int) (0.3 * tamPop); //20% da população
        float vicioProbabilistico = 0.7f;
        int numeroGeracoes = 0;
        float[][] mapa = LeituraScanner.leitura();
        int tamSol = mapa.length;
        int tamanhoInicialVirus = (int) 2 ; //(0.1 * tamSol)
        int tamanhoMaxVirus = 6;
        int tamanhoPopVirus = 5;
        //int tamanhoPopAInfectar = (int) (tamPopElite/2); //infecta metade da população elite
        Random r = new Random();

        long tempoInicial = System.currentTimeMillis(); // tempo inicial de execução
        ArrayList<Cromossomo> populacao = new ArrayList<>();
        for (int i = 0; i < tamPop; i++) {
            populacao.add(new Cromossomo(tamSol, mapa));
        }

        Virus virus = new Virus(tamanhoInicialVirus, tamanhoMaxVirus);
        //criando população de vírus
        ArrayList<Virus> popVirus = new ArrayList<>();

        for (int i = 0; i < tamanhoPopVirus; i++) {
            popVirus.add(new Virus(tamanhoInicialVirus, tamanhoMaxVirus));
        }

        float melhorAvaliacao = Float.MAX_VALUE;
        //System.out.println(melhorAvaliacao);
        
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
            //imprimir o vírus
            /*for (Virus v : popVirus) {
             System.out.print("Vírus: ");
             for (float f : v.getVirus()) {
             System.out.print(f + " ");
             }
             System.out.println(" --> " + v.getTaxaInfectividade());
             }*/

            ArrayList<Cromossomo> novaPopulacao = new ArrayList<>();
            ArrayList<Cromossomo> populacaoInfectados = new ArrayList<>();
            //infecção
            for (int i = 0; i < tamPopInfectados; i++) {
                virus = popVirus.get(r.nextInt(tamanhoPopVirus));//inicializa o vírus com um vírus da população
                Cromossomo cromossomoAInfectar = populacaoElite.get(r.nextInt(tamPopElite));//pega um cromossomo da popElite aleatoriamente
                
                //for (int j = 0; j < tamPopInfectados; j++) {
                    populacaoInfectados.add(virus.infeccao(cromossomoAInfectar));
                //}
            }

            for (int j = 0; j < tamanhoPopVirus; j++) {
                virus = popVirus.get(j);
                if (virus.getTaxaInfectividade() <= 0) {
                    //System.out.println("novo virus");
                    popVirus.set(j, new Virus(tamanhoInicialVirus, tamanhoMaxVirus));
                    //cont = 0;
                }
            }
            novaPopulacao.addAll(populacaoInfectados);

            //adiciona a população elite à novaPopulação
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
            System.out.println("Pop infectados: "+populacaoInfectados.size());
            System.out.println("Pop mutantes: "+populacaoMutante.size());
            System.out.println("Pop n elite: "+populacaoNaoElite.size());*/
        }
        //pegando o cromossomo com menor avaliação
        Cromossomo melhor = Collections.min(populacao);

        for (float f : melhor.getSolucao()) {
            System.out.print(f + " ");
        }
        
        
        System.out.println(" --> " + melhor.getAvaliacao());
        System.out.println();
        melhor.mostrarTrajeto();
        System.out.println("AVALIAÇÃO: " + melhor.getAvaliacao());
        System.out.println("tamanho população: "+populacao.size());
        System.out.println("Tamanho do vírus: "+tamanhoInicialVirus);

        long tempoFinal = System.currentTimeMillis();
        System.out.printf("Tempo de execução: %.3f s%n", (tempoFinal - tempoInicial) / 1000d);
    }
}
