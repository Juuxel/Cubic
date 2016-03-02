package juuxel.cubic.util;

public class Version
{
    public final int major, minor, patch, build;

    public Version(int major, int minor, int patch, int build)
    {
        this.major = major;
        this.minor = minor;
        this.patch = patch;
        this.build = build;
    }

    @Override
    public String toString()
    {
        return major + "." + minor + "." + patch + "-" + build;
    }
}
