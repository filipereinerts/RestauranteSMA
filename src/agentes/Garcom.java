package agentes;

import jade.lang.acl.ACLMessage;
import jade.util.leap.Serializable;
import main.Pedido;

public class Garcom extends AbstractAgent {
    
    @Override
    protected void setup (){
        
        receberMensagem();
        
    }

    @Override
    protected void selectAction(ACLMessage message) throws Exception {
        
        if(AbstractAgent.isCliente(message)){
            
            if(message.getProtocol().equals("CHAMAR_GERENTE")){
                
                System.out.println("CHAMAR GERENTE");
                
            } else {
                
                enviarMensagem("PREPARAR_PEDIDO", "Cozinheiro", (Serializable) message.getContentObject());
                
            }            

        } else if(AbstractAgent.isCozinheiro(message)){
            
            Pedido p = (Pedido) message.getContentObject();
            p.entregar();
            
            enviarMensagem("PEDIDO_ENTREGUE", "Cliente", p);

        }
        
        super.selectAction(message);
        
    }
    
}