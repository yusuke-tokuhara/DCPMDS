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
		int proposition_count = 0;

		//System.out.println("proposition1\n");
		for(int i = 0; i< adjacency_in_list.size(); i++) {
			if(adjacency_in_list.get(i).size() == 0) {
				PreCritical.add(Nodes.get(i));
				Pre1.add(Nodes.get(i));
				proposition_count++;
			}
		}

		while(repeat) {
			Pre_count++;

			Pre_Cri_size = PreCritical_tmp.size();
			Pre_Red_size = PreRedundant_tmp.size();





			BigDecimal theta = BigDecimal.valueOf(ILPMatrix.theta);
			BigDecimal one = BigDecimal.valueOf(1);
			BigDecimal FailureProbability;
			BigDecimal prod = BigDecimal.valueOf(1.0);


			//proposition3
			//if a node vi has at least two directed edges to nodes each of which has out-degree 0 and in-degree 1, and
			//1-ρ>Θ hold, then vi is a critical node.

			//System.out.println("proposition3\n");
			int count = 0;
			for(int i = 0; i < adjacency_out_list.size(); i++) {
				count = 0;
				if(adjacency_out_list.get(i).size() >= 2) {
					for(int j = 0; j < adjacency_out_list.get(i).size(); j++) {
						if(adjacency_out_list.get(adjacency_out_list.get(i).get(j)).size() == 0 && adjacency_in_list.get(adjacency_out_list.get(i).get(j)).size() == 1) {
							FailureProbability = BigDecimal.valueOf(Algorithm.NonLogFailureProbability_matrix[i][adjacency_out_list.get(i).get(j)]);
							if(one.subtract(FailureProbability).compareTo(theta) == 1) {
								count++;
							}
						}
					}
					if(count >= 2 && !PreCritical.contains(Nodes.get(i)) && !PreRedundant.contains(Nodes.get(i))) {
						PreCritical.add(Nodes.get(i));
						Pre3.add(Nodes.get(i));
						proposition_count++;
					}
				}
			}

			

			//proposition4
			//if vi has incoming link from neighbour nodes vj and 1-ρ<Θ holds, vi node is a critical node.)

			//System.out.println("proposition4\n");
			for(int i = 0; i < adjacency_in_list.size(); i++) {
				if(adjacency_in_list.get(i).size() != 0) {
					for(int j = 0; j < adjacency_in_list.get(i).size(); j++) {
						FailureProbability = BigDecimal.valueOf(Algorithm.NonLogFailureProbability_matrix[adjacency_in_list.get(i).get(j)][Nodes.get(i)]);
						prod = prod.multiply(FailureProbability);
					}
					if(one.subtract(prod).compareTo(theta) == -1 && !PreCritical.contains(Nodes.get(i)) &&!PreRedundant.contains(Nodes.get(i))) {
						PreCritical.add(Nodes.get(i));
						Pre4.add(Nodes.get(i));
						proposition_count++;
					} else {
						//System.out.println();
					}
					prod = BigDecimal.valueOf(1.0);
					one = BigDecimal.valueOf(1.0);
				}
			}


			deleteCriticalLinksToNode(PreCritical,adjacency_in_list,adjacency_out_list);


			//proposition2
			//if a node vi is a node with an out-degree 0, and has an incoming link from a critical node, and also 1-ρ>Θ
			//holds, then vi is a redundant node.

			//System.out.println("proposition2");
			prod = BigDecimal.valueOf(1.0);

			for(int i = 0; i < adjacency_out_list.size(); i++) {
				if(adjacency_out_list.get(i).size() == 0) {
					for(int j = 0; j < adjacency_in_list.get(i).size(); j++) {
						for(int t = 0; t < PreCritical.size(); t++) {
							if(adjacency_in_list.get(i).get(j) == PreCritical.get(t)) {
								FailureProbability = BigDecimal.valueOf(Algorithm.NonLogFailureProbability_matrix[PreCritical.get(t)][i]);
								if(one.subtract(FailureProbability).compareTo(theta) == 1) {
									if(!PreCritical.contains(Nodes.get(i)) && !PreRedundant.contains(Nodes.get(i))) {
										PreRedundant.add(Nodes.get(i));
										prod = BigDecimal.valueOf(1.0);
										Pre2.add(Nodes.get(i));
									}

								}
							}
						}


					}
				}

			}
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



			if(Pre_Cri_size == PreCritical_tmp.size() && Pre_Red_size == PreRedundant_tmp.size()) {
				repeat = false;
			}

			System.out.println("*".repeat(100));

		}

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
