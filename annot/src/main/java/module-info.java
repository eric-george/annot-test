import com.example.annot.processsor.MyAnnotationProcessor;

module org.example.annotation {
    requires java.compiler;
    requires net.bytebuddy;
    exports com.example.annot;

    provides javax.annotation.processing.Processor with MyAnnotationProcessor;
}
