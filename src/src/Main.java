import java.io.File;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ
		
		Scanner scanner = new Scanner(System.in);
		System.out.print("Input folder path >");
		String input = scanner.nextLine();
		
		System.out.print("Output folder path >");
		String output = scanner.nextLine();
		
		System.out.print("parameter(0 < Θ <1) >");
		String pa = scanner.nextLine();
		
		scanner.close();
		
		
		ILPMatrix.theta = Double.parseDouble(pa);
		ILPMatrix.parameter = (-1.0)*Math.log(1.0-ILPMatrix.theta);
		
		
		File_read.InputFilePath = input;
		File file = new File(File_read.InputFilePath);
		String[] filename = file.list();
		
		File fileILP = new File(output+"\\ILP");
		
		if(fileILP.exists()) {
			File_make.ILP_path = output+"\\ILP";
		}else {
			fileILP.mkdir();
			File_make.ILP_path = output+"\\ILP";
		}
		
		
		for(int i = 0; i < filename.length; i++) {
			System.out.println("--start analyzing--");
			Clear.clear();
			File_read.FileName = filename[i];
			System.out.println(File_read.FileName);
			long startTime = System.currentTimeMillis();
			
			File_make.FileMake();
			
			Algorithm.main_algorithm(output);
			
			
			
			long endTime = System.currentTimeMillis();
			
			System.out.println("Time : "+(endTime-startTime)+" ms");
			System.out.println();
		}

	}

}
