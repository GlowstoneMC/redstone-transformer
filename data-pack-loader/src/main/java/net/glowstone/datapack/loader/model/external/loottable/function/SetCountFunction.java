package net.glowstone.datapack.loader.model.external.loottable.function;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import net.glowstone.datapack.loader.model.external.loottable.condition.Condition;
import net.glowstone.datapack.loader.model.external.advancement.condition.prop.RangedInt;

import java.io.IOException;
import java.util.List;

public class SetCountFunction extends Function {
    public static class CountDeserializer extends StdDeserializer<Count> {
        public CountDeserializer() {
            super(Count.class);
        }

        @Override
        public Count deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            switch (p.getCurrentToken()) {
                case VALUE_NUMBER_INT:
                    return new ConstantCount(p.getIntValue());

                case START_OBJECT:
                    return p.getCodec().readValue(p, DistributionCount.class);

                default:
                    throw new JsonMappingException(p, "Cannot create SetCountFunction.Count");
            }
        }
    }

    @JsonDeserialize(using = CountDeserializer.class)
    public interface Count {}

    public static class ConstantCount implements Count {
        private final int value;

        public ConstantCount(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    @JsonDeserialize(using = JsonDeserializer.None.class)
    @JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
    )
    @JsonSubTypes({
        @JsonSubTypes.Type(value = UniformDistributionCount.class, name = UniformDistributionCount.TYPE_ID),
        @JsonSubTypes.Type(value = BinomialDistributionCount.class, name = BinomialDistributionCount.TYPE_ID),
    })
    public interface DistributionCount extends Count {}

    public static class UniformDistributionCount implements DistributionCount {
        public static final String TYPE_ID = "minecraft:uniform";

        private final int min;
        private final int max;

        @JsonCreator
        public UniformDistributionCount(
            @JsonProperty("min") int min,
            @JsonProperty("max") int max) {
            this.min = min;
            this.max = max;
        }

        public int getMin() {
            return min;
        }

        public int getMax() {
            return max;
        }
    }

    public static class BinomialDistributionCount implements DistributionCount {
        public static final String TYPE_ID = "minecraft:binomial";

        private final int n;
        private final float p;

        @JsonCreator
        public BinomialDistributionCount(
            @JsonProperty("n") int n,
            @JsonProperty("p") float p) {
            this.n = n;
            this.p = p;
        }

        public int getN() {
            return n;
        }

        public float getP() {
            return p;
        }
    }

    public static final String TYPE_ID = "minecraft:set_count";

    private final Count count;

    @JsonCreator
    public SetCountFunction(
        @JsonProperty("conditions") List<Condition> conditions,
        @JsonProperty("count") Count count) {
        super(conditions);
        this.count = count;
    }

    public Count getCount() {
        return count;
    }
}
