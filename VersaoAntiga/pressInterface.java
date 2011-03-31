/*!
 ===========================================================================
 SOURCE FILE : pressinterface.java
 ===========================================================================
 DESCRIPTION.: 
 AUTHOR......: Hundson Thiago & Tyago Medeiros.
 LOCATION....: DIMAp/UFRN.
 SATARTED ON.: May/2006
 CHANGES.....: 

 TO COMPILE..: javac pressinterface.java
 OBS.........: 
 
 LAST UPDATE.: 
 ---------------------------------------------------------------------------
*/

/*imports*/
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JFileChooser;
import javax.swing.SwingConstants;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import java.io.File;


/*!@brief Classe que representa a interface do Press4All extendida aparti da classe JFrame.
**/
public class pressInterface extends JFrame{
 
 /*Atributos*/
 JTextArea statusTextArea;
 File file1;
 File file2;
 
 //-----------------
 /*!Construtor que gera a inteface do Press4all. Frames, Menus e Botões.
 **/
 public pressInterface(){
  super(" Press4All ");
  setLayout(new FlowLayout(FlowLayout.CENTER));
  //setUndecorated(true);
  //getRootPane().setWindowDecorationStyle(JRootPane.FRAME);
  file1=null;
  file2=null;
  
  //Barra de menus
  JMenuBar barra = new JMenuBar( );
  JMenuItem menuItemCompactar = new JMenuItem(" Compactar ",new ImageIcon( "compactar.gif" ));
  menuItemCompactar.addActionListener(new CompactHandler());
  JMenuItem menuItemDescompactar = new JMenuItem(" Descompactar ",new ImageIcon( "descompactar.gif" ));
  menuItemDescompactar.addActionListener(new UnpackHandler());
  JMenuItem menuItemComparar = new JMenuItem(" Comparar ",new ImageIcon( "comparar.gif" ));
  menuItemComparar.addActionListener(new CompareHandler());
  JMenuItem menuItemSair = new JMenuItem(" Sair ",new ImageIcon( "sair.gif" ));
  menuItemSair.addActionListener(new ExitHandler());
  JMenu menuOpcoes = new JMenu(" Opcoes ");
  menuOpcoes.add( menuItemCompactar );
  menuOpcoes.add( menuItemDescompactar );
  menuOpcoes.add( menuItemComparar );
  menuOpcoes.addSeparator( );
  menuOpcoes.add( menuItemSair );
  JMenuItem menuItemAjuda = new JMenuItem(" Ajuda ",new ImageIcon( "ajuda.gif" ));
  menuItemAjuda.addActionListener(new HelpHandler());
  JMenuItem menuItemSobre = new JMenuItem(" Sobre ",new ImageIcon( "sobre.gif" ));
  menuItemSobre.addActionListener(new AboutHandler());
  JMenu menuAjuda= new JMenu(" Ajuda ");
  menuAjuda.add( menuItemAjuda );
  menuAjuda.addSeparator( );
  menuAjuda.add( menuItemSobre );
  barra.add( menuOpcoes );
  barra.add( menuAjuda );
  setJMenuBar(barra);


  //Painel de botoes
  JPanel buttonJPanel=new JPanel();
  buttonJPanel.setLayout( new FlowLayout(FlowLayout.CENTER));
  JButton buttonCompactar = new JButton(" Compactar ",new ImageIcon( "compactar.gif" ));
  buttonCompactar.setToolTipText("Compactar um arquivo atraves do press4All.");
  buttonCompactar.addActionListener(new CompactHandler());
  buttonJPanel.add(buttonCompactar);
  JButton buttonDescompactar = new JButton(" Descompactar ",new ImageIcon( "descompactar.gif" ));
  buttonDescompactar.setToolTipText("Descompactar um arquivo anteriormente compactado.");
  buttonDescompactar.addActionListener(new UnpackHandler());
  buttonJPanel.add(buttonDescompactar);
  JButton buttonComparar = new JButton(" Comparar ",new ImageIcon( "comparar.gif" ));
  buttonComparar.setToolTipText("Clique aqui para comparar dois arquivos.");
  buttonComparar.addActionListener(new CompareHandler());
  buttonJPanel.add(buttonComparar);
  JButton buttonHelp = new JButton(" Ajuda ",new ImageIcon( "ajuda.gif" ));
  buttonHelp.setToolTipText("Ajuda sobre como usar o press4All.");
  buttonHelp.addActionListener(new HelpHandler());
  buttonJPanel.add(buttonHelp);
  JButton buttonExit = new JButton(" Sair ",new ImageIcon( "sair.gif" ));
  buttonExit.setToolTipText("Sair do programa.");
  buttonExit.addActionListener(new ExitHandler());
  buttonJPanel.add(buttonExit);

  //Mensagens de status
  statusTextArea = new JTextArea(" <STATUS DE PROCESSOS> \n\n",18,60);
  JScrollPane scrollText=new JScrollPane(statusTextArea);
  statusTextArea.setEditable(false);
  add( buttonJPanel,BorderLayout.CENTER );
  add( scrollText,BorderLayout.CENTER);
  
  
  setSize(700,400);
  setResizable(false);
  setVisible( true );
  
  
 }
 
 
 /*!Método para selecionar um arquivo a ser referênciado pelo atributo file1 atraves da classe JFileChooser.
  *@return int Retorna se um determinado arquivo foi referênciado ou não.
 **/
 private int setFile1(){
  JFileChooser file2work = new JFileChooser();
  file2work.setFileSelectionMode( JFileChooser.FILES_ONLY );
  int result = file2work.showOpenDialog(this);
  if(result==JFileChooser.CANCEL_OPTION) 
    return result;
  file1=file2work.getSelectedFile();
  if( file1==null || file1.getName().equals("") )
    return JFileChooser.CANCEL_OPTION;
  else
    return result;
 }
 
 /*!Método para selecionar um arquivo a ser referênciado pelo atributo file2 atraves da classe JFileChooser.
  *@return int Retorna se um determinado arquivo foi referênciado ou não.
 **/
 private int setFile2(){
  JFileChooser file2work = new JFileChooser();
  file2work.setFileSelectionMode( JFileChooser.FILES_ONLY );
  int result = file2work.showOpenDialog(this);
  if(result==JFileChooser.CANCEL_OPTION) 
    return result;
  file2=file2work.getSelectedFile();
  if( file2==null || file2.getName().equals("") )
    return JFileChooser.CANCEL_OPTION;
  else
    return result;
 }
 
 /*!Método para savar um novo arquivo referenciado pelo atributo file2 atraves da classe JFileChooser.
  *@return int Retorna se um determinado arquivo foi referênciado ou não.
 **/
 private int saveNewFile(){
  JFileChooser file2work = new JFileChooser();
  file2work.setFileSelectionMode( JFileChooser.FILES_ONLY );
  int result = file2work.showSaveDialog(this);
  if(result==JFileChooser.CANCEL_OPTION) 
    return result;
  file2=file2work.getSelectedFile();
  if( file2==null || file2.getName().equals("") )
    return JFileChooser.CANCEL_OPTION;
  else
    return result;
 }
 
 
 //Evento de compactacao 
 /*!@brief Classe que representa o evento de compactação a ser chamado pelos seus respectivos objetos na interface.
  * Implementada a partir da classe ActionListener.
 **/  
 private class CompactHandler implements ActionListener{
  public void actionPerformed(ActionEvent event){
    int resultf1=setFile1();
    if(resultf1!=JFileChooser.CANCEL_OPTION){
	 int resultf2=saveNewFile();
	 if(resultf2 != JFileChooser.CANCEL_OPTION){
	  pressInterface.this.statusTextArea.setText(" <STATUS DE PROCESSOS> \n\n");
	  compactador comp=new compactador(pressInterface.this.statusTextArea);
	  comp.compactFile(file1,file2);
	 }
	}
  }
 }
 
 //Evento de descompactacao
 /*!@brief Classe que representa o evento de descompactação a ser chamado pelos seus respectivos objetos na interface.
  * Implementada a partir da classe ActionListener.
 **/
 private class UnpackHandler implements ActionListener{
  public void actionPerformed(ActionEvent event){
    int resultf1=setFile1();
    if(resultf1!=JFileChooser.CANCEL_OPTION){
	 int resultf2=saveNewFile();
	 if(resultf2 != JFileChooser.CANCEL_OPTION){
	  pressInterface.this.statusTextArea.setText(" <STATUS DE PROCESSOS> \n\n");
	  descompactador descomp=new descompactador(pressInterface.this.statusTextArea);
	  descomp.unpackFile(file1,file2);
	 }
	}
  }
 }

 //Evento de comparacao
 /*!@brief Classe que representa o evento de Comparar a ser chamado pelos seus respectivos objetos na interface.
  * Implementada a partir da classe ActionListener.
 **/
 private class CompareHandler implements ActionListener{
  public void actionPerformed(ActionEvent event){
    int resultf1=setFile1();
    if(resultf1!=JFileChooser.CANCEL_OPTION){
	 int resultf2 = setFile2();
	 if(resultf2 != JFileChooser.CANCEL_OPTION){
	  pressInterface.this.statusTextArea.setText(" <STATUS DE PROCESSOS> \n\n");
	  if( comparar.equals(file1,file2) ){
	   pressInterface.this.statusTextArea.append("\nOs Arquivos "+pressInterface.this.file1.getName()+" e "+pressInterface.this.file2.getName()+" sao identicos.");
	  }
	  else{
	   pressInterface.this.statusTextArea.append("\nOs Arquivos "+pressInterface.this.file1.getName()+" e "+pressInterface.this.file2.getName()+" sao diferentes.");
	  }
	 }
	}
  }
 }
 
 //Evento de ajuda
 /*!@brief Classe que representa o evento referente ao Help a ser chamado pelos seus respectivos objetos na interface.
  * Implementada a partir da classe ActionListener.
 **/
 private class HelpHandler implements ActionListener{
  public void actionPerformed(ActionEvent event){
   pressInterface.this.statusTextArea.setText(" <STATUS DE PROCESSOS> \n\n");
   pressInterface.this.statusTextArea.append("\nO press4All serve para compactar os seus arquivos de forma super pratica\n");
   pressInterface.this.statusTextArea.append("\n Voce podera compactar seus arquivos seguindo os seguintes passos:");
   pressInterface.this.statusTextArea.append("\n  -> Clique em [Opcoes->Compactar] ou diretamente no botao [Compactar]");   
   pressInterface.this.statusTextArea.append("\n  -> Na primeira janela, selecione o arquivo que deseja compactar e clique em Open");
   pressInterface.this.statusTextArea.append("\n  -> Na segunda janela, salve o arquivo a ser gerado(o compactado)\n");
   pressInterface.this.statusTextArea.append("\n O processo de descompactacao tambem e muito simples:");
   pressInterface.this.statusTextArea.append("\n  -> Clique em [Opcoes->Descompactar] ou diretamente no botao [Descompactar]");   
   pressInterface.this.statusTextArea.append("\n  -> Na primeira janela, selecione o arquivo que deseja descompactar e clique em Open");
   pressInterface.this.statusTextArea.append("\n  -> Na segunda janela, salve o arquivo a ser gerado(na forma original)\n");
   pressInterface.this.statusTextArea.append("\n O press4All tambem oferece uma ferramenta extra que compara arquivos.");
   pressInterface.this.statusTextArea.append("\n Serve para ajudar Demostenes na avaliacao :p . Para utilizar:");
   pressInterface.this.statusTextArea.append("\n  -> Clique em [Opcoes->Comparar] ou diretamente no botao [Comparar]");   
   pressInterface.this.statusTextArea.append("\n  -> Na primeira janela, selecione um arquivo e clique em Open");
   pressInterface.this.statusTextArea.append("\n  -> Na segunda janela, selecione o segundo arquivo e clique em Open\n");
   pressInterface.this.statusTextArea.append("\n Para sair do programa: ");
   pressInterface.this.statusTextArea.append("\n  -> Clique em [Opcoes->Sair] ou diretamente no botao [Sair] \n\n");   
  }
 }

 //Evento do Sobre
 /*!@brief Classe que representa o evento referente ao Sobre a ser chamado pelos seus respectivos objetos na interface.
  * Implementada a partir da classe ActionListener.
 **/
 private class AboutHandler implements ActionListener{
  public void actionPerformed(ActionEvent event){
   pressInterface.this.statusTextArea.setText(" <STATUS DE PROCESSOS> \n\n");
   pressInterface.this.statusTextArea.append("\nO press4All se trata, realmente, de um trabalho da disciplina de Laboratorio de");
   pressInterface.this.statusTextArea.append("\nAlgoritmos e Estrutura de Dados II do curso de Engenharia de Computacao - UFRN,");
   pressInterface.this.statusTextArea.append("\nministrada pelo Prof. Demostenes Sena (http://www.ppgsc.ufrn.br/~demost/),");
   pressInterface.this.statusTextArea.append("\nconsistindo da implementacao do algoritmo de huffman para compactacao de dados.");
   pressInterface.this.statusTextArea.append("\n Sendo os estudantes responsaveis pelo desenvolvimento: \n");
   pressInterface.this.statusTextArea.append("\n  -Hundson Thiago (http://www.lcc.ufrn.br/~heavy/)");
   pressInterface.this.statusTextArea.append("\n  -Tyago Medeiros (http://www.lcc.ufrn.br/~tyago/)\n");   
   pressInterface.this.statusTextArea.append("\n A documentacao relativa encontra-se em http://www.lcc.ufrn.br/~heavy/compac/ \n\n");
  }
 }

 //Evento para sair
 /*!@brief Classe que representa o evento referente ao fechamento do programa a ser chamado pelos seus respectivos objetos na interface.
  * Implementada a partir da classe ActionListener.
 **/
 private class ExitHandler implements ActionListener{
  public void actionPerformed(ActionEvent event){
   System.exit(0);   
  }
 }

}
