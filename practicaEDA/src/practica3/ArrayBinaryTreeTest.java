package practica3;

import static org.junit.Assert.assertEquals;


import practica3.ArrayBinaryTree;
import org.junit.Test;
import practica2.Position;


public class ArrayBinaryTreeTest {

	@Test
	public void testSize() {
            ArrayBinaryTree <String> t = new ArrayBinaryTree <String>();
		//ArrayBinaryTree <String> t = new ArrayBinaryTree <String>();
		Position <String> p = t.addRoot("+");
                
		t.insertLeft(p, "2");
               
		Position <String> h = t.insertRight(p, "*");
                
		t.insertLeft(h,"3");
                System.out.println(t.size());
		t.insertRight(h,"5");
                
		assertEquals(t.size(), 5);
	}

	@Test
	public void testIsEmpty() {
		ArrayBinaryTree <String> t = new ArrayBinaryTree <String>();
		assertEquals(t.isEmpty(), true);
	}

	@Test
	public void testHasLeft() {
		ArrayBinaryTree <String> t = new ArrayBinaryTree <String>();
		Position <String> p = t.addRoot("+");
		Position <String> q = t.insertLeft(p, "2");
		assertEquals(t.hasLeft(p), true);
		assertEquals(t.hasLeft(q), false);
	}

	@Test
	public void testPositions() {
		ArrayBinaryTree <String> t = new ArrayBinaryTree <String>();
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
		ArrayBinaryTree <String> t = new ArrayBinaryTree <String>();
		Position <String> p = t.addRoot("+");
		Position <String> q = t.insertLeft(p, "2");
		Position <String> h = t.insertRight(p, "*");
		t.insertLeft(h,"3");
		t.insertRight(h,"5");
		t.remove(q);
		String salida = "";
		for (Position <String> e : t.positions()) {
			salida += e.element();
		}
		assertEquals(salida, "+*35");
		assertEquals(t.size(), 4);
	}

	@Test
	public void testRemoveInternal() {
		ArrayBinaryTree <String> t = new ArrayBinaryTree <String>();
		Position <String> p = t.addRoot("+");
		Position <String> q = t.insertLeft(p, "2");
		Position <String> h = t.insertRight(p, "*");
		Position <String> b = t.insertLeft(h,"-");
		t.insertRight(b,"3");
		t.insertRight(h,"5");
		t.remove(b);
		String salida = "";
		for (Position <String> e : t.positions()) {
			salida += e.element();
		}
		assertEquals(salida, "+2*35");
		assertEquals(t.size(), 5);
	}

	
	@Test
	public void testSwapElements() {
		ArrayBinaryTree <String> t = new ArrayBinaryTree <String>();
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
