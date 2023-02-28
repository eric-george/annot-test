package annot_usage;

import com.example.annot.AnnotUsage_source;
import com.example.annot.MyAnnotation;
import annot_usage.AnnotUsage_bytecode;

@MyAnnotation
public class AnnotUsage {
    int foo;
    public static void main(String[] args) {
        AnnotUsage_source annotUsageSource;
        AnnotUsage_bytecode annotUsageBytecode;
    }
}
