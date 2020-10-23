import java.io.*;

public class GLC {
    String generador;
    String[] generados;

    public GLC(String gene){
        this.generador=gene;
    }
    public GLC(String gene,String [] gene2){
        this.generador=gene;
        this.generados=gene2;
    }

    public void añadir(String añadir){
       try{
           String [] tmp=new String[generados.length+1];
           for(int i=0;i<generados.length;i++){
               tmp[i]=generados[i];
           }
           tmp[generados.length]=añadir;
           generados=tmp;
       }catch (NullPointerException e){
           String [] tmp=new String[1];
           tmp[0]=añadir;
           generados=tmp;
       }

    }

    public void imprimir(){
        System.out.print(generador+"->");
        for(int i=0;i<generados.length;i++){
            System.out.print(generados[i]+"|");
        }

    }

    public static void main(String[] args)   {
        GLC prueba=new GLC("S");
        prueba.añadir("a");
        prueba.añadir("b");
        prueba.añadir("SA");
        prueba.imprimir();
    }

}


