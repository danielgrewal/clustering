/* 
 * SOFE3980 Homework Assignment-- input domain modeling -- clustering
 * Daniel Grewal, 100768376
 */

package com.sofe3980u;

import java.io.File;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.distance.EuclideanDistance;
import net.sf.javaml.tools.data.FileHandler;
import net.sf.javaml.clustering.Clusterer;
import net.sf.javaml.clustering.FarthestFirst;
import net.sf.javaml.clustering.KMeans;
import net.sf.javaml.clustering.KMedoids;
import net.sf.javaml.clustering.evaluation.ClusterEvaluation;
import net.sf.javaml.clustering.evaluation.SumOfSquaredErrors;

public class App {

    // method to create cluster given dataset and type of algorithm cluster
    public static Dataset[] cluster(Dataset data, Clusterer clusterer) {
        return clusterer.cluster(data);
    }

    // method to measure the quality of clustering
    public static double score(Dataset[] clusters, ClusterEvaluation evaluator) {
        return evaluator.score(clusters);
    }

    public static void main(String[] args) throws Exception {
        // load the iris dataset
        Dataset data = FileHandler.loadDataset(new File("src/main/resources/iris.data"), 4, ",");

        // create 3 clusters of data using each algorithm
        Dataset[] kmeansClusters = cluster(data, new KMeans(3));
        Dataset[] kmedoidsClusters = cluster(data, new KMedoids(3, 150, new EuclideanDistance()));
        Dataset[] ffClusters = cluster(data, new FarthestFirst(3, new EuclideanDistance()));

        // measure the clustering quality using sum of squared errors in JavaML library
        ClusterEvaluation evaluator = new SumOfSquaredErrors();
        double kmeansSSEScore = score(kmeansClusters, evaluator);
        double kmedoidsSSEScore = score(kmedoidsClusters, evaluator);
        double ffSSEScore = score(ffClusters, evaluator);

        // print clusters for each algorithm
        for (int i = 0; i < kmeansClusters.length; i++) {
            System.out.println("K-Means Cluster: " + (i + 1));
            System.out.println(kmeansClusters[i] + "\n");
        }

        for (int i = 0; i < kmedoidsClusters.length; i++) {
            System.out.println("K-Medoids Cluster: " + (i + 1));
            System.out.println(kmedoidsClusters[i] + "\n");
        }

        for (int i = 0; i < ffClusters.length; i++) {
            System.out.println("Farthest First Cluster: " + (i + 1));
            System.out.println(ffClusters[i] + "\n");
        }

        // print results of each cluster algorithm
        System.out.println("Sum of squared errors (k-Means): " + kmeansSSEScore);
        System.out.println("Sum of squared errors (k-Medoids): " + kmedoidsSSEScore);
        System.out.println("Sum of squared errors (Farthest First): " + ffSSEScore + "\n");
    }
}
