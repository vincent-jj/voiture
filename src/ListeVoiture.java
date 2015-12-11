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
	
public class ListeVoiture extends JPanel{

		
		public JPanel Panneau=new JPanel();
		protected JButton Bactual=new JButton("Actualiser");
		protected JButton Bsupprimer=new JButton("Supprimer");
		protected JTable Tab;
		protected JScrollPane scroll_Liste;
		protected String[] title={"Id Voiture","Type","Couleur","Nombre Passager","Etat","Divers"};
		
		public ListeVoiture(final String idEleve){
			Panneau.setPreferredSize(new Dimension(550,200));

			this.initComponent(title,idEleve);
			//remplissage de la JFrame
			Panneau.add(scroll_Liste);
			
			//bouton d'actualisation de la fenetre 
			// appel � initComponent pour re-ex�cuter la requ�te
			Bactual.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg){
					Panneau.removeAll();
					initComponent(title,idEleve);
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
					String S_num=Qpane.showInputDialog(null, "Donner le numero de rendez-vous � supprimer", "Input Number",JOptionPane.QUESTION_MESSAGE);
					if(S_num != null){
					try{
						Connection conn= Connect.getInstance();// connexion � la base si ce n'est pas d�j� fait
						//requete prepar�e
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
					    //ex�cution de la suppression
					    state.executeUpdate(prepare.toString());
					    }
					    else
					    {
					    	JOptionPane Qpane1=new JOptionPane();
					    	Qpane1.showMessageDialog(null,"numero inexistant","erreur", JOptionPane.ERROR_MESSAGE);
					    }
					    
					    //actualisation du contenu de la fenetre apr�s suppression
					    Panneau.removeAll();
						initComponent(title);
						scroll_Liste.repaint();
						Panneau.add(scroll_Liste);
						Panneau.add(Bactual);
						Panneau.add(Bsupprimer);
						Panneau.repaint();
						revalidate();
						
						//fermeture de la requ�te
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
		
		
		public void initComponent(String[] title,String idEleve){
			
			
			
			try{
				Connection conn= Connect.getInstance();// connexion � la base si ce n'est pas d�j� fait
				Statement state =conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
				String query="SELECT * FROM VOITURE INNER JOIN CONDUCTEUR ON VOITURE.ID_CONDUCTEUR=CONDUCTEUR.ID_CONDUCTEUR WHERE CONDUCTEUR.ID_CONDUCTEUR=";
				query+=Integer.parseInt(idEleve);
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
					
						tab_data[i][0]=result.getString("ID_VOITURE");
						tab_data[i][1]=result.getString("TYPE_VOITURE");
						tab_data[i][2]=result.getString("COULEUR_VOITURE");
						tab_data[i][3]=result.getString("NB_PASSAGER");
						tab_data[i][4]=result.getString("ETAT_VOITURE");
						tab_data[i][5]=result.getString("DIVERS");
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
