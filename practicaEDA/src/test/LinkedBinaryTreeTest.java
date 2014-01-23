package test;

import static org.junit.Assert.assertEquals;
import practica2.Position;
import practica3.LinkedBinaryTree;

import org.junit.Test;

public class LinkedBinaryTreeTest {

	@Test
	public void testSize() {
		LinkedBinaryTree <String> t = new LinkedBinaryTree <String>();
		Position <String> p = t.addRoot("+");
		t.insertLeft(p, "2");
		Position <String> h = t.insertRight(p, "*");
		t.insertLeft(h,"3");
		t.insertRight(h,"5");
		assertEquals(t.size(), 5);
	}

	@Test
	public void testIsEmpty() {
		LinkedBinaryTree <String> t = new LinkedBinaryTree <String>();
		assertEquals(t.isEmpty(), true);
	}

	@Test
	public void testPositions() {
		LinkedBinaryTree <String> t = new LinkedBinaryTree <String>();
		Position <String> p = t.addRoot("+");
		t.insertLeft(p, "2");
		t.insertRight(p, "3");
		String salida = "";
		for (Position <String> e : t.positions()) {
			salida += e.element();
		}
		assertEquals(salida, "+23");
	}

	@Test
	public void testRemove() {
		LinkedBinaryTree <String> t = new LinkedBinaryTree <String>();
		Position <String> p = t.addRoot("+");
		Position <String> q = t.insertLeft(p, "2");
		Position <String> h = t.insertRight(p, "*");
		t.insertLeft(h,"3");
		t.insertRight(h,"5");
		t.remove(q);
		assertEquals(t.size(), 4);
		
	}

	@Test
	public void testSwapElements() {
		LinkedBinaryTree <String> t = new LinkedBinaryTree <String>();
		Position <String> p = t.addRoot("+");
		Position <String> p1 = t.insertLeft(p, "2");
		Position <String> p2 = t.insertRight(p, "3");
		t.swapElements(p1, p2);
		String salida = "";
		for (Position <String> e : t.positions()) {
			salida += e.element();
		}
		assertEquals(salida, "+32");
	}

}
