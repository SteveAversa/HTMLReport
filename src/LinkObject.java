
public class LinkObject {
	String link;
	int percent;
	
	LinkObject(String l,int p){
		link = l;
		percent = p;
	}
	
	void setLink(String l){
		link = l;
	}
	void setP(int p){
		percent = p;
	}
	String getLink(){
		return link;
	}
	int getP(){
		return percent;
	}
	
}
