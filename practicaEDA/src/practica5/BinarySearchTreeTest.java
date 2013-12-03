package practica5;

import static org.junit.Assert.*;

import org.junit.Test;

import practica2.Position;

public class BinarySearchTreeTest {

	@Test
	public void testFindRange() {
		BinarySearchTree<Integer> tree = new BinarySearchTree<Integer>();
		Position<Integer> root=tree.insert(12);
		Position<Integer> pos1=tree.insert(24);
		Position<Integer> pos2=tree.insert(9);
		Position<Integer> pos3=tree.insert(5);
		Position<Integer> pos4=tree.insert(17);
		boolean eq=false;
		for(Position<Integer> p : tree.findRange(10, 18)){
			if(p.element().equals(root.element()) || p.element().equals(pos4.element()))
				eq=true;
		}
		
		assertEquals(true, eq);
		
	}

	@Test
	public void testAutoComplete() {
		BinarySearchTree<String> tree = new BinarySearchTree<String>();
		String[] v = {"casa","cocina","vecino","mando","estuche","comer",
				"comida","comercio","comprar"};
		for(int i=0;i<9;i++)
			tree.insert(v[i]);
		int i=0;
		String[] w = {"comer","comida","comercio","comprar"};
		for(Position<String> p : tree.autoComplete("com")){
			assertEquals(w[i],p.element());
			i++;
		}
		
		
	}

}
