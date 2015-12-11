import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;


public class Choix extends JFrame {
	
	protected String idVoiture;
	private JPanel Panneau=new JPanel();
	JTabbedPane Onglets = new JTabbedPane(/*SwingConstants.TOP*/);
	
	private JPanel Panneau_NewVoiture=new JPanel();
	private JPanel Panneau_NewTrajet=new JPanel();
	private JPanel Panneau_Stat=new JPanel();
	private JPanel Panneau_ListeToutTrajets=new JPanel();
	
	//Liste des voitures
	private JPanel Panneau_ListeVoiture=new JPanel();
	//panel caractéristiques voiture	
	private JPanel Panneau_Voiture=new JPanel();

	//pour modification de caractéristiques de voiture
	protected JTextField text_IdV=new JTextField();	
	protected JLabel lab_IdV=new JLabel("Id Voiture");
	
	private JButton BChoisirV=new JButton("Choisir");
	private JButton BSupprimerV=new JButton("Supprimer");
	private JButton BAjouterV=new JButton("Ajouter une voiture");
	private JButton BActualiserV=new JButton("Actualiser liste voiture");

	//pour liste mes trajets
	private JPanel Panneau_ListeTrajet=new JPanel();
	
	//pour modification de caractéristiques de mes trajet
	private JPanel Panneau_Trajet=new JPanel();
	protected JTextField text_IdT=new JTextField();	
	protected JLabel lab_IdT=new JLabel("Id Trajet");
	
	private JButton BChoisirT=new JButton("Choisir");
	private JButton BAjouterT=new JButton("Ajouter un Trajet");
	private JButton BActualiserT=new JButton("Actualiser liste trajet");

	//pour liste tous les trajets
	private JPanel Panneau_ListeTTrajet=new JPanel();
	
	
	//pour participer a un trajet
	private JPanel Panneau_ListePA=new JPanel();
	protected JTextField text_IdTP=new JTextField();	
	protected JLabel lab_IdTP=new JLabel("Id Trajet");
	
	private JButton BParticiper=new JButton("Participer");
	
public Choix(String idEleve){
		
		// preparation generale de la fenetre
		this.setTitle("Covoiturage");
		this.setSize(550, 600);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setAlwaysOnTop(false);
		this.setLayout(new BorderLayout());
		this.initComponent(idEleve);
		
	}
	
	public void initComponent(final String idEleve){
         		
		//construction des Onglets 
		Panneau_NewVoiture.setPreferredSize(new Dimension(550,600));
		Panneau_NewTrajet.setPreferredSize(new Dimension(550,600));
		Panneau_Stat.setPreferredSize(new Dimension(550,600));
		Panneau_ListeToutTrajets.setPreferredSize(new Dimension(550,600));
		
		
		//****************Panneau New Trajet**************

		//partie liste trajet
		Panneau_ListeTrajet.setPreferredSize(new Dimension(550,150));

		ListeTrajet listeTrajet=new ListeTrajet(idEleve);
		Panneau_ListeTrajet=listeTrajet.Panneau;
		text_IdT.setPreferredSize(new Dimension(30,20));
		
		
		//boutton modification
		BChoisirT.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg){
				JOptionPane Message=new JOptionPane();
				if(text_IdT.getText().equals("") )
					{Message.showMessageDialog(null,"remplir le champ de l'ID trajet","erreur", JOptionPane.ERROR_MESSAGE);}
				else{
					ChoixTrajet choixT=new ChoixTrajet(text_IdT.getText());

					Panneau_NewTrajet.remove(Panneau_Trajet);
					Panneau_Trajet=choixT.Panneau_Trajet;
					Panneau_NewTrajet.add(Panneau_ListeTrajet);
					Panneau_NewTrajet.add(lab_IdT);
					Panneau_NewTrajet.add(text_IdT);
					Panneau_NewTrajet.add(BChoisirT);
					Panneau_NewTrajet.add(BAjouterT);
					Panneau_NewTrajet.add(BActualiserT);
					Panneau_NewTrajet.add(Panneau_Trajet);
					
				
					//remplissage du contenu de la JFrame
					setContentPane(Panneau);
					setVisible(true);
			}}			
		});
		
		//boutton ajouter
		BAjouterT.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg){

				Panneau_NewTrajet.remove(Panneau_Trajet);
				ChoixTrajet choixT=new ChoixTrajet();
				Panneau_Trajet=choixT.Panneau_Trajet;
				Panneau_NewTrajet.add(Panneau_ListeTrajet);
				Panneau_NewTrajet.add(lab_IdT);
				Panneau_NewTrajet.add(text_IdT);
				Panneau_NewTrajet.add(BChoisirT);
				Panneau_NewTrajet.add(BAjouterT);
				Panneau_NewTrajet.add(BActualiserT);
				Panneau_NewTrajet.add(Panneau_Trajet);
				
				//remplissage du contenu de la JFrame
				setContentPane(Panneau);
				setVisible(true);
							
			}
			
		});
		
		
		//boutton actualiser
		BActualiserT.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg){
				
				Panneau_NewTrajet.remove(Panneau_Trajet);
				ChoixTrajet choixT=new ChoixTrajet();
				Panneau_Trajet=choixT.Panneau_Trajet;
				Panneau_NewTrajet.remove(Panneau_ListeTrajet);
				ListeTrajet listeTrajet=new ListeTrajet(idEleve);
				Panneau_ListeTrajet=listeTrajet.Panneau;
				Panneau_NewTrajet.add(Panneau_ListeTrajet);
				Panneau_NewTrajet.add(lab_IdT);
				Panneau_NewTrajet.add(text_IdT);
				Panneau_NewTrajet.add(BChoisirT);
				Panneau_NewTrajet.add(BAjouterT);
				Panneau_NewTrajet.add(BActualiserT);
				Panneau_NewTrajet.add(Panneau_Trajet);
				
				
				//remplissage du contenu de la JFrame
				setContentPane(Panneau);
				setVisible(true);
							
			}
			
		});													
						
						
						
				
		//******Remplissage Onglet du nouveau trajet*******
		Panneau_NewTrajet.add(Panneau_ListeTrajet);
		Panneau_NewTrajet.add(lab_IdT);
		Panneau_NewTrajet.add(text_IdT);
		Panneau_NewTrajet.add(BChoisirT);
		Panneau_NewTrajet.add(BAjouterT);
		Panneau_NewTrajet.add(BActualiserT);
				
			
		
		//******************Panneau NewVoiture*******************
		
		//partie liste voiture
		Panneau_ListeVoiture.setPreferredSize(new Dimension(550,150));

		ListeVoiture listeVoiture=new ListeVoiture(idEleve);
		Panneau_ListeVoiture=listeVoiture.Panneau;
		text_IdV.setPreferredSize(new Dimension(30,20));
		
		
		//boutton modification
		BChoisirV.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg){
				JOptionPane Message=new JOptionPane();
				if(text_IdV.getText().equals("") )
					{Message.showMessageDialog(null,"remplir le champ de l'ID voiture","erreur", JOptionPane.ERROR_MESSAGE);}
				else{
					ChoixVoiture choixV=new ChoixVoiture(text_IdV.getText(),idEleve);
					Panneau_NewVoiture.remove(Panneau_Voiture);
					Panneau_Voiture=choixV.Panneau_Voiture;
					Panneau_NewVoiture.add(Panneau_ListeVoiture);
					Panneau_NewVoiture.add(lab_IdV);
					Panneau_NewVoiture.add(text_IdV);
					Panneau_NewVoiture.add(BChoisirV);
					Panneau_NewVoiture.add(BAjouterV);
					Panneau_NewVoiture.add(BActualiserV);
					Panneau_NewVoiture.add(Panneau_Voiture);

							
					setContentPane(Panneau);
					setVisible(true);

					
			}
			
			}
		});
		
		//boutton suppression de voiture
				BSupprimerV.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent arg){
						JOptionPane Message=new JOptionPane();
						if(text_IdV.getText().equals("") )
							{Message.showMessageDialog(null,"remplir le champ de l'ID voiture","erreur", JOptionPane.ERROR_MESSAGE);}
						else{
							try{
								Connection conn= Connect.getInstance();// connexion à la base si ce n'est pas déjà fait
								//requete preparée
								Statement state =conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
								String query="delete from VOITURE where ID_VOITURE="+Integer.parseInt(text_IdV.getText())+" AND ID_CONDUCTEUR="+Integer.parseInt(idEleve);
								System.out.println(query);
							    //exécution de la suppression
							    state.executeUpdate(query);
							    state.close();
							     
							}catch(SQLException e){
								Message.showMessageDialog(null,e.toString(),"erreur", JOptionPane.ERROR_MESSAGE);
								e.printStackTrace();
							}
							Message.showMessageDialog(null,"Suppression effectuée avec succes","message", JOptionPane.INFORMATION_MESSAGE);
					}
					
					}
				});
		
		//boutton ajouter
		BAjouterV.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg){
				Panneau_NewVoiture.remove(Panneau_Voiture);
				ChoixVoiture choixV=new ChoixVoiture(idEleve);
				Panneau_Voiture=choixV.Panneau_Voiture;
				Panneau_NewVoiture.add(Panneau_ListeVoiture);
				Panneau_NewVoiture.add(lab_IdV);
				Panneau_NewVoiture.add(text_IdV);
				Panneau_NewVoiture.add(BChoisirV);
				Panneau_NewVoiture.add(BSupprimerV);
				Panneau_NewVoiture.add(BAjouterV);
				Panneau_NewVoiture.add(BActualiserV);
				Panneau_NewVoiture.add(Panneau_Voiture);

								
				//remplissage du contenu de la JFrame
				setContentPane(Panneau);
				setVisible(true);
							
			}
			
		});
			
		//boutton actualiser
		BActualiserV.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg){
				Panneau_NewVoiture.remove(Panneau_Voiture);
				ChoixVoiture choixV=new ChoixVoiture(idEleve);
				Panneau_Voiture=choixV.Panneau_Voiture;
				Panneau_NewVoiture.remove(Panneau_ListeVoiture);
				ListeVoiture listeVoiture=new ListeVoiture(idEleve);
				Panneau_ListeVoiture=listeVoiture.Panneau;
				Panneau_NewVoiture.add(Panneau_ListeVoiture);
				Panneau_NewVoiture.add(lab_IdV);
				Panneau_NewVoiture.add(text_IdV);
				Panneau_NewVoiture.add(BChoisirV);
				Panneau_NewVoiture.add(BSupprimerV);
				Panneau_NewVoiture.add(BAjouterV);
				Panneau_NewVoiture.add(BActualiserV);
				//Panneau_NewVoiture.add(Panneau_Voiture);

								
				//remplissage du contenu de la JFrame
				setContentPane(Panneau);
				setVisible(true);
							
			}
			
		});	
				
				
		
		//******Remplissage Onglet du nouveau Voiture*******
		Panneau_NewVoiture.add(Panneau_ListeVoiture);
		Panneau_NewVoiture.add(lab_IdV);
		Panneau_NewVoiture.add(text_IdV);
		Panneau_NewVoiture.add(BChoisirV);
		Panneau_NewVoiture.add(BSupprimerV);
		Panneau_NewVoiture.add(BAjouterV);
		Panneau_NewVoiture.add(BActualiserV);

		
		
		//******************Panneau Liste des Trajets********************
		ListeToutTrajet listeToutTrajet=new ListeToutTrajet(idEleve, idVoiture);
		Panneau_ListeTTrajet=listeToutTrajet.Panneau;
		text_IdTP.setPreferredSize(new Dimension(30,20));
		
		//boutton participer a un trajet (lister les points d'arrets existants)
		BParticiper.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg){
				JOptionPane Message=new JOptionPane();
				if(text_IdTP.getText().equals("") )
				{
					Message.showMessageDialog(null,"remplir le champ de l'ID trajet","erreur", JOptionPane.ERROR_MESSAGE);
				}
				else{
				Panneau_ListeToutTrajets.remove(Panneau_ListeTTrajet);
				Panneau_ListeToutTrajets.remove(Panneau_ListePA);				
				Participer participer=new Participer(idEleve,text_IdTP.getText());
				Panneau_ListePA=participer.Panneau;
				Panneau_ListeToutTrajets.add(Panneau_ListePA);
				
				
				//******Remplissage Onglet de liste trajets*******
				Panneau_ListeToutTrajets.add(Panneau_ListeTTrajet);
				Panneau_ListeToutTrajets.add(lab_IdTP);
				Panneau_ListeToutTrajets.add(text_IdTP);
				Panneau_ListeToutTrajets.add(BParticiper);
				Panneau_ListeToutTrajets.add(Panneau_ListePA);
				
				//remplissage du contenu de la JFrame

				setContentPane(Panneau);
				setVisible(true);
				
			}}
			
		});
		
		
		
		
		//******************Panneau Statistiques*******************
		Stat stat=new Stat();
		Panneau_Stat=stat.Panneau;
		
				
		
		//******Remplissage Onglet de liste trajets*******
		Panneau_ListeToutTrajets.add(Panneau_ListeTTrajet);
		Panneau_ListeToutTrajets.add(lab_IdTP);
		Panneau_ListeToutTrajets.add(text_IdTP);
		Panneau_ListeToutTrajets.add(BParticiper);
		Panneau_ListeToutTrajets.add(Panneau_ListePA);

						
		
		
				
				
		
		
		//remplissage onglets
		Onglets.add("Liste des Trajets",Panneau_ListeToutTrajets);
		Onglets.add("Nouvelle Voiture",Panneau_NewVoiture);
		Onglets.add("Nouveau Trajet",Panneau_NewTrajet);
		Onglets.add("Statistiques",Panneau_Stat);
		
		
		
		//remplissage du contenu de la JFrame
		Panneau.add(Onglets);
		this.setContentPane(Panneau);
		this.setVisible(true);
				
	}


}
