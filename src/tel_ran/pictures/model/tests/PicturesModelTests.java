package tel_ran.pictures.model.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import java.util.*;

import tel_ran.pictures.model.dao.PicturesMap;
import tel_ran.pictures.model.entities.Picture;
import tel_ran.pictures.model.interfaces.IPictures;

public class PicturesModelTests {

	IPictures pictures;
	String[] urls = {"url1","url2","url3","url4"};
	String[][] tags = {{"tag1","tag10","tag5"},{"tag5","tag80","tag90"},
			{"tag100","tag200","tag10","tag300"},{"tag10","tag1","tag200"}};
	Picture pattern = new Picture("url10",tags[3]);
	
	@Before
	public void setUp() throws Exception {
		pictures = new PicturesMap();
		for(int i = 0; i < urls.length; i++){
			pictures.addPicture(new Picture(urls[i],tags[i]));
		}
	}

	@Test
	public void getPictureTest() {
		for(int i = 0; i < urls.length; i++){
			Picture picture = pictures.getPicture(urls[i]);
			assertArrayEquals(tags[i], picture.getTags());
		}
	}
	@Test
	public void findSimilaryTest(){
		
		Iterable<Picture> similarPictures = pictures.findSimilar(pattern);
		List<Picture> listPictures = new ArrayList<>();
		listPictures.add(pictures.getPicture(urls[0]));
		listPictures.add(pictures.getPicture(urls[2]));
		listPictures.add(pictures.getPicture(urls[3]));
		int ind = 0;
		for(Picture picture : similarPictures){
			assertTrue(listPictures.contains(picture));
			ind++;
		}
		assertEquals(3, ind);
	}
	
	@Test
	public void addPictureTest(){
		assertTrue(pictures.addPicture(pattern));
		assertFalse(pictures.addPicture(pattern));
		//assertFalse(pictures.addPicture(new Picture(urls[0],new String[0])));
	
	}

}
