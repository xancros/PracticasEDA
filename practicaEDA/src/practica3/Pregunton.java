package practica3;

import java.util.Scanner;

import practica2.Position;

public class Pregunton {

	/**
	 * @param args
	 */
	private Position<String> pos;
	
	/**
	 * constructor, crea un arbol binario vacio y empieza el juego
	 * 
	 */
	public Pregunton(){
		LinkedBinaryTree<String> mente = new LinkedBinaryTree<String>();
		Scanner scan = new Scanner(System.in);
		System.out.println("¿Con qué tipo de objeto vamos a jugar?");
		String objeto=scan.nextLine();
		String decision=new String();
		String solucion=new String();
		
		do{
			System.out.println("Piensa en un " + objeto
					+ " y luego pulsa una tecla o escribe salir");
			decision = scan.nextLine();
			if (!decision.equalsIgnoreCase("salir")) {
				if (mente.isEmpty()) {
					System.out.println("En que " + objeto + " estabas pensando");
					solucion = scan.nextLine();
					pos=mente.addRoot(solucion);
				} else {
					
					mente=preguntarRecorriendoArbol(mente,objeto,scan,solucion);
					
				}
			}
			
		}while(!decision.equalsIgnoreCase("salir"));
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Pregunton();
	}
	/**
	 * Recorre el arbol actualizando y haciendo las llamadas oportunas en cada caso, después de hacer esto
	 * devuelve un puntero al arbol actualizado despues de hacer los cambios.
	 * 
	 * @param mente
	 * @param objeto
	 * @param scan
	 * @param solucion
	 * @return
	 */
	private LinkedBinaryTree<String> preguntarRecorriendoArbol(LinkedBinaryTree<String> mente,String objeto,Scanner scan,String solucion){
		
		if (mente.size() - 1 == 0) {
			System.out.println("Es un " + mente.root.element() + " ?");
			solucion = scan.nextLine();
			if(solucion.equalsIgnoreCase("si")){
				
				
			}else{
				return aniadirSolucion(mente,objeto,scan);
			}
		}else{
			//Recorremos el arbol segun se pregunta
			System.out.println(mente.root.element());
			
			if(scan.nextLine().equalsIgnoreCase("no")){//izquierda
				if(preguntaRecursivo(mente.checkPosition(mente.left(mente.root())), scan)){
					System.out.println("He acertado");
					
				}else{
					LinkedBinaryTree<String> subtree=aniadirSolucion(mente,objeto,scan);
					BTNode<String> posParent = mente.checkPosition(mente.parent(pos));
					BTNode<String> node=mente.checkPosition(pos);
					if(posParent.getRight().element().equals(node.element())){//node es hijo derecho
						posParent.setRight(subtree.root);
					}else{//soy el hijo izquierdo
						posParent.setLeft(subtree.root);
					}
					//mente.remove(pos);
					
					subtree.root=mente.root;
					mente.size+=subtree.size;
				}
			}else{//derecha
				if(preguntaRecursivo(mente.checkPosition(mente.right(mente.root())), scan)){
					System.out.println("He acertado");
					
				}else{
					LinkedBinaryTree<String> subtree=aniadirSolucion(mente,objeto,scan);
					BTNode<String> posParent = mente.checkPosition(mente.parent(pos));
					BTNode<String> node=mente.checkPosition(pos);
					if(posParent.getRight().element().equals(node.element())){//node es hijo derecho
						posParent.setRight(subtree.root);
					}else{//soy el hijo izquierdo
						posParent.setLeft(subtree.root);
					}
					subtree.root=mente.root;
					mente.size+=subtree.size;
				}
			}
			
		}
		return mente;
	}
	/**
	 * Devuelve un arbol binario de Strings con 3 elementos, raiz izquierda derecha
	 * @param mente
	 * @param scan
	 * @param objeto
	 * @return
	 */
	private LinkedBinaryTree<String> aniadirSolucion(LinkedBinaryTree<String> mente,String objeto,Scanner scan){
			System.out.println("En que " + objeto+ " estabas pensando");
			String solucion = scan.nextLine();
			LinkedBinaryTree<String> tree = new LinkedBinaryTree<String>();
			BTNode<String> node = mente.checkPosition(pos);
			System.out.println("Escribe una pregunta con respuesta afirmativa para "+solucion+" y negativa para "+node.element());
			tree.addRoot(scan.nextLine());
			tree.insertLeft(tree.root(), node.element());
			tree.insertRight(tree.root(), solucion);
			return tree;
	}
	/**
	 * Devuelve si hay exito en las preguntas internas
	 * @param node
	 * @param scan
	 * @return
	 */
	private boolean preguntaRecursivo(BTNode<String> node,Scanner scan){
		BTNode<String> left=node.getLeft();
		BTNode<String> right=node.getRight();
		if(left==null && right==null){//nodo hoja
			System.out.println("Es un "+node.element()+"?");
			pos=node;
			if(scan.nextLine().equalsIgnoreCase("no")){
				
				return false;
			}else{
				
				return true;
			}
		}else{
			System.out.println(node.element());
			if(scan.nextLine().equalsIgnoreCase("no")){
				return preguntaRecursivo(node.getLeft(),scan);
			}else{
				
				return preguntaRecursivo(node.getRight(),scan);
			}
		}
		
	}
}
