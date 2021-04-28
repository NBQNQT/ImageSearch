import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ImageSearch {
    public static void main(String[] args) throws IOException {
        List<String> hashCodes = new ArrayList<String>();

        String filename = ImageProcess.path + "/flowers/";
        String hashCode = null;
        System.out.println("Query:");
        String sourceHashCode = produceFingerPrint(filename +"27.png");

        System.out.println();

        System.out.println("Results");
        for (int i = 0; i < 210; i++) {
            hashCode = produceFingerPrint(filename + (i + 1) +".png");
            hashCodes.add(hashCode);
            System.out.println(filename + + (i + 1) + ".png");
        }
        System.out.println();

        List<Integer> differences = new ArrayList<Integer>();
        for (int i = 0; i < hashCodes.size(); i++) {
            int difference = hammingDistance(sourceHashCode, hashCodes.get(i));
                differences.add(difference);
        }
        FileWriter writer = new FileWriter("C:\\Users\\Thong\\Downloads\\ImageSearch\\Different.csv");

        for (int j = 0; j < differences.size(); j++) {
            if (differences.get(j) <15) {
                writer.append(Integer.toString(j+1)+",");
                writer.append(String.valueOf(differences.get(j)+ ","));
                writer.append(filename + (j + 1) + ".png");
            } else {
                writer.append(Integer.toString(j+1)+",");
                writer.append("Different");
            }
            writer.append("\n");
        }
        writer.close();

}

    public static int hammingDistance(String sourceHashCode, String hashCode){
        int difference = 0;
        int len = sourceHashCode.length();

        for (int i = 0; i < len; i++) {
            if (sourceHashCode.charAt(i) != hashCode.charAt(i)) {
                difference++;
            }
        }

        return difference;
    }

    public static String produceFingerPrint(String filename) {
        BufferedImage source = ImageProcess.readPNGImage(filename);

        int width = 8;
        int height = 8;

        BufferedImage thumb = ImageProcess.thumb(filename, source, width, height, false);

        int[] pixels = new int[width * height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                pixels[i * height + j] = ImageProcess.rgbToGray(thumb.getRGB(i, j));
            }
        }

        int averagePixel = ImageProcess.average(pixels);
        System.out.println("Average Grayscale:" + averagePixel);

        int[] comps = new int[width * height];
        for (int i = 0; i < comps.length; i++) {
            if (pixels[i] >= averagePixel) {
                comps[i] = 1;
            } else {
                comps[i] = 0;
            }
        }
        StringBuffer hashCode = new StringBuffer();
        for (int i = 0; i < comps.length; i+= 4) {
            int result = comps[i] * (int) Math.pow(2, 3) + comps[i + 1] * (int) Math.pow(2, 2) + comps[i + 2] * (int) Math.pow(2, 1) + comps[i + 3];
            hashCode.append(binaryToHex(result));
        }
        String stringValue = "";
        for (int i = 0; i < comps.length; i++) {
            stringValue += comps[i];
        }
        System.out.println("Image Fingerprint: " + hashCode.toString());
        System.out.println();
        return stringValue;
    }
    private static char binaryToHex(int binary) {
        char ch = ' ';
        switch (binary)
        {
            case 0:
                ch = '0';
                break;
            case 1:
                ch = '1';
                break;
            case 2:
                ch = '2';
                break;
            case 3:
                ch = '3';
                break;
            case 4:
                ch = '4';
                break;
            case 5:
                ch = '5';
                break;
            case 6:
                ch = '6';
                break;
            case 7:
                ch = '7';
                break;
            case 8:
                ch = '8';
                break;
            case 9:
                ch = '9';
                break;
            case 10:
                ch = 'a';
                break;
            case 11:
                ch = 'b';
                break;
            case 12:
                ch = 'c';
                break;
            case 13:
                ch = 'd';
                break;
            case 14:
                ch = 'e';
                break;
            case 15:
                ch = 'f';
                break;
            default:
                ch = ' ';
        }
        return ch;
    }

}
