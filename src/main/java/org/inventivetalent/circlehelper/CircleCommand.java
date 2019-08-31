package org.inventivetalent.circlehelper;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;

public class CircleCommand extends CommandBase implements ICommand {


	public static String JOINED_CIRCLE_TYPES;
	static{
		CircleType[] types = CircleType.values();
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < types.length; i++) {
			if (i != 0) {
				builder.append(",");
			}
			builder.append(types[i].name());
		}
		JOINED_CIRCLE_TYPES = builder.toString();
	}

	@Override
	public String getName() {
		return "circlehelper";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "/CircleHelper [center|type|radius]";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if (args.length ==  0) {
			ShapeManager.visible = !ShapeManager.visible;
			sender.sendMessage(new TextComponentString("§aHelper is now §b" + (ShapeManager.visible ? "visible" : "hidden")));
			return;
		}

		if ("center".equalsIgnoreCase(args[0])) {
			BlockPos blockPos = sender.getPosition();
			ShapeManager.center = new Pos(blockPos.getX(), blockPos.getY(), blockPos.getZ());
			sender.sendMessage(new TextComponentString("§aCenter set to your current position"));

			ShapeManager.resetAndGenerate();
			return;
		}
		if ("type".equalsIgnoreCase(args[0])) {
			if (args.length == 1) {
				sender.sendMessage(new TextComponentString("§c/CircleHelper type " + JOINED_CIRCLE_TYPES));
				return;
			}
			try {
				ShapeManager.type = CircleType.valueOf(args[1].toUpperCase());
				sender.sendMessage(new TextComponentString("§aType changed to " + ShapeManager.type));

				ShapeManager.resetAndGenerate();
				return;
			} catch (Exception e) {
				sender.sendMessage(new TextComponentString("§cUnknown type. Available: " + JOINED_CIRCLE_TYPES));
				return;
			}
		}
		if ("radius".equalsIgnoreCase(args[0])) {
			if (args.length == 1) {
				sender.sendMessage(new TextComponentString("§c/CircleHelper radius <radius> [radius2] [radius3]"));
				return;
			}

			//TODO: multiple radii
			try {
				int r = ShapeManager.radiusX = ShapeManager.radiusY = ShapeManager.radiusZ = Integer.parseInt(args[1]);
				sender.sendMessage(new TextComponentString("§aRadius changed to " + r));

				ShapeManager.resetAndGenerate();
			} catch (NumberFormatException e) {
				sender.sendMessage(new TextComponentString("§cCould not parse number"));
				return;
			}
		}

	}


	@Override
	public int getRequiredPermissionLevel() {
		return 0;
	}

}
