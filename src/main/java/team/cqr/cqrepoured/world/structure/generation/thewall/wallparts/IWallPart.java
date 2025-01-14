package team.cqr.cqrepoured.world.structure.generation.thewall.wallparts;

import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap.Type;
import net.minecraft.world.server.ServerWorld;
import team.cqr.cqrepoured.world.structure.generation.generation.GeneratableDungeon;

/**
 * Copyright (c) 23.05.2019 Developed by DerToaster98 GitHub: https://github.com/DerToaster98
 */
public interface IWallPart {

	int getTopY();

	void generateWall(int chunkX, int chunkZ, ChunkGenerator cg, GeneratableDungeon.Builder dungeonBuilder, ServerWorld sw);

	default int getBottomY(ChunkGenerator cg, int x1, int z1) {
		int lowestY = this.getTopY() - 16;
		for (int x2 = 0; x2 < 16; x2++) {
			for (int z2 = 0; z2 < 16; z2++) {
				//int y = DungeonGenUtils.getYForPos(world, x1 + x2, z1 + z2, true);
				int y = cg.getFirstOccupiedHeight(x2 + x1, z2 + z2, Type.WORLD_SURFACE_WG);
				if (y < lowestY) {
					lowestY = y;
				}
			}
		}
		return Math.max(lowestY - 6, 1);
	}

}
