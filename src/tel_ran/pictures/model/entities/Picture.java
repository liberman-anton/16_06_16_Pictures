package tel_ran.pictures.model.entities;

import java.util.Arrays;

public class Picture {
	String url;
	String[] tags;
	
	public Picture(String url, String[] tags) {
		super();
		this.url = url;
		this.tags = tags;
	}
	public String getUrl() {
		return url;
	}
	public String[] getTags() {
		return tags;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(tags);
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Picture other = (Picture) obj;
		if (!Arrays.equals(tags, other.tags))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}
	
	
	
}
