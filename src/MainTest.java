import com.google.gson.Gson;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;


public class MainTest {

    public static void main(String[] args) throws IOException, ClassNotFoundException, IllegalAccessException {

        String repoURL = "http://agustiubu:7200/repositories/app-data-repo";

        File file = new File("src/test.json");
        String data = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
        Gson g = new Gson();
        App app = g.fromJson(data, App.class);
        Class<?> c = Class.forName("App");
        Field[] fieldList = c.getDeclaredFields();
        Method[] methodList = c.getMethods();
        String subj = "http://gessi.upc.edu/app/osmand";
        DBConnection test = new DBConnection(repoURL);
        //test.insertApp(app,"osmand");
        AppFinder appFinder = new AppFinder(repoURL);
        App result = appFinder.retrieveInfo("osmand");
        String json = g.toJson(result);
        System.out.println(json);
    }
}
