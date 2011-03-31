
/*!
 ===========================================================================
 SOURCE FILE : nodeHuff.java
 ===========================================================================
 DESCRIPTION.: 
 AUTHOR......: Hundson Thiago & Tyago Medeiros.
 LOCATION....: DIMAp/UFRN.
 SATARTED ON.: May/2006
 CHANGES.....: 

 TO COMPILE..: javac nodeHuff.java
 OBS.........: 
 
 LAST UPDATE.: 
 ---------------------------------------------------------------------------
*/


/*!@brief Classe de implementacao da arvore de huffman.*/
public class nodeHuff{
 
 //atributos 
 /*!Inteiro para armazenar o byte que dada folha venha a representar*/
 private int byte_element; 
 /*!Armazena a frequencia do no*/
 private int freq; 
 /*!Inteiro que serve para representar um no atraves dos bits contidos neste, 
  *tendo a primeira aparicao do bit 1 como inicio da representacao*/ 
 private int bitName;
 /*!Sub-arvore Esquerda*/
 private nodeHuff esq;
 /*!Sub-arvore Direita*/
 private nodeHuff dir;
 
 /*!Construtor da classe nodeHuff 
  @param element Byte(elemento) do no
  @param f Frequencia do elemento
  @param e Filho esquerdo
  @param d Filho direito
 */
 nodeHuff(int element, int f, nodeHuff e, nodeHuff d){
  byte_element=element;
  freq=f;
  bitName=0;
  esq=e;
  dir=d;
 }
 
 //metodos
 /*!Retorna o elemento contido no nodeHuff
  *@return elemento contido no nodeHuff
  */
 public int getByteElement(){ return byte_element; }
 
 /*!Retorna a frequencia do nodeHuff
  *@return frequencia do nodeHuff
  */
 public int getFreq(){ return freq; }
 
 /*!Retorna a representacao binaria do nodeHuff
  *@return representacao binaria do nodeHuff
  */
 public int getBitName(){  return bitName;  }
 
 /*!Retorna o filho esquerdo
  *@return filho esquerdo
  */
 public nodeHuff getEsq(){ return esq; }
 
 /*!Retorna o filho direito
  *@return filho direito
  */
 public nodeHuff getDir(){ return dir; }

 /*!Indica se o no e folha, ou seja, contem um elemento valido
  *@return true se e folha, false caso contrario
  */
 public boolean isLeaf(){
  if(esq==null && dir==null) 
   return true; 
  else 
   return false;
 }
 
 /*!Seta a representacao binaria do no.
  *@param nome nova representacao binaria do no.
  */
 public void setBitName(int nome){  bitName=nome; }

 /*!Retorna a altura do no na arvore
  *@return Altura do no.
  */
 public int getHeight(){
  if(isLeaf()){  return 1;  }
  else{
   int Hesq=0;
   int Hdir=0;
   if(esq!=null){
    Hesq=esq.getHeight();
   }
   if(dir!=null){
    Hdir=dir.getHeight();
   }
   if(Hesq>Hdir) return Hesq;
    else return Hdir;
  }
 }

 /*!Compara o no com outro dado, tendo como referencia a frequecia de cada um.
  @param o No a ser comparado com o atual
  @return Diferenca entre as frequencias
 */
 public int compareTo(nodeHuff o){
  return ( freq - o.freq);
 } 
 
 /*!Seta um no passado como o filho esquerdo.
  @param e No a ser setado como o no filho esquerdo
 */
 public void setEsq(nodeHuff e){ esq=e; }

 /*!Seta um no passado como o filho direito.
  @param d No a ser setado como o no filho direito.
 */
 public void setDir(nodeHuff d){ dir=d; }
 
 /*!Representacao da arvore, consistindo de um percurso pre-ordem.
  *@return String Representativa da arvore
  */
 public String toString(){
        String ts=new String("E:"+ String.valueOf( getByteElement() ) ) ;
	    if( ( getEsq() != null ) || ( getDir() != null ) ) ts+="(";
	    if(  getEsq() != null  ){	 ts+=getEsq().toString( );    }
	    if( ( getEsq() != null ) || ( getDir() != null ) )  ts+=",";
	    if( getDir() != null  ){	 ts+=getDir().toString( );    }
	    if( ( getEsq() != null ) || ( getDir() != null ) )  ts+=")";
        return ts;
 }
 
 
}
