import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;

public class File_read {

	public static HashMap<String,Integer> Index_1 = new HashMap<String,Integer>();
	public static HashMap<String,String> Index_3 = new HashMap<String,String>();
	public static HashMap<String,String> Index_4 = new HashMap<String,String>();
	public static HashMap<Integer,String> Index_2 = new HashMap<Integer,String>();

	public static ArrayList<Integer> Node_int = new ArrayList<Integer>();


	public static String FileName = "";
	public static String InputFilePath = "";

	public static ArrayList<double[]> file_reader() {
		ArrayList<double[]> lastdata = new ArrayList<double[]>();

		try {

			File file = new File(InputFilePath+"\\"+FileName);
            ArrayList<String> firstdata = new ArrayList<String>();
            ArrayList<String[]> splitdata = new ArrayList<String[]>();


            HashSet<String> hs1 = new LinkedHashSet<String>();


            if (!file.exists()) {
                System.out.print("Cannot open file");
            }


            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String data;
            
            while ((data = bufferedReader.readLine()) != null) {
            	firstdata.add(data);
            }
            

            for(int i = 0; i < firstdata.size(); i++) {
            	String[] pair = firstdata.get(i).split(" ");
            	splitdata.add(pair.clone());
            }
            
            for(int i = 0; i < splitdata.size(); i++) {
            	hs1.add(splitdata.get(i)[0]);
            	hs1.add(splitdata.get(i)[1]);
            }
            
            
            String[] x_array = new String[hs1.size()];
            for(int i = 0; i < x_array.length; i++){
            	x_array[i] = "x" + (i+1);
            }



            Iterator<String> iterator1 = hs1.iterator();
            Iterator<String> iterator2 = hs1.iterator();
            Iterator<String> iterator3 = hs1.iterator();
            Iterator<String> iterator4 = hs1.iterator();

            
            for(int i = 0; i < hs1.size(); i++) {
            	Index_1.put(iterator1.next(), i);
            	Index_2.put(i, iterator2.next());
            	Index_3.put(x_array[i], iterator3.next());
            	Index_4.put(iterator4.next(), x_array[i] );
            }

            
            double[] pair2=new double[3];
            for(int i = 0; i < splitdata.size(); i++) {
            	pair2[0]=Index_1.get(splitdata.get(i)[0]);
            	pair2[1]=Index_1.get(splitdata.get(i)[1]);
            	pair2[2]=Double.parseDouble(splitdata.get(i)[2]);

            	lastdata.add(pair2.clone());

            }

            fileReader.close();

            
            ArrayList<String> Node_String = new ArrayList<String>(hs1);
            Node_int = MDS_return(Node_String);

        } catch (IOException e) {
            e.printStackTrace();
        }
		return lastdata;
	}

	public static int file_size_count(){
		return Index_1.size();

	}


	
	public static ArrayList<String> result_return(ArrayList<String> A){
		ArrayList<String> B = new ArrayList<String>();
		for(int i = 0; i < A.size();i++){
			B.add(Index_3.get(A.get(i)));
		}
		return B;
	}

	
	public static ArrayList<Integer> MDS_return(ArrayList<String> MDS){
		ArrayList<Integer> mds = new ArrayList<Integer>();
		for(int i = 0; i < MDS.size();i++){
			mds.add(Index_1.get(MDS.get(i)));
		}
		return mds;
	}

	
		public static ArrayList<String> PrepMDS_StringName_return(ArrayList<Integer> PrepMDS_index){
			ArrayList<String> PrepMDS_StringName = new ArrayList<String>();
			for(int i = 0; i < PrepMDS_index.size();i++){
				PrepMDS_StringName.add(Index_2.get(PrepMDS_index.get(i)));
			}
			return PrepMDS_StringName;
		}

	
		public static ArrayList<String> PrepMDS_Xindex_return(ArrayList<String> PrepMDS_string){
			ArrayList<String> PrepMDS_Xindex = new ArrayList<String>();
			for(int i = 0; i < PrepMDS_string.size();i++){
				PrepMDS_Xindex.add(Index_4.get(PrepMDS_string.get(i)));
			}
			return PrepMDS_Xindex;
		}

}