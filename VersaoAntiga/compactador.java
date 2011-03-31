

/*!
 ===========================================================================
 SOURCE FILE : compactador.java
 ===========================================================================
 DESCRIPTION.: 
 AUTHOR......: Hundson Thiago & Tyago Medeiros.
 LOCATION....: DIMAp/UFRN.
 SATARTED ON.: May/2006
 CHANGES.....: 

 TO COMPILE..: javac compactador.java
 OBS.........: 
 
 LAST UPDATE.: 
 ---------------------------------------------------------------------------
*/

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.File;
import java.util.PriorityQueue;
import javax.swing.JTextArea;

/*!@brief Classe de implementacao de um compactador de arquivos*/
public class compactador{
 /*!Área de textos para exibição de possíveis mensagens*/
 private JTextArea statusTextArea;
 /*!Arquivo a ser compactado*/
 private File fileIn; 
 /*!Novo arquivo a ser gerado no processo de compactação*/
 private File fileOut; 
 /*!Fila de prioridade para auxiliar na construcao da arvore*/
 private PriorityQueue<nodeHuff> Heap; 
 /*!Tabela de representacoes dos bytes*/
 private int tabelaRepresentacoes[]; 
 /*!Indica a quantidade de bytes distintos que aparecem no arquivo original*/
 private int quant_bytes_usados;
 /*Referencia para a raiz da arvore a ser criada*/
 private nodeHuff raiz; 
 /*!Indica a posicao, dentre as representacoes, que possui a maior quantidade de bits*/
 private int posicaoMaiorRep; 
 
 /*!Construtor default que serve para criar um objeto compactador que
  *ficará na espera da especificação dos arquivos a serem compactados.
  */
 compactador( ){
  statusTextArea=null;
  fileIn=null;
  fileOut=null;
 }

 /*!Construtor que serve para criar um objeto compactador que ficará 
  *na espera da especificação dos arquivos a serem compactados.
  *@param TextArea JTextArea para exibição de mensagens
  */
 compactador(JTextArea TextArea){
  statusTextArea=TextArea;
  fileIn=null;
  fileOut=null;
 }

 /*!Construtor de um compactador que recebe imediatamente
  *um arquivo a ser compactado para um arquivo de mesmo nome 
  *mais a extensão ".press4All".
  *@param arquivoIn Arquivo a ser carregado para compactacao
  */
 compactador(File arquivoIn){
   statusTextArea=null;
   fileIn = arquivoIn;
   fileOut = new File( arquivoIn.getPath()+".press4all" );
 }
 
  /*!Construtor de um compactador que recebe imediatamente
  *um arquivo a ser compactado e o novo a ser gerado.
  *@param arquivoIn Arquivo a ser carregado para compactacao
  *@param arquivoOut Arquivo a ser gerado apos compactacao
  */
 compactador(File arquivoIn, File arquivoOut){
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

 /*!Compacta um arquivo para um arquivo de mesmo nome mais a extensão ".press4All".
  *@param arquivoIn Arquivo a ser carregado para compactacao
  */
 public void compactFile(File arquivoIn){
  fileIn = arquivoIn;
  fileOut = new File( arquivoIn.getPath()+".press4all" );
  compactFile();
 } 

 /*!Compacta um arquivo para um novo tambem passado.
  *@param arquivoIn Arquivo a ser carregado para compactacao
  *@param arquivoOut Arquivo a ser gerado apos compactacao
  */
 public void compactFile(File arquivoIn, File arquivoOut){
  fileIn = arquivoIn;
  fileOut = arquivoOut;
  compactFile( );
 } 
 
 /*!Compacta o arquivo que consta em fileIn para o arquivo 
  *que consta em fileOut, caso ambos sejam validos.
  */
 public void compactFile(){
  if( fileIn==null || fileOut==null ) {
   if(statusTextArea!=null){ 
    statusTextArea.append("\nCom arquivos invalidos 0%");
   }
   return;
  }
  
  if(statusTextArea!=null){
   statusTextArea.append("\nCarregando arquivo "+fileIn.getName()+"...");
   statusTextArea.repaint();
  }
  loadFiletoHeap( );
  if(statusTextArea!=null){
   statusTextArea.append("40%");
   statusTextArea.repaint();
  } 
  if(statusTextArea!=null){
   statusTextArea.append("\nConstruindo arvore...");
   statusTextArea.repaint();
  }
  buildTreeFromHeap();
  
  if(statusTextArea!=null){
   statusTextArea.append("50%");
   statusTextArea.repaint();
  }
  if(statusTextArea!=null){
   if(raiz.getHeight()>=32){
    statusTextArea.append("\n\nAviso: O arquivo compactado pode estar corrompido.\n");
	statusTextArea.repaint();
   }
  }
  
  
  if(statusTextArea!=null){
   statusTextArea.append("\nConstruindo tabela de representacoes...");
   statusTextArea.repaint(); 
  }
  buildTableFromTree( );
  
  if(statusTextArea!=null){
   statusTextArea.append("60%");
   statusTextArea.repaint();
  }
  if(statusTextArea!=null){
   statusTextArea.append("\nMontando o arquivo " +fileOut.getName()+" ...");
   statusTextArea.repaint();
  }
  buildNewFile( );
  
  if(statusTextArea!=null){
   statusTextArea.append("100%");  
   statusTextArea.repaint();
  }
  if(statusTextArea!=null){
   statusTextArea.append("\nO arquivo "+fileIn.getName()+" acabou de ser compactado para "+fileOut.getName()+"!!");
   statusTextArea.repaint(); 
  }
  
 }

 /*!Método que é parte do processo de compactação.
  *Lê o arquivo de entrada, contando a frequencia de cada byte, 
  *carregando na Heap afrequencias diferentes 0 para serem folhas da árvore.
  */
 private void loadFiletoHeap() {
  	int frequencia[] = new int[ 256 ];
	
	// Zerar o contador de frequencia.
	for ( int i=0; i < 256; i++ ){ frequencia[ i ] = 0; }

	// Input stream mais simples, acesso a bytes.
	FileInputStream entrada;

	// Arquivo que esta' sendo lido.
	try {    entrada = new FileInputStream( fileIn );    }
	catch( java.io.IOException e ){
	  if(statusTextArea!=null)
       statusTextArea.append("\n>> Erro na abertura do arquivo[" + fileIn.getName() + "]!");
	  return;
	}
    
   
    int k;
	try {
	    while ( ( k = entrada.read() ) != -1 ){   frequencia[k]++;   }
	}
	catch( java.io.IOException e ) { // O tratamento dessa exececao eh obrigatorio.
	  if(statusTextArea!=null)
       statusTextArea.append("\n>> Erro durante a leitura!");
	  return;
	}
	
	Heap=new PriorityQueue<nodeHuff>( 256, new nodeHuffComparator() );
    quant_bytes_usados=0;
	//criando e inserindo os nos na heap 
    for ( int i=0; i < 256; i++ ){
	  if(frequencia[i]!=0){
	   Heap.add( new nodeHuff(i,frequencia[i],null,null) );
	   quant_bytes_usados++;
      }
	}
	
	// IOException deve ser tratada.
	try {    entrada.close();   }
	catch( java.io.IOException e ) { // O tratamento dessa exececao eh obrigatorio.
	   if(statusTextArea!=null)
        statusTextArea.append("\n>> Erro durante a leitura!");
	   return ;
	}
 }
 
 /*!Realiza a montagem da árvore através de sucessivas remoções e inserções na árvore.*/
 private void buildTreeFromHeap(){
   if(Heap.isEmpty()) return;
   if(Heap.size()==1){
   	nodeHuff filho=Heap.poll();
   	raiz=new nodeHuff(-1,filho.getFreq(),null,filho); 
   }
   else{
    while(Heap.size()>1){
     nodeHuff tmp1=Heap.poll();
 	 nodeHuff tmp2=Heap.poll(); 
	 Heap.add( new nodeHuff(-1,( tmp1.getFreq()+tmp2.getFreq() ),tmp1,tmp2) );
    }
    raiz = Heap.poll();
   }
 }
 
 /*!Constroi a tabela de representações através da chamada à 
  *setBitNameTree(), destruindo a árvore logo após.
  */
 private void buildTableFromTree(){
  tabelaRepresentacoes = new int[256];
  posicaoMaiorRep=-1;
  setBitNameTree(raiz,1);  //0001
  raiz=null;
 }

 /*!Método recursivo para nomear a arvore e identificar 
  *folhas, adicionando as representações à tabela
  */
 private void setBitNameTree(nodeHuff n,int nome){ 
  if(n==null) return;
  n.setBitName(nome);
  if(n.getEsq() != null){  setBitNameTree( n.getEsq(), nome<<1 );  }   
  if(n.getDir() != null){  setBitNameTree( n.getDir(), (nome<<1)|1 );  }
  if(n.isLeaf()){
   	tabelaRepresentacoes[ n.getByteElement( ) ] = n.getBitName( );
	if(posicaoMaiorRep==-1) posicaoMaiorRep = n.getByteElement( );
 	else if(tabelaRepresentacoes[ posicaoMaiorRep ] < n.getBitName( ) ){
 	 posicaoMaiorRep = n.getByteElement();
	}
  }
 }

 /*!Constrói o arquivo compactado em conjunto com a tabela de representações.*/
 private void buildNewFile( ){
 

  FileInputStream entrada;
  FileOutputStream saida;
  
  try { entrada = new FileInputStream(fileIn); }
  catch( java.io.IOException e ) {
      if(statusTextArea!=null)
       statusTextArea.append("\n Erro no arquivo[" + fileIn.getName() + "]!");
	  return;
  }
  try {  saida = new FileOutputStream(fileOut); }
  catch( java.io.IOException e ) {
    if(statusTextArea!=null)
      statusTextArea.append("\n Erro no arquivo[" + fileOut.getName() + "]!");
	return;
  }
  
  
  try {
  	
  /* cabecalho1: especificacao da tabela */
  /*Primeiro byte: (chamemos o primeiro bit de bit1)[0000 0000](e o ultimo de bit8)
   *Os bits 8 e 7 indicam se cada linha vai usar um(00), dois(01), tres(10) ou quatro(11) bytes
   *O bit6 se vamos gravar toda a tabela(0) ou so alguns elementos especificados(1)
   *Do [bit5,bit1] a quantidade de linhas gravadas caso gravemos apenas linhas especificas
   **/  	
  	  	
   int tableAspect=0;  //a ser gravado 	
   int bits7e8; 
   if(tabelaRepresentacoes[posicaoMaiorRep] >= (1<<24) ) {
    bits7e8 = 3; //4-1
   }
   else if(tabelaRepresentacoes[posicaoMaiorRep] >= (1<<16) ) {
    bits7e8 = 2;//3-1
   }
   else if(tabelaRepresentacoes[posicaoMaiorRep] >= (1<<8) ) {
    bits7e8 = 1;//2-1
   }
   else{
    bits7e8 = 0;//1-1
   }
 
      
   if(quant_bytes_usados<32){  tableAspect = (quant_bytes_usados<<3)|4;  }
   tableAspect = (tableAspect|bits7e8);
   
   saida.write( tableAspect ); 
  
   
  
   for(int i=0;i<256;i++){ /*percorre e grava a tabela*/
	if( (tableAspect&4) == 4){ //se vai gravar somente linhas especificas
	  if( tabelaRepresentacoes[i]!=0 ){
	  	saida.write( i );
        if( bits7e8>=3 ){ 
         saida.write( (byte)(tabelaRepresentacoes[i]>>24) ); 
        }
        if( bits7e8>=2 ){ 
         saida.write( (byte)(tabelaRepresentacoes[i]>>16) ); 
        }
        if( bits7e8>=1 ){ 
         saida.write( (byte)(tabelaRepresentacoes[i]>>8) ); 
        }
        saida.write( (byte)(tabelaRepresentacoes[i]) ); 
      }   	
	}
	else {
        if( bits7e8>=3 ){ 
         saida.write( (byte)(tabelaRepresentacoes[i]>>24) ); 
        }
        if( bits7e8>=2 ){ 
         saida.write( (byte)(tabelaRepresentacoes[i]>>16) ); 
        }
        if( bits7e8>=1 ){ 
         saida.write( (byte)(tabelaRepresentacoes[i]>>8) ); 
        }
        saida.write( (byte)(tabelaRepresentacoes[i]) ); 
    }
	
   }
   //fim da gravacao do cabecalho

    /*inicio da gravacao do arquivo*/
	/*
	Enquanto o arquivo original nao acabou
		Leia o byte e jogue a representacao correspondente no buffer(buffer +=)
		
		Se (o buffer for maior que 8) entao
			varByte = pegar o byte 'a frente do buffer
			gravar 'varByte' no novo arquivo
		    desprezar do buffer o que ja foi gravado
	  ....		 
	*/
	int buffer=1;
    int k;
	while( ( k = entrada.read() ) != -1 ){
	  buffer = insertBackBuffer(buffer,tabelaRepresentacoes[k]);//inserindo a representacao no buffer sem o bit marcador
	  //enquanto o buffer tiver mais que 8 bits grava no arquivo
      while(buffer>=256){ 
	   
	   byte toBeRecorded;
	   //se buffer eh menor que 512 significa que tem menos 
	   //de 8 bits de representacao + bit indicador
	   if(buffer<512){  
	     toBeRecorded=(byte)buffer; 
		 buffer=1;
	   }
	   else{
	    /*como entrou no else significa que nao eh menor que 512, 
		 logo cAux ja pode delocar o buffer em uma casa evitando um 
		 laco de repeticao que sempre ira acontecer[logo:cAux=1]
		*/
		//System.out.println("\n3/BUFFER: "+Integer.toBinaryString(buffer) );
	    int cAux=1; 
		//enquanto nao tiver somente os primeiros 8 bits
		//00 101 0101 11
		while( !( (buffer>>cAux) < 512 ) ){ cAux++; } 
	    toBeRecorded = (byte)(buffer>>cAux);
	    //System.out.println("TO.BE.RECORDED: "+Integer.toBinaryString(toBeRecorded) );
		/*Atualizando o buffer*/
		int bitAlone=(1<<cAux);
		int bitArrayAux=1; //0...00011111111  (8 bits 1)
		while(bitArrayAux<bitAlone){ bitArrayAux=((bitArrayAux<<1)|1); }
		buffer=((buffer & bitArrayAux)|(bitAlone));
	    //System.out.println("4/BUFFER: "+Integer.toBinaryString(buffer) );
	   }
	   saida.write( toBeRecorded );
	   //001[0 1000 111]1 0110 0110 1001
	   /*como fazer para tirar os oito primeiros bits da frente?*/
	   /*Resposta: depende do caso, logo deveser modificado dentro da estrutura de decisao*/
       	  
	  }
	  
    }//fim do acesso ao arquivo
	
	//System.out.println("BUFFER1: "+Integer.toBinaryString(buffer) );
	int lixo=0;
    
    while( (buffer>1)&&(buffer<256) ){ 
     lixo++;
     buffer = buffer<<1;
    }
    //System.out.println("BUFFER2: "+Integer.toBinaryString(buffer) );
    //System.out.println("LIXO: "+lixo );
	if(buffer>1){
	  //System.out.println("BUFFER3: "+Integer.toBinaryString(buffer) );
	  saida.write( (byte)buffer );
	}  
	saida.write( (byte)lixo );
	
  }
  catch( java.io.IOException e ) { // O tratamento dessa exececao eh obrigatorio.
   if(statusTextArea!=null)
    statusTextArea.append("\n>> Erro durante a leitura!");
   return;
  }
	
  try {    entrada.close();   }
  catch( java.io.IOException e ) { // O tratamento dessa exececao eh obrigatorio.
	if(statusTextArea!=null)
     statusTextArea.append(">> Erro durante a leitura!");
	return ;
  }
  try {    saida.close();   }
  catch( java.io.IOException e ) { // O tratamento dessa exececao eh obrigatorio.
    if(statusTextArea!=null)
     statusTextArea.append(">> Erro durante a leitura!");
	return ;
  }
 
 }
 
 
 
 
 /*!Insere uma representação, sem bit marcador, em um buffer e retorna o valor correspondente.
  *@param buffer Buffer de bits
  *@param toInsert Representação à ser inserida no buffer
  *@return Valor do buffer após inserção
  */
 private int insertBackBuffer(int buffer,int toInsert){
  //0001 0010.1010  <-  0010.1111 == 0001 0010 1010 01111
  int counter=0; //para saber o tamanho de toInsert[incluindo bit marcador]
  while((toInsert>>counter)!=0){  counter++;  }
  /*bits <> counter 
   *0001 =1 / 0010 =2 / 0100 =3 / 1000 =4
   **/
  if(counter>1){
   buffer=( buffer<<(counter-1) ); //0001011111111 0000
   
   //bitArray resulta em uma sequencia de bits 1 de tamanho (counter-1)
   int bitArrayAux=1;   
   while(bitArrayAux< (1<<(counter-2)) ){ bitArrayAux=((bitArrayAux<<1)|1); }
      
   toInsert = (toInsert&bitArrayAux);
   return ( buffer|toInsert );
  }
  //else
  return buffer; 

 }
 

}
//fim da classe
