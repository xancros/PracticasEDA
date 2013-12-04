package practica5;

import practica2.Position;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*AVLOrderedDict<Integer,String> dict = new AVLOrderedDict<Integer,String>();
		dict.insert(1, "patata");
		dict.insert(3, "comida");
		dict.insert(8, "cielo");
		System.out.println();
		for(Entry<Integer,String> entry:dict.findRange(0, 5)){
			System.out.print(entry.getKey()+" ");
			System.out.println(entry.getValue());
		}
		*/
		
		BinarySearchTree<Integer> bst = new BinarySearchTree<Integer>();
		AVLTree<Integer> avltree = new AVLTree<Integer>();
		RBTree<Integer> rbtree = new RBTree<Integer>();
		
	}
	public static void consultaOrdenada(BinarySearchTree<Integer> bst,AVLTree<Integer>avltree,RBTree<Integer>rbtree){
		System.out.println("CONSULTA EN BINARY SEARCH TREE");
		for(int i=1;i<=1000;i++){
			bst.find(i);
			System.out.println("consulta "+ i+ " en "+ +System.currentTimeMillis());
			
		}
		System.out.println("CONSULTA EN AVL TREE");	
		for(int i=1;i<=10000;i++){
			avltree.find(i);
			System.out.println("consulta "+ i+ " en "+ +System.currentTimeMillis());
		}
		System.out.println("CONSULTA EN RED BLACK TREE");
		for(int i=1;i<=100000;i++){
			rbtree.find(i);
			System.out.println("consulta "+ i+ " en "+ +System.currentTimeMillis());
		}
		
	}
	public static void consultaAleatoria(BinarySearchTree<Integer> bst,AVLTree<Integer>avltree,RBTree<Integer>rbtree){
		System.out.println("CONSULTA EN BINARY SEARCH TREE");
		for(int i=1;i<=1000;i++){
			bst.find((int)(Math.random()*1000)+1);
			System.out.println("consulta "+ i+ " en "+ +System.currentTimeMillis());
			
		}
		System.out.println("CONSULTA EN AVL TREE");	
		for(int i=1;i<=10000;i++){
			avltree.find((int)(Math.random()*1000)+1);
			System.out.println("consulta "+ i+ " en "+ +System.currentTimeMillis());
		}
		System.out.println("CONSULTA EN RED BLACK TREE");
		for(int i=1;i<=100000;i++){
			rbtree.find((int)(Math.random()*1000)+1);
			System.out.println("consulta "+ i+ " en "+ +System.currentTimeMillis());
		}
		
	}
	public static void insercionAleatoria(BinarySearchTree<Integer> bst,AVLTree<Integer>avltree,RBTree<Integer>rbtree){
		System.out.println("INSERCION EN BINARY SEARCH TREE");
		
		for(int i=1;i<=1000;i++){
			bst.insert((int)(Math.random()*1000)+1);
			System.out.println("insercion "+ i+ " en "+ +System.currentTimeMillis());
			
		}
		System.out.println("INSERCIONES EN AVL TREE");	
		for(int i=1;i<=10000;i++){
			avltree.insert((int)(Math.random()*10000)+1);
			System.out.println("insercion "+ i+ " en "+ +System.currentTimeMillis());
		}
		System.out.println("INSERCIONES EN RED BLACK TREE");
		for(int i=1;i<=100000;i++){
			rbtree.insert((int)(Math.random()*100000)+1);
			System.out.println("insercion "+ i+ " en "+ +System.currentTimeMillis());
		}
	}
	public static void insercionOrdenada(BinarySearchTree<Integer> bst,AVLTree<Integer>avltree,RBTree<Integer>rbtree){
		System.out.println("INSERCIONES EN BINARY SEARCH TREE");
		for(int i=1;i<=1000;i++){
			bst.insert(i);
			System.out.println("insercion "+ i+ " en "+ +System.currentTimeMillis());
			
		}
		System.out.println("INSERCIONES EN AVL TREE");	
		for(int i=1;i<=10000;i++){
			avltree.insert(i);
			System.out.println("insercion "+ i+ " en "+ +System.currentTimeMillis());
		}
		System.out.println("INSERCIONES EN RED BLACK TREE");
		for(int i=1;i<=100000;i++){
			rbtree.insert(i);
			System.out.println("insercion "+ i+ " en "+ +System.currentTimeMillis());
		}
	}

}
