import java.util.ArrayList;

public class ILPMatrix {
	public static double theta;
	
	public static double parameter;
	
	public static ArrayList<String> Prep_Critical_Xindex = new ArrayList<String>();
	
	public static ArrayList<String> Prep_Redundant_Xindex = new ArrayList<String>();
	
	public static double[][] adjacency_out_matrix_create(){
		ArrayList<double[]> input_data = new ArrayList<double[]>();
		
		input_data = File_read.file_reader();
		
		int matrix_size = File_read.file_size_count();
		
		
		double ad_matrix[][] = new double[matrix_size][matrix_size];
		
		ArrayList<double[]> ad_matrix_test = new ArrayList<double[]>();
		
		
		
		
		for(int i = 0; i < matrix_size; i++) {
			for(int j = 0; j < matrix_size; j++) {
				ad_matrix[i][j] = 0;
			}
		}
		
		
		for(int i = 0; i < input_data.size(); i++) {
			ad_matrix[(int)input_data.get(i)[0]][(int)input_data.get(i)[1]] = 1;
		}
		
		return ad_matrix;
	}
	
	public static double[][] adjacency_in_matrix_create(){
		ArrayList<double[]> input_data = new ArrayList<double[]>();
		
		input_data = File_read.file_reader();
		
		int matrix_size = File_read.file_size_count();
		
		double ad_matrix[][] = new double[matrix_size][matrix_size];
		
		for(int i = 0; i < matrix_size; i++) {
			for(int j = 0; j < matrix_size; j++) {
				ad_matrix[i][j] = 0;
			}
		}
		
		for(int i = 0; i < input_data.size(); i++) {
			ad_matrix[(int)input_data.get(i)[1]][(int)input_data.get(i)[0]] = 1;
		}
		
		return ad_matrix;
	}
	
	public static double[][] failureprobability_matrix_create(){
		ArrayList<double[]> input_data = new ArrayList<double[]>();
		
		input_data = File_read.file_reader();
		
		int matrix_size = File_read.file_size_count();
		
		double f_matrix[][] = new double[matrix_size][matrix_size];
		
		for(int i = 0; i < matrix_size; i++) {
			for(int j = 0; j < matrix_size; j++) {
				f_matrix[i][j] = 0;
			}
		}
		
		for(int i = 0; i < input_data.size(); i++) {
			if(input_data.get(i)[2] == 0) {
				f_matrix[(int)input_data.get(i)[0]][(int)input_data.get(i)[1]] = (-1.0)*Math.log(0.0000001);
			}else {
				f_matrix[(int)input_data.get(i)[0]][(int)input_data.get(i)[1]] = (-1.0)*Math.log(input_data.get(i)[2]);
			}
		}
		
		return f_matrix;
	}
	
	public static double[][] nonlogfailureprobability_matrix_create(){
		ArrayList<double[]> input_data = new ArrayList<double[]>();
		
		input_data = File_read.file_reader();
		
		int matrix_size = File_read.file_size_count();
		
		double nf_matrix[][] = new double[matrix_size][matrix_size];
		
		for(int i = 0; i < matrix_size; i++) {
			for(int j = 0; j < matrix_size; j++) {
				nf_matrix[i][j] = 0;
			}
		}
		
		for(int i = 0; i < input_data.size(); i++) {
			nf_matrix[(int)input_data.get(i)[0]][(int)input_data.get(i)[1]] = input_data.get(i)[2];
		}
		
		return nf_matrix;
	}
	
	public static void Ad_Matrix() {
		Prep_Critical_Xindex = File_read.PrepMDS_Xindex_return(File_read.PrepMDS_StringName_return(Preproposition2.PreCritical));
		Prep_Redundant_Xindex = File_read.PrepMDS_Xindex_return(File_read.PrepMDS_StringName_return(Preproposition2.PreRedundant));
		
		ArrayList<String> C = new ArrayList<String>();
		String[] contains = new String[Algorithm.Adjacency_out_matrix.length];
		
		for(int i = 0; i < Algorithm.Adjacency_out_matrix.length; i++) {
			contains[i] = parameter + " x" + (i+1);
			for(int j = 0; j < Algorithm.Adjacency_out_matrix.length; j++) {
				if(Algorithm.Adjacency_in_matrix[i][j] == 1) {
					contains[i] += " + " + Algorithm.FailureProbability_matrix[j][i] + " x" + (j+1);
				}
			}
		}
		
		for(int i = 0; i < contains.length; i++) {
			C.add(contains[i]);
		}
		
		File_write.filewrite(C);
	}
	
	public static void DCMD_Ad_Matrix(String m_v) {
		ArrayList<String> C = new ArrayList<String>();
		String[] contents = new String[Algorithm.Adjacency_out_matrix.length];
		
		for(int i = 0; i < Algorithm.Adjacency_out_matrix.length; i++) {
			contents[i] = parameter + " x" + (i+1);
			for(int j = 0; j < Algorithm.Adjacency_out_matrix.length; j++) {
				if(Algorithm.Adjacency_in_matrix[i][j] == 1) {
					contents[i] += " + " +Algorithm.FailureProbability_matrix[j][i]+ " x" + (j+1);
					
				}
			}
		}
		
		for(int i = 0; i < contents.length; i++) {
			C.add(contents[i]);
		}
		
		C.add(m_v+" <= 0");
		
		File_write.filewrite_CMDS(C);
	}
	
	public static void DRMD_Ad_Matrix(String m_v) {
		ArrayList<String> C = new ArrayList<String>();
		String[] contents = new String[Algorithm.Adjacency_out_matrix.length];
		for(int i = 0; i < Algorithm.Adjacency_out_matrix.length; i++) {
			contents[i] = parameter + " x" + (i+1);
			for(int j = 0; j < Algorithm.Adjacency_out_matrix.length; j++) {
				if(Algorithm.Adjacency_in_matrix[i][j] == 1) {
					contents[i] += " + " + Algorithm.FailureProbability_matrix[j][i] + " x" + (j+1);
					
				}
			}
		}
		
		for(int i = 0; i < contents.length; i++) {
			C.add(contents[i]);
		}
		
		C.add(m_v+" >= 1");
		
		
		File_write.filewrite_CMDS(C);
	}

}
