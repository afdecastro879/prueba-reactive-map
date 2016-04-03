import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Felipe De Castro on 4/3/2016.
 */
public class Main {
    public static void main(String[] args) {
        MongoClient mongoClient = new MongoClient("localhost", 3001);
        MongoDatabase admin = mongoClient.getDatabase("meteor");

        try {
            BufferedReader bf = new BufferedReader(new FileReader(new File("./data/mock.csv")));
            String linea;
            bf.readLine();
            //DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
            int cont = 0;
            while ((linea = bf.readLine())!=null){
                String[] datos = linea.split(",");
                double lat = Double.parseDouble(datos[0]);
                double lng = Double.parseDouble(datos[1]);
                if(cont%5==0){
                    admin.getCollection("perros_loc").insertOne(new Document()
                            .append("lat",lat)
                            .append("log", lng)
                            .append("createdAt", System.currentTimeMillis()));
                }
                Thread thread = Thread.currentThread();
                thread.sleep(1000);
                cont++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
