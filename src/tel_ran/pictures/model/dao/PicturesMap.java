package tel_ran.pictures.model.dao;

import tel_ran.pictures.model.entities.Picture;
import tel_ran.pictures.model.interfaces.IPictures;
import java.util.*;

public class PicturesMap implements IPictures {
	
	private Map<String,Set<String>> tagsPictures;// key - tag, value - set of url's pictures of the tag
	private Map<String,Picture> pictures;// key - url, value - picture object reference
	
	public PicturesMap() {
		super();
		this.tagsPictures = new HashMap<String,Set<String>>();
		this.pictures = new HashMap<String,Picture>();
	}

	@Override
	public boolean addPicture(Picture picture) {
		String url = picture.getUrl();
		if(pictures.put(url, picture) != null)
			return false;
		
		String[] tags = picture.getTags();
		for(String tag : tags){
			Set<String> urlsOfTag = tagsPictures.get(tag);
			if(urlsOfTag == null){
				Set<String> setOfUrls = new HashSet<String>();
				setOfUrls.add(url);
				tagsPictures.put(tag, setOfUrls);
			} else{
				urlsOfTag.add(url);
			}
		}
		return true;
	}

	@Override
	public Iterable<Picture> findSimilar(Picture picture) {
		// >= 50 % of tags is same
		HashMap<String, Integer> res = new HashMap<String, Integer>();
		String[] tagsOfPicture = picture.getTags();
		for(String tag : tagsOfPicture){
			Set<String> urls = tagsPictures.get(tag);
			if(urls != null)
				addUrlsToRes(urls,res);
		}
		ArrayList<Picture> list = new ArrayList<Picture>();
		Set<Map.Entry<String,Integer>> set = res.entrySet();
		Integer similaryConst = (int) (0.5 * tagsOfPicture.length);
		for(Map.Entry<String,Integer> entry : set){
			if(entry.getValue() >= similaryConst){
				list.add(pictures.get(entry.getKey()));
			}
		}
		return list;
	}

	private void addUrlsToRes(Set<String> urls, HashMap<String, Integer> res) {
		for(String url : urls){
			Integer counter = res.get(url);
			if(counter == null)
				res.put(url, 1);
			else 
				res.put(url, ++counter);
		}
	}

	@Override
	public Picture getPicture(String url) {
		
		return pictures.get(url);
	}

}
