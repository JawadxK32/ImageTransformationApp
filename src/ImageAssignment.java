import java.awt.*;
import javax.swing.*;
import java.awt.image.*;
/**
 * CPS109 - Assignment 2
 * @author Jawad Khudadad #500526749
 *
 */
public class ImageAssignment {

    /* First, some utility methods that you will need in the methods you write.
       Do not modify these methods in any way. */

    public static int getRed(int rgb) { 
    	return (rgb >> 16) & 0xff; 
    	}
    
    public static int getGreen(int rgb) { 
    	return (rgb >> 8) & 0xff; 
    	}
    
    public static int getBlue(int rgb) { 
    	return rgb & 0xff; 
    	}
    
    public static int rgbColour(int r, int g, int b) {
        return (r << 16) | (g << 8) | b;
    }
    
    public static double brightness(int rgb) {
        int r = getRed(rgb);
        int g = getGreen(rgb);
        int b = getBlue(rgb);
        return 0.21*r + 0.72*g + 0.07*b;
    }

    public static BufferedImage convertToGrayscale(BufferedImage img) {
        BufferedImage result = new BufferedImage(
                img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB
            );
        for(int x = 0; x < img.getWidth(); x++) {
            for(int y = 0; y < img.getHeight(); y++) {
                int col = img.getRGB(x, y);
                int gr = (int)brightness(col);
                result.setRGB(x, y, rgbColour(gr, gr, gr));
            }
        }
        return result;
    }

    /* ----------- Methods that you will write in this assignment. */
    
    /**
     * This method returns a new black & white image of the original image 
     * @param img
     * @param threshold
     * @return
     */
    public static BufferedImage thresholdImage(BufferedImage img, int threshold) {
        
    	BufferedImage result = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
    	
        for(int x = 0; x < img.getWidth(); x++) {
            for(int y = 0; y < img.getHeight(); y++) {
            	int col = img.getRGB(x, y);
                int bri= (int)brightness(col);
                
                if(bri>threshold){
                result.setRGB(x, y, rgbColour(250, 250, 250));
                }
                else{
                	result.setRGB(x, y, rgbColour(0, 0, 0));
                }
            }
        }
        return result; 
    }
    /**
     * This method Creates and returns a new BufferedImage flip horizontally  
     * @param img
     * @return
     */
    public static BufferedImage horizontalMirror(BufferedImage img) {
        
    	BufferedImage result = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
    	
    	for (int x = 0; x < img.getWidth(); x++) {
    		for (int y = 0; y < img.getHeight(); y++) {
    			int col = img.getRGB(x, y);
                int x2 = img.getWidth()-x-1;
                
                result.setRGB(x2, y, col);
    		}
    	}
       return result;
    }
    
    /**
     * This method Creates and returns a new BufferedImage of four half sized copies of the original image
     * @param img
     * @return
     */
    public static BufferedImage splitToFour(BufferedImage img) {

    	BufferedImage result = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
    	
    	int width = img.getWidth()/2;
    	int height = img.getHeight()/2;
    	
    	for(int x=0; x<img.getWidth()/2; x++){
	    	 for(int y=0; y<img.getHeight()/2; y++){
	    		 int col = img.getRGB(x*2,y*2);
	 	 
	    		result.setRGB(x, y, col);
	    		result.setRGB(x+width, y, col);
	    		result.setRGB(x, y+height, col);
	    		result.setRGB(x+width, y+height, col);
	    	 }
    	}
    	 return result;
    }
    
    /**
     * This method Creates and returns a new BufferedImage where each pixel(x,y) equals the weighted sum of the pixels
     * @param img
     * @param mask
     * @return
     */
    public static BufferedImage imageCorrelation(BufferedImage img, double[][] mask) {
        // fill this
    	
    	BufferedImage result = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        
    	for(int y = 1; y<img.getHeight()-1;y++) {
    		for (int x = 1; x<img.getWidth()-1;x++) {
    	
    	double blue = 0;
    	double red = 0;
    	double green = 0;
    	
    	for(int b = 0; b<mask.length;b++) {
    		for(int a= 0; a<mask[0].length;a++) {	
    	
        int col = img.getRGB(x-1+a, b-1+y);
    		blue = blue + (double)getBlue(col)*mask[a][b];
    		red = red + (double)getRed(col)*mask[a][b];
    		green = green + (double)getGreen(col)*mask[a][b];
    	}
    }
    	
    	if(red>255)red=255;
    	if(red<0)red=0;
    	
    	if(blue>255)blue=255;
    	if(blue<0)blue=0;
    	
    	if(green>255)green=255;
    	if(green<0)green=0;
    	
    	result.setRGB(x, y, rgbColour((int)red,(int)green,(int)blue));
    		}
    	}

		return result;
    }
    
    /**
     * This method performs the bubble sort algorithm for each row of pixels of the given image
     * @param img
     * @param n
     * @return
     */
    public static BufferedImage rowPixelSort(BufferedImage img, int n) {
        // fill this in
    	BufferedImage result = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
    	
    	for(int x=0; x<img.getWidth(); x++){
	    	 for(int y=0; y<img.getHeight(); y++){
	    		 int col = img.getRGB(x,y);
	    		result.setRGB(x, y, col);
	    	 }
    	}
    	
    	for(int i=0; i<n; i++){
    		for(int x = 0; x < result.getWidth()-1; x++) {
    			for(int y=0; y<result.getHeight(); y++){
        		//for(int x2 = x-1; x2 < img.getWidth()-1; x2++) {
            	
    				 int col = result.getRGB(x,y) ;
    				 int bri = (int) brightness(col) ;
    				 
                     int col2 = result.getRGB(x+1,y) ;
                     int bri2 = (int) brightness(col2) ;
                     
                     if (bri2<bri)
                     {
                         result.setRGB(x,y,col2) ;
                         result.setRGB(x+1,y,col) ;
                     }
    			}
    		}
    	}
    		return result;
    }
    // ------------------------------------ end of your code

    /* A utility method we need to convert Image objects to BufferedImage, copied from 
     * http://stackoverflow.com/questions/13605248/java-converting-image-to-bufferedimage
     */
    public static BufferedImage toBufferedImage(Image img) {
        if (img instanceof BufferedImage) { 
        	return (BufferedImage) img;}
        // Create a buffered image with transparency
        BufferedImage bimage = new BufferedImage(
                img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB
            );
        // Draw the image on to the buffered image
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();
        // Return the buffered image
        return bimage;
    }

    /* A utility method to create a JPanel instance that displays the given Image. */
    public static JPanel createPanel(Image img) {
        // Define a local class from which we create an object to return as result.
        class ImagePanel extends JPanel {
            private Image img;
            public ImagePanel(Image img) {
                this.img = img;
                this.setPreferredSize(new Dimension(img.getWidth(null), img.getHeight(null)));
            }

            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(img, 0, 0, this);
            }
        }
        return new ImagePanel(img);
    }

    /* The main method to try out the whole shebang. */
    public static void main(String[] args) {
    	
        Image img = Toolkit.getDefaultToolkit().getImage("/Users/JawadxLegend/Desktop/ryerson1.jpg");
        
        MediaTracker m = new MediaTracker(new JPanel());
        m.addImage(img, 0);
        try { m.waitForAll(); } catch(InterruptedException e) { }
        BufferedImage bimg = toBufferedImage(img); 
        JFrame f = new JFrame("CCPS 109 Lab 7");
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setLayout(new GridLayout(2, 3));
        f.add(createPanel(thresholdImage(bimg, 150)));
        f.add(createPanel(horizontalMirror(bimg)));
        f.add(createPanel(splitToFour(bimg)));
        double wt = 1.0/9;
        double[][] blur = {{wt,wt,wt},{wt,wt,wt},{wt,wt,wt}};
        f.add(createPanel(imageCorrelation(bimg, blur)));
        double[][] edged ={{-1,-1,-1},{-1,8,-1},{-1,-1,-1}};
        f.add(createPanel(imageCorrelation(convertToGrayscale(bimg), edged)));
        //double [][] sharpen = {{0,-1,0},{-1,5,-1},{0,-1,0}};
        //f.add(createPanel(imageCorrelation(bimg, sharpen)));
        f.add(createPanel(rowPixelSort(bimg, bimg.getWidth())));
        f.pack();
        f.setVisible(true); 
    }
}
