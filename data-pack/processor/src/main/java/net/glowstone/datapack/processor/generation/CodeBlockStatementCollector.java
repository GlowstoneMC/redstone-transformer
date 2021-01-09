package net.glowstone.datapack.processor.generation;

import com.squareup.javapoet.CodeBlock;

import java.util.stream.Collector;

public class CodeBlockStatementCollector {
    public static Collector<CodeBlock, CodeBlockStatementCollector, CodeBlock> collect() {
        return Collector.of(
            CodeBlockStatementCollector::new,
            CodeBlockStatementCollector::addStatement,
            CodeBlockStatementCollector::merge,
            CodeBlockStatementCollector::combine
        );
    }

    private final CodeBlock.Builder builder;

    private CodeBlockStatementCollector() {
        this.builder = CodeBlock.builder();
    }

    public CodeBlockStatementCollector addStatement(CodeBlock codeBlock) {
        this.builder.addStatement(codeBlock);
        return this;
    }

    public CodeBlockStatementCollector merge(CodeBlockStatementCollector other) {
        CodeBlock otherBlock = other.builder.build();
        if (!otherBlock.isEmpty()) {
            this.builder.add(otherBlock);
        }
        return this;
    }

    public CodeBlock combine() {
        return builder.build();
    }
}
