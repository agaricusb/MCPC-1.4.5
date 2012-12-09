package net.minecraftforge.event;

import net.minecraft.server.ICommand;
import net.minecraft.server.ICommandListener;

@Cancelable
public class CommandEvent extends Event
{

    public final ICommand command;
    public final ICommandListener sender;
    public String[] parameters;
    public Throwable exception;

    public CommandEvent(ICommand command, ICommandListener sender, String[] parameters)
    {
        this.command = command;
        this.sender = sender;
        this.parameters = parameters;
    }
}
