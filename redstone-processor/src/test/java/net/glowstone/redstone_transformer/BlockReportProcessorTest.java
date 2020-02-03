package net.glowstone.redstone_transformer;

import static com.google.testing.compile.Compiler.javac;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.CompilationSubject;
import com.google.testing.compile.JavaFileObjects;
import javax.tools.JavaFileObject;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;

public class BlockReportProcessorTest {
    /*@Test
    void testCompileWithAnnotationProcessor() {
        BlockReportProcessor processor = new BlockReportProcessor();

        JavaFileObject airBlock = JavaFileObjects.forResource("AirBlock.java");
        Compilation compilation = javac().withProcessors(processor).compile(airBlock);

        CompilationSubject.assertThat(compilation).succeeded();
    }*/
}
