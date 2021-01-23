
package agents;
import gui.MainFrame;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.lang.acl.ACLMessage;

public class Receiver extends Agent {
    
      public String valor;
    String valor2;    
    boolean bandera = false;
    public MainFrame migui;        

    @Override
    public void setup() {                //inicializacion del agente
         System.out.println("\n Hola soy el agente--> "+getAID().getLocalName() +" <-- y eh sido iniciado");
        migui = new MainFrame();
        migui.setVisible(true);         //mostramos la interfaz
        //System.out.println("Ingrese palabra a buscar:\n");        
        //Scanner scan = new Scanner(System.in);
        //valor = scan.next();
        valor2 = migui.getWord();      // se obtiene la pala ingresada en la interfaz
                
        //se obtiene la palabra ingresada y se la envia al agente Buscador     
        addBehaviour(new OneShotBehaviour() {     //se ejcuta una sola vez
            @Override
            public void action() {                                

                ACLMessage message_env = new ACLMessage(ACLMessage.REQUEST);     //se envia la palabra al agente buscador
                message_env.setContent(valor2);

                message_env.addReceiver(new AID("seeker", AID.ISLOCALNAME));
                send(message_env);

            }
        });
        
        //comportamiento: recibe los links del agente buscador    
        addBehaviour(new CyclicBehaviour() {     // se ejecuta de forma ciclica

            @Override
            public void action() {
                ACLMessage message_rec = receive();   //recibe el mensaje del agente buscador 
                if (message_rec != null) {
                    valor = message_rec.getContent();                    
                    //System.out.println(valor);       // imprime los enlaces
                    migui.showLinks(valor);
                } else {
                    block();
                }
                

            }
             
        });       
       
    }
}
