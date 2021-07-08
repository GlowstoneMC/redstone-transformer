package net.glowstone.datapack.loader.model.external.loottable.function;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.glowstone.datapack.loader.model.external.loottable.condition.Condition;
import net.glowstone.datapack.loader.model.external.advancement.condition.prop.RangedDouble;

import java.util.List;

public class SetAttributesFunction extends Function {
    public enum Operation {
        ADDITION,
        MULTIPLY_BASE,
        MULTIPLY_TOTAL,
    }

    public enum Slot {
        @JsonProperty("mainhand") MAINHAND,
        @JsonProperty("offhand") OFFHAND,
        @JsonProperty("feet") FEET,
        @JsonProperty("legs") LEGS,
        @JsonProperty("chest") CHEST,
        @JsonProperty("head") HEAD,
    }

    public static class Modifier {
        private final String name;
        private final String attribute;
        private final Operation operation;
        private final RangedDouble amount;
        private final String id;
        private final List<Slot> slot;

        @JsonCreator
        public Modifier(
            @JsonProperty("name") String name,
            @JsonProperty("attribute") String attribute,
            @JsonProperty("operation") Operation operation,
            @JsonProperty("amount") RangedDouble amount,
            @JsonProperty("id") String id,
            @JsonProperty("slot") List<Slot> slot) {
            this.name = name;
            this.attribute = attribute;
            this.operation = operation;
            this.amount = amount;
            this.id = id;
            this.slot = slot;
        }

        public String getName() {
            return name;
        }

        public String getAttribute() {
            return attribute;
        }

        public Operation getOperation() {
            return operation;
        }

        public RangedDouble getAmount() {
            return amount;
        }

        public String getId() {
            return id;
        }

        public List<Slot> getSlot() {
            return slot;
        }
    }

    public static final String TYPE_ID = "minecraft:set_attributes";

    private final List<Modifier> modifiers;

    @JsonCreator
    public SetAttributesFunction(
        @JsonProperty("conditions") List<Condition> conditions,
        @JsonProperty("modifiers") List<Modifier> modifiers) {
        super(conditions);
        this.modifiers = modifiers;
    }

    public List<Modifier> getModifiers() {
        return modifiers;
    }
}
