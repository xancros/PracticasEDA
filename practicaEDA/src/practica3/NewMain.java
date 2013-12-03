/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package practica3;

import practica2.Position;

/**
 *
 * @author xancros
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
       LinkedBinaryTree<Integer> arbol = new LinkedBinaryTree<Integer>();
       Position<Integer> root=arbol.addRoot(1);
       Position<Integer> pos=arbol.insertLeft(root, 3);
       arbol.insertRight(root, 2);
       Position<Integer> pos2=arbol.insertLeft(pos, 4);
       arbol.insertLeft(pos2, 5);
       arbol.insertRight(pos2, 6);
       LinkedBinaryTree<Integer> arbolEspejado=arbol.mirror();
       arbolEspejado.mostrarArbol(arbolEspejado);
    }
    
}
