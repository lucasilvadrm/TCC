
import java.lang.Comparable;
import java.util.ArrayList;
import java.util.Random;
import java.util.Collections;

public class Classe {

    static class Tupla implements Comparable<Tupla> {

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

    public static void main(String args[]) {
        Random r = new Random();

        ArrayList<Tupla> listaTuplas = new ArrayList<>();

        //int tamSol = 12;
        float matriz[][] = {{0, 42, 61, 30, 17, 82, 31, 11},
                {42, 0, 14, 87, 28, 70, 19, 33},
                {61, 14, 0, 20, 81, 21, 8, 29},
                {30, 87, 20, 0, 34, 33, 91, 10},
                {17, 28, 81, 34, 0, 41, 34, 82},
                {82, 70, 21, 33, 41, 0, 19, 32},
                {31, 19, 8, 91, 34, 19, 0, 59},
                {11, 33, 29, 10, 82, 32, 59, 0}};
        
        float[] solucao = new float[matriz.length];
        for(int i=0; i < matriz.length; i++){
            solucao[i] = r.nextFloat();
        }
        /*System.out.println("vetor solucao");
        for(float sol : solucao){
            System.out.print(sol + " ");
        }*/
        
        for (int i = 0; i < matriz.length; i++) {
            Tupla t = new Tupla();

            t.valor = solucao[i];
            t.posicao = i;

            listaTuplas.add(t);
        }
        
        //cria a matriz
        /*for (int i = 0; i < tamSol; i++) {
            matriz[i][i] = 0;
            for (int j = i + 1; j < tamSol; j++) {
                matriz[i][j] = r.nextFloat();
                matriz[j][i] = r.nextFloat();
            }
        }*/
        //exibe a matriz
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                System.out.print(matriz[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();

        //exibe a lista de tuplas 
        for (Tupla t : listaTuplas) {
            System.out.print(t.posicao + " ");
        }
        System.out.println();

        Collections.sort(listaTuplas);//decodificação
        //exibe a tupla decodificada
        for (Tupla t : listaTuplas) {
            System.out.println("Posição: " + t.posicao + " valor: " + t.valor);
        }
        System.out.println();

        int A = listaTuplas.get(0).posicao;
        int B = listaTuplas.get(1).posicao;
        float acum = matriz[A][B];
        for (int i = 2; i < matriz.length; i++) {
            A = B;
            B = listaTuplas.get(i).posicao;
            acum += matriz[A][B];
        }
        A = B;
        B = 0;
        acum += matriz[A][B];

        System.out.println("Valor da avaliação: " + acum);
    }
}
