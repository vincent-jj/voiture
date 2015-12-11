	import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;

	import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellRenderer;
	
public class Participer extends JPanel{

		
		public JPanel Panneau=new JPanel();
		protected JLabel lab_IdPA=new JLabel("ID point d'arrêt: ");
		protected JTextField text_IdPA=new JTextField();
		
		private JButton BConfirmerPA=new JButton("Confirmer");
		private JButton BProposerPA=new JButton("Proposer un point d'arrêt");
		private JButton BValiderPA=new JButton("Valider");

		protected JTable Tab;
		protected JScrollPane scroll_Liste;
		protected String[] title={"Id Point d'arrêt","Distance_ENSEIRB","Adresse","Ville","Frais","Position dans le trajet"};
		protected boolean test=false;
		public Participer(final String idEleve,final String idTrajet){
			Panneau.setPreferredSize(new Dimension(550,200));
			text_IdPA.setPreferredSize(new Dimension(30,20));

			this.initComponent(title,idEleve,idTrajet);
			//remplissage de la JFrame
			Panneau.add(scroll_Liste);
			Panneau.add(lab_IdPA);
			Panneau.add(text_IdPA);
			Panneau.add(BConfirmerPA);
			Panneau.add(BProposerPA);
			
			//bouton pour confirmer 
			BConfirmerPA.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg){
					JOptionPane Message=new JOptionPane();
					if(text_IdPA.getText().equals("") )
					{
						Message.showMessageDialog(null,"remplir le champ de l'ID point d'arret","erreur", JOptionPane.ERROR_MESSAGE);
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
					//**********modification de la table**********
					try{
						Connection conn= Connect.getInstance();// connexion à la base si ce n'est pas déjà fait
						Statement state =conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
						String query="SELECT * FROM PASSAGER WHERE ID_PASSAGER ="  +Integer.parseInt(idEleve); 	
						System.out.println(query); 
						ResultSet res = state.executeQuery(query);//prepare.toString());
						
					    if(res.next()==false)
					    {	System.out.println("inexistant dans la liste des passagers");
					    	String query1="INSERT INTO PASSAGER(ID_PASSAGER) VALUES ("+ Integer.parseInt(idEleve)+")";
									
							System.out.println(query1); 
						    state.executeUpdate(query1);
					    }
					    
							    
					    String query2="INSERT INTO INSCRIPTION (ID_PASSAGER, ID_TRAJET, ID_POINT_ARRET, DATE_INSCRIPTION, VALIDEE) VALUES (" +Integer.parseInt(idEleve)+", "+ Integer.parseInt(idTrajet)+", "+ Integer.parseInt(text_IdPA.getText())+", '"+ date +"', "+ 0 +" )";
					    System.out.println(query2); 
					    state.executeUpdate(query2);
					    state.close();
					    
					    /*{JOptionPane Message=new JOptionPane();
							Message.showMessageDialog(null,"Id voiture inexistant. Veuillez choisir une voiture de votre liste","erreur",JOptionPane.ERROR_MESSAGE);	
					    }*/
						state.close();

					}catch(SQLException e){
						
						Message.showMessageDialog(null,e.toString(),"erreur", JOptionPane.ERROR_MESSAGE);
						e.printStackTrace();
					}  
				}			
				}
					
				
			});
			
			
			try{
				Connection conn= Connect.getInstance();// connexion à la base si ce n'est pas déjà fait
				Statement state =conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
				
				
				//tester si le passager a déja proposé un point d'arret
				String query="SELECT * FROM INSCRIPTION WHERE ID_PASSAGER="+Integer.parseInt(idEleve)+ " AND ID_TRAJET= "+Integer.parseInt(idTrajet); 	
				System.out.println(query); 
				ResultSet res = state.executeQuery(query);//prepare.toString());
				if(res.next()==true) {test=true;}
			}catch(SQLException e){
				e.printStackTrace();
			}  
			
			//bouton pour proposer un nouveau point d'arret
			BProposerPA.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg){
					
					if(test==true)
				    {	System.out.println("Vous avez déja proposé un point d'arret pour ce trajet");
					    JOptionPane Message=new JOptionPane();
						Message.showMessageDialog(null,"Vous avez déja proposé un point d'arrêt pour ce trajet","erreur", JOptionPane.ERROR_MESSAGE);
					
				    }else{
					final Nv_PointArret Nv_PA=new Nv_PointArret(idEleve,idTrajet);
					Panneau.remove(scroll_Liste);
					Panneau.remove(lab_IdPA);
					Panneau.remove(text_IdPA);
					Panneau.remove(BConfirmerPA);
					Panneau.remove(BProposerPA);
					Panneau.add(Nv_PA.Panneau_PointA);
					Panneau.add(BValiderPA);
					
					
					//bouton de validation de PA
					BValiderPA.addActionListener(new ActionListener(){
						public void actionPerformed(ActionEvent arg){
							
							if (Nv_PA.insererChamps(idEleve,idTrajet)){
							JOptionPane Message=new JOptionPane();
							Message.showMessageDialog(null,"Point d'arrêt encours de traitement","message", JOptionPane.INFORMATION_MESSAGE);
							}
							
						}
					});
					}
				}
			});
			
			//remplissage de la JFrame par son contenu
			//this.setContentPane(Panneau);
		}
		
		
		public void initComponent(String[] title,String idEleve,String idTrajet){
			
			
			
			try{
				Connection conn= Connect.getInstance();// connexion à la base si ce n'est pas déjà fait
				Statement state =conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
				String query="SELECT POINT_ARRET.ID_POINT_ARRET,POINT_ARRET.DISTANCE_ENSEIRB,POINT_ARRET.ADRESSE,POINT_ARRET.VILLE,TRAVERSE.FRAIS,TRAVERSE.POSITION_TRAJET FROM (TRAVERSE INNER JOIN POINT_ARRET ON TRAVERSE.ID_POINT_ARRET=POINT_ARRET.ID_POINT_ARRET) WHERE TRAVERSE.ID_TRAJET=";
				query+=Integer.parseInt(idTrajet);
				System.out.println(query);
				ResultSet result = state.executeQuery(query);
				int cmp=0,i=0;
				while(result.next()){
					//compter le nbr de ligne
					cmp++;
				}
				String[][] tab_data=new String[cmp][6];
				result.beforeFirst();
				//remplissage du tableau
				while(result.next()){
						tab_data[i][0]=result.getString("ID_POINT_ARRET");
						tab_data[i][1]=result.getString("DISTANCE_ENSEIRB");
						tab_data[i][2]=result.getString("ADRESSE");
						tab_data[i][3]=result.getString("VILLE");
						tab_data[i][4]=result.getString("FRAIS");
						tab_data[i][5]=result.getString("POSITION_TRAJET");
						i++;
					
						
				}
				
				//creatin de la JTable 
				Tab=new JTable(tab_data,title){
					 public Component prepareRenderer (TableCellRenderer renderer, int index_row, int index_col){  
					        Component comp = super.prepareRenderer(renderer, index_row, index_col);
					        
					        if(index_col==3){
					        	String contenu = (String) getModel().getValueAt(index_row,index_col);
					        }
					        else{
					        	comp.setBackground(Color.WHITE);
					        }
					        
					        return comp; 
					 }
				};
				Tab.setEnabled(false);
				scroll_Liste=new JScrollPane(Tab);
				scroll_Liste.setPreferredSize(new Dimension(500,150));
				result.close();
				state.close();
			}catch(SQLException e){
				JOptionPane Message=new JOptionPane();
				Message.showMessageDialog(null,e.toString(),"erreur", JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}
			
			
		}



}
