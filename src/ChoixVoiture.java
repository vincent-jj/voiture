import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class ChoixVoiture {
	
	//Les caractéristiques de la voiture
	protected JPanel Panneau_Voiture=new JPanel();
	
	protected JTextField text_Voiture=new JTextField("		Caractéristiques de la voiture:			");	
	protected JLabel lab_Type=new JLabel("Type de la voiture: ");
	protected JTextField text_VoitureType=new JTextField();
	protected JLabel lab_Couleur=new JLabel("Couleur de la voiture: ");
	protected JTextField text_VoitureCouleur=new JTextField();
	protected JLabel lab_NbPassager=new JLabel("Nombre de passager possible: ");
	protected JTextField text_VoitureNbPassager=new JTextField();
	protected JLabel lab_Etat=new JLabel("Etat de la voiture: ");
	protected JComboBox text_VoitureEtat = new JComboBox();
	protected JLabel lab_Divers=new JLabel("Divers: ");
	protected JTextField text_VoitureDivers=new JTextField();
	
	private JButton BConfirmerV=new JButton("Confirmer");
	
ChoixVoiture(final String idEleve){
		
		//partie voiture
		Panneau_Voiture.setPreferredSize(new Dimension(550,200));
		text_VoitureType.setPreferredSize(new Dimension(420,20));
		text_VoitureCouleur.setPreferredSize(new Dimension(405,20));
		text_VoitureNbPassager.setPreferredSize(new Dimension(350,20));
		text_VoitureEtat.setPreferredSize(new Dimension(425,20));
		text_VoitureDivers.setPreferredSize(new Dimension(485,20));
		
		text_Voiture.setEditable(false);
		Panneau_Voiture.add(text_Voiture);
		
		Panneau_Voiture.add(lab_Type);
		Panneau_Voiture.add(text_VoitureType);
		
		Panneau_Voiture.add(lab_Couleur);
		Panneau_Voiture.add(text_VoitureCouleur);
		
		Panneau_Voiture.add(lab_NbPassager);
		Panneau_Voiture.add(text_VoitureNbPassager);
		
		Panneau_Voiture.add(lab_Etat);
		for (int i=0;i<10;i++)
		text_VoitureEtat.addItem(i);
		
		Panneau_Voiture.add(text_VoitureEtat);
		
		Panneau_Voiture.add(lab_Divers);
		Panneau_Voiture.add(text_VoitureDivers);
		Panneau_Voiture.add(BConfirmerV);
		
		//**les boutons de confirmation et d'annulation**
		
		//boutton confirmation 
		BConfirmerV.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg){
				if(text_VoitureNbPassager.getText().equals(""))
			    {	
				    JOptionPane Message=new JOptionPane();
					Message.showMessageDialog(null,"Vous devez remplir au moins le nombre de passagers","erreur", JOptionPane.ERROR_MESSAGE);
				
			    }else{
				//**********modification de la table**********
				try{
					Connection conn= Connect.getInstance();// connexion à la base si ce n'est pas déjà fait
					Statement state =conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
					
					String query="SELECT * FROM CONDUCTEUR WHERE ID_CONDUCTEUR ="  +Integer.parseInt(idEleve); 	
					System.out.println(query); 
					ResultSet res = state.executeQuery(query);//prepare.toString());
					
				    if(res.next()==false)
				    {	System.out.println("inexistant dans la liste des conducteurs");
				    	String query1="INSERT INTO CONDUCTEUR(ID_CONDUCTEUR) VALUES ("+ Integer.parseInt(idEleve)+")";
								
						System.out.println(query1); 
					    state.executeUpdate(query1);
				    }
				    String query2_part1="INSERT INTO VOITURE (ID_VOITURE, ID_CONDUCTEUR";
				    String query2_part2=") VALUES (seq_voiture.nextval"+" ," +Integer.parseInt(idEleve);
			    	if (text_VoitureType.getText().length() !=0){query2_part1+=",TYPE_VOITURE";query2_part2+=", '"+text_VoitureType.getText()+"'";}
			    	if (text_VoitureCouleur.getText().length() !=0){query2_part1+=",COULEUR_VOITURE";query2_part2+=", '"+text_VoitureCouleur.getText()+"'";}
			    	
			    	query2_part1+=",NB_PASSAGER";query2_part2+=", "+Integer.parseInt(text_VoitureNbPassager.getText());
			    	
			    
			    	query2_part1+=",ETAT_VOITURE";query2_part2+=", "+text_VoitureEtat.getSelectedItem();
			    	
			    	if (text_VoitureDivers.getText().length() !=0){query2_part1+=",DIVERS";query2_part2+=", '"+text_VoitureDivers.getText()+"'";}
				    	
				  
				    String query2=query2_part1+query2_part2+")";
				    System.out.println(query2); 
				    state.executeUpdate(query2);
				    state.close();
				    
				    /*{JOptionPane Message=new JOptionPane();
						Message.showMessageDialog(null,"Id voiture inexistant. Veuillez choisir une voiture de votre liste","erreur",JOptionPane.ERROR_MESSAGE);	
				    }*/
					state.close();

				}catch(SQLException e){
					JOptionPane Message=new JOptionPane();
					Message.showMessageDialog(null,e.toString(),"erreur", JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				}  
			}			}
		});
		
	
	}
		
		
	ChoixVoiture(final String IdString,final String idEleve){
		
		//partie voiture
		Panneau_Voiture.setPreferredSize(new Dimension(550,200));
		text_VoitureType.setPreferredSize(new Dimension(420,20));
		text_VoitureCouleur.setPreferredSize(new Dimension(405,20));
		text_VoitureNbPassager.setPreferredSize(new Dimension(350,20));
		text_VoitureEtat.setPreferredSize(new Dimension(425,20));
		text_VoitureDivers.setPreferredSize(new Dimension(485,20));
		
		this.initChamps(IdString,idEleve);
		
		text_Voiture.setEditable(false);
		Panneau_Voiture.add(text_Voiture);
		
		Panneau_Voiture.add(lab_Type);
		Panneau_Voiture.add(text_VoitureType);
		
		Panneau_Voiture.add(lab_Couleur);
		Panneau_Voiture.add(text_VoitureCouleur);
		
		Panneau_Voiture.add(lab_NbPassager);
		Panneau_Voiture.add(text_VoitureNbPassager);
		
		Panneau_Voiture.add(lab_Etat);
		
		for (int i=0;i<10;i++)
			text_VoitureEtat.addItem(i);
		Panneau_Voiture.add(text_VoitureEtat);
		
		Panneau_Voiture.add(lab_Divers);
		Panneau_Voiture.add(text_VoitureDivers);
		Panneau_Voiture.add(BConfirmerV);
		
		//**les boutons de confirmation et d'annulation**
		
		//boutton confirmation 
		BConfirmerV.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg){
				
				//**********modification de la table**********
				try{
					Connection conn= Connect.getInstance();// connexion à la base si ce n'est pas déjà fait
					Statement state =conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
										
					    String query="UPDATE VOITURE SET TYPE_VOITURE='"+ text_VoitureType.getText()+"' , COULEUR_VOITURE='"+text_VoitureCouleur.getText()+"' , NB_PASSAGER="+Integer.parseInt(text_VoitureNbPassager.getText())+" , ETAT_VOITURE="+text_VoitureEtat.getSelectedItem()+" , DIVERS='"+text_VoitureDivers.getText()+"' WHERE VOITURE.ID_VOITURE="+Integer.parseInt(IdString); 
					    System.out.println(query);
					    state.executeUpdate(query);
					    state.close();
				    
				    /*{JOptionPane Message=new JOptionPane();
						Message.showMessageDialog(null,"Id voiture inexistant. Veuillez choisir une voiture de votre liste","erreur",JOptionPane.ERROR_MESSAGE);	
				    }*/
					

				}catch(SQLException e){
					JOptionPane Message=new JOptionPane();
					Message.showMessageDialog(null,e.toString(),"erreur", JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				}  
				
			}			
		});
		
		
	}
	
	void initChamps(String IdString,String idEleve){
				try{
				Connection conn=Connect.getInstance();// connexion à la base si ce n'est pas déjà fait
				Statement state =conn.createStatement();//ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
				
				//requete preparée pour verifier l'existance d'un utilisateur dans la base de donnée
				String query = "SELECT * FROM VOITURE";
				query+=" WHERE ID_VOITURE=";
				query+=Integer.parseInt(IdString);
				
				System.out.println(query);
				ResultSet res = state.executeQuery(query);//prepare.toString());
				JOptionPane Message=new JOptionPane();
				if(res.next()==false)
				{
					Message.showMessageDialog(null,"Pas de voiture avec cet ID","erreur", JOptionPane.ERROR_MESSAGE);				 
				}
				else
				{
					//Message.showMessageDialog(null,res.getString("NOM_ELEVE")+" "+res.getString("PRENOM_ELEVE"),"message", JOptionPane.INFORMATION_MESSAGE);
				    				   		    
				    text_VoitureType.setText(res.getString("TYPE_VOITURE"));
					text_VoitureCouleur.setText(res.getString("COULEUR_VOITURE"));
					text_VoitureNbPassager.setText(res.getString("NB_PASSAGER"));
					for (int i=0;i<10;i++)
						text_VoitureEtat.addItem(i);
					text_VoitureEtat.setSelectedIndex(res.getInt("ETAT_VOITURE"));
					text_VoitureDivers.setText(res.getString("DIVERS"));
				    
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
