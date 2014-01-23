package test;

import static org.junit.Assert.*;
import practica2.Position;
import practica5.AVLTree;

import org.junit.Test;

public class AVLTreeTest {

	@Test
	public void testSize() {
		AVLTree <Integer> b = new AVLTree <Integer>();
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
		AVLTree <Integer> b = new AVLTree <Integer>();
		
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
		AVLTree <Integer> b = new AVLTree <Integer>();
		b.insert(5);
		b.insert(3);
		b.insert(1);
		b.insert(7);
		b.insert(6);

		Iterable <Position <Integer>> i = b.positions();
		
		String salida = "";
		for (Position <Integer> e : i) {
			salida += e.element().toString();
		}
		assertEquals(salida, "31657");
		
		b = new AVLTree <Integer>();
		b.insert(4);
		b.insert(5);
		b.insert(7);
		b.insert(2);
		b.insert(1);
		b.insert(3);
		b.insert(6);
		
		i = b.positions();
		salida = "";
		for (Position <Integer> e : i) {
			salida += e.element().toString();
		}
		assertEquals(salida, "4213657");
		
				
	}

	@Test
	public void testRemove() {
		AVLTree <Integer> b = new AVLTree <Integer>();
		b.insert(5);
		b.insert(3);
		Position <Integer> p = b.insert(1);
		b.insert(7);
		b.insert(6);
		
		b.remove(p);
				
		Iterable <Position <Integer>> i = b.positions();
		
		String salida = "";
		for (Position <Integer> e : i) {
			salida += e.element().toString();
		}
		assertEquals(salida, "6357");
		

		b = new AVLTree <Integer>();
		b.insert(44);
		b.insert(17);
		b.insert(62);
		p = b.insert(32);
		b.insert(50);
		b.insert(78);
		b.insert(48);
		b.insert(54);
		b.insert(88);
			
		b.remove(p);
		i = b.positions();
		
		salida = "";
		for (Position <Integer> e : i) {
			salida += e.element().toString();
		}
		assertEquals(salida, "6244175048547888");

		

	}


}
