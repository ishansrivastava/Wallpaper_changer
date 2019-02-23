import java.io.*;
import java.net.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;

import org.json.simple.JSONArray; 
import org.json.simple.JSONObject;

import org.json.simple.parser.*; 
import java.io.BufferedReader;  
import java.io.Reader;
import java.nio.charset.Charset;
import java.lang.Thread;

class First_Project extends Thread
{
	private static String readAllJSON(Reader rd) throws IOException
	{
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1)
		{
			sb.append((char) cp);
		}
		return sb.toString();
	}
	
	public static String getImgUrlFromJSON(String url, int index) throws IOException, ParseException
	{
		InputStream is = new URL(url).openStream();
		String jsonText="";
		try
		{
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			jsonText = readAllJSON(rd);
			System.out.println(jsonText);
		}
		catch (IOException ex) 
		{
			System.out.println("General I/O exception: " + ex.getMessage());
		}
		finally {
			is.close();
		}
		JSONParser parser=new JSONParser();
		JSONObject jsonobject=(JSONObject) parser.parse(jsonText);
		JSONArray getArray_item =(JSONArray) jsonobject.get("items");
		JSONObject item=(JSONObject) getArray_item.get(index);
		JSONObject size=(JSONObject)item.get("sizes");
		String img_url=(String)size.get("2048");
		return img_url;
	}
	
	public static void getImageFromURL(String link, String outputPath)
	{
		try
		{
			URL url=new URL(link);
			url.openStream();
			BufferedImage img = ImageIO.read(url);
			File file = new File(outputPath);
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
	
	public static boolean setWallpaper(String filePath)
	{
		String command="gsettings set org.gnome.desktop.background picture-uri "+"file://"+filePath;
		System.out.println(command);
		try
		{
			Runtime.getRuntime().exec(command);
			return true;
		}
		catch(Exception ex)
		{
			return false;
		}
	}
	
	public static void main(String[] args) throws ParseException
	{
		String url="https://www.nationalgeographic.com/photography/photo-of-the-day/_jcr_content/.gallery.json";
		try
		{
			String img_url = getImgUrlFromJSON(url, 2);
			String wallpaperPath = "/home/ishu/Pictures/wallpaper.jpg";
			getImageFromURL(img_url, wallpaperPath);
			while(setWallpaper(wallpaperPath) == false)
			{
				Thread.sleep(5000);
			}
		}
		catch (IOException | InterruptedException ex) 
		{
			System.out.println("General I/O exception: " + ex.getMessage());
		}
	}
}