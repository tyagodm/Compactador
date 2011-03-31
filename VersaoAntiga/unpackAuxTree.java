

/*!@brief Classe auxiliar para implementacao de arvore.*/
public class unpackAuxTree{
 
 /* atributos */
 /*!Inteiro para armazenar o byte que dada folha venha a representar*/
 private int byte_element; 
 /*!Sub-arvore Esquerda*/ 
 private unpackAuxTree esq;
 /*!Sub-arvore Direita*/
 private unpackAuxTree dir;
 /*!Indica se o no e uma folha*/
 private boolean ehFolha;
 
/*!Construtor da classe unpackAuxTree
 * @param element Byte(elemento) do no
 * @param e Filho esquerdo
 * @param d Filho direito
 * @param ehElemento indica se o no sera uma folha
 */
 unpackAuxTree(int element, unpackAuxTree e, unpackAuxTree d, boolean ehElemento){
  byte_element=element;
  esq=e;
  dir=d;
  ehFolha=ehElemento;
 }
 
 /*!Retorna o elemento contido no nodeHuff
  * @return elemento contido no nodeHuff
  */
 public int getByteElement(){ return byte_element; }

 /*!Retorna o filho esquerdo
  * @return filho esquerdo
  */
 public unpackAuxTree getEsq(){ return esq; }

 /*!Retorna o filho direito
  * @return filho direito
  */
 public unpackAuxTree getDir(){ return dir; }

 /*!Indica se o no e folha, ou seja, contem um elemento valido
  * @return true se e folha, false caso contrario
  */
 public boolean isLeaf(){ return ehFolha; }

 /*!Seta um no passado como o filho esquerdo.
  * @param e No a ser setado como o no filho esquerdo
  */
 public void setEsq(unpackAuxTree e){ esq=e; }
 
 /*!Seta um no passado como o filho direito.
  * @param d No a ser setado como o no filho direito.
  */
 public void setDir(unpackAuxTree d){ dir=d; }
 
 /*!Representacao da arvore, consistindo de um percurso pre-ordem.
  * @return String Representativa da arvore
  */
 public String toString(){
        String ts=new String("E:"+ String.valueOf( getByteElement() ) );
	    if( ( getEsq() != null ) || ( getDir() != null ) ) ts+="(";
	    if(  getEsq() != null  ){	 ts+=getEsq().toString( );    }
	    if( ( getEsq() != null ) || ( getDir() != null ) )  ts+=",";
	    if( getDir() != null  ){	 ts+=getDir().toString( );    }
	    if( ( getEsq() != null ) || ( getDir() != null ) )  ts+=")";
        return ts;
 }
 
 
}
