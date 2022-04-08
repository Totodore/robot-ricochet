package com.example.robotricochet.utils;

import com.twelvemonkeys.image.ResampleOp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.batik.transcoder.Transcoder;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.jetbrains.annotations.Nullable;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Class for manipulating images (adding filters and effects)
 */
@AllArgsConstructor
public class ImageUtils {

    @Getter
    private BufferedImage image;

    public static ImageUtils fromSvg(InputStream stream, int width, int height) throws IOException {

        final int RESOLUTION_DPI = 100;
        final float SCALE_BY_RESOLUTION = RESOLUTION_DPI / 72f;
        final float scaledWidth = width * SCALE_BY_RESOLUTION;
        final float scaledHeight = height * SCALE_BY_RESOLUTION;
        final float pixelUnitToMM = 25.4f / RESOLUTION_DPI;
        // Create a PNG transcoder.
        Transcoder t = new PNGTranscoder();

        // Set the transcoding hints.
        t.addTranscodingHint(PNGTranscoder.KEY_WIDTH, scaledWidth);
        t.addTranscodingHint(PNGTranscoder.KEY_HEIGHT, scaledHeight);
        t.addTranscodingHint(PNGTranscoder.KEY_PIXEL_UNIT_TO_MILLIMETER, pixelUnitToMM);
        try (stream) {
            // Create the transcoder input.
            TranscoderInput input = new TranscoderInput(stream);

            // Create the transcoder output.
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            TranscoderOutput output = new TranscoderOutput(outputStream);

            // Save the image.
            t.transcode(input, output);

            // Flush and close the stream.
            outputStream.flush();
            outputStream.close();
            // Convert the byte stream into an image.
            byte[] imgData = outputStream.toByteArray();
            return new ImageUtils(ImageIO.read(new ByteArrayInputStream(imgData)));
        } catch (IOException | TranscoderException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ImageUtils applyAntiAliasingFilter() {
        return resize(image.getWidth(), image.getHeight());
    }

    /**
     * Resize the image to the given width and height. If one of the dimensions is null the other dimension will be
     * resized to keep the ratio.
     *
     */
    public ImageUtils resize(@Nullable Integer width, @Nullable Integer height) {
        if (width == null && height == null)
            return this;
        float ratio = (float) image.getWidth() / image.getHeight();
        if (width == null)
            width = (int) (ratio * height);
        else if (height == null)
            height = (int) (ratio / width);
        ResampleOp resampleOp = new ResampleOp(width, height);
        BufferedImage dest = resampleOp.filter(image, null);
        image = dest;
        dest.flush();
        return this;
    }

    public ImageUtils applyInvertFilter() {
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                int rgba = image.getRGB(x, y);
                Color col = new Color(rgba, true);
                col = new Color(255 - col.getRed(), 255 - col.getGreen(), 255 - col.getBlue(), col.getAlpha());
                image.setRGB(x, y, col.getRGB());
            }
        }
        return this;
    }

    public ImageUtils applyGaussianBlur(int radius, float sigma) {
        BufferedImage dest = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        ConvolveOp op = getGaussianBlurFilter(radius, sigma);
        op.filter(image, dest);
        image = dest;
        dest.flush();
        return this;
    }

    private static ConvolveOp getGaussianBlurFilter(int radius, float sigma) {
        if (radius < 1) {
            throw new IllegalArgumentException("Radius must be >= 1");
        }

        int size = radius * 2 + 1;
        float[] data = new float[size];

        float twoSigmaSquare = 2.0f * sigma * sigma;
        float sigmaRoot = (float) Math.sqrt(twoSigmaSquare * Math.PI);
        float total = 0.0f;

        for (int i = -radius; i <= radius; i++) {
            float distance = i * i;
            int index = i + radius;
            data[index] = (float) Math.exp(-distance / twoSigmaSquare) / sigmaRoot;
            total += data[index];
        }

        for (int i = 0; i < data.length; i++) {
            data[i] /= total;
        }

        Kernel kernel = new Kernel(size, 1, data);
        return new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
    }
}
