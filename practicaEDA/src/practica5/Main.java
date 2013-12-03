package practica5;

import practica2.Position;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		AVLOrderedDict<Integer,String> dict = new AVLOrderedDict<Integer,String>();
		dict.insert(1, "patata");
		dict.insert(3, "comida");
		dict.insert(8, "cielo");
		System.out.println();
		for(Entry<Integer,String> entry:dict.findRange(0, 5)){
			System.out.print(entry.getKey()+" ");
			System.out.println(entry.getValue());
		}

	}

}
