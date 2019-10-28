package agentes;

import jade.lang.acl.ACLMessage;
import main.Pedido;

public class Garcom extends AbstractAgent {
    
    @Override
    protected void setup (){
        
        receberMensagem();
        
    }

    @Override
    protected void selectAction(ACLMessage message) throws Exception {
        
        Pedido p = (Pedido) message.getContentObject();
        
        if(AbstractAgent.isCliente(message)){
            
            if(message.getProtocol().equals("CHAMAR_GERENTE")){
                
                enviarMensagem("CLIENTE_CHAMOU", "Gerente", p);
                
            } else if(p.reclamacoes > 2) {
                
                enviarMensagem("COZINHEIRO_ERRANDO", "Gerente", p);
                
            } else {
                
                enviarMensagem("PREPARAR_PEDIDO", "Cozinheiro", p);
                
            }            

        } else if(AbstractAgent.isCozinheiro(message)){
            
            p.entregar();
            enviarMensagem("PEDIDO_ENTREGUE", "Cliente", p);

        }
        
        super.selectAction(message);
        
    }
    
}