package practica2;

import static org.junit.Assert.*;

import org.junit.Test;

public class LinkedTreeTest {

	@Test
	public void degreeTest(){
		LinkedTree<Integer> tree = new LinkedTree<Integer>();
		Position<Integer> root = tree.addRoot(13);
		Position<Integer> pos1 = tree.add(0, root);
		Position<Integer> pos2 = tree.add(1, root);
		Position<Integer> pos3 = tree.add(3, root);
		Position<Integer> pos11 = tree.add(1234, pos1);
		Position<Integer> pos111 = tree.add(1, pos11);
		Position<Integer> pos112 = tree.add(2, pos11);
		Position<Integer> pos113 = tree.add(3, pos11);
		Position<Integer> pos114 = tree.add(4, pos11);
		assertEquals(4,tree.degree());
	}

}
