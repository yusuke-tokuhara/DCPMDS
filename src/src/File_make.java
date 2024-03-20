import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class File_make {
	public static String ILP_path = "";

		public static void FileMake() {
			try{
			      File lpfile = new File(ILP_path + "\\dom_test1.lp");
			      File solfile = new File(ILP_path + "\\sol_test1.sol");
			      System.out.println("--File_make--");
			      System.out.println(solfile);
			      System.out.println(lpfile);
			      System.out.println("-----");

				      if (lpfile.createNewFile()){

				      }else{

				      }
				      if (solfile.createNewFile()){
				    	  PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(solfile)));
				    	  //pw.println("<?xml version = \"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
				    	  pw.close();

					  }else{

					  }
			   }catch(IOException e){
				      System.out.println(e);
			   }
		}

}
