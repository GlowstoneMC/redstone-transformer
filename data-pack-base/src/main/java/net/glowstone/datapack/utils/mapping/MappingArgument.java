package net.glowstone.datapack.utils.mapping;

import com.google.common.collect.ImmutableList;
import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class MappingArgument {
    public static MappingArgument forString(String value) {
        return new MappingArgument(Optional.of(value), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty());
    }

    public static MappingArgument forCharacter(char value) {
        return new MappingArgument(Optional.empty(), Optional.of(value), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty());
    }

    public static MappingArgument forInteger(int value) {
        return new MappingArgument(Optional.empty(), Optional.empty(), Optional.of(value), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty());
    }

    public static MappingArgument forFloat(float value) {
        return new MappingArgument(Optional.empty(),Optional.empty(),  Optional.empty(), Optional.of(value), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty());
    }

    public static MappingArgument forEnum(Enum<?> value) {
        return new MappingArgument(Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.of(value), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty());
    }

    public static MappingArgument forTag(String registry, NamespacedKey key, Class<? extends Keyed> type) {
        return new MappingArgument(Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.of(new TagInstanceMapping(registry, key, type)), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty());
    }

    public static MappingArgument forOptional(Optional<MappingArgument> value) {
        return new MappingArgument(Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.of(value), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty());
    }

    public static MappingArgument forList(List<MappingArgument> value) {
        return new MappingArgument(Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.of(value), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty());
    }

    public static MappingArgument forSet(Set<MappingArgument> value) {
        return new MappingArgument(Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.of(value), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty());
    }

    public static MappingArgument forMap(Class<?> keyType, Class<?> valueType, Map<MappingArgument, MappingArgument> value) {
        return new MappingArgument(Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.of(new MapMapping(keyType, valueType, value)), Optional.empty(), Optional.empty(), Optional.empty());
    }

    public static MappingArgument forClassReference(Class<?> type) {
        return new MappingArgument(Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.of(type), Optional.empty(), Optional.empty());
    }

    public static MappingArgument forClassConstructor(Class<?> type, List<MappingArgument> arguments) {
        return new MappingArgument(Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.of(new ClassMapping(type, ImmutableList.copyOf(arguments))), Optional.empty());
    }

    public static MappingArgument forMethodReference(Class<?> type, String method) {
        return new MappingArgument(Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.of(new MethodMapping(type, method)));
    }

    private final Optional<String> stringValue;
    private final Optional<Character> characterValue;
    private final Optional<Integer> integerValue;
    private final Optional<Float> floatValue;
    private final Optional<Enum<?>> enumValue;
    private final Optional<TagInstanceMapping> tagValue;
    private final Optional<Optional<MappingArgument>> optionalValue;
    private final Optional<List<MappingArgument>> listValue;
    private final Optional<Set<MappingArgument>> setValue;
    private final Optional<MapMapping> mapValue;
    private final Optional<Class<?>> classReference;
    private final Optional<ClassMapping> classConstructor;
    private final Optional<MethodMapping> methodReference;

    private MappingArgument(
        Optional<String> stringValue,
        Optional<Character> characterValue,
        Optional<Integer> integerValue,
        Optional<Float> floatValue,
        Optional<Enum<?>> enumValue,
        Optional<TagInstanceMapping> tagValue,
        Optional<Optional<MappingArgument>> optionalValue,
        Optional<List<MappingArgument>> listValue,
        Optional<Set<MappingArgument>> setValue,
        Optional<MapMapping> mapValue,
        Optional<Class<?>> classReference,
        Optional<ClassMapping> classConstructor,
        Optional<MethodMapping> methodReference) {
        this.stringValue = stringValue;
        this.characterValue = characterValue;
        this.integerValue = integerValue;
        this.floatValue = floatValue;
        this.enumValue = enumValue;
        this.tagValue = tagValue;
        this.optionalValue = optionalValue;
        this.listValue = listValue;
        this.setValue = setValue;
        this.mapValue = mapValue;
        this.classReference = classReference;
        this.classConstructor = classConstructor;
        this.methodReference = methodReference;
    }

    public Optional<String> getStringValue() {
        return stringValue;
    }

    public Optional<Character> getCharacterValue() {
        return characterValue;
    }

    public Optional<Integer> getIntegerValue() {
        return integerValue;
    }

    public Optional<Float> getFloatValue() {
        return floatValue;
    }

    public Optional<Enum<?>> getEnumValue() {
        return enumValue;
    }

    public Optional<TagInstanceMapping> getTagValue() {
        return tagValue;
    }

    public Optional<Optional<MappingArgument>> getOptionalValue() {
        return optionalValue;
    }

    public Optional<List<MappingArgument>> getListValue() {
        return listValue;
    }

    public Optional<Set<MappingArgument>> getSetValue() {
        return setValue;
    }

    public Optional<MapMapping> getMapValue() {
        return mapValue;
    }

    public Optional<Class<?>> getClassReference() {
        return classReference;
    }

    public Optional<ClassMapping> getClassConstructor() {
        return classConstructor;
    }

    public Optional<MethodMapping> getMethodReference() {
        return methodReference;
    }
}
