package net.glowstone.datapack.processor;

import net.glowstone.datapack.loader.DataPackLoader;
import net.glowstone.datapack.loader.model.external.DataPack;
import net.glowstone.datapack.processor.generation.SourceGenerator;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Callable;

@Command(name = "DataPackProcessor", description = "Generates Bukkit classes from a data pack.", mixinStandardHelpOptions = true)
public class DataPackProcessorRunner implements Callable<Integer> {
    public static void main(String[] args) {
        int exitCode = new CommandLine(new DataPackProcessorRunner()).execute(args);

        if (exitCode > 0) {
            throw new IllegalStateException("Execution failed with exit code: " + exitCode);
        }
    }

    @Parameters(index = "0", description = "The directory that contains the data pack directory or zip file.")
    private Path dataPackPath;

    @Parameters(index = "1", description = "The directory to output the class files into.")
    private Path generatedClassPath;

    @Parameters(index = "2", description = "The namespace for the generated data pack files.")
    private String generatedClassNamespace;

    @Override
    public Integer call() throws IOException {
        DataPackLoader loader = new DataPackLoader();

        Optional<DataPack> dataPack = loader.loadPack(dataPackPath, true);

        if (!dataPack.isPresent()) {
            throw new IllegalArgumentException("Could not load data pack.");
        }

        SourceGenerator sourceGenerator = new SourceGenerator();
        sourceGenerator.generateSources(generatedClassPath, generatedClassNamespace, dataPack.get());

        return 0;
    }
}
