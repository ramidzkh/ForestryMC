package forestry.api.recipes;

import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.RecipeManager;

public interface ISqueezerContainerManager extends ICraftingProvider<ISqueezerContainerRecipe> {

	/**
	 * Add a recipe for a fluid container to the squeezer.
	 * This will add recipes to get all types of liquids out of this type of fluid container.
	 *
	 * @param timePerItem    Number of work cycles required to squeeze one set of resources.
	 * @param emptyContainer The empty version of the fluid container that will be squeezed.
	 * @param remnants       Item stack representing the possible remnants from this recipe. May be empty.
	 * @param chance         Chance remnants will be produced by a single recipe cycle, from 0 to 1.
	 */
	void addContainerRecipe(int timePerItem, ItemStack emptyContainer, ItemStack remnants, float chance);

	@Nullable
	ISqueezerContainerRecipe findMatchingContainerRecipe(@Nullable RecipeManager recipeManager, ItemStack filledContainer);
}
