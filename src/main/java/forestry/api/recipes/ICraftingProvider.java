/*******************************************************************************
 * Copyright 2011-2014 SirSengir
 *
 * This work (the API) is licensed under the "MIT" License, see LICENSE.txt for details.
 ******************************************************************************/
package forestry.api.recipes;

import java.util.Collection;
import java.util.stream.Collectors;

import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeManager;

public interface ICraftingProvider<T extends IForestryRecipe> {

	/**
	 * Add a new recipe to the crafting provider for all worlds.
	 *
	 * @return <tt>true</tt> if this collection changed as a result of the call
	 * @since Forestry 4.1.0
	 */
	boolean addRecipe(T recipe);

	/**
	 * Gets a collection of all currently registered recipes which this provider supports
	 *
	 * @param manager The recipe manager to use
	 * @return A collection of recipes
	 */
	Collection<T> getRecipes(RecipeManager manager);

	/**
	 * A utility method to find all recipes in a {@link RecipeManager} of a given {@link IRecipeType type}
	 *
	 * @param manager The recipe manager
	 * @param type    The recipe type object
	 * @param <T>     The recipe type
	 * @return A collection of all recipes of this type in the given {@link RecipeManager}
	 */
	static <T extends IForestryRecipe> Collection<T> findRecipes(RecipeManager manager, IRecipeType<T> type) {
		return manager.getRecipes(type).values().stream()
				.map(r -> (T) r)
				.collect(Collectors.toSet());
	}
}
