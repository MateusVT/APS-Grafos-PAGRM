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

    Integer componentesConexas;
    Integer tempo;

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

    public Integer getComponentesConexas() {
        return componentesConexas;
    }

    public void setComponentesConexas(Integer componentesConexas) {
        this.componentesConexas = componentesConexas;
    }

    public void insereVertice(Vertice vertice) {
        List<Aresta> arestas = new ArrayList<>();
        listaAdjacencia.put(vertice, arestas);
    }

    public void removeVertice(Vertice vertice) {
        listaAdjacencia.remove(vertice);
        removeTodasArestas(vertice);
    }

    public void insereArestaND(Vertice verticeOrigem, Aresta aresta) {
        listaAdjacencia.get(verticeOrigem).add(aresta);
        listaAdjacencia.get(aresta.getD()).add(new Aresta(verticeOrigem, aresta.getP()));
    }

    public void removeAresta(Vertice vOrigem, Vertice vDestino) {
        for (Iterator<Aresta> it = listaAdjacencia.get(vOrigem).iterator(); it.hasNext();) {
            Aresta a = it.next();
            if (a.getD().getId().equals(vDestino.getId())) {
                it.remove();
            }
        }
    }

    public void removeArestaND(Vertice vOrigem, Vertice vDestino) {
        removeAresta(vOrigem, vDestino);
        removeAresta(vDestino, vOrigem);
    }

    public void removeTodasArestas(Vertice destino) {
        for (Vertice v : getListaAdjacencia().keySet()) {
            removeAresta(v, destino);
        }
    }

    public Vertice getVerticeGrafo(String nome) {
        Vertice vertice = null;
        for (Vertice v : getListaAdjacencia().keySet()) {
            if (v.getId().equals(nome)) {
                vertice = v;
            }
        }
        return vertice;
    }

    public void printaGrafo(Grafo grafo) {
        for (Vertice v : grafo.getListaAdjacencia().keySet()) {
            System.out.print("vertice : " + v.getId() + "-->");
            for (Aresta ar : grafo.getListaAdjacencia().get(v)) {
                System.out.print(ar.getD().getId() + " ");
            }
            System.out.println();
        }
    }

    public void printaMatriz(Grafo grafo) {
        for (int i = 0; i < grafo.matrizAdjacencia.length; i++) {
            System.out.print(i + " \t");
            for (int j = 0; j < grafo.matrizAdjacencia.length; j++) {
                System.out.print("\t{ " + grafo.matrizAdjacencia[i][j] + " }\t");
            }
            System.out.println();
        }
    }

    public void printaCorGrafo(Grafo grafo) {
        for (Vertice v : grafo.getListaAdjacencia().keySet()) {
            System.out.print("vertice : " + v.getId() + "  ");
            System.out.println("cor: " + v.getCor());
        }
    }

    public boolean existeAdjascencia(Grafo grafo, Vertice origem, Vertice destino) {

        if (grafo.getListaAdjacencia().containsKey(origem)) {
            for (Aresta a : grafo.getListaAdjacencia().get(origem)) {
                if (a.getD() == destino) {
                    return true;
                }
            }
        }
        return false;
    }

}
