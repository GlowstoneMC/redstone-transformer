package net.glowstone.datapack.loader.model.external.loottable.function;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.glowstone.datapack.loader.model.external.loottable.condition.Condition;
import net.glowstone.datapack.loader.model.external.loottable.SourceEntity;

import java.util.List;

public class CopyNbtFunction extends Function {
    public enum Operation {
        @JsonProperty("append") APPEND,
        @JsonProperty("merge") MERGE,
        @JsonProperty("replace") REPLACE,
    }

    public static class OperationTargets {
        private final String source;
        private final String target;
        private final Operation op;

        @JsonCreator
        public OperationTargets(
            @JsonProperty("source") String source,
            @JsonProperty("target") String target,
            @JsonProperty("op") Operation op) {
            this.source = source;
            this.target = target;
            this.op = op;
        }

        public String getSource() {
            return source;
        }

        public String getTarget() {
            return target;
        }

        public Operation getOp() {
            return op;
        }
    }

    public static final String TYPE_ID = "minecraft:copy_nbt";

    private final SourceEntity source;
    private final List<OperationTargets> ops;

    @JsonCreator
    public CopyNbtFunction(
        @JsonProperty("conditions") List<Condition> conditions,
        @JsonProperty("source") SourceEntity source,
        @JsonProperty("ops") List<OperationTargets> ops) {
        super(conditions);
        this.source = source;
        this.ops = ops;
    }

    public SourceEntity getSource() {
        return source;
    }

    public List<OperationTargets> getOps() {
        return ops;
    }
}
