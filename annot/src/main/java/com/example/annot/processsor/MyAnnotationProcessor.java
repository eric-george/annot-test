package com.example.annot.processsor;

import com.example.annot.MyAnnotation;
import net.bytebuddy.ByteBuddy;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.ElementFilter;
import java.io.IOException;
import java.util.Set;

@SupportedAnnotationTypes({"com.example.annot.MyAnnotation"})
@SupportedSourceVersion(SourceVersion.RELEASE_19)
public class MyAnnotationProcessor extends AbstractProcessor {

    private Filer filer;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        filer = processingEnv.getFiler();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        ElementFilter.typesIn(roundEnv.getElementsAnnotatedWith(MyAnnotation.class)).forEach(typeElement -> {
            // Create a class as source
            try {
                var className = typeElement.getSimpleName()+"_source";
                System.err.println("GeneratingSource: "+className);
                var fileWriter = filer.createSourceFile(className).openWriter();
                fileWriter.write("package com.example.annot;public class "+className+"{}");
                fileWriter.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            // Create a class as bytecode
            try {
                var className = typeElement.getQualifiedName()+"_bytecode";
                System.err.println("GeneratingByteCode: "+className);
                var fileWriter = filer.createClassFile(className).openOutputStream();
                fileWriter.write(new ByteBuddy()
                      .subclass(Object.class)
                      .name(className)
                      .make()
                      .getBytes()
                );
                fileWriter.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        return false;
    }
}
