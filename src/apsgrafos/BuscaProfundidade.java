/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apsgrafos;

import java.util.Comparator;

/**
 *
 * @author leotr
 */
public class BuscaProfundidade {
    public static boolean InitProfundidade(Grafo grafo) {
        grafo.componentesConexas = 0;
        for (Vertice u : grafo.getListaAdjacencia().keySet()) {
            u.setCor("Branco");
            u.setPi(null);
            grafo.setTempo(0);
        }
        for (Vertice u : grafo.getListaAdjacencia().keySet()) {
            if (u.getCor().equals("Branco")) {
                BuscaProfundidade(grafo, u);
                grafo.componentesConexas++;
            }
        }
        if(grafo.componentesConexas == 1){
            return true;
        }
        return false;
    }

    public static void BuscaProfundidade(Grafo grafo, Vertice vertice) {
        grafo.tempo += 1;
        vertice.setTd((Integer) grafo.tempo);
//        System.out.println("\ndescoberto vertice: "+ vertice.id + "  cor:  "+ vertice.cor + " Tempo descoberto: " + vertice.getTempoDescoberto());
        vertice.setCor("Cinza");
//        System.out.println("\n sou vertice: "+ vertice.id + " me pintei cor:  "+ vertice.cor);
        for (Aresta ar : grafo.getListaAdjacencia().get(vertice)) {
            Vertice v = ar.getD();
            if (v.getCor().equals("Cinza")) {
                grafo.setCiclico(true);
            } else if (v.getCor().equals("Branco")) {
                v.setPi(vertice);
                BuscaProfundidade(grafo, v);
            }
        }

        vertice.setCor("Preto");
        grafo.setTempo(grafo.tempo + 1);
        vertice.setTf(grafo.getTempo());
//        System.out.println("\n sou vertice: "+ vertice.id + " finalizei :  "+ vertice.cor + " Tempo finalizado: "+ vertice.tf);
    }

    class ComparadorVertices implements Comparator<Vertice> {

        public int compare(Vertice v1, Vertice v2) {
            if (v1.tf > v2.tf) {
                return -1;
            } else if (v1.tf < v2.tf) {
                return +1;
            } else {
                return 0;
            }
        }
    }

}
