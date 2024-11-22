import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.*;



public class Main {
    public static void main(String[] args) {
        int numVertices, numEdges;
        Vertex[] vertices;
        String filePath = "src/input.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            line = br.readLine();
            String[] firstLine = line.split("\\s+");
            numVertices = Integer.parseInt(firstLine[0]);
            numEdges = Integer.parseInt(firstLine[1]);
            vertices = new Vertex[numVertices];
            for(int i = 0; i < vertices.length; i++) {
                Vertex vertex = new Vertex();
                vertices[i] = vertex;
            }

            while ((line = br.readLine()) != null) {
                String[] edgeDetails = line.split("\\s+");
                int parent = Integer.parseInt(edgeDetails[0]);
                int destination = Integer.parseInt(edgeDetails[1]);
                int weight = Integer.parseInt(edgeDetails[2]);
                vertices[parent].neighbors.add(new int[]{destination, weight});
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

//        for(int i = 0; i < vertices.length; i++) {
//            System.out.println(i + " is the parent") ;
//            for(int j = 0; j < vertices[i].neighbors.size(); j++) {
//                System.out.println(Arrays.toString(vertices[i].neighbors.get(j)));
//            }
//        }

        PriorityQueue<Vertex> minHeap = new PriorityQueue<>(new Comparator<Vertex>() {
            @Override
            public int compare(Vertex v1, Vertex v2) {
                return Integer.compare(v1.distance, v2.distance);
            }
        });

        vertices[0].distance = 0;

        for (int i = 0; i < numVertices; i++) {
            minHeap.add(vertices[i]);
        }

        while (!minHeap.isEmpty()) {
            Vertex u = minHeap.poll();
            for (int i = 0; i < u.neighbors.size(); i++) {
                Vertex neighbor = vertices[u.neighbors.get(i)[0]];
                int edgeWeight = u.neighbors.get(i)[1];
                if (edgeWeight + u.distance < neighbor.distance) {
                    minHeap.remove(neighbor);
                    neighbor.distance = u.distance + edgeWeight;
                    minHeap.add(neighbor);
                }
            }
        }

        System.out.print("Vertex: ");
        for (int i = 0; i < numVertices; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
        System.out.print("Distance: ");
        for (int i = 0; i < numVertices; i++) {
            System.out.print(vertices[i].distance + " ");
        }

        String outputFilePath = "src/output.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
            writer.write("Vertex: ");
            for (int i = 0; i < numVertices; i++) {
                writer.write(i + " ");
            }
            writer.newLine();

            writer.write("Distance: ");
            for (int i = 0; i < numVertices; i++) {
                writer.write(vertices[i].distance + " ");
            }
            writer.newLine();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
