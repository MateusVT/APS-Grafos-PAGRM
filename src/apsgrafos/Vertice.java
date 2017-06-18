/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apsgrafos;

/**
 *
 * @author leotr
 */
public class Vertice {

    private String id;
    private String cor;
    private Vertice pi;
    private boolean visited;
   
    Double d;
    Integer td;
    Integer tf;

    public Vertice(String nome) {
        this.id = nome;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public Double getD() {
        return d;
    }

    public void setD(Double d) {
        this.d = d;
    }

    public Vertice getPi() {
        return pi;
    }

    public void setPi(Vertice pi) {
        this.pi = pi;
    }

    public Integer getTd() {
        return td;
    }

    public void setTd(Integer td) {
        this.td = td;
    }

    public Integer getTf() {
        return tf;
    }

    public void setTf(Integer tf) {
        this.tf = tf;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

}
