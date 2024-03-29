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
        
        Pedido p = (Pedido) message.getContentObject();
        
        if(AbstractAgent.isGarcom(message)){
            
            p.preparar();
            enviarMensagem("PEDIDO_PRONTO", "Garcom", p);

        } else if(AbstractAgent.isGerente(message)){
            
            p.preparar(true);
            enviarMensagem("PEDIDO_PRONTO", "Garcom", p);
            
        }
        
        super.selectAction(message);
        
    }
    
}