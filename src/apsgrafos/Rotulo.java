/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apsgrafos;

import java.util.ArrayList;

/**
 *
 * @author leotr
 */
public class Rotulo {

    private Integer id;
    private ArrayList<Aresta> arestas;
    
    Integer qnt;
    Integer numAlcancaveis;

    public Rotulo(Integer nome) {
        this.id = nome;
        this.qnt = 0;
        this.numAlcancaveis = 0;
        this.arestas = new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQnt() {
        return qnt;
    }

    public void setQnt(Integer qnt) {
        this.qnt = qnt;
    }

    public ArrayList<Aresta> getArestas() {
        return arestas;
    }

    public void setArestas(ArrayList<Aresta> arestas) {
        this.arestas = arestas;
    }

    public Integer getNumAlcancaveis() {
        return numAlcancaveis;
    }

    public void setNumAlcancaveis(Integer numAlcancaveis) {
        this.numAlcancaveis = numAlcancaveis;
    }

}
