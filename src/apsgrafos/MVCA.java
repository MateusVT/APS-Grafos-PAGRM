package apsgrafos;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Torres
 */
public class MVCA {

    private HashMap<Integer, Integer> mapRotulos = new HashMap<>();
    private Grafo grafo = new Grafo();
    private List<Integer> listaRotulos = new ArrayList<>();
    private List<Integer> vertices = new ArrayList<>();
    private List<Integer> verticesConectados = new ArrayList<>();
    private ArrayList<Rotulo> rotulosMinimos = new ArrayList<>();

    public void run(Grafo grafo) {

        BuscaProfundidade busca = new BuscaProfundidade();

        if (!grafoPossuiVerticeInalcancavel(grafo)) {//Se o grafo possuir um vértice inalcançavel é impossível encontrar solução

            ArrayList<Rotulo> rotulos = getRotulos(grafo);//Retorna todos os rótulos existentes no grafo
            Grafo grafoArvore = new Grafo();//Grafo que será "preenchido" pelos rótulos até ser totalmente conexo
            for (int i = 0; i < grafo.getListaAdjacencia().size(); i++) {//Inicializa um grafo sem aretas conectando os vértices

                Vertice vertice = new Vertice(String.valueOf(i));
                vertice.setVisited(false);
                grafoArvore.insereVertice(vertice);

            }

            while (!busca.run(grafoArvore)) {//Enquanto a busca em profundidade não retornar um grafo de componente conexa = 1

                calculaAlcancaveis(rotulos, grafoArvore);//Ordena a lista de rótulos de forma que o rótulo com maior capacidade de conectar vértices fique na posição 0
                Rotulo rotuloAux = rotulos.remove(0);//Recebe o rótulo da vez
                rotulosMinimos.add(rotuloAux);//Armazena o rótulo como parte dos rótulos minimos já encontrados
                preencheGrafoArvore(rotuloAux, grafoArvore);//Preenche o grafo sem arestas com o rótulo escolhido, enquanto o grafo não estiver totalmente conexo

            }

//            grafoArvore.printaListaAdj();//Printa lista adjacência antes de remover os ciclos
//            System.out.println(grafoArvore.isCiclico());
            grafoArvore.removeCiclo();//Implementar esse método e caso verificar se nenhum rótulo move com a remoçao dos ciclos.
//            grafoArvore.printaListaAdj();//Printa a lista depois de remover o ciclo, ou seja a arvore geradora em si.
            atualizaRotulosMinimos(grafoArvore);

            System.out.print("Rótulos geradores minimos: ");

            for (int i = 0; i < rotulosMinimos.size(); i++) {
                System.out.print("[");
                System.out.print(rotulosMinimos.get(i).getId() + "");
                System.out.print("]");
            }

            System.out.println("");
        } else {

            Rotulo rotuloAux = new Rotulo(-10);
            rotulosMinimos.add(rotuloAux);
            System.out.println("O grafo possui vértices inalcaçavéis portanto não é possível encontrar uma solução.");

        }

    }

    public void atualizaRotulosMinimos(Grafo grafoArvore) {

        boolean verificacao = true;
        ArrayList<Rotulo> rotulosRemovidos = new ArrayList<>();

        for (int i = 0; i < rotulosMinimos.size(); i++) {

            for (Vertice vertice : grafoArvore.getListaAdjacencia().keySet()) {

                for (Aresta aresta : grafoArvore.getListaAdjacencia().get(vertice)) {

                    if (aresta.getPeso().equals(rotulosMinimos.get(i).getId())) {
                        verificacao = false;
                    }
                }

            }
            if (verificacao) {

                rotulosRemovidos.add(rotulosMinimos.get(i));

            }
        }

        if (rotulosRemovidos.size() > 0) {
            System.out.println("Removeu algum rótulo!");
        }

        for (int i = 0; i < rotulosRemovidos.size(); i++) {
            rotulosMinimos.remove(rotulosRemovidos.get(i));
        }

    }

    public boolean grafoPossuiVerticeInalcancavel(Grafo grafo) {

        for (Vertice vertice : grafo.getListaAdjacencia().keySet()) {

            if (grafo.getListaAdjacencia().get(vertice).isEmpty()) {
                return true;
            }

        }

        return false;

    }

    public ArrayList<Rotulo> getRotulos(Grafo grafo) {

        ArrayList<Rotulo> rotulos = new ArrayList<>();//Lista de rotulos do grafo

        for (Vertice vertices : grafo.getListaAdjacencia().keySet()) {//Percorre cada vértice(Key) do grafo

            for (Aresta arestas : grafo.getListaAdjacencia().get(vertices)) {//Percore todas arestas de cada vértice

                Rotulo rotulo = new Rotulo(arestas.getPeso());//Cria um rótulo cujo Id é o peso da Aresta
                Aresta aresta = new Aresta(arestas.getDestino(), arestas.getPeso());//Cria uma aresta cujo peso é o id do rótulo definindo origem e destino
                aresta.setOrigem(vertices);
                rotulo.getArestasRepresentantes().add(aresta);//Adiciona todas arestas que representam esse rótulo
                rotulo.setQtdFrequencia(rotulo.getQtdFrequencia() + 1);//Contabiliza a frequência que esse rótulo ocorre

                if (!contains(rotulos, rotulo)) {//Se o rótulo ainda não tiver sido mapeado
                    rotulos.add(rotulo);//Adiciona o rótulo a lista de rótulos
                }

            }

        }

        return rotulos;
    }

    public boolean contains(ArrayList<Rotulo> rotulos, Rotulo rotulo) {

        for (Rotulo r : rotulos) {

            if (r.getId().equals(rotulo.getId())) {

                for (Aresta aresta : rotulo.getArestasRepresentantes()) {

                    if (!contemAresta(r, aresta)) {
                        r.getArestasRepresentantes().add(aresta);
                        r.setQtdFrequencia(rotulo.getQtdFrequencia());
                    }

                }
                return true;
            }
        }

        return false;
    }

    public boolean contemAresta(Rotulo rotulo, Aresta aresta) {

        for (Aresta a : rotulo.getArestasRepresentantes()) {

            if (a.getOrigem().getId().equals(aresta.getOrigem().getId()) && a.getDestino().getId().equals(aresta.getDestino().getId())) {
                return true;
            } else if (a.getDestino().getId().equals(aresta.getOrigem().getId()) && a.getOrigem().getId().equals(aresta.getDestino().getId())) {
                return true;
            }

        }

        return false;
    }

    //Recebe como parametro todos os rótulos do grafo e o grafo com o preenchimento da vez e calcula o próximo rótulo a ser inserido como parte dos rótulos mínimos
    public void calculaAlcancaveis(ArrayList<Rotulo> rotulos, Grafo grafo) {

        for (Rotulo rotulo : rotulos) {//Percorre todos rótulos existentes no grafo original

            rotulo.setQtdVerticesAlcancaveis(0);//Zera o número vértices alcaçaveis

            for (Aresta aresta : rotulo.getArestasRepresentantes()) {//Percorre todas as arestas representadas pelo rótulo, todas as ocorrências do rótulo

                Vertice origem = grafo.getVerticeGrafo(aresta.getOrigem().getId());//Recebe o vértice de origem da aresta sendo iterada
                Vertice destino = grafo.getVerticeGrafo(aresta.getDestino().getId());//Recebe o vértice de destino da aresta sendo iterada

                if (!origem.isVisited()) {//Se o vértice de origem não foi visitado
                    rotulo.setQtdVerticesAlcancaveis(rotulo.getQtdVerticesAlcancaveis() + 1);//Incrimenta a quantidade de vértices alcançaveis pelo rótulo
                }
                if (!destino.isVisited()) {//Se o vértice de destino não foi visitado
                    rotulo.setQtdVerticesAlcancaveis(rotulo.getQtdVerticesAlcancaveis() + 1);//Incrimenta a quantidade de vértices alcançaveis pelo rótulo
                }

            }

        }
        //Ordena a lista de existentes rótulos no grafo de acordo com a quantidade de vértices que esse rótulo pode conectar que já não estejam conectados
        //de modo que na posição rotulos.get(0) fique o rótulo que mais conecta vértices
        rotulos.sort(new comparadorRotulos());
    }

    public void preencheGrafoArvore(Rotulo rotulo, Grafo arvore) {

        for (Aresta aresta : rotulo.getArestasRepresentantes()) {

            Vertice origem = arvore.getVerticeGrafo(aresta.getOrigem().getId());
            origem.setVisited(true);
            Vertice destino = arvore.getVerticeGrafo(aresta.getDestino().getId());
            destino.setVisited(true);

            if (!arvore.existeAdjascencia(origem, destino)) {
                arvore.insereArestaND(origem, new Aresta(destino, aresta.getPeso()));
            }
        }
    }

    public void getRotuloMaisFrequente() {

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

    class comparadorRotulos implements Comparator<Rotulo> {//Estrutura usada para definir como a função .sort() realizará a ordenação de rótulos

        public int compare(Rotulo rotulo1, Rotulo rotulo2) {

            if (rotulo1.getQtdVerticesAlcancaveis() > rotulo2.getQtdVerticesAlcancaveis()) {
                return -1;
            } else if (rotulo1.getQtdVerticesAlcancaveis() < rotulo2.getQtdVerticesAlcancaveis()) {
                return +1;
            } else {
                return 0;
            }
        }
    }

    public ArrayList<Rotulo> getRotulosMinimos() {
        return rotulosMinimos;
    }

    public void setRotulosMinimos(ArrayList<Rotulo> rotulosMinimos) {
        this.rotulosMinimos = rotulosMinimos;
    }
}
