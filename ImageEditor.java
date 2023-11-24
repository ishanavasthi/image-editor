import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import javax.imageio.ImageIO;

public class ImageEditor {

  /*****************GREYSCALE FUNCTION*****************/
  public static BufferedImage converttogrey(BufferedImage inputImage) {
    int height = inputImage.getHeight();
    int width = inputImage.getWidth();
    BufferedImage outputImage = new BufferedImage(
      width,
      height,
      BufferedImage.TYPE_BYTE_GRAY
    );
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        outputImage.setRGB(j, i, inputImage.getRGB(j, i));
      }
    }
    return outputImage;
  }

  /*****************INCREASE BRIGHTNESS FUNCTION*****************/
  public static BufferedImage increasebrightness(
    BufferedImage inputImage,
    int increase
  ) {
    int height = inputImage.getHeight();
    int width = inputImage.getWidth();
    BufferedImage outImage = new BufferedImage(
      width,
      height,
      BufferedImage.TYPE_3BYTE_BGR
    );
    for (int y = 0; y < height; ++y) {
      for (int x = 0; x < width; ++x) {
        Color pixel = new Color(inputImage.getRGB(x, y));
        int r = pixel.getRed();
        int g = pixel.getGreen();
        int b = pixel.getBlue();
        g = g + (increase * g / 100);
        b = b + (increase * b / 100);
        r = r + (increase * r / 100);
        if (r > 255) r = 255;
        if (b > 255) b = 255;
        if (g > 255) g = 255;
        if (r < 0) r = 0;
        if (b < 0) b = 0;
        if (g < 0) g = 0;
        Color newpixel = new Color(r, g, b);
        outImage.setRGB(x, y, newpixel.getRGB());
      }
    }
    return outImage;
  }

  /*****************DECREASE BRIGHTNESS FUNCTION*****************/
  public static BufferedImage decreasebrightness(
    BufferedImage inputImage,
    int decrease
  ) {
    int height = inputImage.getHeight();
    int width = inputImage.getWidth();
    BufferedImage outImage = new BufferedImage(
      width,
      height,
      BufferedImage.TYPE_3BYTE_BGR
    );
    for (int y = 0; y < height; ++y) {
      for (int x = 0; x < width; ++x) {
        Color pixel = new Color(inputImage.getRGB(x, y));
        int r = pixel.getRed();
        int g = pixel.getGreen();
        int b = pixel.getBlue();
        g = g - (decrease * g / 100);
        b = b - (decrease * b / 100);
        r = r - (decrease * r / 100);
        if (r > 255) r = 255;
        if (b > 255) b = 255;
        if (g > 255) g = 255;
        if (r < 0) r = 0;
        if (b < 0) b = 0;
        if (g < 0) g = 0;
        Color newpixel = new Color(r, g, b);
        outImage.setRGB(x, y, newpixel.getRGB());
      }
    }
    return outImage;
  }

  /*****************ROTATE FUNCTION*****************/
  public static BufferedImage rotate(BufferedImage inputImage, int degrees) {
    AffineTransform transform = new AffineTransform();
    transform.rotate(
      Math.toRadians(degrees),
      inputImage.getWidth() / 2,
      inputImage.getHeight() / 2
    );

    AffineTransformOp op = new AffineTransformOp(
      transform,
      AffineTransformOp.TYPE_BILINEAR
    );
    BufferedImage outputImage = new BufferedImage(
      inputImage.getHeight(),
      inputImage.getWidth(),
      inputImage.getType()
    );

    op.filter(inputImage, outputImage);

    return outputImage;
  }

  /*****************BLUR FUNCTION*****************/
  public static BufferedImage Blur(BufferedImage inputImage, int radius) {
    int width = inputImage.getWidth();
    int height = inputImage.getHeight();
    BufferedImage blurredImage = new BufferedImage(
      width,
      height,
      inputImage.getType()
    );

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        int avgRed = 0;
        int avgGreen = 0;
        int avgBlue = 0;
        int numPixels = 0;

        for (int offsetY = -radius; offsetY <= radius; offsetY++) {
          for (int offsetX = -radius; offsetX <= radius; offsetX++) {
            int posX = x + offsetX;
            int posY = y + offsetY;

            if (posX >= 0 && posX < width && posY >= 0 && posY < height) {
              int pixel = inputImage.getRGB(posX, posY);
              avgRed += (pixel >> 16) & 0xFF;
              avgGreen += (pixel >> 8) & 0xFF;
              avgBlue += pixel & 0xFF;
              numPixels++;
            }
          }
        }
        avgRed /= numPixels;
        avgGreen /= numPixels;
        avgBlue /= numPixels;
        int blurredPixel = (avgRed << 16) | (avgGreen << 8) | avgBlue;
        blurredImage.setRGB(x, y, blurredPixel);
      }
    }
    return blurredImage;
  }

  /*****************INVERT COLORS FUNCTION*****************/
  public static BufferedImage invertColors(BufferedImage inputImage) {
    int width = inputImage.getWidth();
    int height = inputImage.getHeight();
    BufferedImage invertedImage = new BufferedImage(
      width,
      height,
      BufferedImage.TYPE_INT_RGB
    );

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        Color pixel = new Color(inputImage.getRGB(x, y));
        int red = 255 - pixel.getRed();
        int green = 255 - pixel.getGreen();
        int blue = 255 - pixel.getBlue();
        Color invertedPixel = new Color(red, green, blue);
        invertedImage.setRGB(x, y, invertedPixel.getRGB());
      }
    }
    return invertedImage;
  }

  /*****************ROTATE 90 DEGREES ANTI-CLOCKWISE FUNCTION*****************/
  public static BufferedImage rotateCounterclockwise(BufferedImage inputImage) {
    int width = inputImage.getWidth();
    int height = inputImage.getHeight();
    BufferedImage rotatedImage = new BufferedImage(
      height, // Swap width and height
      width, // Swap width and height
      inputImage.getType()
    );

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        int newX = y; // Swap x and y coordinates
        int newY = width - 1 - x;
        rotatedImage.setRGB(newX, newY, inputImage.getRGB(x, y));
      }
    }
    return rotatedImage;
  }

  private static void saveImage(BufferedImage image, String outputPath) {
    try {
      File output = new File(outputPath);
      ImageIO.write(image, "jpg", output);

      /********OUTPUT PRINT STATEMENT**********/
      System.out.println();
      System.out.println(
        "  ##   ##    ##     ### ##   ### ###           ### ##   ##  ##   "
      );
      System.out.println(
        "   ## ##      ##     ##  ##   ##  ##            ##  ##  ##  ##   "
      );
      System.out.println(
        "  # ### #   ## ##    ##  ##   ##                ##  ##  ##  ##   "
      );
      System.out.println(
        "  ## # ##   ##  ##   ##  ##   ## ##             ## ##    ## ##   "
      );
      System.out.println(
        "  ##   ##   ## ###   ##  ##   ##                ##  ##    ##     "
      );
      System.out.println(
        "  ##   ##   ##  ##   ##  ##   ##  ##            ##  ##    ##     "
      );
      System.out.println(
        "  ##   ##  ###  ##  ### ##   ### ###           ### ##     ##     "
      );
      System.out.println();
      System.out.println(
        "  ####    ## ##   ###  ##    ##     ###  ##             ##     ### ###    ##      ## ##   #### ##  ###  ##    ####   "
      );
      System.out.println(
        "   ##    ##   ##   ##  ##     ##      ## ##              ##     ##  ##     ##    ##   ##  # ## ##   ##  ##     ##    "
      );
      System.out.println(
        "   ##    ####      ##  ##   ## ##    # ## #            ## ##    ##  ##   ## ##   ####       ##      ##  ##     ##    "
      );
      System.out.println(
        "   ##     #####    ## ###   ##  ##   ## ##             ##  ##   ##  ##   ##  ##   #####     ##      ######     ##    "
      );
      System.out.println(
        "   ##        ###   ##  ##   ## ###   ##  ##            ## ###   ### ##   ## ###      ###    ##      ##  ##     ##    "
      );
      System.out.println(
        "   ##    ##   ##   ##  ##   ##  ##   ##  ##            ##  ##    ###     ##  ##  ##   ##    ##      ##  ##     ##    "
      );
      System.out.println(
        "  ####    ## ##   ###  ##  ###  ##  ###  ##           ###  ##     ##    ###  ##   ## ##    ####    ###  ##    ####   "
      );
      System.out.println();
      System.out.println("Image saved as " + outputPath);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /*****************MAIN FUNCTION*****************/
  public static void main(String[] args) {
    try {
      Scanner scanner = new Scanner(System.in);
      System.out.println(
        "Welcome to Command Line Image Editor made by Ishan Avasthi. Visit https://ishanavasthi.in"
      );

      // Input and output file paths
      System.out.println("Enter the path of the image to edit: ");
      String inputImagePath = scanner.nextLine();
      BufferedImage image = ImageIO.read(new File(inputImagePath));

      if (image == null) {
        System.out.println("I was unable to locate the image :(");
        return;
      }

      System.out.print("Enter the path of the output image: ");
      String outputImagePath = scanner.nextLine();

      System.out.println();
      System.out.println("I got the Image! :)");
      System.out.println();
      System.out.println("Now choose options [1-8] from below: ");
      System.out.println();
      System.out.println("1. Increase Brightness");
      System.out.println("2. Decrease Brightness");
      System.out.println("3. Grayscale");
      System.out.println("4. Rotate Clockwise");
      System.out.println("5. Blur");
      System.out.println("6. Invert Colors");
      System.out.println("7. Rotate Counter-clockwise");
      System.out.println("8. Exit");

      System.out.println();
      // System.out.print("Enter your choice [1-6]: ");
      int choice = scanner.nextInt();

      switch (choice) {
        case 1:
          image = increasebrightness(image, 10);
          break;
        case 2:
          image = decreasebrightness(image, 10);
          break;
        case 3:
          image = converttogrey(image);
          break;
        case 4:
          image = rotate(image, 90);
          break;
        case 5:
          image = Blur(image, 5);
          break;
        case 6:
          image = invertColors(image); // Apply the invertColors function
          break;
        case 7:
          image = rotate(image, -90); // Apply the rotate function with -90 degrees to rotate counterclockwise
          break;
        case 8:
          System.out.println(
            "Thank you for trying my Image Editor! If you want to run this again, you can do 'java ImageEditor'."
          );
          break;
        default:
          System.out.println(
            "Invalid choice. Please choose a number between 1-8"
          );
      }

      if (choice >= 1 && choice <= 7) {
        saveImage(image, outputImagePath);
      }

      scanner.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
/*****************END OF CODE*****************/
