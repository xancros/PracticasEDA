package practica3;

import static org.junit.Assert.*;

import org.junit.Test;

import practica2.Position;

public class LinkedBinaryTreeTest {

	@Test
	public void test() {
		fail("Not yet implemented");
	}
	@Test
	public void levelTest (){
		LinkedBinaryTree<Integer> tree = new LinkedBinaryTree<Integer>();
		Position<Integer> root=tree.addRoot(12);
		Position<Integer> pos1=tree.insertLeft(root, 1);
		Position<Integer> pos2=tree.insertRight(root, 2);
		Position<Integer> pos11 = tree.insertLeft(pos1, 22);
		assertEquals(2,tree.level(pos11));
	}
	@Test
	public void lvlNodeTest(){
		LinkedBinaryTree<Integer> tree = new LinkedBinaryTree<Integer>();
		Position<Integer> root=tree.addRoot(12);
		Position<Integer> pos1=tree.insertLeft(root, 1);
		Position<Integer> pos2=tree.insertRight(root, 2);
		Position<Integer> pos11 = tree.insertLeft(pos1, 22);
		assertEquals(2,tree.lvlAux(pos11));
	}

}
