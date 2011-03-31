
/*imports*/
import java.io.FileInputStream;
import java.io.File;
import javax.swing.JTextArea;
import huffman.compactador;
import huffman.descompactador;

/*!@brief Classe abstraindo o uso das classes de compactacao e descompactacao. */
public class codecFacade
{
 protected codecFacade( ){ }

 public static void compactar(File fIn, File fOut, JTextArea aTextArea){
  (compactador.getInstancia()).setTextArea( aTextArea );
  (compactador.getInstancia()).compactFile( fIn, fOut );
 } 
 
 public static void descompactar(File fIn, File fOut, JTextArea aTextArea)
 {
  (descompactador.getInstancia()).setTextArea( aTextArea );
  (descompactador.getInstancia()).unpackFile( fIn, fOut );
 } 

 /*!Metodo para comparar dois arquivos passados como parametro, byte a byte. 
  *@param File1 arquivo a ser comparado.
  *@param File2 arquivo a ser comparado.
  *@return Boolean Caso true os aquivos são idênticos caso contrário são diferentes.
  **/
 public static boolean equals(File f1, File f2){
	int input1;
	int input2;

	FileInputStream fin1;
	FileInputStream fin2;
	
	try {    fin1 = new FileInputStream( f1 );    }
	catch( java.io.IOException e ) {
	    System.out.println(">> Erro na abertura do arquivo[FileReaderTest.java]!");
	    return false;
	}
	try {    fin2 = new FileInputStream( f2 );    }
	catch( java.io.IOException e ) {
	    System.out.println(">> Erro na abertura do arquivo[FileReaderTest.java]!");
	    return false;
	}

	int j=0,k=0;
	try {
	    while (  (j!=-1)&&(k!=-1) ) {  
		 k=fin1.read();
		 j=fin2.read();
		 if(k!=j){ 
		  fin1.close();
		  fin2.close();
		  return false; 
		 }
        }
	}
	catch( java.io.IOException e ) { // O tratamento dessa exececao eh obrigatorio.
	    System.out.println(">> Erro durante a leitura!");
	    return false;
	}
    
	// IOException deve ser tratada.
	try {    fin1.close();   }
	catch( java.io.IOException e ) { // O tratamento dessa exececao eh obrigatorio.
	    System.out.println(">> Erro durante a leitura!");
	    return false;
	}
	try {    fin2.close();   }
	catch( java.io.IOException e ) { // O tratamento dessa exececao eh obrigatorio.
	    System.out.println(">> Erro durante a leitura!");
	    return false;
	}
	if(k!=j){ return false; }
	else{ return true; }
 }


}
