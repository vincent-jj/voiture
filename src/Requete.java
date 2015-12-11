import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;


public class Requete {

	protected String nom;
	protected String prenom;
	protected boolean verif=false;
	
	public Requete(String var1,String var2){
	
		try{
			Connection conn=Connect.getInstance();// connexion à la base si ce n'est pas déjà fait
	Statement state =conn.createStatement();//ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
	
	//requete preparée pour verifier l'existance d'un utilisateur dans la base de donnée
	String query = "SELECT * FROM ELEVE";
	query+=" WHERE ID_ELEVE=";
	query+=Integer.parseInt(var1);
	query+=" AND MDP_ELEVE='";
	query+=var2;
	query+="'";
	System.out.println(query);
	//PreparedStatement prepare = conn.prepareStatement(query);
    //prepare.setInt(1,Integer.parseInt(var1));
    //prepare.setString(2,var2);*/
    ResultSet res = state.executeQuery(query);//prepare.toString());
    JOptionPane Message=new JOptionPane();
    if(res.next()==false)
    {
    	Message.showMessageDialog(null,"verifier vos informations","erreur", JOptionPane.ERROR_MESSAGE);
        verif=false;
    }
    else
    {
    	Message.showMessageDialog(null,res.getString("NOM_ELEVE")+" "+res.getString("PRENOM_ELEVE"),"message", JOptionPane.INFORMATION_MESSAGE);
        verif=true;
        this.nom=res.getString("NOM_ELEVE");
        this.prenom=res.getString("PRENOM_ELEVE");
        
    }
    
    //prepare.close();
    state.close();
		}catch(Exception e){
			JOptionPane Message=new JOptionPane();
			Message.showMessageDialog(null,e.toString(),"erreur", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		
}
}
