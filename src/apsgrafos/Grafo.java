/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apsgrafos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

/**
 *
 * @author leotr
 */
public class Grafo {

    private HashMap<Vertice, List<Aresta>> listaAdjacencia;
    private int[][] matrizAdjacencia;
    private boolean ciclico;
    private Integer isTotalmenteConexo;
    private Integer tempo;

    public Grafo(int vertices) {

        matrizAdjacencia = new int[vertices][vertices];
        for (int i = 0; i < vertices; i++) {
            for (int j = 0; j < vertices; j++) {
                matrizAdjacencia[i][j] = 0;
            }
        }

    }

    public Grafo() {
        this.listaAdjacencia = new LinkedHashMap<>();
    }

    public void printaListaAdj() {
        System.out.println("Lista adjacência : ");
        System.out.println("Vértices Origem --> Vértices alcançáveis");

        for (Vertice vertice : this.getListaAdjacencia().keySet()) {

            System.out.print(vertice.getId() + " --> ");

            for (Aresta aresta : this.getListaAdjacencia().get(vertice)) {
                System.out.print(aresta.getDestino().getId() + " ");
            }

            System.out.println();
        }
        System.out.println();
    }

    public void printaMatrizAdj(Grafo grafo) {

        for (int i = 0; i < grafo.getMatrizAdjacencia().length; i++) {
            System.out.print(i + " \t");

            for (int j = 0; j < grafo.getMatrizAdjacencia().length; j++) {
                System.out.print("\t{ " + grafo.getMatrizAdjacencia()[i][j] + " }\t");
            }

            System.out.println();
        }
        System.out.println();
    }

    public void removeCiclo() {

        BuscaProfundidade busca = new BuscaProfundidade();

        while (isCiclico()) {

            setCiclico(false);

            for (Vertice u : getListaAdjacencia().keySet()) {
                u.setCor("Branco");
                u.setPi(null);
                setTempo(0);
            }

            for (Vertice u : getListaAdjacencia().keySet()) {
                if (u.getCor().equals("Branco")) {
                    busca.BuscaProfundidadeRemoveCiclo(this, u, u.getId());
                }
            }

            List<Aresta> listaArestas = new ArrayList<>();

            for (Vertice u : getListaAdjacencia().keySet()) {
                for (Aresta a : getListaAdjacencia().get(u)) {
                    if (a.isEmCiclo()) {
                        a.setOrigem(u);
                        listaArestas.add(a);
                    }
                }
            }

            if (listaArestas.size() > 0) {
                removeAresta(listaArestas.get(0).getOrigem(), listaArestas.get(0).getDestino());
                removeAresta(listaArestas.get(0).getDestino(), listaArestas.get(0).getOrigem());
            }
        }
    }

    public boolean existeAdjascencia(Vertice origem, Vertice destino) {

        if (this.getListaAdjacencia().containsKey(origem)) {

            for (Aresta aresta : this.getListaAdjacencia().get(origem)) {

                if (aresta.getDestino() == destino) {

                    return true;
                }

            }
        }
        return false;
    }

    public void insereVertice(Vertice vertice) {
        List<Aresta> arestas = new ArrayList<>();
        listaAdjacencia.put(vertice, arestas);
    }

    public void removeVertice(Vertice vertice) {
        listaAdjacencia.remove(vertice);
        removeTodasArestas(vertice);
    }

    public void insereArestaND(Vertice origem, Aresta aresta) {
        listaAdjacencia.get(origem).add(aresta);
        listaAdjacencia.get(aresta.getDestino()).add(new Aresta(origem, aresta.getPeso()));
    }

    public void removeAresta(Vertice origem, Vertice destino) {
        for (Iterator<Aresta> it = listaAdjacencia.get(origem).iterator(); it.hasNext();) {
            Aresta a = it.next();
            if (a.getDestino().getId().equals(destino.getId())) {
                it.remove();
            }
        }
    }

    public void removeArestaND(Vertice origem, Vertice destino) {
        removeAresta(origem, destino);
        removeAresta(destino, origem);
    }

    public void removeTodasArestas(Vertice destino) {
        for (Vertice v : getListaAdjacencia().keySet()) {
            removeAresta(v, destino);
        }
    }

    public Vertice getVerticeGrafo(String id) {

        Vertice vertice = null;

        for (Vertice v : getListaAdjacencia().keySet()) {
            if (v.getId().equals(id)) {
                vertice = v;
            }
        }

        return vertice;
    }

    public HashMap<Vertice, List<Aresta>> getListaAdjacencia() {
        return listaAdjacencia;
    }

    public void setListaAdjacencia(HashMap<Vertice, List<Aresta>> listaAdjacencia) {
        this.listaAdjacencia = listaAdjacencia;
    }

    public Integer getTempo() {
        return tempo;
    }

    public void setTempo(Integer tempo) {
        this.tempo = tempo;
    }

    public boolean isCiclico() {
        return ciclico;
    }

    public void setCiclico(boolean ciclico) {
        this.ciclico = ciclico;
    }

    public int[][] getMatrizAdjacencia() {
        return matrizAdjacencia;
    }

    public void setMatrizAdjacencia(int[][] matrizAdjacencia) {
        this.matrizAdjacencia = matrizAdjacencia;
    }

    public Integer getIsTotalmenteConexo() {
        return isTotalmenteConexo;
    }

    public void setIsTotalmenteConexo(Integer isTotalmenteConexo) {
        this.isTotalmenteConexo = isTotalmenteConexo;
    }
}
