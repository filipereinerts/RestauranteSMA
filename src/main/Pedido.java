package main;

import jade.util.leap.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Random;

public class Pedido implements Serializable {
    
    public LocalDateTime pedidoEm;
    public LocalDateTime preparadoEm;
    public LocalDateTime entregueEm;
    
    public boolean pedidoErrado = false;
    public boolean pedidoFrio = false;
    
    public int reclamacoes;
    
    public Pedido(){
        
        pedidoEm = LocalDateTime.now();
        
    }
    
    public void preparar(){
        
        preparar(false);
        
    }
   
    public void preparar(boolean doGerente){

         Random rand = new Random();

         try {
             Thread.sleep(rand.nextInt(doGerente ? 200 : 3000));
         } catch(InterruptedException e){    
         }

         pedidoErrado = rand.nextInt(doGerente ? 2050 : 3000) > 2000;
         pedidoFrio = rand.nextInt(doGerente ? 2050 : 3000) > 2000;
         preparadoEm = LocalDateTime.now();

    }
   
    public void reclamar(){

         pedidoEm = LocalDateTime.now();
         reclamacoes++;

    }

    public void entregar(){

         Random rand = new Random();

         try {
             Thread.sleep(rand.nextInt(1000));
         } catch(InterruptedException e){    
         }

         entregueEm = LocalDateTime.now();

    }

    public long tempoDeEntrega(){

        if(pedidoEm == null || entregueEm == null) return 0;

        Duration periodo = Duration.between(pedidoEm, entregueEm);

        return periodo.toMillis();

    }

    public boolean foiDemorado(){

        long tempoAceito = (new Random()).nextInt(2000) + 2000;

        return tempoDeEntrega() > tempoAceito;

    }
    
}