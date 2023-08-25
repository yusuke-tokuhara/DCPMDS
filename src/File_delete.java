import java.io.File;

public class File_delete {

	public static void file_delete() {
		File solfile = new File(File_make.ILP_path + "\\sol_test1.sol");
		solfile.delete();
	}

}
