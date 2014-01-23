package practica3;

import practica2.BoundaryViolationException;
import practica2.EmptyTreeException;
import practica2.InvalidPositionException;
import practica2.NonEmptyTreeException;
import practica2.Position;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;



public class ArrayBinaryTree<E> implements BinaryTree<E> {
	protected ArrayList<BTPos<E>> tree; // indexed list of tree positions
        private int pos;
        private int size;
        
    public ArrayBinaryTree (){
        this.tree= new ArrayList<BTPos<E>>();
        pos=0;
        size=0;
    }

    
    @Override
    public Position<E> left(Position<E> v) throws InvalidPositionException, BoundaryViolationException {
        //throw new UnsupportedOperationException("Not supported yet.");
        /*
         * Lo que hacemos es desencapsular el position y comprobar que tenga hijo izquierdo
         * cada BTPOS guarda en que posicion del array esta, así que lo que hacemos es buscar el hijo izquierdo a partir
         * del indice, y una vez lo tenemos sacamos el BTPos del array y lo encapsulamos como position
         */
        
        
        BTPos<E> node=checkPosition(v);
        int nodeleft=node.getLeft();
        if (nodeleft==-1)
            throw new BoundaryViolationException("No left child");
        this.pos=0;
        buscar (nodeleft, 0, tree.size()-1);
        BTPos<E> nodel=tree.get(this.pos);
        Position<E> pNode=nodel;
        return pNode;
        
        
       
    }
    
    
   @Override
    public Position<E> right(Position<E> v) throws InvalidPositionException, BoundaryViolationException {
        //throw new UnsupportedOperationException("Not supported yet.");
        BTPos<E> node=checkPosition(v);
        int noderight=node.getRight(); //indice del hijo derecho del nodo
        if (noderight==-1)
            throw new BoundaryViolationException("No left child");
        this.pos=0;
        buscar (noderight, 0, tree.size()-1); //si es de 6 posiciones, van desde el 0 al 5
        BTPos<E> noder=tree.get(this.pos); 
        Position<E> pNode=noder;
        return pNode;
    }
   
   
   private void buscar (int elem, int ini, int fin){
       
       //esta pensado para buscar la posición en el arraylist del elemento con index elem
       if (ini==fin){
           if (elem == this.tree.get(ini).index())
               this.pos=ini;
       }
       else{
           int medio = (ini+fin)/2;
           if (elem <= this.tree.get(medio).index())
             buscar (elem, ini, medio);
           else
             buscar (elem, medio+1, fin);  
       }
       
           
   }

    @Override
    public boolean hasLeft(Position<E> v) throws InvalidPositionException {
        //throw new UnsupportedOperationException("Not supported yet.");
        BTPos<E> h=checkPosition(v);
        return h.getLeft()!=-1;
        
    }

    @Override
    public boolean hasRight(Position<E> v) throws InvalidPositionException {
        //throw new UnsupportedOperationException("Not supported yet.");
        BTPos<E> h=checkPosition(v);
        return h.getRight()!=-1;
    }

    @Override
    public int size() {
        //throw new UnsupportedOperationException("Not supported yet.");
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        //throw new UnsupportedOperationException("Not supported yet.");
        return ( (this.tree.size()) == 0);
    }
    
    public Position<E> addRoot (E e) throws NonEmptyTreeException{
        if( !isEmpty() )
            throw new NonEmptyTreeException("Tree already has a root");
        BTPos<E> node=createNode(e, 1);
        this.tree.add(node);
        Position<E> h=node;
        this.size++;
        return h;
    }
    
    public Position<E> insertLeft (Position<E> patata, E e) throws InvalidPositionException {
        BTPos<E> node=checkPosition(patata);
        int left=node.getLeft();
        if (left!=-1)
            throw new InvalidPositionException("Node already has a left child");
        Iterator<E> recorrer = iterator();
        boolean esta=false;
        E elemento;
        while( (esta==false) && ( ( recorrer.hasNext()) == true ) ){
            elemento=recorrer.next();
            if (elemento==e)
                throw new InvalidPositionException("Node already has exist"); 
        }
        int indice=(node.index())*2;
        BTPos<E> nodeleft=createNode(e, indice);
        nodeleft.setParent( node.index() );
        node.setLeft(indice);
        this.tree.add(nodeleft);
        Position<E> h=nodeleft;
        this.size++;
        return h;
    }
    
    public Position<E> insertRight (Position<E> patata, E e) throws InvalidPositionException {
        BTPos<E> node=checkPosition(patata);
        int right=node.getRight();
        if (right!=-1)
            throw new InvalidPositionException("Node already has a left child");
        Iterator<E> recorrer = iterator();
        boolean esta=false;
        E elemento;
        while( (esta==false) && ( ( recorrer.hasNext()) == true ) ){
            elemento=recorrer.next();
            if (elemento==e)
                throw new InvalidPositionException("Node already has exist"); 
        }
        int indice=( (node.index())*2 )+1;
        BTPos<E> noderight=createNode(e, indice);
        noderight.setParent( node.index() );
        node.setRight(indice);
        this.tree.add(noderight);
        Position<E> h=noderight;
        this.size++;
        return h;
    }
    
    public void swapElements(Position<E> p1,Position<E> p2)throws InvalidPositionException{
        BTPos<E> nodo1=checkPosition(p1);
        BTPos<E> nodo2=checkPosition(p2);
        E elemento=nodo1.element;
        E elemento2=nodo2.element;
        this.tree.get( posicionArray ( nodo1.index() ) ).setElement(elemento2);
        this.tree.get( posicionArray ( nodo2.index() ) ).setElement(elemento);
    }

    
    private void recolocar (int index){
        int pospadre=posicionArray(index);
        BTPos<E> node = this.tree.get(pospadre);
        if ( node.getLeft()!=-1 ){
            int aux1=index*2;
            int izq=posicionArray(node.getLeft());
            this.tree.get(pospadre).setLeft(aux1);
            this.tree.get(izq).setIndex(aux1);
            BTPos<E> nodeizq = this.tree.get(izq);
            if ( (nodeizq.getLeft()!=-1) || (nodeizq.getRight()!=-1))
                recolocar(aux1);
        }
        if ( node.getRight()!=-1 ){
            int aux2=(index*2)+1;
            int der=posicionArray(node.getRight());
            this.tree.get(pospadre).setRight(aux2);
            this.tree.get(der).setIndex(aux2);
            BTPos<E> nodeizq = this.tree.get(der);
            if ( (nodeizq.getLeft()!=-1) || (nodeizq.getRight()!=-1))
                recolocar(aux2);
        }
    }

   private int posicionArray (int elem){
       boolean encontrado = false;
       int i=0;
       while ( (encontrado == false) && (i<this.size) ){
           if(this.tree.get(i).index()==elem)
               encontrado=true;
           else
           i++;
       }
       return i;
   }
    public void remove (Position<E> e) throws InvalidPositionException {
        BTPos<E> node=checkPosition(e);
        if ( (node.getLeft()!=-1) && (node.getRight()!=-1) ){
            //tiene dos hijos
            System.out.println("No se puede borrar, tiene dos hijos ele elemento seleccionado.");
        }else{
            if ( (node.getLeft()==-1) && (node.getRight()==-1) ){
                //no tiene hijos
                int pos1=posicionArray(node.index());
                this.tree.remove(pos1);
                this.size--;
            }else{
                //tiene un hijo y vemos si izquierdo a derecho
                int pos;
                if(node.getLeft()!=-1){
                    pos=posicionArray(node.getLeft());
                }else{
                    pos=posicionArray(node.getRight()); 
                }
                BTPos<E> nodeaux = tree.get(pos);
                //si nodeaux no tiene hijos, se borra el nodo y se coloca en su sitio nodeaux
                if( (nodeaux.getLeft()==-1) && (nodeaux.getRight()==-1) ){
                    this.tree.remove(node);
                    pos=posicionArray(nodeaux.index());
                    this.tree.get(pos).setIndex(node.index());
                    this.size--;
                }else{
                    //si tiene uno o mÃ¡s hijos el node aux, hay que reordenar tooodo el array, todos los hijos que tenga
                    int indexpadre=node.index();
                    int indexhijo=nodeaux.index();
                    this.tree.remove(posicionArray(indexpadre));
                    this.tree.get(posicionArray(indexhijo)).setIndex(indexpadre);
                    this.size--;
                    //con el nodo borradoo y su hijo ocupando su valor, recolocamos el resto del Ã¡rbol
                    recolocar(indexhijo);
                }
            }
        }
            
    }
    
    @Override
    public Iterator<E> iterator() {
        //throw new UnsupportedOperationException("Not supported yet.");
        Iterable<Position<E>> positions = positions();
	List<E> elements = new ArrayList<E>();
	for (Position<E> pos : positions)
		elements.add(pos.element());
	return elements.iterator(); 
        
    }

    @Override
    public Iterable<Position<E>> positions() {
        //throw new UnsupportedOperationException("Not supported yet.");
        List<Position<E>> positions = new ArrayList<Position<E>>();
        if ( (this.tree.size()) != 0)
            preorderPositions(root(), positions);
        return positions;
    }

    @Override
    public E replace(Position<E> v, E e) throws InvalidPositionException {
        BTPos nodo = this.checkPosition(v);
        E elemento=(E)nodo.element();
        nodo.setElement(e);
        return elemento;
    }

    @Override
    public Position<E> root() throws EmptyTreeException {
        //throw new UnsupportedOperationException("Not supported yet.");
        E root=this.tree.get(0).element;
        if (root==null)
            throw new EmptyTreeException("The tree is empty");
        BTPos<E> z=tree.get(0);
        Position<E> h=z;
        return h;
        
    }

    @Override
    public Position<E> parent(Position<E> v) throws InvalidPositionException, BoundaryViolationException {
        //throw new UnsupportedOperationException("Not supported yet.");
        BTPos<E> node=checkPosition(v);
        if ( node.parent == -1)
            throw new BoundaryViolationException("No parent");
        BTPos<E> parent=tree.get(tree.indexOf(node.parent));
        Position<E> parentp=parent;
        return parentp;
    }

    @Override
    public Iterable<? extends Position<E>> children(Position<E> v) throws InvalidPositionException {
        //throw new UnsupportedOperationException("Not supported yet.");
		List<Position<E>> children = new ArrayList<Position<E>>();
		if (hasLeft(v))
			children.add(left(v));
		if (hasRight(v))
			children.add(right(v));
		return children;
    }
    
    public Position<E> sibling(Position<E> p) throws InvalidPositionException,
			BoundaryViolationException {
        if ( (this.isRoot(p))==true ){
            throw new InvalidPositionException("Is root");
        }
        else {
            BTPos<E> nodeson=checkPosition(p);
            int indiceSon=nodeson.index();
            int indicePater=nodeson.getParent();
            BTPos<E> nodepater = this.tree.get(indicePater-1);
            BTPos<E> brother;
            if ( indiceSon == (nodepater.getLeft()) ){
                brother=this.tree.get( nodepater.getRight() );
            }
            else{
                brother=this.tree.get( nodepater.getLeft() );
            }
            Position<E> h=brother;
            return brother;
        }
    }

    @Override
    public boolean isInternal(Position<E> v) throws InvalidPositionException {
        //throw new UnsupportedOperationException("Not supported yet.");
        BTPos<E> node=checkPosition(v);
        return ( (node.getLeft())!=-1 ) || ( (node.getRight())!=-1 );
    }

    @Override
    public boolean isLeaf(Position<E> v) throws InvalidPositionException {
        //throw new UnsupportedOperationException("Not supported yet.");
        return !isInternal(v);
    }

    @Override
    public boolean isRoot(Position<E> v) throws InvalidPositionException {
        //throw new UnsupportedOperationException("Not supported yet.");
        BTPos<E> node=checkPosition(v);
        return node.parent==-1;
    }
    
    protected BTPos<E> checkPosition (Position<E> p)
        throws InvalidPositionException {
        if ( (p==null) || !(p instanceof BTPos) )
            throw new InvalidPositionException("The position is invalid");
        return (BTPos<E>) p;
    }
    
    protected BTPos<E> createNode (E element, int Index){
        return new BTPos<E>(element, Index);
    }
    
    protected void preorderPositions(Position<E> v, List<Position<E>> pos)
			throws InvalidPositionException {
		pos.add(v);
		if (hasLeft(v))
			preorderPositions(left(v), pos); // recurse on left child
		if (hasRight(v))
			preorderPositions(right(v), pos); // recurse on right child
    }
    
    protected void inorderPositions(Position<E> v, List<Position<E>> pos)
			throws InvalidPositionException {
		if (hasLeft(v))
			inorderPositions(left(v), pos); // recurse on left child
		pos.add(v);
		if (hasRight(v))
			inorderPositions(right(v), pos); // recurse on right child
	}


}
