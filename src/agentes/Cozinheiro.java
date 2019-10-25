package agentes;

import jade.lang.acl.ACLMessage;
import main.Pedido;


public class Cozinheiro extends AbstractAgent {
    
     @Override
    protected void setup (){
        
        receberMensagem();
        
    }
    
    @Override
    protected void selectAction(ACLMessage message) throws Exception {
        
        if(AbstractAgent.isGarcom(message)){
         
            if(message.getContentObject() instanceof Pedido){
                
                Pedido p = (Pedido) message.getContentObject();
                p.preparar();
                
                enviarMensagem("PEDIDO_PRONTO", "Garcom", p);
                
            }

        }
        
        super.selectAction(message);
        
    }
    
}