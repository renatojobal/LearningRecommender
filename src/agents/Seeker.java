
package agents;
import data.ConnectionDB;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Seeker extends Agent {
    
    
    static Statement st;
    String contenido = "";
    String sentencia;

    @Override
    public void setup() {              //inicializacion del agente
        System.out.println("\n Hola soy el agente--> "+getAID().getLocalName() +" <-- y eh sido iniciado");
        addBehaviour(new CyclicBehaviour() {     
            @Override
            public void action() {
                ACLMessage message_reci = receive();      //recibe el mensaje del agente detector
                
                if (message_reci != null) {

                    try {
                        st = ConnectionDB.conecction.createStatement();                                                
                        //System.out.println("iniciando busqueda");                        //Realiza la busqueda en la BD

                        sentencia = "SELECT * FROM Resource WHERE title = '" + message_reci.getContent() + "';";
                        ResultSet rs = st.executeQuery(sentencia);
                        System.out.println("Los Enlaces Relacionados con -- "+ message_reci.getContent() +" -- son:");

                        while (rs.next()) {
                            //System.out.println(rs.getString(1) + "\n");
                            contenido = contenido + rs.getString("enlace") + "\n";  //almacenamos los enlaces consultados

                        }
                        
                        ACLMessage message_env = new ACLMessage(ACLMessage.REQUEST);       //enviamos los enlaces obtenidos al agente detector
                        message_env.setContent(contenido);
                        message_env.addReceiver(new AID("receiver", AID.ISLOCALNAME));
                        send(message_env);
                       

                    } catch (SQLException ex) {
                        Logger.getLogger(Seeker.class.getName()).log(Level.SEVERE, null, ex);
                    }

                } else {
                    block();
                }
                
            }
        });
    }
}
