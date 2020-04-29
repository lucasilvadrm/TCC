/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import brkga.Cromossomo;
import brkga.Cruzamento;
import java.io.IOException;

import java.util.ArrayList;

import java.util.Collections;


import leitura.LeituraScanner;

/**
 *
 * @author User
 */
public class Main {

    //private static int tamPopElite;

    public static void main(String[] args) throws IOException {
        int tamPop = 30;
        ArrayList<Cromossomo> populacao = new ArrayList<>();
        int tamPopMutante = (int) (tamPop * .2);
        int tamPopElite = (int) (0.3 * tamPop); //30% da população
        int tamPopFilhos = tamPop - tamPopElite - tamPopMutante;
        int tamSol = 58;
        float[][] mapa;
        mapa = LeituraScanner.leitura();

        //Cromossomo cromossomo = new Cromossomo(tamSol, mapa);
        for (int i = 0; i < tamPop; i++) {
            populacao.add(new Cromossomo(tamSol, mapa));
        }
        Collections.sort(populacao);

        for (Cromossomo lista : populacao) {
            for (float f : lista.getSolucao()) {
                System.out.print(f + " ");
            }
            System.out.println(" --> " + lista.getAvaliacao());
        }

        
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

        Cruzamento cruzamento = new Cruzamento(populacaoElite, populacaoNaoElite, (float) 0.7);

        System.out.println();
        System.out.println("POPULAÇÃO ELITE:");
        for (Cromossomo c : populacaoElite) {
            for (float f : c.getSolucao()) {
                System.out.print(f + " ");
            }
            System.out.println(" --> " + c.getAvaliacao());
        }

        System.out.println();
        System.out.println("POPULAÇÃO NÃO ELITE:");
        for (Cromossomo c : populacaoNaoElite) {
            for (float f : c.getSolucao()) {
                System.out.print(f + " ");
            }
            System.out.println(" --> " + c.getAvaliacao());
        }

        System.out.println();
        System.out.println("CROMOSSOMO FILHO");
        Cromossomo filho = cruzamento.nextFilho();
        for (float f : filho.getSolucao()) {
            System.out.print(f + " ");
        }
        System.out.println(" --> " + filho.getAvaliacao());
        
        //criando população de filhos
        
        ArrayList<Cromossomo> populacaoFilho = new ArrayList<>();
        for (int i = 0; i < tamPopFilhos; i++) {
            populacaoFilho.add(cruzamento.nextFilho());
        }
        //adicionando a população de filhos à nova população
        novaPopulacao.addAll(populacaoFilho);
        

        //Collections.sort(populacaoFilho);
        System.out.println();
        System.out.println("POPULAÇÃO DE CROMOSSOMO FILHO");
        for (Cromossomo lista : populacaoFilho) {
            for (float f : lista.getSolucao()) {
                System.out.print(f + " ");
            }
            System.out.println(" --> " + lista.getAvaliacao());
        }

    }
}
