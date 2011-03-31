
/*!
 ===========================================================================
 SOURCE FILE : press4all.java
 ===========================================================================
 DESCRIPTION.: 
 AUTHOR......: Hundson Thiago & Tyago Medeiros.
 LOCATION....: DIMAp/UFRN.
 SATARTED ON.: May/2006
 CHANGES.....: 

 TO COMPILE..: javac press4all.java
 OBS.........: 
 
 LAST UPDATE.: 
 ---------------------------------------------------------------------------
*/


import javax.swing.JFrame;
import java.io.File;

/*!@brief Implementacao da funcao main*/
public class press4all{
 /*!Funcao main*/
 public static void main(String args[]){
    pressInterface fr=new pressInterface();
	fr.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
 }
}
