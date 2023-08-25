import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;

public class Algorithm {
	
	public static double[][] Adjacency_out_matrix;
	public static double[][] Adjacency_in_matrix;
	public static double[][] FailureProbability_matrix;
	public static double[][] NonLogFailureProbability_matrix;
	
	public static void main_algorithm(String output) {
		int M = 0;
		ArrayList<String> MDS = new ArrayList<String>();
		ArrayList<String> Nodes = new ArrayList<String>();
		
		int M_next = 0;
		ArrayList<String> MDS_x_index = new ArrayList<String>();
		ArrayList<String> MDS_next = new ArrayList<String>();
		ArrayList<String> DCMDS = new ArrayList<String>();
		ArrayList<String> DRMDS = new ArrayList<String>();
		ArrayList<String> DIMDS = new ArrayList<String>();
		
		Adjacency_out_matrix = ILPMatrix.adjacency_out_matrix_create();
		Adjacency_in_matrix = ILPMatrix.adjacency_in_matrix_create();
		FailureProbability_matrix = ILPMatrix.failureprobability_matrix_create();
		NonLogFailureProbability_matrix = ILPMatrix.nonlogfailureprobability_matrix_create();
		
		
		
		//Preproposition.proposition();
		Preproposition2.proposition();
		
		ILPMatrix.Ad_Matrix();
		
		MDS = ILPResult.ILP_get_result();
		M = MDS.size();
		MDS_x_index = ILPResult.MDS_x_index();
		
		ArrayList<String> Prep_DCPMDS = new ArrayList<String>();
		ArrayList<String> Prep_DRPMDS = new ArrayList<String>();
		
		ArrayList<String> MDS_Prep_DCPMDS = new ArrayList<String>();
		ArrayList<String> Node_Prep_DRPMDS = new ArrayList<String>();
		ArrayList<String> MDS_Prep_DCPMDS_String = new ArrayList<String>();
		ArrayList<String> Node_Prep_DRPMDS_String = new ArrayList<String>();
		ArrayList<String> MDS_Prep_DRPMDS_String = new ArrayList<String>();
		
		Prep_DCPMDS = File_read.PrepMDS_Xindex_return(File_read.PrepMDS_StringName_return(Preproposition2.PreCritical));
		Prep_DRPMDS = File_read.PrepMDS_Xindex_return(File_read.PrepMDS_StringName_return(Preproposition2.PreRedundant));
		
		System.out.println("----Prep_DCPMDS---");
		for(int i = 0; i < Prep_DCPMDS.size(); i++) {
			System.out.print(Prep_DCPMDS.get(i)+" ");
		}
		System.out.println();
		System.out.println("----------");
		
		System.out.println("----Prep_DRPMDS---");
		for(int i = 0; i < Prep_DRPMDS.size(); i++) {
			System.out.print(Prep_DRPMDS.get(i)+" ");
		}
		System.out.println();
		System.out.println("----------");
		
		
		//前処理criticalを求める
		for(String a: MDS_x_index) {
			if(Prep_DCPMDS.contains(a) != true) {
				MDS_Prep_DCPMDS.add(a);
			}
		}
		
		
		
		//前処理redundantを求める
		Nodes = File_read.PrepMDS_Xindex_return(File_read.PrepMDS_StringName_return(File_read.Node_int));
		
		
		
		for(String a: Nodes) {
			if(MDS_x_index.contains(a) != true && Prep_DRPMDS.contains(a) != true) {
				Node_Prep_DRPMDS.add(a);
			}
		}
		
		
		
		MDS_Prep_DCPMDS_String = File_read.result_return(MDS_Prep_DCPMDS);
		Node_Prep_DRPMDS_String = File_read.result_return(Node_Prep_DRPMDS);
		
		for(int i = 0; i < MDS_Prep_DCPMDS.size(); i++) {
			File_delete.file_delete();
			ILPMatrix.DCMD_Ad_Matrix(MDS_Prep_DCPMDS.get(i));
			MDS_next = ILPResult.ILP_get_result();
			M_next = MDS_next.size();
			
			if(ILPSolver.solfile_not_found == true) {
				DCMDS.add(MDS_Prep_DCPMDS_String.get(i));
				ILPSolver.solfile_not_found = false;
			}
			
			if(M_next > M) {
				DCMDS.add(MDS_Prep_DCPMDS_String.get(i));
			}
			
			MDS_next.clear();
		}
		
		
		//redundant_PMDSを求める
		for(int i = 0; i < Node_Prep_DRPMDS.size(); i++) {
			File_delete.file_delete();
			ILPMatrix.DRMD_Ad_Matrix(Node_Prep_DRPMDS.get(i));
			MDS_next = ILPResult.ILP_get_result();
			M_next = MDS_next.size();
			
			if(M_next > M) {
				DRMDS.add(Node_Prep_DRPMDS_String.get(i));
			}
			MDS_next.clear();
		}
		//終了
		
		//出力用に情報を整理
		DCMDS.addAll(File_read.PrepMDS_StringName_return(Preproposition2.PreCritical));
		HashSet<String> Critical = new HashSet<String>(DCMDS);
		DRMDS.addAll(File_read.PrepMDS_StringName_return(Preproposition2.PreRedundant));
		HashSet<String> Redundant = new HashSet<String>(DRMDS);
		
		
		
		//Intermittentを求める
		Nodes = File_read.PrepMDS_StringName_return(File_read.Node_int);
		for(int i = 0; i < Nodes.size(); i++) {
			if(DCMDS.contains(Nodes.get(i)) == false && DRMDS.contains(Nodes.get(i)) == false) {
				DIMDS.add(Nodes.get(i));
			}
		}
		
		File_delete.file_delete();
		
		//終了
		
		
		
		System.out.println("--Result--");
		
		System.out.println("|DCPMDS| = " + Critical.size());
		System.out.println("|DRPMDS| = " + Redundant.size());
		System.out.println("|DIPMDS| = " + DIMDS.size());
		
		System.out.println("--DIMDS--");
		for(int i = 0; i < DIMDS.size(); i++) {
			System.out.println(DIMDS.get(i));
		}
		
		System.out.println("--------");
		
		
		System.out.println("--MDS--");
		for(int i = 0; i < MDS.size(); i++) {
			System.out.println(MDS.get(i));
		}
		System.out.println("------");
		
		ArrayList<String> critical = new ArrayList<String>(Critical);
		ArrayList<String> redundant = new ArrayList<String>(Redundant);
		
		System.out.println("--redundant--");
		for(int i = 0; i < redundant.size(); i++) {
			System.out.println(redundant.get(i));
		}
		System.out.println("------");
		
		//結果テキストファイル作成
		File ResultFile = new File(output + "\\" + File_read.FileName + "_" + ILPMatrix.theta + "_result.txt");
		try {
			ResultFile.createNewFile();
			PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(ResultFile)));
			pw.println("\\Network name: "+File_read.FileName);
			pw.println("Node	DCPMDS	Category");
			
			for(int i = 0; i < critical.size(); i++) {
				//pw.println(DCMDS.get(i)+"	1	Critical");
				pw.println(critical.get(i)+"	1	Critical");
			}
			for(int i = 0; i < redundant.size(); i++) {
				//pw.println(DRMDS.get(i)+"	0	Redundant");
				pw.println(redundant.get(i)+"	0	Redundant");
			}
			for(int i = 0; i < DIMDS.size(); i++) {
				if(MDS.contains(DIMDS.get(i))) {
					pw.println(DIMDS.get(i)+"	1	Intermittent");
				}else {
					pw.println(DIMDS.get(i)+"	0	Intermittent");
				}
			}
			
			pw.close();
			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	

}
