package test;

import static org.junit.Assert.*;
import practica2.Position;
import practica5.BinarySearchTree;

import org.junit.Test;

public class BinarySearchTreeTest {

	@Test
	public void testSize() {
		BinarySearchTree <Integer> b = new BinarySearchTree <Integer>();
		assertEquals(b.size(), 0);
		b.insert(5);
		assertEquals(b.size(), 1);		
		b.insert(10);
		assertEquals(b.size(), 2);
		
		for (int cont = 0; cont < 25; cont++)
			b.insert(cont);
	
		assertEquals(b.size(), 27);		
	}

	@Test
	public void testFind() {
		BinarySearchTree <Integer> b = new BinarySearchTree <Integer>();
		
		for (int cont = 0; cont < 25; cont+=2)
			b.insert(cont);

		b.insert(17);

		Position <Integer> p = b.find(17);
		assertEquals(p.element().intValue(),17);
		p = b.find(2);
		assertEquals(p.element().intValue(),2);
		p = b.find(13);
		assertEquals(p,null);
	}

	@Test
	public void testInsert() {
		BinarySearchTree <Integer> b = new BinarySearchTree <Integer>();
		b.insert(5);
		b.insert(3);
		b.insert(6);
		b.insert(7);
		b.insert(1);
		b.insert(6);

		Iterable <Position <Integer>> i = b.positions();
		
		String salida = "";
		for (Position <Integer> e : i) {
			salida += e.element().toString();
		}
		assertEquals(salida, "531676");
	}

	@Test
	public void testRemove() {
		BinarySearchTree <Integer> b = new BinarySearchTree <Integer>();
		b.insert(5);
		Position <Integer> p = b.insert(3);
		b.insert(7);
		b.insert(1);
		b.insert(6);
		b.remove(p);
		
		
		Iterable <Position <Integer>> i = b.positions();
		
		String salida = "";
		for (Position <Integer> e : i) {
			salida += e.element().toString();
		}
		assertEquals(salida, "5176");

	}

}
