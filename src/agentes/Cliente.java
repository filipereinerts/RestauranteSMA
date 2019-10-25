package agentes;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.Pedido;

public class Cliente extends AbstractAgent {
    
    @Override
    protected void setup (){
        
        enviarMensagem("ENVIAR_PEDIDO", "Garcom", new Pedido());
        receberMensagem();
        
    }
    
    @Override
    protected void selectAction(ACLMessage message) throws Exception {
        
        try {
            
            Pedido p = (Pedido) message.getContentObject();
            
            if(p.foiDemorado()){
                
                p.reclamar();
                
            }
            
            if(p.pedidoErrado || p.pedidoFrio){
                
                if(p.pedidoErrado) p.reclamar();
                if(p.pedidoFrio) p.reclamar();
                
                enviarMensagem("REENVIAR_PEDIDO", "Garcom", new Pedido());
                
            } else if(p.reclamacoes > 4){
                
                enviarMensagem("CHAMAR_GERENTE", "Garcom", p);
                
            }
            
       } catch (UnreadableException ex) {
           Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
       }
        
        super.selectAction(message);
        
    }
    
}