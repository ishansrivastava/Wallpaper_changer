import java.io.*;
import java.net.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
/*import org.jsoup.Jsoup;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;*/
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

class Program
{
	public static void HtmlParser(String url)
	{
		String href="",srcset="";
		try
		{
        Document doc = Jsoup.connect(url).get();
        Elements a = doc.getElementsByTag("a");
        for(Element el:a)
        {
        	 href = el.absUrl("href");
        	if(href.matches(".*\\photo-of-the-day\\b.*"))
        	{
        		//getImage(href);
        		break;
        	}
        }
        Document doc_href = Jsoup.connect(href).get();
        Elements source = doc_href.getElementsByTag("source");
       for(Element el:source)
       {
       	srcset=el.absUrl("srcset");
       	break;
       }
       getImage(srcset);
    }
         catch (IOException ex) 
       {
            System.err.println("There was an error");
            Logger.getLogger(DownloadImages.class.getName()).log(Level.SEVERE, null, ex);
	   }
  }
	public static void getImage(String href)
	{
		try
		{
			URL url=new URL(href);
			InputStream in = url.openStream();
			BufferedImage img = ImageIO.read(url);
			File file = new File("/home/ishu/Pictures/wallpaper.jpg");
			ImageIO.write(img, "jpg", file);
		}
		catch(MalformedURLException ex)
		{
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	public static void main(String[] args)
	{
		String url="https://www.nationalgeographic.com/";
		HtmlParser(url);
	}
}