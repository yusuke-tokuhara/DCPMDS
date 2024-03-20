import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ILPResult {
	
	public static int m = 0;

	public static ArrayList<String> ILP_get_result(){

		File solfile = new File(File_make.ILP_path + "\\sol_test1.sol");
		Map<String, Integer> result = new HashMap<>();

		result = ILPSolver.getResult(solfile);
		ArrayList<String> A = new ArrayList<String>();

		for(Map.Entry<String, Integer> entry : result.entrySet()){
			A.add(entry.getKey());
		}

		ArrayList<String> B = new ArrayList<String>();
		B = File_read.result_return(A);

		int i = 0;
		ArrayList<String> MDS = new ArrayList<String>();

		for(Map.Entry<String, Integer> entry : result.entrySet()){
			if(entry.getValue() == 1) {
				MDS.add(B.get(i));
			}
			i++;
		}
		return MDS;
	}

	public static ArrayList<String> ILP_get_result_forR(){

		File solfile = new File(File_make.ILP_path + "\\sol_test1.sol");
		Map<String, Integer> result = new HashMap<>();
		result = ILPSolver.getResult(solfile);
		ArrayList<String> A = new ArrayList<String>();

		for(Map.Entry<String, Integer> entry : result.entrySet()){
			A.add(entry.getKey());
		}

		ArrayList<String> B = new ArrayList<String>();
		B = File_read.result_return(A);

		int i = 0;
		ArrayList<String> MDS = new ArrayList<String>();
		ArrayList<String> RMDS = new ArrayList<String>();

		for(Map.Entry<String, Integer> entry : result.entrySet()){
			if(entry.getValue() == 0) {
				RMDS.add(B.get(i));
			}else {
				MDS.add(B.get(i));
			}
			i++;
		}
		return RMDS;
	}

	public static ArrayList<String> Nodes(){

		File solfile = new File(File_make.ILP_path + "\\sol_test1.sol");
		Map<String, Integer> result = new HashMap<>();
		result = ILPSolver.getResult(solfile);
		ArrayList<String> A = new ArrayList<String>();

		for(Map.Entry<String, Integer> entry : result.entrySet()){
			A.add(entry.getKey());
		}

		ArrayList<String> B = new ArrayList<String>();
		B = File_read.result_return(A);

		int i = 0;
		ArrayList<String> Node = new ArrayList<String>();

		for(Map.Entry<String, Integer> entry : result.entrySet()){

			Node.add(B.get(i));

			i++;
		}
		return Node;
	}

	public static ArrayList<String> MDS_x_index() {
		File solfile = new File(File_make.ILP_path + "\\sol_test1.sol");
		Map<String, Integer> result = new HashMap<>();
		result = ILPSolver.getResult(solfile);
		ArrayList<String> MDS_x_Index = new ArrayList<String>();

		for(Map.Entry<String, Integer> entry : result.entrySet()){
			if(entry.getValue() == 1) {
				MDS_x_Index.add(entry.getKey());

			}
		}
		return MDS_x_Index;
	}
	public static ArrayList<String> NonMDS_x_index() {
		File solfile = new File(File_make.ILP_path + "\\sol_test1.sol");
		Map<String, Integer> result = new HashMap<>();
		result = ILPSolver.getResult(solfile);
		ArrayList<String> NonMDS_x_Index = new ArrayList<String>();

		for(Map.Entry<String, Integer> entry : result.entrySet()){
			if(entry.getValue() == 0) {
				NonMDS_x_Index.add(entry.getKey());

			}
		}
		return NonMDS_x_Index;
	}


}
