import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class File_write {
	
	public static int counter = 0;
	
	public static void filewrite(ArrayList<String> C) {
		try {
			File lpfile = new File(File_make.ILP_path + "\\dom_test1.lp");
			File solfile = new File(File_make.ILP_path + "\\sol_test1.sol");
			solfile.createNewFile();
			
			
			if(checkBeforeWritefile(lpfile)) {
				PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(lpfile)));
				String str = "";
				pw.println("\\Problem name: dom_test1.lp");
				pw.println("Minimize");
				for(int i = 0; i < C.size()-1; i++) {
					str += "  x" + (i+1) + " " + "+ ";
				}
				str += "  x" + (C.size());
				pw.println(str);
				pw.println("Subject To");
				for(int i = 0; i < C.size(); i++) {
					pw.println(C.get(i) + "  >= " + ILPMatrix.parameter);
				}
				
				pw.println("Binaries");
				for(int i = 0; i <= C.size()-1; i++) {
					pw.println("  x" + (i+1));
				}
				
				pw.println("END");
				pw.close();
				
				ILPSolver.createCommandFile(lpfile, solfile);
			}else {
				System.out.println("***");
			}
			
		}catch(IOException e) {
			System.out.println(e);
		}
	}
	
	public static void filewrite_CMDS(ArrayList<String> C) {
		try {
			File lpfile = new File(File_make.ILP_path + "\\dom_test1.lp");
			File solfile = new File(File_make.ILP_path + "\\sol_test1.sol");
			//solfile.createNewFile();
			
			if(checkBeforeWritefile(lpfile)) {
				PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(lpfile)));
				String str = "";
				pw.println("\\Problem name: dom_test1.lp");
				pw.println("Minimize");
				for(int i = 0; i < Algorithm.Adjacency_out_matrix.length-1; i++) {
					str += "  x" + (i+1) + " " + "+ ";
				}
				str += "  x" + (Algorithm.Adjacency_out_matrix.length);
				pw.println(str);
				pw.println("Subject To");
				for(int i = 0; i < C.size()-1; i++) {
					pw.println(C.get(i) + "  >= "+ILPMatrix.parameter);
				}
				pw.println(C.get(C.size()-1));
				
				pw.println("Binaries");
				for(int i = 0; i <= Algorithm.Adjacency_out_matrix.length-1; i++) {
					pw.println("  x" + (i+1));
				}
				
				pw.println("END");
				pw.close();
				
				ILPSolver.createCommandFile(lpfile, solfile);
			}else {
				System.out.println("****");
			}
			
		}catch(IOException e) {
			System.out.println(e);
		}
	}
	
	
	private static boolean checkBeforeWritefile(File file) {
		if(file.exists()) {
			if(file.isFile() && file.canWrite()) {
				return true;
			}
		}
		return false;
	}

	
}
