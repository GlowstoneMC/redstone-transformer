package net.glowstone.datapack.loader.model.external;

import java.util.Map;
import net.glowstone.datapack.loader.model.external.advancement.Advancement;
import net.glowstone.datapack.loader.model.external.function.Function;
import net.glowstone.datapack.loader.model.external.loottable.LootTable;
import net.glowstone.datapack.loader.model.external.predicate.Predicate;
import net.glowstone.datapack.loader.model.external.recipe.Recipe;
import net.glowstone.datapack.loader.model.external.structures.Structure;
import net.glowstone.datapack.loader.model.external.tag.Tag;

public class Data {
    private final Map<String, Advancement> advancements;
    private final Map<String, Function> functions;
    private final Map<String, LootTable> lootTables;
    private final Map<String, Predicate> predicates;
    private final Map<String, Recipe> recipes;
    private final Map<String, Structure> structures;
    private final Map<String, Tag> blockTags;
    private final Map<String, Tag> itemTags;
    private final Map<String, Tag> entityTypeTags;
    private final Map<String, Tag> fluidTags;
    private final Map<String, Tag> functionTags;

    public Data(
        Map<String, Advancement> advancements,
        Map<String, Function> functions,
        Map<String, LootTable> lootTables,
        Map<String, Predicate> predicates,
        Map<String, Recipe> recipes,
        Map<String, Structure> structures,
        Map<String, Tag> blockTags,
        Map<String, Tag> itemTags,
        Map<String, Tag> entityTypeTags,
        Map<String, Tag> fluidTags,
        Map<String, Tag> functionTags) {
        this.advancements = advancements;
        this.functions = functions;
        this.lootTables = lootTables;
        this.predicates = predicates;
        this.recipes = recipes;
        this.structures = structures;
        this.blockTags = blockTags;
        this.itemTags = itemTags;
        this.entityTypeTags = entityTypeTags;
        this.fluidTags = fluidTags;
        this.functionTags = functionTags;
    }

    public Map<String, Advancement> getAdvancements() {
        return advancements;
    }

    public Map<String, Function> getFunctions() {
        return functions;
    }

    public Map<String, LootTable> getLootTables() {
        return lootTables;
    }

    public Map<String, Predicate> getPredicates() {
        return predicates;
    }

    public Map<String, Recipe> getRecipes() {
        return recipes;
    }

    public Map<String, Structure> getStructures() {
        return structures;
    }

    public Map<String, Tag> getBlockTags() {
        return blockTags;
    }

    public Map<String, Tag> getItemTags() {
        return itemTags;
    }

    public Map<String, Tag> getEntityTypeTags() {
        return entityTypeTags;
    }

    public Map<String, Tag> getFluidTags() {
        return fluidTags;
    }

    public Map<String, Tag> getFunctionTags() {
        return functionTags;
    }
}
