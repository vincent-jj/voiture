import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class ChoixTrajet {
	
	//Les caractéristiques du trajet
		protected JPanel Panneau_Trajet=new JPanel();
		
		protected JTextField text_Trajet=new JTextField("		Caractéristiques du trajet:			");	
		protected JTextField text_IdVoiture=new JTextField();
		protected JLabel lab_IdVoiture=new JLabel("Voiture du trajet: ");
		protected JLabel lab_Date=new JLabel("Date du trajet: ");
		protected JTextField text_Date=new JTextField();
		protected JLabel lab_Duree=new JLabel("Durée totale du trajet: ");
		protected JTextField text_Duree=new JTextField();
		protected JLabel lab_Distance=new JLabel("Distance totale: ");
		protected JTextField text_Distance=new JTextField();
		protected JLabel lab_Sens=new JLabel("A Partir/Vers l'ENSEIRB: ");
		protected JTextField text_Sens=new JTextField();
	
	private JButton BConfirmerT=new JButton("Confirmer");
		
	
ChoixTrajet(){
		
	//partie trajet
	Panneau_Trajet.setPreferredSize(new Dimension(550,200));
	text_IdVoiture.setPreferredSize(new Dimension(430,20));
	text_Date.setPreferredSize(new Dimension(445,20));
	text_Duree.setPreferredSize(new Dimension(400,20));
	text_Distance.setPreferredSize(new Dimension(435,20));
	text_Sens.setPreferredSize(new Dimension(390,20));
	
	text_Trajet.setEditable(false);
	Panneau_Trajet.add(text_Trajet);
	
	Panneau_Trajet.add(lab_IdVoiture);
	Panneau_Trajet.add(text_IdVoiture);
	
	Panneau_Trajet.add(lab_Date);
	Panneau_Trajet.add(text_Date);
	
	Panneau_Trajet.add(lab_Duree);
	Panneau_Trajet.add(text_Duree);
	
	Panneau_Trajet.add(lab_Distance);
	Panneau_Trajet.add(text_Distance);
	
	Panneau_Trajet.add(lab_Sens);
	Panneau_Trajet.add(text_Sens);
	Panneau_Trajet.add(BConfirmerT);

		
	//**les boutons de confirmation et d'annulation**
	
	//boutton confirmation 
	BConfirmerT.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent arg){
			
			//**********modification de la table**********
			try{
				Connection conn= Connect.getInstance();// connexion à la base si ce n'est pas déjà fait
				Statement state =conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
				
				//recherche dans la base pour verifier la validité de id voiture
				String query="SELECT * FROM VOITURE WHERE ID_VOITURE=";
				query+=Integer.parseInt(text_IdVoiture.getText());
				System.out.println(query);
				
			    ResultSet result = state.executeQuery(query);
			    //test de verification d'existance de l'id voiture
			    if(result.next()==true){
				    result.first();
				    //String date=text_Date.getText().substring(8, 10)+"/"+text_Date.getText().substring(5, 7)+"/"+text_Date.getText().substring(0, 4);
				    String query1="INSERT INTO TRAJET(ID_TRAJET,ID_VOITURE, DATE_TRAJET, DUREE_TRAJET, DISTANCE_TRAJET, SENS_TRAJET) VALUES (seq_trajet.nextval , "+Integer.parseInt(text_IdVoiture.getText())+", '"+text_Date.getText()+"', "+Integer.parseInt(text_Duree.getText())+", "+Integer.parseInt(text_Distance.getText())+", "+Integer.parseInt(text_Sens.getText())+")"; 
				    System.out.println(query1);
				    state.executeUpdate(query1);
				    state.close();
			    }
			    else{JOptionPane Message=new JOptionPane();
					Message.showMessageDialog(null,"Id voiture inexistant. Veuillez choisir une voiture de votre liste","erreur",JOptionPane.ERROR_MESSAGE);	
			    }
				state.close();

			}catch(SQLException e){
				JOptionPane Message=new JOptionPane();
				Message.showMessageDialog(null,e.toString(),"erreur", JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}  
			
			
			//actualisation de la liste des trajets
			
			
		}			
	});
		
	
	}
		
		
	ChoixTrajet(final String IdString){
		
		//partie trajet
		Panneau_Trajet.setPreferredSize(new Dimension(550,200));
		text_IdVoiture.setPreferredSize(new Dimension(430,20));
		text_Date.setPreferredSize(new Dimension(445,20));
		text_Duree.setPreferredSize(new Dimension(400,20));
		text_Distance.setPreferredSize(new Dimension(435,20));
		text_Sens.setPreferredSize(new Dimension(390,20));
		
		this.initChamps(IdString);
		
		text_Trajet.setEditable(false);
		Panneau_Trajet.add(text_Trajet);
		
		Panneau_Trajet.add(lab_IdVoiture);
		Panneau_Trajet.add(text_IdVoiture);
		
		Panneau_Trajet.add(lab_Date);
		Panneau_Trajet.add(text_Date);
		
		Panneau_Trajet.add(lab_Duree);
		Panneau_Trajet.add(text_Duree);
		
		Panneau_Trajet.add(lab_Distance);
		Panneau_Trajet.add(text_Distance);
		
		Panneau_Trajet.add(lab_Sens);
		Panneau_Trajet.add(text_Sens);
		Panneau_Trajet.add(BConfirmerT);

		
		//**les boutons de confirmation et d'annulation**
		
		//boutton confirmation 
		BConfirmerT.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg){

				//**********modification de la table**********
				try{
					Connection conn= Connect.getInstance();// connexion à la base si ce n'est pas déjà fait
					Statement state =conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
					
					//recherche dans la base pour verifier la validité de id voiture
					String query="SELECT * FROM VOITURE WHERE ID_VOITURE=";
					query+=Integer.parseInt(text_IdVoiture.getText());
					System.out.println(query);
					
				    ResultSet result = state.executeQuery(query);
				    //test de verification d'existance de l'id voiture
				    if(result.next()==true){
					    result.first();
					    //String date=text_Date.getText().substring(8, 10)+"/"+text_Date.getText().substring(5, 7)+"/"+text_Date.getText().substring(0, 4);
					    String query1="UPDATE TRAJET SET ID_VOITURE="+Integer.parseInt(text_IdVoiture.getText())+", DATE_TRAJET='"+text_Date.getText()+"', DUREE_TRAJET="+Integer.parseInt(text_Duree.getText())+", DISTANCE_TRAJET="+Integer.parseInt(text_Distance.getText())+", SENS_TRAJET="+Integer.parseInt(text_Sens.getText())+" WHERE TRAJET.ID_TRAJET="+Integer.parseInt(IdString);
					    System.out.println(query1);
					    state.executeUpdate(query1);
					    state.close();
				    }
				    else{JOptionPane Message=new JOptionPane();
						Message.showMessageDialog(null,"Id voiture inexistant. Veuillez choisir une voiture de votre liste","erreur",JOptionPane.ERROR_MESSAGE);	
				    }
					state.close();

				}catch(SQLException e){
					JOptionPane Message=new JOptionPane();
					Message.showMessageDialog(null,e.toString(),"erreur", JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				}  
				
			
		
				//actualisation de la liste des Trajets
			}			
		});
		
		
	}
	
	void initChamps(String IdString){
					try{
						Connection conn=Connect.getInstance();// connexion à la base si ce n'est pas déjà fait
				Statement state =conn.createStatement();//ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
				
				//requete preparée pour verifier l'existance d'un utilisateur dans la base de donnée
				String query = "SELECT * FROM TRAJET";
				query+=" WHERE ID_TRAJET=";
				query+=Integer.parseInt(IdString);
				
				System.out.println(query);
				ResultSet res = state.executeQuery(query);//prepare.toString());
				JOptionPane Message=new JOptionPane();
				if(res.next()==false)
				{
					Message.showMessageDialog(null,"Pas de trajet avec cet ID","erreur", JOptionPane.ERROR_MESSAGE);				 
				}
				else
				{
					//Message.showMessageDialog(null,res.getString("NOM_ELEVE")+" "+res.getString("PRENOM_ELEVE"),"message", JOptionPane.INFORMATION_MESSAGE);
				    
					text_IdVoiture.setText(res.getString("ID_VOITURE"));
					text_Date.setText(res.getString("DATE_TRAJET"));
					text_Duree.setText(res.getString("DUREE_TRAJET"));
					text_Distance.setText(res.getString("DISTANCE_TRAJET"));
					text_Sens.setText(res.getString("SENS_TRAJET"));
				    
				    
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
