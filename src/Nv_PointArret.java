import java.awt.Dimension;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class Nv_PointArret {
	
	//Les caractéristiques de la voiture
	protected JPanel Panneau_PointA=new JPanel();
	
	protected JTextField text_PointA=new JTextField("		Les points d'arrêts proposés:			");	
	protected JLabel lab_Distance=new JLabel("Distance par rapport à l'ENSEIRB: ");
	protected JTextField text_Distance=new JTextField();
	protected JLabel lab_Adresse=new JLabel("Adresse: ");
	protected JTextField text_Adresse=new JTextField();
	protected JLabel lab_Ville=new JLabel("Ville: ");
	protected JTextField text_Ville=new JTextField();

Nv_PointArret(final String idEleve,String text_IdTP){
		
		//partie voiture
		Panneau_PointA.setPreferredSize(new Dimension(550,150));
		text_Distance.setPreferredSize(new Dimension(340,20));
		text_Adresse.setPreferredSize(new Dimension(480,20));
		text_Ville.setPreferredSize(new Dimension(500,20));
	
		text_PointA.setEditable(false);
		Panneau_PointA.add(text_PointA);
		
		Panneau_PointA.add(lab_Distance);
		Panneau_PointA.add(text_Distance);
		
		Panneau_PointA.add(lab_Adresse);
		Panneau_PointA.add(text_Adresse);
		
		Panneau_PointA.add(lab_Ville);
		Panneau_PointA.add(text_Ville);
	
		
	}
	
	boolean insererChamps(String idEleve,String idTrajet){
		JOptionPane Message=new JOptionPane();
		if(text_Distance.getText().equals("") && text_Adresse.getText().equals("")&& text_Ville.getText().equals(""))
		{   
			
			Message.showMessageDialog(null,"remplir tous les champs","erreur", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		else{
		//lecture de la date du systeme
		Calendar calendar = Calendar.getInstance();
		int day = calendar.get(Calendar.DATE);
		int annee = calendar.get(Calendar.YEAR);
		int mois = (calendar.get(Calendar.MONTH))+1;
		String Sday=String.valueOf(day);
		String Smois=String.valueOf(mois);
		String Sannee=String.valueOf(annee);
		if(mois<10){
		    Smois="0"+Smois;
		}
		if(day<10){
			Sday="0"+Sday;
		}
		// variable string date pour la comparaison et le filtrage des resultat
		String date=Sday+"/"+Smois+"/"+Sannee;
		try{
			Connection conn= Connect.getInstance();// connexion à la base si ce n'est pas déjà fait
			Statement state =conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			
			
			//inserer point d'arret
		    String query1="INSERT INTO POINT_ARRET (DISTANCE_ENSEIRB, ADRESSE, VILLE) VALUES ("+Integer.parseInt(text_Distance.getText())+", '"+text_Adresse.getText()+"', '"+text_Ville.getText()+"')"; 
		    System.out.println(query1);
		    state.executeUpdate(query1);
		    
		    //rechercher le nouveau ID_POINT_ARRET
		    String query2="select SEQ_POINT_ARRET.CURRVAL FROM DUAL"; 	
			System.out.println(query2); 
			ResultSet res2 = state.executeQuery(query2);//prepare.toString());
			int id=res2.getInt("CURRVAL");
			System.out.println("iddddd: "+id);
			
		    //ajouter inscription
		    String query3="INSERT INTO INSCRIPTION (ID_PASSAGER, ID_TRAJET, ID_POINT_ARRET, DATE_INSCRIPTION, VALIDEE) VALUES (" +Integer.parseInt(idEleve)+", "+ Integer.parseInt(idTrajet)+", "+ id+", '"+ date +"', "+ 0 +" )"; 
		    System.out.println(query3);
		    state.executeUpdate(query3);
		    
			state.close();
			return true;

		}catch(SQLException e){
			
			Message.showMessageDialog(null,e.toString(),"erreur", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			return true;
		}  
		
	}}
}
