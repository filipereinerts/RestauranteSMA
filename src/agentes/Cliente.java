package agentes;
import jade.lang.acl.ACLMessage;
import main.Pedido;

public class Cliente extends AbstractAgent {
    
    @Override
    protected void setup (){
        
        enviarMensagem("ENVIAR_PEDIDO", "Garcom", new Pedido());
        receberMensagem();
        
    }
    
    @Override
    protected void selectAction(ACLMessage message) throws Exception {
        
        if(AbstractAgent.isGarcom(message)){
            
            Pedido p = (Pedido) message.getContentObject();
            
            if(p.foiDemorado()) p.reclamar();
            
            if(p.reclamacoes > 3){
                
                enviarMensagem("CHAMAR_GERENTE", "Garcom", p);
                
            } else if(p.pedidoErrado || p.pedidoFrio){
                
                if(p.pedidoErrado) p.reclamar();
                if(p.pedidoFrio) p.reclamar();
                
                enviarMensagem("REENVIAR_PEDIDO", "Garcom", p);
                
            }
            
        } else {
            
            enviarMensagem("ENVIAR_PEDIDO", "Garcom", new Pedido());
            
        }
        
        super.selectAction(message);
        
    }
    
}