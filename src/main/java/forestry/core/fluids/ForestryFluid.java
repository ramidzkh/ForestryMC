package forestry.core.fluids;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.Item;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;

import net.minecraftforge.fluids.FluidAttributes;

public abstract class ForestryFluid extends FlowingFluid {
	public final boolean flowing;
	public final ForestryFluids definition;

	public ForestryFluid(ForestryFluids definition, boolean flowing) {
		this.definition = definition;
		this.flowing = flowing;
	}

	@Override
	protected FluidAttributes createAttributes(Fluid fluid) {
		ResourceLocation[] resources = definition.getResources();
		return FluidAttributes.builder(fluid.getRegistryName().getPath(), resources[0], definition.flowTextureExists() ? resources[1] : resources[0])
			.density(definition.getDensity())
			.viscosity(definition.getViscosity())
			.temperature(definition.getTemperature())
			.build();
	}

	@Override
	public Fluid getFlowingFluid() {
		if (flowing) {
			return this;
		}
		return definition.getFlowing();
	}

	@Override
	public Fluid getStillFluid() {
		if (!flowing) {
			return this;
		}
		return definition.getFluid();
	}

	@Override
	protected boolean canSourcesMultiply() {
		return false;
	}

	@Override
	protected void beforeReplacingBlock(IWorld world, BlockPos blockPos, BlockState blockState) {
		TileEntity tileEntity = blockState.hasTileEntity() ? world.getTileEntity(blockPos) : null;
		Block.spawnDrops(blockState, world.getWorld(), blockPos, tileEntity);
	}

	@Override
	protected int getSlopeFindDistance(IWorldReader iWorldReader) {
		return 4;
	}

	@Override
	protected int getLevelDecreasePerBlock(IWorldReader iWorldReader) {
		return 1;
	}

	@Override
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.TRANSLUCENT;
	}

	@Override
	public Item getFilledBucket() {
		return null;    //TODO fluids
	}

	//Checks if the fluid can flow in this direction
	@Override
	protected boolean func_215665_a(IFluidState fluidState, IBlockReader blockReader, BlockPos pos, Fluid fluid, Direction direction) {
		return false;
	}

	@Override
	public int getTickRate(IWorldReader worldReader) {
		return 0;
	}

	@Override
	protected float getExplosionResistance() {
		return 100.0F;
	}

	public Block getBlock() {
		return flowing ? definition.flowingBlock : definition.sourceBlock;
	}

	@Override
	protected BlockState getBlockState(IFluidState state) {
		return getBlock().getDefaultState().with(FlowingFluidBlock.LEVEL, getLevelFromState(state));
	}

	public static class Flowing extends ForestryFluid {
		public Flowing(ForestryFluids definition) {
			super(definition, true);
		}

		@Override
		protected void fillStateContainer(StateContainer.Builder<Fluid, IFluidState> builder) {
			super.fillStateContainer(builder);
			builder.add(LEVEL_1_8);
		}

		@Override
		public int getLevel(IFluidState fluidState) {
			return fluidState.get(LEVEL_1_8);
		}

		public boolean isSource(IFluidState state) {
			return false;
		}
	}

	public static class Source extends ForestryFluid {
		public Source(ForestryFluids definition) {
			super(definition, false);
		}

		@Override
		public int getLevel(IFluidState fluidState) {
			return 8;
		}

		@Override
		public boolean isSource(IFluidState state) {
			return true;
		}
	}

}
