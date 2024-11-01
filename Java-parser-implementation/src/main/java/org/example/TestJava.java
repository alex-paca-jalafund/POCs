package org.example;

public class TestJava {
    int numtest = 1;
    String cadenatest= "";
    boolean booltest;

    public TestJava(String message){
        this.cadenatest = message;
    }

    public int suma (int num){
        return this.numtest + num;
    }
    public void repeatMessage(){
        System.out.println(this.cadenatest);
    }
}
