package huffman;

/*!
 ===========================================================================
 SOURCE FILE : descompactador.java
 ===========================================================================
 DESCRIPTION.: 
 AUTHOR......: Hundson Thiago & Tyago Medeiros.
 LOCATION....: DIMAp/UFRN.
 SATARTED ON.: May/2006
 CHANGES.....: 

 TO COMPILE..: javac descompactador.java
 OBS.........: 
 
 LAST UPDATE.:
 ---------------------------------------------------------------------------
*/

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.File;
import java.util.PriorityQueue;
import javax.swing.JTextArea;

/*!@brief Classe de implementacao de um descompactador de arquivos compactados atraves da classe compactador*/
public class descompactador{
 
 /*!Área de textos para exibição de possíveis mensagens*/
 private JTextArea statusTextArea;
 /*!Arquivo compactado a ser descompactado*/
 private File fileIn; 
 /*!Novo arquivo a ser gerado no processo de descompactação*/
 private File fileOut;
 /*!Byte de cabecalho do arquivo aberto pra ser descompactado(fileIn)*/
 private byte tableAspect;
 /*!Tabela de representacoes dos bytes*/
 private int tabelaRepresentacoes[]; 
 /*!Indica a quantidade de bytes distintos que aparecem no arquivo original*/
 private int quantDeRep; 
 /*Referencia para a raiz da arvore a ser criada*/
 private unpackAuxTree raiz;

 /*!Instancia unica do descompactador. Padrao Singleton. */
 private static descompactador instancia = null;

 /*! Acesso a instancia unica do descompactador. Singleton*/
 public static descompactador getInstancia()
 {
	if(instancia==null)
		instancia = new descompactador();
	return instancia;
 }

  
 /*!Construtor default que serve para criar um objeto descompactador que
  *ficará na espera da especificação dos arquivos a serem descompactados.
  */
 protected descompactador( ){
  statusTextArea=null;
  fileIn=null;
  fileOut=null;
 }

 /*!Construtor que serve para criar um objeto descompactador que ficará 
  *na espera da especificação dos arquivos a serem descompactados.
  *@param TextArea JTextArea para exibição de mensagens
  */
 protected descompactador(JTextArea TextArea){
  statusTextArea=TextArea;
  fileIn=null;
  fileOut=null;
 }

 
 /*!Construtor de um descompactador que recebe imediatamente
  *um arquivo a ser descompactado para um arquivo de mesmo nome 
  *mais a extensão ".press4All".
  *@param arquivoIn Arquivo a ser carregado para descompactacao
  */
 protected descompactador(File arquivoIn){
   statusTextArea=null;
   fileIn = arquivoIn;
   fileOut = new File( arquivoIn.getPath()+".press4All" );
 }

 /*!Construtor de um descompactador que recebe imediatamente
  *um arquivo a ser descompactado e o novo a ser gerado.
  *@param arquivoIn Arquivo a ser carregado para descompactacao
  *@param arquivoOut Arquivo a ser gerado apos descompactacao
  */
 protected descompactador(File arquivoIn, File arquivoOut){
   statusTextArea=null;
   fileIn = arquivoIn;
   fileOut = arquivoOut;
 }

 /*!Serve para setar a área de texto de exibição de mensagens do objeto.
  *@param TextArea JTextArea para exibição de mensagens
  */
 public void setTextArea(JTextArea TextArea){
  statusTextArea=TextArea;
 }

 /*!Descompacta um arquivo para um arquivo de mesmo nome mais a extensão ".press4All".
  *@param arquivoIn Arquivo a ser carregado para descompactacao
  */
 public void unpackFile(File arquivoIn){
   fileIn = arquivoIn;
   fileOut = new File( arquivoIn.getPath()+".press4All" );
   unpackFile();
 } 

 /*!Descompacta um arquivo para um novo tambem passado.
  *@param arquivoIn Arquivo a ser carregado para descompactacao
  *@param arquivoOut Arquivo a ser gerado apos descompactacao
  */
 public void unpackFile(File arquivoIn, File arquivoOut){
   fileIn = arquivoIn;
   fileOut = arquivoOut;
   unpackFile();
 } 
 
 /*!Descompacta o arquivo que consta em fileIn para o arquivo 
  *que consta em fileOut, caso ambos sejam validos.
  */
 public void unpackFile(){
    if( fileIn==null || fileOut==null ) 
    {
      if(statusTextArea!=null)
         statusTextArea.append("\nConsta arquivos invalidos");
      return;
    }
	
    FileInputStream entrada;
    FileOutputStream saida;

	try {    entrada = new FileInputStream( fileIn );    }
	catch( java.io.IOException e ) {
	    if(statusTextArea!=null)
         statusTextArea.append("\n>> Erro na abertura do arquivo[" + fileIn.getName() + "]!");
	    return;
	}
	try {    saida = new FileOutputStream( fileOut );    }
	catch( java.io.IOException e ) {
      if(statusTextArea!=null)
       statusTextArea.append("\n>> Erro na abertura do arquivo[" + fileOut.getName() + "]!");
	  return;
	}
    
   /*Lendo o cabecalho1(especificacao da tabela) */
  /*Primeiro byte: (chamemos o primeiro bit de bit1)[0000 0000](e o ultimo de bit8)
   *Os bits 8 e 7 indicam se cada linha vai usar um(00), dois(01), tres(10) ou quatro(11) bytes
   *O bit6 se vamos gravar toda a tabela(0) ou so alguns elementos especificados(1)
   *Do [bit5,bit1] a quantidade de linhas gravadas caso gravemos apenas linhas especificas
   **/  	
   int tableAspect; 
   
   try {   tableAspect = entrada.read();  }
   catch( java.io.IOException e ) { // O tratamento dessa exececao eh obrigatorio.
	    if(statusTextArea!=null)
         statusTextArea.append("\n>> Erro durante a leitura!");
	    return;
   }
   
   int bits7e8= (tableAspect&3);
   boolean bit6 = (tableAspect&4)==4?true:false;//true=1 e false=0

   if(bit6==false){  quantDeRep = 256;  }
   else{
    quantDeRep = (tableAspect>>3);
   }

   tabelaRepresentacoes=new int[256];

   

   try {   
    //maior representacao
    if(bit6==false){  //0**
     for ( int i=0; i < 256; i++ ){	  
	  if(bits7e8>=3)  tabelaRepresentacoes[i]= entrada.read();
	  if(bits7e8>=2)  tabelaRepresentacoes[i]=(tabelaRepresentacoes[i]<<8)|entrada.read();
	  if(bits7e8>=1)  tabelaRepresentacoes[i]=(tabelaRepresentacoes[i]<<8)|entrada.read();
	  tabelaRepresentacoes[i]=(tabelaRepresentacoes[i]<<8)|entrada.read();
     }
    }
    else{ //1**
     for ( int k=0; k < quantDeRep; k++ ){	
      int i = entrada.read();
      if(bits7e8>=3)  tabelaRepresentacoes[i]= entrada.read();
	  if(bits7e8>=2)  tabelaRepresentacoes[i]=(tabelaRepresentacoes[i]<<8)|entrada.read();
	  if(bits7e8>=1)  tabelaRepresentacoes[i]=(tabelaRepresentacoes[i]<<8)|entrada.read();
	  tabelaRepresentacoes[i]=(tabelaRepresentacoes[i]<<8)|entrada.read();
     }//fim do for  
    } 
   }
   catch( java.io.IOException e ) { // O tratamento dessa exececao eh obrigatorio.
	    if(statusTextArea!=null)
         statusTextArea.append("\n>> Erro durante a leitura!");
	    return ;
   }
  
  
   int posicaoMaiorRep = 0;
   for ( int i=0; i < 256; i++ ){
     if(tabelaRepresentacoes[posicaoMaiorRep] < tabelaRepresentacoes[i]){
	  posicaoMaiorRep = i;
	 }
   }

   //construir arvore
   buildTreeFromTable( );

   byte p1; /*antepenultimo lido*/
   byte p2; /*penultimo lido*/
   int p3; /*ultimo lido*/
   IteratorAuxTree itr = new IteratorAuxTree( raiz );
   
      
   try {
   	p1 = (byte)entrada.read();
   	p2 = (byte)entrada.read();
   	
	while ( ( p3 = entrada.read() ) != -1 ){
     try{
      
      for(int i=0;i<8;i++){
       if( itr.itrNodeIsLeaf() )
       {
        saida.write( itr.getByte() );
        itr.setItr(raiz);
       }
       itr.go2Bit( getBit(p1,i) );
      }
      
     }
     catch(Exception e){ 
	    if(statusTextArea!=null)
         statusTextArea.append("\nO arquivo "+fileIn.getName()+" esta corrompido.");
	    return;
     } 
     p1=p2;
     p2=(byte)p3;

    }
	/* Neste ponto, temos:
	p1=byte com informacao e com lixo
	p2=byte de informacao de lixo
	p3=-1 indicando final de arquivo
	*/
	try{
		
	 for(int i=0;i<(8-(int)p2);i++){
      if( itr.itrNodeIsLeaf() )
      {
        saida.write( itr.getByte() );
        itr.setItr(raiz);
      }
      itr.go2Bit( getBit(p1,i) );
     }
     if( itr.itrNodeIsLeaf() )
     {
        saida.write( itr.getByte() );
        itr.setItr(raiz);
     }

     
    }
    catch(Exception e){
	   if(statusTextArea!=null)
        statusTextArea.append("\n>> Erro durante a leitura!");
	   return;
    }
	 
   }
   catch( java.io.IOException e ) { // O tratamento dessa exececao eh obrigatorio.
      if(statusTextArea!=null)
       statusTextArea.append(">> Erro durante a leitura!");
	  return;
   }



	// IOException deve ser tratada.
   try {    entrada.close();   }
   catch( java.io.IOException e ) { // O tratamento dessa exececao eh obrigatorio.
      if(statusTextArea!=null)
       statusTextArea.append(">> Erro durante a leitura!");
	  return;
   }
   try {    saida.close();   }
   catch( java.io.IOException e ) { // O tratamento dessa exececao eh obrigatorio.
	    if(statusTextArea!=null)
         statusTextArea.append(">> Erro durante a leitura!");
	    return;
   }
   if(statusTextArea!=null)
       statusTextArea.append("\nO Arquivo "+fileIn.getName()+" foi descompactado para "+fileOut.getName()+".");
	  

 }
 /*!Constrói a árvore inserindo cada representação da tabela através de 
  *de chamadas sucessivas ao método insertNodeInTree().
  */
 private void buildTreeFromTable(){
    raiz = new unpackAuxTree(-1,null,null,false);
    for(int i=0;i<256;i++){
	 if(tabelaRepresentacoes[i]!=0){
      int counter=0; //para saber o tamanho da representacao[incluindo bit marcador]
      while((tabelaRepresentacoes[i]>>counter)!=0){  counter++;  }
	  insertNodeInTree(raiz,i,counter-1); 
	 }
	} 
 }
 
 /*!Método recursivo auxiliar de construção da árvore a partir da tabela de representações
  *@param tree No da árvore correspondente à chamada
  *@param i Posicao, na tabela de representações, da representação a ser inserida na arvore.
  *@param altura Altura, dentro da árvore, relativa ao no da chamada.
 */ 
 private void insertNodeInTree(unpackAuxTree tree,int i,int altura){
  if(tree == null) return;
  if( altura > 0 ){
        if(  ((tabelaRepresentacoes[i]>>(altura-1))&1) == 0 ){   //     forma.substring( 0, 1 ).equals("0") ){
            if( tree.getEsq() == null ){
                tree.setEsq( new unpackAuxTree( (altura == 1?i:-1) , null, null, (altura == 1?true:false)  ) );
                insertNodeInTree( tree.getEsq(), i, altura-1 );
            }
            else{  insertNodeInTree( tree.getEsq(), i, altura-1 );   }
        }
        else {
            if( tree.getDir() == null ){
                tree.setDir( new unpackAuxTree( (altura == 1?i:-1) , null, null, (altura == 1?true:false) ) );
                insertNodeInTree( tree.getDir(), i, altura-1 );
            }
            else{   insertNodeInTree( tree.getDir(), i, altura-1 );   }
        }
   }
   
 }
 
 /*!Informa se i-esimo bit do byte e 1(true) ou 0(false),
  *considerando que os bits estao em ordem de 0 ao 7.
  *@param toAnalisy Byte a ser analisado
  *@param iBit Indicacao de qual bit, dentre os 8 possiveis, deve ser verificado o valor
  *@throw Exception Exceção é lançada caso tente-se verificar um bit inexistente.
  *@return true caso o bit sea 1 ou false caso contrario
  **/
 private boolean getBit(byte toAnalisy, int iBit) throws Exception{
  if(iBit<0 || 7<iBit)
   throw new Exception();
  return ( ((toAnalisy>>(7-iBit))&1) == 1 ); 	
 }


  
}
//fim da classe
