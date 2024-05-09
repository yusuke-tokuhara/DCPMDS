import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class Preproposition2 {

	public static ArrayList<Integer> PreCritical = new ArrayList<Integer>();

	public static ArrayList<Integer> Pre1 = new ArrayList<Integer>();
	public static ArrayList<Integer> Pre2 = new ArrayList<Integer>();
	public static ArrayList<Integer> Pre3 = new ArrayList<Integer>();
	public static ArrayList<Integer> Pre4 = new ArrayList<Integer>();
	public static ArrayList<Integer> PreRedundant = new ArrayList<Integer>();

	public static HashSet<Integer> PreCritical_tmp = new HashSet<Integer>();
	public static HashSet<Integer> PreRedundant_tmp = new HashSet<Integer>();


	public static void proposition() {
		System.out.println("preprosessing");

		ArrayList<Integer> Nodes = new ArrayList<Integer>();
		Nodes = File_read.Node_int;

		ArrayList<Integer> copy = new ArrayList<Integer>();
		ArrayList<ArrayList<Integer>> adjacency_out_list = new ArrayList<ArrayList<Integer>>();
		ArrayList<ArrayList<Integer>> adjacency_in_list = new ArrayList<ArrayList<Integer>>();


		for(int i = 0; i < Algorithm.Adjacency_out_matrix.length; i++) {
			for(int j = 0; j < Algorithm.Adjacency_out_matrix.length; j++) {
				if(Algorithm.Adjacency_out_matrix[i][j] == 1)copy.add(j);
			}

			adjacency_out_list.add(i,(ArrayList<Integer>)copy.clone());
			copy.clear();
		}

		for(int i = 0; i < Algorithm.Adjacency_in_matrix.length; i++) {
			for(int j = 0; j < Algorithm.Adjacency_in_matrix.length; j++) {
				if(Algorithm.Adjacency_in_matrix[i][j] == 1)copy.add(j);
			}
			adjacency_in_list.add(i,(ArrayList<Integer>)copy.clone());
			copy.clear();
		}


		boolean repeat = true;
		int Pre_count = 0;

		int Pre_Cri_size = 0;
		int Pre_Red_size = 0;

		System.out.println("*".repeat(100));

		//proposition1
		//if a node vi is a node with in-degree 0, and then vi is a critical node.
//		int flag[] = new int[adjacency_out_list.size()];

		System.out.println("proposition1\n");
		for(int i = 0; i< adjacency_in_list.size(); i++) {
			if(adjacency_in_list.get(i).size() == 0) {
				PreCritical.add(Nodes.get(i));
				System.out.println(File_read.Index_2.get(Nodes.get(i))+" : is Critical by proposition1\n");
				Pre1.add(Nodes.get(i));
			}
		}

		while(repeat) {
			Pre_count++;

			Pre_Cri_size = PreCritical_tmp.size();
			Pre_Red_size = PreRedundant_tmp.size();

			System.out.println();
			System.out.println("start_Ctmp_size : "+Pre_Cri_size);
			System.out.println("start_Rtmp_size : "+Pre_Red_size);
			System.out.println();




			BigDecimal theta = BigDecimal.valueOf(ILPMatrix.theta);
			BigDecimal one = BigDecimal.valueOf(1);
			BigDecimal FailureProbability;
			BigDecimal prod = BigDecimal.valueOf(1.0);


			//proposition3
			//if a node vi has at least two directed edges to nodes each of which has out-degree 0 and in-degree 1, and
			//1-ρ>Θ hold, then vi is a critical node.

			System.out.println("proposition3\n");
			int count = 0;
			for(int i = 0; i < adjacency_out_list.size(); i++) {
				count = 0;
				if(adjacency_out_list.get(i).size() >= 2) {
					for(int j = 0; j < adjacency_out_list.get(i).size(); j++) {
						if(adjacency_out_list.get(adjacency_out_list.get(i).get(j)).size() == 0 && adjacency_in_list.get(adjacency_out_list.get(i).get(j)).size() == 1) {
							FailureProbability = BigDecimal.valueOf(Algorithm.NonLogFailureProbability_matrix[i][adjacency_out_list.get(i).get(j)]);
							if(one.subtract(FailureProbability).compareTo(theta) == 1) {
								count++;
								System.out.println("FailureProbability "+File_read.Index_2.get(i)+" to "+File_read.Index_2.get(adjacency_out_list.get(i).get(j))+" :"+FailureProbability);
								System.out.println(FailureProbability+"<"+theta+" "+count);
							}
						}
					}
					if(count >= 2 && !PreCritical.contains(Nodes.get(i)) && !PreRedundant.contains(Nodes.get(i))) {
						PreCritical.add(Nodes.get(i));
						System.out.println(File_read.Index_2.get(Nodes.get(i))+" : is Critical by proposition3");
						Pre3.add(Nodes.get(i));
					}
				}
			}
			System.out.println();


			//proposition4
			//if vi has incoming link from neighbour nodes vj and 1-ρ<Θ holds, vi node is a critical node.)

			System.out.println("proposition4\n");
			for(int i = 0; i < adjacency_in_list.size(); i++) {
				if(adjacency_in_list.get(i).size() != 0) {
					for(int j = 0; j < adjacency_in_list.get(i).size(); j++) {
						FailureProbability = BigDecimal.valueOf(Algorithm.NonLogFailureProbability_matrix[adjacency_in_list.get(i).get(j)][Nodes.get(i)]);
						prod = prod.multiply(FailureProbability);
						System.out.println("FailureProbability "+File_read.Index_2.get(adjacency_in_list.get(i).get(j))+" to "+File_read.Index_2.get(Nodes.get(i))+" :"+FailureProbability);
						System.out.println("prod"+prod);
					}
					if(one.subtract(prod).compareTo(theta) == -1 && !PreCritical.contains(Nodes.get(i)) &&!PreRedundant.contains(Nodes.get(i))) {
						PreCritical.add(Nodes.get(i));

						System.out.println(prod+">"+theta);
						System.out.println(File_read.Index_2.get(Nodes.get(i))+" : is Critical by proposition4\n");
						Pre4.add(Nodes.get(i));
					} else {
						System.out.println();
					}
					prod = BigDecimal.valueOf(1.0);
					one = BigDecimal.valueOf(1.0);
				}
			}


			deleteCriticalLinksToNode(PreCritical,adjacency_in_list,adjacency_out_list);


			//proposition2
			//if a node vi is a node with an out-degree 0, and has an incoming link from a critical node, and also 1-ρ>Θ
			//holds, then vi is a redundant node.

			System.out.println("proposition2");


			for(int i = 0; i < adjacency_out_list.size(); i++) {
				prod = BigDecimal.valueOf(1.0);
				if(adjacency_out_list.get(i).size() == 0) {
					for(int j = 0; j < adjacency_in_list.get(i).size(); j++) {
						for(int t = 0; t < PreCritical.size(); t++) {
							if(adjacency_in_list.get(i).get(j).equals(PreCritical.get(t))) {
								FailureProbability = BigDecimal.valueOf(Algorithm.NonLogFailureProbability_matrix[PreCritical.get(t)][i]);
								prod = prod.multiply(FailureProbability);
								if(one.subtract(FailureProbability).compareTo(theta) == 1) {
									if(!PreCritical.contains(Nodes.get(i)) && !PreRedundant.contains(Nodes.get(i))) {
										PreRedundant.add(Nodes.get(i));
										prod = BigDecimal.valueOf(1.0);
										System.out.println(File_read.Index_2.get(Nodes.get(i))+" : is Redundant by propsition2");
										Pre2.add(Nodes.get(i));
									}

								}
							}
						}


					}
				}

			}
			System.out.println();

			deleteRedundantLinksToNode(PreRedundant,adjacency_in_list,adjacency_out_list);


			for(int i = 0; i < PreCritical.size(); i++) {
				if(!PreCritical_tmp.contains(PreCritical.get(i))) {
					PreCritical_tmp.add(PreCritical.get(i));
				}

			}

			for(int i = 0; i < PreRedundant.size(); i++) {
				if(!PreRedundant_tmp.contains(PreRedundant.get(i))) {
					PreRedundant_tmp.add(PreRedundant.get(i));
				}

			}


			System.out.println("final_Ctmp_size : "+PreCritical_tmp.size());
			System.out.println("final_Rtmp_size ; "+PreRedundant_tmp.size());

			if(Pre_Cri_size == PreCritical_tmp.size() && Pre_Red_size == PreRedundant_tmp.size()) {
				repeat = false;
			}

			System.out.println("*".repeat(100));

		}
		System.out.println("Pre_count : "+Pre_count);

		System.out.println("--PreCritical_node--");
		for(int i:PreCritical) {
			System.out.print(i);
			System.out.print(" ");
		}

		System.out.println();

		System.out.println("--PreRedundant_node--");
		for(int i:PreRedundant) {
			System.out.print(i);
			System.out.print(" ");
		}

		System.out.println("\nPre1:"+Pre1.size());
		System.out.println("Pre2:"+Pre2.size());
		System.out.println("Pre3:"+Pre3.size());
		System.out.println("Pre4:"+Pre4.size());


		System.out.println();

		ArrayList<Integer> PreCritical_list = new ArrayList<Integer>();
		ArrayList<Integer> PreRedundant_list = new ArrayList<Integer>();

		for(int i:PreCritical_tmp) {
			PreCritical_list.add(i);
		}

		for(int i:PreRedundant_tmp) {
			PreRedundant_list.add(i);
		}

		PreCritical = PreCritical_list;
		PreRedundant = PreRedundant_list;

		System.out.println("PreCritical_size : "+PreCritical.size());
		System.out.println("PreRedundant_size : "+PreRedundant.size());

	}

	public static void deleteCriticalLinksToNode(ArrayList<Integer> nodes, ArrayList<ArrayList<Integer>> adjacency_in_matrix, ArrayList<ArrayList<Integer>> adjacency_out_matrix) {
		//inlinks
		for(int i:nodes) {
			adjacency_in_matrix.get(i).clear();
			for(int j = 0; j < adjacency_out_matrix.size(); j++) {
				if(adjacency_out_matrix.get(j).contains(i)) {
					adjacency_out_matrix.get(j).remove(adjacency_out_matrix.get(j).indexOf(i));
				}
			}
		}

	}

	public static void deleteRedundantLinksToNode(ArrayList<Integer> nodes, ArrayList<ArrayList<Integer>> adjacency_in_matrix,ArrayList<ArrayList<Integer>> adjacency_out_matrix) {
		//inlinks
		for(int i:nodes) {
			adjacency_in_matrix.get(i).clear();
			for(int j = 0; j < adjacency_out_matrix.size(); j++) {
				if(adjacency_out_matrix.get(j).contains(i)) {
					adjacency_out_matrix.get(j).remove(adjacency_out_matrix.get(j).indexOf(i));
				}
			}
		}
	}


	public static void deleteOutLinksToNode(ArrayList<Integer> nodes, ArrayList<ArrayList<Integer>> Adjacency_matrix) {
		int size = Adjacency_matrix.size();

		for(int i = 0; i < nodes.size(); i++) {
			for(int j = 0; j < Adjacency_matrix.size(); j++) {
				Iterator<Integer> it = Adjacency_matrix.get(j).iterator();
				while(it.hasNext()) {
					int i_node = it.next();
					if(PreCritical.contains(i_node)) {
						it.remove();
					}
				}

			}
		}

	}

	public static void deleteInLinksToNode(ArrayList<Integer> nodes, ArrayList<ArrayList<Integer>> Adjacency_matrix) {
		int size = Adjacency_matrix.size();

		for(int i = 0; i < nodes.size(); i++) {
			for(int j = 0; j < size; j++) {
				Adjacency_matrix.get(j).remove(nodes.get(i));
			}
		}
	}

}
