package team.cqr.cqrepoured.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.*;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import team.cqr.cqrepoured.tileentity.TileEntityTable;

public class BlockTable extends Block implements ITileEntityProvider {

	public static final PropertyBool TOP = PropertyBool.create("top");

	public static final AxisAlignedBB TABLE_AABB = new AxisAlignedBB(0.0D, 0.8125D, 0.0D, 1.0D, 1.0D, 1.0D);
	public static final AxisAlignedBB TABLE_TOP_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);

	public BlockTable() {
		super(Material.WOOD);

		this.setSoundType(SoundType.WOOD);
		this.setHardness(2.0F);
		this.setResistance(15.0F);
		this.setHarvestLevel("axe", 0);
		this.setDefaultState(this.blockState.getBaseState().withProperty(TOP, false));
	}

	@Deprecated
	@Override
	public boolean isFullCube(BlockState state) {
		return false;
	}

	@Deprecated
	@Override
	public boolean isOpaqueCube(BlockState state) {
		return false;
	}

	@Deprecated
	@Override
	public AxisAlignedBB getBoundingBox(BlockState state, IBlockAccess source, BlockPos pos) {
		if (Boolean.TRUE.equals(state.getValue(TOP))) {
			return TABLE_AABB;
		} else {
			return TABLE_TOP_AABB;
		}
	}

	@Deprecated
	@Override
	public BlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(TOP, ((meta & 1) != 0));
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	public int getMetaFromState(BlockState state) {
		if (Boolean.TRUE.equals(state.getValue(TOP))) {
			return 1;
		} else {
			return 0;
		}
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, TOP);
	}

	@Deprecated
	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, BlockState state, BlockPos pos, Direction face) {
		return BlockFaceShape.UNDEFINED;
	}

	@Override
	public BlockState getStateForPlacement(World world, BlockPos pos, Direction facing, float hitX, float hitY, float hitZ, int meta, LivingEntity placer, Hand hand) {
		boolean flag = (world.getBlockState(pos.west()).getBlock() == this && world.getBlockState(pos.east()).getBlock() == this) || (world.getBlockState(pos.north()).getBlock() == this && world.getBlockState(pos.south()).getBlock() == this);
		return this.getDefaultState().withProperty(TOP, flag);
	}

	@Deprecated
	@Override
	public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		if (Boolean.TRUE.equals(state.getValue(TOP))) {
			if ((worldIn.getBlockState(pos.west()).getBlock() != this || worldIn.getBlockState(pos.east()).getBlock() != this) && (worldIn.getBlockState(pos.north()).getBlock() != this || worldIn.getBlockState(pos.south()).getBlock() != this)) {
				worldIn.setBlockState(pos, this.getDefaultState().withProperty(TOP, false));
			}
		} else {
			if ((worldIn.getBlockState(pos.west()).getBlock() == this && worldIn.getBlockState(pos.east()).getBlock() == this) || (worldIn.getBlockState(pos.north()).getBlock() == this && worldIn.getBlockState(pos.south()).getBlock() == this)) {
				worldIn.setBlockState(pos, this.getDefaultState().withProperty(TOP, true));
			}
		}
	}

	@Deprecated
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, BlockState state, PlayerEntity playerIn, Hand hand, Direction facing, float hitX, float hitY, float hitZ) {
		TileEntityTable tile = this.getTileEntity(worldIn, pos);
		ItemStack helditem = playerIn.getHeldItem(hand);
		IItemHandler itemHandler = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing);
		ItemStack table = itemHandler.getStackInSlot(0);

		if (table.isEmpty()) {
			if (helditem.isEmpty()) {
				return false;
			}

			if (!worldIn.isRemote) {
				playerIn.setHeldItem(hand, itemHandler.insertItem(0, helditem, false));
				tile.setRotation(Math.round(playerIn.rotationYaw / 22.5F) % 16);
			} else {
				worldIn.playSound(pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.BLOCKS, 0.5F, worldIn.rand.nextFloat() * 0.4F + 0.8F, false);
			}
		} else if (!playerIn.isSneaking()) {
			if (!worldIn.isRemote) {
				ItemStack stack = itemHandler.extractItem(0, 64, false);
				if (!playerIn.inventory.addItemStackToInventory(stack)) {
					ItemEntity item = new ItemEntity(worldIn, pos.getX() + 0.5F, pos.getY() + 1.0F, pos.getZ() + 0.5F, stack);
					worldIn.spawnEntity(item);
				}
			} else {
				worldIn.playSound(pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.BLOCKS, 0.5F, worldIn.rand.nextFloat() * 0.4F + 0.8F, false);
			}
		} else {
			if (!worldIn.isRemote) {
				tile.setRotation(tile.getRotation() + 1);
			} else {
				worldIn.playSound(pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.BLOCKS, 0.5F, worldIn.rand.nextFloat() * 0.4F + 0.8F, false);
			}
		}

		return true;
	}

	@Override
	public void breakBlock(World world, BlockPos pos, BlockState state) {
		TileEntityTable tile = this.getTileEntity(world, pos);
		IItemHandler itemHandler = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, Direction.NORTH);
		ItemStack stack = itemHandler.getStackInSlot(0);

		if (!stack.isEmpty()) {
			ItemEntity item = new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), stack);
			world.spawnEntity(item);
		}
		super.breakBlock(world, pos, state);
	}

	public TileEntityTable getTileEntity(IBlockAccess world, BlockPos pos) {
		return (TileEntityTable) world.getTileEntity(pos);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityTable();
	}

}
