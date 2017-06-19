package apsgrafos;

import java.util.ArrayList;

/**
 *
 * @author leotr
 */
public class Rotulo {

    private Integer id;
    private ArrayList<Aresta> arestasRepresentantes;
    private Integer qtdFrequencia;
    private Integer qtdVerticesAlcancaveis;

    public Rotulo(Integer id) {
        this.id = id;
        this.qtdFrequencia = 0;
        this.qtdVerticesAlcancaveis = 0;
        this.arestasRepresentantes = new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQtdFrequencia() {
        return qtdFrequencia;
    }

    public void setQtdFrequencia(Integer qtdFrequencia) {
        this.qtdFrequencia = qtdFrequencia;
    }

    public ArrayList<Aresta> getArestasRepresentantes() {
        return arestasRepresentantes;
    }

    public void setArestasRepresentantes(ArrayList<Aresta> arestasRepresentantes) {
        this.arestasRepresentantes = arestasRepresentantes;
    }

    public Integer getQtdVerticesAlcancaveis() {
        return qtdVerticesAlcancaveis;
    }

    public void setQtdVerticesAlcancaveis(Integer qtdVerticesAlcancaveis) {
        this.qtdVerticesAlcancaveis = qtdVerticesAlcancaveis;
    }

}
