package graph;

import graphcase.GraphGenerator;
import graphcase.GraphNet;

import java.awt.Point;


public class TestGraph {
	public static void main(String[] args)  {
		testGenerateGraph();
	}
	
	public static void testGenerateGraph() {
		int pointNum = 10;
		int edgeNum = 20;
		GraphNet treeGraph = GraphGenerator.minConGraph(pointNum);
		String treesString = treeGraph.toString();
		System.out.println("treeGraph" + treesString );
		System.out.println("----------------------------------------" );
		treeGraph.addSomeEdge(11);		
		System.out.println("Graph" + treeGraph.toString() );
		System.out.println("----------------------------------------" );
		System.out.println("Graph" + treeGraph.totxtString() );
	}
		

}
