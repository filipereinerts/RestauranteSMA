package agentes;

import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.Pedido;

public class Garcom extends AbstractAgent {
    
    @Override
    protected void setup (){
        
        livre();
        receberMensagem();
        
    }

    @Override
    protected void selectAction(ACLMessage message) throws Exception {
        
        ocupado();
        
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
        
        livre();
        
    }
    
    private void livre(){
        
        DFAgentDescription dfd = new DFAgentDescription() ;
        dfd.setName(getAID());
        
        ServiceDescription sd = new ServiceDescription();
        sd.setType("Atendimento");
        sd.setName("Atendimento");
        dfd.addServices(sd);
        
        try {
            
            DFService.register(this, dfd);
            
        } catch(FIPAException e){
            
            e.printStackTrace();
            
        } 
       
    }
    
    private void ocupado(){
        
        try {
            
            DFService.deregister(this);
            
        } catch (FIPAException e) {
            
            e.printStackTrace();
        
        }
        
    }
    
}