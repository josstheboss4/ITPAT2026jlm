package dojomanager.util;

import java.awt.Image;
import java.io.File;
import java.net.URL;
import javax.swing.ImageIcon;

/**
 * Loads the dojo belt image from inside the app (jar/exe) or from the
 * images folder next to the project when running from NetBeans.
 */
public final class ImageLoader {

    private ImageLoader() {
    }

    /**
     * Loads the belt image at its natural size.
     *
     * @return the image icon, or {@code null} if it could not be found
     */
    public static ImageIcon loadBeltIcon() {
        URL resource = ImageLoader.class.getResource("/images/belt.png");
        if (resource != null) {
            return new ImageIcon(resource);
        }
        File file = new File("images/belt.png");
        if (file.exists()) {
            return new ImageIcon(file.getAbsolutePath());
        }
        return null;
    }

    /**
     * Loads and scales the belt image.
     *
     * @param width  target width in pixels
     * @param height target height in pixels
     * @return the scaled image icon, or {@code null} if not found
     */
    public static ImageIcon loadBeltIcon(int width, int height) {
        ImageIcon raw = loadBeltIcon();
        if (raw == null || raw.getIconWidth() <= 0) {
            return null;
        }
        Image scaled = raw.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaled);
    }
}
