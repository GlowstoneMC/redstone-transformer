package net.glowstone.datapack.loader;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import net.glowstone.datapack.loader.exception.MalformedDataPackException;
import net.glowstone.datapack.loader.model.external.Data;
import net.glowstone.datapack.loader.model.external.DataPack;
import net.glowstone.datapack.loader.model.external.advancement.Advancement;
import net.glowstone.datapack.loader.model.external.function.Function;
import net.glowstone.datapack.loader.model.external.loottable.LootTable;
import net.glowstone.datapack.loader.model.external.mcmeta.McMeta;
import net.glowstone.datapack.loader.model.external.predicate.Predicate;
import net.glowstone.datapack.loader.model.external.recipe.Recipe;
import net.glowstone.datapack.loader.model.external.structures.Structure;
import net.glowstone.datapack.loader.model.external.tag.Tag;
import net.glowstone.datapack.loader.model.external.worldgen.WorldGen;

public class DataPackLoader {
    private final ObjectMapper objectMapper;

    public DataPackLoader() {
        objectMapper = new ObjectMapper()
            .enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
            .registerModules(
                new Jdk8Module()
            );
    }

    public Map<String, DataPack> loadPacks(Path dataPacksDirectory) throws IOException {
        FileSystem fs = dataPacksDirectory.getFileSystem();

        if (!Files.isDirectory(dataPacksDirectory)) {
            throw new IllegalArgumentException("dataPackDir must be a directory");
        }

        try {
            return Files.list(dataPacksDirectory)
                .map(this::loadDataPackWithName)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toMap(Entry::getKey, Entry::getValue));
        } catch (UncheckedIOException e) {
            IOException ioException = e.getCause();
            e.setStackTrace(
                Stream.concat(Arrays.stream(ioException.getStackTrace()), Arrays.stream(e.getStackTrace()))
                    .toArray(StackTraceElement[]::new)
            );
            throw e;
        }
    }

    public Optional<DataPack> loadPack(Path dataPackPath) {
        return loadDataPackWithName(dataPackPath)
            .map(Entry::getValue);
    }

    private Optional<Entry<String, DataPack>> loadDataPackWithName(Path dataPackPath) {
        try {
            if (Files.isDirectory(dataPackPath)) {
                String dataPackName = dataPackPath.getFileName().toString();
                DataPack dataPack = loadDataPackResources(Files.list(dataPackPath).collect(Collectors.toList()));
                return Optional.of(new SimpleImmutableEntry<>(dataPackName, dataPack));
            }

            if (dataPackPath.endsWith(".zip")) {
                String dataPackName = dataPackPath.getFileName().toString();
                dataPackName = dataPackName.substring(0, dataPackName.length() - 4);
                FileSystem zipFs = FileSystems.newFileSystem(dataPackPath, getClass().getClassLoader());
                DataPack dataPack = loadDataPackResources(zipFs.getRootDirectories());
                return Optional.of(new SimpleImmutableEntry<>(dataPackName, dataPack));
            }

            return Optional.empty();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private DataPack loadDataPackResources(Iterable<Path> packContents) throws IOException {
        McMeta mcMeta = null;
        Map<String, Data> namespacedData = null;

        for (Path rootFilePath : packContents) {
            switch (rootFilePath.getFileName().toString()) {
                case "pack.mcmeta":
                    if (Files.isRegularFile(rootFilePath)) {
                        mcMeta = objectMapper.readValue(Files.newInputStream(rootFilePath), McMeta.class);
                    }
                    break;

                case "data":
                    if (Files.isDirectory(rootFilePath)) {
                        namespacedData = Files.list(rootFilePath)
                            .map(this::loadDataWithName)
                            .collect(Collectors.toMap(Entry::getKey, Entry::getValue));
                    }
                    break;
            }
        }

        if (mcMeta == null) {
            throw new MalformedDataPackException(String.format("Required file 'pack.mcmeta' is missing in %s.", packContents.iterator().next().getParent()));
        }

        if (namespacedData == null) {
            throw new MalformedDataPackException(String.format("Required directory 'data' is missing in %s.", packContents.iterator().next().getParent()));
        }

        return new DataPack(mcMeta, namespacedData);
    }

    private Entry<String, Data> loadDataWithName(Path namespacePath) {
        try {
            String namespaceName = namespacePath.getFileName().toString();
            Data data = new Data(
                loadAdvancements(namespacePath),
                loadFunctions(namespacePath),
                loadLootTables(namespacePath),
                loadPredicates(namespacePath),
                loadRecipes(namespacePath),
                loadStructures(namespacePath),
                loadTags(namespacePath, "blocks"),
                loadTags(namespacePath, "items"),
                loadTags(namespacePath, "entity_types"),
                loadTags(namespacePath, "fluids"),
                loadTags(namespacePath, "functions"),
                loadWorldGen(namespacePath)
            );

            return new SimpleImmutableEntry<>(namespaceName, data);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private Map<String, Advancement> loadAdvancements(Path namespacePath) throws IOException {
        Map<String, Advancement> advancements = new HashMap<>();

        Path advancementsPath = namespacePath.resolve("advancements");
        for (Entry<String, Path> resource : iterateResourceFiles(advancementsPath, ".json")) {
            try {
                advancements.put(resource.getKey(), objectMapper.readValue(Files.newInputStream(resource.getValue()), Advancement.class));
            } catch (IOException e) {
                throw new IOException("Could not parse " + resource.getValue(), e);
            }
        }

        return advancements;
    }

    private Map<String, Function> loadFunctions(Path namespacePath) throws IOException {
        Map<String, Function> functions = new HashMap<>();

        Path functionsPath = namespacePath.resolve("functions");
        for (Entry<String, Path> resource : iterateResourceFiles(functionsPath, ".mcfunction")) {
            List<String> commands = Files.readAllLines(resource.getValue())
                .stream()
                .map(String::trim)
                .filter((s) -> !s.startsWith("#"))
                .collect(Collectors.toList());
            functions.put(resource.getKey(), new Function(commands));
        }

        return functions;
    }

    private Map<String, LootTable> loadLootTables(Path namespacePath) throws IOException {
        Map<String, LootTable> lootTables = new HashMap<>();

        Path lootTablePath = namespacePath.resolve("loot_tables");
        for (Entry<String, Path> resource : iterateResourceFiles(lootTablePath, ".json")) {
            try {
                lootTables.put(resource.getKey(), objectMapper.readValue(Files.newInputStream(resource.getValue()), LootTable.class));
            } catch (IOException e) {
                throw new IOException("Could not parse " + resource.getValue(), e);
            }
        }

        return lootTables;
    }

    private Map<String, Predicate> loadPredicates(Path namespacePath) throws IOException {
        Map<String, Predicate> predicates = new HashMap<>();

        Path predicatesPath = namespacePath.resolve("predicates");
        for (Entry<String, Path> resource : iterateResourceFiles(predicatesPath, ".json")) {
            predicates.put(resource.getKey(), objectMapper.readValue(Files.newInputStream(resource.getValue()), Predicate.class));
        }

        return predicates;
    }

    private Map<String, Recipe> loadRecipes(Path namespacePath) throws IOException {
        Map<String, Recipe> recipes = new HashMap<>();

        Path recipesPath = namespacePath.resolve("recipes");
        for (Entry<String, Path> resource : iterateResourceFiles(recipesPath, ".json")) {
            recipes.put(resource.getKey(), objectMapper.readValue(Files.newInputStream(resource.getValue()), Recipe.class));
        }

        return recipes;
    }

    private Map<String, Structure> loadStructures(Path namespacePath) throws IOException {
        Map<String, Structure> structures = new HashMap<>();

        Path functionsPath = namespacePath.resolve("structure");
        for (Entry<String, Path> resource : iterateResourceFiles(functionsPath, ".nbt")) {
            String nbt = String.join("\n", Files.readAllLines(resource.getValue()));
            structures.put(resource.getKey(), new Structure(nbt));
        }

        return structures;
    }

    private WorldGen loadWorldGen(Path namespacePath) throws IOException {
        return WorldGen.loadWorldGenWithName(namespacePath, this);
    }

    private Map<String, Tag> loadTags(Path namespacePath, String tagGroup) throws IOException {
        Map<String, Tag> tags = new HashMap<>();

        Path tagGroupPath = namespacePath.resolve("tags").resolve(tagGroup);
        for (Entry<String, Path> resource : iterateResourceFiles(tagGroupPath, ".json")) {
            tags.put(resource.getKey(), objectMapper.readValue(Files.newInputStream(resource.getValue()), Tag.class));
        }

        return tags;
    }

    public Iterable<Entry<String, Path>> iterateResourceFiles(Path parentDir, String extension) {
        if (Files.isDirectory(parentDir)) {
            return () -> {
                try {
                    Stream<Entry<String, Path>> filePathStream = Files.walk(parentDir)
                        .filter((filePath) -> Files.isRegularFile(filePath) && filePath.toString().endsWith(extension))
                        .map((filePath) -> {
                            String resourceName = StreamSupport.stream(parentDir.relativize(filePath).spliterator(), false)
                                .map(Object::toString)
                                .collect(Collectors.joining("/"));
                            resourceName = resourceName.substring(0, resourceName.length() - extension.length());
                            return new SimpleImmutableEntry<>(resourceName, filePath);
                        });
                    return filePathStream.iterator();
                } catch (IOException e) {
                    throw new UncheckedIOException(e);
                }
            };
        }
        return Collections.emptyList();
    }

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }
}
