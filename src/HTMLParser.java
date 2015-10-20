

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet implementation class HTMLParser
 */
@WebServlet("/HTMLParser")
public class HTMLParser extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Vector<LinkObject> objects = new Vector();
	double clicks = 1000;

    /**
     * Default constructor. 
     */
    public HTMLParser() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		objects.clear();
		int count = 0;
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter(  );
		response.setContentType("text/html");
		String fileName = "file.txt";
		URL link = new URL("https://c.cs9.content.force.com/servlet/servlet.FileDownload?file=00PK0000002GjAcMAK/Body");
		InputStream in = new BufferedInputStream(link.openStream());
		ByteArrayOutputStream out1 = new ByteArrayOutputStream();
		byte[] buf = new byte[1024];
		int n = 0;
		while (-1!=(n=in.read(buf)))
		{
		   out1.write(buf, 0, n);
		}
		out1.close();
		in.close();
		byte[] response1 = out1.toByteArray();

		FileOutputStream fos = new FileOutputStream(fileName);
		fos.write(response1);
		fos.close();
    //End download code
		 
		 System.out.println("Finished");
		
		
		FileReader reader = new FileReader("C:/Users/Steve Aversa/workspace/HTMLParser/formatted_creative_2.html");
		StringBuilder sb = new StringBuilder();
		BufferedReader br = new BufferedReader(reader);
		FileWriter fWriter = new FileWriter("fileName.html");
		BufferedWriter writer = new BufferedWriter(fWriter);
		String line;
		
		while ( (line=br.readLine()) != null) {
			  sb.append(line);
			  
			}
		sb.append("<input type=\"submit\" value=\"Submit\"></Form>");
		//sb.append("<FORM method=\"POST\"><INPUT TYPE=\"SUBMIT\" NAME=\"Submit\"></FORM>");

		String text = sb.toString();
		String lastLink = "";
		String regex = "(?i)<a([^>]+)>(.+?)</a>|(?i)<area ([^>]+)>(.+?)>";
		//String regex = "<a (.*?) \"|<a href=\"(.*?)\"|<a style=\"(.*?)\"";
		Pattern pattern =  Pattern.compile(regex);
		Matcher  matcher = pattern.matcher(text);

		while(matcher.find()){
		String imageLink =  matcher.group();
			if (count == 0){
				text = text.replace(imageLink,"<Form method=\"POST\"><input type=\"text\" name=\"InBox0\" value=\"0\"></input>"+imageLink);
				objects.add(new LinkObject(imageLink,count));
			}else{
				text = text.replace(imageLink,"<input type=\"text\" name=\"InBox" + String.valueOf(count) +"\"value=\"0\"></input>"+imageLink);
				objects.add(new LinkObject(imageLink,count));
			}
			count++;
		}
		
		writer.append(text);
		writer.append("<input type=\"submit\" value=\"Submit\"></Form>");
		out.print(text);
		writer.close();
		
		

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		PrintWriter out = response.getWriter(  );
		StringBuilder builder = new StringBuilder();
		builder.append("<!DOCTYPE html>");
		builder.append("<html lang=\"en\">");
		
		builder.append("<p>");
		for (int i = 0; i < objects.size();i++){
			builder.append("<br>");
			String nums = request.getParameter("InBox" + objects.get(i).getP());
			int numClicks = Integer.parseInt(nums);
			String link = objects.get(i).getLink();
			link = returnLink(link);
			objects.get(i).setP(numClicks);
			objects.get(i).setLink(link);
			builder.append(getPercentage(numClicks) +" Clicks: " + "\"" + link + "\"\n");
		}
		builder.append("</p>");
		builder.append("</html>");
		String html = builder.toString();
		out.print(html);
	}
	
	public String returnLink(String text){
		Pattern p = Pattern.compile("href=\"(.*?)\"");
		Matcher m = p.matcher(text);
		String url = null;
		if (m.find()) {
		    url = m.group(1); // this variable should contain the link URL
		}
		return url;
	}
	
	public int getPercentage(int num){
		double newnum = num;
		newnum = (newnum/100 * clicks);
		return (int)newnum;
		
	}


}
