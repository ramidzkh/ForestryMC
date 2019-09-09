/*******************************************************************************
 * Copyright (c) 2011-2014 SirSengir.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Various Contributors including, but not limited to:
 * SirSengir (original work), CovertJaguar, Player, Binnie, MysteriousAges
 ******************************************************************************/
package forestry.core.items;

import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import forestry.api.core.IItemModelRegister;
import forestry.api.core.IModelManager;
import forestry.core.ItemGroupForestry;

public class ItemForestryFood extends Item implements IItemModelRegister {

	private boolean isDrink = false;

	public ItemForestryFood(int heal) {
		this(heal, 0.6f);
	}

	public ItemForestryFood(Item.Properties properties) {
		super(properties);
	}

	public ItemForestryFood(int heal, float saturation) {
		this(heal, saturation, new Item.Properties());
	}

	public ItemForestryFood(int heal, float saturation, Item.Properties properties) {
		super(properties
			.group(ItemGroupForestry.tabForestry)
			.food((new Food.Builder())
				.hunger(heal)
				.saturation(saturation)
				.build()));
	}

	@Override
	public UseAction getUseAction(ItemStack itemstack) {
		if (isDrink) {
			return UseAction.DRINK;
		} else {
			return UseAction.EAT;
		}
	}

	public ItemForestryFood setIsDrink() {
		isDrink = true;
		return this;
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void registerModel(Item item, IModelManager manager) {
		manager.registerItemModel(item, 0);
	}

}
