import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import java.io.File;
import java.io.IOException;
import tvpoo.InputMapper.InputMapper;
import tvpoo.OutputMapper.Output;
public final class Main {
    private Main() {
        //constructor for checkstyle
    }
    /**
     * @param args first argument received from Test.java is:
     *             name of input file basic_[nr].json & output created by me
     *             "result_basic.json" this file makes diff with ref files
     * @throws IOException in case of incorrect arguments or input/output file
     */
    public static void main(final String[] args) throws IOException {
        ArrayNode output;
        Output outputClass = new Output();

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
        InputMapper inputMapper = objectMapper.readValue(new File(args[0]), InputMapper.class);

        output = outputClass.output(inputMapper);

        objectWriter.writeValue(new File("result_basic.json"), output);
        objectWriter.writeValue(new File(args[1]), output);
    }
}
