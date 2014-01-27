/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package practica2;

/**
 *
 * @author Aitor
 */
public class LCRSNode <E> implements Position <E> {
    
    //Atributes
    protected E element;
    protected LCRSNode <E> parent;
    protected LCRSNode <E> leftChild;
    protected LCRSNode <E> rightSibbling;
    
    //Builders
    
    public LCRSNode (E element, LCRSNode<E> parent, LCRSNode<E> leftChild,LCRSNode <E> rightSibbling ){
            this.element=element;
            this.parent=parent;
            this.leftChild=leftChild;
            this.rightSibbling=rightSibbling;
    }
    
    //Methods
    
    //getters
    //return the element of the Tree Node
    public E element() {
        return element;
    }
    //return left child
    public LCRSNode<E> getLeftChild() {
        return leftChild;
    }
    //return parent
    public LCRSNode<E> getParent() {
        return parent;
    }
    //return right sibbling
    public LCRSNode<E> getRightSibbling() {
        return rightSibbling;
    }
    
    //setters
    
    //set a new valor to element
    public void setElement(E element){
        this.element=element;
    }
    //set a new valor to left child
    public void setLeftChild(LCRSNode<E> leftChild) {
        this.leftChild = leftChild;
    }
    //set a new valor to parent
    public void setParent(LCRSNode<E> parent) {
        this.parent = parent;
    }
    //set a new valor to right sibbling
    public void setRightSibbling(LCRSNode<E> rightSibbling) {
        this.rightSibbling = rightSibbling;
    }
    
    
    
}
