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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
	
public class ListeToutTrajet extends JPanel{

		
		public JPanel Panneau=new JPanel();
		protected JButton Bactual=new JButton("Actualiser");
		protected JButton Bsupprimer=new JButton("Supprimer");
		protected JTable Tab;
		protected JScrollPane scroll_Liste;
		protected String[] title={"ID Trajet","Date trajet","Durée trajet","Distance Trajet","Sens trajet","Type voiture","Prenom conducteur","Nom conducteur"};
	
		
		public ListeToutTrajet(final String idEleve,final String idVoiture){
			Panneau.removeAll();
			Panneau.setPreferredSize(new Dimension(550,200));

			
			this.initComponent(title,idEleve,idVoiture);
			//remplissage de la JFrame
			Panneau.add(scroll_Liste);
			//Panneau.add(Bactual);
			//Panneau.add(Bsupprimer);
			
			//bouton d'actualisation de la fenetre 
			// appel à initComponent pour re-exécuter la requète
			Bactual.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg){
					Panneau.removeAll();
					initComponent(title,idEleve,idVoiture);
					scroll_Liste.repaint();
					Panneau.add(scroll_Liste);
					Panneau.add(Bactual);
					Panneau.add(Bsupprimer);
					Panneau.repaint();
					revalidate();
				}
			});
			
			//bouton de suppression d'une ligne du calendrier avec une requete par un nombre
			Bsupprimer.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg){
					/*JOptionPane Qpane=new JOptionPane();
					//lecture du num de la ligne dans un string pour le delete 
					String S_num=Qpane.showInputDialog(null, "Donner le numero de rendez-vous à supprimer", "Input Number",JOptionPane.QUESTION_MESSAGE);
					if(S_num != null){
					try{
						Connection conn= Connect.getInstance();// connexion à la base si ce n'est pas déjà fait
						//requete preparée
						Statement state =conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
						String query="SELECT * FROM calendrier";
						query+=" WHERE numero= ?";
						PreparedStatement prepare = conn.prepareStatement(query);
					    prepare.setString(1,S_num);
					    ResultSet result =state.executeQuery(prepare.toString());

					    if(result.next()==true){
						query="DELETE FROM calendrier";
						query+=" WHERE numero= ?";
						prepare = conn.prepareStatement(query);
					    prepare.setString(1,S_num);
					    //exécution de la suppression
					    state.executeUpdate(prepare.toString());
					    }
					    else
					    {
					    	JOptionPane Qpane1=new JOptionPane();
					    	Qpane1.showMessageDialog(null,"numero inexistant","erreur", JOptionPane.ERROR_MESSAGE);
					    }
					    
					    //actualisation du contenu de la fenetre aprés suppression
					    Panneau.removeAll();
						initComponent(title);
						scroll_Liste.repaint();
						Panneau.add(scroll_Liste);
						Panneau.add(Bactual);
						Panneau.add(Bsupprimer);
						Panneau.repaint();
						revalidate();
						
						//fermeture de la requète
						state.close();
					     
					}catch(SQLException e){
						e.printStackTrace();
					}
					}
				*/}
			});
			
			//remplissage de la JFrame par son contenu
			//this.setContentPane(Panneau);
		}
		
		
		public void initComponent(String[] title,String idEleve,String idVoiture){
			
			try{
				Connection conn= Connect.getInstance();// connexion à la base si ce n'est pas déjà fait
				Statement state =conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
				ResultSet result = state.executeQuery("SELECT ID_TRAJET, DATE_TRAJET, DUREE_TRAJET, DISTANCE_TRAJET, SENS_TRAJET, TYPE_VOITURE, ELEVE.PRENOM_ELEVE, ELEVE.NOM_ELEVE FROM ELEVE INNER JOIN ( SELECT ID_TRAJET, DATE_TRAJET, DUREE_TRAJET, DISTANCE_TRAJET, SENS_TRAJET, TYPE_VOITURE, ID_CONDUCTEUR FROM VOITURE INNER JOIN (SELECT * FROM TRAJET WHERE sysdate < TRAJET.DATE_TRAJET) T	ON VOITURE.ID_VOITURE = T.ID_VOITURE) ON ELEVE.ID_ELEVE = ID_CONDUCTEUR");
				int cmp=0,i=0;
				while(result.next()){
					//compter le nbr de ligne 
					cmp++;
				}
				String[][] tab_data=new String[cmp][8];
				result.beforeFirst();
				//remplissage du tableau
				while(result.next()){
					
						tab_data[i][0]=result.getString("ID_TRAJET");
						tab_data[i][1]=result.getString("DATE_TRAJET");
						tab_data[i][2]=result.getString("DUREE_TRAJET");
						tab_data[i][3]=result.getString("DISTANCE_TRAJET");
						tab_data[i][4]=result.getString("SENS_TRAJET");
						tab_data[i][5]=result.getString("TYPE_VOITURE");
						tab_data[i][6]=result.getString("PRENOM_ELEVE");
						tab_data[i][7]=result.getString("NOM_ELEVE");


						i++;
					
						
				}
				
				//creation de la JTable avec colorige des cellules specifiques
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
