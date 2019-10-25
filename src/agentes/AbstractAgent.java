package agentes;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.util.leap.Serializable;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AbstractAgent extends Agent {
    
    protected void enviarMensagem(String conteudo, String destinatario, Serializable obj) {
        
        System.out.println(this.getName() + " SEND MESSAGE");
        
        addBehaviour(new OneShotBehaviour() {
            @Override
            public void action() {
                
                ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                msg.addReceiver(new AID(destinatario, AID.ISLOCALNAME));
                msg.setSender(myAgent.getAID());
                
                if(obj != null){
                    try {
                        msg.setContentObject(obj);
                    } catch (IOException ex) {
                        Logger.getLogger(Garcom.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                msg.setProtocol(conteudo);
                
                
                myAgent.send(msg);
                
            }
            
        });
        
    }
    
    protected void receberMensagem(){
        
        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                
               ACLMessage msg = myAgent.receive();
               
               if(msg != null){
                   
                   System.out.println(myAgent.getName() + " RECEIVE MESSAGE");
                   
                   try {
                       selectAction(msg);
                   } catch (Exception ex) {
                       Logger.getLogger(AbstractAgent.class.getName()).log(Level.SEVERE, null, ex);
                   }
                   
                   
               } else {
                   
                   block();
                   
               }
                
            }
            
        });
        
    }
    
    protected void selectAction(ACLMessage message) throws Exception {}
    
    protected static boolean isCliente(ACLMessage message){
        
        return message.getSender().equals(new AID("Cliente", AID.ISLOCALNAME));
        
    }
    
    protected static boolean isGarcom(ACLMessage message){
        
        return message.getSender().equals(new AID("Garcom", AID.ISLOCALNAME));
        
    }
    
    protected static boolean isCozinheiro(ACLMessage message){
        
        return message.getSender().equals(new AID("Cozinheiro", AID.ISLOCALNAME));
        
    }
    
}