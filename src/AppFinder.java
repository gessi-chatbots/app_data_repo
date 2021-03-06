import org.eclipse.rdf4j.model.Value;
import org.eclipse.rdf4j.query.BindingSet;
import org.eclipse.rdf4j.query.TupleQuery;
import org.eclipse.rdf4j.query.TupleQueryResult;
import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.repository.http.HTTPRepository;

import java.lang.reflect.Field;

public class AppFinder {

    private String rd4jEndpoint;
    private Repository repository = null;

    public AppFinder(String repoURL) {
        this.rd4jEndpoint = repoURL;
        repository = new HTTPRepository(this.rd4jEndpoint);
    }
    public App retrieveInfo(String appName) throws ClassNotFoundException, IllegalAccessException {
        RepositoryConnection repoConnection = repository.getConnection();
        String query = "PREFIX gessi: <http://gessi.upc.edu/app/> SELECT ?x ?y ?z WHERE {gessi:"+appName+" ?y ?z}" ;
        TupleQuery tupleQuery = repoConnection.prepareTupleQuery(query);
        TupleQueryResult result = tupleQuery.evaluate();
        App res = new App();
        Class<?> c = Class.forName("App");
        Field[] fieldList = c.getDeclaredFields();
        while (result.hasNext()) {
            BindingSet bindings = result.next();
            Value pred = bindings.getValue("y");
            Value obj = bindings.getValue("z");
            String object = obj.stringValue();
            String predicate = pred.stringValue();
            for (Field f : fieldList) {
                if (predicate.toLowerCase().endsWith(f.getName().toLowerCase())) {
                    f.setAccessible(true);
                    f.set(res,object);
                }
            }
        }
        repoConnection.close();
        return res;
    }

    public String getRd4jEndpoint() {
        return rd4jEndpoint;
    }

    public void setRd4jEndpoint(String rd4jEndpoint) {
        this.rd4jEndpoint = rd4jEndpoint;
    }
}
