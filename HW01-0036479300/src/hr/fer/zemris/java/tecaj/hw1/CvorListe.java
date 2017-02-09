package hr.fer.zemris.java.tecaj.hw1;

/**
 * List data structure implementation with all basic operations
 * to manipulate data on It.
 *
 * @author Filip Saina
 * @version 1.0
 */

class ProgramListe {
	
  static class CvorListe {
    CvorListe sljedeci;
      String podatak;
  }

  /*
   * Entry point of the application. Arguments not tested.
   */
  
  public static void main(String[] args) {
    CvorListe cvor = null;
      cvor = ubaci(cvor, "Jasna");
      cvor = ubaci(cvor, "Ana");
      cvor = ubaci(cvor, "Ivana");
      System.out.println("Ispisujem listu uz originalni poredak:");
      ispisiListu(cvor);
      cvor = sortirajListu(cvor);
      System.out.println("Ispisujem listu nakon sortiranja:");
      ispisiListu(cvor);
      int vel = velicinaListe(cvor);
      System.out.println("Lista sadrzi elemenata: "+vel);
  }
 
  /*
   *  Method calculating the size of List.
   *  @returns - int value of the size
   */

  private static int velicinaListe(CvorListe cvor) {
	  int size = 0;
	  
	  if(cvor == null) return 0;
	  
	  do{
		  size++;
	  }while((cvor = cvor.sljedeci) != null);
	  
	  return size;
  }

  /*
   * Method for adding a new element to the List.
   * Arguments required are the first node and the value to
   * be added. 
   */

  private static CvorListe ubaci(CvorListe prvi, String podatak) {
	  CvorListe tmp = new CvorListe();
	  tmp.podatak = podatak;
	  tmp.sljedeci = prvi;
	  return tmp;
  }
  
  /*
   * Method for printing out the elements of the List.
   * All elements are printed to the std::out so no return
   * value is provided(void)
   */

  private static void ispisiListu(CvorListe cvor) {
      if(cvor == null) return;
    	  System.out.println(cvor.podatak);
      ispisiListu(cvor.sljedeci);
  }

  /*
   * Sorting algoritm for the List data object.
   * Bubble sort implemented with the return value
   * of the new sorted List.
   */

  private static CvorListe sortirajListu(CvorListe cvor) {
      if (velicinaListe(cvor) < 2) return cvor; //sortirano
      int sorted;
      CvorListe tmpRet = cvor;
      
      do{
    	  	sorted = 1;
    	  	
    	  	while(tmpRet.sljedeci != null){
    	  		//leksikografska uspordba
    	  		if(tmpRet.sljedeci.podatak.compareTo(tmpRet.podatak) < 0){
    	  			//zamijeni podatke
    	  			System.out.format("zamijenili smo %s i %s", tmpRet.sljedeci.podatak, tmpRet.podatak);
    	  			
    	  			String tmp = tmpRet.podatak;
    	  			tmpRet.podatak = tmpRet.sljedeci.podatak;
    	  			tmpRet.sljedeci.podatak = tmp;
    	  			sorted = 0;
    	  		}
    	  		tmpRet = tmpRet.sljedeci;
    	  	}
      }while(sorted == 0);
      return cvor;
  } 
  
  
}

















