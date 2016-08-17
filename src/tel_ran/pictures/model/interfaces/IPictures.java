package tel_ran.pictures.model.interfaces;

import tel_ran.pictures.model.entities.Picture;

public interface IPictures {
	boolean addPicture(Picture picture);
	Iterable<Picture> findSimilar(Picture picture);
	Picture getPicture(String url);
}
