package agentes;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.Pedido;

public class Cliente extends AbstractAgent {
    
    @Override
    protected void setup (){
        
        try {
            enviarMensagem("ENVIAR_PEDIDO", "Garcom", new Pedido(), getGarcom());
        } catch (FIPAException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        
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
    
    private DFAgentDescription[] getGarcom() throws FIPAException {
        
        DFAgentDescription template = new DFAgentDescription();
        
        ServiceDescription sd = new ServiceDescription();
        sd.setType("Atendimento");
        template.addServices(sd);
        
        return DFService.search(this, template);
         
        
    }
    
}