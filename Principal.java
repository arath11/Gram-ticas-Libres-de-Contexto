import java.io.*;

public class Principal {



    String [] generadores;
    String [] terminales;

    GLC[] glc;

    GLC[] N1;

    GLC[] N2;

    public void leerArchivo()   {
        File archivo= new File("src/GLC.txt");

        try{
            BufferedReader br= new BufferedReader(new FileReader(archivo));
            int contador=0;
            int cantidad=-1;
            String st;
            boolean checar =true;
            while ((st=br.readLine()) !=null) {
                if(contador==0){
                    generadores=st.split(",");
                }
                else if(contador==1){
                    String []tmp = st.split(",");

                    for(int i=0;i<generadores.length;i++){
                        for(int j=0;j<tmp.length;j++){
                            if(generadores[i]==tmp[j]){
                                checar=false;
                            }
                        }
                    }
                    if(checar==true){
                        terminales=st.split(",");
                    }

                }
                if(contador%2==0 && contador>1) {
                    //añadir el generador
                    cantidad++;
                    if(cantidad==0){//generar el glc
                        glc=new GLC[1];
                        glc[cantidad]=new GLC(st);
                    }else{
                        GLC[] tmp=new GLC[glc.length+1];
                        //   System.out.println(tmp.length);
                        for(int i=0;i<glc.length;i++){
                            tmp[i]=glc[i];
                        }

                        tmp[glc.length]=new GLC(st);
                        glc=tmp;
                    }

                    //System.out.print(st+"->");

                }else if (contador%2==1 && contador>1){
                    //System.out.println(st);
                    //añadir lo que produce
                    String []tmp=st.split(",");
                    //System.out.println(tmp.length);
                    for(int i=0;i<tmp.length;i++){
                        try{
                            //System.out.println(cantidad);
                            glc[cantidad].añadir(tmp[i]);
                        }catch (Exception e){

                        }
                    }
                }
                contador++;
            }
        }catch (IOException e ){
            System.out.println("Porfa ingrese un archivo");
        }
    }


    public void checarN1(){
        for(int i =0;i<glc.length;i++){
            boolean a=true;
            for(int j=0;j<glc[i].generados.length && a;j++){
                for(int k=0;k<terminales.length;k++){
                    //if(glc[i].generados[j].equals(terminales[k])) {
                    if(glc[i].generados[j].length()==1){
                        if(glc[i].generados[j].equals(terminales[k]) ) {
                            //   System.out.println(glc[i].generados[j] + "----" + terminales[k]);
                            a=false;
                            añadorA1(glc[i]);
                        }
                    }else{
                        if(glc[i].generados[j].contains(terminales[k]) ) {
                            String [] ca= glc[i].generados[j].split("");
                            boolean entara=true;
                            for(int z=0;z< ca.length;z++){
                                for(int x=0;x<generadores.length;x++){
                                    if(ca[z].equals(generadores[x])){
                                       entara=false;
                                    }
                                }
                                //System.out.println(ca[z]+"..........."+entara);
                            }
                            if(entara){
                                añadorA1(glc[i]);
                            }
                            a=false;
                        }


                    }

                }
            }
        }
        System.out.println("N1 con puros terminales");
        System.out.println("----------");
        for(int i=0;i< N1.length;i++){
            N1[i].imprimir();
        }
        System.out.println();
        System.out.println("----------");

        //ahora meter a N1 los que dependen de otros
        System.out.println("Pruebaa----");
        for(int a=0;a< glc.length;a++){
            for(int i=0;i<glc.length;i++){
                for(int j=0;j< glc[i].generados.length;j++){
                    //if(glc[i].generados[j].contains());
                        for(int b = 0;b< N1.length;b++){
                            if(glc[i].generados[j].contains(N1[b].generador)){
//                                 System.out.println(glc[i].generador);
                                 if(estaEnN1(glc[i])){
//                                     System.out.println("Metera");
                                     insertarAN1(glc[i]);
                                 }
                            }
                    }
                }
            }
        }
        for(int i=0;i< N1.length;i++){

        }


        System.out.println("N1 con generadores");
        System.out.println("----------");
        for(int i=0;i< N1.length;i++){
            N1[i].imprimir();
        }
        System.out.println();
        System.out.println("----------");


    }

    public void insertarAN1(GLC insertar){
        GLC[] tmp = new GLC[N1.length+1];
        for(int i=0;i< N1.length;i++){
            tmp[i]=N1[i];
        }
        tmp[N1.length]=insertar;
        N1=tmp;
    }

    public boolean estaEnN1(GLC entrada){
        boolean regreso = false;
        for(int i =0;i< N1.length;i++){
            if(N1[i].generador.equals(entrada.generador)){
                regreso=false;
                return false;
            }
        }
        regreso = true;
        return regreso;
    }


    private void añadorA1(GLC añadir){
        try{//ya instanciado
            GLC [] tmp=new GLC[N1.length+1];
            for (int i=0;i< N1.length;i++){
                tmp[i]=N1[i];
            }
            tmp[N1.length]=añadir;
            N1=tmp;

        }catch (NullPointerException e){
            N1=new GLC[1];
            N1[0]=añadir;
        }
    }


    private void imprimir(){
        System.out.print("Generadores: ");
        for(int i=0;i<generadores.length;i++){
            System.out.print(generadores[i]+",");
        }
        System.out.println();
        System.out.print("terminales:");
        for(int i=0;i<terminales.length;i++){
            System.out.print(terminales[i]+",");
        }
        System.out.println();
        //imprimir el glc
        for(int i=0;i<glc.length;i++){
            glc[i].imprimir();
            System.out.println();
        }
    }

    public void checarNodosN1(){
        //encontrar S
        N2=new GLC[1];
        N2[0]=N1[posicion("S")];
        //encontra Los demas
        parteRecursiva(posicion("S"));
    }

    public void parteRecursiva(int posicion){
        for(int i=0;i<N1[posicion].generados.length;i++) {
            System.out.println(N1[posicion].generados[i].length());
            if(N1[posicion].generados[i].length()==1){
                for(int q=0;q< generadores.length;q++){
                    if(N1[posicion].generados[i].equals(generadores[q])){
                        System.out.println(N1[posicion].generados[i]);
                        //todo: poner que llame con la otra y que lo meta si no esta
                    }
                }
            }else {
                String []s1=N1[posicion].generados[i].split("");
                for(int z=0;z< s1.length;z++){
                    for(int q=0;q< generadores.length;q++){
                        if(s1[z].equals(generadores[q])){
                            System.out.println(s1[z]);
                            //todo: poner que llame con la otra y que lo meta si no esta
                        }
                    }

                }

                }
            }

        }


    public int posicion(String dato){
        int S=0;
        for(int i=0;i< N1.length;i++){
            if(N1[i].generador.equals(dato)){
                //System.out.println("----"+i+"");
                S=i;
            }
        }
        return S;
    }

    public static void main(String[] args) {
        Principal prueba= new Principal();
        prueba.leerArchivo();
        prueba.imprimir();
        prueba.checarN1();
        prueba.checarNodosN1();
    }


}
