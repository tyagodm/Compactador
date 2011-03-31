package huffman;

import java.util.Comparator;


/*!@brief Implementacao de um Comparator para nodeHuff
 *Tem como unico objetivo comparar dois nodeHuff, para que se
 *possa usa-la interna a heap de construcao da arvore de representacoes de huffman
 */
public class nodeHuffComparator implements Comparator<nodeHuff>{
 /*!Serve para comparar dois nodeHuff
 *@param o1 Primeiro nodeHuff
 *@param o2 Segundo nodeHuff
 *@return Diferenca entre os nodeHuff, que e dada pelo metodo compareTo() 
 *        dos proprios nodeHuff passados como parametro.
 */
 public int compare(nodeHuff o1, nodeHuff o2){
  return o1.compareTo(o2);
 }
}
