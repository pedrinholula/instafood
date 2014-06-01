package interfaceHandler;

import imageHandler.Conversor;
import imageHandler.ImageViewer;
import imageHandler.MainImageViewer;
import imageHandler.Imagem;
import imageHandler.Miniaturas;

import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SpringLayout;
import javax.swing.JEditorPane;
import javax.swing.JButton;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.name.Rename;
import searchHandler.BuscaTags;
import storageHandler.Sincronizador;

import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.Color;
import java.awt.Button;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Font;
import java.awt.TextField;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.awt.Dimension;
import java.awt.Cursor;

/**
 * Essa classe implementa a interface principal do programa
 * @author Felipe César, Pedro Lopes
 *
 */
public class MainInterface {

	private SpringLayout springLayout;
	private JFrame frmInstafood;
	private TextArea textArea;
	private ImageViewer iv;
	private TextField textField;
	private Imagem newImage;
	private JComboBox<String> menuTiposDeBusca;
	private JEditorPane campoDeBusca;
	private JPanel pContainer;
	private JPanel painelLogo;
	private Button botaoAddTag;
	private JScrollPane painelResultados;
	private JScrollPane painelImagemPrincipal;
	
	private ImageViewer ivMiniatura[];
	private JPanel painelMiniaturas[];
	private int numMiniaturas;
	
	private Sincronizador sincronizador;
	private Conversor conversor;


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
	 * Inicializa os paineis que armazenarão as miniaturas correspondentes ao resultado das pesquisas do usuário
	 */
	private void inicializaMiniaturas(int limite) {

		//Remove miniaturas anteriores
		if(numMiniaturas != 0) {

			for(int i = 0; i < limite; i++) {
				painelMiniaturas[i].removeAll();
				ivMiniatura[i].removeAll();
			}
			
			pContainer = new JPanel();
			numMiniaturas = 0;
			
			return;
		}

		
		ivMiniatura = new ImageViewer[limite];
		painelMiniaturas = new JPanel[limite];
		for(int i = 0; i < limite; i++) {
			ivMiniatura[i] = new ImageViewer();
			painelMiniaturas[i] = new JPanel();
			painelMiniaturas[i].add(ivMiniatura[i]);
			numMiniaturas++;
			
			pContainer.add(painelMiniaturas[i]);
		}
	}
	
	/**
	 * Carrega as miniaturas correspondentes ao resultado da pesquisa realizada
	 */
	private void carregaMiniaturas() {
		//TODO: Colocar clique na miniatura para carregar outra imagem
		File diretorioMiniaturas = new File(".//resources//resultadosBusca");
		File[] resultadosBusca = diretorioMiniaturas.listFiles();
		
		Miniaturas miniaturas = new Miniaturas();
		
		int i = 0;
		
		for(File resultado : resultadosBusca) {
			if( !resultado.getName().startsWith("thumbnail.") && resultado.getName().compareTo("Thumbs.db") != 0 ) {
				miniaturas.geraThumbnailImg(resultado);
				i++;
			}
		}
		
		inicializaMiniaturas(i);
		
		i = 0;
		
		for(File resultado : resultadosBusca) {
			if( !resultado.getName().startsWith("thumbnail.") && resultado.getName().compareTo("Thumbs.db") != 0 ) {
				
				String caminhoImagem = resultado.getParent() + "\\thumbnail." + resultado.getName();
				
				ivMiniatura[i].setImagem( caminhoImagem );
				
				i++;
			}
		}
	}
	
	/**
	 * Inicia JFrame principal da interface
	 */
	public void iniciaInterfacePrincipal() {
		frmInstafood = new JFrame();
		frmInstafood.setMinimumSize(new Dimension(650, 400));
		frmInstafood.setTitle("Instafood");
		frmInstafood.setBounds(100, 100, 950, 600);
		frmInstafood.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		springLayout = new SpringLayout();
		frmInstafood.getContentPane().setLayout(springLayout);
		
		
	}
	
	/**
	 * Inicia Campo de TipoBusca
	 */
	private void iniciaTipoBusca() {
		String[] tipoBusca = {"Tipo de Busca", "Tag", "Comida", "Local"};
		//TODO: Remover isso quando não for mais necessário utilizar o WindowBuilder
		menuTiposDeBusca = new JComboBox/*<String>*/(tipoBusca);
		frmInstafood.getContentPane().add(menuTiposDeBusca);
	}
	
	/**
	 * Inicia Campo de Busca
	 */
	public void iniciaCampoDeBusca() {
		campoDeBusca = new JEditorPane();
		campoDeBusca.setToolTipText("Digite aqui a sua busca");
		springLayout.putConstraint(SpringLayout.NORTH, menuTiposDeBusca, 0, SpringLayout.NORTH, campoDeBusca);
		springLayout.putConstraint(SpringLayout.EAST, menuTiposDeBusca, -17, SpringLayout.WEST, campoDeBusca);
		springLayout.putConstraint(SpringLayout.WEST, campoDeBusca, 342, SpringLayout.WEST, frmInstafood.getContentPane());
		campoDeBusca.setMinimumSize(new Dimension(0, 0));
		springLayout.putConstraint(SpringLayout.NORTH, campoDeBusca, 10, SpringLayout.NORTH, frmInstafood.getContentPane());
		frmInstafood.getContentPane().add(campoDeBusca);
	}
	
	/**
	 * Inicia área de texto que guarda informações da imagem principal
	 */
	public void iniciaInformacoesImagemPrincipal() {
		textArea = new TextArea();
		springLayout.putConstraint(SpringLayout.WEST, menuTiposDeBusca, 0, SpringLayout.WEST, textArea);
		textArea.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		textArea.setFocusTraversalKeysEnabled(false);
		springLayout.putConstraint(SpringLayout.NORTH, textArea, -112, SpringLayout.SOUTH, frmInstafood.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, textArea, 215, SpringLayout.WEST, frmInstafood.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, textArea, -10, SpringLayout.SOUTH, frmInstafood.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, textArea, -145, SpringLayout.EAST, frmInstafood.getContentPane());
		textArea.setBackground(Color.WHITE);
		textArea.setEditable(false);
		frmInstafood.getContentPane().add(textArea);
	}
	
	/**
	 * Inicia painel destinado a apresentar miniaturas de imagens pesquisadas
	 */
	private void iniciaPainelMiniaturas() {
		pContainer = new JPanel();
		pContainer.setLayout(new BoxLayout(pContainer, BoxLayout.Y_AXIS) );
		
		//Painel que armazerá thumbnails de outras fotos que o usuário poderá abrir
		painelResultados = new JScrollPane(pContainer);
		springLayout.putConstraint(SpringLayout.SOUTH, menuTiposDeBusca, -16, SpringLayout.NORTH, painelResultados);
		springLayout.putConstraint(SpringLayout.SOUTH, painelLogo, -6, SpringLayout.NORTH, painelResultados);
		springLayout.putConstraint(SpringLayout.EAST, painelResultados, -6, SpringLayout.WEST, textArea);
		springLayout.putConstraint(SpringLayout.SOUTH, painelResultados, 0, SpringLayout.SOUTH, frmInstafood.getContentPane());
		springLayout.putConstraint(SpringLayout.NORTH, painelResultados, 49, SpringLayout.NORTH, frmInstafood.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, painelResultados, 0, SpringLayout.WEST, frmInstafood.getContentPane());
		frmInstafood.getContentPane().add(painelResultados);
	}
	
	/**
	 * Inicia painel que exibe a logo da aplicação
	 */
	private void iniciaPainelLogo() {
		ImageViewer logo = new ImageViewer();
		logo.setImagem(".//resources//logo.jpg");
		painelLogo = new JPanel();
		painelLogo.add(logo);
		springLayout.putConstraint(SpringLayout.NORTH, painelLogo, 2, SpringLayout.NORTH, frmInstafood.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, painelLogo, 60, SpringLayout.WEST, frmInstafood.getContentPane());
		painelLogo.setBorder(null);
		frmInstafood.getContentPane().add(painelLogo);
	}
	
	/**
	 * Inicia botão Pesquisar e configura suas funcionalidades 
	 */
	private void iniciaBotaoPesquisar() {
		
		Button botaoPesquisar = new Button("Pesquisar");
		springLayout.putConstraint(SpringLayout.NORTH, botaoPesquisar, 10, SpringLayout.NORTH, frmInstafood.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, campoDeBusca, -20, SpringLayout.WEST, botaoPesquisar);
		springLayout.putConstraint(SpringLayout.WEST, botaoPesquisar, -100, SpringLayout.EAST, frmInstafood.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, botaoPesquisar, -20, SpringLayout.EAST, frmInstafood.getContentPane());
		botaoPesquisar.setMaximumSize(new Dimension(50, 50));
		botaoPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(campoDeBusca.getText().isEmpty()) {
					return;
				}
				
				String tipo = (String)menuTiposDeBusca.getSelectedItem();
				String query = campoDeBusca.getText();
				
				try {
					
					//Aqui colocar:
						//Copiar fotos do Instagram para pasta resultadosBusca
						//Carregar Miniaturas da pesquisa realizada
					Imagem[] image = BuscaTags.buscaDoc(tipo, query);
					
					if(image == null)
						return;
					
					//TODO: criar uma classe para lidar com a mudança de arquivos
					if(image.length > 0) {
						
						//Atualiza imagem principal
						try {
							newImage = image[0];
							sincronizador.importaAnotacoes(newImage);
							sincronizador.guardaRegistro(newImage);
							textArea.setText("nova imagem selecionada!\n\nPropriedades:\n--\n" + newImage.printPropriedades() + "\n\n(REGISTRO SALVO NA PASTA RESOURCES)" );
							iv.setImagem(newImage.getCaminho());
						} catch (IOException e) {
							e.printStackTrace();
						}
						
						
						File diretorioResultado = new File(".//resources//resultadosBusca");
						File[] arquivosResultado = diretorioResultado.listFiles();
						
						//Deleta arquivos antigos
						for(File arquivo : arquivosResultado) {
							arquivo.delete();
						}
					
						//Copia arquivos encontrados para a pasta respectiva
						for(Imagem img : image) {
							File source = new File(img.getCaminho());
							File newImg = new File(".//resources//resultadosBusca//" + img.getNome());
							
							FileChannel sourceChannel = null;  
						     FileChannel destinationChannel = null;  
						  
						     try {  
						         sourceChannel = new FileInputStream(source).getChannel();  
						         destinationChannel = new FileOutputStream(newImg).getChannel();  
						         sourceChannel.transferTo(0, sourceChannel.size(),  
						                 destinationChannel);  
						     } finally {  
						         if (sourceChannel != null && sourceChannel.isOpen())  
						             sourceChannel.close();  
						         if (destinationChannel != null && destinationChannel.isOpen())  
						             destinationChannel.close();  
						    }
						     
							
						}
						
						diretorioResultado = new File(".//resources//resultadosBusca");
						arquivosResultado = diretorioResultado.listFiles();
						
						for(File arquivo : arquivosResultado) {
							if(!arquivo.getName().endsWith(".ppm"))
								continue;
							
							//System.out.println(">>> " + arquivo.getName());
							
							File dst = new File(".//resources//resultadosBusca//" + arquivo.getName() + ".jpg" );
							/*
							System.out.println("\n---\n");
							System.out.println("arquivo: " + arquivo.getAbsolutePath());
							System.out.println("destino: " + ".//resources//resultadosBusca//" + arquivo.getName() + ".jpg");
							*/
							
							conversor.convert2jpg(arquivo, dst);
						}
						arquivosResultado = diretorioResultado.listFiles();
						for(File arquivo : arquivosResultado) {
							
							if(arquivo.getName().endsWith("ppm" ) ) {
								arquivo.delete();
								
							}
						}
						
						
						
						//remove resultadosBusca
						//Atualiza novos resultados
						carregaMiniaturas();
					}
				} catch (IOException e) {
					e.printStackTrace();
				} catch (org.apache.lucene.queryparser.classic.ParseException e) {
					e.printStackTrace();
				}
			}
		});
		frmInstafood.getContentPane().add(botaoPesquisar);
	}
	
	/**
	 * Inicia painel do programa que irá exibir a imagem principal
	 */
	private void iniciaPainelImagemPrincipal() {
		iv = new MainImageViewer();
		iv.setMinimumSize(new Dimension(600, 600));
		painelImagemPrincipal = new JScrollPane(iv);
		springLayout.putConstraint(SpringLayout.NORTH, painelImagemPrincipal, 49, SpringLayout.NORTH, frmInstafood.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, painelImagemPrincipal, -9, SpringLayout.NORTH, textArea);
		springLayout.putConstraint(SpringLayout.WEST, painelImagemPrincipal, 215, SpringLayout.WEST, frmInstafood.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, painelImagemPrincipal, -21, SpringLayout.EAST, frmInstafood.getContentPane());
		frmInstafood.getContentPane().add(painelImagemPrincipal);
	}
	
	/**
	 * Inicia botão que adiciona tags a principal imagem visualizada
	 */
	private void iniciaBotaoAddTag() {

		botaoAddTag = new Button("Adiciona Tag");
		springLayout.putConstraint(SpringLayout.WEST, botaoAddTag, 27, SpringLayout.EAST, textArea);
		botaoAddTag.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(textArea.getText().toString().equals(""))
					textArea.setText("Essa funcionalidade não funciona com a imagem default. Por favor, utilize a opção \"Troca Imagem\" antes de adicionar uma tag" );
				else {
					String newTag = textField.getText();
					try {
						newImage.addTag(newTag);
						sincronizador.guardaRegistro(newImage);
					} catch (IOException e) {
						e.printStackTrace();
					}
					textArea.setText("Propriedades:\n--\n" + newImage.printPropriedades() );
					textField.setText("");
				}
			}
		});
		frmInstafood.getContentPane().add(botaoAddTag);
	}
	
	/**
	 * Inicia área de texto que exibe informações da imagem principal selecionada
	 */
	private void iniciaAreaDeTexto() {
		textField = new TextField();
		springLayout.putConstraint(SpringLayout.NORTH, textField, 29, SpringLayout.SOUTH, painelImagemPrincipal);
		springLayout.putConstraint(SpringLayout.WEST, textField, 21, SpringLayout.EAST, textArea);
		springLayout.putConstraint(SpringLayout.EAST, textField, -21, SpringLayout.EAST, frmInstafood.getContentPane());
		springLayout.putConstraint(SpringLayout.NORTH, botaoAddTag, 13, SpringLayout.SOUTH, textField);
		frmInstafood.getContentPane().add(textField);
	}
	

	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		sincronizador = new Sincronizador();
		conversor = new Conversor();
		
		numMiniaturas = 0;
		
		try{
			BuscaTags.addImageDoc();
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		
		//Inicia interface principal e seus componentes
		iniciaInterfacePrincipal();
		iniciaTipoBusca();
		iniciaCampoDeBusca();
		iniciaInformacoesImagemPrincipal();
		iniciaPainelLogo();
		iniciaPainelMiniaturas();
		iniciaBotaoPesquisar();
		iniciaPainelImagemPrincipal();
		iniciaBotaoAddTag();
		iniciaAreaDeTexto();
	}
	
}
