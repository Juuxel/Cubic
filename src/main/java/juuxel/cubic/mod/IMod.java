package juuxel.cubic.mod;

public interface IMod
{
    /**
     * Called after Cubic's coreInit phase.
     * Registers things like sprite handlers.
     */
    default void coreInit()
    {}

    /**
     * Called after Cubic's contentInit phase.
     * Registers things like custom creatures.
     */
    default void contentInit()
    {}
}
