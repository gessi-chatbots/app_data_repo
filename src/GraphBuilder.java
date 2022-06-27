import org.eclipse.rdf4j.model.util.ModelBuilder;

public class GraphBuilder {

    private ModelBuilder builder = new ModelBuilder();


    public GraphBuilder(String graphName) {
        builder.setNamespace("cb","https://gessi.org/app/");
        builder.setNamespace("sch","https.schema.org/");
        builder.namedGraph("cb:"+graphName);
    }

    public void setSubject(String subject) {
        builder.subject("cb:"+subject);
    }
}
