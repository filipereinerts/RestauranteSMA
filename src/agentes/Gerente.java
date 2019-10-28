package agentes;

import jade.lang.acl.ACLMessage;
import main.Pedido;


public class Gerente extends AbstractAgent {
    
     @Override
    protected void setup (){
        
        receberMensagem();
        
    }
    
    @Override
    protected void selectAction(ACLMessage message) throws Exception {
        
        Pedido p = (Pedido) message.getContentObject();
        
        if(message.getProtocol().equals("COZINHEIRO_ERRANDO")){
            
            enviarMensagem("PREPARAR_PEDIDO_DIREITO", "Cozinheiro", p);
            
        } else {
            
            System.out.println("GERENTE PISTOLANDO DEMITINDO TODO MUNDO");
            
        }
        
        super.selectAction(message);
        
    }
    
}