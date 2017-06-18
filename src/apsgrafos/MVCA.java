/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apsgrafos;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author leotr
 */
public class MVCA {

    private HashMap<Integer, Integer> mapRotulos = new HashMap<>();
    private Grafo grafo = new Grafo();
    private List<Integer> listaRotulos = new ArrayList<>();
    private List<Integer> vertices = new ArrayList<>();
    private List<Integer> verticesConectados = new ArrayList<>();
    private ArrayList<Rotulo> rotulosMin = new ArrayList<>();

    public ArrayList<Rotulo> getRotulosMin() {
        return rotulosMin;
    }

    public void setRotulosMin(ArrayList<Rotulo> rotulosMin) {
        this.rotulosMin = rotulosMin;
    }

    public void rotulos(Grafo grafo) {
        ArrayList<Rotulo> rotulos = getRotulos(grafo);

        Grafo arvore = new Grafo();

        for (Integer i = 0; i < grafo.getListaAdjacencia().size(); i++) {
            Vertice v = new Vertice(i.toString());
            v.setVisited(false);
            arvore.insereVertice(v);

        }

        while (!BuscaProfundidade.InitProfundidade(arvore)) {
            calculaAlcancaveis(rotulos, arvore);

            Rotulo rotuloAux = rotulos.remove(0);
            rotulosMin.add(rotuloAux);

            insereArvore(rotuloAux, arvore);
        }
        System.out.print("Rótulos geradores minimos: ");

        for (int i = 0; i < rotulosMin.size(); i++) {
            System.out.print("[");
            System.out.print(rotulosMin.get(i).getId() + "");
            System.out.print("]");
        }

        System.out.println("");

    }

    public ArrayList<Rotulo> getRotulos(Grafo grafo) {

        ArrayList<Rotulo> rotulos = new ArrayList<>();

        for (Vertice v : grafo.getListaAdjacencia().keySet()) {

            for (Aresta a : grafo.getListaAdjacencia().get(v)) {

                Rotulo r = new Rotulo(a.getP());
                Aresta aresta = new Aresta(a.getD(), a.getP());
                aresta.setO(v);
                r.getArestas().add(aresta);
                r.qnt++;

                if (!exists(rotulos, r)) {

                    rotulos.add(r);
                }

            }

        }

        return rotulos;
    }

    public static boolean exists(ArrayList<Rotulo> rotulos, Rotulo rotulo) {
        for (Rotulo r : rotulos) {
            if (r.getId().equals(rotulo.getId())) {

                for (Aresta a : rotulo.getArestas()) {
                    if (!contemAresta(r, a)) {
                        r.getArestas().add(a);
                        r.qnt += rotulo.qnt;
                    }
                }
                return true;
            }
        }

        return false;
    }

    public static boolean contemAresta(Rotulo rotulo, Aresta aresta) {

        for (Aresta a : rotulo.getArestas()) {

            if (a.getO().getId().equals(aresta.getO().getId()) && a.getD().getId().equals(aresta.getD().getId())) {
                return true;
            } else if (a.getD().getId().equals(aresta.getO().getId()) && a.getO().getId().equals(aresta.getD().getId())) {
                return true;
            }

        }

        return false;
    }

    public void calculaAlcancaveis(ArrayList<Rotulo> rotulos, Grafo grafo) {

        for (Rotulo rotulo : rotulos) {
            rotulo.numAlcancaveis = 0;

            for (Aresta aresta : rotulo.getArestas()) {
                Vertice origem = grafo.getVerticeGrafo(aresta.getO().getId());
                Vertice destino = grafo.getVerticeGrafo(aresta.getD().getId());

                if (!origem.isVisited()) {
                    rotulo.setNumAlcancaveis(rotulo.getNumAlcancaveis() + 1);
                }
                if (!destino.isVisited()) {
                    rotulo.setNumAlcancaveis(rotulo.getNumAlcancaveis() + 1);
                }

            }

        }

        rotulos.sort(new comparadorRotulos());
    }

    class comparadorRotulos implements Comparator<Rotulo> {

        public int compare(Rotulo r1, Rotulo r2) {

            if (r1.numAlcancaveis > r2.numAlcancaveis) {
                return -1;
            } else if (r1.numAlcancaveis < r2.numAlcancaveis) {
                return +1;
            } else {
                return 0;
            }
        }
    }

    public void insereArvore(Rotulo rotulo, Grafo arvore) {
        for (Aresta aresta : rotulo.getArestas()) {

            Vertice origem = arvore.getVerticeGrafo(aresta.getO().getId());
            origem.setVisited(true);
            Vertice destino = arvore.getVerticeGrafo(aresta.getD().getId());
            destino.setVisited(true);

            if (!arvore.existeAdjascencia(arvore, origem, destino)) {
                arvore.insereArestaND(origem, new Aresta(destino, aresta.getP()));
            }
        }
    }

    public void getMaisFrequente() {

        int frequenciaRotulo = 0;
        int rotuloMaisFrequente = 0;

        for (int i = 0; i < grafo.getMatrizAdjacencia().length; i++) {
            for (int j = 0; j < grafo.getMatrizAdjacencia().length; j++) {

                mapRotulos.put(grafo.getMatrizAdjacencia()[i][j], mapRotulos.containsKey(grafo.getMatrizAdjacencia()[i][j]) ? mapRotulos.get(grafo.getMatrizAdjacencia()[i][j]) + 1 : 1);

            }
        }

        mapRotulos.remove(-1);

        System.out.println("Keys : " + mapRotulos.keySet());

        for (Integer key : mapRotulos.keySet()) {

            if (frequenciaRotulo < mapRotulos.get(key)) {
                frequenciaRotulo = mapRotulos.get(key);
                rotuloMaisFrequente = key;
            }

        }

        listaRotulos.add(rotuloMaisFrequente);
        System.out.println("Rótulo mais frequente : " + rotuloMaisFrequente + " Ocorre : " + frequenciaRotulo + " vezes");
        verificaConectados();

    }

    public void verificaConectados() {

        HashMap<Integer, List<Integer>> listaAdj = new HashMap<>();

        for (int i = 0; i < grafo.getMatrizAdjacencia().length; i++) {
            for (int j = 0; j < grafo.getMatrizAdjacencia().length; j++) {
                if (grafo.getMatrizAdjacencia()[i][j] == listaRotulos.get(0)) {
                    listaAdj.put(i, new ArrayList<Integer>());
                    listaAdj.get(i).add(j);

                }
            }
        }

        for (Integer key : listaAdj.keySet()) {
            System.out.print("Key : " + key + " -> ");
            for (int i = 0; i < listaAdj.get(key).size(); i++) {
                System.out.print(listaAdj.get(key).get(i) + " ");
            }
            System.out.println("");
        }

    }
}
