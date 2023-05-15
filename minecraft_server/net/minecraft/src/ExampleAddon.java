package net.minecraft.src;

public class ExampleAddon extends FCAddOn {
    public static ExampleAddon instance = new ExampleAddon();
    
    private ExampleAddon() {
        super("example", "1.0", ":)");
    }
    
    @Override
    public void Initialize() {

    }
}