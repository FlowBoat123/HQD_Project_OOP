package logic;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Utility class for generating and displaying QR codes using the ZXing library.
 */
public class QRGenerator {

    /**
     * Generates a QR code as a JavaFX `Image` from a given text.
     *
     * @param text   The content to encode in the QR code.
     * @param width  The width of the QR code image.
     * @param height The height of the QR code image.
     * @return A JavaFX `Image` containing the QR code.
     * @throws WriterException If the text cannot be encoded as a QR code.
     * @throws IOException     If an I/O error occurs while generating the image.
     */
    public static Image generateQRCodeImage(String text, int width, int height) throws WriterException, IOException {
        // Create a QR code writer
        QRCodeWriter qrCodeWriter = new QRCodeWriter();

        // Generate a BitMatrix representing the QR code
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        // Convert the BitMatrix to a BufferedImage
        BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);

        // Convert the BufferedImage to a JavaFX Image and return it
        return SwingFXUtils.toFXImage(bufferedImage, null);
    }

    /**
     * Displays a QR code in a given JavaFX `ImageView` by generating it from the provided link.
     *
     * @param link      The URL or text to encode in the QR code.
     * @param imageView The `ImageView` where the QR code will be displayed.
     */
    public static void displayQRCode(String link, ImageView imageView) {
        try {
            // Generate the QR code image
            Image qrCodeImage = generateQRCodeImage(link, 250, 250);

            // Set the generated QR code image in the ImageView
            imageView.setImage(qrCodeImage);

            // Configure the ImageView for better display
            imageView.setPreserveRatio(true); // Maintain aspect ratio
            imageView.setSmooth(true);        // Enable smooth rendering
        } catch (WriterException | IOException e) {
            // Print stack trace if an error occurs during QR code generation
            e.printStackTrace();
        }
    }
}
