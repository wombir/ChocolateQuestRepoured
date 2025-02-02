package team.cqr.cqrepoured.client.model;

import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ModelChestLeft extends ModelChestNormal {

	public ModelChestLeft() {
		this.textureWidth = 64;
		this.textureHeight = 32;

		this.chestBase = new ModelRenderer(this);
		this.chestBase.setRotationPoint(0.0F, 24.0F, 0.0F);
		this.chestBase.cubeList.add(new ModelBox(this.chestBase, 0, 0, -7.0F, -14.0F, -7.0F, 15, 14, 14, 0.0F, false));

		this.chestKnob = new ModelRenderer(this);
		this.chestKnob.setRotationPoint(0.0F, 24.0F, 0.0F);
		this.chestKnob.cubeList.add(new ModelBox(this.chestKnob, 0, 0, 7.0F, -11.0F, -8.0F, 1, 4, 1, 0.0F, false));
	}

}
