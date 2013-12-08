/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author xancros
 * @param <E>
 */
public class parseoWeb<E> {

    private final URL oracle;
    private final BufferedReader in;
    private String titulo;
    private String inputLine;
    private String contenido;
    private Position<E> h1;
    private Position<E> h2;
    private Position<E> h3;
    private Position<E> h4;
    private Position<E> h5;
    private Position<E> h6;
    private Position<E> title;
    private Position<E> h7;
    private Boolean encontrado;
    private int nContenido;
    private int nivelNodal=0;
    private final LinkedTree<E> arbolWeb;
    /**
     * Constructor de la clase que genera el arbol y se inicia el menu
     * @param url
     * @throws MalformedURLException
     * @throws IOException
     */
    public parseoWeb(String url) throws MalformedURLException, IOException {
        oracle = new URL(url);
        h1 = null;
        h2 = null;
        h3 = null;
        h4 = null;
        h5 = null;
        h6 = null;
        h7 = null;
        title = null;
        in = new BufferedReader(
                new InputStreamReader(oracle.openStream()));
        titulo = new String();
        nContenido = 0;
        arbolWeb = new LinkedTree<E>();

        generarArbol();
        menu();
    }
	/**
	*Es un bucle que genera el menu y cotniene las distintas llamadas a los metodos
	*
	*/
    private void menu() {
        int sel = 0;
        Scanner scan = new Scanner(System.in);
        do {
            
            String obj;
            System.out.println("Options\n 1 - Show TOC \n 2 - Move Node \n 3 - Delete Node \n 4 - Add Node \n 5 - Exit");
            System.out.print("Selec an option : ");
            sel = Integer.parseInt(scan.next());
            System.out.println("");
            switch (sel) {
                case 1: {
                    this.mostrarArbol(arbolWeb.root, 0);
                }
                break;
                case 2: {
                    //mover nodo
                    System.out.println("Which node and the associated sub tree do you want to move (provide the Item number)?");
                    obj = ("(Item:" + scan.next() + ")");
                    System.out.println("Which node will be the new parent node (provide the Item number)?");
                    String nextParent = ("(Item:" + scan.next() + ")");
                    System.out.println("Order in parent node");
                    String ord = scan.next();
                    this.mover(obj, nextParent, ord);

                    //fin mover nodo
                }
                break;
                case 3: {
                    //Borrar nodo
                    System.out.println("Node to be deleted (Provide the Item number)");

                    obj = ("(Item:" + scan.next() + ")");
                    this.borrar(obj);

                    //Fin borrar nodo
                }
                break;
                case 4: {
                //Nuevo nodo

                    System.out.println("Write the text of the new node");
                    String name = scan.next();
                    System.out.println("Which node will be the parent of the new node (Provide the Item number)?");
                    obj = ("(Item:" + scan.next() + ")");
                    Position<E> p = buscar(obj);
                    if (p != null) {
                        this.addNode(p, name);
                    } else {
                        System.out.println("No se encuentra");
                    }
                    //FIN NUEVO NODO
                }
                break;
                case 5: {
                    System.out.println("EXIT");
                }
                break;
            }
        } while (sel != 5);
    }
	/**
	* parsing a web reference and creates a new Linked Tree
	* with the web headers as the nodes of the tree
	* 
	* lee desde la pagina web, y en cada caso siendo el titulo quita el codigo html y se queda solo con el texto
	* y añade el titulo a la raiz
	* 
	* en las distintas cabeceras (h1..h7) se van añadiendo y se actualiza los atributos de las cabeceras,
	* para ir asociando cada cabecera debajo de cada punto.
	*/
	private void generarArbol() throws IOException {

        while ((inputLine = in.readLine()) != null) {
            //Obtiene el titulo de la pagina web
			if (inputLine.contains("<title>")) {
                titulo = (quitarHTML(inputLine) + " (Item:" + nContenido + ")");
                title = arbolWeb.addRoot((E) titulo);
                nContenido++;
            } else {
				//busca las cabeceras de la pagina web y las incluye en el arbol
                for (int i = 1; i < 8; i++) {
                    if (inputLine.contains("<h" + i + ">") && inputLine.contains("<span class")) {
                        contenido = quitarHTML(inputLine);
                        switch (i) {
                            case 1: {
                                if (!(titulo.contains(contenido))) {
                                    contenido = (inputLine + " (Item:" + nContenido + ")");
                                    h1 = arbolWeb.add((E) contenido, title);
                                    nContenido++;
                                }
                            }
                            break;
                            case 2: {
                                contenido = (contenido + " (Item:" + nContenido + ")");
                                if (h1 != null) {
                                    h2 = arbolWeb.add((E) contenido, h1);
                                } else {
                                    h2 = arbolWeb.add((E) contenido, title);
                                }
                                nContenido++;
                            }
                            break;
                            case 3: {
                                contenido = (contenido + " (Item:" + nContenido + ")");
                                if (h2 != null) {
                                    h3 = arbolWeb.add((E) contenido, h2);
                                } else if (h1 != null) {
                                    h3 = arbolWeb.add((E) contenido, h1);
                                } else {
                                    h3 = arbolWeb.add((E) contenido, title);
                                }

                                nContenido++;
                            }
                            break;
                            case 4: {

                                contenido = (contenido + " (Item:" + nContenido + ")");
                                if (h3 != null) {
                                    h4 = arbolWeb.add((E) contenido, h3);
                                } else if (h2 != null) {
                                    h4 = arbolWeb.add((E) contenido, h2);
                                } else if (h1 != null) {
                                    h4 = arbolWeb.add((E) contenido, h1);
                                } else {
                                    h4 = arbolWeb.add((E) contenido, title);
                                }

                            }
                            break;
                            case 5: {

                                contenido = (contenido + " (Item:" + nContenido + ")");
                                if (h4 != null) {
                                    h5 = arbolWeb.add((E) contenido, h4);
                                } else if (h3 != null) {
                                    h5 = arbolWeb.add((E) contenido, h3);
                                } else if (h2 != null) {
                                    h5 = arbolWeb.add((E) contenido, h2);
                                } else if (h1 != null) {
                                    h5 = arbolWeb.add((E) contenido, h1);
                                } else {
                                    h5 = arbolWeb.add((E) contenido, title);
                                }

                                nContenido++;
                            }
                            break;
                            case 6: {

                                contenido = (contenido + " (Item:" + nContenido + ")");
                                if (h5 != null) {
                                    h6 = arbolWeb.add((E) contenido, h5);
                                } else if (h4 != null) {
                                    h6 = arbolWeb.add((E) contenido, h4);
                                } else if (h3 != null) {
                                    h6 = arbolWeb.add((E) contenido, h3);
                                } else if (h2 != null) {
                                    h6 = arbolWeb.add((E) contenido, h2);
                                } else if (h1 != null) {
                                    h6 = arbolWeb.add((E) contenido, h1);
                                } else {
                                    h6 = arbolWeb.add((E) contenido, title);
                                }

                                nContenido++;
                            }
                            break;
                            case 7: {

                                contenido = (contenido + " (Item:" + nContenido + ")");
                                if (h6 != null) {
                                    h7 = arbolWeb.add((E) contenido, h6);
                                } else if (h5 != null) {
                                    h7 = arbolWeb.add((E) contenido, h5);
                                } else if (h4 != null) {
                                    h7 = arbolWeb.add((E) contenido, h4);
                                } else if (h3 != null) {
                                    h7 = arbolWeb.add((E) contenido, h3);
                                } else if (h2 != null) {
                                    h7 = arbolWeb.add((E) contenido, h2);
                                } else if (h1 != null) {
                                    h7 = arbolWeb.add((E) contenido, h1);
                                } else {
                                    h7 = arbolWeb.add((E) contenido, title);
                                }
                                nContenido++;
                            }
                            break;

                        }
                    }

                }
            }
        }
        in.close();

    }
	/**
	* Complementary method that convert html headers to a simple text header without <hx>
	*/
    private String quitarHTML(String htmlString) {
        String noHTMLString = htmlString.replaceAll("\\<.*?\\>", "");
        return noHTMLString;
    }
    /**
     * Metodo privado que permite buscar el texto del numero del objeto en el arbol
     * @param h
     * @return
     */
    private Position<E> buscar(String h) {
        encontrado = false;
        Iterator<Position<E>> it = arbolWeb.positions().iterator();
        String cont;
        Position<E> pos = null;
        while (it.hasNext() && !encontrado) {
            pos = it.next();
            cont = (String) (arbolWeb.checkPosition(pos).element());
            if (cont.contains(h)) {
                encontrado = true;
            }
        }
        return pos;

    }
    /**
     * Muestra el arbol en modo texto
     * @param node
     * @param nivel
     */
    private void mostrarArbol(TreeNode<E> node,int nivel) {
        
        for(int i=0;i<nivel; i++){
            System.out.print("\t");
        }
        System.out.println(node.element());

        Iterable<TreeNode<E>> hijos = node.getChildren();
        for (TreeNode<E> n : hijos) {

            mostrarArbol(n,nivel+1);
        }
    }
    /**
     * metodo interno que se llama desde el menu para añadir un nuevo nodo al arbol
     * @param p
     * @param name
     */
    private void addNode(Position<E> p,String name){
        TreeNode<E> node = arbolWeb.checkPosition(p);
        recorrer(1,arbolWeb.root,node);
        Scanner s = new Scanner(System.in);
        String cont;
        System.out.println("Order in parent Node");
        String ord=s.next();
        int orden = Integer.parseInt(ord);
        cont = (name + " (Item:" + nContenido + ")");
        this.addNodePos(p, orden, cont);
        nContenido++;
            
        
        System.out.println("The Node has been created");
    }
    /**
     * Metodo auxiliar para buscar elementos en el arbol
     * @param nivel
     * @param node
     * @param bus
     */
    private void recorrer(int nivel,TreeNode<E> node,TreeNode<E> bus){
                
                Iterable<TreeNode<E>> nodes=node.getChildren();
                //System.out.println(nivel);
                for(TreeNode<E> n : nodes){
                    
                    if(n.element()==bus.element()){
                       
                        nivelNodal=nivel;
                    }else{
                        recorrer(nivel+1,n,bus);
                    }
                }
                
                
	}
    /**
     * metodo auxiliar para añadir nuevos nodos en el arbol
     * @param parent
     * @param pos
     * @param con
     */
    private void addNodePos(Position<E> parent,int pos,String con){
        boolean empty=arbolWeb.checkPosition(parent).getChildren().isEmpty();
        if(arbolWeb.checkPosition(parent).getChildren().isEmpty()){
            arbolWeb.add((E) con, parent);
        }else{
            TreeNode<E> node = arbolWeb.checkPosition(parent);
            TreeNode<E> newNode = arbolWeb.createNode((E)con, node, new LinkedList<TreeNode<E>>());
            node.getChildren().add(pos, newNode);
            int size=arbolWeb.size();
            size++;
            arbolWeb.size=size;
        }
    }
    /**
     * Metodo que sirve para borrar un nodo dado segun el texto que se da desde la cabecera primer lo busca
     * despues si lo ha encontrado lo borra.
     * @param item
     */
    private void borrar(String item){
        Position<E> p = buscar(item);
            if(p!=null){
                arbolWeb.removeNode(p);
                System.out.println("Node has been removed");
            }else{
                System.out.println("No se encuentra");
            }
    }
    /**
     * Busca primero el nodo, y cuando lo encuentra se obtiene su padre, se cambian los punteros y se establece el nodo
     * con el orden dado en el sitio correcto.
     * @param item
     * @param nextParent
     * @param orden
     */
    private void mover(String item,String nextParent,String orden){
        Position<E> nodeMove = buscar(item);
            if(nodeMove!=null){
                boolean found=false;
                TreeNode<E> nodeM=arbolWeb.checkPosition(nodeMove);
                List<TreeNode<E>> l = nodeM.getParent().getChildren();
                for(int i=0;i<l.size() && !found;i++){
                    if(l.get(i).element().equals(nodeM.element())){
                        found=true;
                        l.remove(i);
                    }
                }
                int ord=Integer.parseInt(orden);
                Position<E> newParent = buscar(nextParent);
            if(newParent!=null){
                TreeNode<E> nParent=arbolWeb.checkPosition(newParent);
                List<TreeNode<E>> lista = nParent.getChildren();
                if(lista.isEmpty()){
                    lista.add(nodeM);
                }else{
                    lista.add(ord, nodeM);
                }
                System.out.println("Node has been moved");
            }else{
                System.out.println("No se encuentra el nuevo padre");
            }
            
            }else{
                System.out.println("No se encuentra el nodo a mover");
            }
    }
}
