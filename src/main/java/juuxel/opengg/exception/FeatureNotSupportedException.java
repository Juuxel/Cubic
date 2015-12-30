package juuxel.opengg.exception;

public class FeatureNotSupportedException extends RuntimeException
{
    public FeatureNotSupportedException(String feature)
    {
        super("Feature not supported: " + feature);
    }

    public FeatureNotSupportedException(String feature, String message)
    {
        this(feature + "\n" + message);
    }
}
