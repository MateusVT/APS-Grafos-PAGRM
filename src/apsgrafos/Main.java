/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apsgrafos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author leotr
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Arquivo fr = new Arquivo();
        List<Grafo> grafos = fr.leitor("C:/Users/leotr/Documents/BCC/7semestre/Grafos/APS Grafos Aux/APS/APS.Teoria.dos.Grafos.2017.1/instancias/GROUP 1/HDGraph20_20.txt");
        AlgGenetico alg = new AlgGenetico();
        List<Integer> quantidadeRotulos = new ArrayList<>();
        List<Float> tempoGrafo = new ArrayList<>();
        List<List<Integer>> aux = new ArrayList<>();

        for (Grafo grafo : grafos) {
            long tempoInicial = System.currentTimeMillis();
            List<Integer> listaRotulos1 = alg.individuo(grafo);

            List<Integer> listaRotulos2 = alg.individuo(grafo);
            if (listaRotulos1 != null && listaRotulos2 != null) {
                listaRotulos1 = union(listaRotulos1, listaRotulos2);
                List<Integer> crossover = alg.crossover(grafo, listaRotulos1);
                List<Integer> mutation = alg.mutation(grafo, crossover);
                aux.add(mutation);
                quantidadeRotulos.add(mutation.size());
                long tempoFinal = System.currentTimeMillis();
                float tempo = (tempoFinal - tempoInicial);
                tempoGrafo.add(tempo);
            }
        }
        float somaDeRotulos = 0;
        float tempoTotal = 0;
        for (int i = 0; i < quantidadeRotulos.size(); i++) {
            somaDeRotulos += quantidadeRotulos.get(i);
            tempoTotal += tempoGrafo.get(i);
        }
        tempoTotal = tempoTotal / tempoGrafo.size();
        System.out.println("Media de Rotulos: " + somaDeRotulos / quantidadeRotulos.size());

        System.out.println("Tempo Médio: " + tempoTotal + " ms");
        fr.gravar(aux, (somaDeRotulos/quantidadeRotulos.size()), (tempoTotal/tempoGrafo.size()));

    }

    public static <T> List<T> union(List<T> list1, List<T> list2) {
        Set<T> set = new HashSet<T>();

        set.addAll(list1);
        set.addAll(list2);

        return new ArrayList<T>(set);
    }

}
