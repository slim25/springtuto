package springtutorial.persistance;

public enum Rating {
	HIGH("High"), MID("Middle"), LOW("Low");
	private String ratingName;

	private Rating(String ratingName) {
		this.ratingName = ratingName;
	}

	public String getRatingName() {
		return ratingName;
	}

	public void setRatingName(String ratingName) {
		this.ratingName = ratingName;
	}
	

}
