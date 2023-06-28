package bm.erciyes.edu.tr.project.Controller;

import bm.erciyes.edu.tr.project.Model.MovidleModel;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MovidleController {
    public static Map < String , MovidleModel> moviemap = new HashMap<>();

    public static ArrayList< String> titlelist = new ArrayList<>();

    public static ArrayList<MovidleModel> movielist = new ArrayList<>();

    public  static void reader() throws IOException {
        //csv file encoding
        BufferedReader reader = new BufferedReader(new InputStreamReader(new
                FileInputStream("src\\main\\java\\bm\\erciyes\\edu\\tr\\project\\imdb_top_250.csv"), StandardCharsets.ISO_8859_1));
        String line;
        while ((line = reader.readLine()) !=null) {
            String[] data = line.split(";");
            String no = data[0];
            String title = data[1];
            String year = data[2];
            String genre = data[3];
            String origin = data[4];
            String director = data[5];
            String star = data[6];
            String link = data[7];
            titlelist.add(data[1]);
            titlelist.remove("Title");

            MovidleModel mov=new MovidleModel(no,title,year,genre,origin,director,star,link);
            movielist.add(mov);
            moviemap.put(no,mov);
        }

    }

    public static MovidleModel randomMovieSelector() throws IOException {
        reader();
        int rNumber;
        Random rand= new Random();
        rNumber=2+rand.nextInt(250);
        System.out.println(moviemap.get(Integer.toString(rNumber)));
        return moviemap.get(Integer.toString(rNumber));
    }

    public static MovidleModel getMovie(String movieTitle) throws IOException {
       reader();
        for (Map.Entry<String, MovidleModel> entry : moviemap.entrySet()) {
            if (entry.getValue().title.equals(movieTitle)) {
                return  entry.getValue();
            }
        }
        return null;
    }


}

