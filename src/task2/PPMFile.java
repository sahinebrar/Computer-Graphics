
package task2;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class PPMFile {
    ArrayList<Integer> array;
    private byte bytes[] = null;     
    private double doubles[] = null;
    private String filename = null;     
    int height = 0;
    int width = 0;
    BufferedImage bi;
    File f;

    public PPMFile(String filename) throws FileNotFoundException, IOException {
        array= new ArrayList<Integer>();
        this.filename = filename;
        readImage();
        bi= PPMFile.ppm(width, height, 255, array.toArray());                
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public double[] getDouble() {
        return doubles;
    }

    private void readImage() throws FileNotFoundException, IOException, NumberFormatException {

        // read PPM format image
        bytes = null;
        char buffer;                   // character in PPM header
        String id = new String();      // PPM magic number 
        String dim = new String();     // image dimension as a string
        int count = 0;
        File f = new File(filename);
        FileInputStream isr = new FileInputStream(f);

        boolean weird = false;

        do {
            buffer = (char) isr.read();
            id = id + buffer;
            count++;
        } while (buffer != '\n' && buffer != ' ');

        if (id.charAt(0) == 'P') {
            buffer = (char) isr.read();
            count++;
            if (buffer == '#') {
                do {
                    buffer = (char) isr.read();
                    count++;
                } while (buffer != '\n');
                count++;
                buffer = (char) isr.read();
            }
            // second header line is "width height\n"
            do {
                dim = dim + buffer;
                buffer = (char) isr.read();
                count++;
            } while (buffer != ' ' && buffer != '\n');

            width = Integer.parseInt(dim.trim());

            dim = new String();
            buffer = (char) isr.read();
            count++;
            do {
                dim = dim + buffer;
                buffer = (char) isr.read();
                count++;
            } while (buffer != ' ' && buffer != '\n');
            height = Integer.parseInt(dim);
            
            do {           
                buffer = (char) isr.read();
                count++;
            } while (buffer != ' ' && buffer != '\n');

            bytes = new byte[height * width];
            
            doubles = new double[height * width];
            
            buffer= (char) isr.read();
            String counter="";
            while(!"A".equals(String.valueOf(buffer))){
                
                if(buffer=='\n'){
                    array.add(Integer.parseInt(counter));

                    counter="";
                }
                if(buffer!='\n')
                    counter+=buffer;
                buffer= (char) isr.read();
            }
            
            isr.close();
        } else {
            width = height = 0;
            doubles = new double[0];
            bytes = new byte[0];
            throw new NumberFormatException("Wrong header information!");
        }
    }
    static public BufferedImage ppm(int width, int height, int maxcolval, Object[] data2){
        
        if(maxcolval<256){
            BufferedImage image=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
            int r,g,b,k=0,pixel;
            if(maxcolval==255){                         
                for(int y=0;y<height;y++){
                    for(int x=0;(x<width)&&((k+3)<data2.length);x++){
                        r=((int)data2[k++]) & 0xFF;
                        g=((int)data2[k++]) & 0xFF;
                        b=((int)data2[k++]) & 0xFF;
                        pixel=0xFF000000+(r<<16)+(g<<8)+b;
                        image.setRGB(x,y,pixel);
                    }
                }
            }
            else{
                for(int y=0;y<height;y++){
                    for(int x=0;(x<width)&&((k+3)<data2.length);x++){
                        r=((int)data2[k++]) & 0xFF;r=((r*255)+(maxcolval>>1))/maxcolval;  
                        g=((int)data2[k++]) & 0xFF;g=((g*255)+(maxcolval>>1))/maxcolval;
                        b=((int)data2[k++]) & 0xFF;b=((b*255)+(maxcolval>>1))/maxcolval;
                        pixel=0xFF000000+(r<<16)+(g<<8)+b;
                        image.setRGB(x,y,pixel);
                    }
                }
            }
            return image;
        }
        else{


            BufferedImage image=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
            int r,g,b,k=0,pixel;
            for(int y=0;y<height;y++){
                for(int x=0;(x<width)&&((k+6)<data2.length);x++){
                    r=(((int)data2[k++]) & 0xFF)|((((int)data2[k++]) & 0xFF)<<8);r=((r*255)+(maxcolval>>1))/maxcolval;  // scale to 0..255 range
                    g=(((int)data2[k++]) & 0xFF)|((((int)data2[k++]) & 0xFF)<<8);g=((g*255)+(maxcolval>>1))/maxcolval;
                    b=(((int)data2[k++]) & 0xFF)|((((int)data2[k++]) & 0xFF)<<8);b=((b*255)+(maxcolval>>1))/maxcolval;
                    pixel=0xFF000000+(r<<16)+(g<<8)+b;
                    image.setRGB(x,y,pixel);
                }
            }
            return image;
        }
    }
    void save(String imgName) throws IOException{
        f=new File("/Users/ebrarsahin/Desktop/"+imgName+".jpg");
        ImageIO.write(bi, "jpg", f);
    }

}
