package tel_ran.pictures.model.dao;

import tel_ran.pictures.model.entities.Picture;
import tel_ran.pictures.model.interfaces.IPictures;
import java.util.*;

public class PicturesMap implements IPictures {
	
	private static final float DEFAULT_SIMILARITY = 0.5f;
	private Map<String,Set<String>> tagsPictures;// key - tag, value - set of url's pictures of the tag
	private Map<String,Picture> pictures;// key - url, value - picture object reference
	private float similarity = DEFAULT_SIMILARITY;
	
	public PicturesMap() {
		super();
		this.tagsPictures = new HashMap<String,Set<String>>();
		this.pictures = new HashMap<String,Picture>();
	}
	public PicturesMap(float similarity){
		this();
		this.similarity = similarity;
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
				urlsOfTag = new HashSet<String>();
				tagsPictures.put(tag, urlsOfTag);
			}
			urlsOfTag.add(url);
		}
		return true;
	}

	@Override
	public Iterable<Picture> findSimilar(Picture picture) {
		
		String[] tagsOfPicture = picture.getTags();
		int thresholdSim = (int) (similarity  * tagsOfPicture.length + 0.5f);
		HashMap<String, Integer> urlCount = new HashMap<String, Integer>();
		ArrayList<Picture> listRes = new ArrayList<Picture>();

		for(String tag : tagsOfPicture){
			Set<String> urls = tagsPictures.get(tag);
			if(urls != null)
				addUrlsToUrlCount(urls,urlCount,thresholdSim,listRes);
		}
		return listRes;
	}

	private void addUrlsToUrlCount(Set<String> urls, HashMap<String, Integer> urlCount, int thresholdSim,ArrayList<Picture> listRes) {
		for(String url : urls){
			Integer counter = urlCount.getOrDefault(url,0);
			counter++;
				
			if(counter >= thresholdSim){
				listRes.add(pictures.get(url));
				counter = -1000;
			}
			urlCount.put(url, counter);
			
		}
	}

	@Override
	public Picture getPicture(String url) {
		if(url == null)
			return null;
		return pictures.get(url);
	}

}
