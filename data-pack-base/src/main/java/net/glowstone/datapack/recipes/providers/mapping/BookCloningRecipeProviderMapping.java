package net.glowstone.datapack.recipes.providers.mapping;

import com.google.common.collect.ImmutableList;
import net.glowstone.datapack.TagManager;
import net.glowstone.datapack.loader.model.external.recipe.special.BookCloningRecipe;
import net.glowstone.datapack.loader.model.external.recipe.special.SuspiciousStewRecipe;
import net.glowstone.datapack.recipes.providers.BookCloningRecipeProvider;
import net.glowstone.datapack.recipes.providers.SuspiciousStewRecipeProvider;
import net.glowstone.datapack.utils.mapping.MappingArgument;

import java.util.List;

public class BookCloningRecipeProviderMapping implements RecipeProviderMapping<BookCloningRecipeProvider, BookCloningRecipe> {
    @Override
    public Class<BookCloningRecipe> getModelType() {
        return BookCloningRecipe.class;
    }

    @Override
    public Class<BookCloningRecipeProvider> getRecipeProviderType() {
        return BookCloningRecipeProvider.class;
    }

    @Override
    public List<MappingArgument> providerArguments(String namespace, String key, BookCloningRecipe recipe) {
        return ImmutableList.of(
            MappingArgument.forString(namespace),
            MappingArgument.forString(key)
        );
    }

    @Override
    public BookCloningRecipeProvider provider(TagManager tagManager, String namespace, String key, BookCloningRecipe recipe) {
        return new BookCloningRecipeProvider(namespace, key);
    }
}
