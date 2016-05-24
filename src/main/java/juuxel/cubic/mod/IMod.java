package juuxel.cubic.mod;

/**
 * An IMod is a mod for Cubic. This interface
 * provides methods for hooking to Cubic's init
 * phases.
 */
public interface IMod
{
    /**
     * Called after Cubic's coreInit phase.
     * Registers things like sprite providers.
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
