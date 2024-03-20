
public class Clear {
	public static void clear() {
		File_read.Index_1.clear();
		File_read.Index_2.clear();
		File_read.Index_3.clear();
		File_read.Index_4.clear();
		File_read.Node_int.clear();
		File_write.counter = 0;
		ILPMatrix.Prep_Critical_Xindex.clear();
		ILPMatrix.Prep_Redundant_Xindex.clear();
		ILPResult.m = 0;
		ILPSolver.solfile_not_found = false;
		Preproposition.PreCritical.clear();
		Preproposition.PreRedundant.clear();
		Preproposition2.PreCritical_tmp.clear();
		Preproposition2.PreRedundant_tmp.clear();
		//File_delete.file_delete();
		
	}

}
