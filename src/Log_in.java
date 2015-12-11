import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


public class Log_in extends JFrame {

	private JPanel Panneau=new JPanel();
	private JButton Blog_in=new JButton("Se Connecter");
	private JButton Blog_out=new JButton("Annuler");
	private JPasswordField Text_pass=new JPasswordField();
	private JTextField Text_id=new JTextField();
	private JLabel Pass=new JLabel("Password");
	private JLabel Id=new JLabel("Identificateur");
	
	private String Verif_pass,Verif_id;
	
	public Log_in(){
		
		// preparation generale de la fenetre
		this.setTitle("Covoiturage");
		this.setSize(550, 100);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setAlwaysOnTop(false);
		this.setLayout(new BorderLayout());
		// chargement de l'icone
		/*try{
			Image icone = ImageIO.read(new File("icone.png"));
			this.setIconImage(icone);
		}catch(IOException e){
			e.printStackTrace();
		}*/
		this.initComponent();
		
	}
	
	public void initComponent(){
         
		Panneau.setBackground(Color.WHITE);
		
		//les champs de la formulaire d'identification
		Text_pass.setPreferredSize(new Dimension(150,20));
		Text_id.setPreferredSize(new Dimension(150,20));
		JPanel Top=new JPanel();
		Top.add(Id);
		Top.add(Text_id);
		Top.add(Pass);
		Top.add(Text_pass);
		Panneau.add(Top,BorderLayout.NORTH);
		
		//boutton de lancement de la recherche 
		Blog_in.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg){
				Verif_pass=Text_pass.getText();
				Verif_id=Text_id.getText();
				JOptionPane Message=new JOptionPane();
				if(Verif_pass.equals("") || Verif_id.equals(""))
					{Message.showMessageDialog(null,"remplir tous les champs","erreur", JOptionPane.ERROR_MESSAGE);}
				else{
					//creation d'un objet Requete qui se charge de faire la recherche dans la base de données
				   Requete test=new Requete(Verif_id, Verif_pass);
				   if(test.verif){
					   // il doit etre un etudiant
					   dispose();
					   Choix choix=new Choix(Verif_id);
				   }else {//Message.showMessageDialog(null,"Vérifier vos données","erreur", JOptionPane.ERROR_MESSAGE);}
				   //dispose();
				   }
				}
			}
			
		});
		
		
		Blog_out.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg){
				//setVisible(false);
				dispose();
			
			}
			
		});
		Panneau.add(Blog_in,BorderLayout.SOUTH);
		Panneau.add(Blog_out,BorderLayout.SOUTH);
		this.setContentPane(Panneau);
		this.setVisible(true);
				
	}

	
}
