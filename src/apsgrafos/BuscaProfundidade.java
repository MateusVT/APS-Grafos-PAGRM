package apsgrafos;

import java.util.Comparator;

/**
 *
 * @author leotr
 */
public class BuscaProfundidade {

    public boolean run(Grafo grafo) {

        grafo.setIsTotalmenteConexo(0);

        for (Vertice u : grafo.getListaAdjacencia().keySet()) {
            u.setCor("Branco");
            u.setPi(null);
            grafo.setTempo(0);
        }
        
        for (Vertice u : grafo.getListaAdjacencia().keySet()) {
            
            if (u.getCor().equals("Branco")) {
                BuscaProfundidade(grafo, u);
                grafo.setIsTotalmenteConexo(grafo.getIsTotalmenteConexo()+1);
            }
            
        }
        
        if (grafo.getIsTotalmenteConexo() == 1) {
            return true;
        }
        
        return false;
    }

    public void BuscaProfundidade(Grafo grafo, Vertice vertice) {
        
        grafo.setTempo(grafo.getTempo()+1);
        vertice.setTempoDescoberta((Integer) grafo.getTempo());
        vertice.setCor("Cinza");

        for (Aresta aresta : grafo.getListaAdjacencia().get(vertice)) {            
            Vertice vert = aresta.getDestino();
            
            if (vert.getCor().equals("Cinza")) {                
                grafo.setCiclico(true);
                
            } else if (vert.getCor().equals("Branco")) {                
                vert.setPi(vertice);
                BuscaProfundidade(grafo, vert);                
            }
            
        }

        vertice.setCor("Preto");
        grafo.setTempo(grafo.getTempo() + 1);
        vertice.setTempoFinal(grafo.getTempo());

    }

    class ComparadorVertices implements Comparator<Vertice> {

        public int compare(Vertice vertice1, Vertice vertice2) {

            if (vertice1.getTempoFinal() > vertice2.getTempoFinal() ) {

                return -1;

            } else if (vertice1.getTempoFinal()  < vertice2.getTempoFinal() ) {

                return +1;

            } else {

                return 0;

            }
        }
    }

}
