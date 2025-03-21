package testBellmanFord;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import bellmanFord.BellmanFord;


public class TestBellmanFord {
	
	public static String GREEN = "\u001B[32m";
	public static String RED = "\u001B[31m";
	public static String RESET = "\u001B[0m";
	
	private static String testFilePath = "jeux_de_test.txt";
	//private static String testFilePath = "jeux_de_test_q4.txt";
	//private static String testFilePath = "jeux_de_test_q5.txt";
	
	
	// Crée un graphe à partir des données du fichier de jeux de test
	private static BellmanFord getGraph(BufferedReader reader, String line) throws IOException {
		// On récupère V et E
		String[] v_e = line.split(" ");
		int V = Integer.parseInt(v_e[0]);
		int E = Integer.parseInt(v_e[1]);
		
		BellmanFord graph = new BellmanFord(V, E);
		
		// On rÃ©cupÃ¨re les arÃªtes
		for (int i = 0; i < E; i++) {
			line = reader.readLine();
			
			String[] s_d_w = line.split(" ");
			int source = Integer.parseInt(s_d_w[0]);
			int destination = Integer.parseInt(s_d_w[1]);
			int weight = Integer.parseInt(s_d_w[2]);
			
			graph.addEdge(i, source, destination, weight);
		}
		
		return graph;
	}
	
	private static int[][] testGraph(BufferedReader reader, String line) throws IOException {
		BellmanFord graph;
		int[] resultatsAttendus;
		int[] resultatsObtenus;
		
		// On passe le commentaire "# TEST X"
		if (line.startsWith("#"))
			line = reader.readLine();
		
		// On rÃ©cupÃ¨re le graphe
		graph = getGraph(reader, line);
		
		// On rÃ©cupÃ¨re les rÃ©sultats attendus
		line = reader.readLine();
		
		if (line.equals("null"))
			resultatsAttendus = null;
		else {
			String[] results = line.split(" ");
			resultatsAttendus = Arrays.stream(results)
									  .mapToInt(Integer::parseInt)
									  .toArray();
		}
				
		resultatsObtenus = graph.BellmanFordAlgo(graph, 0);
		
		return new int[][] {resultatsAttendus, resultatsObtenus};
	}
	
	private static void testBellmanFord() {
		int nbTests = 0;
		int nbTestsPass = 0;
		
		try {
			// Ouverture du fichier contenant les jeux de test
			BufferedReader reader = new BufferedReader(new FileReader(testFilePath));
			
			int i = 1;
			String line;
			int[][] results;
			boolean resultB;
			
			// Boucle de tests
			while ((line = reader.readLine()) != null) {
				System.out.print("Test " + i + " : ");
				
				results = testGraph(reader, line);
				
				// On rÃ©cupÃ¨re si le test doit Ãªtre valide ou non
				line = reader.readLine();
				if (line.equals("true"))
					resultB = true;
				else
					resultB = false;
					
				// On vÃ©rifie si le rÃ©sultat obtenu est Ã©gal au rÃ©sulat attendu
				if (Arrays.equals(results[0], results[1]) == resultB) { 
					System.out.println(GREEN + "pass !" + RESET);
					nbTestsPass++;
				}
				else {
					System.out.println(RED + "fail..." + RESET);
					System.out.println("\tRésultats attendus : " + Arrays.toString(results[0]));
					System.out.println("\tRésultats obtenus : " + Arrays.toString(results[1]));
				}
				
				nbTests++;
				i++;
			}
	    } catch (IOException e) {
	    	System.out.println("Erreur : problème lors de la lecture du fichier de jeux de tests.");
			e.printStackTrace();
			return;
		}
		
		System.out.println("\nRsultat : " + nbTestsPass + "/" + nbTests + " tests passés (" + ((double) nbTestsPass / nbTests) * 100 + "%).");
	}
	
	
	public static void main(String[] args) {
		testBellmanFord();
	}
}
