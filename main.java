import java.io.*;
import java.net.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;

class Program
{
	public static void main(String[] args) {
		try
		{
		URL url=new URL("http://www.avajava.com/images/avajavalogo.jpg");

		InputStream in = url.openStream();
		BufferedImage img = ImageIO.read(url);
		File file = new File("/home/ishu/Pictures/downloaded.jpg");
		ImageIO.write(img, "jpg", file);
	}
	catch(MalformedURLException ex){
}
catch(IOException e) {
  e.printStackTrace();
}
	}
}