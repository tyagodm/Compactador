package huffman;

/*!
 ===========================================================================
 SOURCE FILE : iteratorAuxTree.java
 ===========================================================================
 DESCRIPTION.: 
 AUTHOR......: Hundson Thiago & Tyago Medeiros.
 LOCATION....: DIMAp/UFRN.
 SATARTED ON.: May/2006
 CHANGES.....: 

 TO COMPILE..: javac iteratorAuxTree.java
 OBS.........: 
 
 LAST UPDATE.: 
 ---------------------------------------------------------------------------
*/

//!@brief Classe que representa o iterador responsável por percorrer a arvore auxiliar de descompactação.
public class IteratorAuxTree{
  //Atributos
  /*!Referência ao um nó da árvore. */
 private unpackAuxTree ptr;
 
 //-----------------
 /*!Construtor de um iterador para a unpackAuxTree com sua referência passada por parâmetro.
  *@param node Nó a ser referênciado.
 **/
 IteratorAuxTree(unpackAuxTree node){
 	ptr=node;
 }
 //-----------------
 /*!Método para seta um nó passado por parâmetro.
  *@param node Nó a ser setado.
  *@throw Exception Lançada quando o nó passado for nulo.
 **/
 public void setItr(unpackAuxTree node) throws Exception{
 	if(node==null) 
 	  throw new Exception();
 	ptr = node;
 }
 
 /*!Método para seta um nó a direita ou a esquerda do nó atual.
  *@param direcao Caso o booleano for true irá para direita caso contrário vai para a direita.
  *@throw Exception Lançada quando o nó referênciado for nulo.
 **/
 public void go2Bit(boolean direcao) throws Exception{
 
 /* considerando:
  * true  = 1 = direita 
  * false = 0 = esquerda
  */

 if(ptr==null){
 		throw new Exception();
 	}
 	if(direcao) //direita
 	{
 	 if(ptr.getDir()!=null){
 	 	ptr=ptr.getDir();
 	 }
 	 else throw new Exception();
 	}
 	else{ //esquerda
 	 if(ptr.getEsq()!=null){
 	 	ptr=ptr.getEsq();
 	 }
 	 else throw new Exception();
 	}
 }

 /*!Método para retorna o Byte contido no elemento do nó referênciado pelo iterador.
  *@throw Exception Lançada quando o nó referênciado for nulo.
  *@return Byte com a representação do elemento no nó.
 **/
 public byte getByte() throws Exception{
  if(ptr==null) 
    throw new Exception();
  return (byte)ptr.getByteElement();
 }
 
 /*!Método para identificar se nó é folha.
  *@throw Exception Lançada quando o nó referênciado for nulo.
  *@return Boolean Caso true é folha caso contrário não é folha.
 **/
 public boolean itrNodeIsLeaf() throws Exception{
 	if(ptr==null){
 	 	throw new Exception();
 	}
 	return ptr.isLeaf();
 }
		
}
