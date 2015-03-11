package no.itera.lego;

import lejos.hardware.Button;

public class Main {

  public static void main(String[] args) {
    System.out.println("IteraBot reporting for duty");

    Helpers helper = new Helpers();

//    helper.forward(13);
//    helper.backward(10);
//    helper.forward(10);
//    helper.backward(13);

    boolean run = true;
    while(run){
      helper.forward(13);
      helper.backward(10);
      helper.forward(10);
      helper.backward(13);
    }
  }
}
