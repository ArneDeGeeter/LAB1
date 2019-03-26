package sample;

import jdk.nashorn.internal.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class DataTransferUtils {

    public static void save(String fileName, List<double[]> points){
        FileWriter writer;

        try{
            writer = new FileWriter("graphs/" + fileName);

            int aantal = points.size();

            writer.append(aantal + "\n");

            for(double[] point : points){
                writer.append(point[0] + ",");
                writer.append(point[1] + ",");
            }
            writer.flush();
            writer.close();
        }
        catch(Exception e){e.printStackTrace();}
    }

    public static LinkedList<double[]> load(String fileName){
        Scanner sc;

        LinkedList<double[]> points = new LinkedList<>();
        double[] point;
        try{
            sc = new Scanner(new File(fileName));

            int aantal = sc.nextInt();
            sc.nextLine();
            sc.useDelimiter(",");

            for(int i=0; i< aantal; i++){
                point = new double[2];
                point[0] = Double.parseDouble(sc.next());
                point[1] = Double.parseDouble(sc.next());

                points.add(point);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return points;
    }
}
