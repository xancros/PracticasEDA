/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package practica2;
import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author aitor
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       /*ArrayList<Integer> lista=new ArrayList<Integer>();
       for(int i=1;i<8;i++){
           lista.add(i);
       }
        System.out.println("ANTES");
        for(int i=0;i<lista.size();i++){
           System.out.println(lista.get(i));
       }
       int anterior=lista.get(2);
       int siguiente;
       int elemAniadir=77;
       lista.add(2, elemAniadir);
       /*for(int i=3;i<lista.size();i++){
           siguiente=lista.get(i);
           lista.set(i, anterior);
           anterior=siguiente;
       }
       lista.add(2, elemAniadir);
       lista.add(anterior);
       System.out.println("DESPUES");
       for(int i=0;i<lista.size();i++){
           System.out.println(lista.get(i));
       }
        
        */
        try {
            // TODO code application logic here
            /*LinkedTree<Integer> tree = new LinkedTree<Integer>();
            Position root=tree.addRoot(0);
            Position a1=tree.add(1, root);
            tree.add(2,root);
            Position a11=tree.add(3,a1);
            Position a111=tree.add(27,a11);
            // tree.add(9,a111);
            System.out.println(tree.depth());
            /*
            Iterable<Position<Integer>> i = tree.front();
            ArrayList<Position<Integer>> a=new ArrayList<Position<Integer>>();
            a.addAll((Collection<? extends Position<Integer>>) i);
            Iterator it = i.iterator();
            while(it.hasNext()){
            Position<Integer> pos = ((Position<Integer>)it.next());
            
            System.out.println(pos.element());
            }
            */
           // mostrar();
            System.out.println("\n\n");
            
            parseoWeb a = new parseoWeb("http://en.wikipedia.org/wiki/Tree_(data_structure)");
        } catch (IOException ex) {
            Logger.getLogger(NewMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
   
    public static String quitarHTML(String htmlString){
        String noHTMLString = htmlString.replaceAll("\\<.*?\\>", "");
        return noHTMLString;
    }
    public static void mostrar()throws MalformedURLException, IOException {
        //http://en.wikipedia.org/wiki/Tree_(data_structure)
        //http://www.oracle.com/
        URL oracle = new URL("http://en.wikipedia.org/wiki/Tree_(data_structure)");
        BufferedReader in = new BufferedReader(
        new InputStreamReader(oracle.openStream()));
        String titulo=new String();
        String inputLine;
        String contenido;
        String noespacio;
        int nContenido=0;
        String anterior=new String();
        while ((inputLine = in.readLine()) != null){
            //if(inputLine.contains("<script")==false)
              //  System.out.println(inputLine);
            if(inputLine.contains("<title>")){
                //titulo=(titulo(inputLine));
                System.out.println(quitarHTML(inputLine)+" (Item: "+nContenido+")");
                nContenido++;
            }else{
                //sacar lineas donde contenga <h y ver la relacion de x tabulaciones cuanto mayor sea h
                
                    for(int i=1;i<8;i++){
                        //&&inputLine.contains("<span class")
                    if(inputLine.contains("<h"+i+">")&&inputLine.contains("<span class")){
                        //System.out.println((inputLine));
                        //System.out.println("\n \n \n \n");
                        contenido=quitarHTML(inputLine);
                        switch (i){
                            case 1:{
                                if(! (titulo.contains(contenido)) ){
                                   // System.out.println(inputLine+" (Item: "+nContenido+")");
                                   // nContenido++;
                                }
                            }break;
                            case 2:{
                                //if(anterior.equalsIgnoreCase("h2")){
                                    
                                    System.out.println("\t"+contenido+" (Item: "+nContenido+")");
                                    
                                //}
                                    /*else{
                                    System.out.println(" "+contenido+" (Item: "+nContenido+")");
                                    
                                }*/
                                nContenido++;
                                anterior="h2";
                                
                            }break;
                            case 3:{
                                //if(anterior.equalsIgnoreCase("h2")){
                                    System.out.println("\t\t"+ contenido+" (Item: "+nContenido+")");
                                //}
                                nContenido++;
                            }break;
                            case 4:{
                                System.out.println("\t\t"+contenido+" (Item: "+nContenido+")");
                                nContenido++;
                            }break;
                            case 5:{
                                System.out.println("\t\t\t"+contenido+" (Item: "+nContenido+")");
                                nContenido++;
                            }break;
                            case 6:{
                                System.out.println("\t\t\t\t"+contenido+" (Item: "+nContenido+")");
                                nContenido++;
                            }break;
                            case 7:{
                                System.out.println("\t\t\t\t\t"+contenido+" (Item: "+nContenido+")");
                                nContenido++;
                            }break;    
                                
                        }
                    
            
                    
                    }
                /*for(int i=1;i<8;i++)
                if(inputLine.contains("<h"+i)){
                    System.out.println(quitarHTML(inputLine));
                }*/
                //contenido=(quitarHTML(inputLine));
                /*if(contenido.equalsIgnoreCase("Contents")){
                    System.out.println(contenido);
                    while ((contenido = in.readLine()) != null){
                        noespacio=quitarHTML(contenido);
                        if(!noespacio.equals("")){
                            System.out.println(noespacio);
                        }
                    }
                    
                }
                */
            }
            }
        }
        in.close();
    }

}
