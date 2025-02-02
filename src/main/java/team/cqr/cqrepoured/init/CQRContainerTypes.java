package team.cqr.cqrepoured.init;

import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import team.cqr.cqrepoured.CQRMain;
import team.cqr.cqrepoured.inventory.ContainerAlchemyBag;
import team.cqr.cqrepoured.inventory.ContainerBackpack;
import team.cqr.cqrepoured.inventory.ContainerBadge;
import team.cqr.cqrepoured.inventory.ContainerBossBlock;
import team.cqr.cqrepoured.inventory.ContainerCQREntity;
import team.cqr.cqrepoured.inventory.ContainerMerchant;
import team.cqr.cqrepoured.inventory.ContainerMerchantEditTrade;
import team.cqr.cqrepoured.inventory.ContainerSpawner;

public class CQRContainerTypes {
	
	public static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, CQRMain.MODID);

	public static final RegistryObject<ContainerType<ContainerMerchant>> MERCHANT = CONTAINERS.register("merchant", () -> new ContainerType<>(ContainerMerchant::new));
	public static final RegistryObject<ContainerType<ContainerMerchantEditTrade>> MERCHANT_EDIT_TRADE = CONTAINERS.register("merchant_edit_trade", () -> new ContainerType<>(ContainerMerchantEditTrade::new));
	public static final RegistryObject<ContainerType<ContainerAlchemyBag>> ALCHEMY_BAG = CONTAINERS.register("alchemy_bag", () -> new ContainerType<>(ContainerAlchemyBag::new));
	public static final RegistryObject<ContainerType<ContainerBackpack>> BACKPACK = CONTAINERS.register("backpack", () -> new ContainerType<>(ContainerBackpack::new));
	public static final RegistryObject<ContainerType<ContainerBadge>> BADGE = CONTAINERS.register("badge", () -> new ContainerType<>(ContainerBadge::new));
	public static final RegistryObject<ContainerType<ContainerBossBlock>> BOSS_BLOCK = CONTAINERS.register("boss_block", () -> new ContainerType<>(ContainerBossBlock::new));
	public static final RegistryObject<ContainerType<ContainerCQREntity>> CQR_ENTITY_EDITOR = CONTAINERS.register("entity_editor", () -> new ContainerType<>(ContainerCQREntity::new));
	public static final RegistryObject<ContainerType<ContainerSpawner>> SPAWNER = CONTAINERS.register("spawner", () -> new ContainerType<>(ContainerSpawner::new));

	public static void registerContainerTypes() {
		CONTAINERS.register(FMLJavaModLoadingContext.get().getModEventBus());
	}

}
