package interfaceHandler;

import imageHandler.ImageViewer;
import imageHandler.Imagem;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.SpringLayout;
import javax.swing.JEditorPane;
import javax.swing.JButton;

import storageHandler.Sincronizador;

import java.awt.EventQueue;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.Color;
import java.awt.Button;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.TextField;
import java.io.IOException;
import java.awt.Dimension;
import java.awt.Cursor;

/**
 * Essa classe implementa a interface principal do programa
 * @author Felipe César, Pedro Lopes
 *
 */
public class MainInterface {

	private JFrame frmInstafood;
	private TextArea textArea;
	private ImageViewer iv;
	private TextField textField;
	private Imagem newImage;
	private JComboBox<String> menuTiposDeBusca;
	private JEditorPane campoDeBusca;
	
	private Sincronizador sincronizador;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainInterface window = new MainInterface();
					window.frmInstafood.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainInterface() {
		initialize();
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		sincronizador = new Sincronizador();
		
		//Inicializa propriedades da interface principal
		frmInstafood = new JFrame();
		frmInstafood.setMinimumSize(new Dimension(650, 400));
		frmInstafood.setTitle("Instafood");
		frmInstafood.setBounds(100, 100, 950, 600);
		frmInstafood.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SpringLayout springLayout = new SpringLayout();
		frmInstafood.getContentPane().setLayout(springLayout);
		
		
		//Define o tipo de busca que será realizado
		String[] tipoBusca = {"Tipo de Busca", "Tag", "Comida", "Local"};
		menuTiposDeBusca = new JComboBox<String>(tipoBusca);
		springLayout.putConstraint(SpringLayout.NORTH, menuTiposDeBusca, 10, SpringLayout.NORTH, frmInstafood.getContentPane());
		frmInstafood.getContentPane().add(menuTiposDeBusca);
		
		//Será utilizado para guardar a string de busca do usuário
		campoDeBusca = new JEditorPane();
		springLayout.putConstraint(SpringLayout.WEST, campoDeBusca, 120, SpringLayout.WEST, menuTiposDeBusca);
		campoDeBusca.setMinimumSize(new Dimension(0, 0));
		springLayout.putConstraint(SpringLayout.NORTH, campoDeBusca, 10, SpringLayout.NORTH, frmInstafood.getContentPane());
		campoDeBusca.setText("Digite aqui a sua busca");
		frmInstafood.getContentPane().add(campoDeBusca);
		
		//Apresenta informações básicas da foto sendo exibida
		textArea = new TextArea();
		textArea.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		textArea.setFocusTraversalKeysEnabled(false);
		springLayout.putConstraint(SpringLayout.NORTH, textArea, -112, SpringLayout.SOUTH, frmInstafood.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, textArea, 215, SpringLayout.WEST, frmInstafood.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, textArea, -10, SpringLayout.SOUTH, frmInstafood.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, textArea, -145, SpringLayout.EAST, frmInstafood.getContentPane());
		textArea.setBackground(Color.WHITE);
		textArea.setEditable(false);
		frmInstafood.getContentPane().add(textArea);
		
		Button botaoPesquisar = new Button("Pesquisar");
		springLayout.putConstraint(SpringLayout.EAST, campoDeBusca, -20, SpringLayout.WEST, botaoPesquisar);
		springLayout.putConstraint(SpringLayout.NORTH, botaoPesquisar, 0, SpringLayout.NORTH, menuTiposDeBusca);
		springLayout.putConstraint(SpringLayout.WEST, botaoPesquisar, -100, SpringLayout.EAST, frmInstafood.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, botaoPesquisar, -20, SpringLayout.EAST, frmInstafood.getContentPane());
		botaoPesquisar.setMaximumSize(new Dimension(50, 50));
		botaoPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Realiza pesquisa
				//Tipo de Busca: (String)menuTiposDeBusca.getSelectedItem()
				//Texto da busca: campoDeBusca.getText() 
				
				//CArrega imagens
				
			}
		});
		frmInstafood.getContentPane().add(botaoPesquisar);
		
		//Armazena logo do programa
		ImageViewer logo = new ImageViewer();
		logo.setImagem(".//resources//logo.jpg");
		JScrollPane painelLogo = new JScrollPane(logo);
		springLayout.putConstraint(SpringLayout.NORTH, painelLogo, 0, SpringLayout.NORTH, frmInstafood.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, painelLogo, 0, SpringLayout.WEST, frmInstafood.getContentPane());
		painelLogo.setBorder(null);
		frmInstafood.getContentPane().add(painelLogo);
		
		//Painel que armazerá thumbnails de outras fotos que o usuário poderá abrir
		Panel panel = new Panel();
		springLayout.putConstraint(SpringLayout.SOUTH, logo, -6, SpringLayout.NORTH, panel);
		springLayout.putConstraint(SpringLayout.EAST, panel, -6, SpringLayout.WEST, textArea);
		springLayout.putConstraint(SpringLayout.SOUTH, panel, 0, SpringLayout.SOUTH, frmInstafood.getContentPane());
		springLayout.putConstraint(SpringLayout.NORTH, panel, 49, SpringLayout.NORTH, frmInstafood.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, panel, 0, SpringLayout.WEST, frmInstafood.getContentPane());
		panel.setBackground(Color.DARK_GRAY);
		frmInstafood.getContentPane().add(panel);
		
		//Funcionalidade provisória que permite o usuário trocar a imagem que está sendo exibida
		JButton btnTrocaImagem = new JButton("Troca Imagem");
		springLayout.putConstraint(SpringLayout.WEST, menuTiposDeBusca, 10, SpringLayout.EAST, btnTrocaImagem);
		springLayout.putConstraint(SpringLayout.EAST, menuTiposDeBusca, 120, SpringLayout.EAST, btnTrocaImagem);
		springLayout.putConstraint(SpringLayout.WEST, btnTrocaImagem, 101, SpringLayout.WEST, painelLogo);
		springLayout.putConstraint(SpringLayout.EAST, btnTrocaImagem, 110, SpringLayout.EAST, painelLogo);
		springLayout.putConstraint(SpringLayout.EAST, logo, -6, SpringLayout.WEST, btnTrocaImagem);
		springLayout.putConstraint(SpringLayout.NORTH, btnTrocaImagem, 10, SpringLayout.NORTH, frmInstafood.getContentPane());
		btnTrocaImagem.setFont(new Font("Tahoma", Font.PLAIN, 10));
		//Troca a imagem principal e exibe suas informações
		btnTrocaImagem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String imagemSelecionada = iv.choseImage("", "");
				try {
					newImage = new Imagem(imagemSelecionada);
					sincronizador.guardaRegistro(newImage);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				textArea.setText("nova imagem selecionada!\n\nPropriedades:\n--\n" + newImage.printPropriedades() + "\n\n(REGISTRO SALVO NA PASTA RESOURCES)" );
			}
		});
		frmInstafood.getContentPane().add(btnTrocaImagem);
		
		//Região do programa que exibe a imagem principal
		iv = new ImageViewer();
		iv.setMinimumSize(new Dimension(600, 600));
		JScrollPane scrollPane = new JScrollPane(iv);
		springLayout.putConstraint(SpringLayout.SOUTH, menuTiposDeBusca, -16, SpringLayout.NORTH, scrollPane);
		springLayout.putConstraint(SpringLayout.NORTH, scrollPane, 49, SpringLayout.NORTH, frmInstafood.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, scrollPane, -9, SpringLayout.NORTH, textArea);
		springLayout.putConstraint(SpringLayout.WEST, scrollPane, 215, SpringLayout.WEST, frmInstafood.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, scrollPane, -21, SpringLayout.EAST, frmInstafood.getContentPane());
		frmInstafood.getContentPane().add(scrollPane);
		
		//Permite que o usuário adicione uma tag a imagem
		Button button_1 = new Button("Adiciona Tag");
		springLayout.putConstraint(SpringLayout.WEST, button_1, 27, SpringLayout.EAST, textArea);
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(textArea.getText().toString().equals(""))
					textArea.setText("Essa funcionalidade não funciona com a imagem default. Por favor, utilize a opção \"Troca Imagem\" antes de adicionar uma tag" );
				else {
					String newTag = textField.getText();
					try {
						newImage.addTag(newTag);
						sincronizador.adicionaTag(newImage, newTag);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					textArea.setText("nova imagem selecionada!\n\nPropriedades:\n--\n" + newImage.printPropriedades() + "\n\n(REGISTRO SALVO NA PASTA RESOURCES)" );
					textField.setText("");
				}
			}
		});
		frmInstafood.getContentPane().add(button_1);
		
		textField = new TextField();
		springLayout.putConstraint(SpringLayout.NORTH, textField, 29, SpringLayout.SOUTH, scrollPane);
		springLayout.putConstraint(SpringLayout.WEST, textField, 21, SpringLayout.EAST, textArea);
		springLayout.putConstraint(SpringLayout.EAST, textField, -21, SpringLayout.EAST, frmInstafood.getContentPane());
		springLayout.putConstraint(SpringLayout.NORTH, button_1, 13, SpringLayout.SOUTH, textField);
		frmInstafood.getContentPane().add(textField);
	}
	
}
