import jdk.nashorn.internal.objects.Global;

import java.io.*;
import java.sql.SQLOutput;

public class Principal {


    private String[] generadores;
    private String[] terminales;

    private GLC[] glc;

    private GLC[] N1;

    private GLC[] N2;

    private String[] posibles = {"â", "ä","å","æ","ç","é","ë","Ħ","Ţ","Ŧ","Ơ","Ƥ","Ƨ","Ʃ","Ʈ","Ʒ","ƽ","ƽ","Ǯ","Ǵ","Ǹ","À","È","Ë","Ö","Ô","Ð","Ý","Ď","Ħ","Ĥ","Ğ","Ł","Ň","Ŕ","Ő","Ř","Ŕ","Ş","Š"};
    private int posiblesPos = 0;
    private int cantidadAnterior = 0;
    private boolean yaTieneCantidadAnterior = false;


    public void leerArchivo() {
        File archivo = new File("src/GLC.txt");

        try {
            BufferedReader br = new BufferedReader(new FileReader(archivo));
            int contador = 0;
            int cantidad = -1;
            String st;
            boolean checar = true;
            while ((st = br.readLine()) != null) {
                if (contador == 0) {
                    generadores = st.split(",");
                } else if (contador == 1) {
                    String[] tmp = st.split(",");

                    for (int i = 0; i < generadores.length; i++) {
                        for (int j = 0; j < tmp.length; j++) {
                            if (generadores[i] == tmp[j]) {
                                checar = false;
                            }
                        }
                    }
                    if (checar == true) {
                        terminales = st.split(",");
                    }

                }
                if (contador % 2 == 0 && contador > 1) {
                    //añadir el generador
                    cantidad++;
                    if (cantidad == 0) {//generar el glc
                        glc = new GLC[1];
                        glc[cantidad] = new GLC(st);
                    } else {
                        GLC[] tmp = new GLC[glc.length + 1];
                        //   System.out.println(tmp.length);
                        for (int i = 0; i < glc.length; i++) {
                            tmp[i] = glc[i];
                        }

                        tmp[glc.length] = new GLC(st);
                        glc = tmp;
                    }

                    //System.out.print(st+"->");

                } else if (contador % 2 == 1 && contador > 1) {
                    //System.out.println(st);
                    //añadir lo que produce
                    String[] tmp = st.split(",");
                    //System.out.println(tmp.length);
                    for (int i = 0; i < tmp.length; i++) {
                        try {
                            //System.out.println(cantidad);
                            glc[cantidad].añadir(tmp[i]);
                        } catch (Exception e) {

                        }
                    }
                }
                contador++;
            }
        } catch (IOException e) {
            System.out.println("Porfa ingrese un archivo");
        }


    }


    public void checarN1() {
        for (int i = 0; i < glc.length; i++) {
            //System.out.println(glc.length);
            boolean a = true;
            for (int j = 0; j < glc[i].generados.length && a; j++) {
                String [] split=glc[i].generados[j].split("");
                for(int q=0;q<split.length;q++){
                    if(esTerminal(split[q])){
                        //System.out.println(glc[i].generador+"llega a un temrinal");
                        añadorA1(glc[i]);
                    }
                }
            }
        }

        for (int a = 0; a < glc.length; a++) {
            for (int i = 0; i < glc.length; i++) {
                for (int j = 0; j < glc[i].generados.length; j++) {
                    //if(glc[i].generados[j].contains());
                    try {
                        for (int b = 0; b < N1.length; b++) {
                            if (glc[i].generados[j].contains(N1[b].generador)) {
//                                 System.out.println(glc[i].generador);
                                if (estaEnN1(glc[i])) {
//                                     System.out.println("Metera");
                                    insertarAN1(glc[i]);
                                }
                            }
                        }
                    } catch (Exception e) {
                    }
                }
            }
        }
        String[] noEstan = new String[1];
 try {
    for (int i = 0; i < glc.length; i++) {
        if (!noEsta(glc[i])) {
            //metemos a no esta
            if (noEstan.length == 1 && noEstan[0] == null) {
                noEstan[0] = glc[i].generador;
            } else {
                String[] tmp1 = new String[noEstan.length];
                for (int k = 0; k < noEstan.length; k++) {
                    tmp1[k] = noEstan[k];
                }
                tmp1[noEstan.length - 1] = glc[i].generador;
                noEstan = tmp1;
            }
        }
    }
}catch (Exception e){

}
        try {
            for (int i = 0; i < N1.length; i++) {
                String[] tmp2 = new String[1];
                //System.out.println(N1[i].generador+"-");
                for (int j = 0; j < N1[i].generados.length; j++) {

                    for (int k = 0; k < noEstan.length; k++) {
                        if (N1[i].generados[j].contains(noEstan[k])) {

                        } else {
                            if (tmp2.length == 1 && tmp2[0] == null) {
                                tmp2[0] = N1[i].generados[j];
                                //System.out.println(N1[i].generados[j]);
                            } else {
                                String[] tmp3 = new String[tmp2.length + 1];
                                for (int z = 0; z < tmp2.length; z++) {
                                    tmp3[z] = tmp2[z];
                                }
                                tmp3[tmp2.length] = N1[i].generados[j];
                                tmp2 = tmp3;
                                //System.out.println(N1[i].generados[j]);
                            }
                        }
                    }
                }
                N1[i].generados = tmp2;
            }
        }catch (Exception e){

        }


        for (int i = 0; i < N1.length; i++) {
            String[] tmp2 = new String[1];
            //System.out.println(N1[i].generador+"-");
            for (int j = 0; j < N1[i].generados.length; j++) {
                if (N1[i].generados[j].contains("Ɛ")) {
                } else {
                    if (tmp2.length == 1 && tmp2[0] == null) {
                        tmp2[0] = N1[i].generados[j];

                    } else {
                        String[] tmp3 = new String[tmp2.length + 1];
                        for (int z = 0; z < tmp2.length; z++) {
                            tmp3[z] = tmp2[z];
                        }
                        tmp3[tmp2.length] = N1[i].generados[j];
                        tmp2 = tmp3;
                    }
                }
            }
            N1[i].generados = tmp2;
        }
    }


    public boolean noEsta(GLC dato) {
        for (int i = 0; i < N1.length; i++) {
            if (N1[i].generador.equals(dato.generador)) {
                return true;
            }
        }
        return false;
    }

    public void insertarAN1(GLC insertar) {
        GLC[] tmp = new GLC[N1.length + 1];
        for (int i = 0; i < N1.length; i++) {
            tmp[i] = N1[i];
        }
        tmp[N1.length] = insertar;
        N1 = tmp;
    }

    public boolean estaEnN1(GLC entrada) {
        boolean regreso = false;
        for (int i = 0; i < N1.length; i++) {
            if (N1[i].generador.equals(entrada.generador)) {
                regreso = false;
                return false;
            }
        }
        regreso = true;
        return regreso;
    }


    public boolean estaEnN2(String entrada){
        for (int i = 0; i < N2.length; i++) {
            if(N2[i].generador.equals(entrada)){
                return true;
            }
        }
        return false;
    }

    private void añadorA1(GLC añadir) {
        try {//ya instanciado
            GLC[] tmp = new GLC[N1.length + 1];
            for (int i = 0; i < N1.length; i++) {
                tmp[i] = N1[i];
            }
            tmp[N1.length] = añadir;
            N1 = tmp;

        } catch (NullPointerException e) {
            N1 = new GLC[1];
            N1[0] = añadir;
        }
    }


    private void imprimir() {
        System.out.print("Generadores: ");
        for (int i = 0; i < generadores.length; i++) {
            System.out.print(generadores[i] + ",");
        }
        System.out.println();
        System.out.print("terminales:");
        for (int i = 0; i < terminales.length; i++) {
            System.out.print(terminales[i] + ",");
        }
        System.out.println();
        //imprimir el glc
        for (int i = 0; i < glc.length; i++) {
            glc[i].imprimir();
            //System.out.println();
        }
    }

    public void checarNodosN1() {
        //encontrar S

        N2 = new GLC[1];
        N2[0] = N1[posicion("S")];
        //System.out.println(N2[0].generador+"AAAAAAAAAAAAAAAAAAAAAAAA");
        //encontra Los demas
        parteRecursiva(posicion("S"));
        N1 = N2;

        /*System.out.println("N1 con generadores apaaa");
        System.out.println("----------");
        for(int i=0;i< N1.length;i++){
            N1[i].imprimir();
        }*/

    }

    public void parteRecursiva(int posicion) {
        //System.out.println("------------------------------------");
        for(int i=0;i< N2.length;i++){
            //System.out.print(N2[i].generador+":");
            for(int j=0;j< N2[i].generados.length;j++){
              //  System.out.print(N2[i].generados[j]+",");
            }
        }
        //System.out.println("------------------------------------");
        for (int i = 0; i < N1[posicion].generados.length; i++) {
            //System.out.println(N1[posicion].generador+" generador");
            //si el tamaño es uno o es generador o es terminal
            if (N1[posicion].generados[i].length() == 1) {
                //    System.out.println("Entro a 1");
                for (int q = 0; q < generadores.length; q++) {
                    if (N1[posicion].generados[i].equals(generadores[q])) {

                        if (N1[posicion].generador.equals(N1[posicion].generados[i])) {
                            //SIGNINIFCA QUE YA ESTA PORQUE ES EL MISMO
                            //System.out.println(N1[posicion].generados[i]+"Es el mismo ");

                        } else {
                            //System.out.println(estaEnN2("A"));
                            if(!estaEnN2(N1[posicion].generados[i])){
                                //                              System.out.println(N1[posicion].generados[i]+" es diferente y ñ ");
                                meterAN2(N1[posicion(N1[posicion].generados[i])]);
                                parteRecursiva(posicion(N1[posicion].generados[i]));

                            }
                                                    }
                    }else {
                        //pues no puede meter a uno que no pertenece
                    }
                }
            } else {
                //System.out.println("Entro a 2");
                //System.out.println(N1[posicion].generados[i]+"|||||||");
                String[] s1 = N1[posicion].generados[i].split("");
                for (int z = 0; z < s1.length; z++) {
                    boolean algunavez = false;

                    for (int q = 0; q < generadores.length; q++) {
                        if(s1[z].equals(generadores[q])){
                            //System.out.println(s1[z]+"||||||--------");
                            if(estaEnN2(s1[z])){
                                //System.out.println("if");
                                //System.out.println("No********");

                            }else{
                                //System.out.println("else");
                                if (!N1[posicion].generador.equals(s1[z])){
                                    //System.out.println("else");
                                    //System.out.println("Meterlo|||||||||"+s1[z]);
                                    if(!estaEnN2(s1[z])){
                                        //           System.out.println("si esta");
                                        meterAN2(N1[posicion(s1[z])]);
                                        parteRecursiva(posicion(s1[z]));
                                    }else{
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }



    public void meterAN2(GLC dato) {
        boolean noesta = false;
        //checar que no este
        for (int i = 0; i < N2.length; i++) {
            if (dato.generador.equals(N2[i].generador)) {
                noesta = true;
                //System.out.println(dato.generador+" ya esta");
            }
        }
        //meter
        if (!noesta) {
            GLC[] tmp = new GLC[N2.length + 1];
            for (int i = 0; i < N2.length; i++) {
                tmp[i] = N2[i];
            }
            tmp[N2.length] = dato;
            N2 = tmp;
        }

    }

    public void imprimirN1() {

        for (int i = 0; i < N1.length; i++) {
            N1[i].imprimir();
        }

    }

    public int posicion(String dato) {
        int S = 0;
        for (int i = 0; i < N1.length; i++) {
            if (N1[i].generador.equals(dato)) {
                //System.out.println("----"+i+"");
                S = i;
            }
        }
        return S;
    }

    public void eliminarUnitarios() {
        //System.out.println("Eliminar Unitarios");

        for (int i = N1.length - 1; i >= 0; i--) {

            boolean segundabander = false;
            boolean sonTodosTerminales = false;
            int cont = 0;
            for (int j = 0; j < N1[i].generados.length; j++) {
                //System.out.println(N1[i].generador);
                if (N1[i].generados[j].length() == 1) {
                    for (int k = 0; k < terminales.length; k++) {
                        if (N1[i].generados[j].equals(terminales[k])) {
                            sonTodosTerminales = true;
                            cont++;
                        }
                    }
                } else {
                    String[] tmp = N1[i].generados[j].split("");
                    for (int f = 0; f < tmp.length; f++) {
                        if (estaPp(tmp[f])) {
                            sonTodosTerminales = true;
                            cont++;
                        } else {
                            sonTodosTerminales = false;
                        }
                    }

                }


                if (j == 0 && sonTodosTerminales) {
                    segundabander = true;

                }
                if (sonTodosTerminales && segundabander && j == N1[i].generados.length - 1) {
                    //System.out.println(N1[i].generador + cont);
                        //System.out.println("vAMOS A CAMBIAR A TODOS ");
                    if (cont == 1) {
                        for (int z = 0; z < N1.length; z++) {
                            for (int y = 0; y < N1[z].generados.length; y++) {
                                //cuando es uno
                                if (N1[z].generados[y].contains(N1[i].generador)) {
                                    N1[z].generados[y] = cambiarString(N1[i].generados[j], N1[z].generados[y], N1[i].generador);
                                }
                            }
                        }
                    } else {
                        //System.out.println("Entroaqui");
                        for (int z = 0; z < N1.length; z++) {
                            int bandera = 0;
                            for (int y = 0; y < N1[z].generados.length; y++) {
                                //cuando es uno
                                if (N1[z].generados[y].contains(N1[i].generador)) {
                                    // System.out.println(N1[i].generados[j]);
                                    String cadenaCambio = N1[z].generados[y];
                                    for (int q = 0; q < N1[i].generados.length; q++) {
                                        bandera++;
                                        if (bandera == 1) {
                                            //System.out.println(cambiarString(N1[i].generados[q],cadenaCambio,N1[i].generador));
                                            N1[z].generados[y] = cambiarString(N1[i].generados[q], cadenaCambio, N1[i].generador);
                                        } else {
                                            N1[z].añadir(cambiarString(N1[i].generados[q], cadenaCambio, N1[i].generador));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        for(int i =0;i<N1.length;i++){
            for (int j=0;j< N1[i].generados.length;j++){
                if(!esTerminal(N1[i].generados[j]) && N1[i].generados[j].length()==1){
                    System.out.println(N1[i].generados[j]+"aaaaaaaaaaaaaaaaaaaaaaaaaaa");

                }
            }
        }


    }

    public String cambiarString(String entrada, String cambiar, String letra) {
        if (cambiar.length() == 1 && letra.equals(cambiar)) {
            return entrada;
        }
        String regreso = "";
        String[] split = cambiar.split("");
        for (int i = 0; i < split.length; i++) {
            if (split[i].equals(letra)) {
                regreso = regreso + entrada;
            } else {
                regreso = regreso + split[i];
            }
        }
        return regreso;

    }

    private boolean estaPp(String s) {
        boolean pp = false;
        for (int i = 0; i < terminales.length; i++) {
            if (s.equals(terminales[i])) {
                pp = true;
            }
        }
        return pp;
    }

    public void choms() {
        System.out.println();
        for (int i = 0; i < N1.length; i++) {
            for (int j = 0; j < N1[i].generados.length; j++) {
                //System.out.println(N1[i].generador+"   en el for");
                if (N1[i].generados[j].length() == 1) {
                    //  System.out.println(N1[i].generados[j]+" en el if");
                    //comparar si es un terminal
                    if (esTerminal(N1[i].generados[j])) {
                        //buscar si tiene alguino que lo genere
                        //sino pues hacer uno que genere y cambiar todos
                        if ((quienLogenera(N1[i].generados[j]) != -1)) {
                            //no se hace nada por que ya esta
                            //System.out.println(N1[i].generados[j] + "__yaQuedo1");

                        } else {
                            //System.out.println(N1[i].generados[j] + "++Yaquedo1");
                            //aqui si tenemos que generar uno nuevo
                            //N1[i].generados[j]=cambiarString(N1[quienLogenera(N1[i].generados[j])].generador,N1[i].generados[j],N1[i].generados[j]);
                        }

                    }
                } else {
                    String[] split = N1[i].generados[j].split("");
                    //System.out.println(N1[i].generados[j]+" en el else");
                    for (int k = 0; k < split.length; k++) {
                        //comparar es terminal
                        if (esTerminal(split[k])) {
                            //buscar si tiene alguino que lo genere
                            //sino pues hacer uno que genere y cambiar todos
                            if (quienLogenera(split[k]) != -1) {
                                String tmpString = "";
                                // System.out.println(split[k] + "__YaExiste2" + N1[quienLogenera(split[k])].generador);
                                //split[k]=N1[quienLogenera(split[k])].generador;
                                //System.out.println(split[k]);
                                for (int o = 0; o < k; o++) {
                                    tmpString = tmpString + split[o];
                                }
                                tmpString = N1[quienLogenera(split[k])].generador;
                                for (int o = k + 1; o < split.length; o++) {
                                    tmpString = tmpString + split[o];
                                }
                                //System.out.println(tmpString);
                                N1[i].generados[j] = tmpString;
                            } else {
                                //            System.out.println(split[k] + "++Se tiene que generar2");
                                String tmpString = "";
                                String[] tmp1 = new String[1];
                                tmp1[0] = split[k];
                                GLC tmp = new GLC(posibles[posiblesPos++], tmp1);
                                for (int o = 0; o < k; o++) {
                                    tmpString = tmpString + split[o];
                                }
                                añadirGeneradores(tmp.generador);
                                tmpString = cambiarString(tmp.generador, split[k], split[k]);
                                for (int o = k + 1; o < split.length; o++) {
                                    tmpString = tmpString + split[o];
                                }
                                N1[i].generados[j] = tmpString;
                                insertarAN1(tmp);
                                //tmp.imprimir();
                                //System.out.println();
                                //System.out.println(N1[i].generados[j]+"||||||||");
                            }
                        } else {

                        }
                    }
                }
            }
        }
        //System.out.println();
        //System.out.println("nueva Parte");
        for (int i = 0; i < N1.length; i++) {
            try {
                for (int j = 0; j < N1[i].generados.length; j++) {
                    if (N1[i].generados[j].length() > 2) {
                        //System.out.println(N1[i].generados[j]);
                        while (N1[i].generados[j].length() > 2) {
                            String[] split = N1[i].generados[j].split("");

                            String tmp = split[N1[i].generados[j].length() - 2] + split[N1[i].generados[j].length() - 1];
                            //System.out.println(tmp + "___");
                            //hacer funcion
                            boolean encontroUno = false;
                            int xG = 0, yG = 0, quien = 0;
                            for (int x = 0; x < N1.length; x++) {
                                for (int y = 0; y < N1[x].generados.length; y++) {
                                    if (N1[x].generados[y].length() == 2) {
                                        if (N1[x].generados[y].equals(tmp)) {
                                            if (quienLogenera(tmp) != -1) {
                                                xG = x;
                                                yG = y;
                                                quien = quienLogenera(tmp);
                                                encontroUno = true;
                                                break;
                                            }
                                        }

                                    }
                                }
                            }
                            if (encontroUno == true) {
                                //                            System.out.println(N1[xG].generados[yG]+"Encontro unooo"+" "+N1[quien].generador);
                                String nuevo = "";
                                String[] parseo = N1[i].generados[j].split("");
                                for (int q = 0; q < N1[i].generados[j].length() - 2; q++) {
                                    nuevo = nuevo + parseo[q];
                                }
                                nuevo = nuevo + N1[quien].generador;
                                N1[i].generados[j] = nuevo;
                                //                         System.out.println(N1[i].generados[j]+" TIENE QUE SER ESTE");

                            } else {
                                //System.out.println(N1[xG].generados[yG] + "lo tendra que agregar" + tmp);
                                String[] tmp1 = new String[1];
                                tmp1[0] = tmp;
                                String nuevo = "";
                                String[] parseo = N1[i].generados[j].split("");
                                GLC tmpglc = new GLC(posibles[posiblesPos++], tmp1);

                                for (int q = 0; q < N1[i].generados[j].length() - 2; q++) {
                                    nuevo = nuevo + parseo[q];
                                }
                                nuevo = nuevo + tmpglc.generador;
                                añadirGeneradores(tmpglc.generador);
                                N1[i].generados[j] = nuevo;
                                insertarAN1(tmpglc);
                            }
                        }
                        break;
                    }
                }
            } catch (NullPointerException e) {
            }
        }
    }


    public void añadirGeneradores(String añadir) {
        String[] tmp = new String[generadores.length + 1];
        for (int i = 0; i < generadores.length; i++) {
            tmp[i] = generadores[i];
        }
        tmp[generadores.length] = añadir;
        generadores = tmp;
    }


    public int quienLogenera(String a) {
        int bandera = -1;
        for (int i = 0; i < N1.length; i++) {
            for (int j = 0; j < N1[i].generados.length; j++) {
                if (N1[i].generados[j].equals(a)) {
                    //System.out.println(a+"*******************");
                    return i;
                }
            }
        }
        return bandera;
    }

    public boolean esTerminal(String entrada) {
        boolean esT = false;
        for (int i = 0; i < terminales.length; i++) {
            if (entrada.equals(terminales[i])) {
                esT = true;
            }
        }
        return esT;
    }


    public int posicionGray(String dato) {
        for (int i = 0; i < generadores.length; i++) {
            if (dato.equals(generadores[i])) {
                return i;
            }
        }
        return -1;
    }

    public void grayRecursion(GLC dato) {

        for (int i = 0; i < dato.generados.length; i++) {
            String[] split = dato.generados[i].split("");
            String tmp = "";
            if (!esTerminal(split[0])) {

                for (int q = 1; q < split.length; q++) {
                    tmp = tmp + split[q];
                }
            }
            if (posicionGray(split[0]) != -1) {
                dato.generados[i] = (N1[posicionGray(split[0])].generados[0] + tmp);

                    for (int p = 1; p < N1[posicionGray(split[0])].generados.length; p++) {
                        dato.añadir(N1[posicionGray(split[0])].generados[p] + tmp);
                    }

            }
        }

        for (int i = 0; i < dato.generados.length; i++) {
            String[] split = dato.generados[i].split("");
            if (split.length > 1) {
                if (!esTerminal(split[0]) && posicion(split[0]) < posicionGray(dato.generador)) {
                    grayRecursion(dato);
                }

            }

        }


    }

    public void ordenar(){
        //System.out.println("ordenar");
        try {
            for (int i = 0; i < generadores.length; i++) {
                //   System.out.println(generadores[i]+posicion(generadores[i]));

                GLC tmp = N1[i];
                GLC tmp2 = N1[posicion(generadores[i])];
                N1[i] = tmp2;
                N1[posicion(generadores[i])] = tmp;


            }
        }catch (Exception e){

        }

    }

    public void gray() {
        ordenar();
        /*
        generadores = new String[]{"S", "A", "B"};
        terminales = new String[]{"a", "b"};
        N1 = new GLC[3];
        N1[0] = new GLC("S", new String[]{"AB"});
        N1[1] = new GLC("A", new String[]{"BS", "b"});
        N1[2] = new GLC("B", new String[]{"SA", "a"});
*/
        if (cantidadAnterior == 0 && yaTieneCantidadAnterior == false) {
            cantidadAnterior = N1.length - 1;
            yaTieneCantidadAnterior = true;
            //System.out.println("Entro");

            //checar todos
            for (int i = N1.length - 1; i >= 0; i--) {
                //vamos a llamar para ver si elimino recursion por la izquierda ya esta o tengo que cambiar porque tiene uno menor que el
                //System.out.println(N1[i].generador);
                //System.out.println(N1[i].generador+"  i:"+i+"  Posicion En generadores:"+posicionGray(N1[i].generador));
                //por cada una de sus produciones

                for (int j = 0; j < N1[i].generados.length; j++) {
                    boolean bandera = true;
                    int con = 0;
                    int cantidadOriginal = N1[i].generados[j].length();

                    if (N1[i].generados[j].length() > 1) {
                        while (bandera && cantidadOriginal - 1 != con) {
                            con++;
                            String[] tmpGenerados = N1[i].generados[j].split("");
                            if (posicionGray(N1[i].generador) > posicionGray(tmpGenerados[0]) && posicionGray(tmpGenerados[0]) >= -1) {
                                if (!esTerminal(tmpGenerados[0])) {
                                    grayRecursion(N1[i]);
                                    //System.out.println("imprimiendo");
                                    for (int z = 0; z < N1[i].generados.length; z++) {
        //                                System.out.println(N1[i].generados[z]);
                                    }
                                    buscarRecursion(N1[i]);
                                } else {
                                    bandera = false;
                                }
                            } else if (posicionGray(N1[i].generador) < posicionGray(tmpGenerados[0])) {
                                //System.out.println("tenemos que cambiar pero no hasta que lo supere ");
                                //System.out.println(N1[posicionGray(tmpGenerados[0])].generados);

                                String tmp = "";
                                for (int q = 1; q < tmpGenerados.length; q++) {
                                    tmp = tmp + tmpGenerados[q];
                                }
                                try {
                                    N1[i].generados[j] = N1[posicionGray(tmpGenerados[0])].generados[0] + tmp;


                                //System.out.println(N1[i].generados[0]);
                                //System.out.println(N1[posicionGray(tmpGenerados[0])].generados.length+" aqyu");

                                //System.out.println(tmp);
                                for (int p = 1; p < N1[posicionGray(tmpGenerados[0])].generados.length; p++) {
                                    //  System.out.println(N1[posicionGray(tmpGenerados[0])].generados[p] + tmpGenerados[1]);
                                    N1[i].añadir(N1[posicionGray(tmpGenerados[0])].generados[p] + tmp);

                                }
                                for (int p = 0; p < N1[i].generados.length; p++) {
                                    //tmpGenerados = N1[i].generados[j].split("");
                                    //                                    System.out.println(N1[i].generados[p]);
                                }
                                }catch (Exception e){

                                }

                            } else {
                                //                             System.out.println("Intento de rescate ");
                                break;
                            }
                            //igual por lo que hacemos recursion
                            if (posicionGray(N1[i].generador) == posicionGray(tmpGenerados[0])) {
                                //System.out.println("tenemos que checarRecursion");
                                buscarRecursion(N1[i]);
                            }


                            //checar que todos sean mayores
                            for (int q = 0; q < N1[i].generados.length; q++) {
                                tmpGenerados = N1[i].generados[j].split("");
                                try {
                                    if (posicionGray(N1[q].generador) > posicionGray(tmpGenerados[0])) {
                                        //        System.out.println((N1[q].generador));
                                        bandera = true;
                                    }
                                } catch (Exception e) {

                                }

                            }

                        }


                    }
                }
            }

            for (int i = N1.length - 1; i >= cantidadAnterior + 1; i--) {
                String [] añadir=new String[1];
                GLC meter=new GLC("P");
                for(int j=0;j< N1[i].generados.length;j++){
                    String[] split=N1[i].generados[j].split("");
                    String maspeque="";

                    for(int s=1;s<split.length;s++){
                        maspeque=maspeque+split[s];
                    }

                    if(!esTerminal(split[0])){
                        //System.out.println(N1[posicionGray(split[0])].generador);

                        boolean posuciibUsada=false;

                        for(int q=0;q<N1[posicionGray(split[0])].generados.length;q++) {
                            String tmpMeter = "";
                            String[] split2 = N1[posicionGray(split[0])].generados[q].split("");
                            for (int f = 0; f < split2.length; f++) {
                                tmpMeter = tmpMeter + split2[f];

                            }
                            meter.añadir(tmpMeter+maspeque);
                            //System.out.println(tmpMeter+maspeque);

                        }


                    }
                    //System.out.println(!esTerminal(split[0]));
                }
                N1[i].generados=meter.generados;
            }

        } else {

        }
    }



    public String[] quitarNull(String [] datos){
        int cantidad=0;
        for (int i=0;i<datos.length;i++){
            if(datos[i].equals(null)){
                cantidad++;
            }
        }
        System.out.println(cantidad+" cantidad nulls"+datos.length);
        return datos;
    }


    public void funcionPaProbarRecursion(){
        N1=new GLC[1];
        //String [] datos={"Sa","Sb","cA"};
        String [] datos={"Aa","Sb","cA"};
        N1[0]=new GLC("S",datos);
        buscarRecursion(N1[0]);

    }


    public int buscarRecursion(GLC checar){
        String [] aChecar=new String[1];
        String [] alNuevo=new String[1];

        boolean necesita=false;
        for(int i =0;i<checar.generados.length;i++){
            String[] partir=checar.generados[i].split("");
            if(partir[0].equals(checar.generador)){
                necesita=true;
            }
        }
        if(necesita){
        boolean necesitaNuevo=false;
        for(int i =0;i<checar.generados.length;i++){
            if(checar.generados[i].length()>1){
                //hacer String[]
                String[] datos=checar.generados[i].split("");
                if(checar.generador.equals(datos[0])){
                    necesitaNuevo=true;
                    //System.out.println(checar.generados[i]);
                    String meter="";
                    for(int q=1;q<datos.length;q++){
                        meter=meter+datos[q];
                    }
                    alNuevo=agregar(alNuevo,meter);

                }else {
                    String meter="";
                    for(int q=0;q<datos.length;q++){
                        meter=meter+datos[q];
                    }

                    aChecar=agregar(aChecar,meter);
                }
            }else{
                aChecar=agregar(aChecar,checar.generados[i]);
            }
        }
        GLC meter=new GLC(posibles[posiblesPos++]);
        aChecar=duplicarYAgregar(aChecar,meter.generador);
        alNuevo=duplicarYAgregar(alNuevo,meter.generador);
        meter.generados=alNuevo;
        añadorA1(meter);
        checar.generados=aChecar;
        int regreso=-1;
        }
        return 1;
    }

    public String[] duplicarYAgregar(String[] arreglo,String palabra){
        String [] nuevo=new String[arreglo.length*2];
        String [] meter=new String[arreglo.length];
        for(int i=0;i<arreglo.length;i++){
            meter[i]=arreglo[i]+palabra;
        }

        for(int i=0;i<arreglo.length;i++){
            nuevo[i]=arreglo[i];
        }
        for(int i=0;i<meter.length;i++){
            nuevo[i+arreglo.length]=meter[i];
        }

        return nuevo;
    }

    public String[] agregar(String[] arreglo,String palabra){
        if(arreglo.length==1 && arreglo[0]==null){
            arreglo[0]=palabra;
        }else {
            String [] nuevo=new String[arreglo.length+1];
            for(int i =0;i<arreglo.length;i++){
                nuevo[i]=arreglo[i];
            }
            nuevo[arreglo.length]=palabra;
            arreglo=nuevo;
        }
        return arreglo;
    }



    public void elminirarRecursion(int pos){
        
    }

    public void pila(){
        System.out.println();
        System.out.println("-----Automata de pila-------");
        System.out.println("De q0 a q1:\n Ɛ,⊥/ S⊥");
        System.out.println("De q1 a q1:");
        for(int i=0;i<N1.length;i++){
            for(int j=0;j<N1[i].generados.length;j++){
                String [] strings=N1[i].generados[j].split("");
                String leer="";
                String meterPila="";
                boolean yanoaplica=true;
                boolean sacarPila=true;
                String sacar="";
                sacar=N1[i].generador;
                for(int q=0;q<strings.length;q++){
                    if(esTerminal(strings[q]) && yanoaplica){
                        leer=leer+strings[q];
                    }else{

                 //           sacar=strings[q]+"____"+N1[i].generador;
                            sacarPila=false;
                 //|
                            meterPila=meterPila+strings[q];

                        yanoaplica=false;

                    }

                }
                //System.out.println();
                if(leer.equals("")){
                    System.out.print(" Ɛ,");
                }else {
                    System.out.print(" "+leer+",");
                }
                    System.out.print(sacar+"/ ");
                if(meterPila.equals("")){
                    System.out.print("Ɛ");
                }else {
                    System.out.print(meterPila);
                }
                System.out.println();
                //System.out.println();
                //System.out.println(""+leer+", "+sacar+"/ "+meterPila);
                //System.out.println(N1[i].generados[j]);

            }
        }


        System.out.println("De q1 a q2 \n Ɛ,⊥/ Ɛ");

    }

    public static void main(String[] args) {
        Principal prueba= new Principal();
        prueba.leerArchivo();

        prueba.imprimir();
        //guardar a n1 los que llegen a terminales
        prueba.checarN1();

        //prueba.N1=prueba.glc;
        System.out.println();
        //System.out.println("Impimiendo");


        System.out.println();
        //prueba.checarNodosN1();
        //System.out.println("imprimiendoN1");
        //prueba.imprimirN1();
        prueba.eliminarUnitarios();
        prueba.checarNodosN1();
        prueba.imprimirN1();


        prueba.N2=prueba.N1;

        prueba.choms();
        System.out.println("-------Choomsky-----------");

        prueba.imprimirN1();
        System.out.println();

        prueba.gray();
        System.out.println("-------FNG-----");
        prueba.imprimirN1();
        prueba.pila();


    }


}
