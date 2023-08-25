import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class ILPSolver {

	public static boolean solfile_not_found = false;

	public static void solve(File cmdfile) {

		String command = "cplex -f \""+cmdfile.getAbsolutePath()+"\"";
		List<String> commandList = Arrays.stream(command.split(" ")).collect(Collectors.toList());

		try {
			ProcessBuilder pb = new ProcessBuilder(commandList);
			Process p  = pb.start();
			while (p.getInputStream().read() >= 0);
		} catch (IOException e) {
			System.out.println(e);

		}
	}

	public static void createCommandFile(File lpfile,File solfile) {
		try {
			File cmdfile = new File(File_make.ILP_path + "\\cmd_test1.cmd");
			if (cmdfile.createNewFile()){

		    }else{
		    //System.out.println("**");
		    }

			FileWriter pw = new FileWriter(cmdfile);
			pw.write("read \""+lpfile.getAbsolutePath()+"\"\r\n");
			pw.write("optimize\r\n");
			pw.write("write \""+solfile.getAbsolutePath()+"\"\r\n");
			pw.write("y"+"\"\r\n");
			pw.close();

			solve(cmdfile);


		} catch (IOException e) {
			System.out.println(e);
		}
	}

	public static Map<String, Integer> getResult(File solfile) {
		Map<String, Double> result = new HashMap<>();
		Map<String, Integer> result2 = new HashMap<>();

		try{
			Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(solfile);
			Element rootElement = document.getDocumentElement();

			NodeList variables = rootElement.getElementsByTagName("variable");
			for(int i = 0; i < variables.getLength(); i++){
				String key = variables.item(i).getAttributes().getNamedItem("name") .getNodeValue();
				String val = variables.item(i).getAttributes().getNamedItem("value").getNodeValue();
				//変更
				//resultに(x,結果)を格納する
				result.put(key, Double.parseDouble(val));
			}

			//たまに結果に1,0以外の数値があるので二値化する
			for(Entry<String,Double> entry : result.entrySet()) {
				if(entry.getValue()>=0.5) {
					result2.put(entry.getKey(), 1);
				}else {
					result2.put(entry.getKey(), 0);
				}


			}
		} catch (Exception e) {
			solfile_not_found = true;
		}
		return result2;
	}

}
