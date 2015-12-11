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
import javax.swing.table.TableCellRenderer;
	
public class Stat extends JPanel{

		
		public JPanel Panneau=new JPanel();
		protected JTable Tab1;
		protected JScrollPane scroll_Liste1;
		protected String[] title1={"Nom élève","Prénom élève","Moyenne"};
		protected JTable Tab2;
		protected JScrollPane scroll_Liste2;
		protected String[] title2={"Nom élève","Prénom élève","Moyenne"};
		protected JTable Tab3;
		protected JScrollPane scroll_Liste3;
		protected String[] title3={"Rang","ID élève","Distance globale parcourue"};
		protected JTable Tab4;
		protected JScrollPane scroll_Liste4;
		protected String[] title4={"Rang","Ville","Nombre de Passage"};
		
		
		protected JLabel lab_stat1=new JLabel("Le classement des élèves selon les avis pour les trajets effectués en tant que passagers :");
		private JPanel Panneau_stat1=new JPanel();
		
		protected JLabel lab_stat2=new JLabel("Le classement des conducteurs selon les avis :");
		private JPanel Panneau_stat2=new JPanel();
		
		protected JLabel lab_stat3=new JLabel("Le classement des conducteurs selon la distance parcourue :");
		private JPanel Panneau_stat3=new JPanel();
		
		protected JLabel lab_stat4=new JLabel("Le classement des villes les plus desservies :");
		private JPanel Panneau_stat4=new JPanel();
		
		
		public Stat(){
			Panneau.setPreferredSize(new Dimension(550,600)); 
			Panneau_stat1.setPreferredSize(new Dimension(550,130));
			Panneau_stat2.setPreferredSize(new Dimension(550,130));
			Panneau_stat3.setPreferredSize(new Dimension(550,130));
			Panneau_stat4.setPreferredSize(new Dimension(550,130));
			
			this.stat1(title1);
			Panneau_stat1.add(lab_stat1);
			Panneau_stat1.add(scroll_Liste1);
			
			this.stat2(title2);
			Panneau_stat2.add(lab_stat2);
			Panneau_stat2.add(scroll_Liste2);
			
			this.stat3(title3);
			Panneau_stat3.add(lab_stat3);
			Panneau_stat3.add(scroll_Liste3);
			
			this.stat4(title4);
			Panneau_stat4.add(lab_stat4);
			Panneau_stat4.add(scroll_Liste4);
			
			Panneau.add(Panneau_stat1);
			Panneau.add(Panneau_stat2);
			Panneau.add(Panneau_stat3);
			Panneau.add(Panneau_stat4);
			
			
		}
		
		
		public void stat1(String[] title){
			
			try{
				Connection conn= Connect.getInstance();// connexion à la base si ce n'est pas déjà fait
				Statement state =conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
				String query="SELECT ELEVE.NOM_ELEVE nom, ELEVE.PRENOM_ELEVE prenom, MOYENNE FROM ELEVE INNER JOIN (SELECT ID_ELEVE, AVG(NOTE_AVIS) AS MOYENNE FROM AVIS NATURAL JOIN (SELECT ID_AVIS, RECEVOIR.ID_ELEVE, ID_TRAJET FROM RECEVOIR INNER JOIN (SELECT ID_ELEVE, TRAJET.ID_TRAJET AS ID_TRAJET FROM PASSAGERS_TRAJET INNER JOIN TRAJET ON TRAJET.ID_TRAJET = PASSAGERS_TRAJET.ID_TRAJET WHERE TRAJET.DATE_TRAJET < sysdate) C ON C.ID_ELEVE = RECEVOIR.ID_ELEVE) B GROUP BY ID_ELEVE ORDER BY MOYENNE DESC) A ON ELEVE.ID_ELEVE = A.ID_ELEVE";
				ResultSet result = state.executeQuery(query);
				int cmp=0,i=0;
				while(result.next()){
					//compter le nbr de ligne
					cmp++;
				}
				String[][] tab_data=new String[cmp][3];
				result.beforeFirst();
				//remplissage du tableau
				while(result.next()){
					
						tab_data[i][0]=result.getString("nom");
						tab_data[i][1]=result.getString("prenom");
						tab_data[i][2]=result.getString("MOYENNE");
						i++;
					
						
				}
				
				//creatin de la JTable 
				Tab1=new JTable(tab_data,title1){
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
				Tab1.setEnabled(false);
				scroll_Liste1=new JScrollPane(Tab1);
				scroll_Liste1.setPreferredSize(new Dimension(500,90));
				result.close();
				state.close();
			}catch(SQLException e){
				JOptionPane Message=new JOptionPane();
				Message.showMessageDialog(null,e.toString(),"erreur", JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}
			
			
		}

		
		
	public void stat2(String[] title){
				
				try{
					Connection conn= Connect.getInstance();// connexion à la base si ce n'est pas déjà fait
					Statement state =conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
					String query="SELECT ELEVE.NOM_ELEVE nom, ELEVE.PRENOM_ELEVE prenom, MOYENNE FROM ELEVE INNER JOIN (select ID_CONDUCTEUR, AVG(AVIS.NOTE_AVIS) as MOYENNE FROM AVIS INNER JOIN (SELECT CONDUCTEUR.ID_CONDUCTEUR as ID_CONDUCTEUR, RECEVOIR.ID_AVIS as ID_AVIS FROM CONDUCTEUR INNER JOIN RECEVOIR ON CONDUCTEUR.ID_CONDUCTEUR = RECEVOIR.ID_ELEVE) T ON AVIS.ID_AVIS = T.ID_AVIS GROUP BY ID_CONDUCTEUR ORDER BY MOYENNE DESC) U ON ELEVE.ID_ELEVE = U.ID_CONDUCTEUR";
					ResultSet result = state.executeQuery(query);
					int cmp=0,i=0;
					while(result.next()){
						//compter le nbr de ligne
						cmp++;
					}
					String[][] tab_data=new String[cmp][3];
					result.beforeFirst();
					//remplissage du tableau
					while(result.next()){
						
							tab_data[i][0]=result.getString("nom");
							tab_data[i][1]=result.getString("prenom");
							tab_data[i][2]=result.getString("MOYENNE");
							i++;
						
							
					}
					
					//creatin de la JTable 
					Tab2=new JTable(tab_data,title2){
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
					Tab2.setEnabled(false);
					scroll_Liste2=new JScrollPane(Tab2);
					scroll_Liste2.setPreferredSize(new Dimension(500,90));
					result.close();
					state.close();
				}catch(SQLException e){
					JOptionPane Message=new JOptionPane();
					Message.showMessageDialog(null,e.toString(),"erreur", JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				}
				
				
			}



		public void stat3(String[] title){
			
			try{
				Connection conn= Connect.getInstance();// connexion à la base si ce n'est pas déjà fait
				Statement state =conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
				String query="SELECT ROWNUM as RANG, ID_ELEVE, SUM(DISTANCE_TRAJET) as DISTANCE FROM CONDUCTEURS_TRAJET INNER JOIN (SELECT ID_TRAJET, DISTANCE_TRAJET FROM TRAJET WHERE TRAJET.DATE_TRAJET < sysdate) T ON CONDUCTEURS_TRAJET.ID_TRAJET=T.ID_TRAJET GROUP BY ROWNUM, ID_ELEVE ORDER BY DISTANCE DESC";
				ResultSet result = state.executeQuery(query);
				int cmp=0,i=0;
				while(result.next()){
					//compter le nbr de ligne
					cmp++;
				}
				String[][] tab_data=new String[cmp][3];
				result.beforeFirst();
				//remplissage du tableau
				while(result.next()){
					
						tab_data[i][0]=result.getString("Rang");
						tab_data[i][1]=result.getString("ID_ELEVE");
						tab_data[i][2]=result.getString("DISTANCE");
						i++;
					
						
				}
				
				//creatin de la JTable 
				Tab3=new JTable(tab_data,title3){
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
				Tab3.setEnabled(false);
				scroll_Liste3=new JScrollPane(Tab3);
				scroll_Liste3.setPreferredSize(new Dimension(500,90));
				result.close();
				state.close();
			}catch(SQLException e){
				JOptionPane Message=new JOptionPane();
				Message.showMessageDialog(null,e.toString(),"erreur", JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}
			
			
		}




		public void stat4(String[] title){
			
			try{
				Connection conn= Connect.getInstance();// connexion à la base si ce n'est pas déjà fait
				Statement state =conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
				String query="SELECT ROWNUM as RANG, VILLE, N FROM (SELECT POINT_ARRET.VILLE as VILLE, COUNT(POINT_ARRET.VILLE) as N FROM TRAVERSE INNER JOIN POINT_ARRET ON TRAVERSE.ID_POINT_ARRET = POINT_ARRET.ID_POINT_ARRET GROUP BY VILLE ORDER BY N DESC)";
				ResultSet result = state.executeQuery(query);
				int cmp=0,i=0;
				while(result.next()){
					//compter le nbr de ligne
					cmp++;
				}
				String[][] tab_data=new String[cmp][3];
				result.beforeFirst();
				//remplissage du tableau
				while(result.next()){
					
						tab_data[i][0]=result.getString("RANG");
						tab_data[i][1]=result.getString("VILLE");
						tab_data[i][2]=result.getString("N");
						i++;
					
						
				}
				
				//creatin de la JTable 
				Tab4=new JTable(tab_data,title4){
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
				Tab4.setEnabled(false);
				scroll_Liste4=new JScrollPane(Tab4);
				scroll_Liste4.setPreferredSize(new Dimension(500,90));
				result.close();
				state.close();
			}catch(SQLException e){
				JOptionPane Message=new JOptionPane();
				Message.showMessageDialog(null,e.toString(),"erreur", JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}
			
			
		}

}
