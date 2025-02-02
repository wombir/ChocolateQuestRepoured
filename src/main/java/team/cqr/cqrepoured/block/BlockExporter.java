package team.cqr.cqrepoured.block;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import team.cqr.cqrepoured.CQRMain;
import team.cqr.cqrepoured.tileentity.TileEntityExporter;
import team.cqr.cqrepoured.util.GuiHandler;

public class BlockExporter extends Block implements ITileEntityProvider {

	public BlockExporter() {
		super(Material.IRON);

		this.setSoundType(SoundType.METAL);
		this.setBlockUnbreakable();
		this.setResistance(Float.MAX_VALUE);
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, BlockState state, PlayerEntity playerIn, Hand hand, Direction facing, float hitX, float hitY, float hitZ) {
		if (worldIn.isRemote) {
			playerIn.openGui(CQRMain.INSTANCE, GuiHandler.EXPORTER_GUI_ID, worldIn, pos.getX(), pos.getY(), pos.getZ());
		}

		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityExporter();
	}

	public TileEntityExporter getTileEntity(IBlockAccess world, BlockPos pos) {
		return (TileEntityExporter) world.getTileEntity(pos);
	}

}
